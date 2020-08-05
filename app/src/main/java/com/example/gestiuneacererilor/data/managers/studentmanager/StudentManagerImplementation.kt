package com.example.gestiuneacererilor.data.managers.studentmanager

import com.example.gestiuneacererilor.data.restmanager.StudentService
import com.example.gestiuneacererilor.data.restmanager.data.GetAllStudentsResponse
import com.example.gestiuneacererilor.data.restmanager.data.NewStudentRequestBody
import io.reactivex.Single

class StudentManagerImplementation  private constructor(private val studentService: StudentService) : StudentManager{
    companion object {
        private var instance: StudentManager? = null

        fun getInstance(studentService: StudentService): StudentManager {
            if (instance == null) {
                instance = StudentManagerImplementation(studentService)
            }
            return instance!!
        }
    }

    override fun getAllStudents(): Single<GetAllStudentsResponse> {
        return studentService.getAllStudents()
    }

    override fun enterNewStudent(request: NewStudentRequestBody): Single<NewStudentRequestBody> {
        return studentService.enterNewStudent(request)
    }
}