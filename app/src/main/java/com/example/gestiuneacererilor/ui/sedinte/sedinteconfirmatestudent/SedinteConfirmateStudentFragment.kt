package com.example.gestiuneacererilor.ui.sedinte.sedinteconfirmatestudent

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
import kotlinx.android.synthetic.main.fragment_sedinte_confirmate_student.*

class SedinteConfirmateStudentFragment :
    BaseFragment<SedinteConfirmateStudentMvp.Presenter>(),
    SedinteConfirmateStudentMvp.View {

    private lateinit var recyclerViewForSedinteConfirmateStudent: RecyclerView
    private lateinit var mySedinteConfirmateStudentAdapter: SedinteConfirmateStudentAdapter
    private var sedinteConfirmateStudentList: List<Sedinta> = arrayListOf()

    override fun initializePresenter(): SedinteConfirmateStudentMvp.Presenter {
        return SedinteConfirmateStudentPresenter(
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
        return inflater.inflate(R.layout.fragment_sedinte_confirmate_student, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerViewForSedinteConfirmateStudent =
            view.findViewById(R.id.lista_sedinte_confirmate_student)

        presenter.getCurrentStudentByEmail(requireActivity())
        presenter.getAllSedinteConfirmateStudent()

        mySedinteConfirmateStudentAdapter = SedinteConfirmateStudentAdapter(
            requireContext(),
            sedinteConfirmateStudentList
        )
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        recyclerViewForSedinteConfirmateStudent.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = mySedinteConfirmateStudentAdapter
            visibility = View.VISIBLE
        }
    }

    override fun showListaSedinteConfirmateStudent(list: List<Sedinta>) {
        mySedinteConfirmateStudentAdapter.apply {
            sedinteConfirmateStudentList = list
            notifyDataSetChanged()
        }
    }

    override fun showPlaceholderForSedinteConfirmateStudent() {
        placeholderEmpty_sedinte_confirmate_student.visibility = View.VISIBLE
    }

    override fun showPlaceholderForSedinteConfirmateNetworkStudent() {
        placeholderNetwork_sedinte_confirmate_student.visibility = View.VISIBLE
    }
}