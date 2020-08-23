package com.example.gestiuneacererilor.data.managers.profesormanager

import com.example.gestiuneacererilor.data.restmanager.data.*
import io.reactivex.Single

interface ProfesorManager {
    fun getAllProfessors(): Single<List<Professor>>
    fun enterNewProfesor(request: Professor): Single<Professor>
    fun getProfesorByEmail(email: String): Single<List<Professor>>
    //fun getProfesorById(id: String): Single<List<NewProfesorRequestBody>>
    fun updateProfesorById(id: String, profesor: Professor): Single<Professor>
}