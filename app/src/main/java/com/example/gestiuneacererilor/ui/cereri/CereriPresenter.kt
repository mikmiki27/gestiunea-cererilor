package com.example.gestiuneacererilor.ui.cereri

import android.app.Activity
import android.content.Context
import android.os.SystemClock.sleep
import android.util.Log
import androidx.annotation.MainThread
import com.example.gestiuneacererilor.data.managers.cereremanager.CerereManager
import com.example.gestiuneacererilor.data.managers.profesormanager.ProfesorManager
import com.example.gestiuneacererilor.data.managers.studentmanager.StudentManager
import com.example.gestiuneacererilor.data.restmanager.data.*
import com.example.gestiuneacererilor.ui.base.BasePresenter
import com.example.gestiuneacererilor.utils.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.delay
import java.lang.Thread.sleep

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
                            SharedPrefUtil.addKeyValue(
                                activity, SharedPrefUtil.CURRENT_FIREBASE_USER_EMAIL, it[0].email
                                    ?: ""
                            )
                            SharedPrefUtil.addKeyValue(
                                activity, SharedPrefUtil.CURRENT_FIREBASE_USER_NAME, it[0].prenume + " " + it[0].nume
                                    ?: ""
                            )
                            SharedPrefUtil.addKeyValue(
                                activity, SharedPrefUtil.CURRENT_USER_NUME, it[0].nume
                                    ?: ""
                            )
                            SharedPrefUtil.addKeyValue(
                                activity, SharedPrefUtil.CURRENT_USER_PRENUME, it[0].prenume
                                    ?: ""
                            )
                            SharedPrefUtil.addKeyValue(
                                activity, SharedPrefUtil.CURRENT_USER_FACULTATE, it[0].facultate
                                    ?: ""
                            )
                            SharedPrefUtil.addKeyValue(
                                activity, SharedPrefUtil.CURRENT_USER_ID, it[0].id
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
                        }
                        else -> view?.showCereriDisponibileForProfesor(it)
                    }
                    view?.hideProgress()
                }, {
                    Log.d("problem", "could not get all cerere")
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
                        it.isNullOrEmpty() -> view?.showPlaceholderForEmptylist()
                        //  else -> view?.showCereriDisponibileForProfesor(it)
                    }
                    view?.hideProgress()
                }, {
                    Log.d("problem", "could not get all cerere")
                    view?.showPlaceholderForNetwork()
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
        lateinit var student: NewStudentRequestBody
        var studentUpdate: Student? = null
        subscription.add(
            studentManger.getStudentByEmail(
                cerereSelectata.email_student_solicitat
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    student = it[0]
                    studentUpdate = Student(
                        id = student.id,
                        nume = student.nume,
                        prenume = student.prenume,
                        email = student.email,
                        facultate = student.facultate,
                        an = student.an,
                        ciclu = student.ciclu,
                        profesor_coordonator = student.profesor_coordonator,
                        titlu_lucrare = student.titlu_lucrare
                    )
                    studentUpdate?.profesor_coordonator = profesor.email

                    subscription.add(
                        studentManger.updateStudentById(
                            cerereSelectata.id_student,
                            studentUpdate!!
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

                }, {
                    Log.d("problem", "could not update student")
                    view?.showPlaceholderForNetwork()
                })
        )

        val profesorUpdate = Profesor(
            profesor.id,
            profesor.nume,
            profesor.prenume,
            profesor.email,
            profesor.cerinte_suplimentare_licenta,
            profesor.cerinte_suplimentare_disertatie,
            profesor.facultate,
            profesor.nr_studenti_echipa_licenta,
            profesor.nr_studenti_echipa_disertatie,
            profesor.studenti_licenta_acceptati,
            profesor.studenti_disertatie_acceptati
        )

        if (cerereSelectata.tip_cerere == Constants.TipCerere.LICENTA.name) {
            profesorUpdate.nr_studenti_echipa_licenta =
                (getProfesorLicentaEchipa(context).toInt() + 1).toString()
            profesorUpdate.studenti_licenta_acceptati = profesorUpdate.studenti_licenta_acceptati + cerereSelectata.student_solicitant + ", "
        } else if (cerereSelectata.tip_cerere == Constants.TipCerere.DISERTATIE.name) {
            profesorUpdate.nr_studenti_echipa_disertatie =
                (getProfesorMasterEchipa(context).toInt() + 1).toString()
            profesorUpdate.studenti_disertatie_acceptati = profesorUpdate.studenti_disertatie_acceptati + cerereSelectata.student_solicitant + ", "
        }
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
                    view?.hideProgress()
                    view?.goToEchipe()
                }, {
                    Log.d("problem", "could not update cerere")
                    view?.showPlaceholderForNetwork()
                })
        )
    }
}