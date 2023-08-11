package com.example.termprojectv6.Adapter

import android.content.Context
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

class EntryGroupRecyclerAdapter(val entryGroups : ArrayList<EntryGroup>, val locale: Locale, context: Context) : RecyclerView.Adapter<EntryGroupRecyclerAdapter.ViewHolder>() {

    val months = context.resources.getStringArray(R.array.months)
    val dateFormatMonthYear = context.resources.getString(R.string.date_format_month_year)
    val numOfEntries0 = context.resources.getString(R.string.num_of_entries_0)
    val numOfEntries1 = context.resources.getString(R.string.num_of_entries_1)
    val numOfEntriesPlural = context.resources.getString(R.string.num_of_entries_plural)
    val tvAvgWeight = context.resources.getString(R.string.tv_avg_weight)
    val tvMinMaxWeight = context.resources.getString(R.string.tv_min_max_weight)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_layout_monthly_data, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        val item = entryGroups.get(i)
        viewHolder.itemDate.text = String.format(dateFormatMonthYear, item.year, months[item.month])
        viewHolder.itemSize.text = when (item.size) {
            0 -> String.format(numOfEntries0, item.size)
            1 -> String.format(numOfEntries1, item.size)
            else -> String.format(numOfEntriesPlural, item.size)
        }
        viewHolder.itemAvgWeight.text = String.format(tvAvgWeight, item.avgWeight)
        viewHolder.itemMinMaxWeight.text = String.format(tvMinMaxWeight, item.minWeight, item.maxWeight)
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