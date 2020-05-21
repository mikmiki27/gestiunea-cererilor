package com.example.gestiuneacererilor.ui.onboarding

import android.content.Context
import com.example.gestiuneacererilor.data.managers.authmanager.FirebaseAuthManager
import com.example.gestiuneacererilor.ui.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class OnBoardingActivityPresenter(
    view: OnBoardingMvp.View,
    private val firebaseAuth: FirebaseAuthManager
) : BasePresenter<OnBoardingMvp.View>(view), OnBoardingMvp.Presenter