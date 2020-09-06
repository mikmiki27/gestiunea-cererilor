package com.example.gestiuneacererilor.data.restmanager

import com.example.gestiuneacererilor.BuildConfig
import com.example.gestiuneacererilor.data.restmanager.data.*
import com.example.gestiuneacererilor.data.restmanager.util.CustomHttpClient
import com.google.gson.GsonBuilder
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface CerereService {
    @Headers(value = ["Content-Type: application/json"])
    @POST("cerere/nou")
    fun enterNewCerere(@Body request: Cerere): Single<Cerere>

    @GET("cerere")
    fun getAllCerere(): Single<List<Cerere>>

    @GET("cerere/{email}")
    fun getCerereByStudentEmail(@Path(value = "email") email: String): Single<List<Cerere>>

    @GET("cerere/{email}")
    fun getCerereByProfesorEmail(@Path(value = "email") email: String): Single<List<Cerere>>

    /*@GET("cerere/{id}")
    fun getCerereById(@Path(value = "id") id: String): Single<List<Cerere>>*/

    @PUT("cerere/{id}")
    fun updateCerereById(@Path(value = "id") id: String, @Body cerere: Cerere): Single<Cerere>

    companion object {
        fun create(): CerereService {

            val httpClient = CustomHttpClient().provideHttpClientDefaultBuilder()

            return Retrofit.Builder()
                .baseUrl(BuildConfig.BACKEND_API)
                .client(httpClient.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build()
                .create(CerereService::class.java)
        }
    }
}