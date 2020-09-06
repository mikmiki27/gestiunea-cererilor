package com.example.gestiuneacererilor.data.managers.authmanager

import android.app.Activity
import android.content.Context
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface FirebaseAuthManager {
    fun initFirebaseAuth(context: Context)
    fun getCurrentUser(): FirebaseUser?
    fun createUserWithEmailAndPassword(email: String, password: String, activity: Activity): Observable<FirebaseUser>
    fun signInWithEmailAndPassword(email: String, password: String, activity: Activity): Observable<FirebaseUser>
    fun sendEmailVerif(activity: Activity): Observable<FirebaseUser>
}