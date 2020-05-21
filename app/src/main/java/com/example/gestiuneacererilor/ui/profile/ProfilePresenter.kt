package com.example.gestiuneacererilor.ui.profile

import android.content.Context
import com.example.gestiuneacererilor.data.managers.authmanager.FirebaseAuthManagerImpl
import com.example.gestiuneacererilor.ui.base.BasePresenter

class ProfilePresenter(
    view: ProfileMvp.View,
    val context: Context,
    private val firebaseAuth: FirebaseAuthManagerImpl
) : BasePresenter<ProfileMvp.View>(view), ProfileMvp.Presenter {

    override fun singOut() {
        firebaseAuth.mAuth?.signOut()
        view?.goToMainActivity()
    }
}
