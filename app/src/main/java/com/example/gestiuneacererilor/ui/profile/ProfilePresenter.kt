package com.example.gestiuneacererilor.ui.profile

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.gestiuneacererilor.R
import com.example.gestiuneacererilor.data.managers.authmanager.FirebaseAuthManagerImpl
import com.example.gestiuneacererilor.data.managers.profesormanager.ProfesorManager
import com.example.gestiuneacererilor.data.managers.studentmanager.StudentManager
import com.example.gestiuneacererilor.ui.base.BasePresenter
import com.example.gestiuneacererilor.utils.getCurrentUserId
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ProfilePresenter(
    view: ProfileMvp.View,
    val context: Context,
    private val firebaseAuth: FirebaseAuthManagerImpl,
    private val studentManager: StudentManager,
    private val profesorManager: ProfesorManager
) : BasePresenter<ProfileMvp.View>(view), ProfileMvp.Presenter {

    var idUser: String = ""

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
                        it.isEmpty() -> Toast.makeText(context, context.getString(R.string.no_student_in_db), Toast.LENGTH_SHORT).show()
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

    override fun updateStudent() {
        view?.showProgress()
        idUser = getCurrentUserId(context)
        val student = view?.getCurrentStudentDetails()!!
        subscription.add(
            studentManager.updateStudentById(idUser, student)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view?.setViewsForStudent(it)
                    view?.hideProgress()
                    Toast.makeText(context, context.resources.getString(R.string.update_succesful), Toast.LENGTH_SHORT).show()
                    view?.disableUpdateButton()
                }, {
                    Log.d("problem", "could not update student by id")
                    Toast.makeText(context, context.getString(R.string.update_failed), Toast.LENGTH_SHORT).show()
                    view?.hideProgress()
                    view?.disableUpdateButtonAndSetOldTextForStudent()
                })
        )
    }

    override fun updateProfesor() {
        view?.showProgress()
        idUser = getCurrentUserId(context)
        val profesor = view?.getCurrentProfesorDetails()!!
        subscription.add(
            profesorManager.updateProfesorById(idUser, profesor)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view?.setViewsForProfesor(it)
                    view?.hideProgress()
                    Toast.makeText(context, context.resources.getString(R.string.update_succesful), Toast.LENGTH_SHORT).show()
                    view?.disableUpdateButton()
                }, {
                    Log.d("problem", "could not update professor by id")
                    Toast.makeText(context, context.getString(R.string.update_failed), Toast.LENGTH_SHORT).show()
                    view?.hideProgress()
                    view?.disableUpdateButtonAndSetOldTextForStudent()
                })
        )
    }
}
