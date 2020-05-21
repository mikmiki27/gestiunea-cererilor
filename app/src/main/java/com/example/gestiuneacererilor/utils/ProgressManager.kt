package com.example.gestiuneacererilor.utils

import android.app.Dialog
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView

import androidx.appcompat.app.AppCompatActivity
import com.example.gestiuneacererilor.R
import com.example.gestiuneacererilor.ui.base.BaseActivity

class ProgressManager(private val activity: AppCompatActivity) {

    fun showProgress() {
        activity.runOnUiThread {
            (activity as BaseActivity<*>).showProgress()
            activity.window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
        }
    }

    fun hideProgress() {
        activity.runOnUiThread {
            (activity as BaseActivity<*>).hideProgress()
            activity.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }
    }

    /*fun showAlert(title: String, msg: String) {
        activity.runOnUiThread {
            val dialog = Dialog(activity)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.custom_alert_dialog)

            val titleText = dialog.findViewById<View>(R.id.alert_title) as TextView
            titleText.text = title

            val msgText = dialog.findViewById<View>(R.id.alert_message) as TextView
            msgText.text = msg

            val dialogButton = dialog.findViewById<View>(R.id.alert_button) as Button
            dialogButton.setOnClickListener { dialog.dismiss() }

            dialog.show()
        }
    }*/
}
