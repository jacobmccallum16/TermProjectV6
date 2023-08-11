package com.example.termprojectv6

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

interface EntryItemClickListener {
    fun onDeleteClick(entryId: Int)
}

class RecyclerAdapter(val entries : ArrayList<Entry>, private val onDeleteClickListener: EntryItemClickListener) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    val days = arrayOf("", "1st", "2nd", "3rd", "4th", "5th", "6th", "7th", "8th",
        "9th", "10th", "11th", "12th", "13th", "14th", "15th", "16th", "17th", "18th", "19th",
        "20th", "21st", "22nd", "23rd", "24th", "25th", "26th", "27th", "28th", "29th", "30th")
    val months = arrayOf("","January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        var entry = entries.get(i)
        viewHolder.itemDate.text = "[${entry.id}] ${months[entry.month]} ${days[entry.day]}, ${entry.year}"
        viewHolder.itemWeight.text = entries[i].weight.toString() + " lbs"
        viewHolder.itemDelete.setOnClickListener {
            onDeleteClickListener.onDeleteClick(entry.id)
        }
    }

    override fun getItemCount(): Int {
        return entries.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var cardView: CardView
        var itemDate: TextView
        var itemWeight: TextView
        var itemEdit: ImageView
        var itemDelete: ImageView

        init {
            cardView = itemView.findViewById(R.id.cardView)
            itemDate = itemView.findViewById(R.id.itemDate)
            itemWeight = itemView.findViewById(R.id.itemAvgWeight)
            itemEdit = itemView.findViewById(R.id.itemEdit)
            itemDelete = itemView.findViewById(R.id.itemDelete)
        }
    }
}