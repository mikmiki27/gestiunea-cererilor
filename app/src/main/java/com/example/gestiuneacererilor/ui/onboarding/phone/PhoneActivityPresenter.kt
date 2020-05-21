package com.example.gestiuneacererilor.ui.onboarding.phone

import android.content.Context
import android.util.Log
import com.example.gestiuneacererilor.R
import com.example.gestiuneacererilor.ui.base.BasePresenter
import com.example.gestiuneacererilor.utils.generateLogTag
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PhoneActivityPresenter(
    view: PhoneMvp.View,
    private val context: Context
) : BasePresenter<PhoneMvp.View>(view), PhoneMvp.Presenter {

    override fun setName() {
       // view?.setName(authManager.getUserAuthData(context).name)
    }

    override fun savePhoneClicked(phone: String?) {
        if(phone == null || phone.isEmpty() || phone.isBlank()){
            view?.setPhoneError(context.getString(R.string.enter_phone_number))
        }else if(phone.length < 10){
            view?.setPhoneError(context.getString(R.string.phone_must_have_10))
        }else{
            view?.setPhoneError(null)
            view?.showProgress()
        }
    }
}