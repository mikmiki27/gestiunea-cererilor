package com.example.gestiuneacererilor.ui.onboarding.phone

import com.example.gestiuneacererilor.ui.base.BaseMvp

interface PhoneMvp {
    interface View : BaseMvp.View {
        fun setName(name: String)
        fun showErrorDialog(title: String, message: String)
        fun setPhoneError(error: String?)
        fun finishSettingPhoneNo()
    }

    interface Presenter : BaseMvp.Presenter {
        fun setName()
        fun savePhoneClicked(phone: String?)
    }
}