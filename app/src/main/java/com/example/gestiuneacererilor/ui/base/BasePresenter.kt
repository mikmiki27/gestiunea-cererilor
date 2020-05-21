package com.example.gestiuneacererilor.ui.base

import io.reactivex.disposables.CompositeDisposable

abstract class BasePresenter<T : BaseMvp.View>(protected var view: T?) : BaseMvp.Presenter {

    protected var subscription = CompositeDisposable()
    protected var subscriptionForIds = CompositeDisposable()

    override fun onDestroy() {
        subscription.clear()
        view = null
    }

    override fun clearSubscriptions() {
        subscription.clear()
    }

    override fun clearSubscriptionsForIds() {
        subscriptionForIds.clear()
    }
}