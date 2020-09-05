package com.example.gestiuneacererilor.ui.cereri.listaprofesori

import android.content.Context
import android.util.Log
import com.example.gestiuneacererilor.data.managers.cereremanager.CerereManager
import com.example.gestiuneacererilor.data.managers.profesormanager.ProfesorManager
import com.example.gestiuneacererilor.data.managers.studentmanager.StudentManager
import com.example.gestiuneacererilor.ui.base.BasePresenter
import com.example.gestiuneacererilor.utils.getStudentProfesorCoordonatorEmail
import com.example.gestiuneacererilor.utils.getStudentProfesorCoordonatorFullName
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

    override fun getAllProfesoriDisponibili() {
        view?.showProgress()
        subscription.add(
            profesorManager.getAllProfessors()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    when {
                        it.isNullOrEmpty() -> {
                            view?.showPlaceholderForProfessors()
                        }
                        else -> {
                            if (getStudentProfesorCoordonatorEmail(context).isEmpty()) {
                                view?.showListaProfesoriDisponibili(it)
                            } else {
                                view?.showPlaceHolderForAlreadyGotProf(
                                    getStudentProfesorCoordonatorFullName(context)
                                )
                            }
                        }
                    }
                    view?.hideProgress()
                }, {
                    Log.d("problem", "could not get all professors")
                    view?.showPlaceholderForProfessorNetwork()
                    view?.hideProgress()
                })
        )
    }

    override fun getAllCereriForCurrentStudent() {
        subscription.add(
            cerereManager.getCerereByStudentEmail(FirebaseAuth.getInstance().currentUser?.email.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view?.setListCereriStudentCurent(it)
                }, {
                    Log.d("problem", "could not get all professors")
                })
        )
    }
}