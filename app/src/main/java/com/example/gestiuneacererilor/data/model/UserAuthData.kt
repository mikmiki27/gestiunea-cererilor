package com.example.gestiuneacererilor.data.model

data class UserAuthData(
    var email: String,
    var iamId: String,
    var id: String? = null,
    var name: String,
    var phone: String? = null,
    var rev: String? = null,
    var ciclu: String? =null,
    var refreshToken: String
)