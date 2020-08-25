package com.example.gestiuneacererilor.ui.echipa

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gestiuneacererilor.R
import com.example.gestiuneacererilor.data.managers.profesormanager.ProfesorManagerImplementation
import com.example.gestiuneacererilor.data.restmanager.ProfesorService
import com.example.gestiuneacererilor.ui.base.BaseActivity
import com.example.gestiuneacererilor.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_echipa.*

class EchipaFragment : BaseFragment<EchipaMvp.Presenter>(), EchipaMvp.View {

    private lateinit var recyclerViewLicenta: RecyclerView
    private lateinit var recyclerViewMaster: RecyclerView
    private lateinit var myEchipaLicentaAdapter: EchipaLicentaAdapter
    private lateinit var myEchipaMasterAdapter: EchipaMasterAdapter
    private var studentiLicenta: ArrayList<String> = arrayListOf()
    private var studentiMaster: ArrayList<String> = arrayListOf()
    private var listaCorectaStudentiLicenta = listOf<String>()
    private var listaCorectaStudentiMaster = listOf<String>()

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
        presenter.getProfesorByEmail()
        recyclerViewLicenta = view.findViewById(R.id.recycler_echipa_student_licenta)
        recyclerViewMaster = view.findViewById(R.id.recycler_echipa_student_master)

        myEchipaLicentaAdapter = EchipaLicentaAdapter(
            requireContext(),
            studentiLicenta
        )
        myEchipaMasterAdapter = EchipaMasterAdapter(
            requireContext(),
            studentiMaster
        )
        recyclerViewLicenta.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = myEchipaLicentaAdapter
            visibility = View.VISIBLE
        }
        recyclerViewMaster.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = myEchipaMasterAdapter
            visibility = View.VISIBLE
        }

    }

    override fun afisareStudentiLicenta(studentiLicentaAcceptati: String?) {
        val listaStudenti: List<String>? = studentiLicentaAcceptati?.split(", ")?.toList()
        listaCorectaStudentiLicenta = listaStudenti?.subList(0, listaStudenti.size - 1)!!
        if (listaCorectaStudentiLicenta != null && listaCorectaStudentiLicenta.isNotEmpty()) {
            myEchipaLicentaAdapter.apply {
                studentiList = listaCorectaStudentiLicenta
                notifyDataSetChanged()
            }
        }
    }

    override fun afisareStudentiMaster(studentiDisertatieAcceptati: String?) {
        val listaStudenti: List<String>? = studentiDisertatieAcceptati?.split(", ")?.toList()
        listaCorectaStudentiMaster = listaStudenti?.subList(0, listaStudenti.size - 1)!!
        if (listaCorectaStudentiMaster != null && listaCorectaStudentiMaster.isNotEmpty()) {
            myEchipaMasterAdapter.apply {
                studentiList = listaCorectaStudentiMaster
                notifyDataSetChanged()
            }
        }
    }
}