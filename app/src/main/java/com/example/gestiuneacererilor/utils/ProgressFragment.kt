package com.example.gestiuneacererilor.utils

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.Animation
import androidx.fragment.app.Fragment
import com.example.gestiuneacererilor.R
import kotlinx.android.synthetic.main.fragment_progress.*

class ProgressFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_progress, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val objectAnimator = ObjectAnimator.ofFloat(progress_imageView, View.ROTATION, 0f, 180f).setDuration(1500)
        objectAnimator.repeatCount = Animation.INFINITE
        objectAnimator.repeatMode = ValueAnimator.REVERSE
        objectAnimator.start()
    }

    fun showProgress() {
        activity?.let {
            it.runOnUiThread {
                container_progress.visibility = View.VISIBLE
                it.window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                progress_imageView.bringToFront()
            }
        }
    }

    fun hideProgress() {
        activity?.let {
            it.runOnUiThread {
                container_progress.visibility = View.GONE
                it.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            }
        }
    }

    companion object {
        fun newInstance() = ProgressFragment()
    }
}
