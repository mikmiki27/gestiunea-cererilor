package com.example.gestiuneacererilor.ui.sedinte.sedintesolicitate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
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
import com.example.gestiuneacererilor.utils.Constants
import kotlinx.android.synthetic.main.custom_alert_dialog_profesor.view.*
import kotlinx.android.synthetic.main.fragment_sedinte_solicitate.*

class SedinteSolicitateFragment :
    BaseFragment<SedinteSolicitateMvp.Presenter>(),
    SedinteSolicitateMvp.View {

    private lateinit var recyclerViewForSedinte: RecyclerView
    private lateinit var mySedinteSolicitateAdapter: SedinteSolicitateAdapter
    private var sedinteList: List<Sedinta> = arrayListOf()

    companion object {
        private var onRequestItemClicked: ((Any) -> Unit)? = null

        fun newInstance(onRequestItemClicked: (Any) -> Unit): SedinteSolicitateFragment {
            this.onRequestItemClicked = onRequestItemClicked

            val fragment = SedinteSolicitateFragment()
            val args = Bundle()
            fragment.arguments = args

            return fragment
        }
    }

    override fun initializePresenter(): SedinteSolicitateMvp.Presenter {
        return SedinteSolicitatePresenter(
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
        return inflater.inflate(R.layout.fragment_sedinte_solicitate, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //ecran doar pt profesor
        recyclerViewForSedinte = view.findViewById(R.id.lista_sedinte_ecran_profesori)

        presenter.getAllSedinte()

        mySedinteSolicitateAdapter = SedinteSolicitateAdapter(
            requireContext(),
            sedinteList
        )
        { sedintaSelectata: Any ->
            val myDialogView = LayoutInflater.from(context)
                .inflate(R.layout.custom_alert_dialog_sedinta_de_confirmat, null)

            lateinit var builder: android.app.AlertDialog.Builder

            builder = android.app.AlertDialog.Builder(context)
                .setView(myDialogView)
                .setTitle(getString(R.string.atentie))
                .setCancelable(true)
            myDialogView.custom_alert_dialog_title.visibility = View.VISIBLE
            myDialogView.custom_alert_dialog_title.text =
                getString(R.string.doresti_sa_adaugi_sedinta)

            val myAlertDialog = builder.show()

            myDialogView.custom_alert_dialog_buttonCancel.setOnClickListener {
                myAlertDialog.dismiss()
            }
            myDialogView.custom_alert_dialog_buttonNo.setOnClickListener {
                myAlertDialog.dismiss()
                presenter.updateStatusSedintaToRespins(
                    Sedinta(
                        (sedintaSelectata as Sedinta).id,
                        sedintaSelectata.student_solicitant,
                        sedintaSelectata.id_student,
                        sedintaSelectata.email_student_solicitat,
                        sedintaSelectata.facultate_student,
                        sedintaSelectata.an_student,
                        sedintaSelectata.profesor_solicitat,
                        sedintaSelectata.email_profesor_solicitat,
                        sedintaSelectata.id_profesor,
                        Constants.StatusCerere.RESPINSA.name,
                        myDialogView.custom_alert_dialog_edittext.text.toString(),
                        sedintaSelectata.motiv,
                        sedintaSelectata.data,
                        sedintaSelectata.orai,
                        sedintaSelectata.orasf
                    )
                )
            }
            myDialogView.custom_alert_dialog_buttonYes.setOnClickListener {
                myAlertDialog.dismiss()
                presenter.updateStatusSedintaToAcceptat(
                    Sedinta(
                        (sedintaSelectata as Sedinta).id,
                        sedintaSelectata.student_solicitant,
                        sedintaSelectata.id_student,
                        sedintaSelectata.email_student_solicitat,
                        sedintaSelectata.facultate_student,
                        sedintaSelectata.an_student,
                        sedintaSelectata.profesor_solicitat,
                        sedintaSelectata.email_profesor_solicitat,
                        sedintaSelectata.id_profesor,
                        Constants.StatusCerere.ACCEPTATA.name,
                        myDialogView.custom_alert_dialog_edittext.text.toString(),
                        sedintaSelectata.motiv,
                        sedintaSelectata.data,
                        sedintaSelectata.orai,
                        sedintaSelectata.orasf
                    )
                )
            }
        }
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        recyclerViewForSedinte.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = mySedinteSolicitateAdapter
            visibility = View.VISIBLE
        }
    }

    override fun showListaSedinte(list: List<Sedinta>) {
        mySedinteSolicitateAdapter.apply {
            sedinteList = list
            notifyDataSetChanged()
        }
    }

    override fun showPlaceholderForSedinte() {
        placeholderEmpty_sedinte_solicitate.visibility = View.VISIBLE
    }

    override fun showPlaceholderForSedinteNetwork() {
        placeholderNetwork_sedinte_solicitate.visibility = View.VISIBLE
    }

    override fun goToSedinteConfirmate() {
        view?.findNavController()?.navigate(R.id.action_menu_sedinte_solicitate_to_confirmate)
    }
}