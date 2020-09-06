package com.example.gestiuneacererilor.ui.onboarding.registration

import android.app.Activity
import android.util.Log
import com.example.gestiuneacererilor.data.managers.authmanager.FirebaseAuthManager
import com.example.gestiuneacererilor.data.managers.profesormanager.ProfesorManager
import com.example.gestiuneacererilor.data.managers.studentmanager.StudentManager
import com.example.gestiuneacererilor.data.restmanager.data.Student
import com.example.gestiuneacererilor.data.restmanager.data.Professor
import com.example.gestiuneacererilor.ui.base.BasePresenter
import com.example.gestiuneacererilor.utils.SharedPrefUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

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
                    view?.goToMainActivity()
                }, {
                    Log.d("problem", "firebase signup failed")
                })
        )
    }

    override fun singUpUser(student: Student, activity: Activity) {
        subscription.add(
            studentManager.enterNewStudent(
                Student(
                    nume = student.nume,
                    prenume = student.prenume,
                    email = student.email,
                    facultate = student.facultate,
                    an = student.an,
                    ciclu = student.ciclu,
                    profesor_coordonator = student.profesor_coordonator,
                    profesor_coordonator_full_name = student.profesor_coordonator_full_name,
                    id_profesor_coordonator = student.id_profesor_coordonator,
                    titlu_lucrare = student.titlu_lucrare
                )
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    SharedPrefUtil.addKeyValue(
                        activity, SharedPrefUtil.STUDENT_CURRENT_ID, it.id
                            ?: ""
                    )
                    SharedPrefUtil.addKeyValue(
                        activity, SharedPrefUtil.STUDENT_CURRENT_NUME, it.nume
                            ?: ""
                    )
                    SharedPrefUtil.addKeyValue(
                        activity, SharedPrefUtil.STUDENT_CURRENT_PRENUME, it.prenume
                            ?: ""
                    )
                    SharedPrefUtil.addKeyValue(
                        activity, SharedPrefUtil.STUDENT_FULL_NAME, it.prenume + " " + it.nume
                            ?: ""
                    )
                    SharedPrefUtil.addKeyValue(
                        activity, SharedPrefUtil.STUDENT_CURRENT_EMAIL, it.email
                            ?: ""
                    )
                    SharedPrefUtil.addKeyValue(
                        activity, SharedPrefUtil.STUDENT_CURRENT_FACULTATE, it.facultate
                            ?: ""
                    )
                    SharedPrefUtil.addKeyValue(
                        activity, SharedPrefUtil.STUDENT_CICLU, it.ciclu
                            ?: ""
                    )
                    SharedPrefUtil.addKeyValue(
                        activity, SharedPrefUtil.STUDENT_AN, it.an
                            ?: ""
                    )
                    SharedPrefUtil.addKeyValue(
                        activity, SharedPrefUtil.STUDENT_PROFESOR_COORDONATOR_EMAIL, it.profesor_coordonator
                            ?: ""
                    )
                    SharedPrefUtil.addKeyValue(
                        activity, SharedPrefUtil.STUDENT_PROFESOR_COORDONATOR_FULL_NAME, it.profesor_coordonator_full_name
                            ?: ""
                    )
                    SharedPrefUtil.addKeyValue(
                        activity, SharedPrefUtil.STUDENT_PROFESOR_COORDONATOR_ID, it.id_profesor_coordonator
                            ?: ""
                    )
                    SharedPrefUtil.addKeyValue(
                        activity, SharedPrefUtil.STUDENT_TITLU_LUCRARE, it.titlu_lucrare
                            ?: ""
                    )
                    view?.hideProgress()
                }, {
                    Log.d("problem", "cloud9 insert failed for student")
                    view?.hideProgress()
                })
        )
    }

    override fun singUpUser(professor: Professor, activity: Activity) {
        subscription.add(
            profesorManager.enterNewProfesor(
                Professor(
                    nume = professor.nume,
                    prenume = professor.prenume,
                    email = professor.email,
                    cerinte_suplimentare_licenta = professor.cerinte_suplimentare_licenta,
                    cerinte_suplimentare_disertatie = professor.cerinte_suplimentare_disertatie,
                    facultate = professor.facultate,
                    nr_studenti_echipa_licenta = professor.nr_studenti_echipa_licenta,
                    nr_studenti_echipa_disertatie = professor.nr_studenti_echipa_disertatie,
                    studenti_licenta_acceptati = professor.studenti_licenta_acceptati,
                    studenti_disertatie_acceptati = professor.studenti_disertatie_acceptati
                )
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    SharedPrefUtil.addKeyValue(
                        activity, SharedPrefUtil.PROFESOR_CURRENT_ID, it.id
                            ?: ""
                    )
                    SharedPrefUtil.addKeyValue(
                        activity, SharedPrefUtil.PROFESOR_CURRENT_NUME, it.nume
                            ?: ""
                    )
                    SharedPrefUtil.addKeyValue(
                        activity, SharedPrefUtil.PROFESOR_CURRENT_PRENUME, it.prenume
                            ?: ""
                    )
                    SharedPrefUtil.addKeyValue(
                        activity, SharedPrefUtil.PROFESOR_FULL_NAME, it.prenume + " " + it.nume
                            ?: ""
                    )
                    SharedPrefUtil.addKeyValue(
                        activity, SharedPrefUtil.PROFESOR_CURRENT_EMAIL, it.email
                            ?: ""
                    )
                    SharedPrefUtil.addKeyValue(
                        activity, SharedPrefUtil.PROFESOR_CERINTE_LICENTA, it.cerinte_suplimentare_licenta
                            ?: ""
                    )
                    SharedPrefUtil.addKeyValue(
                        activity, SharedPrefUtil.PROFESOR_CERINTE_MASTER, it.cerinte_suplimentare_disertatie
                            ?: ""
                    )
                    SharedPrefUtil.addKeyValue(
                        activity, SharedPrefUtil.PROFESOR_CURRENT_FACULTATE, it.facultate
                            ?: ""
                    )
                    SharedPrefUtil.addKeyValue(
                        activity, SharedPrefUtil.PROFESOR_ECHIPA_LICENTA, it.nr_studenti_echipa_licenta
                            ?: ""
                    )
                    SharedPrefUtil.addKeyValue(
                        activity, SharedPrefUtil.PROFESOR_ECHIPA_MASTER, it.nr_studenti_echipa_disertatie
                            ?: ""
                    )
                    SharedPrefUtil.addKeyValue(
                        activity, SharedPrefUtil.PROFESOR_LICENTA_ACCEPTATI, it.studenti_licenta_acceptati
                            ?: ""
                    )
                    SharedPrefUtil.addKeyValue(
                        activity, SharedPrefUtil.PROFESOR_DISERTATIE_ACCEPTATI, it.studenti_disertatie_acceptati
                            ?: ""
                    )
                    view?.hideProgress()
                }, {
                    Log.d("problem", "cloud9 insert failed for professor")
                    view?.hideProgress()
                })
        )
    }
}