package com.example.gestiuneacererilor.ui.splash

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Pair
import android.view.View
import android.view.animation.AnimationUtils
import com.example.gestiuneacererilor.R
import com.example.gestiuneacererilor.ui.base.BaseActivity
import com.example.gestiuneacererilor.ui.main.MainActivity
import com.example.gestiuneacererilor.ui.onboarding.OnBoardingActivity
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : BaseActivity<SplashMvp.Presenter>(), SplashMvp.View {

    override var presenter: SplashMvp.Presenter =
        SplashActivityPresenter(this)

    private val animationDelay: Long = 1100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        setContentView(R.layout.activity_splash)

        logo_imageView.animation =
            AnimationUtils.loadAnimation(applicationContext, R.anim.top_animation).apply {
                fillAfter = true
                isFillEnabled = true
            }

        Handler().postDelayed({
            applicationContext?.let {
                presenter.tryToLoginUser(it)
            }
        }, animationDelay)
    }

    override fun goToOnBoardingActivity() {
        val intent = Intent(applicationContext, OnBoardingActivity::class.java)
        val activityOptions = ActivityOptions.makeSceneTransitionAnimation(
            this, Pair.create(logo_imageView, "imageTransition")
        )
        startActivity(intent, activityOptions.toBundle())
    }

    override fun goToMainActivity() {
        startActivity(MainActivity.getIntent(this))
    }
}
