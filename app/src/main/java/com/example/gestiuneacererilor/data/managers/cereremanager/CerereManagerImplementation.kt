package com.example.gestiuneacererilor.data.managers.cereremanager

import com.example.gestiuneacererilor.data.restmanager.CerereService
import com.example.gestiuneacererilor.data.restmanager.data.Cerere
import io.reactivex.Single

class CerereManagerImplementation private constructor(private val cerereService: CerereService) : CerereManager {
    companion object {
        private var instance: CerereManager? = null

        fun getInstance(cerereService: CerereService): CerereManager {
            if (instance == null) {
                instance = CerereManagerImplementation(cerereService)
            }
            return instance!!
        }
    }

    override fun getAllCerere(): Single<List<Cerere>> {
        return cerereService.getAllCerere()
    }

    override fun getCerereByStudentEmail(email: String): Single<List<Cerere>> {
        return cerereService.getCerereByStudentEmail(email)
    }

    override fun getCerereByProfesorEmail(email: String): Single<List<Cerere>> {
        return cerereService.getCerereByProfesorEmail(email)
    }

    override fun updateCerereById(id: String, cerere: Cerere): Single<Cerere> {
        return cerereService.updateCerereById(id, cerere)
    }

    override fun enterNewCerere(request: Cerere): Single<Cerere> {
        return cerereService.enterNewCerere(request)
    }
}