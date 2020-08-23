package com.example.gestiuneacererilor.ui.cereri.listaprofesori

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.gestiuneacererilor.R
import com.example.gestiuneacererilor.data.restmanager.data.Professor
import com.example.gestiuneacererilor.utils.Constants
import com.example.gestiuneacererilor.utils.getCurrentStudentCiclu
import java.util.*

class ListaProfesoriAdapter(
    private var context: Context,
    var profesoriDispList: List<Professor?>,
    private var onRequestItemClicked: (Any) -> Unit
) : RecyclerView.Adapter<ListaProfesoriAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return ListaProfesoriAdapter.ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_profesor_disponibil,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        if (holder is ItemViewHolder) {
            if (!profesoriDispList.isNullOrEmpty()) {
                profesoriDispList[position]?.let { item ->
                    holder.apply {
                        fullNameProf.text = String.format(
                            context.resources.getString(
                                (R.string.profesor_nume_disp),
                                String.format(
                                    context.resources.getString(R.string.template_for_two),
                                    item.nume,
                                    item.prenume
                                )
                            )
                        )
                        emailProf.text = String.format(
                            context.resources.getString(R.string.email_disp),
                            item.email
                        )
                        if (getCurrentStudentCiclu(context) == Constants.TipCiclu.LICENTA.name.toLowerCase(
                                Locale.getDefault()
                            )
                        ) {
                            mentiuniProf.text = String.format(
                                context.resources.getString(R.string.mentiuni_prof_disp),
                                item.cerinte_suplimentare_licenta
                            )
                            echipaLocuriProf.text = String.format(
                                context.resources.getString(R.string.locuri_disponibile),
                                (15 - item.nr_studenti_echipa_licenta!!.toInt()).toString()
                            )
                        } else if (getCurrentStudentCiclu(context) == Constants.TipCiclu.MASTER.name.toLowerCase(
                                Locale.getDefault()
                            )
                        ) {
                            mentiuniProf.text = String.format(
                                context.resources.getString(R.string.mentiuni_prof_disp),
                                item.cerinte_suplimentare_disertatie
                            )
                            echipaLocuriProf.text = String.format(
                                context.resources.getString(R.string.locuri_disponibile),
                                (15 - item.nr_studenti_echipa_disertatie!!.toInt()).toString()
                            )
                        }

                        cardViewProfesor.setOnClickListener {
                            onRequestItemClicked.invoke(item)
                        }
                    }
                }
            }
        }
    }

    override fun getItemCount() = this.profesoriDispList.size

    open class Holder(view: View) : RecyclerView.ViewHolder(view)

    class ItemViewHolder(view: View) : Holder(view) {
        var fullNameProf: TextView = view.findViewById(R.id.profesor_disp_full_name)
        var emailProf: TextView = view.findViewById(R.id.profesor_disp_email)
        var mentiuniProf: TextView = view.findViewById(R.id.profesor_disp_mentiuni)
        var echipaLocuriProf: TextView = view.findViewById(R.id.profesor_disp_echipa)
        var cardViewProfesor: CardView = view.findViewById(R.id.card_view_profesori_disp)
    }
}