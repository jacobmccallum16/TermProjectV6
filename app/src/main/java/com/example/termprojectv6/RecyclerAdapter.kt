package com.example.termprojectv6

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(val colorSchemeId : Int, val entries : ArrayList<Entry>) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.item_ID.text = entries[i].id.toString()
        viewHolder.item_Date.text = entries[i].date
        viewHolder.item_Weight.text = entries[i].weight.toString()
    }

    override fun getItemCount(): Int {
        return entries.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var card_view: CardView
        var item_ID: TextView
        var item_Date: TextView
        var item_Weight: TextView

        init {
            card_view = itemView.findViewById(R.id.card_view)
            item_ID = itemView.findViewById(R.id.item_ID)
            item_Date = itemView.findViewById(R.id.item_Date)
            item_Weight = itemView.findViewById(R.id.item_Weight)
        }
    }
}