package com.example.gestiuneacererilor.ui.profile

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.gestiuneacererilor.R
import com.example.gestiuneacererilor.data.managers.authmanager.FirebaseAuthManagerImpl
import com.example.gestiuneacererilor.data.managers.profesormanager.ProfesorManager
import com.example.gestiuneacererilor.data.managers.studentmanager.StudentManager
import com.example.gestiuneacererilor.ui.base.BasePresenter
import com.example.gestiuneacererilor.utils.SharedPrefUtil
import com.example.gestiuneacererilor.utils.getProfesorCurrentID
import com.example.gestiuneacererilor.utils.getStudentCurrentID
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

    override fun getStudentByEmail(activity: Activity) {
        subscription.add(
            studentManager.getStudentByEmail(firebaseAuth.getCurrentUser()?.email!!.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    SharedPrefUtil.addKeyValue(
                        activity, SharedPrefUtil.STUDENT_CURRENT_ID, it[0].id
                            ?: ""
                    )
                    SharedPrefUtil.addKeyValue(
                        activity, SharedPrefUtil.STUDENT_CURRENT_NUME, it[0].nume
                            ?: ""
                    )
                    SharedPrefUtil.addKeyValue(
                        activity, SharedPrefUtil.STUDENT_CURRENT_PRENUME, it[0].prenume
                            ?: ""
                    )
                    SharedPrefUtil.addKeyValue(
                        activity, SharedPrefUtil.STUDENT_FULL_NAME, it[0].prenume + " " + it[0].nume
                            ?: ""
                    )
                    SharedPrefUtil.addKeyValue(
                        activity, SharedPrefUtil.STUDENT_CURRENT_EMAIL, it[0].email
                            ?: ""
                    )
                    SharedPrefUtil.addKeyValue(
                        activity, SharedPrefUtil.STUDENT_CURRENT_FACULTATE, it[0].facultate
                            ?: ""
                    )
                    SharedPrefUtil.addKeyValue(
                        activity, SharedPrefUtil.STUDENT_CICLU, it[0].ciclu
                            ?: ""
                    )
                    SharedPrefUtil.addKeyValue(
                        activity, SharedPrefUtil.STUDENT_AN, it[0].an
                            ?: ""
                    )
                    SharedPrefUtil.addKeyValue(
                        activity, SharedPrefUtil.STUDENT_PROFESOR_COORDONATOR_EMAIL, it[0].profesor_coordonator
                            ?: ""
                    )
                    SharedPrefUtil.addKeyValue(
                        activity, SharedPrefUtil.STUDENT_PROFESOR_COORDONATOR_FULL_NAME, it[0].profesor_coordonator_full_name
                            ?: ""
                    )
                    SharedPrefUtil.addKeyValue(
                        activity, SharedPrefUtil.STUDENT_PROFESOR_COORDONATOR_ID, it[0].id_profesor_coordonator
                            ?: ""
                    )
                    SharedPrefUtil.addKeyValue(
                        activity, SharedPrefUtil.STUDENT_TITLU_LUCRARE, it[0].titlu_lucrare
                            ?: ""
                    )
                    when {
                        it.isEmpty() -> Toast.makeText(context, context.getString(R.string.no_student_in_db), Toast.LENGTH_SHORT).show()
                        else -> view?.setViewsForStudent(it[0])
                    }
                }, {
                    Log.d("problem", "could not get student by email")
                })
        )
    }

    override fun getProfessorByEmail(activity: Activity) {
        subscription.add(
            profesorManager.getProfesorByEmail(firebaseAuth.getCurrentUser()?.email!!.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                    SharedPrefUtil.addKeyValue(
                        activity, SharedPrefUtil.PROFESOR_CURRENT_ID, it[0].id
                            ?: ""
                    )
                    SharedPrefUtil.addKeyValue(
                        activity, SharedPrefUtil.PROFESOR_CURRENT_NUME, it[0].nume
                            ?: ""
                    )
                    SharedPrefUtil.addKeyValue(
                        activity, SharedPrefUtil.PROFESOR_CURRENT_PRENUME, it[0].prenume
                            ?: ""
                    )
                    SharedPrefUtil.addKeyValue(
                        activity, SharedPrefUtil.PROFESOR_FULL_NAME, it[0].prenume + " " + it[0].nume
                            ?: ""
                    )
                    SharedPrefUtil.addKeyValue(
                        activity, SharedPrefUtil.PROFESOR_CURRENT_EMAIL, it[0].email
                            ?: ""
                    )
                    SharedPrefUtil.addKeyValue(
                        activity, SharedPrefUtil.PROFESOR_CERINTE_LICENTA, it[0].cerinte_suplimentare_licenta
                            ?: ""
                    )
                    SharedPrefUtil.addKeyValue(
                        activity, SharedPrefUtil.PROFESOR_CERINTE_MASTER, it[0].cerinte_suplimentare_disertatie
                            ?: ""
                    )
                    SharedPrefUtil.addKeyValue(
                        activity, SharedPrefUtil.PROFESOR_CURRENT_FACULTATE, it[0].facultate
                            ?: ""
                    )
                    SharedPrefUtil.addKeyValue(
                        activity, SharedPrefUtil.PROFESOR_ECHIPA_LICENTA, it[0].nr_studenti_echipa_licenta
                            ?: ""
                    )
                    SharedPrefUtil.addKeyValue(
                        activity, SharedPrefUtil.PROFESOR_ECHIPA_MASTER, it[0].nr_studenti_echipa_disertatie
                            ?: ""
                    )
                    SharedPrefUtil.addKeyValue(
                        activity, SharedPrefUtil.PROFESOR_LICENTA_ACCEPTATI, it[0].studenti_licenta_acceptati
                            ?: ""
                    )
                    SharedPrefUtil.addKeyValue(
                        activity, SharedPrefUtil.PROFESOR_DISERTATIE_ACCEPTATI, it[0].studenti_disertatie_acceptati
                            ?: ""
                    )
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
        idUser = getStudentCurrentID(context)
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
        idUser = getProfesorCurrentID(context)
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
