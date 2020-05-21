package com.example.gestiuneacererilor.ui.onboarding.signin

import android.app.Activity
import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.gestiuneacererilor.R
import com.example.gestiuneacererilor.data.managers.authmanager.FirebaseAuthManager
import com.example.gestiuneacererilor.data.managers.authmanager.FirebaseAuthManagerImpl
import com.example.gestiuneacererilor.ui.base.BaseActivity
import com.example.gestiuneacererilor.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : BaseActivity<SignInMvp.Presenter>(), SignInMvp.View {

    override var presenter: SignInMvp.Presenter =
        SignInActivityPresenter(this, FirebaseAuthManagerImpl.getInstance())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        setContentView(R.layout.activity_sign_in)

        email_text.onFocusChangeListener =
            View.OnFocusChangeListener { view, hasFocus ->
                if (!hasFocus) {
                    hideKeyboard(view)
                }
            }

        email_text.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s.toString().isEmpty() || s.toString().isBlank()) {
                    email_layout.error = getString(R.string.please_enter_email)
                } else if (!Patterns.EMAIL_ADDRESS.matcher(s.toString()).matches()) {
                    email_layout.error = getString(R.string.invalid_email)
                } else {
                    email_layout.error = null
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        password_text.onFocusChangeListener =
            View.OnFocusChangeListener { view, hasFocus ->
                if (!hasFocus) {
                    hideKeyboard(view)
                }
            }

        password_text.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s.toString().isEmpty() || s.toString().isBlank()) {
                    password_layout.error = getString(R.string.enter_password)
                } else if (s.toString().length < 8) {
                    password_layout.error = getString(R.string.password_minimum_limit)
                } else {
                    password_layout.error = null
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })


        login_btn.setOnClickListener {
            if (email_layout.error == null &&
                password_layout.error == null
            ) {
                val email = email_text.text.toString()
                val password = password_text.text.toString()
                presenter.signInWithEmailAndPassword(this, email, password)
            }
        }

        forgot_textView.setOnClickListener {
           // presenter.forgotPassword(this)
        }
    }

    override fun goToMainActivity() {
        startActivity(MainActivity.getIntent(this))
    }

    private fun hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun showErrorDialog(title: String, message: String) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(android.R.string.ok, null)
            .show()
    }
}