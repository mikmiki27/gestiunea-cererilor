package com.example.gestiuneacererilor.ui.onboarding.registration

import android.app.Activity
import com.example.gestiuneacererilor.data.managers.authmanager.FirebaseAuthManager
import com.example.gestiuneacererilor.ui.base.BasePresenter
import com.example.gestiuneacererilor.utils.SharedPrefUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RegistrationActivityPresenter(
    view: RegistrationMvp.View,
    private val firebaseAuth: FirebaseAuthManager
) : BasePresenter<RegistrationMvp.View>(view), RegistrationMvp.Presenter {

    override fun singUpUser(email: String, password: String, activity: Activity) {
        view?.showProgress()

        subscription.add(
            firebaseAuth.createUserWithEmailAndPassword(email, password, activity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    SharedPrefUtil.addKeyValue(
                        activity, SharedPrefUtil.CURRENT_FIREBASE_USER_EMAIL, it.email
                            ?: ""
                    )
                    SharedPrefUtil.addKeyValue(
                        activity, SharedPrefUtil.CURRENT_FIREBASE_USER_NAME, it.displayName
                            ?: ""
                    )
                    view?.goToMainActivity()
                }, {
                    view?.hideProgress()
                })
        )
    }
}