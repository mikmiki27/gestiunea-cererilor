package com.example.gestiuneacererilor.ui.cereri.listaprofesori

import android.app.Activity
import com.example.gestiuneacererilor.data.restmanager.data.Cerere
import com.example.gestiuneacererilor.data.restmanager.data.Professor
import com.example.gestiuneacererilor.ui.base.BaseMvp

interface ListaProfesoriMvp {
    interface View : BaseMvp.View {
        fun showListaProfesoriDisponibili(list: List<Professor>)
        fun showPlaceholderForProfessors()
        fun showPlaceholderForProfessorNetwork()
        fun showPlaceHolderForAlreadyGotProf(currentStudentProfesorCoordonator: String)
        fun setListCereriStudentCurent(list: List<Cerere>): List<Cerere>
    }

    interface Presenter : BaseMvp.Presenter {
        fun getAllCereriForCurrentStudent()
        fun getAllProfesoriDisponibili()
        fun getStudentByEmail(activity: Activity)
    }
}