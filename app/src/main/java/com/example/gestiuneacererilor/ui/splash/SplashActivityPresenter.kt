package com.example.gestiuneacererilor.ui.splash

import android.content.Context
import com.example.gestiuneacererilor.data.managers.authmanager.AuthManager
import com.example.gestiuneacererilor.ui.base.BasePresenter

class SplashActivityPresenter(
    view: SplashMvp.View,
    val authManager: AuthManager
) : BasePresenter<SplashMvp.View>(view), SplashMvp.Presenter {

    override fun tryToLoginUser(context: Context) {
        authManager.initAppId(context)

        try {
            if (authManager.getUserAuthData(context).refreshToken.isNotEmpty()) {
                view?.goToMainActivity()
            } else {
                view?.goToOnBoardingActivity()
            }
        } catch (ex: Throwable) {
            view?.goToOnBoardingActivity()
        }


        // We don't neet to check refresh token availability in this phase of project
        // because of the Lite Plan in App Id - limitations with the number of events
//       subscription.add(
//           authManager.tryLoginUserWithRefreshToken(context)
//               .subscribeOn(Schedulers.io())
//               .observeOn(AndroidSchedulers.mainThread())
//               .subscribe({
//                   view?.goToMainActivity()
//               },{
//                   view?.goToOnBoardingActivity()
//               })
//       )
    }
}