package com.example.gestiuneacererilor.ui.cereri

import android.app.Activity
import com.example.gestiuneacererilor.data.restmanager.data.Cerere
import com.example.gestiuneacererilor.data.restmanager.data.NewProfesorRequestBody
import com.example.gestiuneacererilor.ui.base.BaseMvp

interface CereriMvp {

    interface View : BaseMvp.View {
        fun filterListOfRequests(list: List<Cerere>): List<Cerere>
        fun showCereriDisponibileForProfesor(list: List<Cerere>)
        fun showPlaceholderForEmptylist()
        fun showPlaceholderForNetwork()
        fun hideViewsForTeams()
        fun showViewsForTeams(it: List<NewProfesorRequestBody>)
        fun goToEchipe()
        fun goToCereri()
    }

    interface Presenter : BaseMvp.Presenter {
        fun getAllCerereForProfesor(activity: Activity)
        fun getAllCerereForStudent()
        fun updateCerereToAccepted(cerereSelectata: Cerere)
        fun updateCerereToRespins(cerereSelectata: Cerere)
    }
}