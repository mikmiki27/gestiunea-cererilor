package com.example.gestiuneacererilor.ui.cereri

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gestiuneacererilor.R
import com.example.gestiuneacererilor.data.restmanager.data.Cerere
import com.example.gestiuneacererilor.ui.sedinte.OnRequestItemClicked


class CereriForProfesorListAdapter(
    private var context: Context,
    private var onRequestItemClicked: OnRequestItemClicked
) : RecyclerView.Adapter<CereriForProfesorListAdapter.Holder>() {

    var requestsList /*ArrayList<Cerere?> */ = mutableListOf<Cerere>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        requestsList.add(
            Cerere(
                "1",
                "Miki Plestiu",
                "1",
                "plestiu@stud.ase.ro",
                "Ion Ion",
                "ionion@csie.ase.ro",
                "1",
                "in progres",
                "",
                ""
            )
        )
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
            requestsList[position]?.let { item ->

                holder.apply {
                    numeStudent.text = item.student_solicitant
                    /*helpOthersMessageTextView.text = item.description
                    helpOthersStreetTextView.text = item.location.address.toUpperCase()

                    helpOthersSubmitDateTextView.text = String.format(
                        context.getString(R.string.event_submitted_date),
                        convertLongToTime(item.date).toUpperCase()
                    )

                    val requestStatus = item.status
                    val requestStatusEnum = Status.getStatusBasedOnValue(item.status)
                    status.text = context.getString(requestStatusEnum.statusDisplayValue)
                    status.setTextColor(
                        ContextCompat.getColor(
                            context,
                            requestStatusEnum.statusColor
                        )
                    )
                    statusColor.setBackgroundResource(requestStatusEnum.statusColor)

                    helpOthersStatusTextView.text = context.resources.getString(
                        if (userType == UserType.HERO) Status.getStatusBasedOnValue(requestStatus).heroDisplayTest
                        else Status.getStatusBasedOnValue(requestStatus).requesterDisplayText
                    )

                    helpOthersStatusTextView.visibility =
                        if (requestStatusEnum == Status.PENDING) View.GONE else View.VISIBLE

                    when (requestStatus) {
                        context.getString(R.string.event_status_pending) -> {
                            displayedPerson.visibility = View.GONE
                        }
                        context.getString(R.string.event_status_approved) -> {
                            helpOthersStatusTextView.setPadding(0, 0, 0, 0)
                            displayedPerson.apply {
                                visibility = View.VISIBLE
                                name = if (userType == UserType.REQUESTER) item.hero?.name ?: ""
                                else item.requester?.name ?: ""
                                displayPleaseRate = false
                                rating =
                                    if (userType == UserType.REQUESTER) item.hero?.receivedRating
                                    else item.requester?.receivedRating
                            }
                        }
                        context.getString(R.string.event_status_finished) -> {
                            helpOthersStatusTextView.setPadding(0, 0, 0, 0)
                            displayedPerson.apply {
                                visibility = View.VISIBLE
                                name = if (userType == UserType.REQUESTER) item.hero?.name ?: ""
                                else item.requester?.name ?: ""
                                displayPleaseRate = true
                                rating =
                                    if (userType == UserType.REQUESTER) item.hero?.receivedRating
                                    else item.requester?.receivedRating
                            }
                        }
                    }

                    val inflater =
                        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

                    helpOthersChipGroup.removeAllViews()
                    item.tags.forEach { tag ->
                        val mChip = inflater.inflate(R.layout.event_chip_item, null, false) as Chip
                        mChip.text = tag
                        helpOthersChipGroup.addView(mChip)
                    }

                    cardView.setOnClickListener {
                        onRequestItemClicked?.invoke(item._id)
                    }*/
                }
            }
        }
    }

    override fun getItemCount() = requestsList.size

    open class Holder(view: View) : RecyclerView.ViewHolder(view)

    class ItemViewHolder(view: View) : Holder(view) {
        var numeStudent: TextView = view.findViewById(R.id.student_name)
        /* var statusColor: LinearLayout = view.findViewById(R.id.status_color)
         var helpOthersMessageTextView: TextView = view.findViewById(R.id.help_others_message)
         var helpOthersStreetTextView: TextView = view.findViewById(R.id.help_others_street)
         var helpOthersSubmitDateTextView: TextView = view.findViewById(R.id.help_others_submit_date)
         var helpOthersStatusTextView: TextView = view.findViewById(R.id.help_others_status)
         var displayedPerson: PersonLayout = view.findViewById(R.id.displayed_person)
         var helpOthersChipGroup: ChipGroup = view.findViewById(R.id.help_others_chipList)
         var cardView: MaterialCardView = view.findViewById(R.id.card_view)*/
    }
}