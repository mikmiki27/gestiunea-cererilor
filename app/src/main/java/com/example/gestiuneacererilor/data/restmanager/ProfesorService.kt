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

interface ProfesorService {
    @Headers(value = ["Content-Type: application/json"])
    @POST("profesor/nou")
    fun enterNewProfesor(@Body request: NewProfesorRequestBody): Single<NewProfesorRequestBody>

    @GET("profesor")
    fun getAllProfessors(): Single<GetAllProfessorsResponse>

    @GET("profesor/")
    fun getProfesorByEmail(@Query(value = "", encoded = true) email: String): Single<List<NewProfesorRequestBody>>

    companion object {
        fun create(): ProfesorService {

            val httpClient = CustomHttpClient().provideHttpClientDefaultBuilder()

            return Retrofit.Builder()
                .baseUrl(BuildConfig.BACKEND_API)
                .client(httpClient.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build()
                .create(ProfesorService::class.java)
        }
    }
}