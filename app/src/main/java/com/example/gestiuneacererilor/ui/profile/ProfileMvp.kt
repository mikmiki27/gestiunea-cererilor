package com.example.gestiuneacererilor.ui.profile

import android.app.Activity
import com.example.gestiuneacererilor.data.restmanager.data.Student
import com.example.gestiuneacererilor.data.restmanager.data.Professor
import com.example.gestiuneacererilor.ui.base.BaseMvp

interface ProfileMvp {

    interface View : BaseMvp.View {
        fun goToMainActivity()
        fun setViewsForStudent(student: Student)
        fun setViewsForProfesor(profesor: Professor)
        fun getCurrentStudentDetails(): Student
        fun getCurrentProfesorDetails(): Professor
        fun disableUpdateButton()
        fun disableUpdateButtonAndSetOldTextForStudent()
        fun disableUpdateButtonAndSetOldTextForProfesor()
    }

    interface Presenter : BaseMvp.Presenter {
        fun singOut()
        fun updateStudent()
        fun updateProfesor()
        fun getStudentByEmail(activity: Activity)
        fun getProfessorByEmail(activity: Activity)
    }
}