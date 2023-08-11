package com.example.termprojectv6

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast

object Entries {
    fun data(activity: Activity) : SharedPreferences {
        return activity.getSharedPreferences("data", Context.MODE_PRIVATE)
    }
    fun getEntryNum(activity: Activity) : Int {
        return data(activity).getInt("entryNum", 0)
    }
    private fun getNextId(activity: Activity) : Int {
        return data(activity).getInt("nextId", 0)
    }
    @SuppressLint("ApplySharedPref")
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
        val entries = getEntries(activity)
        entries.sort()
        entries.reverse()
        return entries
    }
    fun getEntriesSortByOld(activity: Activity) : ArrayList<Entry> {
        val entries = getEntries(activity)
        entries.sort()
        return entries
    }
    fun newEntry(activity: Activity, id: Int, date: String, weight: Float) : Entry? {
        val dateData : List<String> = date.split("-", " ", "/", ".", "_")
        val patternInt: Int
//        val patterns = arrayOf("","YYYY-MM-DD", "YYYY-Mon-DD", "YYYY-DD-Mon", // for reference only,
//            "Mon-DD-YYYY", "DD-Mon-YYYY", "DD-MM-YYYY",                     // don't delete it though
//            "YYYY-MM", "YYYY-Mon", "Mon-YYYY", "MM-YYYY", "Mon-DD", "DD-Mon")
        if (dateData.size == 3) { // 1 through 6
            if (dateData[0].toIntOrNull() == null) { // 4
                patternInt = 4 // Mon-DD-YYYY
            } else if (dateData[1].toIntOrNull() == null) { // 2 or 5
                patternInt = if (dateData[0].length == 4) { // 2
                    2 // YYYY-Mon-DD
                } else { // 5
                    5 // DD-Mon-YYYY
                }
            } else if (dateData[2].toIntOrNull() == null) { // 3
                patternInt = 3 // Mon-DD-YYYY
            } else { // 1 or 6
                patternInt = if (dateData[0].length == 4) {
                    1 // YYYY-MM-DD
                } else {
                    6 // DD-MM-YYYY
                }
            }
        } else if (dateData.size == 2) { // 7 through 12
            if (dateData[0].toIntOrNull() == null) { // 9 or 11
                patternInt = if (dateData[1].toInt() >= 1900) {
                    9 // Mon-YYYY
                } else {
                    11 // DD-Mon-2023
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
        val year = when (patternInt) {
            1, 2, 3, 7, 8 -> dateData[0].take(4).toInt()
            9, 10 -> dateData[1].take(4).toInt()
            4, 5, 6 ->  dateData[2].take(4).toInt()
            else -> 2023
        }
        val day = when (patternInt) {
            5, 6, 12 -> dateData[0].take(2).toInt()
            3, 4, 11 -> dateData[1].take(2).toInt()
            1, 2 ->  dateData[2].take(2).toInt()
            else -> 0
        }
        val month = when (patternInt) {
            10 -> dateData[0].take(2).toInt()
            1, 6, 7 -> dateData[1].take(2).toInt()
            4, 9, 11 -> processMonth(dateData[0])
            2, 5, 8, 12 -> processMonth(dateData[1])
            3 -> processMonth(dateData[2])
            else -> 0 // if it's failing t won't get this far anyway
        }
        // double-check that nothing went wrong before adding it to the model and storage
        if (day in 0..31 && month in 1 .. 12 && year in 1900 .. 2100) {
            return Entry(id, date, weight, year, month, day)
        } else {
            return null
        }
    }
    private fun processMonth(month: String) : Int {
        val month3 = month.take(3)
        val month4 = month.take(4)
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
        return if (newEntry != null) {
            entries.add(saveNewEntry(activity, newEntry))
            true
        } else {
            false
        }
    }
    fun createEntry(activity: Activity, date: String, weight: Float) : Boolean {
        val nextId = getNextId(activity)
        val newEntry = newEntry(activity, nextId, date, weight)
        if (newEntry != null) {
            saveNewEntry(activity, newEntry)
            return true
        } else {
            return false
        }
    }
    @SuppressLint("ApplySharedPref")
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

    private fun getByMonthYear(activity: Activity, month: Int, year: Int): EntryGroup {
        val data = data(activity)
        val nextId = getNextId(activity)
        val entries: ArrayList<Entry> = ArrayList()
        for (i in 0 until nextId) {
            if (data.contains("id-$i")) {
                if (data.getInt("month-$i", 0) == month) {
                    if (data.getInt("year-$i", 0) == year) {
                        entries.add(
                            Entry(
                                data.getInt("id-$i", 0),
                                data.getString("date-$i", "n/a").toString(),
                                data.getFloat("weight-$i", 0f),
                                data.getInt("year-$i", 2023),
                                data.getInt("month-$i", 12),
                                data.getInt("day-$i", 0)
                            )
                        )
                    }
                }
            }
        }
        return EntryGroup(entries, month, year)
    }
    private fun getOldestMonth(activity: Activity) : Int {
        val entries = getEntriesSortByOld(activity)
        return entries[0].month
    }
    private fun getOldestYear(activity: Activity) : Int {
        val entries = getEntriesSortByOld(activity)
        return entries[0].year
    }
    private fun getNewestMonth(activity: Activity) : Int {
        val entries = getEntriesSortByNew(activity)
        return entries[0].month
    }
    private fun getNewestYear(activity: Activity) : Int {
        val entries = getEntriesSortByNew(activity)
        return entries[0].year
    }

    fun groupByMonth(activity: Activity) : ArrayList<EntryGroup> {
        // Returns an array list of array lists
        val oldestMonth = getOldestMonth(activity)
        val oldestYear = getOldestYear(activity)
        val newestMonth = getNewestMonth(activity)
        val newestYear = getNewestYear(activity)
        val entries = ArrayList<EntryGroup>()
        // dec 2022
        when (oldestYear) {
            newestYear -> {
                for (month in oldestMonth .. newestMonth) {
                    entries.add(getByMonthYear(activity, month, oldestYear))
                }
            }
            newestYear -1 -> {
                for (month in oldestMonth .. 12) {
                    entries.add(getByMonthYear(activity, month, oldestYear))
                }
                for (month in 1 .. newestMonth) {
                    entries.add(getByMonthYear(activity, month, newestYear))
                }
            }
            else -> {
                for (month in oldestMonth .. 12) {
                    entries.add(getByMonthYear(activity, month, oldestYear))
                }
                for (year in (oldestYear + 1) until newestYear) {
                    for (month in 1 .. 12) {
                        entries.add(getByMonthYear(activity, month, year))
                    }
                }
                for (month in 1 .. newestMonth) {
                    entries.add(getByMonthYear(activity, month, newestYear))
                }
            }
        }
        return entries
    }

    @SuppressLint("ApplySharedPref")
    fun setMainSortBy(activity: Activity, sort: String) {
        val data = activity.getSharedPreferences("data", Context.MODE_PRIVATE)
        data.edit().putString("mainSortBy", sort).commit()
    }
    fun getMainSortBy(activity: Activity) : String {
        val data = activity.getSharedPreferences("data", Context.MODE_PRIVATE)
        return data.getString("mainSortBy", "old").toString()
    }
    @SuppressLint("ApplySharedPref")
    fun setEnterDataSortBy(activity: Activity, sort: String) {
        val data = activity.getSharedPreferences("data", Context.MODE_PRIVATE)
        data.edit().putString("enterDataSortBy", sort).commit()
    }
    fun getEnterDataSortBy(activity: Activity) : String {
        val data = activity.getSharedPreferences("data", Context.MODE_PRIVATE)
        return data.getString("enterDataSortBy", "id").toString()
    }
    @SuppressLint("ApplySharedPref")
    fun setDisplayPreference(activity: Activity, displayPreference: String) {
        val data = activity.getSharedPreferences("data", Context.MODE_PRIVATE)
        data.edit().putString("displayPreference", displayPreference).commit()
    }
    fun getDisplayPreference(activity: Activity) : String {
        val data = activity.getSharedPreferences("data", Context.MODE_PRIVATE)
        return data.getString("displayPreference", "Monthly").toString()
    }


    @SuppressLint("ApplySharedPref")
    fun deleteAllEntries(activity: Activity) {
        val data = activity.getSharedPreferences("data", Context.MODE_PRIVATE)
        data.edit().clear().commit()
        data.edit().putInt("nextId", 0).commit()
        data.edit().putInt("entryNum", 0).commit()
        val alertDeleteAllPositiveToast = R.string.alertDeleteAllPositiveToast
        Toast.makeText(activity, alertDeleteAllPositiveToast, Toast.LENGTH_SHORT).show()
    }

    fun predictionSimple(entries: ArrayList<EntryGroup>) : ArrayList<EntryGroup> {
        val size = entries.size
        val duration = size - 1
        var time = duration
        val firstEntry = entries[1]
        val lastEntry = entries[duration]
        // find the simplest slope, (last - first) / (size - 1)
        val slopeMinWeight = (lastEntry.minWeight - firstEntry.minWeight) / (duration)
        val slopeAvgWeight = (lastEntry.avgWeight - firstEntry.avgWeight) / (duration)
        val slopeMaxWeight = (lastEntry.maxWeight - firstEntry.maxWeight) / (duration)
        // linear forecast

        // add 8 months
        for (i in 1 .. 8) {
            var predictedMonth : Int = lastEntry.month + i
            var predictedYear : Int = lastEntry.year
            while (predictedMonth > 12) {
                predictedMonth -= 12
                predictedYear += 1
            }
            val predictedMinWeight : Float = entries[time].minWeight + slopeMinWeight - 1
            val predictedAvgWeight : Float = entries[time].avgWeight + slopeAvgWeight
            val predictedMaxWeight : Float = entries[time].maxWeight + slopeMaxWeight + 1
            val prediction = EntryGroup(predictedMonth, predictedYear, predictedMinWeight, predictedAvgWeight, predictedMaxWeight)
            entries.add(prediction)
            time += 1
        }
        return entries
    }
    fun predictionComplex(entries: ArrayList<EntryGroup>) : ArrayList<EntryGroup> {
        val size = entries.size
        val duration = size - 1
        val lastEntry = entries[duration]
        // Since linear regression forecasts are a lot harder here than in excel, I'm simplifying it
        // Hopefully the math still half-checks out
        var minWeightOld = 0f
        var minWeightNew = 0f
        var avgWeightOld = 0f
        var avgWeightNew = 0f
        var maxWeightOld = 0f
        var maxWeightNew = 0f
        var divisor = 0
        var divisor2 = 0
        for (i in 0 .. duration) {
            divisor += (i + 1)
            divisor2 += (i * 2 + 1)
        }
        for (i in 0 .. duration) {
            minWeightOld += entries[i].minWeight * (duration - i + 1) / divisor
            minWeightNew += entries[i].minWeight * ((i * 2) + 1) / divisor2
            avgWeightOld += entries[i].avgWeight * (duration - i + 1) / divisor
            avgWeightNew += entries[i].avgWeight * ((i * 2) + 1) / divisor2
            maxWeightOld += entries[i].maxWeight * (duration - i + 1) / divisor
            maxWeightNew += entries[i].maxWeight * ((i * 2) + 1) / divisor2
        }
        val slopeMinWeight = (maxWeightNew - minWeightOld) / (duration)
        val slopeAvgWeight = (avgWeightNew - avgWeightOld) / (duration)
        val slopeMaxWeight = (minWeightNew - maxWeightOld) / (duration)
        // add 8 months
        var time = 1
        for (i in 1 .. 8) {
            var predictedMonth : Int = lastEntry.month + i
            var predictedYear : Int = lastEntry.year
            while (predictedMonth > 12) {
                predictedMonth -= 12
                predictedYear += 1
            }
            val predictedMinWeight : Float = entries[duration].minWeight + ((slopeMinWeight - 1) * time)
            val predictedAvgWeight : Float = entries[duration].avgWeight + (slopeAvgWeight * time)
            val predictedMaxWeight : Float = entries[duration].maxWeight + ((slopeMaxWeight + 1) * time)
            val prediction = EntryGroup(predictedMonth, predictedYear, predictedMinWeight, predictedAvgWeight, predictedMaxWeight)
            entries.add(prediction)
            time += 1
        }
        return entries
    }

}