package com.example.gestiuneacererilor.ui.cereri.formularCerere

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.gestiuneacererilor.R
import com.example.gestiuneacererilor.data.managers.cereremanager.CerereManagerImplementation
import com.example.gestiuneacererilor.data.managers.profesormanager.ProfesorManagerImplementation
import com.example.gestiuneacererilor.data.managers.studentmanager.StudentManagerImplementation
import com.example.gestiuneacererilor.data.restmanager.CerereService
import com.example.gestiuneacererilor.data.restmanager.ProfesorService
import com.example.gestiuneacererilor.data.restmanager.StudentService
import com.example.gestiuneacererilor.data.restmanager.data.Cerere
import com.example.gestiuneacererilor.data.restmanager.data.NewProfesorRequestBody
import com.example.gestiuneacererilor.ui.base.BaseActivity
import com.example.gestiuneacererilor.ui.base.BaseFragment
import com.example.gestiuneacererilor.ui.cereri.listaprofesori.ListaProfesoriMvp
import com.example.gestiuneacererilor.ui.cereri.listaprofesori.ListaProfesoriPresenter
import com.example.gestiuneacererilor.utils.Constants
import com.example.gestiuneacererilor.utils.getCurrentStudentCiclu
import com.example.gestiuneacererilor.utils.getCurrentUserDisplayName
import com.example.gestiuneacererilor.utils.getCurrentUserId
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_detalii_profesor.*

class FormularCerereFragment : BaseFragment<FormularCerereMvp.Presenter>(),
    FormularCerereMvp.View, View.OnClickListener {

    var profesorSolicitat = NewProfesorRequestBody()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detalii_profesor, container, false)
    }

    override fun initializePresenter(): FormularCerereMvp.Presenter {
        return FormularCererePresenter(
            this,
            requireContext(),
            CerereManagerImplementation.getInstance(CerereService.create())
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (context as? BaseActivity<*>)?.supportActionBar?.title =
            requireContext().resources.getString(R.string.depunere_cerere)
        formular_trimite_cerere.setOnClickListener(this)
        profesorSolicitat = arguments?.get("profesor_solicitat") as NewProfesorRequestBody

        setViews(profesorSolicitat)
    }

    override fun onClick(view: View?) {
        if (view?.id == R.id.formular_trimite_cerere) {
            val cerereNoua = Cerere(
                student_solicitant = FirebaseAuth.getInstance().currentUser?.displayName.toString(),
                id_student = getCurrentUserId(requireContext()),
                email_student_solicitat = FirebaseAuth.getInstance().currentUser?.email.toString(),
                profesor_solicitat = String.format(
                    requireContext().resources.getString(R.string.template_for_two),
                    profesorSolicitat.nume,
                    profesorSolicitat.prenume
                ),
                email_profesor_solicitat = profesorSolicitat.email,
                id_profesor = profesorSolicitat.id,
                status = Constants.StatusCerere.PROGRES.name,
                tip_cerere = if (getCurrentStudentCiclu(requireContext()) == Constants.TipCiclu.LICENTA.name.toLowerCase()) {
                    Constants.TipCiclu.LICENTA.name
                } else {
                    Constants.TipCerere.DISERTATIE.name
                },
                raspuns = "",
                mentiuni = formular_mentiuni_pentru_prof_text.text.toString()
            )
            presenter.postNewCerere(cerereNoua)
        }
    }

    private fun setViews(profesorSolicitat: NewProfesorRequestBody) {
        formular_profesor_nume.visibility = View.VISIBLE
        formular_email_prof.visibility = View.VISIBLE
        formular_cerinte_extra_prof.visibility = View.VISIBLE
        formular_mentiuni_pentru_prof.visibility = View.VISIBLE
        formular_mentiuni_pentru_prof_layout.visibility = View.VISIBLE
        formular_trimite_cerere.visibility = View.VISIBLE

        formular_profesor_nume.text = String.format(
            requireContext().resources.getString(R.string.profesor_nume_disp), String.format(
                requireContext().resources.getString(R.string.template_for_two),
                profesorSolicitat.nume,
                profesorSolicitat.prenume
            )
        )
        formular_email_prof.text = String.format(
            requireContext().resources.getString(R.string.email_disp),
            profesorSolicitat.email
        )
        if (getCurrentStudentCiclu(requireContext()) == Constants.TipCiclu.LICENTA.name.toLowerCase()) {
            formular_cerinte_extra_prof.text = String.format(
                requireContext().resources.getString(R.string.other_requests),
                profesorSolicitat.cerinte_suplimentare_licenta
            )
        } else if (getCurrentStudentCiclu(requireContext()) == Constants.TipCiclu.MASTER.name.toLowerCase()) {
            formular_cerinte_extra_prof.text = String.format(
                requireContext().resources.getString(R.string.other_requests),
                profesorSolicitat.cerinte_suplimentare_disertatie
            )
        }
    }

    override fun goToCererileMele() {
        view?.findNavController()?.navigate(R.id.action_menu_details_to_menu_cereri_stud)
    }

    override fun showPlaceholderForProfessorNetwork() {
        formular_profesor_nume.visibility = View.INVISIBLE
        formular_email_prof.visibility = View.INVISIBLE
        formular_cerinte_extra_prof.visibility = View.INVISIBLE
        formular_mentiuni_pentru_prof.visibility = View.INVISIBLE
        formular_mentiuni_pentru_prof_layout.visibility = View.INVISIBLE
        formular_trimite_cerere.visibility = View.INVISIBLE
        placeholderNetwork_formular.visibility = View.VISIBLE
    }

}