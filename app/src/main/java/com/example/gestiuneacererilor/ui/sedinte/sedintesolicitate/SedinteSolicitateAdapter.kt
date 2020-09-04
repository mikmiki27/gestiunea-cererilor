package com.example.gestiuneacererilor.ui.sedinte.sedintesolicitate;

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.gestiuneacererilor.R
import com.example.gestiuneacererilor.data.restmanager.data.Sedinta

public class SedinteSolicitateAdapter(
    private var context: Context,
    var sedinteList: List<Sedinta?>,
    private var onRequestItemClicked: (Any) -> Unit
) : RecyclerView.Adapter<SedinteSolicitateAdapter.Holder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Holder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_sedinta_solicitata,
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: Holder, position: Int) {
        if (holder is ItemViewHolder) {
            if (!sedinteList.isNullOrEmpty()) {
                sedinteList[position]?.let { item ->
                    holder.apply {

                        fullNameStud.text = String.format(
                            context.resources.getString(
                                (R.string.student_nume_disp), item.student_solicitant)
                        )

                        emailStud.text = String.format(
                            context.resources.getString(
                                (R.string.email_disp), item.email_student_solicitat)
                        )

                        anFacultateStud.text = String.format(
                            context.resources.getString(
                                (R.string.an_facultate_disp), item.an_student, item.facultate_student)
                        )

                        dataStudent.text = String.format(
                            context.resources.getString(
                                (R.string.data_sedinta), item.data)
                        )

                        oraStudent.text = String.format(
                            context.resources.getString(
                                (R.string.ora_sedinta), item.orai, item.orasf)
                        )

                        cardViewSedintaStud.setOnClickListener {
                            onRequestItemClicked.invoke(item)
                        }
                    }
                }
            }
        }
    }

    override fun getItemCount() = this.sedinteList.size

    open class Holder(view: View) : RecyclerView.ViewHolder(view)

    class ItemViewHolder(view: View) : Holder(view) {
          var fullNameStud: TextView = view.findViewById(R.id.sedinte_solicitate_student_nume)
          var emailStud: TextView = view.findViewById(R.id.sedinte_solicitate_email_student)
          var anFacultateStud: TextView = view.findViewById(R.id.sedinte_solicitate_student_an_facultate)
          var dataStudent: TextView = view.findViewById(R.id.sedinte_solicitate_data_student)
          var oraStudent: TextView = view.findViewById(R.id.sedinte_solicitate_ora_student)
          var cardViewSedintaStud: CardView = view.findViewById(R.id.cardview_sedinta)

    }
}
