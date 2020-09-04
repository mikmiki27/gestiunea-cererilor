package com.example.gestiuneacererilor.ui.sedinte.programareSedinte

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.gestiuneacererilor.R
import com.example.gestiuneacererilor.data.managers.profesormanager.ProfesorManagerImplementation
import com.example.gestiuneacererilor.data.managers.sedintamanager.SedintaManagerImplementation
import com.example.gestiuneacererilor.data.managers.studentmanager.StudentManagerImplementation
import com.example.gestiuneacererilor.data.restmanager.ProfesorService
import com.example.gestiuneacererilor.data.restmanager.SedintaService
import com.example.gestiuneacererilor.data.restmanager.StudentService
import com.example.gestiuneacererilor.data.restmanager.data.Sedinta
import com.example.gestiuneacererilor.ui.base.BaseFragment
import com.example.gestiuneacererilor.utils.*
import kotlinx.android.synthetic.main.custom_date_picker.view.*
import kotlinx.android.synthetic.main.custom_time_picker.view.*
import kotlinx.android.synthetic.main.fragment_programare_sedinte.*

class ProgramareSedinteFragment :
    BaseFragment<ProgramareSedinteMvp.Presenter>(),
    ProgramareSedinteMvp.View, View.OnClickListener {

    override fun initializePresenter(): ProgramareSedinteMvp.Presenter {
        return ProgramareSedintePresenter(
            this,
            requireContext(),
            SedintaManagerImplementation.getInstance(SedintaService.create()),
            ProfesorManagerImplementation.getInstance(ProfesorService.create()),
            StudentManagerImplementation.getInstance(StudentService.create())
        )
    }

    //TODO CEL MAI MARE TODO EVER, PUNE ALTER TABLE PT DIACRITICE

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_programare_sedinte, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //ecran doar pt student

        calendar_data.setOnClickListener(this)
        ceas_ora_start.setOnClickListener(this)
        ceas_ora_finish.setOnClickListener(this)

        presenter.getProfesorCoordonatorByEmail(
            getCurrentStudentProfesorCoordonator(requireContext()),
            requireActivity()
        )
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.calendar_data -> {
                val myDialogView = LayoutInflater.from(context)
                    .inflate(R.layout.custom_date_picker, null)

                lateinit var builder: AlertDialog.Builder
                builder = AlertDialog.Builder(context)
                    .setView(myDialogView)
                    .setTitle(getString(R.string.set_date))
                    .setCancelable(true)
                val myAlertDialog = builder.show()

                myDialogView.buton_trimite.setOnClickListener {
                    val day = myDialogView.datepicker.dayOfMonth
                    val luna = myDialogView.datepicker.month
                    val year = myDialogView.datepicker.year
                    val stringDate = "$day-$luna-$year"
                    programare_sedinte_data.text = String.format(
                        requireContext().resources.getString(
                            (R.string.data_sedinta), stringDate
                        )
                    )
                    myAlertDialog.dismiss()
                }
            }
            R.id.ceas_ora_start -> {
                val myDialogView = LayoutInflater.from(context)
                    .inflate(R.layout.custom_time_picker, null)

                lateinit var builder: AlertDialog.Builder
                builder = AlertDialog.Builder(context)
                    .setView(myDialogView)
                    .setCancelable(true)
                val myAlertDialog = builder.show()

                myDialogView.buton_trimite_ceas.setOnClickListener {
                    val ora = myDialogView.timepicker.hour
                    val min = myDialogView.timepicker.minute
                    val stringHour = "$ora:$min"
                    programare_sedinte_ora_start.text = String.format(
                        requireContext().resources.getString(
                            (R.string.template_for_one), stringHour
                        )
                    )
                    myAlertDialog.dismiss()
                }
            }
            R.id.ceas_ora_finish -> {
                val myDialogView = LayoutInflater.from(context)
                    .inflate(R.layout.custom_time_picker, null)

                lateinit var builder: AlertDialog.Builder
                builder = AlertDialog.Builder(context)
                    .setView(myDialogView)
                    .setCancelable(true)
                val myAlertDialog = builder.show()

                myDialogView.buton_trimite_ceas.setOnClickListener {
                    val ora = myDialogView.timepicker.hour
                    val min = myDialogView.timepicker.minute
                    val stringHour = "$ora:$min"
                    programare_sedinte_ora_finish.text = String.format(
                        requireContext().resources.getString(
                            (R.string.template_for_one), stringHour
                        )
                    )
                    myAlertDialog.dismiss()
                }
            }

            R.id.buton_trimite -> {
                //todo create new sedinta
                presenter.enterNewSedinta(
                    Sedinta(
                        student_solicitant = getCurrentUserDisplayName(requireContext()),
                        id_student = getCurrentUserId(requireContext()),
                        email_student_solicitat = getCurrentUserEmail(requireContext()),
                        facultate_student = getCurrentUserFacultate(requireContext()),
                        an_student = getCurrentStudentAn(requireContext()),
                        profesor_solicitat = getCurrentStudentProfesorCoordonatorDisplayName(
                            requireContext()
                        ), //todo seteaza-l cumva, testeaza-l
                        email_profesor_solicitat = getCurrentStudentProfesorCoordonator(
                            requireContext()
                        ),
                        id_profesor = getCurrentStudentProfesorCoordonatorId(requireContext()),
                        status = Constants.StatusCerere.RESPINSA.name,
                        motiv = programare_sedinte_motiv_text.text.toString(),
                        data = programare_sedinte_data.text.toString(),
                        orai = programare_sedinte_ora_start.text.toString(),
                        orasf = programare_sedinte_ora_finish.text.toString()
                    )
                )
            }
        }
    }

    override fun goToCerileConfirmateAleStudentului() {
        view?.findNavController()
            ?.navigate(R.id.action_menu_programare_sedinta_to_confirmate_student) //todo test this
    }
}