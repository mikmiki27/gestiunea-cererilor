package com.example.gestiuneacererilor.data.managers.profesormanager

import com.example.gestiuneacererilor.data.restmanager.ProfesorService
import com.example.gestiuneacererilor.data.restmanager.StudentService
import com.example.gestiuneacererilor.data.restmanager.data.GetAllProfessorsResponse
import com.example.gestiuneacererilor.data.restmanager.data.GetAllStudentsResponse
import com.example.gestiuneacererilor.data.restmanager.data.NewProfesorRequestBody
import com.example.gestiuneacererilor.data.restmanager.data.NewStudentRequestBody
import io.reactivex.Single
import retrofit2.http.Query

class ProfesorManagerImplementation  private constructor(private val profesorService: ProfesorService) : ProfesorManager{
    companion object {
        private var instance: ProfesorManager? = null

        fun getInstance(profesorService: ProfesorService): ProfesorManager {
            if (instance == null) {
                instance = ProfesorManagerImplementation(profesorService)
            }
            return instance!!
        }
    }

    override fun getAllProfessors(): Single<GetAllProfessorsResponse> {
        return profesorService.getAllProfessors()
    }

    override fun enterNewProfesor(request: NewProfesorRequestBody): Single<NewProfesorRequestBody> {
        return profesorService.enterNewProfesor(request)
    }

    override fun getProfesorByEmail(email: String): Single<List<NewProfesorRequestBody>> {
        return profesorService.getProfesorByEmail(email)
    }
}