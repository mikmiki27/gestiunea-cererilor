package com.example.gestiuneacererilor.ui.sedinte.programareSedinte

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.gestiuneacererilor.R
import kotlinx.android.synthetic.main.custom_date_picker.view.*
import kotlinx.android.synthetic.main.custom_time_picker.view.*
import kotlinx.android.synthetic.main.fragment_programare_sedinte.*

class ProgramareSedinteFragment : Fragment(), View.OnClickListener {

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
    }

    override fun onClick(p0: View?) {
       when(p0?.id) {
           R.id.calendar_data -> {
               val myDialogView = LayoutInflater.from(context)
                   .inflate(R.layout.custom_date_picker, null)

               lateinit var builder: AlertDialog.Builder
               builder = AlertDialog.Builder(context)
                   .setView(myDialogView)
                   .setTitle("Set date")
                   .setCancelable(true)
               val myAlertDialog = builder.show()

               myDialogView.buton_trimite.setOnClickListener {
                   val day = myDialogView.datepicker.dayOfMonth
                   val luna = myDialogView.datepicker.month
                   val year = myDialogView.datepicker.year
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
                   myAlertDialog.dismiss()
               }
           }
       }
    }
}