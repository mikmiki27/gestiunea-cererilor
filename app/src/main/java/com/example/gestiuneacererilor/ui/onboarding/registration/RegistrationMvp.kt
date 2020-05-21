package com.example.gestiuneacererilor.ui.onboarding.registration

import android.app.Activity
import com.example.gestiuneacererilor.ui.base.BaseMvp

interface RegistrationMvp {
    interface View : BaseMvp.View {
        fun goToMainActivity()
    }

    interface Presenter : BaseMvp.Presenter {
        fun singUpUser(email: String, password: String, activity: Activity)
    }
}