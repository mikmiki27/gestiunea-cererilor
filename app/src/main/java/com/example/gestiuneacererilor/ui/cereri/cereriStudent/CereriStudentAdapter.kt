package com.example.gestiuneacererilor.ui.cereri.cereriStudent

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gestiuneacererilor.R
import com.example.gestiuneacererilor.data.restmanager.data.Cerere
import com.example.gestiuneacererilor.utils.Constants
import java.util.*

class CereriStudentAdapter(
    private var context: Context,
    var cerereList: List<Cerere?>,
    private var onRequestItemClicked: (Any) -> Unit
) : RecyclerView.Adapter<CereriStudentAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_cererile_mele,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        if (holder is ItemViewHolder) {
            if (!cerereList.isNullOrEmpty()) {
                cerereList[position]?.let { item ->
                    holder.apply {
                        numeProf.text = String.format(context.resources.getString((R.string.profesor_nume_disp),item.profesor_solicitat))
                        emailProf.text = String.format(context.resources.getString(R.string.email_disp), item.email_profesor_solicitat)
                        statusCerere.text = String.format(context.resources.getString(R.string.status), item.status)

                        if (item.raspuns.isNullOrEmpty())
                            raspunsProf.text = String.format(context.resources.getString(R.string.raspuns), context.resources.getString(R.string.noResponseYet))
                         else
                            raspunsProf.text = String.format(context.resources.getString(R.string.raspuns), item.raspuns)

                        if (item.status.toLowerCase(Locale.getDefault()) != Constants.StatusCerere.PROGRES.name.toLowerCase(Locale.getDefault())) {
                            anuleazaButton.visibility = View.GONE
                        } else {
                            anuleazaButton.setOnClickListener {
                            onRequestItemClicked.invoke(item)
                            }
                        }
                    }
                }
            }
        }
    }

    override fun getItemCount() = this.cerereList.size

    open class Holder(view: View) : RecyclerView.ViewHolder(view)

    class ItemViewHolder(view: View) : Holder(view) {
        var numeProf: TextView = view.findViewById(R.id.cerile_mele_nume_prof)
        var emailProf: TextView = view.findViewById(R.id.cerile_mele_email_prof)
        var statusCerere: TextView = view.findViewById(R.id.cerile_mele_status_prof)
        var raspunsProf: TextView = view.findViewById(R.id.cerile_mele_raspuns_prof)
        var anuleazaButton: TextView = view.findViewById(R.id.cerile_mele_anuleza_btn)
    }
}