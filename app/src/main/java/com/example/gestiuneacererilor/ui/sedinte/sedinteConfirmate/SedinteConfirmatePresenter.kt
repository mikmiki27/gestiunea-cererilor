package com.example.gestiuneacererilor.ui.sedinte.sedinteConfirmate

import android.content.Context
import android.util.Log
import com.example.gestiuneacererilor.data.managers.profesormanager.ProfesorManager
import com.example.gestiuneacererilor.data.managers.sedintamanager.SedintaManager
import com.example.gestiuneacererilor.data.managers.studentmanager.StudentManager
import com.example.gestiuneacererilor.data.restmanager.data.Sedinta
import com.example.gestiuneacererilor.ui.base.BasePresenter
import com.example.gestiuneacererilor.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SedinteConfirmatePresenter(
    view: SedinteConfirmateMvp.View,
    val context: Context,
    private val sedintaManager: SedintaManager,
    private val profesorManager: ProfesorManager,
    private val studentManger: StudentManager
) : BasePresenter<SedinteConfirmateMvp.View>(view), SedinteConfirmateMvp.Presenter {


    override fun getAllSedinteConfirmate() {
        view?.showProgress()
        subscription.add(
            sedintaManager.getAllSedinta()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if(it.isNotEmpty()) {
                    when {
                        it.isNullOrEmpty() -> {
                            view?.showPlaceholderForSedinteConfirmate()
                        }
                        else -> {
                            if (filterLista(it).isEmpty()) {
                                view?.showPlaceholderForSedinteConfirmate()
                            } else {
                                view?.showListaSedinteConfirmate(filterLista(it))
                            }
                        }
                    } }
                    view?.hideProgress()
                }, {
                    Log.d("problem", "could not get all sedinte")
                    view?.showPlaceholderForSedinteConfirmateNetwork()
                    view?.hideProgress()
                })
        )
    }

    private fun filterLista(list: List<Sedinta>): List<Sedinta> {
        val listaNoua = arrayListOf<Sedinta>()
         for (sedinta in list) {
             if (sedinta.email_profesor_solicitat == FirebaseAuth.getInstance().currentUser?.email.toString() &&
                     sedinta.status == Constants.StatusSedinta.ACCEPTATA.name) {
                 listaNoua.add(sedinta)
             }
         }
        return listaNoua
    }
}