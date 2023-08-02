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
        if (colorSchemeId == 0) { viewHolder.card_view.setCardBackgroundColor(Color.parseColor("#6c757d")) }
        if (colorSchemeId == 1) { viewHolder.card_view.setCardBackgroundColor(Color.parseColor("#0a58ca")) }
        if (colorSchemeId == 2) { viewHolder.card_view.setCardBackgroundColor(Color.parseColor("#520dc2")) }
        if (colorSchemeId == 3) { viewHolder.card_view.setCardBackgroundColor(Color.parseColor("#59359a")) }
        if (colorSchemeId == 4) { viewHolder.card_view.setCardBackgroundColor(Color.parseColor("#ab296a")) }
        if (colorSchemeId == 5) { viewHolder.card_view.setCardBackgroundColor(Color.parseColor("#b02a37")) }
        if (colorSchemeId == 6) { viewHolder.card_view.setCardBackgroundColor(Color.parseColor("#ca6510")) }
        if (colorSchemeId == 7) { viewHolder.card_view.setCardBackgroundColor(Color.parseColor("#cc9a06")) }
        if (colorSchemeId == 8) { viewHolder.card_view.setCardBackgroundColor(Color.parseColor("#146c43")) }
        if (colorSchemeId == 9) { viewHolder.card_view.setCardBackgroundColor(Color.parseColor("#1aa179")) }
        if (colorSchemeId == 10) { viewHolder.card_view.setCardBackgroundColor(Color.parseColor("#0aa2c0")) }
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