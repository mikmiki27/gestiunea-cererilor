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

interface StudentService {

    @Headers(value = ["Content-Type: application/json"])
    @POST("student/nou")
    fun enterNewStudent(@Body request: NewStudentRequestBody): Single<NewStudentRequestBody>

    @GET("student")
    fun getAllStudents(): Single<GetAllStudentsResponse>

    @GET("student/")
    fun getStudentByEmail(@Query(value = "", encoded = true) email: String): Single<List<NewStudentRequestBody>>

    @PUT("student/{id}")
    fun updateStudentById(@Path("id") id: String, @Body student: Student): Single<NewStudentRequestBody>

    companion object {
        fun create(): StudentService {

            val httpClient = CustomHttpClient().provideHttpClientDefaultBuilder()

            return Retrofit.Builder()
                .baseUrl(BuildConfig.BACKEND_API)
                .client(httpClient.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build()
                .create(StudentService::class.java)
        }
    }
}