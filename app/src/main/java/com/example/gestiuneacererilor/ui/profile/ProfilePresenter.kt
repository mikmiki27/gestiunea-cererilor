package com.example.gestiuneacererilor.ui.profile

import android.content.Context
import android.util.Log
import com.example.gestiuneacererilor.data.managers.authmanager.FirebaseAuthManagerImpl
import com.example.gestiuneacererilor.data.managers.profesormanager.ProfesorManager
import com.example.gestiuneacererilor.data.managers.studentmanager.StudentManager
import com.example.gestiuneacererilor.data.restmanager.data.NewStudentRequestBody
import com.example.gestiuneacererilor.ui.base.BasePresenter
import com.example.gestiuneacererilor.utils.Constants
import com.example.gestiuneacererilor.utils.SharedPrefUtil
import com.example.gestiuneacererilor.utils.determineCurrentTypeUser
import com.google.firebase.auth.FirebaseUser
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ProfilePresenter(
    view: ProfileMvp.View,
    val context: Context,
    private val firebaseAuth: FirebaseAuthManagerImpl,
    private val studentManager: StudentManager,
    private val profesorManager: ProfesorManager
) : BasePresenter<ProfileMvp.View>(view), ProfileMvp.Presenter {

    override fun singOut() {
        firebaseAuth.mAuth?.signOut()
        view?.goToMainActivity()
    }

    override fun getStudentByEmail() {
        subscription.add(
            studentManager.getStudentByEmail(firebaseAuth.getCurrentUser()?.email!!.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    when {
                        it.isEmpty() -> singOut()
                        else -> view?.setViewsForStudent(it[0])
                    }
                }, {
                    Log.d("problem", "could not get student by email")
                })
        )
    }

    override fun getProfessorByEmail() {
        subscription.add(
            profesorManager.getProfesorByEmail(firebaseAuth.getCurrentUser()?.email!!.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    when {
                        it.isEmpty() -> singOut()
                        else -> view?.setViewsForProfesor(it[0])
                    }
                }, {
                    Log.d("problem", "could not get professor by email")
                })
        )
    }
}
