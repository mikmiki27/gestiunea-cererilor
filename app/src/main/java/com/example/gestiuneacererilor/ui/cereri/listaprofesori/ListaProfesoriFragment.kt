package com.example.gestiuneacererilor.ui.cereri.listaprofesori

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gestiuneacererilor.R
import com.example.gestiuneacererilor.data.managers.cereremanager.CerereManagerImplementation
import com.example.gestiuneacererilor.data.managers.profesormanager.ProfesorManagerImplementation
import com.example.gestiuneacererilor.data.managers.studentmanager.StudentManagerImplementation
import com.example.gestiuneacererilor.data.restmanager.CerereService
import com.example.gestiuneacererilor.data.restmanager.ProfesorService
import com.example.gestiuneacererilor.data.restmanager.StudentService
import com.example.gestiuneacererilor.data.restmanager.data.Cerere
import com.example.gestiuneacererilor.data.restmanager.data.NewProfesorRequestBody
import com.example.gestiuneacererilor.data.restmanager.data.Profesor
import com.example.gestiuneacererilor.ui.base.BaseFragment
import com.example.gestiuneacererilor.utils.Constants
import com.example.gestiuneacererilor.utils.getCurrentStudentCiclu
import kotlinx.android.synthetic.main.fragment_lista_profesori.*
import java.util.*

class ListaProfesoriFragment :
    BaseFragment<ListaProfesoriMvp.Presenter>(),
    ListaProfesoriMvp.View {

    private lateinit var recyclerViewForProfesoriDisponibili: RecyclerView
    private lateinit var myProfesoriDisponibiliAdapter: ListaProfesoriAdapter
    private var profesoriDispList: List<NewProfesorRequestBody> = arrayListOf()

    companion object {
        private var onRequestItemClicked: ((Any) -> Unit)? = null

        fun newInstance(onRequestItemClicked: (Any) -> Unit): ListaProfesoriFragment {
            this.onRequestItemClicked = onRequestItemClicked

            val fragment = ListaProfesoriFragment()
            val args = Bundle()
            fragment.arguments = args

            return fragment
        }
    }

    override fun initializePresenter(): ListaProfesoriMvp.Presenter {
        return ListaProfesoriPresenter(
            this,
            requireContext(),
            CerereManagerImplementation.getInstance(CerereService.create()),
            ProfesorManagerImplementation.getInstance(ProfesorService.create()),
            StudentManagerImplementation.getInstance(StudentService.create())
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_lista_profesori, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerViewForProfesoriDisponibili = view.findViewById(R.id.lista_profesori_disponibili)

        presenter.getStudentByEmail(requireActivity())
        presenter.getAllProfesoriDisponibili()

        myProfesoriDisponibiliAdapter = ListaProfesoriAdapter(
            requireContext(),
            profesoriDispList
        )
        {
            //todo go to creare cerere fragment
            Toast.makeText(context, "TEST PROF", Toast.LENGTH_SHORT).show()
        }
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        recyclerViewForProfesoriDisponibili.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = myProfesoriDisponibiliAdapter
            visibility = View.VISIBLE
        }
    }

    /* override fun onClick(view: View?) {
         if (view?.id == R.id.card_view_student) {
             fragmentManager?.beginTransaction()
                 ?.add(android.R.id.content, DetaliiProfesorFragment(null))
                 ?.commit()
         }
     }*/

    override fun showListaProfesoriDisponibili(list: List<NewProfesorRequestBody>) {
        val listaFiltrata = filterListOfProfDisp(list)
        myProfesoriDisponibiliAdapter.apply {
            profesoriDispList = listaFiltrata
            notifyDataSetChanged()
        }
    }

    private fun filterListOfProfDisp(list: List<NewProfesorRequestBody>): List<NewProfesorRequestBody> {
        val listaFiltrata = arrayListOf<NewProfesorRequestBody>()
        if (getCurrentStudentCiclu(requireContext()) == Constants.TipCiclu.LICENTA.name.toLowerCase(Locale.getDefault())) {
            for (profesor in list) {
                if (profesor.nr_studenti_echipa_licenta?.toInt()!! < 15) {
                    listaFiltrata.add(profesor)
                }
            }
        } else if (getCurrentStudentCiclu(requireContext()) == Constants.TipCiclu.MASTER.name.toLowerCase(Locale.getDefault())) {
            for (profesor in list) {
                if (profesor.nr_studenti_echipa_disertatie?.toInt()!! < 15) {
                    listaFiltrata.add(profesor)
                }
            }
        }

        return listaFiltrata
    }

    override fun showPlaceholderForProfessorNetwork() {
        placeholderNetwork_lista_profi_disp.visibility = View.VISIBLE
    }

    override fun showPlaceholderForProfessors() {
        placeholderEmpty_lista_profi_disp.visibility = View.VISIBLE
    }

    override fun showPlaceHolderForAlreadyGotProf(currentStudentProfesorCoordonator: String) {
        placeholderNetwork_already_got_prof.visibility = View.VISIBLE
        placeholderNetwork_already_got_prof.text = String.format(
            requireContext().resources.getString(R.string.already_got_prof),
            currentStudentProfesorCoordonator
        )
    }
}