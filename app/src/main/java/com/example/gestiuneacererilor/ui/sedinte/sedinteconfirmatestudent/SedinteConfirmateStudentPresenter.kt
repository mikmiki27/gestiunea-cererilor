package com.example.gestiuneacererilor.ui.sedinte.sedinteconfirmatestudent

import android.content.Context
import android.util.Log
import com.example.gestiuneacererilor.data.managers.profesormanager.ProfesorManager
import com.example.gestiuneacererilor.data.managers.sedintamanager.SedintaManager
import com.example.gestiuneacererilor.data.managers.studentmanager.StudentManager
import com.example.gestiuneacererilor.data.restmanager.data.Sedinta
import com.example.gestiuneacererilor.ui.base.BasePresenter
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
        //todo to filter this list confirmate profesor
        /* for (sedinta in list) {
             if (getCurrentLicentaAcceptati(context).contains(sedinta.nume_student) || getCurrentDisertatieAcceptati(
                     context
                 ).contains(sedinta.nume_student)
             ) {
                 listaNoua.add(sedinta)
             }
         }*/
        return listaNoua
    }
}