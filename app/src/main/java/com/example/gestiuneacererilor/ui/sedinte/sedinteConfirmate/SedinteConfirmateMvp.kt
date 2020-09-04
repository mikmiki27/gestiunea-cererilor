package com.example.gestiuneacererilor.ui.sedinte.sedinteConfirmate

import com.example.gestiuneacererilor.data.restmanager.data.Sedinta
import com.example.gestiuneacererilor.ui.base.BaseMvp

interface SedinteConfirmateMvp {
    interface View : BaseMvp.View {
        fun showListaSedinteConfirmate(list: List<Sedinta>)
        fun showPlaceholderForSedinteConfirmate()
        fun showPlaceholderForSedinteConfirmateNetwork()
    }

    interface Presenter : BaseMvp.Presenter {
        fun getAllSedinteConfirmate()
    }
}