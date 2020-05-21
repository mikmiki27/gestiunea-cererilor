package com.example.gestiuneacererilor.ui.onboarding

import android.app.Activity
import com.example.gestiuneacererilor.data.managers.authmanager.AuthManager
import com.example.gestiuneacererilor.ui.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class OnBoardingActivityPresenter(
    view: OnBoardingMvp.View,
    private val authManager: AuthManager
) : BasePresenter<OnBoardingMvp.View>(view), OnBoardingMvp.Presenter {

    override fun launchSignUp(activity: Activity) {
        view?.showProgress()

        subscription.add(
            authManager.launchSignUp(activity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view?.goToMainActivity()
                }, {
                    view?.hideProgress()
                })
        )
    }
}