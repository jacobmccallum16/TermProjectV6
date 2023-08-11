package com.example.termprojectv6

class Entry (var id : Int = 0, var date : String = "", var weight : Float = 0f,
             var year: Int = 2023, var month: Int = 12, var day: Int = 0)
    : Comparable<Entry> {
    override fun compareTo(other: Entry): Int {
        val yearComparison = year.compareTo(other.year)
        if (yearComparison != 0) {
            return yearComparison
        }
        val monthComparison = month.compareTo(other.month)
        if (monthComparison != 0) {
            return monthComparison
        }
        return day.compareTo(other.day)
    }
}