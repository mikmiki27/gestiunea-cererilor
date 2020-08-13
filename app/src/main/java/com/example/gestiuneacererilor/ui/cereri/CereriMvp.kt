package com.example.gestiuneacererilor.ui.cereri

import com.example.gestiuneacererilor.data.restmanager.data.Cerere
import com.example.gestiuneacererilor.ui.base.BaseMvp

interface CereriMvp {

    interface View : BaseMvp.View {
        fun filterListOfRequests(list: List<Cerere>): List<Cerere>
        fun showCereriDisponibileForProfesor(list: List<Cerere>)
        fun showPlaceholderForEmptylist()
        fun showPlaceholderForNetwork()
    }

    interface Presenter : BaseMvp.Presenter {
        fun getAllCerere()
    }
}