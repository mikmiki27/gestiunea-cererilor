package com.example.gestiuneacererilor.ui.cereri

import android.app.Activity
import android.content.Context
import android.util.Log
import com.example.gestiuneacererilor.data.managers.cereremanager.CerereManager
import com.example.gestiuneacererilor.data.managers.profesormanager.ProfesorManager
import com.example.gestiuneacererilor.data.managers.studentmanager.StudentManager
import com.example.gestiuneacererilor.data.restmanager.data.Cerere
import com.example.gestiuneacererilor.data.restmanager.data.Student
import com.example.gestiuneacererilor.data.restmanager.data.Professor
import com.example.gestiuneacererilor.ui.base.BasePresenter
import com.example.gestiuneacererilor.utils.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

class CereriPresenter(
    view: CereriMvp.View,
    val context: Context,
    private val cerereManager: CerereManager,
    private val profesorManager: ProfesorManager,
    private val studentManger: StudentManager
) : BasePresenter<CereriMvp.View>(view), CereriMvp.Presenter {

    lateinit var profesor: Professor

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
                                activity,
                                SharedPrefUtil.CURRENT_FIREBASE_DISPLAY_NAME,
                                it[0].prenume + " " + it[0].nume
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

/*    override fun getAllCerereForStudent() {
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
    }*/

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
        lateinit var student: Student
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

        val profesorUpdate = Professor(
            id = profesor.id,
            nume = profesor.nume,
            prenume = profesor.prenume,
            email = profesor.email,
            cerinte_suplimentare_licenta = profesor.cerinte_suplimentare_licenta,
            cerinte_suplimentare_disertatie = profesor.cerinte_suplimentare_disertatie,
            facultate = profesor.facultate,
            nr_studenti_echipa_licenta = profesor.nr_studenti_echipa_licenta,
            nr_studenti_echipa_disertatie = profesor.nr_studenti_echipa_disertatie,
            studenti_licenta_acceptati = profesor.studenti_licenta_acceptati,
            studenti_disertatie_acceptati = profesor.studenti_disertatie_acceptati
        )

        if (cerereSelectata.tip_cerere.toLowerCase(Locale.getDefault()) == Constants.TipCerere.LICENTA.name.toLowerCase(
                Locale.getDefault()
            )
        ) {
            profesorUpdate.nr_studenti_echipa_licenta =
                (getProfesorLicentaEchipa(context).toInt() + 1).toString()
            profesorUpdate.studenti_licenta_acceptati =
                profesorUpdate.studenti_licenta_acceptati + cerereSelectata.student_solicitant + ", "
        } else if (cerereSelectata.tip_cerere.toLowerCase(Locale.getDefault()) == Constants.TipCerere.DISERTATIE.name.toLowerCase(
                Locale.getDefault()
            )
        ) {
            profesorUpdate.nr_studenti_echipa_disertatie =
                (getProfesorMasterEchipa(context).toInt() + 1).toString()
            profesorUpdate.studenti_disertatie_acceptati =
                profesorUpdate.studenti_disertatie_acceptati + cerereSelectata.student_solicitant + ", "
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

    override fun getStudentByEmail(email: String) {
        view?.showProgress()
        subscription.add(
            studentManger.getStudentByEmail(email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    SharedPrefUtil.addKeyValue(
                        context,
                        SharedPrefUtil.CURRENT_USER_PROFESOR_COORDONATOR,
                        it[0].profesor_coordonator
                            ?: ""
                    )
                }, {
                    Log.d("problem", "could not update student")
                    view?.showPlaceholderForNetwork()
                })
        )
    }
}