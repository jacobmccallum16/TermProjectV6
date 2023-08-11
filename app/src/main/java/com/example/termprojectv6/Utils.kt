package com.example.termprojectv6

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.util.TypedValue
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import java.util.Locale
import kotlin.random.Random

object Utils {
    val colorSchemes : Array<String> = arrayOf("Gray", "Blue", "Indigo", "Purple", "Pink",
        "Red", "Orange", "Yellow", "Green", "Teal", "Cyan")

    fun applyColorScheme(activity : Activity) {
        processNightMode(activity)
        processLanguage(activity)
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
        val currentColorSchemeId = getColorScheme(activity)
        var colorSchemeId= Random.nextInt(0,11)
        while (colorSchemeId == currentColorSchemeId) {
            colorSchemeId = Random.nextInt(0,11)
        }
        saveColorScheme(activity, colorSchemeId)
        applyColorScheme(activity)
        recreateActivity(activity)
        Toast.makeText(activity, "Color scheme randomly set to: ${colorSchemes[colorSchemeId]}", Toast.LENGTH_SHORT).show()
    }

    fun saveColorMode(activity: Activity, colorModeId: Int) {
        val data = activity.getSharedPreferences("data", Context.MODE_PRIVATE)
        data.edit().putInt("colorModeId", colorModeId).apply()
    }
    fun processNightMode(activity: Activity) {
        val data = activity.getSharedPreferences("data", Context.MODE_PRIVATE)
        when (data.getInt("colorModeId", 0)) {
            1 -> AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
            2 -> AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
            else -> AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }
    fun processLanguage(activity: Activity) {
        val data = activity.getSharedPreferences("data", Context.MODE_PRIVATE)
        val locale = when (data.getInt("languageId", 0)) {
            1 -> Locale.ENGLISH
            2 -> Locale.FRENCH
            else -> Locale.getDefault()
        }
        val config = Configuration()
        config.locale = locale
        activity.baseContext.resources.updateConfiguration(config, null)
    }
    fun getLocale(activity: Activity): Locale {
        val data = activity.getSharedPreferences("data", Context.MODE_PRIVATE)
        return when (data.getInt("languageId", 0)) {
            1 -> Locale.ENGLISH
            2 -> Locale.FRENCH
            else -> Locale.getDefault()
        }
    }
    fun saveLanguage(activity: Activity, languageId: Int) {
        val data = activity.getSharedPreferences("data", Context.MODE_PRIVATE)
        data.edit().putInt("languageId", languageId).apply()
    }
    fun updateSettings(activity: Activity, colorSchemeId: Int, colorModeId: Int, languageId: Int) {
        saveColorScheme(activity, colorSchemeId)
        saveColorMode(activity, colorModeId)
        saveLanguage(activity, languageId)
        recreateActivity(activity)
        Toast.makeText(activity, "Settings updated", Toast.LENGTH_SHORT).show()
    }


    fun recreateActivity(activity: Activity) {
        val intent = Intent(activity, activity::class.java)
        activity.startActivity(intent)
        activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        activity.finish()
    }


    fun openMain(activity: Activity) {
        if (activity::class.java != MainActivity::class.java) {
            val intent = Intent(activity, MainActivity::class.java)
            activity.startActivity(intent)
        } else {
            recreateActivity(activity)
        }
        Toast.makeText(activity, "Main", Toast.LENGTH_SHORT).show()
    }
    fun openEnterData(activity: Activity) {
        if (activity::class.java != EnterData::class.java) {
            val intent = Intent(activity, EnterData::class.java)
            activity.startActivity(intent)
        } else {
            recreateActivity(activity)
        }
        Toast.makeText(activity, "Enter Data", Toast.LENGTH_SHORT).show()
    }
    fun openDisplayData(activity: Activity) {
        if (activity::class.java != DisplayData::class.java) {
            val intent = Intent(activity, DisplayData::class.java)
            activity.startActivity(intent)
        } else {
            recreateActivity(activity)
        }
        Toast.makeText(activity, "Display Data", Toast.LENGTH_SHORT).show()
    }
    fun openSettings(activity: Activity) {
        if (activity::class.java != Settings::class.java) {
            val intent = Intent(activity, Settings::class.java)
            activity.startActivity(intent)
        } else {
            recreateActivity(activity)
        }
        Toast.makeText(activity, "Settings", Toast.LENGTH_SHORT).show()
    }
    fun openSecondActivity(activity: Activity) {
        if (activity::class.java != SecondActivity::class.java) {
            val intent = Intent(activity, SecondActivity::class.java)
            activity.startActivity(intent)
        } else {
            recreateActivity(activity)
        }
        Toast.makeText(activity, "Second Activity", Toast.LENGTH_SHORT).show()
    }


    fun getColorScheme(activity: Activity) : Int {
        val colorSchemeId = activity.getSharedPreferences("data", AppCompatActivity.MODE_PRIVATE).getInt("colorSchemeId", 1)
        return colorSchemeId
    }

    fun getThemeColor(activity: Activity, choice: String = "colorPrimary") : Int {
        val typedValue = TypedValue()
        when (choice) {
            "colorSurface" -> activity.theme.resolveAttribute(com.google.android.material.R.attr.colorSurface, typedValue, true) // light1 dark7
            "colorOnSurface" -> activity.theme.resolveAttribute(com.google.android.material.R.attr.colorOnSurface, typedValue, true) // light8 dark0
            "colorPrimarySurface" -> activity.theme.resolveAttribute(com.google.android.material.R.attr.colorPrimarySurface, typedValue, true) // c2
            "colorOnPrimarySurface" -> activity.theme.resolveAttribute(com.google.android.material.R.attr.colorOnPrimarySurface, typedValue, true) // c9
            "colorOnPrimary" -> activity.theme.resolveAttribute(com.google.android.material.R.attr.colorOnPrimary, typedValue, true) // c0
            "colorPrimaryVariant" -> activity.theme.resolveAttribute(com.google.android.material.R.attr.colorPrimaryVariant, typedValue, true) // c6
            "colorSecondary" -> activity.theme.resolveAttribute(com.google.android.material.R.attr.colorSecondary, typedValue, true) // light7 dark3
            "colorOnSecondary" -> activity.theme.resolveAttribute(com.google.android.material.R.attr.colorOnSecondary, typedValue, true) // light1 dark8
            "colorSecondaryVariant" -> activity.theme.resolveAttribute(com.google.android.material.R.attr.colorSecondaryVariant, typedValue, true) // light8 dark?
            "colorPrimaryDark" -> activity.theme.resolveAttribute(com.google.android.material.R.attr.colorPrimaryDark, typedValue, true) // c9
            else -> activity.theme.resolveAttribute(com.google.android.material.R.attr.colorPrimary, typedValue, true) // c5
        }
        return typedValue.data
    }
    fun getColor(activity: Activity, shade: Int = 5, color : Int = activity.getSharedPreferences("data", AppCompatActivity.MODE_PRIVATE)
        .getInt("colorSchemeId", 1)) : Int {
        return when (shade) {
            0 -> activity.resources.getColor(R.color.white, activity.theme)
            1 -> when (color) {
                1 ->    activity.resources.getColor(R.color.blue1, activity.theme)
                2 ->  activity.resources.getColor(R.color.indigo1, activity.theme)
                3 ->  activity.resources.getColor(R.color.purple1, activity.theme)
                4 ->    activity.resources.getColor(R.color.pink1, activity.theme)
                5 ->     activity.resources.getColor(R.color.red1, activity.theme)
                6 ->  activity.resources.getColor(R.color.orange1, activity.theme)
                7 ->  activity.resources.getColor(R.color.yellow1, activity.theme)
                8 ->   activity.resources.getColor(R.color.green1, activity.theme)
                9 ->    activity.resources.getColor(R.color.teal1, activity.theme)
                10 ->   activity.resources.getColor(R.color.cyan1, activity.theme)
                else -> activity.resources.getColor(R.color.gray1, activity.theme)
            }
            2 -> when (color) {
                1 ->    activity.resources.getColor(R.color.blue2, activity.theme)
                2 ->  activity.resources.getColor(R.color.indigo2, activity.theme)
                3 ->  activity.resources.getColor(R.color.purple2, activity.theme)
                4 ->    activity.resources.getColor(R.color.pink2, activity.theme)
                5 ->     activity.resources.getColor(R.color.red2, activity.theme)
                6 ->  activity.resources.getColor(R.color.orange2, activity.theme)
                7 ->  activity.resources.getColor(R.color.yellow2, activity.theme)
                8 ->   activity.resources.getColor(R.color.green2, activity.theme)
                9 ->    activity.resources.getColor(R.color.teal2, activity.theme)
                10 ->   activity.resources.getColor(R.color.cyan2, activity.theme)
                else -> activity.resources.getColor(R.color.gray2, activity.theme)
            }
            3 -> when (color) {
                1 ->    activity.resources.getColor(R.color.blue3, activity.theme)
                2 ->  activity.resources.getColor(R.color.indigo3, activity.theme)
                3 ->  activity.resources.getColor(R.color.purple3, activity.theme)
                4 ->    activity.resources.getColor(R.color.pink3, activity.theme)
                5 ->     activity.resources.getColor(R.color.red3, activity.theme)
                6 ->  activity.resources.getColor(R.color.orange3, activity.theme)
                7 ->  activity.resources.getColor(R.color.yellow3, activity.theme)
                8 ->   activity.resources.getColor(R.color.green3, activity.theme)
                9 ->    activity.resources.getColor(R.color.teal3, activity.theme)
                10 ->   activity.resources.getColor(R.color.cyan3, activity.theme)
                else -> activity.resources.getColor(R.color.gray3, activity.theme)
            }
            4 -> when (color) {
                1 ->    activity.resources.getColor(R.color.blue4, activity.theme)
                2 ->  activity.resources.getColor(R.color.indigo4, activity.theme)
                3 ->  activity.resources.getColor(R.color.purple4, activity.theme)
                4 ->    activity.resources.getColor(R.color.pink4, activity.theme)
                5 ->     activity.resources.getColor(R.color.red4, activity.theme)
                6 ->  activity.resources.getColor(R.color.orange4, activity.theme)
                7 ->  activity.resources.getColor(R.color.yellow4, activity.theme)
                8 ->   activity.resources.getColor(R.color.green4, activity.theme)
                9 ->    activity.resources.getColor(R.color.teal4, activity.theme)
                10 ->   activity.resources.getColor(R.color.cyan4, activity.theme)
                else -> activity.resources.getColor(R.color.gray4, activity.theme)
            }
            6 -> when (color) {
                1 ->    activity.resources.getColor(R.color.blue6, activity.theme)
                2 ->  activity.resources.getColor(R.color.indigo6, activity.theme)
                3 ->  activity.resources.getColor(R.color.purple6, activity.theme)
                4 ->    activity.resources.getColor(R.color.pink6, activity.theme)
                5 ->     activity.resources.getColor(R.color.red6, activity.theme)
                6 ->  activity.resources.getColor(R.color.orange6, activity.theme)
                7 ->  activity.resources.getColor(R.color.yellow6, activity.theme)
                8 ->   activity.resources.getColor(R.color.green6, activity.theme)
                9 ->    activity.resources.getColor(R.color.teal6, activity.theme)
                10 ->   activity.resources.getColor(R.color.cyan6, activity.theme)
                else -> activity.resources.getColor(R.color.gray6, activity.theme)
            }
            7 -> when (color) {
                1 ->    activity.resources.getColor(R.color.blue7, activity.theme)
                2 ->  activity.resources.getColor(R.color.indigo7, activity.theme)
                3 ->  activity.resources.getColor(R.color.purple7, activity.theme)
                4 ->    activity.resources.getColor(R.color.pink7, activity.theme)
                5 ->     activity.resources.getColor(R.color.red7, activity.theme)
                6 ->  activity.resources.getColor(R.color.orange7, activity.theme)
                7 ->  activity.resources.getColor(R.color.yellow7, activity.theme)
                8 ->   activity.resources.getColor(R.color.green7, activity.theme)
                9 ->    activity.resources.getColor(R.color.teal7, activity.theme)
                10 ->   activity.resources.getColor(R.color.cyan7, activity.theme)
                else -> activity.resources.getColor(R.color.gray7, activity.theme)
            }
            8 -> when (color) {
                1 ->    activity.resources.getColor(R.color.blue8, activity.theme)
                2 ->  activity.resources.getColor(R.color.indigo8, activity.theme)
                3 ->  activity.resources.getColor(R.color.purple8, activity.theme)
                4 ->    activity.resources.getColor(R.color.pink8, activity.theme)
                5 ->     activity.resources.getColor(R.color.red8, activity.theme)
                6 ->  activity.resources.getColor(R.color.orange8, activity.theme)
                7 ->  activity.resources.getColor(R.color.yellow8, activity.theme)
                8 ->   activity.resources.getColor(R.color.green8, activity.theme)
                9 ->    activity.resources.getColor(R.color.teal8, activity.theme)
                10 ->   activity.resources.getColor(R.color.cyan8, activity.theme)
                else -> activity.resources.getColor(R.color.gray8, activity.theme)
            }
            9 -> when (color) {
                1 ->    activity.resources.getColor(R.color.blue9, activity.theme)
                2 ->  activity.resources.getColor(R.color.indigo9, activity.theme)
                3 ->  activity.resources.getColor(R.color.purple9, activity.theme)
                4 ->    activity.resources.getColor(R.color.pink9, activity.theme)
                5 ->     activity.resources.getColor(R.color.red9, activity.theme)
                6 ->  activity.resources.getColor(R.color.orange9, activity.theme)
                7 ->  activity.resources.getColor(R.color.yellow9, activity.theme)
                8 ->   activity.resources.getColor(R.color.green9, activity.theme)
                9 ->    activity.resources.getColor(R.color.teal9, activity.theme)
                10 ->   activity.resources.getColor(R.color.cyan9, activity.theme)
                else -> activity.resources.getColor(R.color.gray9, activity.theme)
            }
            10 -> activity.resources.getColor(R.color.black, activity.theme)
            else -> when (color) {
                1 ->    activity.resources.getColor(R.color.blue5, activity.theme)
                2 ->  activity.resources.getColor(R.color.indigo5, activity.theme)
                3 ->  activity.resources.getColor(R.color.purple5, activity.theme)
                4 ->    activity.resources.getColor(R.color.pink5, activity.theme)
                5 ->     activity.resources.getColor(R.color.red5, activity.theme)
                6 ->  activity.resources.getColor(R.color.orange5, activity.theme)
                7 ->  activity.resources.getColor(R.color.yellow5, activity.theme)
                8 ->   activity.resources.getColor(R.color.green5, activity.theme)
                9 ->    activity.resources.getColor(R.color.teal5, activity.theme)
                10 ->   activity.resources.getColor(R.color.cyan5, activity.theme)
                else -> activity.resources.getColor(R.color.gray5, activity.theme)
            }
        }
    }
}