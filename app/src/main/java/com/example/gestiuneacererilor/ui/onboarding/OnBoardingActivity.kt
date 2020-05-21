package com.example.gestiuneacererilor.ui.onboarding

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import com.example.gestiuneacererilor.R
import com.example.gestiuneacererilor.data.managers.authmanager.AuthManagerImpl
import com.example.gestiuneacererilor.ui.base.BaseActivity
import com.example.gestiuneacererilor.ui.main.MainActivity
import com.example.gestiuneacererilor.ui.onboarding.signin.SignInActivity
import kotlinx.android.synthetic.main.activity_onboarding.*

class OnBoardingActivity : BaseActivity<OnBoardingMvp.Presenter>(), OnBoardingMvp.View {

    companion object{
        fun getIntent(context: Context): Intent{
            val intent = Intent(context, OnBoardingActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            return intent
        }
    }

    override var presenter: OnBoardingMvp.Presenter = OnBoardingActivityPresenter(this, AuthManagerImpl.getInstance())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        setContentView(R.layout.activity_onboarding)

        signin_button.setOnClickListener {
            val intent = Intent(applicationContext, SignInActivity::class.java)
            startActivity(intent)
        }

        create_account_button.setOnClickListener {
            presenter.launchSignUp(this)
        }

    }

    override fun goToMainActivity() {
        startActivity(MainActivity.getIntent(this))
    }
}
