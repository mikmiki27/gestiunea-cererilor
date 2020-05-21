package com.example.gestiuneacererilor.ui.splash

import android.content.Context
import com.example.gestiuneacererilor.data.managers.authmanager.FirebaseAuthManager
import com.example.gestiuneacererilor.ui.base.BasePresenter
import com.google.firebase.auth.FirebaseUser



class SplashActivityPresenter(
    view: SplashMvp.View,
    private val firebaseAuth: FirebaseAuthManager
) : BasePresenter<SplashMvp.View>(view), SplashMvp.Presenter {

    override fun tryToLoginUser(context: Context) {
        firebaseAuth.initFirebaseAuth(context)

        try {
            if (firebaseAuth.getCurrentUser() != null) {
                view?.goToMainActivity()
           } else {
                view?.goToOnBoardingActivity()
            }
        } catch (ex: Throwable) {
            view?.goToOnBoardingActivity()
        }
    }
}