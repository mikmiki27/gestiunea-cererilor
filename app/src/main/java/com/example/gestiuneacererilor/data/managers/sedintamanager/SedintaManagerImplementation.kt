package com.example.gestiuneacererilor.data.managers.sedintamanager

import com.example.gestiuneacererilor.data.restmanager.SedintaService
import com.example.gestiuneacererilor.data.restmanager.data.Sedinta
import io.reactivex.Single

class SedintaManagerImplementation private constructor(private val sedintaService: SedintaService) :
    SedintaManager {
    companion object {
        private var instance: SedintaManager? = null

        fun getInstance(sedintaService: SedintaService): SedintaManager {
            if (instance == null) {
                instance = SedintaManagerImplementation(sedintaService)
            }
            return instance!!
        }
    }

    override fun getAllSedinta(): Single<List<Sedinta>> {
        return sedintaService.getAllSedinte()
    }

    override fun getSedintaByStudentEmail(email: String): Single<List<Sedinta>> {
        return sedintaService.getSedintaByStudentEmail(email)
    }

    override fun getSedintaByProfesorEmail(email: String): Single<List<Sedinta>> {
        return sedintaService.getSedintaByProfesorEmail(email)
    }

    override fun updateSedintaById(id: String, sedinta: Sedinta): Single<Sedinta> {
        return sedintaService.updateSedintaById(id, sedinta)
    }

    override fun enterNewSedinta(request: Sedinta): Single<Sedinta> {
        return sedintaService.enterNewSedinta(request)
    }
}