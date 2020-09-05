package com.example.gestiuneacererilor.ui.sedinte.programareSedinte

import android.app.Activity
import android.content.Context
import android.util.Log
import com.example.gestiuneacererilor.data.managers.profesormanager.ProfesorManager
import com.example.gestiuneacererilor.data.managers.sedintamanager.SedintaManager
import com.example.gestiuneacererilor.data.managers.studentmanager.StudentManager
import com.example.gestiuneacererilor.data.restmanager.data.Professor
import com.example.gestiuneacererilor.data.restmanager.data.Sedinta
import com.example.gestiuneacererilor.ui.base.BasePresenter
import com.example.gestiuneacererilor.utils.SharedPrefUtil
import com.example.gestiuneacererilor.utils.getStudentProfesorCoordonatorEmail
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ProgramareSedintePresenter(
    view: ProgramareSedinteMvp.View,
    val context: Context,
    private val sedintaManager: SedintaManager,
    private val profesorManager: ProfesorManager,
    private val studentManger: StudentManager
) : BasePresenter<ProgramareSedinteMvp.View>(view), ProgramareSedinteMvp.Presenter {

    lateinit var profesor: Professor

    override fun enterNewSedinta(sedinta: Sedinta) {
        view?.showProgress()
        subscription.add(
            sedintaManager.enterNewSedinta(sedinta)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view?.goToCerileConfirmateAleStudentului()
                    view?.hideProgress()
                }, {
                    Log.d("problem", "could not post new sedinta")

                    view?.hideProgress()
                })
        )
    }

    override fun getProfesorCoordonatorByEmail(emailProfCoord: String, activity: Activity) {
        view?.showProgress()
        subscription.add(
            profesorManager.getProfesorByEmail(getStudentProfesorCoordonatorEmail(context))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    when {
                        it.isNullOrEmpty() -> {
                            //view?.hideViewsForTeams()
                        }
                        else -> {
                            SharedPrefUtil.addKeyValue(
                                activity, SharedPrefUtil.PROFESOR_CURRENT_ID, it[0].id
                                    ?: ""
                            )
                            SharedPrefUtil.addKeyValue(
                                activity, SharedPrefUtil.PROFESOR_CURRENT_NUME, it[0].nume
                                    ?: ""
                            )
                            SharedPrefUtil.addKeyValue(
                                activity, SharedPrefUtil.PROFESOR_CURRENT_PRENUME, it[0].prenume
                                    ?: ""
                            )
                            SharedPrefUtil.addKeyValue(
                                activity, SharedPrefUtil.PROFESOR_FULL_NAME, it[0].prenume + " " + it[0].nume
                                    ?: ""
                            )
                            SharedPrefUtil.addKeyValue(
                                activity, SharedPrefUtil.PROFESOR_CURRENT_EMAIL, it[0].email
                                    ?: ""
                            )
                            SharedPrefUtil.addKeyValue(
                                activity, SharedPrefUtil.PROFESOR_CERINTE_LICENTA, it[0].cerinte_suplimentare_licenta
                                    ?: ""
                            )
                            SharedPrefUtil.addKeyValue(
                                activity, SharedPrefUtil.PROFESOR_CERINTE_MASTER, it[0].cerinte_suplimentare_disertatie
                                    ?: ""
                            )
                            SharedPrefUtil.addKeyValue(
                                activity, SharedPrefUtil.PROFESOR_CURRENT_FACULTATE, it[0].facultate
                                    ?: ""
                            )
                            SharedPrefUtil.addKeyValue(
                                activity, SharedPrefUtil.PROFESOR_ECHIPA_LICENTA, it[0].nr_studenti_echipa_licenta
                                    ?: ""
                            )
                            SharedPrefUtil.addKeyValue(
                                activity, SharedPrefUtil.PROFESOR_ECHIPA_MASTER, it[0].nr_studenti_echipa_disertatie
                                    ?: ""
                            )
                            SharedPrefUtil.addKeyValue(
                                activity, SharedPrefUtil.PROFESOR_LICENTA_ACCEPTATI, it[0].studenti_licenta_acceptati
                                    ?: ""
                            )
                            SharedPrefUtil.addKeyValue(
                                activity, SharedPrefUtil.PROFESOR_DISERTATIE_ACCEPTATI, it[0].studenti_disertatie_acceptati
                                    ?: ""
                            )
                        }
                    }
                    view?.hideProgress()
                }, {
                    Log.d("problem", "could not get profesor by email")
                    view?.hideProgress()
                })
        )
    }
}