package com.example.gestiuneacererilor.data.managers.cereremanager

import com.example.gestiuneacererilor.data.restmanager.data.Cerere
import com.example.gestiuneacererilor.data.restmanager.data.GetAllCerere
import io.reactivex.Single

interface CerereManager {
    fun getAllCerere(): Single<GetAllCerere>
    fun getCerereByStudentEmail(email: String): Single<List<Cerere>>
    fun getCerereByProfesorEmail(email: String): Single<List<Cerere>>
    //fun getCerereById(@Path(value = "id") id: String): Single<List<Cerere>>
    fun updateCerereById(id: String, cerere: Cerere): Single<Cerere>

}