package com.example.gestiuneacererilor.data.restmanager.data

data class CerereRequestBody(
    val request: CerereRequest
)

data class CerereRequest(
    val requesterId: String,
    val description: String,
    val location: RequestLocation,
    val tags: ArrayList<String>,
    val notes: String,
    val requester: Requester,
    val date: Long
)

data class RequestLocation(
    val lat: String,
    val long: String,
    val address: String
)

data class Requester(
    val name: String,
    val phone: String
)