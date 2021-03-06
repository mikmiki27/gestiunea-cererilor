package com.example.gestiuneacererilor.data.managers.cereremanager

import com.example.gestiuneacererilor.data.restmanager.data.Cerere
import io.reactivex.Single

interface CerereManager {
    fun getAllCerere(): Single<List<Cerere>>
    fun getCerereByStudentEmail(email: String): Single<List<Cerere>>
    fun getCerereByProfesorEmail(email: String): Single<List<Cerere>>
    //fun getCerereById(@Path(value = "id") id: String): Single<List<Cerere>>
    fun updateCerereById(id: String, cerere: Cerere): Single<Cerere>
    fun enterNewCerere(request: Cerere): Single<Cerere>
}