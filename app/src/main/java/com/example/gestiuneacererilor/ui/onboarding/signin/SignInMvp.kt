package com.example.gestiuneacererilor.ui.onboarding.signin

import com.example.gestiuneacererilor.ui.base.BaseMvp

interface SignInMvp {
    interface View : BaseMvp.View {
        fun goToMainActivity()

        fun showErrorDialog(title: String, message: String)
    }

    interface Presenter : BaseMvp.Presenter
}