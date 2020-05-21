package com.example.gestiuneacererilor.ui.onboarding

import android.app.Activity
import com.example.gestiuneacererilor.ui.base.BaseMvp

interface OnBoardingMvp {
    interface View : BaseMvp.View {
        fun goToMainActivity()
    }

    interface Presenter : BaseMvp.Presenter
}