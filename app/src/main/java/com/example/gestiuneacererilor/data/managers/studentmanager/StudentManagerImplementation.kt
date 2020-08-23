package com.example.gestiuneacererilor.data.managers.studentmanager

import com.example.gestiuneacererilor.data.restmanager.StudentService
import com.example.gestiuneacererilor.data.restmanager.data.Student
import io.reactivex.Single

class StudentManagerImplementation private constructor(private val studentService: StudentService) :
    StudentManager {
    companion object {
        private var instance: StudentManager? = null

        fun getInstance(studentService: StudentService): StudentManager {
            if (instance == null) {
                instance = StudentManagerImplementation(studentService)
            }
            return instance!!
        }
    }

    override fun enterNewStudent(request: Student): Single<Student> {
        return studentService.enterNewStudent(request)
    }

    override fun getStudentByEmail(email: String): Single<List<Student>> {
        return studentService.getStudentByEmail(email)
    }

    override fun updateStudentById(
        id: String,
        student: Student
    ): Single<Student> {
        return studentService.updateStudentById(id, student)
    }
}