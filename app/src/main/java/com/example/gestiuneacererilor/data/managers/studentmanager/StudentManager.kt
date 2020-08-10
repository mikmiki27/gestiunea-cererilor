package com.example.gestiuneacererilor.data.managers.studentmanager

import com.example.gestiuneacererilor.data.restmanager.data.GetAllStudentsResponse
import com.example.gestiuneacererilor.data.restmanager.data.NewStudentRequestBody
import com.example.gestiuneacererilor.data.restmanager.data.Student
import io.reactivex.Single

interface StudentManager {
    fun getAllStudents(): Single<GetAllStudentsResponse>
    fun enterNewStudent(request: NewStudentRequestBody): Single<NewStudentRequestBody>
    fun getStudentByEmail(email: String): Single<List<NewStudentRequestBody>>
    //fun getStudentById(id: String): Single<List<NewStudentRequestBody>>
    fun updateStudentById(id: String, student: Student): Single<NewStudentRequestBody>
}