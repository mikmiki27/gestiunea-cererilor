package com.example.gestiuneacererilor.ui.cereri.formularCerere

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.gestiuneacererilor.R
import com.example.gestiuneacererilor.data.managers.cereremanager.CerereManagerImplementation
import com.example.gestiuneacererilor.data.restmanager.CerereService
import com.example.gestiuneacererilor.data.restmanager.data.Cerere
import com.example.gestiuneacererilor.data.restmanager.data.Professor
import com.example.gestiuneacererilor.ui.base.BaseActivity
import com.example.gestiuneacererilor.ui.base.BaseFragment
import com.example.gestiuneacererilor.utils.*
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_detalii_profesor.*
import java.util.*

class FormularCerereFragment : BaseFragment<FormularCerereMvp.Presenter>(),
    FormularCerereMvp.View, View.OnClickListener {

    var profesorSolicitat = Professor()

    override fun initializePresenter(): FormularCerereMvp.Presenter {
        return FormularCererePresenter(
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
        return inflater.inflate(R.layout.fragment_detalii_profesor, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (context as? BaseActivity<*>)?.supportActionBar?.title =
            requireContext().resources.getString(R.string.depunere_cerere)
        programare_sedinte_buton_send.setOnClickListener(this)
        profesorSolicitat = arguments?.get("profesor_solicitat") as Professor

        setViews(profesorSolicitat)
    }

    override fun onClick(view: View?) {
        if (view?.id == R.id.programare_sedinte_buton_send) {
            val cerereNoua = Cerere(
                student_solicitant = getStudentCurrentFullName((requireContext())),
                id_student = getStudentCurrentID(requireContext()),
                email_student_solicitat = FirebaseAuth.getInstance().currentUser?.email.toString(),
                facultate_student = getStudentCurrentFacultate(requireContext()),
                profesor_solicitat = String.format(
                    requireContext().resources.getString(R.string.template_for_two),
                    profesorSolicitat.nume,
                    profesorSolicitat.prenume
                ),
                email_profesor_solicitat = profesorSolicitat.email,
                id_profesor = profesorSolicitat.id,
                status = Constants.StatusCerere.PROGRES.name,
                tip_cerere = if (getStudentCiclu(requireContext()).toLowerCase(Locale.getDefault()) == Constants.TipCiclu.LICENTA.name.toLowerCase()) {
                    Constants.TipCiclu.LICENTA.name
                } else {
                    Constants.TipCerere.DISERTATIE.name
                },
                mentiuni = programare_sedinte_motiv_text.text.toString()
            )
            presenter.postNewCerere(cerereNoua)
        }
    }

    private fun setViews(profesorSolicitat: Professor) {
        programare_sedinte_nume_profesor.visibility = View.VISIBLE
        programare_sedinte_email_prof.visibility = View.VISIBLE
        programare_sedinte_data.visibility = View.VISIBLE
        programare_sedinte_motiv.visibility = View.VISIBLE
        programare_sedinte_motiv_layout.visibility = View.VISIBLE
        programare_sedinte_buton_send.visibility = View.VISIBLE

        programare_sedinte_nume_profesor.text = String.format(
            requireContext().resources.getString(R.string.profesor_nume_disp), String.format(
                requireContext().resources.getString(R.string.template_for_two),
                profesorSolicitat.nume,
                profesorSolicitat.prenume
            )
        )
        programare_sedinte_email_prof.text = String.format(
            requireContext().resources.getString(R.string.email_disp),
            profesorSolicitat.email
        )
        if (getStudentCiclu(requireContext()).toLowerCase(Locale.getDefault()) == Constants.TipCiclu.LICENTA.name.toLowerCase()) {
            programare_sedinte_data.text = String.format(
                requireContext().resources.getString(R.string.other_requests),
                profesorSolicitat.cerinte_suplimentare_licenta
            )
        } else if (getStudentCiclu(requireContext()).toLowerCase(Locale.getDefault()) == Constants.TipCiclu.MASTER.name.toLowerCase()) {
            programare_sedinte_data.text = String.format(
                requireContext().resources.getString(R.string.other_requests),
                profesorSolicitat.cerinte_suplimentare_disertatie
            )
        }
    }

    override fun goToCererileMele() {
        view?.findNavController()?.navigate(R.id.action_menu_details_to_menu_cereri_stud)
    }

    override fun showPlaceholderForProfessorNetwork() {
        programare_sedinte_nume_profesor.visibility = View.INVISIBLE
        programare_sedinte_email_prof.visibility = View.INVISIBLE
        programare_sedinte_data.visibility = View.INVISIBLE
        programare_sedinte_motiv.visibility = View.INVISIBLE
        programare_sedinte_motiv_layout.visibility = View.INVISIBLE
        programare_sedinte_buton_send.visibility = View.INVISIBLE
        placeholderNetwork_formular.visibility = View.VISIBLE
    }

}