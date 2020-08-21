package com.example.gestiuneacererilor.ui.echipa

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.gestiuneacererilor.R
import com.example.gestiuneacererilor.data.managers.profesormanager.ProfesorManagerImplementation
import com.example.gestiuneacererilor.data.restmanager.ProfesorService
import com.example.gestiuneacererilor.ui.base.BaseActivity
import com.example.gestiuneacererilor.ui.base.BaseFragment


class EchipaFragment : BaseFragment<EchipaMvp.Presenter>(), EchipaMvp.View {

    private var parentLinearLayout: ConstraintLayout? = null
    override fun initializePresenter(): EchipaMvp.Presenter {
        return EchipaPresenter(
            this,
            requireContext(), ProfesorManagerImplementation.getInstance(ProfesorService.create())
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_echipa, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (context as? BaseActivity<*>)?.supportActionBar?.title = getString(R.string.menu_echipa)
        parentLinearLayout = view.findViewById(R.id.parent)
    }

    override fun afisareStudentiLicenta(studentiLicentaAcceptati: String?) {
        var listaStudenti: List<String>? = studentiLicentaAcceptati?.split(", ")?.toList()
        /*for(student in listaStudenti) {

        }*/
    }

    override fun afisareStudentiMaster(studentiDisertatieAcceptati: String?) {
        TODO("Not yet implemented")
    }
}