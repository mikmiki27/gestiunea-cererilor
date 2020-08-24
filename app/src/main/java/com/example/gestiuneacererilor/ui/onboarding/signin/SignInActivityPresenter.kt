package com.example.gestiuneacererilor.ui.onboarding.signin

import android.app.Activity
import android.util.Log
import com.example.gestiuneacererilor.data.managers.authmanager.FirebaseAuthManager
import com.example.gestiuneacererilor.data.managers.profesormanager.ProfesorManager
import com.example.gestiuneacererilor.data.managers.studentmanager.StudentManager
import com.example.gestiuneacererilor.ui.base.BasePresenter
import com.example.gestiuneacererilor.utils.SharedPrefUtil
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SignInActivityPresenter(
    view: SignInMvp.View,
    private val firebaseAuth: FirebaseAuthManager,
    private val studentManager: StudentManager,
    private val profesorManager: ProfesorManager
) : BasePresenter<SignInMvp.View>(view), SignInMvp.Presenter {

    override fun signInWithEmailAndPassword(activity: Activity, email: String, password: String) {
        view?.showProgress()
        subscription.add(
            firebaseAuth.signInWithEmailAndPassword(email, password, activity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    SharedPrefUtil.addKeyValue(
                        activity, SharedPrefUtil.CURRENT_FIREBASE_USER_EMAIL, it.email
                            ?: ""
                    )
                    SharedPrefUtil.addKeyValue(
                        activity, SharedPrefUtil.CURRENT_FIREBASE_DISPLAY_NAME, it.displayName
                            ?: ""
                    )
                    view?.hideProgress()
                    view?.goToMainActivity()
                }, {
                    if (it is FirebaseAuthInvalidCredentialsException) {
                        view?.setInvalidErrorInline()
                    }
                    if (it is FirebaseTooManyRequestsException) {
                        view?.setInvalidErrorUnsualActivity()
                    }
                    view?.hideProgress()
                })
        )
    }

    override fun getStudentByEmail(activity: Activity, email: String) {
        view?.showProgress()
        subscription.add(
            studentManager.getStudentByEmail(email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    SharedPrefUtil.addKeyValue(
                        activity, SharedPrefUtil.CURRENT_USER_NUME, it[0].nume
                            ?: ""
                    )
                    SharedPrefUtil.addKeyValue(
                        activity, SharedPrefUtil.CURRENT_USER_PRENUME, it[0].prenume
                            ?: ""
                    )
                    SharedPrefUtil.addKeyValue(
                        activity, SharedPrefUtil.CURRENT_USER_FACULTATE, it[0].facultate
                            ?: ""
                    )
                    SharedPrefUtil.addKeyValue(
                        activity, SharedPrefUtil.CURRENT_USER_AN, it[0].an
                            ?: ""
                    )
                    SharedPrefUtil.addKeyValue(
                        activity,
                        SharedPrefUtil.CURRENT_USER_PROFESOR_COORDONATOR,
                        it[0].profesor_coordonator
                            ?: ""
                    )
                    SharedPrefUtil.addKeyValue(
                        activity, SharedPrefUtil.CURRENT_USER_TITLU_LUCRARE, it[0].titlu_lucrare
                            ?: ""
                    )
                    SharedPrefUtil.addKeyValue(
                        activity, SharedPrefUtil.CURRENT_USER_CICLU, it[0].ciclu
                            ?: ""
                    )
                    SharedPrefUtil.addKeyValue(
                        activity, SharedPrefUtil.CURRENT_USER_ID, it[0].id
                            ?: ""
                    )
                    view?.hideProgress()
                }, {
                    Log.d("problem", "could not student by email")
                    view?.hideProgress()
                })
        )
    }

    override fun getProfessorByEmail(activity: Activity, email: String) {
        view?.showProgress()
        subscription.add(
            profesorManager.getProfesorByEmail(email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    when {
                        it.isNullOrEmpty() -> {
                            SharedPrefUtil.addKeyValue(
                                activity,
                                SharedPrefUtil.CURRENT_USER_ECHIPA_LICENTA,
                                it[0].nr_studenti_echipa_licenta
                                    ?: ""
                            )
                            SharedPrefUtil.addKeyValue(
                                activity,
                                SharedPrefUtil.CURRENT_USER_ECHIPA_MASTER,
                                it[0].nr_studenti_echipa_disertatie
                                    ?: ""
                            )
                            SharedPrefUtil.addKeyValue(
                                activity,
                                SharedPrefUtil.CURRENT_USER_CERINTE_MASTER,
                                it[0].cerinte_suplimentare_disertatie
                                    ?: ""
                            )
                            SharedPrefUtil.addKeyValue(
                                activity,
                                SharedPrefUtil.CURRENT_USER_CERINTE_LICENTA,
                                it[0].cerinte_suplimentare_licenta
                                    ?: ""
                            )
                            SharedPrefUtil.addKeyValue(
                                activity,
                                SharedPrefUtil.CURRENT_USER_LICENTA_ACCEPTATI,
                                it[0].studenti_licenta_acceptati
                                    ?: ""
                            )
                            SharedPrefUtil.addKeyValue(
                                activity,
                                SharedPrefUtil.CURRENT_USER_DISERTATIE_ACCEPTATI,
                                it[0].studenti_disertatie_acceptati
                                    ?: ""
                            )
                            SharedPrefUtil.addKeyValue(
                                activity, SharedPrefUtil.CURRENT_USER_NUME, it[0].nume
                                    ?: ""
                            )
                            SharedPrefUtil.addKeyValue(
                                activity, SharedPrefUtil.CURRENT_USER_PRENUME, it[0].prenume
                                    ?: ""
                            )
                            SharedPrefUtil.addKeyValue(
                                activity, SharedPrefUtil.CURRENT_USER_FACULTATE, it[0].facultate
                                    ?: ""
                            )
                            SharedPrefUtil.addKeyValue(
                                activity, SharedPrefUtil.CURRENT_USER_ID, it[0].id
                                    ?: ""
                            )
                        }
                    }
                    view?.hideProgress()
                }, {
                    Log.d("problem", "could not get profesor by email")
                    view?.hideProgress()
                })
        )
    }
}