package com.example.gestiuneacererilor.ui.cereri.listaprofesori

import android.app.Activity
import android.content.Context
import android.util.Log
import com.example.gestiuneacererilor.data.managers.cereremanager.CerereManager
import com.example.gestiuneacererilor.data.managers.profesormanager.ProfesorManager
import com.example.gestiuneacererilor.data.managers.studentmanager.StudentManager
import com.example.gestiuneacererilor.ui.base.BasePresenter
import com.example.gestiuneacererilor.utils.SharedPrefUtil
import com.example.gestiuneacererilor.utils.getCurrentStudentProfesorCoordonator
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ListaProfesoriPresenter(
    view: ListaProfesoriMvp.View,
    val context: Context,
    private val cerereManager: CerereManager,
    private val profesorManager: ProfesorManager,
    private val studentManger: StudentManager
) : BasePresenter<ListaProfesoriMvp.View>(view), ListaProfesoriMvp.Presenter {

    override fun getStudentByEmail(activity: Activity) {
        view?.showProgress()
        subscription.add(
            studentManger.getStudentByEmail(FirebaseAuth.getInstance().currentUser?.email.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    SharedPrefUtil.addKeyValue(
                        activity,
                        SharedPrefUtil.CURRENT_USER_PROFESOR_COORDONATOR,
                        it[0].profesor_coordonator
                            ?: ""
                    )
                    SharedPrefUtil.addKeyValue(
                        activity,
                        SharedPrefUtil.CURRENT_USER_CICLU,
                        it[0].ciclu
                            ?: ""
                    )
                }, {
                    Log.d("problem", "could not get all profesors")
                    view?.showPlaceholderForProfessorNetwork() //todo test this
                })
        )
    }

    override fun getAllProfesoriDisponibili() {
        view?.showProgress()
        subscription.add(
            profesorManager.getAllProfessors()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    when {
                        it.isNullOrEmpty() -> {
                            view?.showPlaceholderForProfessors() //todo test this
                        }
                        else -> {
                            if (getCurrentStudentProfesorCoordonator(context).isEmpty()) {
                                view?.showListaProfesoriDisponibili(it)
                            } else {
                                view?.showPlaceHolderForAlreadyGotProf(
                                    getCurrentStudentProfesorCoordonator(context)
                                )  //todo test this
                            }
                        }
                    }
                }, {
                    Log.d("problem", "could not get all professors")
                    view?.showPlaceholderForProfessorNetwork() //todo test this
                })
        )
    }
}