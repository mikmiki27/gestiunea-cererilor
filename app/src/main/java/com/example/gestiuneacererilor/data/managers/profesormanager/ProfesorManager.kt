package com.example.gestiuneacererilor.data.managers.profesormanager

import com.example.gestiuneacererilor.data.restmanager.data.GetAllProfessorsResponse
import com.example.gestiuneacererilor.data.restmanager.data.GetAllStudentsResponse
import com.example.gestiuneacererilor.data.restmanager.data.NewProfesorRequestBody
import com.example.gestiuneacererilor.data.restmanager.data.NewStudentRequestBody
import io.reactivex.Single

interface ProfesorManager {
    fun getAllProfessors(): Single<GetAllProfessorsResponse>
    fun enterNewProfesor(request: NewProfesorRequestBody): Single<NewProfesorRequestBody>
    fun getProfesorByEmail(email: String): Single<List<NewProfesorRequestBody>>
}