package com.example.gestiuneacererilor.ui.onboarding.phone

import android.content.Context
import android.util.Log
import com.example.gestiuneacererilor.R
import com.example.gestiuneacererilor.data.managers.authmanager.AuthManager
import com.example.gestiuneacererilor.data.managers.usermanager.UserManager
import com.example.gestiuneacererilor.data.restmanager.data.CreateIdRequestBody
import com.example.gestiuneacererilor.data.restmanager.data.ProfileRequest
import com.example.gestiuneacererilor.data.restmanager.data.SaveUserRequestBody
import com.example.gestiuneacererilor.ui.base.BasePresenter
import com.example.gestiuneacererilor.utils.generateLogTag
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PhoneActivityPresenter(
    view: PhoneMvp.View,
    private val context: Context,
    private val authManager: AuthManager,
    val userManager: UserManager
) : BasePresenter<PhoneMvp.View>(view), PhoneMvp.Presenter {

    override fun setName() {
        view?.setName(authManager.getUserAuthData(context).name)
    }

    override fun savePhoneClicked(phone: String?) {
        if(phone == null || phone.isEmpty() || phone.isBlank()){
            view?.setPhoneError(context.getString(R.string.enter_phone_number))
        }else if(phone.length < 10){
            view?.setPhoneError(context.getString(R.string.phone_must_have_10))
        }else{
            view?.setPhoneError(null)
            view?.showProgress()

            val userAuth= authManager.getUserAuthData(context)
            subscription.add(
                userManager.saveUser(
                    SaveUserRequestBody(
                        ProfileRequest(
                    email = userAuth.email,
                    iamId = userAuth.iamId,
                    id = userAuth.id,
                    name = userAuth.name,
                    phone = phone,
                    ciclu = userAuth.ciclu,
                    rev = userAuth.rev
                )
                    )
                )
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        if(it.ok){
                            authManager.updateUserAuthData(it, phone, context)

                            addPendingId()
                           // addMyRequestsId()
                            view?.finishSettingPhoneNo()

                        }else{
                            view?.showErrorDialog(context.getString(R.string.error),context.getString(R.string.please_try_again_phone))
                            view?.hideProgress()
                        }
                    }, {
                        view?.hideProgress()
                        view?.showErrorDialog(context.getString(R.string.error),context.getString(R.string.please_try_again_phone))
                    })
            )
        }
    }

    private fun addPendingId(){
        val userId= authManager.getUserAuthData(context).id
        subscriptionForIds.add(
            userManager.createPendingId(CreateIdRequestBody(userId!!))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if(it.result.contentEquals("created")){
                        Log.d(generateLogTag(),"pending Id was created successfully")
                    }else{
                        Log.e(generateLogTag(),"pending Id was NOT created successfully")
                    }
                },{
                    Log.e(generateLogTag(),"pending Id was NOT created successfully + ${it.message}")
                })
        )
    }
}