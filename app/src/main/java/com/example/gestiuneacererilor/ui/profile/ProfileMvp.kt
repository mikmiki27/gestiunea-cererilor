package com.example.gestiuneacererilor.ui.profile

import com.example.gestiuneacererilor.data.restmanager.data.NewProfesorRequestBody
import com.example.gestiuneacererilor.data.restmanager.data.NewStudentRequestBody
import com.example.gestiuneacererilor.data.restmanager.data.Profesor
import com.example.gestiuneacererilor.data.restmanager.data.Student
import com.example.gestiuneacererilor.ui.base.BaseMvp

interface ProfileMvp {

    interface View : BaseMvp.View {
        fun goToMainActivity()
        fun setViewsForStudent(student: NewStudentRequestBody)
        fun setViewsForProfesor(profesor: NewProfesorRequestBody)
        fun getCurrentStudentDetails(): Student
        fun getCurrentProfesorDetails(): Profesor
        fun disableUpdateButton()
        fun disableUpdateButtonAndSetOldTextForStudent()
        fun disableUpdateButtonAndSetOldTextForProfesor()
    }

    interface Presenter : BaseMvp.Presenter {
        fun singOut()
        fun updateStudent()
        fun updateProfesor()
        fun getStudentByEmail()
        fun getProfessorByEmail()
    }
}