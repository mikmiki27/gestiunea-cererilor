package com.example.gestiuneacererilor.data.managers.authmanager

import android.app.Activity
import android.content.Context
import com.example.gestiuneacererilor.data.model.UserAuthData
import com.example.gestiuneacererilor.data.restmanager.data.GetUserResponse
import com.example.gestiuneacererilor.data.restmanager.data.SaveUserResponse
import io.reactivex.Single

interface AuthManager {
    fun initAppId(context: Context)

    fun tryLoginUserWithRefreshToken(context: Context): Single<AuthData>

    fun launchSignUp(activity: Activity): Single<AuthData>

    fun signInWithResourceOwnerPassword(
        context: Context,
        email: String,
        password: String
    ): Single<AuthData>

    fun forgotPassword(activity: Activity): Single<AuthData>

    fun setUserCustomAttribute(key: String, value: String): Single<Boolean>

    fun getUserAuthData(context: Context): UserAuthData

    fun updateUserAuthData(saveUserResponse: SaveUserResponse, phone: String, context: Context)

    fun setUserAuthData(context: Context, user: UserAuthData)

    fun updateUserAuthData(getUserResponse: GetUserResponse, context: Context)

    fun logOut(context: Context)
}