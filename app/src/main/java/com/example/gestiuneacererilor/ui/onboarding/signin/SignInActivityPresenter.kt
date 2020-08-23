package com.example.gestiuneacererilor.ui.onboarding.signin

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.gestiuneacererilor.data.managers.authmanager.FirebaseAuthManager
import com.example.gestiuneacererilor.ui.base.BasePresenter
import com.example.gestiuneacererilor.utils.SharedPrefUtil
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
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
                    if (it is FirebaseAuthInvalidCredentialsException) {
                        // Toast.makeText(context, "Invalid password", Toast.LENGTH_SHORT).show()
                        Log.i("invalid password", "invalid")
                        view?.setInvalidErrorInline()
                    }
                    if(it is FirebaseTooManyRequestsException) {
                        view?.setInvalidErrorUnsualActivity()
                    }
                    view?.hideProgress()
                })
        )
    }
}