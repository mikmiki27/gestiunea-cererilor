package com.example.gestiuneacererilor.ui.cereri

import android.app.Activity
import android.content.Context
import android.util.Log
import com.example.gestiuneacererilor.data.managers.cereremanager.CerereManager
import com.example.gestiuneacererilor.data.managers.profesormanager.ProfesorManager
import com.example.gestiuneacererilor.data.managers.studentmanager.StudentManager
import com.example.gestiuneacererilor.data.restmanager.data.Cerere
import com.example.gestiuneacererilor.data.restmanager.data.Profesor
import com.example.gestiuneacererilor.data.restmanager.data.Student
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
        view?.showProgress()
        subscription.add(
            cerereManager.updateCerereById(cerereSelectata.id, cerereSelectata)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    //nothing
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

        subscription.add(
            cerereManager.updateCerereById(cerereSelectata.id, cerereSelectata)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    //nothing
                }, {
                    Log.d("problem", "could not update cerere")
                    view?.showPlaceholderForNetwork()
                })
        )

        subscription.add(
            studentManger.updateStudentById(
                cerereSelectata.id_student,
                Student(cerereSelectata.email_profesor_solicitat)
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

        subscription.add(
            profesorManager.updateProfesorById(
                cerereSelectata.id_profesor, Profesor(
                    (getProfesorLicentaEchipa(context).toInt() + 1).toString(),
                    (getProfesorMasterEchipa(context).toInt() + 1).toString()
                )
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
    }
}