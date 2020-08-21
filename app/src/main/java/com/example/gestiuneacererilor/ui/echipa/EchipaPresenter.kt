package com.example.gestiuneacererilor.ui.echipa

import android.content.Context
import android.util.Log
import com.example.gestiuneacererilor.data.managers.profesormanager.ProfesorManager
import com.example.gestiuneacererilor.ui.base.BasePresenter
import com.example.gestiuneacererilor.utils.getCurrentUserEmail
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class EchipaPresenter(
    view: EchipaMvp.View,
    val context: Context,
    private val profesorManager: ProfesorManager
) : BasePresenter<EchipaMvp.View>(view), EchipaMvp.Presenter {

    override fun getProfesorByEmail() {
        view?.showProgress()
        subscription.add(
            profesorManager.getProfesorByEmail(getCurrentUserEmail(context))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.isNotEmpty()) {
                        view?.afisareStudentiLicenta(it[0].studenti_licenta_acceptati)
                        view?.afisareStudentiMaster(it[0].studenti_disertatie_acceptati)
                    }
                    view?.hideProgress()
                }, {
                    Log.d("problem", "could not get all cerere")
                   view?.hideProgress()
                })
        )
    }
}