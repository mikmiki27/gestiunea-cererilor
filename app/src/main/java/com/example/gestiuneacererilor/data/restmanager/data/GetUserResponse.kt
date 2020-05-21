package com.example.gestiuneacererilor.data.restmanager.data

data class GetUserResponse(
    val id: String,
    val rev: String,
    val iamId: String,
    val name: String,
    val phone: String?,
    val email: String,
    val ciclu: String
)