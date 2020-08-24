package com.example.gestiuneacererilor.ui.cereri.listaprofesori

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.findNavController
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
import com.example.gestiuneacererilor.data.restmanager.data.Professor
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
    private var profesoriDispList: List<Professor> = arrayListOf()
    private var cererileStudentuluiCurrent: List<Cerere> = arrayListOf()

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

        presenter.getAllCereriForCurrentStudent()
        presenter.getAllProfesoriDisponibili()

        myProfesoriDisponibiliAdapter = ListaProfesoriAdapter(
            requireContext(),
            profesoriDispList
        )
        {
            //todo to test this
            var vizitat = false
            for (cerere in cererileStudentuluiCurrent) {
                if (cerere.status.toLowerCase(Locale.getDefault()) == Constants.StatusCerere.PROGRES.name.toLowerCase(Locale.getDefault())) {
                    val prof = (it as Professor)
                    AlertDialog.Builder(requireContext())
                        .setTitle("Warning!")
                        .setMessage("You cannot make another request, until your current one is closed.")
                        .setPositiveButton(android.R.string.ok, null)
                        .setCancelable(true)
                        .show()
                    vizitat = true
                    break
                }
            }
            if (!vizitat) {
                val bundle = Bundle()
                bundle.putSerializable("profesor_solicitat", (it as Professor))
                view.findNavController().navigate(R.id.action_menu_cereri_to_menu_details, bundle)
            }
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

    override fun showListaProfesoriDisponibili(list: List<Professor>) {
        val listaFiltrata = filterListOfProfDisp(list)
        if (isThereAnyAcceptedRequest()) { //todo test this
            myProfesoriDisponibiliAdapter.apply {
                profesoriDispList = emptyList()
                notifyDataSetChanged()
            }
        } else {
            myProfesoriDisponibiliAdapter.apply {
                profesoriDispList = listaFiltrata
                notifyDataSetChanged()
            }
        }
    }

    private fun isThereAnyAcceptedRequest(): Boolean {
        for (cerere in cererileStudentuluiCurrent) {
            if (cerere.status.toLowerCase(Locale.getDefault()) == Constants.StatusCerere.ACCEPTATA.name.toLowerCase(Locale.getDefault())) {
                return true
            }
        }
        return false
    }

    private fun filterListOfProfDisp(list: List<Professor>): List<Professor> {
        val listaFiltrata = arrayListOf<Professor>()
        if (getCurrentStudentCiclu(requireContext()).toLowerCase(Locale.getDefault()) == Constants.TipCiclu.LICENTA.name.toLowerCase(Locale.getDefault())) {
            for (profesor in list) {
                if (profesor.nr_studenti_echipa_licenta?.toInt()!! < 15) {
                    listaFiltrata.add(profesor)
                }
            }
        } else if (getCurrentStudentCiclu(requireContext()).toLowerCase(Locale.getDefault()) == Constants.TipCiclu.MASTER.name.toLowerCase(Locale.getDefault())) {
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

    override fun setListCereriStudentCurent(list: List<Cerere>): List<Cerere> {
        cererileStudentuluiCurrent = list
        return cererileStudentuluiCurrent
    }
}