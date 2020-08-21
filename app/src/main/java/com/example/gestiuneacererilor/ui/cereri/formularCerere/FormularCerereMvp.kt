package com.example.gestiuneacererilor.ui.cereri.formularCerere

import com.example.gestiuneacererilor.data.restmanager.data.Cerere
import com.example.gestiuneacererilor.ui.base.BaseMvp

interface FormularCerereMvp {
    interface View : BaseMvp.View {
        fun goToCererileMele()
        fun showPlaceholderForProfessorNetwork()
    }

    interface Presenter : BaseMvp.Presenter {
        fun postNewCerere(cerereNoua: Cerere)
    }
}