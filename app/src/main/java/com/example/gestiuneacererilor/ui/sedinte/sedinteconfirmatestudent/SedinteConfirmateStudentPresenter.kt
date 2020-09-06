package com.example.gestiuneacererilor.ui.sedinte.sedinteconfirmatestudent

import android.app.Activity
import android.content.Context
import android.util.Log
import com.example.gestiuneacererilor.data.managers.profesormanager.ProfesorManager
import com.example.gestiuneacererilor.data.managers.sedintamanager.SedintaManager
import com.example.gestiuneacererilor.data.managers.studentmanager.StudentManager
import com.example.gestiuneacererilor.data.restmanager.data.Sedinta
import com.example.gestiuneacererilor.ui.base.BasePresenter
import com.example.gestiuneacererilor.utils.*
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SedinteConfirmateStudentPresenter(
    view: SedinteConfirmateStudentMvp.View,
    val context: Context,
    private val sedintaManager: SedintaManager,
    private val profesorManager: ProfesorManager,
    private val studentManger: StudentManager
) : BasePresenter<SedinteConfirmateStudentMvp.View>(view), SedinteConfirmateStudentMvp.Presenter {


    override fun getAllSedinteConfirmateStudent() {
        view?.showProgress()
        subscription.add(
            sedintaManager.getAllSedinta()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    when {
                        it.isNullOrEmpty() -> {
                            view?.showPlaceholderForSedinteConfirmateStudent()
                        }
                        else -> {
                            view?.showListaSedinteConfirmateStudent(filterLista(it))
                        }
                    }
                    view?.hideProgress()
                }, {
                    Log.d("problem", "could not get all sedinte")
                    view?.showPlaceholderForSedinteConfirmateNetworkStudent()
                    view?.hideProgress()
                })
        )
    }

    private fun filterLista(list: List<Sedinta>): List<Sedinta> {
        val listaNoua = arrayListOf<Sedinta>()
        for (sedinta in list) {
            if (sedinta.email_student_solicitant == FirebaseAuth.getInstance().currentUser?.email.toString()) {
                if (getCurrentLicentaAcceptati(context).contains(sedinta.student_solicitant) || getCurrentDisertatieAcceptati(context).contains(sedinta.student_solicitant)) {
                        listaNoua.add(sedinta)
                }
            }
        }
        return listaNoua
    }

    override fun getCurrentStudentByEmail(activity: Activity) {
        subscription.add(
            studentManger.getStudentByEmail(FirebaseAuth.getInstance().currentUser?.email.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.isNotEmpty()) {
                        SharedPrefUtil.addKeyValue(
                            activity, SharedPrefUtil.STUDENT_CURRENT_ID, it[0].id
                                ?: ""
                        )
                        SharedPrefUtil.addKeyValue(
                            activity, SharedPrefUtil.STUDENT_CURRENT_NUME, it[0].nume
                                ?: ""
                        )
                        SharedPrefUtil.addKeyValue(
                            activity, SharedPrefUtil.STUDENT_CURRENT_PRENUME, it[0].prenume
                                ?: ""
                        )
                        SharedPrefUtil.addKeyValue(
                            activity,
                            SharedPrefUtil.STUDENT_FULL_NAME,
                            it[0].prenume + " " + it[0].nume
                                ?: ""
                        )
                        SharedPrefUtil.addKeyValue(
                            activity, SharedPrefUtil.STUDENT_CURRENT_EMAIL, it[0].email
                                ?: ""
                        )
                        SharedPrefUtil.addKeyValue(
                            activity, SharedPrefUtil.STUDENT_CURRENT_FACULTATE, it[0].facultate
                                ?: ""
                        )
                        SharedPrefUtil.addKeyValue(
                            activity, SharedPrefUtil.STUDENT_CICLU, it[0].ciclu
                                ?: ""
                        )
                        SharedPrefUtil.addKeyValue(
                            activity, SharedPrefUtil.STUDENT_AN, it[0].an
                                ?: ""
                        )
                        SharedPrefUtil.addKeyValue(
                            activity,
                            SharedPrefUtil.STUDENT_PROFESOR_COORDONATOR_EMAIL,
                            it[0].profesor_coordonator
                                ?: ""
                        )
                        SharedPrefUtil.addKeyValue(
                            activity,
                            SharedPrefUtil.STUDENT_PROFESOR_COORDONATOR_FULL_NAME,
                            it[0].profesor_coordonator_full_name
                                ?: ""
                        )
                        SharedPrefUtil.addKeyValue(
                            activity,
                            SharedPrefUtil.STUDENT_PROFESOR_COORDONATOR_ID,
                            it[0].id_profesor_coordonator
                                ?: ""
                        )
                        SharedPrefUtil.addKeyValue(
                            activity, SharedPrefUtil.STUDENT_TITLU_LUCRARE, it[0].titlu_lucrare
                                ?: ""
                        )
                    }
                }, {
                    Log.d("problem", "could not get student by email")
                })
        )
    }
}