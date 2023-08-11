package com.example.termprojectv6

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import java.util.Objects
import kotlin.reflect.typeOf

object Entries {
    val mon = arrayOf("","jan", "feb", "mar", "apr", "may", "jun",
        "jul", "aug", "sep", "oct", "nov", "dec")

    fun data(activity: Activity) : SharedPreferences {
        return activity.getSharedPreferences("data", Context.MODE_PRIVATE)
    }
    fun getEntryNum(activity: Activity) : Int {
        return data(activity).getInt("entryNum", 0)
    }
    fun getNextId(activity: Activity) : Int {
        return data(activity).getInt("nextId", 0)
    }
    fun getEntries(activity: Activity) : ArrayList<Entry> {
        val data = data(activity)
        val nextId = getNextId(activity)
        var entryNum = 0
        val entries : ArrayList<Entry> = ArrayList()
        for (i in 0 until nextId) {
            if (data.contains("id-$i")) {
                entries.add(Entry(data.getInt("id-$i", 0),
                    data.getString("date-$i", "null").toString(),
                    data.getFloat("weight-$i", 0f),
                    data.getInt("year-$i", 2023),
                    data.getInt("month-$i", 12),
                    data.getInt("day-$i", 0)))
                entryNum += 1
            }
        }
        data.edit().putInt("entryNum", entryNum).commit()
        return entries
    }
    fun getEntriesSortByNew(activity: Activity) : ArrayList<Entry> {
        var entries = getEntries(activity)
        entries.sort()
        entries.reverse()
        return entries
    }
    fun getEntriesSortByOld(activity: Activity) : ArrayList<Entry> {
        var entries = getEntries(activity)
        entries.sort()
        return entries
    }
    fun newEntry(activity: Activity, id: Int, date: String, weight: Float) : Entry? {
        var dateData : List<String> = date.split("-", " ", "/", ".", "_")
        var patternInt = 0
        val patterns = arrayOf("","YYYY-MM-DD", "YYYY-Mon-DD", "YYYY-DD-Mon", // for reference only,
            "Mon-DD-YYYY", "DD-Mon-YYYY", "DD-MM-YYYY",                     // don't delete it though
            "YYYY-MM", "YYYY-Mon", "Mon-YYYY", "MM-YYYY", "Mon-DD", "DD-Mon")
        if (dateData.size == 3) { // 1 through 6
            if (dateData[0].toIntOrNull() == null) { // 4
                patternInt = 4 // Mon-DD-YYYY
            } else if (dateData[1].toIntOrNull() == null) { // 2 or 5
                if (dateData[0].length == 4) { // 2
                    patternInt = 2 // YYYY-Mon-DD
                } else { // 5
                    patternInt = 5 // DD-Mon-YYYY
                }
            } else if (dateData[2].toIntOrNull() == null) { // 3
                patternInt = 3 // Mon-DD-YYYY
            } else { // 1 or 6
                if (dateData[0].length == 4) {
                    patternInt = 1 // YYYY-MM-DD
                } else {
                    patternInt = 6 // DD-MM-YYYY
                }
            }
        } else if (dateData.size == 2) { // 7 through 12
            if (dateData[0].toIntOrNull() == null) { // 9 or 11
                if (dateData[1].toInt() >= 1900) {
                    patternInt = 9 // Mon-YYYY
                } else {
                    patternInt = 11 // DD-Mon-2023
                }
            } else if (dateData[1].toIntOrNull() == null) { // 8 or 12
                if (dateData[0].toInt() >= 1900) {
                    patternInt = 8 // YYYY-Mon
                } else {
                    patternInt = 12 // DD-Mon-2023
                }
            } else if (dateData[0].toInt() >= 1900) { // 7
                patternInt = 7 // YYYY-MM
            } else { // 10
                patternInt = 10 // MM-YYYY
            }
        } else {
            patternInt = 0 // fail somehow idk
        }
        if (patternInt == 0) {
            throw IllegalStateException("An error occurred due an invalid date input")
        }
        var year = when (patternInt) {
            1, 2, 3, 7, 8 -> dateData[0].take(4).toInt()
            9, 10 -> dateData[1].take(4).toInt()
            4, 5, 6 ->  dateData[2].take(4).toInt()
            else -> 2023
        }
        var day = when (patternInt) {
            5, 6, 12 -> dateData[0].take(2).toInt()
            3, 4, 11 -> dateData[1].take(2).toInt()
            1, 2 ->  dateData[2].take(2).toInt()
            else -> 0
        }
        var month = when (patternInt) {
            10 -> dateData[0].take(2).toInt()
            1, 6, 7 -> dateData[1].take(2).toInt()
            4, 9, 11 -> processMonth(dateData[0])
            2, 5, 8, 12 -> processMonth(dateData[1])
            3 -> processMonth(dateData[2])
            else -> 0 // if it's failing t won't get this far anyway
        }
        // double-check that nothing went wrong before adding it to the model and storage
        if (day in 0..31 && month in 1 .. 12 && year in 1900 .. 2100) {
            Toast.makeText(activity, "ID: $id, Y: $year, M: $month, D: $day, W: $weight", Toast.LENGTH_SHORT).show()
            return Entry(id, date, weight, year, month, day)
        } else {
            Toast.makeText(activity, "Invalid date input", Toast.LENGTH_SHORT).show()
            return null
        }
    }
    fun processMonth(month: String) : Int {
        var month3 = month.take(3)
        var month4 = month.take(4)
        return when (month3.lowercase()) {
            "jan" -> 1
            "feb", "fév", "fev" -> 2
            "mar" -> 3
            "apr", "avr" -> 4
            "may", "mai" -> 5
            "jun" -> 6
            "jul" -> 7
            "aug", "aou", "aoû" -> 8
            "sep" -> 9
            "oct" -> 10
            "nov" -> 11
            "dec", "déc" -> 12
            "jui" -> {
                when (month4.lowercase()) {
                    "juin" -> 6
                    "juil" -> 7
                    else -> 7 // idk
                }
            }
            else -> 12
        }
    }
    fun createEntry(activity: Activity, entries : ArrayList<Entry>, date: String, weight: Float) : Boolean {
        val nextId = getNextId(activity)
        val newEntry = newEntry(activity, nextId, date, weight)
        if (newEntry != null) {
            entries.add(saveNewEntry(activity, newEntry))
            return true
        } else {
            return false
        }
    }
    fun saveNewEntry(activity: Activity, entry: Entry) : Entry {
        val data = data(activity)
        val entryNum = getEntryNum(activity)
        val nextId = getNextId(activity)
        data.edit().putInt("id-$nextId", entry.id).commit()
        data.edit().putString("date-$nextId", entry.date).commit()
        data.edit().putFloat("weight-$nextId", entry.weight).commit()
        data.edit().putInt("year-$nextId", entry.year).commit()
        data.edit().putInt("month-$nextId", entry.month).commit()
        data.edit().putInt("day-$nextId", entry.day).commit()
        data.edit().putInt("entryNum", entryNum + 1).commit()
        data.edit().putInt("nextId", nextId + 1).commit()
        return entry
    }
    fun sortEntries(activity: Activity) : ArrayList<Entry> {
        val data = data(activity)
        val entries = getEntries(activity)
        entries.sort()
        return entries
    }

