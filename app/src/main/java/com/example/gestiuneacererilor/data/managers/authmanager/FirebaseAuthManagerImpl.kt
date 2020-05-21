package com.example.gestiuneacererilor.data.managers.authmanager

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import io.reactivex.Observable

class FirebaseAuthManagerImpl private constructor() : FirebaseAuthManager {

    var mAuth: FirebaseAuth? = null

    companion object {
        val TAG: String = FirebaseAuthManagerImpl.javaClass.simpleName

        private var instance: FirebaseAuthManagerImpl? = null
        fun getInstance(): FirebaseAuthManagerImpl {
            if (instance == null) {
                instance = FirebaseAuthManagerImpl()
            }
            return instance!!
        }
    }

    override fun initFirebaseAuth(context: Context) {
        mAuth = FirebaseAuth.getInstance()
    }

    override fun getCurrentUser(): FirebaseUser? {
        return mAuth?.currentUser
    }

    override fun createUserWithEmailAndPassword(email: String, password: String, activity: Activity): Observable<FirebaseUser> {
        return Observable.create<FirebaseUser> { emitter ->
            try {
                mAuth?.createUserWithEmailAndPassword(email, password)
                    ?.addOnCompleteListener(activity) { task ->
                        if (task.isSuccessful) {
                            mAuth?.currentUser?.let { emitter.onNext(it) }
                        } else {
                            emitter.tryOnError(task.exception!!)
                        }
                    }
            } catch (e: Exception) {
                emitter.onError(e)
            }
        }
    }

    override fun signInWithEmailAndPassword(email: String, password: String, activity: Activity): Observable<FirebaseUser> {
        return Observable.create<FirebaseUser> { emitter ->
            try {
                mAuth?.signInWithEmailAndPassword(email, password)
                    ?.addOnCompleteListener(activity) { task ->
                        if (task.isSuccessful) {
                            mAuth?.currentUser?.let { emitter.onNext(it) }
                            // Log.d(TAG, "signInWithEmail:success")
                            // val user = mAuth?.currentUser
                            //  Toast.makeText(activity, "Authentication succeeded.", Toast.LENGTH_SHORT).show()
                        } else {
                            emitter.tryOnError(task.exception!!)
                            //Log.w(TAG, "signInWithEmail:failure", task.exception)
                            //Toast.makeText(activity, "Authentication failed.", Toast.LENGTH_SHORT).show()
                        }
                    }
            } catch (e: Exception) {
                emitter.onError(e)
            }
        }
    }
}