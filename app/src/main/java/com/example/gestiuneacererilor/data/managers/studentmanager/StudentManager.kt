package com.example.gestiuneacererilor.data.managers.studentmanager

import com.example.gestiuneacererilor.data.restmanager.data.GetAllStudentsResponse
import com.example.gestiuneacererilor.data.restmanager.data.NewStudentRequestBody
import io.reactivex.Single

interface StudentManager {
    fun getAllStudents(): Single<GetAllStudentsResponse>
    fun enterNewStudent(request: NewStudentRequestBody): Single<NewStudentRequestBody>
}