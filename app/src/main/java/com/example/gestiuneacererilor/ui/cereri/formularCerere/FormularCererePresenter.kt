package com.example.gestiuneacererilor.ui.cereri.formularCerere

import android.content.Context
import android.util.Log
import com.example.gestiuneacererilor.data.managers.cereremanager.CerereManager
import com.example.gestiuneacererilor.data.restmanager.data.Cerere
import com.example.gestiuneacererilor.ui.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class FormularCererePresenter(
    view: FormularCerereMvp.View,
    val context: Context,
    private val cerereManager: CerereManager
) : BasePresenter<FormularCerereMvp.View>(view), FormularCerereMvp.Presenter {

    override fun postNewCerere(cerereNoua: Cerere) {
        view?.showProgress()
        subscription.add(
            cerereManager.enterNewCerere(cerereNoua)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view?.goToCererileMele()
                    view?.hideProgress()
                }, {
                    Log.d("problem", "could not post new cerere")
                    view?.showPlaceholderForProfessorNetwork() //todo test this
                    view?.hideProgress()
                })
        )
    }
}