package com.example.gestiuneacererilor.ui.sedinte.sedintesolicitate

import com.example.gestiuneacererilor.data.restmanager.data.Sedinta
import com.example.gestiuneacererilor.ui.base.BaseMvp

interface SedinteSolicitateMvp {
    interface View : BaseMvp.View {
        fun showListaSedinte(list: List<Sedinta>)
        fun showPlaceholderForSedinte()
        fun showPlaceholderForSedinteNetwork()
        fun goToSedinteConfirmate()
    }

    interface Presenter : BaseMvp.Presenter {
        fun getAllSedinte()
        fun updateStatusSedintaToRespins(sedinta: Sedinta)
        fun updateStatusSedintaToAcceptat(sedinta: Sedinta)
    }
}