package com.example.termprojectv6

import android.app.Activity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Entry (var id : Int = 0, var weight : Float = 0f, var date : Date) : Comparable<Entry> {
    override fun compareTo(other: Entry): Int {
        return date.compareTo(other.date)
    }
    companion object {
//        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Utils.getLocale(App.context as Activity))
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        fun getEntry(id: Int, weight: Float, date: String) : Entry {
            val date = dateFormat.parse(date) ?: Date()
            return Entry(id, weight, date)
        }
        fun setEntry(entry: Entry) : Triple<Int, Float, String> {
            val dateString = dateFormat.format(entry.date)
            return Triple(entry.id, entry.weight, dateString)
        }
    }
}