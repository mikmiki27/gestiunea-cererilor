package com.example.gestiuneacererilor.ui.sedinte.sedintesolicitate;

import android.content.Context
import android.util.Log
import com.example.gestiuneacererilor.data.managers.profesormanager.ProfesorManager
import com.example.gestiuneacererilor.data.managers.sedintamanager.SedintaManager
import com.example.gestiuneacererilor.data.managers.studentmanager.StudentManager
import com.example.gestiuneacererilor.data.restmanager.data.Professor
import com.example.gestiuneacererilor.data.restmanager.data.Sedinta
import com.example.gestiuneacererilor.ui.base.BasePresenter
import com.example.gestiuneacererilor.utils.getCurrentDisertatieAcceptati
import com.example.gestiuneacererilor.utils.getCurrentLicentaAcceptati
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SedinteSolicitatePresenter(
    view: SedinteSolicitateMvp.View,
    val context: Context,
    private val sedintaManager: SedintaManager,
    private val profesorManager: ProfesorManager,
    private val studentManger: StudentManager
) : BasePresenter<SedinteSolicitateMvp.View>(view), SedinteSolicitateMvp.Presenter {

    override fun getAllSedinte() {
        view?.showProgress()
        subscription.add(
            sedintaManager.getAllSedinta()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    when {
                        it.isNullOrEmpty() -> {
                            view?.showPlaceholderForSedinte()
                        }
                        else -> {
                            view?.showListaSedinte(filterLista(it))
                        }
                    }
                    view?.hideProgress()
                }, {
                    Log.d("problem", "could not get all sedinte")
                       view?.showPlaceholderForSedinteNetwork()
                    view?.hideProgress()
                })
        )
    }

    private fun filterLista(list: List<Sedinta>): List<Sedinta> {
        val listaNoua = arrayListOf<Sedinta>()
        for (sedinta in list) {
            if (getCurrentLicentaAcceptati(context).contains(sedinta.nume_student) || getCurrentDisertatieAcceptati(
                    context
                ).contains(sedinta.nume_student)
            ) {
                listaNoua.add(sedinta)
            }
        }
        return listaNoua
    }

    override fun updateStatusSedintaToAcceptat(sedinta: Sedinta) {
        subscription.add(
            sedintaManager.updateSedintaById(sedinta.id, sedinta)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    //nothing
                     view?.goToSedinteConfirmate()
                    view?.hideProgress()
                }, {
                    Log.d("problem", "could not update cerere")
                    view?.showPlaceholderForSedinteNetwork()
                    view?.hideProgress()
                })
        )
    }

    override fun updateStatusSedintaToRespins(sedinta: Sedinta) {
        subscription.add(
            sedintaManager.updateSedintaById(sedinta.id, sedinta)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    //nothing
                    view?.goToSedinteConfirmate()
                    view?.hideProgress()
                }, {
                    Log.d("problem", "could not update cerere")
                    view?.showPlaceholderForSedinteNetwork()
                    view?.hideProgress()
                })
        )

    }
}
