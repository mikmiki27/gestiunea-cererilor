package com.example.gestiuneacererilor.ui.cereri

import android.content.Context
import android.util.Log
import com.example.gestiuneacererilor.data.managers.cereremanager.CerereManager
import com.example.gestiuneacererilor.ui.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CereriPresenter(
    view: CereriMvp.View,
    val context: Context,
    private val cerereManager: CerereManager
) : BasePresenter<CereriMvp.View>(view), CereriMvp.Presenter {

    override fun getAllCerere() {
        subscription.add(
            cerereManager.getAllCerere()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view?.showCereriDisponibileForProfesor(it)
                }, {
                    Log.d("problem", "could not get all cerere")
                })
        )
    }
}