package com.example.gestiuneacererilor.ui.sedinte.sedinteconfirmatestudent

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.gestiuneacererilor.R
import com.example.gestiuneacererilor.data.restmanager.data.Sedinta
import com.example.gestiuneacererilor.utils.createDate
import com.example.gestiuneacererilor.utils.isCurrentDateBiggerThatDateToCompare

class SedinteConfirmateStudentAdapter(
    private var context: Context,
    var sedinteConfirmateStudentList: List<Sedinta?>
) : RecyclerView.Adapter<SedinteConfirmateStudentAdapter.Holder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Holder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_sedinta_confirmata_student,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        if (holder is ItemViewHolder) {
            if (!sedinteConfirmateStudentList.isNullOrEmpty()) {
                sedinteConfirmateStudentList[position]?.let { item ->
                    holder.apply {

                        fullNameProf.text = String.format(
                            context.resources.getString(
                                (R.string.student_nume_disp), item.profesor_solicitat
                            )
                        )

                        emailProf.text = String.format(
                            context.resources.getString(
                                (R.string.email_disp), item.email_profesor_solicitat
                            )
                        )

                        facultateProf.text = String.format(
                            context.resources.getString(
                                (R.string.an_facultate_disp),
                                item.an_student,
                                item.facultate_student
                            )
                        )

                        dataProf.text = String.format(
                            context.resources.getString(
                                (R.string.data_sedinta), item.data
                            )
                        )

                        oraProf.text = String.format(
                            context.resources.getString(
                                (R.string.ora_sedinta), item.orai, item.orasf
                            )
                        )

                        //todo test this
                        if (isCurrentDateBiggerThatDateToCompare(
                                createDate(
                                    item.data,
                                    item.orai
                                )
                            )
                        ) {
                            container.background =
                                ResourcesCompat.getDrawable(
                                    context.resources,
                                    R.drawable.grey_gradient_reverse,
                                    null
                                );
                        } else {
                            container.background =
                                ResourcesCompat.getDrawable(
                                    context.resources,
                                    R.drawable.green_reverse,
                                    null
                                );
                        }
                    }
                }
            }
        }
    }

    override fun getItemCount() = this.sedinteConfirmateStudentList.size

    open class Holder(view: View) : RecyclerView.ViewHolder(view)

    class ItemViewHolder(view: View) : Holder(view) {
        var fullNameProf: TextView = view.findViewById(R.id.sedinte_solicitate_prof_nume)
        var emailProf: TextView = view.findViewById(R.id.sedinte_solicitate_email_prof)
        var facultateProf: TextView = view.findViewById(R.id.sedinte_solicitate_prof_facultate)
        var dataProf: TextView = view.findViewById(R.id.sedinte_solicitate_data_prof)
        var oraProf: TextView = view.findViewById(R.id.sedinte_solicitate_ora_prof)
        var container: ConstraintLayout = view.findViewById(R.id.container)
    }
}