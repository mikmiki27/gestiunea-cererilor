package com.example.gestiuneacererilor.ui.echipa

import android.app.Activity
import com.example.gestiuneacererilor.ui.base.BaseMvp

interface EchipaMvp {

    interface View : BaseMvp.View {
        fun afisareStudentiLicenta(studentiLicentaAcceptati: String?)
        fun afisareStudentiMaster(studentiDisertatieAcceptati: String?)
    }

    interface Presenter : BaseMvp.Presenter {
        fun getProfesorByEmail(activity: Activity)
    }
}