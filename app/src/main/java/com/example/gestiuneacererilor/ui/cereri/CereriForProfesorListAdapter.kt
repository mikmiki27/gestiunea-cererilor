package com.example.gestiuneacererilor.ui.cereri

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.gestiuneacererilor.R
import com.example.gestiuneacererilor.data.restmanager.data.Cerere
import java.util.*

class CereriForProfesorListAdapter(
    private var context: Context,
    var requestsList: List<Cerere?>,
    private var onRequestItemClicked: (Any) -> Unit
) : RecyclerView.Adapter<CereriForProfesorListAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_cerere_disponibila_profesor,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        if (holder is ItemViewHolder) {
            if (!requestsList.isNullOrEmpty()) {
                requestsList[position]?.let { item ->
                    holder.apply {
                        numeStudent.text = String.format(context.resources.getString(R.string.nume), item.student_solicitant)
                        emailStudent.text = item.email_student_solicitat
                        status.text = item.status.toLowerCase(Locale.getDefault())
                        tipCerere.text = item.tip_cerere.toLowerCase(Locale.getDefault())
                        when {
                            item.mentiuni.isEmpty() -> mentiuni.text =
                                context.resources.getString(R.string.fara_mentiuni)
                            else -> mentiuni.text = item.mentiuni
                        }

                        cardViewCerere.setOnClickListener {
                            onRequestItemClicked.invoke(item)
                        }
                    }
                }
            }
        }
    }

    override fun getItemCount() = this.requestsList.size

    open class Holder(view: View) : RecyclerView.ViewHolder(view)

    class ItemViewHolder(view: View) : Holder(view) {
        var numeStudent: TextView = view.findViewById(R.id.student_name)
        var emailStudent: TextView = view.findViewById(R.id.student_email)
        var status: TextView = view.findViewById(R.id.status_cerere)
        var mentiuni: TextView = view.findViewById(R.id.mentiuni_student)
        var tipCerere: TextView = view.findViewById(R.id.tipcerere)
        var cardViewCerere: CardView = view.findViewById(R.id.card_view_cerere)
    }
}