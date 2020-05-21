package com.example.gestiuneacererilor.data.managers.usermanager

import android.content.Context
import com.example.gestiuneacererilor.data.restmanager.data.*
import io.reactivex.Single

interface UserManager {
    fun getUser(iamId: String, context: Context):Single<GetUserResponse>
    fun saveUser(saveUserRequestBody: SaveUserRequestBody):Single<SaveUserResponse>
    fun updateUser(context: Context, saveUserRequestBody: SaveUserRequestBody): Single<SaveUserResponse>
    fun createPendingId(createIdRequestBody: CreateIdRequestBody): Single<CreateIdResponse>
}