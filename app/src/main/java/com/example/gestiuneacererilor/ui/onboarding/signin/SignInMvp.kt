package com.example.gestiuneacererilor.ui.onboarding.signin

import android.app.Activity
import com.example.gestiuneacererilor.ui.base.BaseMvp

interface SignInMvp {
    interface View : BaseMvp.View {
        fun goToMainActivity()
        fun showErrorDialog(title: String, message: String)
        fun setInvalidErrorInline()
        fun setInvalidErrorUnsualActivity()
    }

    interface Presenter : BaseMvp.Presenter {
        fun signInWithEmailAndPassword(activity: Activity, email: String, password: String)
    }
}