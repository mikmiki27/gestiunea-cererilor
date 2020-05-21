package com.example.gestiuneacererilor.ui.onboarding.registration

import android.os.Build
import android.os.Bundle
import android.view.View
import com.example.gestiuneacererilor.R
import com.example.gestiuneacererilor.data.managers.authmanager.FirebaseAuthManagerImpl
import com.example.gestiuneacererilor.ui.base.BaseActivity
import com.example.gestiuneacererilor.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_registration.*

class RegistrationActivity : BaseActivity<RegistrationMvp.Presenter>(), RegistrationMvp.View {

    override var presenter: RegistrationMvp.Presenter =
        RegistrationActivityPresenter(this, FirebaseAuthManagerImpl.getInstance())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        setContentView(R.layout.activity_registration)

        sign_up_registration_btn.setOnClickListener {
            presenter.singUpUser(email_text.text.toString(), password_text.text.toString(), this)
        }
    }

    override fun goToMainActivity() {
        startActivity(MainActivity.getIntent(this))
    }
}