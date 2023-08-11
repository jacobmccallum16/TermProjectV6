package com.example.termprojectv6

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class EntryGroupRecyclerAdapter(private val entryGroups : ArrayList<EntryGroup>, context: Context) : RecyclerView.Adapter<EntryGroupRecyclerAdapter.ViewHolder>() {

    private val months: Array<String> = context.resources.getStringArray(R.array.months)
    private val dateFormatMonthYear = context.resources.getString(R.string.date_format_month_year)
    private val numOfEntries0 = context.resources.getString(R.string.num_of_entries_0)
    private val numOfEntries1 = context.resources.getString(R.string.num_of_entries_1)
    private val numOfEntriesPlural = context.resources.getString(R.string.num_of_entries_plural)
    private val tvAvgWeight = context.resources.getString(R.string.tv_avg_weight)
    private val tvMinMaxWeight = context.resources.getString(R.string.tv_min_max_weight)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_layout_monthly_data, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        val item = entryGroups[i]
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
        private var cardView : CardView
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