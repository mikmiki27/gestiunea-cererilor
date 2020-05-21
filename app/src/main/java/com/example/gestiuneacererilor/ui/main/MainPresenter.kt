package com.example.gestiuneacererilor.ui.main

import android.content.Context
import com.example.gestiuneacererilor.ui.base.BasePresenter

class MainPresenter(
    view: MainMvp.View,
    private val context: Context
) : BasePresenter<MainMvp.View>(view), MainMvp.Presenter