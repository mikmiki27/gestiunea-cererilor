package com.example.gestiuneacererilor.ui.base

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.gestiuneacererilor.utils.ConnectionLiveData
import com.example.gestiuneacererilor.utils.ProgressFragment
import com.example.gestiuneacererilor.utils.ProgressManager

abstract class BaseActivity<T : BaseMvp.Presenter> : AppCompatActivity(), BaseMvp.View {

    protected abstract var presenter: T
    var hasInternetConnection = false
    private var noInternetConnectionDialog: AlertDialog? = null
    private var progressBarFragment: ProgressFragment? = null
    var progressManager: ProgressManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val connectionLiveData = ConnectionLiveData(this)
        connectionLiveData.observe(this, Observer { isConnected ->
            isConnected?.let {
                hasInternetConnection = isConnected
            }
        })

        this.progressManager = ProgressManager(this)

        setUpProgressBar()
    }

    override fun doIfHasInternetConnectivity(doAfter: () -> Unit, doOnError: (() -> Unit)?) {
        if (!hasInternetConnection) {
            showActionNotAvailableInOfflineMode()
            doOnError?.invoke()
        } else {
            doAfter.invoke()
        }
    }

    private fun showActionNotAvailableInOfflineMode() {
        if (noInternetConnectionDialog == null) {
            noInternetConnectionDialog = AlertDialog.Builder(this)
                .setTitle("lalala")
                .setMessage("no internet")
                .setPositiveButton(android.R.string.ok, null)
                .show()
        }

        if (noInternetConnectionDialog?.isShowing == false) {
            noInternetConnectionDialog?.show()
        }
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    v.clearFocus()
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                    imm!!.hideSoftInputFromWindow(v.windowToken, 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }

    override fun showGeneralError() {
        AlertDialog.Builder(this)
            .setTitle("error")
            .setMessage("erroooor")
            .setCancelable(false)
            .setPositiveButton(android.R.string.ok, null)
            .show()
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    private fun setUpProgressBar() {
        progressBarFragment = ProgressFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .add(android.R.id.content, progressBarFragment!!)
            .commit()
    }

    override fun showProgress() {
        progressBarFragment?.showProgress()
    }

    override fun hideProgress() {
        progressBarFragment?.hideProgress()
    }
}