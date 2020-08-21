package com.example.gestiuneacererilor.ui.echipa

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gestiuneacererilor.R

class EchipaLicentaAdapter(
    private var context: Context,
    var studentiList: List<String?>
) : RecyclerView.Adapter<EchipaLicentaAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.field,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        if (holder is ItemViewHolder) {
            if (!studentiList.isNullOrEmpty()) {
                studentiList[position]?.let { item ->
                    holder.apply {
                        numeStudent.text = String.format(
                            context.resources.getString(R.string.nume_student_echipa),
                            (position + 1).toString(), item
                        )
                    }
                }
            }
        }
    }

    override fun getItemCount() = this.studentiList.size

    open class Holder(view: View) : RecyclerView.ViewHolder(view)

    class ItemViewHolder(view: View) : Holder(view) {
        var numeStudent: TextView = view.findViewById(R.id.student_nume)
    }
}