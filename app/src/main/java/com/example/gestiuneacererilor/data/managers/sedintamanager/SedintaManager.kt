package com.example.gestiuneacererilor.data.managers.sedintamanager

import com.example.gestiuneacererilor.data.restmanager.data.Sedinta
import io.reactivex.Single

interface SedintaManager {
    fun getAllSedinta(): Single<List<Sedinta>>
    fun getSedintaByStudentEmail(email: String): Single<List<Sedinta>>
   // fun getSedintaByProfesorEmail(email: String): Single<List<Sedinta>>
    //fun getSedintaById(@Path(value = "id") id: String): Single<List<Sedinta>>
    fun updateSedintaById(id: String, sedinta: Sedinta): Single<Sedinta>
    fun enterNewSedinta(request: Sedinta): Single<Sedinta>
}