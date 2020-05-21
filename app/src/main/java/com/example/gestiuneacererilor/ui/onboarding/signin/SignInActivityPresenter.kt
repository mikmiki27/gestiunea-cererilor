package com.example.gestiuneacererilor.ui.onboarding.signin

import android.app.Activity
import com.example.gestiuneacererilor.data.managers.authmanager.FirebaseAuthManager
import com.example.gestiuneacererilor.ui.base.BasePresenter
import com.example.gestiuneacererilor.utils.SharedPrefUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SignInActivityPresenter(
    view: SignInMvp.View,
    private val firebaseAuth: FirebaseAuthManager
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
                        activity, SharedPrefUtil.CURRENT_FIREBASE_USER_NAME, it.displayName
                            ?: ""
                    )
                    view?.hideProgress()
                    view?.goToMainActivity()
                }, {
                    view?.hideProgress()
                })
        )
    }
}