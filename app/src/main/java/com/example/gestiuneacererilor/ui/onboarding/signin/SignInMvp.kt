package com.example.gestiuneacererilor.ui.onboarding.signin

import android.app.Activity
import android.content.Context
import com.example.gestiuneacererilor.ui.base.BaseMvp

interface SignInMvp {
    interface View : BaseMvp.View {
        fun goToMainActivity()

        fun showErrorDialog(title: String, message: String)
    }

    interface Presenter : BaseMvp.Presenter {
        fun signinWithResourceOwnerPassword(context: Context, email: String, password: String)
        fun forgotPassword(activity: Activity)
    }
}