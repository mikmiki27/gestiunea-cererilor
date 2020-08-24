package com.example.gestiuneacererilor.ui.cereri.cereriStudent

import com.example.gestiuneacererilor.data.restmanager.data.Cerere
import com.example.gestiuneacererilor.ui.base.BaseMvp

interface CereriStudentMvp {
    interface View : BaseMvp.View {
        fun showPlaceholderForNoRequests()
        fun showCererileMele(list: List<Cerere>)
        fun goToCereriFromCereri()
    }

    interface Presenter : BaseMvp.Presenter {
        fun getAllCerere()
        fun anulareCerere(cerere: Cerere)
    }
}