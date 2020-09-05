package com.example.gestiuneacererilor.ui.sedinte.sedinteconfirmatestudent

import android.app.Activity
import com.example.gestiuneacererilor.data.restmanager.data.Sedinta
import com.example.gestiuneacererilor.ui.base.BaseMvp

interface SedinteConfirmateStudentMvp {
    interface View : BaseMvp.View {
        fun showListaSedinteConfirmateStudent(list: List<Sedinta>)
        fun showPlaceholderForSedinteConfirmateStudent()
        fun showPlaceholderForSedinteConfirmateNetworkStudent()
    }

    interface Presenter : BaseMvp.Presenter {
        fun getAllSedinteConfirmateStudent()
        fun getCurrentStudentByEmail(activity: Activity)
    }
}