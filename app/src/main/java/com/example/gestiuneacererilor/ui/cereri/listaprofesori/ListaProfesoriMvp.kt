package com.example.gestiuneacererilor.ui.cereri.listaprofesori

import android.app.Activity
import com.example.gestiuneacererilor.data.restmanager.data.NewProfesorRequestBody
import com.example.gestiuneacererilor.ui.base.BaseMvp

interface ListaProfesoriMvp {
    interface View : BaseMvp.View {
        fun showListaProfesoriDisponibili(list: List<NewProfesorRequestBody>)
        fun showPlaceholderForProfessors()
        fun showPlaceholderForProfessorNetwork()
        fun showPlaceHolderForAlreadyGotProf(currentStudentProfesorCoordonator: String)
    }

    interface Presenter : BaseMvp.Presenter {
        fun getAllProfesoriDisponibili()
        fun getStudentByEmail(activity: Activity)
    }
}