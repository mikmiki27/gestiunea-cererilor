package com.example.gestiuneacererilor.ui.onboarding.phone

import android.app.Activity
import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.gestiuneacererilor.R
import com.example.gestiuneacererilor.data.managers.authmanager.AuthManagerImpl
import com.example.gestiuneacererilor.data.managers.usermanager.UserManagerImpl
import com.example.gestiuneacererilor.data.restmanager.UserService
import com.example.gestiuneacererilor.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_phone.*

class PhoneActivity : BaseActivity<PhoneMvp.Presenter>(), PhoneMvp.View {

    override var presenter: PhoneMvp.Presenter = PhoneActivityPresenter(this,this,
        AuthManagerImpl.getInstance(),
        UserManagerImpl(UserService.create(), AuthManagerImpl.getInstance())
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        setContentView(R.layout.activity_phone)

        presenter.setName()

        phone_text.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
                if (!hasFocus) {
                    hideKeyboard(view)
                }
            }

       save_phone_button.setOnClickListener {
           presenter.savePhoneClicked(phone_text.text.toString())
       }


    }

    override fun setName(name: String) {
        val str = SpannableStringBuilder(getString(R.string.hi_user,name))
        str.setSpan(
            android.text.style.StyleSpan(android.graphics.Typeface.BOLD),
            3,
            getString(R.string.hi_user,name).lastIndex,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        hi_user_textView.text = str
    }

    override fun onBackPressed() {
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

    override fun setPhoneError(error: String?) {
        phone_layout.error = error
    }

    override fun finishSettingPhoneNo() {
        finish()
    }

}
