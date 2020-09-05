package com.example.gestiuneacererilor.ui.sedinte.sedinteConfirmate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gestiuneacererilor.R
import com.example.gestiuneacererilor.data.managers.profesormanager.ProfesorManagerImplementation
import com.example.gestiuneacererilor.data.managers.sedintamanager.SedintaManagerImplementation
import com.example.gestiuneacererilor.data.managers.studentmanager.StudentManagerImplementation
import com.example.gestiuneacererilor.data.restmanager.ProfesorService
import com.example.gestiuneacererilor.data.restmanager.SedintaService
import com.example.gestiuneacererilor.data.restmanager.StudentService
import com.example.gestiuneacererilor.data.restmanager.data.Sedinta
import com.example.gestiuneacererilor.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_sedinte_confirmate.*

class SedinteConfirmateFragment:
    BaseFragment<SedinteConfirmateMvp.Presenter>(),
    SedinteConfirmateMvp.View {

    private lateinit var recyclerViewForSedinteConfirmate: RecyclerView
    private lateinit var mySedinteConfirmateAdapter: SedinteConfirmateAdapter
    private var sedinteConfirmateList: List<Sedinta> = arrayListOf()

    override fun initializePresenter(): SedinteConfirmateMvp.Presenter {
        return SedinteConfirmatePresenter(
            this,
            requireContext(),
            SedintaManagerImplementation.getInstance(SedintaService.create()),
            ProfesorManagerImplementation.getInstance(ProfesorService.create()),
            StudentManagerImplementation.getInstance(StudentService.create())
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sedinte_confirmate, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerViewForSedinteConfirmate = view.findViewById(R.id.lista_sedinte_confirmate_ecran_profesori)

        presenter.getAllSedinteConfirmate()

        mySedinteConfirmateAdapter = SedinteConfirmateAdapter(
            requireContext(),
            sedinteConfirmateList
        )
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        recyclerViewForSedinteConfirmate.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = mySedinteConfirmateAdapter
            visibility = View.VISIBLE
        }
    }

    override fun showListaSedinteConfirmate(list: List<Sedinta>) {
        mySedinteConfirmateAdapter.apply {
            sedinteConfirmateList = list
            notifyDataSetChanged()
        }
    }

    override fun showPlaceholderForSedinteConfirmate() {
        placeholderEmpty_sedinte_confirmate.visibility = View.VISIBLE
    }

    override fun showPlaceholderForSedinteConfirmateNetwork() {
        placeholderNetwork_sedinte_confirmate.visibility = View.VISIBLE
    }

}