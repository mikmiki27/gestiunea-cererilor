package com.example.gestiuneacererilor.data.restmanager

import com.example.gestiuneacererilor.BuildConfig
import com.example.gestiuneacererilor.data.restmanager.data.*
import com.google.gson.GsonBuilder
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface UserService {

    @Headers(value = ["Content-Type: application/json"])
    @GET("profile/{iamId}")
    fun getUser(@Query("iamId") iamId: String): Single<GetUserResponse>

    @Headers(value = ["Content-Type: application/json"])
    @POST("profile")
    fun saveUser(@Body saveUserRequestBody: SaveUserRequestBody): Single<SaveUserResponse>

    @Headers(value = ["Content-Type: application/json"])
    @POST("index/pending")
    fun createPendingId(@Body createIdRequestBody: CreateIdRequestBody): Single<CreateIdResponse>

    companion object {
        fun create(): UserService {

            val httpClient = CustomHttpClient().provideHttpClientDefaultBuilder()

            return Retrofit.Builder()
                .baseUrl(BuildConfig.IBM_BACKEND_API)
                .client(httpClient.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build()
                .create(UserService::class.java)
        }
    }
}