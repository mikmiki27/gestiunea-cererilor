package com.example.gestiuneacererilor.ui.echipa

import android.app.Activity
import android.content.Context
import android.util.Log
import com.example.gestiuneacererilor.data.managers.profesormanager.ProfesorManager
import com.example.gestiuneacererilor.ui.base.BasePresenter
import com.example.gestiuneacererilor.utils.SharedPrefUtil
import com.example.gestiuneacererilor.utils.getProfesorCurrentEmail
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class EchipaPresenter(
    view: EchipaMvp.View,
    val context: Context,
    private val profesorManager: ProfesorManager
) : BasePresenter<EchipaMvp.View>(view), EchipaMvp.Presenter {

    override fun getProfesorByEmail(activity: Activity) {
        view?.showProgress()
        subscription.add(
            profesorManager.getProfesorByEmail(FirebaseAuth.getInstance().currentUser?.email.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                    if (it.isNotEmpty()) {
                        SharedPrefUtil.addKeyValue(
                            activity, SharedPrefUtil.PROFESOR_CURRENT_ID, it[0].id
                                ?: ""
                        )
                        SharedPrefUtil.addKeyValue(
                            activity, SharedPrefUtil.PROFESOR_CURRENT_NUME, it[0].nume
                                ?: ""
                        )
                        SharedPrefUtil.addKeyValue(
                            activity, SharedPrefUtil.PROFESOR_CURRENT_PRENUME, it[0].prenume
                                ?: ""
                        )
                        SharedPrefUtil.addKeyValue(
                            activity,
                            SharedPrefUtil.PROFESOR_FULL_NAME,
                            it[0].prenume + " " + it[0].nume
                                ?: ""
                        )
                        SharedPrefUtil.addKeyValue(
                            activity, SharedPrefUtil.PROFESOR_CURRENT_EMAIL, it[0].email
                                ?: ""
                        )
                        SharedPrefUtil.addKeyValue(
                            activity,
                            SharedPrefUtil.PROFESOR_CERINTE_LICENTA,
                            it[0].cerinte_suplimentare_licenta
                                ?: ""
                        )
                        SharedPrefUtil.addKeyValue(
                            activity,
                            SharedPrefUtil.PROFESOR_CERINTE_MASTER,
                            it[0].cerinte_suplimentare_disertatie
                                ?: ""
                        )
                        SharedPrefUtil.addKeyValue(
                            activity, SharedPrefUtil.PROFESOR_CURRENT_FACULTATE, it[0].facultate
                                ?: ""
                        )
                        SharedPrefUtil.addKeyValue(
                            activity,
                            SharedPrefUtil.PROFESOR_ECHIPA_LICENTA,
                            it[0].nr_studenti_echipa_licenta
                                ?: ""
                        )
                        SharedPrefUtil.addKeyValue(
                            activity,
                            SharedPrefUtil.PROFESOR_ECHIPA_MASTER,
                            it[0].nr_studenti_echipa_disertatie
                                ?: ""
                        )
                        SharedPrefUtil.addKeyValue(
                            activity,
                            SharedPrefUtil.PROFESOR_LICENTA_ACCEPTATI,
                            it[0].studenti_licenta_acceptati
                                ?: ""
                        )
                        SharedPrefUtil.addKeyValue(
                            activity,
                            SharedPrefUtil.PROFESOR_DISERTATIE_ACCEPTATI,
                            it[0].studenti_disertatie_acceptati
                                ?: ""
                        )
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