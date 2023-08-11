package com.example.termprojectv6

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import java.util.Objects

object Entries {
    val mon = arrayOf("","jan", "feb", "mar", "apr", "may", "jun",
        "jul", "aug", "sep", "oct", "nov", "dec")

    fun data(activity: Activity) : SharedPreferences {
        return activity.getSharedPreferences("data", Context.MODE_PRIVATE)
    }
    fun getEntryNum(activity: Activity) : Int {
        return data(activity).getInt("entries2", 0)
    }
    fun getNextId(activity: Activity) : Int {
        return data(activity).getInt("nextId", 0)
    }
    fun getEntries(activity: Activity) : ArrayList<Entry2> {
        val data = data(activity)
        val entryNum = getEntryNum(activity)
        val entries : ArrayList<Entry2> = ArrayList()
        for (i in 0 until entryNum) {
            entries.add(Entry2(data.getInt("id2-$i", 0),
                data.getString("date2-$i", "n/a").toString(),
                data.getFloat("weight2-$i", 0f),
                data.getInt("year2-$i", 2023),
                data.getInt("month2-$i", 1),
                data.getInt("day2-$i", 1)))
        }
        entries.sort()
        return entries
    }
    fun getEntriesReversed(activity: Activity) : ArrayList<Entry2> {
        val data = data(activity)
        val entryNum = getEntryNum(activity)
        val entries : ArrayList<Entry2> = ArrayList()
        for (i in 0 until entryNum) {
            entries.add(Entry2(data.getInt("id2-$i", 0),
                data.getString("date2-$i", "n/a").toString(),
                data.getFloat("weight2-$i", 0f),
                data.getInt("year2-$i", 2023),
                data.getInt("month2-$i", 1),
                data.getInt("day2-$i", 1)))
        }
        entries.reverse()
        return entries
    }
    fun newEntry(activity: Activity, id: Int, date: String, weight: Float) : Entry2 {
        var dateData : List<String> = date.split("-", " ", "/")
        var year : Int = 2023
        var month : Int = 1
        var day : Int = 1
        var patternInt = 0
        val patterns = arrayOf("","YYYY-MM-DD", "YYYY-Mon-DD", "YYYY-DD-Mon",
            "Mon-DD-YYYY", "DD-Mon-YYYY", "DD-MM-YYYY", "Mon-DD", "DD-Mon")
        if (dateData.size == 3) {
            if (dateData.get(0).toInt() > 1900) {
                if (dateData.get(1).length >= 3) { patternInt = 2 }
                else if (dateData.get(2).length >= 3) { patternInt = 3 }
                else { patternInt = 1 }
            } else {
                if (dateData.get(0).length >= 3) { patternInt = 4 }
                else if (dateData.get(1).length >= 3) { patternInt = 5 }
                else { patternInt = 6 }
            }
        } else {
            if (dateData.get(0).length >= 3) { patternInt = 7 }
            else if (dateData.get(1).length >= 3) { patternInt = 8 }
            else { patternInt = 9 } // fail somehow
        }
        if (patternInt.equals(1) || patternInt.equals(2) || patternInt.equals(3)) {
            year = dateData.get(0).take(4).toInt()
        } else if (patternInt.equals(4) || patternInt.equals(5) || patternInt.equals(6)) {
            year = dateData.get(2).take(4).toInt()
        }
        if (patternInt.equals(1) || patternInt.equals(6)) {
            month = dateData.get(1).take(2).toInt()
        }
        if (patternInt.equals(5) || patternInt.equals(6) || patternInt.equals(8)) {
            day = dateData.get(0).take(2).toInt()
        } else if (patternInt.equals(3) || patternInt.equals(4) || patternInt.equals(7)) {
            day = dateData.get(1).take(2).toInt()
        } else if (patternInt.equals(1) || patternInt.equals(2)) {
            day = dateData.get(2).take(2).toInt()
        }
        if (patternInt.equals(4) || patternInt.equals(7)) {
            month = processMonth(dateData.get(0))
        } else if (patternInt.equals(2) || patternInt.equals(5) || patternInt.equals(8)) {
            month = processMonth(dateData.get(1))
        } else if (patternInt.equals(3)) {
            month = processMonth(dateData.get(2))
        }
        Toast.makeText(activity, "ID: $id, Y: $year, M: $month, D: $day, W: $weight", Toast.LENGTH_SHORT).show()
        return Entry2(id, date, weight, year, month, day)
    }
    fun processMonth(month: String) : Int {
        return when (month.lowercase()) {
            "jan" -> 1
            "feb", "fév", "fev" -> 2
            "mar" -> 3
            "apr", "avr" -> 4
            "may", "mai" -> 5
            "jun", "juin" -> 6
            "jul", "juil" -> 7
            "aug", "aou", "aoû" -> 8
            "sep" -> 9
            "oct" -> 10
            "nov" -> 11
            "dec", "déc" -> 12
            else -> 12
        }
    }
    fun createEntry(activity: Activity, entries : ArrayList<Entry2>, date: String, weight: Float) : ArrayList<Entry2> {
        val entryNum = getEntryNum(activity)
        val nextId = getNextId(activity)
        entries.add(saveNewEntry(activity, newEntry(activity, nextId, date, weight)))
        return entries
    }
    fun saveNewEntry(activity: Activity, entry: Entry2) : Entry2 {
        val data = data(activity)
        val entryNum = getEntryNum(activity)
        val nextId = getNextId(activity)
        data.edit().putInt("id2-$nextId", entry.id).commit()
        data.edit().putString("date2-$nextId", entry.date).commit()
        data.edit().putFloat("weight2-$nextId", entry.weight).commit()
        data.edit().putInt("year2-$nextId", entry.year).commit()
        data.edit().putInt("month2-$nextId", entry.month).commit()
        data.edit().putInt("day2-$nextId", entry.day).commit()
        data.edit().putInt("entries2", nextId + 1).commit()
        data.edit().putInt("nextId", nextId + 1).commit()
        return entry
    }
    fun sortEntries(activity: Activity) : ArrayList<Entry2> {
        val data = data(activity)
        val entries = getEntries(activity)
        entries.sort()
        return entries
    }

