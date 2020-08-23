package com.example.gestiuneacererilor.data.managers.profesormanager

import com.example.gestiuneacererilor.data.restmanager.ProfesorService
import com.example.gestiuneacererilor.data.restmanager.data.*
import io.reactivex.Single

class ProfesorManagerImplementation private constructor(private val profesorService: ProfesorService) : ProfesorManager{
    companion object {
        private var instance: ProfesorManager? = null

        fun getInstance(profesorService: ProfesorService): ProfesorManager {
            if (instance == null) {
                instance = ProfesorManagerImplementation(profesorService)
            }
            return instance!!
        }
    }

    override fun getAllProfessors(): Single<List<Professor>> {
        return profesorService.getAllProfessors()
    }

    override fun enterNewProfesor(request: Professor): Single<Professor> {
        return profesorService.enterNewProfesor(request)
    }

    override fun getProfesorByEmail(email: String): Single<List<Professor>> {
        return profesorService.getProfesorByEmail(email)
    }

    override fun updateProfesorById(id: String, profesor: Professor): Single<Professor> {
        return profesorService.updateProfesorById(id, profesor)
    }
}