package com.example.gestiuneacererilor.data.restmanager

import com.example.gestiuneacererilor.BuildConfig
import com.example.gestiuneacererilor.data.restmanager.data.Sedinta
import com.example.gestiuneacererilor.data.restmanager.util.CustomHttpClient
import com.google.gson.GsonBuilder
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface SedintaService {
    @Headers(value = ["Content-Type: application/json"])
    @POST("sedinta/nou")
    fun enterNewSedinta(@Body request: Sedinta): Single<Sedinta>

    @GET("sedinta")
    fun getAllSedinte(): Single<List<Sedinta>>

    @GET("sedinta/")
    fun getSedintaByStudentEmail(@Query(value = "", encoded = true) email: String): Single<List<Sedinta>>

   /* @GET("sedinta/")
    fun getSedintaByProfesorEmail(@Query(value = "", encoded = true) email: String): Single<List<Sedinta>>
*/
    /*@GET("sedinta/{id}")
    fun getSedintaById(@Path(value = "id") id: String): Single<List<Sedinta>>*/

    @PUT("sedinta/{id}")
    fun updateSedintaById(@Path(value = "id") id: String, @Body sedinta: Sedinta): Single<Sedinta>

    companion object {
        fun create(): SedintaService {

            val httpClient = CustomHttpClient().provideHttpClientDefaultBuilder()

            return Retrofit.Builder()
                .baseUrl(BuildConfig.BACKEND_API)
                .client(httpClient.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build()
                .create(SedintaService::class.java)
        }
    }
}