    fun getByMonthYear(activity: Activity, month: Int, year: Int) : EntryGroup {
        val data = data(activity)
        val nextId = getNextId(activity)
        val entries : ArrayList<Entry> = ArrayList()
        for (i in 0 until nextId) {
            if (data.contains("id-$i")) {
                if (data.getInt("month-$i", 0) == month) {
                    if (data.getInt("year-$i", 0) == year) {
                        entries.add(Entry(data.getInt("id-$i", 0),
                            data.getString("date-$i", "n/a").toString(),
                            data.getFloat("weight-$i", 0f),
                            data.getInt("year-$i", 2023),
                            data.getInt("month-$i", 12),
                            data.getInt("day-$i", 0)))
                    }
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
        val data = activity.getSharedPreferences("data", Context.MODE_PRIVATE)
        data.edit().putString("displayPreference", displayPreference).commit()
    }
    fun getDisplayPreference(activity: Activity) : String {
        val data = activity.getSharedPreferences("data", Context.MODE_PRIVATE)
        return data.getString("displayPreference", "Monthly").toString()
    }
    fun setEnterDataSortBy(activity: Activity, sort: String) {
        val data = activity.getSharedPreferences("data", Context.MODE_PRIVATE)
        data.edit().putString("enterDataSortBy", sort).commit()
    }
    fun getEnterDataSortBy(activity: Activity) : String {
        val data = activity.getSharedPreferences("data", Context.MODE_PRIVATE)
        return data.getString("enterDataSortBy", "id").toString()
    }


    fun deleteAllEntries(activity: Activity) {
        val data = activity.getSharedPreferences("data", Context.MODE_PRIVATE)
        data.edit().clear().commit()
        data.edit().putInt("nextId", 0).commit()
        data.edit().putInt("entryNum", 0).commit()
        Toast.makeText(activity, "All Entries Deleted", Toast.LENGTH_SHORT).show()
    }


}