package com.example.gestiuneacererilor.data.managers.functionsmanager

import android.content.Context
import com.google.firebase.functions.FirebaseFunctions

class FirebaseFunctionsManagerImpl private constructor() : FirebaseFunctionsManager {

    var mFunctions: FirebaseFunctions? = null

    companion object {
        val TAG: String = FirebaseFunctionsManagerImpl.javaClass.simpleName

        private var instance: FirebaseFunctionsManagerImpl? = null
        fun getInstance(): FirebaseFunctionsManagerImpl {
            if (instance == null) {
                instance = FirebaseFunctionsManagerImpl()
            }
            return instance!!
        }
    }

    override fun initFirebaseFunctions(context: Context) {
        mFunctions = FirebaseFunctions.getInstance()
    }
}