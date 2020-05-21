package com.example.gestiuneacererilor.data.managers.authmanager

import android.app.Activity
import android.content.Context
import com.example.gestiuneacererilor.BuildConfig
import com.example.gestiuneacererilor.data.model.UserAuthData
import com.example.gestiuneacererilor.data.restmanager.data.GetUserResponse
import com.example.gestiuneacererilor.data.restmanager.data.SaveUserResponse
import com.example.gestiuneacererilor.utils.Constants
import com.google.gson.Gson
import com.ibm.cloud.appid.android.api.AppID
import com.ibm.cloud.appid.android.api.AppIDAuthorizationManager
import com.ibm.cloud.appid.android.api.AuthorizationException
import com.ibm.cloud.appid.android.api.AuthorizationListener
import com.ibm.cloud.appid.android.api.tokens.AccessToken
import com.ibm.cloud.appid.android.api.tokens.IdentityToken
import com.ibm.cloud.appid.android.api.tokens.RefreshToken
import com.ibm.cloud.appid.android.api.userprofile.UserProfileException
import com.ibm.cloud.appid.android.api.userprofile.UserProfileResponseListener
import io.reactivex.Single
import org.json.JSONObject

class AuthManagerImpl private constructor() :
    AuthManager {
    val region = AppID.REGION_UK
    var appId: AppID? = null
    var appIdAuthorizationManager: AppIDAuthorizationManager? = null
    var userIdentityToken: IdentityToken? = null


    companion object {
        private var instance: AuthManagerImpl? = null
        fun getInstance(): AuthManagerImpl {
            if (instance == null) {
                instance =
                    AuthManagerImpl()
            }
            return instance!!
        }
    }

    override fun initAppId(context: Context) {
        if (appId == null) {
            appId = AppID.getInstance()
            appId?.initialize(context, BuildConfig.TENANT_ID, region)
            appIdAuthorizationManager = AppIDAuthorizationManager(appId)
        }
    }

    override fun tryLoginUserWithRefreshToken(context: Context): Single<AuthData> {
        checkInitialization()

        return Single.create {

            val refreshToken = getUserAuthData(context).refreshToken
            if (refreshToken.isNotEmpty()) {

                appId?.signinWithRefreshToken(
                    context,
                    refreshToken,
                    object : AuthorizationListener {
                        override fun onAuthorizationCanceled() {
                            it.onError(Exception("onAuthorizationCanceled"))
                        }

                        override fun onAuthorizationFailure(exception: AuthorizationException?) {
                            it.onError(exception?.cause ?: Exception("onAuthorizationFailure"))
                        }

                        override fun onAuthorizationSuccess(
                            accessToken: AccessToken?,
                            identityToken: IdentityToken?,
                            refreshToken: RefreshToken?
                        ) {
                            val authData =
                                AuthData(
                                    accessToken,
                                    identityToken,
                                    refreshToken
                                )
                            userIdentityToken = identityToken
                            saveAuthData(authData, context)
                            it.onSuccess(authData)
                        }

                    }
                )
            } else {
                it.onError(Exception("Refresh token Empty"))
            }
        }
    }

    override fun launchSignUp(activity: Activity): Single<AuthData> {
        checkInitialization()

        val loginWidget = appId?.loginWidget

        return Single.create {

            loginWidget?.launchSignUp(activity,
                object : AuthorizationListener {
                    override fun onAuthorizationCanceled() {
                        it.onError(Exception("onAuthorizationCanceled"))
                    }

                    override fun onAuthorizationFailure(exception: AuthorizationException?) {
                        it.onError(exception?.cause ?: Exception("onAuthorizationFailure"))
                    }

                    override fun onAuthorizationSuccess(
                        accessToken: AccessToken?,
                        identityToken: IdentityToken?,
                        refreshToken: RefreshToken?
                    ) {
                        val authData =
                            AuthData(
                                accessToken,
                                identityToken,
                                refreshToken
                            )
                        saveAuthData(authData, activity)
                        it.onSuccess(authData)
                    }
                }
            )
        }
    }

    override fun signInWithResourceOwnerPassword(
        context: Context,
        email: String,
        password: String
    ): Single<AuthData> {
        checkInitialization()

        return Single.create {

            appId?.signinWithResourceOwnerPassword(context,
                email,
                password,
                object : AuthorizationListener {
                    override fun onAuthorizationCanceled() {
                        it.onError(Exception("onAuthorizationCanceled"))
                    }

                    override fun onAuthorizationFailure(exception: AuthorizationException?) {
                        it.onError(exception?.cause ?: Exception("onAuthorizationFailure"))
                    }

                    override fun onAuthorizationSuccess(
                        accessToken: AccessToken?,
                        identityToken: IdentityToken?,
                        refreshToken: RefreshToken?
                    ) {
                        val authData =
                            AuthData(
                                accessToken,
                                identityToken,
                                refreshToken
                            )
                        saveAuthData(authData, context)
                        it.onSuccess(authData)
                    }
                }
            )
        }

    }

    override fun logOut(context: Context) {
        checkInitialization()
        appId!!.logout()
        deleteAuthData(context)
    }

    override fun forgotPassword(activity: Activity): Single<AuthData> {
        checkInitialization()

        val loginWidget = appId?.loginWidget

        return Single.create {

            loginWidget?.launchForgotPassword(activity,
                object : AuthorizationListener {
                    override fun onAuthorizationCanceled() {
                        it.onError(Exception("onAuthorizationCanceled"))
                    }

                    override fun onAuthorizationFailure(exception: AuthorizationException?) {
                        it.onError(exception?.cause ?: Exception("onAuthorizationFailure"))
                    }

                    override fun onAuthorizationSuccess(
                        accessToken: AccessToken?,
                        identityToken: IdentityToken?,
                        refreshToken: RefreshToken?
                    ) {
                        val authData =
                            AuthData(
                                accessToken,
                                identityToken,
                                refreshToken
                            )
                        saveAuthData(authData, activity)
                        it.onSuccess(authData)
                    }

                }
            )
        }
    }

    override fun setUserCustomAttribute(key: String, value: String): Single<Boolean> {
        checkInitialization()
        return Single.create {
            appId!!.userProfileManager
                .setAttribute(key, value, object : UserProfileResponseListener {
                    override fun onFailure(e: UserProfileException?) {
                        it.onError(e!!)
                    }

                    override fun onSuccess(attributes: JSONObject) {
                        it.onSuccess(true)
                    }
                })
        }

    }

    private fun saveAuthData(authData: AuthData, context: Context) {

        val user = UserAuthData(
            email = authData.identityToken!!.email,
            iamId = authData.identityToken.identities.getJSONObject(0).getString("id"),
            refreshToken = authData.refreshToken!!.raw,
            name = authData.identityToken.name
        )

        setUserAuthData(context, user)
    }

    private fun deleteAuthData(context: Context) {
        val sharedPref =
            context.getSharedPreferences(Constants.SHARED_PREF_FILE, Context.MODE_PRIVATE)

        val editor = sharedPref.edit()

        editor.putString(Constants.USER_KEY, null)

        editor.commit()
    }

    override fun getUserAuthData(context: Context): UserAuthData {
        val sharedPref =
            context.getSharedPreferences(Constants.SHARED_PREF_FILE, Context.MODE_PRIVATE)

        val json = sharedPref.getString(Constants.USER_KEY, null)
        val user: UserAuthData = Gson().fromJson(json, UserAuthData::class.java)

        return user
    }

    override fun setUserAuthData(context: Context, user: UserAuthData) {
        val sharedPref =
            context.getSharedPreferences(Constants.SHARED_PREF_FILE, Context.MODE_PRIVATE)

        val editor = sharedPref.edit()

        val userJson = Gson().toJson(user)
        editor.putString(Constants.USER_KEY, userJson)

        editor.commit()
    }

    override fun updateUserAuthData(
        saveUserResponse: SaveUserResponse,
        phone: String,
        context: Context
    ) {
        var userAuth = getUserAuthData(context)

        userAuth.phone = phone
        userAuth.id = saveUserResponse.id
        userAuth.rev = saveUserResponse.rev

        setUserAuthData(context, userAuth)
    }

    override fun updateUserAuthData(getUserResponse: GetUserResponse, context: Context) {
        var userAuth = getUserAuthData(context)

        userAuth.phone = getUserResponse.phone
        userAuth.id = getUserResponse.id
        userAuth.rev = getUserResponse.rev
        userAuth.ciclu = getUserResponse.ciclu


        val sharedPref =
            context.getSharedPreferences(Constants.SHARED_PREF_FILE, Context.MODE_PRIVATE)

        val editor = sharedPref.edit()

        val userJson = Gson().toJson(userAuth)
        editor.putString(Constants.USER_KEY, userJson)

        editor.commit()
    }

    private fun checkInitialization() {
        if (appId == null) throw Exception("App Id not initialized")
    }
}