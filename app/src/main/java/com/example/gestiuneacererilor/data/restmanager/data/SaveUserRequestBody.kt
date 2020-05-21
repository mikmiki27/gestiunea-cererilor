package com.example.gestiuneacererilor.data.restmanager.data

import com.example.gestiuneacererilor.data.model.UserAuthData

data class SaveUserRequestBody(var profile: ProfileRequest) {

    constructor(userAuthData: UserAuthData) : this(
        ProfileRequest(
            email = userAuthData.email,
            iamId = userAuthData.iamId,
            id = userAuthData.id,
            name = userAuthData.name,
            phone = userAuthData.phone!!,
            rev = userAuthData.rev,
            ciclu = userAuthData.ciclu
        )
    )
}