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

    @GET("profesor/{email}")
    fun getProfesorByEmail(@Path(value = "email") email: String): Single<List<NewProfesorRequestBody>>

    /*@GET("profesor/{id}")
    fun getProfesorById(@Path(value = "id") id: String): Single<List<NewProfesorRequestBody>>*/

    @PUT("profesor/{id}")
    fun updateProfesorById(@Path(value = "id") id: String, @Body profesor: Profesor): Single<NewProfesorRequestBody>

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