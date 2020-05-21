package com.example.gestiuneacererilor.ui.splash

import android.content.Context
import com.example.gestiuneacererilor.ui.base.BaseMvp

interface SplashMvp {
    interface View : BaseMvp.View {
        fun goToOnBoardingActivity()

        fun goToMainActivity()
    }

    interface Presenter : BaseMvp.Presenter {
        fun tryToLoginUser(context: Context)
    }
}