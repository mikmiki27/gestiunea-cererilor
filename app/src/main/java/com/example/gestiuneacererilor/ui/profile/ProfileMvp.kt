package com.example.gestiuneacererilor.ui.profile

import com.example.gestiuneacererilor.ui.base.BaseMvp

interface ProfileMvp {

    interface View : BaseMvp.View {
        fun goToMainActivity()
    }

    interface Presenter : BaseMvp.Presenter {
        fun singOut()
    }
}