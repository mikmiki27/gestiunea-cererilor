package com.example.gestiuneacererilor.data.managers.usermanager

import android.content.Context
import com.example.gestiuneacererilor.data.managers.authmanager.AuthManager
import com.example.gestiuneacererilor.data.model.UserAuthData
import com.example.gestiuneacererilor.data.restmanager.UserService
import com.example.gestiuneacererilor.data.restmanager.data.*
import io.reactivex.Single

class UserManagerImpl(private val userService: UserService,
                      private val authManager: AuthManager
): UserManager {

    override fun getUser(iamId: String, context: Context): Single<GetUserResponse> {
        return userService.getUser(iamId)
            .map { response ->
                authManager.setUserAuthData(context, UserAuthData(
                    email = response.email,
                    iamId = response.iamId,
                    id = response.id,
                    name = response.name,
                    phone = response.phone,
                    rev = response.rev,
                    ciclu = response.ciclu,
                    refreshToken = authManager.getUserAuthData(context).refreshToken
                   )
                )
                response
            }
    }

    override fun saveUser(saveUserRequestBody: SaveUserRequestBody): Single<SaveUserResponse> {
        return userService.saveUser(saveUserRequestBody)
    }

    override fun updateUser(context: Context, saveUserRequestBody: SaveUserRequestBody): Single<SaveUserResponse> {
        return userService.getUser(saveUserRequestBody.profile.iamId)
            .flatMap { response ->
                saveUserRequestBody.profile.rev = response.rev
                userService.saveUser(saveUserRequestBody)
            }
            .map { response ->
                authManager.setUserAuthData(context,
                    UserAuthData(
                        email = saveUserRequestBody.profile.email,
                        iamId = saveUserRequestBody.profile.iamId,
                        id = response.id,
                        name = saveUserRequestBody.profile.name,
                        phone = saveUserRequestBody.profile.phone,
                        rev = response.rev,
                        ciclu = saveUserRequestBody.profile.ciclu,
                        refreshToken = authManager.getUserAuthData(context).refreshToken
                    )
                )
                response
            }


    }

    override fun createPendingId(createIdRequestBody: CreateIdRequestBody): Single<CreateIdResponse> {
        return userService.createPendingId(createIdRequestBody)
    }
}