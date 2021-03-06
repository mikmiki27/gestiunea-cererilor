package com.example.gestiuneacererilor.ui.onboarding.registration

import android.app.Activity
import com.example.gestiuneacererilor.data.restmanager.data.Student
import com.example.gestiuneacererilor.data.restmanager.data.Professor
import com.example.gestiuneacererilor.ui.base.BaseMvp

interface RegistrationMvp {
    interface View : BaseMvp.View {
        fun goToMainActivity()
    }

    interface Presenter : BaseMvp.Presenter {
        fun singUpUser(student: Student, activity: Activity)
        fun singUpUser(professor: Professor, activity: Activity)
        fun singUpUserToFirebase(email: String, password: String, activity: Activity)
    }
}