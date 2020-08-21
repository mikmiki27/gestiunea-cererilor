package com.example.gestiuneacererilor.ui.echipa

import com.example.gestiuneacererilor.ui.base.BaseMvp

interface EchipaMvp {

    interface View : BaseMvp.View {
        fun afisareStudentiLicenta(studentiLicentaAcceptati: String?)
        fun afisareStudentiMaster(studentiDisertatieAcceptati: String?)
    }

    interface Presenter : BaseMvp.Presenter {
        fun getProfesorByEmail()
    }
}