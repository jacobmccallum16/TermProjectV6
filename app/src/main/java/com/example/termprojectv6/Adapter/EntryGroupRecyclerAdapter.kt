package com.example.termprojectv6.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.termprojectv6.EntryGroup
import com.example.termprojectv6.R
import com.example.termprojectv6.RecyclerAdapter
import org.w3c.dom.Text
import java.util.Locale

class EntryGroupRecyclerAdapter(val entryGroups : ArrayList<EntryGroup>, val locale: Locale) : RecyclerView.Adapter<EntryGroupRecyclerAdapter.ViewHolder>() {

    val months = arrayOf("","January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December")
    val mois = arrayOf("","Janvier", "Février", "Mars", "Avril", "Mai", "Juin",
        "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_layout_monthly_data, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        val item = entryGroups.get(i)
        if (locale != Locale.FRENCH) {
            viewHolder.itemDate.text = "${months[item.month]} ${item.year}"
            if (item.size == 1) {
                viewHolder.itemSize.text = "${item.size} entry"
            } else {
                viewHolder.itemSize.text = "${item.size} entries"
            }
        } else {
            viewHolder.itemDate.text = "${mois[item.month]} ${item.year}"
            if (item.size == 1 || item.size == 0) {
                viewHolder.itemSize.text = "${item.size} saisie"
            } else {
                viewHolder.itemSize.text = "${item.size} saisies"
            }
        }
        viewHolder.itemAvgWeight.text = "${String.format("%.1f", item.avgWeight)} lbs"
        viewHolder.itemMinMaxWeight.text = "${String.format("%.1f", item.minWeight)} - ${String.format("%.1f", item.maxWeight)}"
    }

    override fun getItemCount(): Int {
        return entryGroups.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var cardView : CardView
        var itemDate : TextView
        var itemSize : TextView
        var itemAvgWeight : TextView
        var itemMinMaxWeight : TextView
        init {
            cardView = itemView.findViewById(R.id.cardView)
            itemDate = itemView.findViewById(R.id.itemDate)
            itemSize = itemView.findViewById(R.id.itemSize)
            itemAvgWeight = itemView.findViewById(R.id.itemAvgWeight)
            itemMinMaxWeight = itemView.findViewById(R.id.itemMinMaxWeight)
        }
    }
}