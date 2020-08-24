package com.example.gestiuneacererilor.ui.cereri.cereriStudent

import android.content.Context
import android.util.Log
import com.example.gestiuneacererilor.data.managers.cereremanager.CerereManager
import com.example.gestiuneacererilor.data.restmanager.data.Cerere
import com.example.gestiuneacererilor.ui.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CereriStudentPresenter(
    view: CereriStudentMvp.View,
    val context: Context,
    private val cerereManager: CerereManager
) : BasePresenter<CereriStudentMvp.View>(view), CereriStudentMvp.Presenter {

    override fun getAllCerere() {
        view?.showProgress()
        subscription.add(
            cerereManager.getAllCerere()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    when {
                        it.isNullOrEmpty() -> {
                            view?.showPlaceholderForNoRequests()
                        }
                        else -> view?.showCererileMele(it)
                    }
                    view?.hideProgress()
                }, {
                    Log.d("problem", "could not get all cerere")
                    view?.hideProgress()
                })
        )
    }

    override fun anulareCerere(cerere: Cerere) {
        view?.showProgress()
        subscription.add(
            cerereManager.updateCerereById(cerere.id, cerere)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view?.goToCereriFromCereri()
                    view?.hideProgress()
                }, {
                    Log.d("problem", "could not update cerere")
                    view?.hideProgress()
                })
        )
    }
}