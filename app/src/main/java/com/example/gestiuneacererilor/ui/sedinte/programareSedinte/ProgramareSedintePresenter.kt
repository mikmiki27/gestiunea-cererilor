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
import com.example.gestiuneacererilor.utils.getCurrentUserEmail
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
            profesorManager.getProfesorByEmail(getCurrentUserEmail(context))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    when {
                        it.isNullOrEmpty() -> {
                            //view?.hideViewsForTeams()
                        }
                        else -> {
                            profesor = it[0]

                            SharedPrefUtil.addKeyValue(
                                activity,
                                SharedPrefUtil.PROFESOR_COORDONATOR_ID,
                                it[0].id
                                    ?: ""
                            )
                            SharedPrefUtil.addKeyValue(
                                activity,
                                SharedPrefUtil.PROFESOR_COORDONATOR_DISPLAY_NAME,
                                it[0].prenume + " " + it[0].nume
                                    ?: ""
                            )
                        }
                    }
                }, {
                    Log.d("problem", "could not get profesor by email")
                })
        )
    }
}