package com.example.termprojectv6

class Entry2 (var id : Int = 0, var date : String = "", var weight : Float = 0f, var year: Int, var month: Int, var day: Int)
    : Comparable<Entry2> {
    override fun compareTo(other: Entry2): Int {
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