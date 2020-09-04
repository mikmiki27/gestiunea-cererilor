package com.example.gestiuneacererilor.ui.sedinte.sedinteConfirmate

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
import com.example.gestiuneacererilor.utils.Constants
import com.example.gestiuneacererilor.utils.createDate
import com.example.gestiuneacererilor.utils.isCurrentDateBiggerThatDateToCompare

class SedinteConfirmateAdapter(
    private var context: Context,
    var sedinteConfirmateList: List<Sedinta?>
) : RecyclerView.Adapter<SedinteConfirmateAdapter.Holder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Holder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_sedinta_confirmata,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        if (holder is ItemViewHolder) {
            if (!sedinteConfirmateList.isNullOrEmpty()) {
                sedinteConfirmateList[position]?.let { item ->
                    holder.apply {

                        fullNameStud.text = String.format(
                            context.resources.getString(
                                (R.string.student_nume_disp), item.student_solicitant
                            )
                        )

                        emailStud.text = String.format(
                            context.resources.getString(
                                (R.string.email_disp), item.email_student_solicitat
                            )
                        )

                        facultateStud.text = String.format(
                            context.resources.getString(
                                (R.string.an_facultate_disp),
                                item.an_student,
                                item.facultate_student
                            )
                        )

                        dataStud.text = String.format(
                            context.resources.getString(
                                (R.string.data_sedinta), item.data
                            )
                        )

                        oraStud.text = String.format(
                            context.resources.getString(
                                (R.string.ora_sedinta), item.orai, item.orasf
                            )
                        )

                        status.text = String.format(
                            context.resources.getString(
                                (R.string.status), item.status
                            )
                        )

                        raspuns.text = String.format(
                            context.resources.getString(
                                (R.string.raspuns), item.status
                            )
                        )

                        //todo test this
                        if (item.status == Constants.StatusSedinta.ACCEPTATA.name && isCurrentDateBiggerThatDateToCompare(
                                createDate(item.data, item.orai)
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

    override fun getItemCount() = this.sedinteConfirmateList.size

    open class Holder(view: View) : RecyclerView.ViewHolder(view)

    class ItemViewHolder(view: View) : Holder(view) {
        var fullNameStud: TextView = view.findViewById(R.id.sedinte_confirmate_student_nume)
        var emailStud: TextView = view.findViewById(R.id.sedinte_confirmate_email_student)
        var facultateStud: TextView =
            view.findViewById(R.id.sedinte_confirmate_student_an_facultate)
        var dataStud: TextView = view.findViewById(R.id.sedinte_confirmate_data_student)
        var oraStud: TextView = view.findViewById(R.id.sedinte_confirmate_ora_student)
        var status: TextView = view.findViewById(R.id.sedinte_confirmate_status)
        var raspuns: TextView = view.findViewById(R.id.sedinte_confirmate_raspuns)
        var container: ConstraintLayout = view.findViewById(R.id.constrlayout_sedinta_confirmata)
    }
}
