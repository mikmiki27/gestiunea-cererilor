package com.example.gestiuneacererilor.ui.base

interface BaseMvp {

    interface View{
        fun showProgress()
        fun hideProgress()
        fun doIfHasInternetConnectivity(doAfter: () -> Unit, doOnError: (() -> Unit)? = null)
        fun showGeneralError()
    }

    interface Presenter{
        fun onDestroy()
        fun clearSubscriptions()
        fun clearSubscriptionsForIds()
    }
}