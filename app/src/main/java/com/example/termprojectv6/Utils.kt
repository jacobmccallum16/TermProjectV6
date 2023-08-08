package com.example.termprojectv6

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

object Utils {
    val colorSchemes : Array<String> = arrayOf("Gray", "Blue", "Indigo", "Purple", "Pink",
        "Red", "Orange", "Yellow", "Green", "Teal", "Cyan")

    fun applyColorScheme(activity : Activity) {
        when (activity.getSharedPreferences("data", AppCompatActivity.MODE_PRIVATE).getInt("colorSchemeId", 1)) {
            0 -> { activity.setTheme(R.style.AppTheme_Gray) }
            1 -> { activity.setTheme(R.style.AppTheme_Blue) }
            2 -> { activity.setTheme(R.style.AppTheme_Indigo) }
            3 -> { activity.setTheme(R.style.AppTheme_Purple) }
            4 -> { activity.setTheme(R.style.AppTheme_Pink) }
            5 -> { activity.setTheme(R.style.AppTheme_Red) }
            6 -> { activity.setTheme(R.style.AppTheme_Orange) }
            7 -> { activity.setTheme(R.style.AppTheme_Yellow) }
            8 -> { activity.setTheme(R.style.AppTheme_Green) }
            9 -> { activity.setTheme(R.style.AppTheme_Teal) }
            10 -> { activity.setTheme(R.style.AppTheme_Cyan) }
        }
    }
    fun saveColorScheme(activity: Activity, colorSchemeId: Int) {
        activity.getSharedPreferences("data", AppCompatActivity.MODE_PRIVATE).edit().putInt("colorSchemeId", colorSchemeId).commit()
    }
    fun updateColorScheme(activity : Activity, colorSchemeId : Int) {
        saveColorScheme(activity, colorSchemeId)
        applyColorScheme(activity)
        recreateActivity(activity)
        Toast.makeText(activity, "Color scheme changed to: ${colorSchemes[colorSchemeId]}", Toast.LENGTH_SHORT).show()
    }
    fun randomizeColorScheme(activity : Activity) {
        val colorSchemeId : Int = Random.nextInt(0,11)
        saveColorScheme(activity, colorSchemeId)
        applyColorScheme(activity)
        recreateActivity(activity)
        Toast.makeText(activity, "Color scheme randomly set to: ${colorSchemes[colorSchemeId]}", Toast.LENGTH_SHORT).show()
    }

    fun recreateActivity(activity: Activity) {
        val intent = Intent(activity, activity::class.java)
        activity.startActivity(intent)
        activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        activity.finish()
    }

    fun getColorScheme(activity: Activity) : Int {
        val colorSchemeId = activity.getSharedPreferences("data", AppCompatActivity.MODE_PRIVATE).getInt("colorSchemeId", 1)
        return colorSchemeId
    }

    fun getBarChartColorStartId(activity: Activity) : Array<Int> {
        var colorSchemeId = getColorScheme(activity)
        var colorStartId = 0
        var colorEndId = 0
        when (colorSchemeId) {
            0 -> { activity.setTheme(R.style.AppTheme_Gray) }
            1 -> { activity.setTheme(R.style.AppTheme_Blue) }
            2 -> { activity.setTheme(R.style.AppTheme_Indigo) }
            3 -> { activity.setTheme(R.style.AppTheme_Purple) }
            4 -> { activity.setTheme(R.style.AppTheme_Pink) }
            5 -> { activity.setTheme(R.style.AppTheme_Red) }
            6 -> { activity.setTheme(R.style.AppTheme_Orange) }
            7 -> { activity.setTheme(R.style.AppTheme_Yellow) }
            8 -> { activity.setTheme(R.style.AppTheme_Green) }
            9 -> { activity.setTheme(R.style.AppTheme_Teal) }
            10 -> { activity.setTheme(R.style.AppTheme_Cyan) }
        }
        return arrayOf(0, 0)
    }

    fun getColor4(activity: Activity, color : Int = activity.getSharedPreferences("data", AppCompatActivity.MODE_PRIVATE)
        .getInt("colorSchemeId", 1)) : Int {
        return when (color) {
            1 -> activity.resources.getColor(R.color.blue4, activity.theme)
            2 -> activity.resources.getColor(R.color.indigo4, activity.theme)
            3 -> activity.resources.getColor(R.color.purple4, activity.theme)
            4 -> activity.resources.getColor(R.color.pink4, activity.theme)
            5 -> activity.resources.getColor(R.color.red4, activity.theme)
            6 -> activity.resources.getColor(R.color.orange4, activity.theme)
            7 -> activity.resources.getColor(R.color.yellow4, activity.theme)
            8 -> activity.resources.getColor(R.color.green4, activity.theme)
            9 -> activity.resources.getColor(R.color.teal4, activity.theme)
            10 -> activity.resources.getColor(R.color.cyan4, activity.theme)
            else -> activity.resources.getColor(R.color.gray4, activity.theme)
        }
    }
    fun getColor9(activity: Activity, color : Int = activity.getSharedPreferences("data", AppCompatActivity.MODE_PRIVATE)
        .getInt("colorSchemeId", 1)) : Int {
        return when (color) {
            1 -> activity.resources.getColor(R.color.blue9, activity.theme)
            2 -> activity.resources.getColor(R.color.indigo9, activity.theme)
            3 -> activity.resources.getColor(R.color.purple9, activity.theme)
            4 -> activity.resources.getColor(R.color.pink9, activity.theme)
            5 -> activity.resources.getColor(R.color.red9, activity.theme)
            6 -> activity.resources.getColor(R.color.orange9, activity.theme)
            7 -> activity.resources.getColor(R.color.yellow9, activity.theme)
            8 -> activity.resources.getColor(R.color.green9, activity.theme)
            9 -> activity.resources.getColor(R.color.teal9, activity.theme)
            10 -> activity.resources.getColor(R.color.cyan9, activity.theme)
            else -> activity.resources.getColor(R.color.gray9, activity.theme)
        }
    }
}