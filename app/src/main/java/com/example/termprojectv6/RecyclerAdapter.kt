package com.example.termprojectv6

import android.content.Context
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

class RecyclerAdapter(val entries : ArrayList<Entry>, private val onDeleteClickListener: EntryItemClickListener, context: Context) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    val days = context.resources.getStringArray(R.array.days)
    val months = context.resources.getStringArray(R.array.months)
    val dateFormatFull = context.resources.getString(R.string.date_format_full)
    val dateFormatMonthYear = context.resources.getString(R.string.date_format_month_year)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        var entry = entries.get(i)
        if (entry.day > 0) {
            viewHolder.itemDate.text = String.format(dateFormatFull, entry.year, months[entry.month], days[entry.day])
        } else {
            viewHolder.itemDate.text = String.format(dateFormatMonthYear, entry.year, months[entry.month])
        }
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
        var itemDelete: ImageView

        init {
            cardView = itemView.findViewById(R.id.cardView)
            itemDate = itemView.findViewById(R.id.itemDate)
            itemWeight = itemView.findViewById(R.id.itemAvgWeight)
            itemDelete = itemView.findViewById(R.id.itemDelete)
        }
    }
}