    fun getByMonthYear(activity: Activity, month: Int, year: Int) : EntryGroup {
        val data = data(activity)
        val entryNum = getEntryNum(activity)
        val entries : ArrayList<Entry2> = ArrayList()
        for (i in 0 until entryNum) {
            if (data.getInt("month2-$i", 0) == month) {
                if (data.getInt("year2-$i", 0) == year) {
                    entries.add(Entry2(data.getInt("id2-$i", 0),
                        data.getString("date2-$i", "n/a").toString(),
                        data.getFloat("weight2-$i", 0f),
                        data.getInt("year2-$i", 2023),
                        data.getInt("month2-$i", 1),
                        data.getInt("day2-$i", 1)))
                }
            }
        }
        val entryGroup = EntryGroup(entries, month, year)
        return entryGroup
    }

    fun groupByMonth(activity: Activity) : ArrayList<EntryGroup> {
        // Returns an array list of array lists
        // hardcoding it for now to be 9 months starting from Dec 2022
        var ENTRIES = ArrayList<EntryGroup>()
        // dec 2022
        ENTRIES.add(getByMonthYear(activity, 12, 2022))
        ENTRIES.add(getByMonthYear(activity, 1, 2023))
        ENTRIES.add(getByMonthYear(activity, 2, 2023))
        ENTRIES.add(getByMonthYear(activity, 3, 2023))
        ENTRIES.add(getByMonthYear(activity, 4, 2023))
        ENTRIES.add(getByMonthYear(activity, 5, 2023))
        ENTRIES.add(getByMonthYear(activity, 6, 2023))
        ENTRIES.add(getByMonthYear(activity, 7, 2023))
        ENTRIES.add(getByMonthYear(activity, 8, 2023))
        return ENTRIES
    }

    fun setDisplayPreference(activity: Activity, displayPreference: String) {
        val data = data(activity)
        data.edit().putString("displayPreference", displayPreference).commit()
    }
    fun getDisplayPreference(activity: Activity) : String {
        val data = data(activity)
        return data.getString("displayPreference", "Monthly").toString()
    }

    fun deleteAllEntries(activity: Activity) {
        val data = activity.getSharedPreferences("data", Context.MODE_PRIVATE)
        data.edit().clear().commit()
        data.edit().putInt("nextId", 0).commit()
        data.edit().putInt("entryNum", 0).commit()
        Toast.makeText(activity, "All Entries Deleted", Toast.LENGTH_SHORT).show()
    }


}