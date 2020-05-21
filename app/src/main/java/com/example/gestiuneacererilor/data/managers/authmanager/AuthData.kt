package com.example.gestiuneacererilor.data.managers.authmanager

import com.ibm.cloud.appid.android.api.tokens.AccessToken
import com.ibm.cloud.appid.android.api.tokens.IdentityToken
import com.ibm.cloud.appid.android.api.tokens.RefreshToken

data class AuthData(
    val accessToken: AccessToken?,
    val identityToken: IdentityToken?,
    val refreshToken: RefreshToken?
)