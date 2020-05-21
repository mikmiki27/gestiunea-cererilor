package com.example.gestiuneacererilor.ui.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

abstract class BaseFragment<T : BaseMvp.Presenter> : Fragment(), BaseMvp.View {

    lateinit var presenter: T

    abstract fun initializePresenter() : T

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.presenter = initializePresenter()
    }

    override fun onDestroyView() {
        presenter.onDestroy()
        super.onDestroyView()
    }

    override fun doIfHasInternetConnectivity(doAfter: () -> Unit, doOnError: (() -> Unit)?) {
        (context as? BaseActivity<*>)?.doIfHasInternetConnectivity(doAfter, doOnError)
    }

    override fun showProgress() {
        (context as? BaseActivity<*>)?.showProgress()
    }

    override fun hideProgress() {
        (context as? BaseActivity<*>)?.hideProgress()
    }


    override fun showGeneralError() {
        (context as? BaseActivity<*>)?.showGeneralError()
    }
}
