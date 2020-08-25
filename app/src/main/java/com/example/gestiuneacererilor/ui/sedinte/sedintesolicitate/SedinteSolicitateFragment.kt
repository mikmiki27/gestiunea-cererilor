package com.example.gestiuneacererilor.ui.sedinte.sedintesolicitate

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.gestiuneacererilor.R
import com.example.gestiuneacererilor.ui.sedinte.EventsViewPagerAdapter
import com.example.gestiuneacererilor.ui.sedinte.programareSedinte.ProgramareSedinteFragment
import com.example.gestiuneacererilor.ui.sedinte.sedinteConfirmate.SedinteConfirmateFragment
import com.example.gestiuneacererilor.utils.Constants
import com.example.gestiuneacererilor.utils.determineCurrentTypeUser
import com.example.gestiuneacererilor.utils.getCurrentUserEmail
import kotlinx.android.synthetic.main.fragment_cereri.*
import kotlinx.android.synthetic.main.fragment_sedinte_solicitate.*

class SedinteSolicitateFragment(/*private var onRequestItemClicked: OnRequestItemClicked*/) :
    Fragment(), View.OnClickListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sedinte_solicitate, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cardview1.setOnClickListener(this)
        //ecran doar pt profesor, trb sa scap de when-ul de mai jos
        when (determineCurrentTypeUser(getCurrentUserEmail(requireContext()))) {
            Constants.UserType.PROFESSOR -> {
                setViewsForProfessor()
            }
            Constants.UserType.STUDENT -> {
                setViewsForStudent()
            }
        }
    }

    private fun setViewsForProfessor() {
        sedinte_solicitate_student_nume.visibility = View.VISIBLE
        sedinte_solicitate_student_an_facultate.visibility = View.VISIBLE

        sedinte_solicitate_ora_ambii.visibility = View.VISIBLE
        sedinte_solicitate_data_ambii.visibility = View.VISIBLE
        sedinte_solicitate_email_ambii.visibility = View.VISIBLE

        sedinte_solicitate_profesor_facultate.visibility = View.INVISIBLE
        sedinte_solicitate_profesor_nume.visibility = View.INVISIBLE
    }

    private fun setViewsForStudent() {
        sedinte_solicitate_student_nume.visibility = View.INVISIBLE
        sedinte_solicitate_student_an_facultate.visibility = View.INVISIBLE

        sedinte_solicitate_ora_ambii.visibility = View.VISIBLE
        sedinte_solicitate_data_ambii.visibility = View.VISIBLE
        sedinte_solicitate_email_ambii.visibility = View.VISIBLE

        sedinte_solicitate_profesor_facultate.visibility = View.VISIBLE
        sedinte_solicitate_profesor_nume.visibility = View.VISIBLE
    }

    override fun onClick(view: View?) {
        if (view?.id == R.id.cardview1) {
            AlertDialog.Builder(context)
                .setTitle("Doresti sa adaugi sedinta in calendar?")
                .setCancelable(true)
                .setPositiveButton("DA", null)
                .setNegativeButton("NU", null)
                .setNeutralButton("CANCEL", null)
                .show()
        }
    }
}