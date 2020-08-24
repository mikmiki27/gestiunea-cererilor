package com.example.gestiuneacererilor.ui.cereri.cereriStudent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gestiuneacererilor.R
import com.example.gestiuneacererilor.data.managers.cereremanager.CerereManagerImplementation
import com.example.gestiuneacererilor.data.restmanager.CerereService
import com.example.gestiuneacererilor.data.restmanager.data.Cerere
import com.example.gestiuneacererilor.ui.base.BaseFragment
import com.example.gestiuneacererilor.utils.Constants
import kotlinx.android.synthetic.main.fragment_cereri_student.*

class CereriStudentFragment() : BaseFragment<CereriStudentMvp.Presenter>(),
    CereriStudentMvp.View {

    private lateinit var recyclerViewForCererileMele: RecyclerView
    private lateinit var myCereriStudentAdapter: CereriStudentAdapter
    private var cerereList: List<Cerere> = arrayListOf()

    companion object {
        private var onRequestItemClicked: ((Any) -> Unit)? = null

        fun newInstance(onRequestItemClicked: (Any) -> Unit): CereriStudentFragment {
            this.onRequestItemClicked = onRequestItemClicked

            val fragment = CereriStudentFragment()
            val args = Bundle()
            fragment.arguments = args

            return fragment
        }
    }

    override fun initializePresenter(): CereriStudentMvp.Presenter {
        return CereriStudentPresenter(
            this,
            requireContext(),
            CerereManagerImplementation.getInstance(CerereService.create())
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cereri_student, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerViewForCererileMele = view.findViewById(R.id.recylerView_cererile_mele)

        presenter.getAllCerere()

        myCereriStudentAdapter = CereriStudentAdapter(requireContext(), cerereList) { item ->
            val cerereVeche = item as Cerere
            val cerereNoua = Cerere(
                id = cerereVeche.id,
                student_solicitant = cerereVeche.student_solicitant,
                id_student = cerereVeche.id_student,
                email_student_solicitat = cerereVeche.email_student_solicitat,
                facultate_student = cerereVeche.facultate_student,
                profesor_solicitat = cerereVeche.profesor_solicitat,
                id_profesor = cerereVeche.id_profesor,
                email_profesor_solicitat = cerereVeche.email_profesor_solicitat,
                tip_cerere = cerereVeche.tip_cerere,
                mentiuni = cerereVeche.mentiuni,
                status = Constants.StatusCerere.ANULATA.name,
                raspuns = cerereVeche.raspuns
            )
            presenter.anulareCerere(cerereNoua)
            Toast.makeText(context, "Cancel successful", Toast.LENGTH_SHORT).show()
        }
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        recyclerViewForCererileMele.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = myCereriStudentAdapter
            visibility = View.VISIBLE
        }
    }

    override fun showCererileMele(list: List<Cerere>) {
        myCereriStudentAdapter.apply {
            cerereList = list
            notifyDataSetChanged()
        }
    }

    override fun showPlaceholderForNoRequests() {
        placeholder_no_cereri.visibility = View.VISIBLE
    }

    override fun goToCereriFromCereri() {
        //Toast.makeText(context,view?.findNavController()?.currentDestination?.label,Toast.LENGTH_SHORT).show()
        view?.findNavController()?.navigate(R.id.action_menu_cereri_to_menu_cereri_stud)
    }
}