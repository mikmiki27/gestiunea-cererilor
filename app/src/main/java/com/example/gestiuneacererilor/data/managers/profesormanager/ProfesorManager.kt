package com.example.gestiuneacererilor.data.managers.profesormanager

import com.example.gestiuneacererilor.data.restmanager.data.*
import io.reactivex.Single

interface ProfesorManager {
    fun getAllProfessors(): Single<GetAllProfessorsResponse>
    fun enterNewProfesor(request: NewProfesorRequestBody): Single<NewProfesorRequestBody>
    fun getProfesorByEmail(email: String): Single<List<NewProfesorRequestBody>>
    //fun getProfesorById(id: String): Single<List<NewProfesorRequestBody>>
    fun updateProfesorById(id: String, profesor: Profesor): Single<NewProfesorRequestBody>
}