package com.example.gestiuneacererilor.ui.onboarding.signin

import android.app.Activity
import android.content.Context
import com.example.gestiuneacererilor.R
import com.example.gestiuneacererilor.data.managers.authmanager.AuthManager
import com.example.gestiuneacererilor.ui.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SignInActivityPresenter(
    view: SignInMvp.View,
    private val authManager: AuthManager
) : BasePresenter<SignInMvp.View>(view), SignInMvp.Presenter {

    override fun signinWithResourceOwnerPassword(
        context: Context,
        email: String,
        password: String
    ) {
        view?.showProgress()

        subscription.add(
            authManager.signInWithResourceOwnerPassword(context, email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view?.goToMainActivity()
                }, {
                    view?.hideProgress()
                    view?.showErrorDialog(
                        context.getString(R.string.error_login), context.getString(
                            R.string.invalid_email_or_password
                        )
                    )
                })
        )
    }

    override fun forgotPassword(activity: Activity) {
        view?.showProgress()
        subscription.add(
            authManager.forgotPassword(activity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view?.hideProgress()
                }, {
                    view?.hideProgress()
                    view?.showErrorDialog(
                        activity.getString(R.string.forgot_pass_failed), activity.getString(
                            R.string.forgot_pass_process_failed
                        )
                    )
                })
        )
    }
}