package com.example.gestiuneacererilor.ui.onboarding.registration

import android.app.Activity
import android.util.Log
import com.example.gestiuneacererilor.data.managers.authmanager.FirebaseAuthManager
import com.example.gestiuneacererilor.data.managers.profesormanager.ProfesorManager
import com.example.gestiuneacererilor.data.managers.studentmanager.StudentManager
import com.example.gestiuneacererilor.data.restmanager.data.NewProfesorRequestBody
import com.example.gestiuneacererilor.data.restmanager.data.NewStudentRequestBody
import com.example.gestiuneacererilor.data.restmanager.data.Profesor
import com.example.gestiuneacererilor.data.restmanager.data.Student
import com.example.gestiuneacererilor.ui.base.BasePresenter
import com.example.gestiuneacererilor.utils.SharedPrefUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.logging.Logger

class RegistrationActivityPresenter(
    view: RegistrationMvp.View,
    private val firebaseAuth: FirebaseAuthManager,
    private val studentManager: StudentManager,
    private val profesorManager: ProfesorManager
) : BasePresenter<RegistrationMvp.View>(view), RegistrationMvp.Presenter {

    override fun singUpUserToFirebase(email: String, password: String, activity: Activity) {
        view?.showProgress()
        subscription.add(
            firebaseAuth.createUserWithEmailAndPassword(email, password, activity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d("success", "firebase signup succeded")
                }, {
                    Log.d("problem", "firebase signup failed")
                })
        )
    }

    override fun singUpUser(student: Student, activity: Activity) {
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
                    SharedPrefUtil.addKeyValue(
                        activity, SharedPrefUtil.CURRENT_FIREBASE_USER_EMAIL, it.email
                            ?: ""
                    )
                    SharedPrefUtil.addKeyValue(
                        activity, SharedPrefUtil.CURRENT_FIREBASE_USER_NAME, it.prenume + " " + it.nume
                            ?: ""
                    )
                    SharedPrefUtil.addKeyValue(
                        activity, SharedPrefUtil.CURRENT_USER_NUME, it.nume
                            ?: ""
                    )
                    SharedPrefUtil.addKeyValue(
                        activity, SharedPrefUtil.CURRENT_USER_PRENUME, it.prenume
                            ?: ""
                    )
                    SharedPrefUtil.addKeyValue(
                        activity, SharedPrefUtil.CURRENT_USER_FACULTATE, it.facultate
                            ?: ""
                    )
                    SharedPrefUtil.addKeyValue(
                        activity, SharedPrefUtil.CURRENT_USER_AN, it.an
                            ?: ""
                    )
                    SharedPrefUtil.addKeyValue(
                        activity, SharedPrefUtil.CURRENT_USER_CICLU, it.ciclu
                            ?: ""
                    )
                    view?.hideProgress()
                    view?.goToMainActivity()
                }, {
                    Log.d("problem", "cloud9 insert failed for student")
                    view?.hideProgress()
                })
        )
    }

    override fun singUpUser(professor: Profesor, activity: Activity) {
        subscription.add(
            profesorManager.enterNewProfesor(NewProfesorRequestBody(
                professor.email,
                professor.nume,
                professor.prenume,
                professor.facultate,
                professor.cerinte_suplimentare_licenta ?: "",
                professor.cerinte_suplimentare_disertatie ?: "",
                professor.nr_studenti_echipa_licenta ?: "",
                professor.nr_studenti_echipa_disertatie ?: ""))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    SharedPrefUtil.addKeyValue(
                        activity, SharedPrefUtil.CURRENT_FIREBASE_USER_NAME, it.prenume + " " + it.nume
                            ?: ""
                    )
                    SharedPrefUtil.addKeyValue(
                        activity, SharedPrefUtil.CURRENT_USER_NUME, it.nume
                            ?: ""
                    )
                    SharedPrefUtil.addKeyValue(
                        activity, SharedPrefUtil.CURRENT_USER_PRENUME, it.prenume
                            ?: ""
                    )
                    SharedPrefUtil.addKeyValue(
                        activity, SharedPrefUtil.CURRENT_USER_FACULTATE, it.facultate
                            ?: ""
                    )
                    SharedPrefUtil.addKeyValue(
                        activity, SharedPrefUtil.CURRENT_FIREBASE_USER_EMAIL, it.email
                            ?: ""
                    )
                   /* SharedPrefUtil.addKeyValue(
                        activity, SharedPrefUtil.CURRENT_USER_CERINTE_LICENTA, it.cerinte_suplimentare_licenta
                            ?: ""
                    )
                    SharedPrefUtil.addKeyValue(
                        activity, SharedPrefUtil.CURRENT_USER_CERINTE_MASTER, it.cerinte_suplimentare_disertatie
                            ?: ""
                    )
                    SharedPrefUtil.addKeyValue(
                        activity, SharedPrefUtil.CURRENT_USER_ECHIPA_LICENTA, it.nr_studenti_echipa_licenta
                            ?: ""
                    )
                    SharedPrefUtil.addKeyValue(
                        activity, SharedPrefUtil.CURRENT_USER_ECHIPA_MASTER, it.nr_studenti_echipa_disertatie
                            ?: ""
                    )*/
                    view?.hideProgress()
                    view?.goToMainActivity()
                }, {
                    Log.d("problem", "cloud9 insert failed for professor")
                    view?.hideProgress()
                })
        )
    }
}