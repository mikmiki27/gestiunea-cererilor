package com.example.gestiuneacererilor.data.restmanager

import com.example.gestiuneacererilor.BuildConfig
import com.example.gestiuneacererilor.data.restmanager.data.CerereRequestBody
import com.example.gestiuneacererilor.data.restmanager.data.CerereRequestResponse
import com.google.gson.GsonBuilder
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface RequestsService {

    @Headers(value = ["Content-Type: application/json"])
    @POST("cerere")
    fun submitRequest(@Body request: CerereRequestBody): Single<CerereRequestResponse>

    companion object {
        fun create(): RequestsService {

            val httpClient = CustomHttpClient().provideHttpClientDefaultBuilder()

            return Retrofit.Builder()
                .baseUrl(BuildConfig.IBM_BACKEND_API)
                .client(httpClient.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build()
                .create(RequestsService::class.java)
        }
    }
}