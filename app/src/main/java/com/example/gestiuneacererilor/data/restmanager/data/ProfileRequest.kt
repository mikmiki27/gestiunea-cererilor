package com.example.gestiuneacererilor.data.restmanager.data

data class ProfileRequest(
    val email: String,
    val iamId: String,
    val id: String? = null,
    val name: String,
    val phone: String,
    var rev: String? = null,
    var ciclu: String? = null
)