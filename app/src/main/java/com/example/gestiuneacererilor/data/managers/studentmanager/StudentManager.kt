package com.example.gestiuneacererilor.data.managers.studentmanager

import com.example.gestiuneacererilor.data.restmanager.data.Student
import io.reactivex.Single

interface StudentManager {
    fun enterNewStudent(request: Student): Single<Student>
    fun getStudentByEmail(email: String): Single<List<Student>>

    //fun getStudentById(id: String): Single<List<NewStudentRequestBody>>
    fun updateStudentById(id: String, student: Student): Single<Student>
}