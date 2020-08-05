package com.example.gestiuneacererilor.ui.onboarding.registration

import android.app.Activity
import com.example.gestiuneacererilor.data.managers.authmanager.FirebaseAuthManager
import com.example.gestiuneacererilor.data.managers.studentmanager.StudentManager
import com.example.gestiuneacererilor.data.restmanager.data.NewStudentRequestBody
import com.example.gestiuneacererilor.data.restmanager.data.Profesor
import com.example.gestiuneacererilor.data.restmanager.data.Student
import com.example.gestiuneacererilor.ui.base.BasePresenter
import com.example.gestiuneacererilor.utils.SharedPrefUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RegistrationActivityPresenter(
    view: RegistrationMvp.View,
    private val firebaseAuth: FirebaseAuthManager,
    private val studentManager: StudentManager
) : BasePresenter<RegistrationMvp.View>(view), RegistrationMvp.Presenter {

    override fun singUpUser(student: Student, password: String, activity: Activity) {
        view?.showProgress()

        subscription.add(
            firebaseAuth.createUserWithEmailAndPassword(student.email, password, activity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    SharedPrefUtil.addKeyValue(
                        activity, SharedPrefUtil.CURRENT_FIREBASE_USER_EMAIL, it.email
                            ?: ""
                    )
                    SharedPrefUtil.addKeyValue(
                        activity, SharedPrefUtil.CURRENT_FIREBASE_USER_NAME, it.displayName
                            ?: ""
                    )
                    view?.hideProgress()
                }, {
                    view?.hideProgress()
                })
        )

        subscription.add(
            studentManager.enterNewStudent(NewStudentRequestBody(
                    student.email,
                    student.nume,
                    student.prenume,
                    student.profesor_coordonator ?: "",
                    student.facultate,
                    student.an,
                    student.ciclu,
                    student.titlu_lucrare ?: ""))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view?.hideProgress()
                    view?.goToMainActivity()
                }, {
                    view?.hideProgress()
                })
        )
    }

    override fun singUpUser(professor: Profesor, password: String, activity: Activity) {
        TODO("Not yet implemented")
    }
}