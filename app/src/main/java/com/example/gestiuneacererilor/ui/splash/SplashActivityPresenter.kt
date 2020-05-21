package com.example.gestiuneacererilor.ui.splash

import android.content.Context
import com.example.gestiuneacererilor.ui.base.BasePresenter

class SplashActivityPresenter(
    view: SplashMvp.View
) : BasePresenter<SplashMvp.View>(view), SplashMvp.Presenter {

    override fun tryToLoginUser(context: Context) {
       /* authManager.initAppId(context)

        try {
            if (authManager.getUserAuthData(context).refreshToken.isNotEmpty()) {
             */   view?.goToMainActivity()
           /* } else {
                view?.goToOnBoardingActivity()
            }
        } catch (ex: Throwable) {
            view?.goToOnBoardingActivity()
        }*/
    }
}