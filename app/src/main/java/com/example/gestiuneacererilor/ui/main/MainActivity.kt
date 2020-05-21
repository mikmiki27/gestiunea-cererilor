package com.example.gestiuneacererilor.ui.main

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.gestiuneacererilor.R
import com.example.gestiuneacererilor.ui.base.BaseActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : BaseActivity<MainMvp.Presenter>(), MainMvp.View {

    override var presenter: MainMvp.Presenter = MainPresenter(this, this)

    companion object {
        fun getIntent(context: Context): Intent {
            val intent = Intent(context, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        setUpNavigation()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.menu_toolbar, menu)

        menu?.findItem(R.id.toolbar_notifications)?.setOnMenuItemClickListener {
            true
        }

        return super.onCreateOptionsMenu(menu)
    }

    private fun setUpNavigation() {
        (supportFragmentManager.findFragmentById(R.id.navigation_host) as? NavHostFragment)?.let { navHostFragment ->
            NavigationUI.setupWithNavController(
                findViewById<BottomNavigationView>(R.id.bottom_navigation),
                navHostFragment.navController
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.clearSubscriptionsForIds()
    }
}
