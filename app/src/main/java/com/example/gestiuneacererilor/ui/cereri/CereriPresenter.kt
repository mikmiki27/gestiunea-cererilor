package com.example.gestiuneacererilor.ui.cereri

import android.app.Activity
import android.content.Context
import android.util.Log
import com.example.gestiuneacererilor.data.managers.cereremanager.CerereManager
import com.example.gestiuneacererilor.data.managers.profesormanager.ProfesorManager
import com.example.gestiuneacererilor.data.managers.studentmanager.StudentManager
import com.example.gestiuneacererilor.data.restmanager.data.*
import com.example.gestiuneacererilor.ui.base.BasePresenter
import com.example.gestiuneacererilor.utils.SharedPrefUtil
import com.example.gestiuneacererilor.utils.getCurrentUserEmail
import com.example.gestiuneacererilor.utils.getProfesorLicentaEchipa
import com.example.gestiuneacererilor.utils.getProfesorMasterEchipa
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CereriPresenter(
    view: CereriMvp.View,
    val context: Context,
    private val cerereManager: CerereManager,
    private val profesorManager: ProfesorManager,
    private val studentManger: StudentManager
) : BasePresenter<CereriMvp.View>(view), CereriMvp.Presenter {

    lateinit var profesor: NewProfesorRequestBody

    override fun getAllCerereForProfesor(activity: Activity) {
        view?.showProgress()
        subscription.add(
            profesorManager.getProfesorByEmail(getCurrentUserEmail(context))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    when {
                        it.isNullOrEmpty() -> {
                            view?.hideViewsForTeams()
                        }
                        else -> {
                            profesor = it[0]
                            view?.showViewsForTeams(it)
                            SharedPrefUtil.addKeyValue(
                                activity,
                                SharedPrefUtil.CURRENT_USER_ECHIPA_LICENTA,
                                it[0].nr_studenti_echipa_licenta
                                    ?: ""
                            )
                            SharedPrefUtil.addKeyValue(
                                activity,
                                SharedPrefUtil.CURRENT_USER_ECHIPA_MASTER,
                                it[0].nr_studenti_echipa_disertatie
                                    ?: ""
                            )
                        }
                    }
                }, {
                    Log.d("problem", "could not get all cerere")
                    view?.hideViewsForTeams()
                    view?.showPlaceholderForNetwork()
                })
        )
        subscription.add(
            cerereManager.getAllCerere()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    when {
                        it.isNullOrEmpty() -> {
                            view?.showPlaceholderForEmptylist()
                            view?.hideViewsForTeams()
                        }
                        else -> view?.showCereriDisponibileForProfesor(it)
                    }
                    view?.hideProgress()
                }, {
                    Log.d("problem", "could not get all cerere")
                    view?.hideViewsForTeams()
                    view?.showPlaceholderForNetwork()
                    view?.hideProgress()
                })
        )
    }

    override fun getAllCerereForStudent() {
        view?.showProgress()
        subscription.add(
            cerereManager.getAllCerere()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    when {
                        it.isNullOrEmpty() -> view?.showPlaceholderForEmptylist() //todo test this
                        //  else -> view?.showCereriDisponibileForProfesor(it)
                    }
                    view?.hideProgress()
                }, {
                    Log.d("problem", "could not get all cerere")
                    view?.showPlaceholderForNetwork() //todo test this
                    view?.hideProgress()
                })
        )
    }

    override fun updateCerereToRespins(cerereSelectata: Cerere) {
        subscription.add(
            cerereManager.updateCerereById(cerereSelectata.id, cerereSelectata)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    //nothing
                    view?.goToCereri()
                    view?.hideProgress()
                }, {
                    Log.d("problem", "could not update cerere")
                    view?.showPlaceholderForNetwork()
                    view?.hideProgress()
                })
        )

    }
    override fun updateCerereToAccepted(cerereSelectata: Cerere) {
        view?.showProgress()

        //todo sa se astepte callurile intre ele. cv de la procesare paralela? sau un zip dela??

        lateinit var student: NewStudentRequestBody
        subscription.add(
            studentManger.getStudentByEmail(
                cerereSelectata.email_student_solicitat
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    student = it[0]
                }, {
                    Log.d("problem", "could not update student")
                    view?.showPlaceholderForNetwork()
                })
        )

        val studentUpdate = Student(
            student.id,
            student.email,
            student.nume,
            student.prenume,
            student.profesor_coordonator,
            student.facultate,
            student.an,
            student.ciclu,
            student.titlu_lucrare!!
        )
        studentUpdate.profesor_coordonator = profesor.email

        subscription.add(
            studentManger.updateStudentById(
                cerereSelectata.id_student,
                studentUpdate
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    //nothing
                }, {
                    Log.d("problem", "could not update student")
                    view?.showPlaceholderForNetwork()
                })
        )

        val profesorUpdate = Profesor(
            profesor.id,
            profesor.email,
            profesor.prenume,
            profesor.facultate,
            profesor.cerinte_suplimentare_licenta!!,
            profesor.cerinte_suplimentare_disertatie!!,
            profesor.nr_studenti_echipa_licenta!!,
            profesor.nr_studenti_echipa_disertatie!!
        )
        profesorUpdate.nr_studenti_echipa_licenta =
            (getProfesorLicentaEchipa(context).toInt() + 1).toString()
        profesorUpdate.nr_studenti_echipa_disertatie =
            (getProfesorMasterEchipa(context).toInt() + 1).toString()

        subscription.add(
            profesorManager.updateProfesorById(
                cerereSelectata.id_profesor, profesorUpdate
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    //nothing
                    view?.hideProgress()
                }, {
                    Log.d("problem", "could not update profesor")
                    view?.showPlaceholderForNetwork()
                    view?.hideProgress()
                })
        )

        subscription.add(
            cerereManager.updateCerereById(cerereSelectata.id, cerereSelectata)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view?.goToEchipe()
                }, {
                    Log.d("problem", "could not update cerere")
                    view?.showPlaceholderForNetwork()
                })
        )
    }
}