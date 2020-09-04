package com.example.gestiuneacererilor.ui.sedinte.programareSedinte

import android.app.Activity
import com.example.gestiuneacererilor.data.restmanager.data.Sedinta
import com.example.gestiuneacererilor.ui.base.BaseMvp

interface ProgramareSedinteMvp {
    interface View : BaseMvp.View {
        fun goToCerileConfirmateAleStudentului()
    }

    interface Presenter : BaseMvp.Presenter {
        fun enterNewSedinta(sedinta: Sedinta)
        fun getProfesorCoordonatorByEmail(emailProfCoord: String, activity: Activity)
    }
}