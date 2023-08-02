package com.example.termprojectv6

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    lateinit var etDate : EditText
    lateinit var etWeight : EditText
    lateinit var btnSubmit : Button
    lateinit var tvFeedback : TextView
    lateinit var tvIds : TextView
    lateinit var tvDates : TextView
    lateinit var tvWeights : TextView
    lateinit var btnDeleteAllEntries : Button
    lateinit var btnRemoveLastEntry : Button
    lateinit var btnMain : Button
    lateinit var btnEnterData : Button
    lateinit var btnDisplayData : Button
    lateinit var linearLayoutBottom : LinearLayout
    var entryNum = 0
    var displayIds = "ID:"
    var displayDates = "Date:"
    var displayWeights = "Weight:"
    val colorSchemes : Array<String> = arrayOf("Gray", "Blue", "Indigo", "Purple", "Pink",
        "Red", "Orange", "Yellow", "Green", "Teal", "Cyan")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title = resources.getString(R.string.MainActivity_title)
        etDate = findViewById(R.id.etDate)
        etWeight = findViewById(R.id.etWeight)
        btnSubmit = findViewById(R.id.btnSubmit)
        tvFeedback = findViewById(R.id.tvFeedback)
        tvIds = findViewById(R.id.tvColorScheme)
        tvDates = findViewById(R.id.tvDates)
        tvWeights  = findViewById(R.id.tvWeights)
        btnDeleteAllEntries = findViewById(R.id.btnDeleteAllEntries)
        btnRemoveLastEntry= findViewById(R.id.btnRemoveLastEntry)
        // navigation
        btnMain = findViewById(R.id.btnMain)
        btnMain.setOnClickListener {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
        }
        btnEnterData = findViewById(R.id.btnEnterData)
        btnEnterData.setOnClickListener {
            val i = Intent(this, EnterData::class.java)
            startActivity(i)
        }
        btnDisplayData = findViewById(R.id.btnDisplayData)
        btnDisplayData.setOnClickListener {
            val intent = Intent(this, DisplayData::class.java)
            startActivity(intent)
        }
        linearLayoutBottom = findViewById(R.id.linearLayoutBottom)


        // get data
        val data = getSharedPreferences("data", MODE_PRIVATE) // getter
        val editor = data.edit() // setter
        // MANUALLY MODIFY DATA FOR DEBUGGING PURPOSES
//
        // MANUALLY MODIFY DATA FOR DEBUGGING PURPOSES
        entryNum = data.getInt("entries", 0)
//        var ids : ArrayList<Int> = ArrayList<Int>()
//        var dates : ArrayList<String> = ArrayList<String>()
//        var weights : ArrayList<Double> = ArrayList<Double>()
        val entries : ArrayList<Entry> = ArrayList()
        // GET AND DISPLAY DATA FROM SHARED PREFERENCES START
        for (i in 0 until entryNum) {
            entries.add(Entry(data.getInt("id-$i", 0),
                data.getString("date-$i", "n/a").toString(),
                data.getFloat("weight-$i", 0f)))
            displayIds += "\n${entries[i].id}"
            displayDates += "\n${entries[i].date}"
            displayWeights += "\n${entries[i].weight}"
        }
        // GET AND DISPLAY DATA FROM SHARED PREFERENCES END
        // Display Data Start
        tvIds.text = displayIds
        tvDates.text = displayDates
        tvWeights.text = displayWeights
        // Display Data End

        // add data
        btnSubmit.setOnClickListener {
            try {
                entries.add(Entry(entryNum, etDate.text.toString(), etWeight.text.toString().toFloat()))
                editor.putInt("id-$entryNum", entries[entryNum].id).apply()
                displayIds += "\n${entries[entryNum].id}"
                editor.putString("date-$entryNum", entries[entryNum].date).apply()
                displayDates += "\n${entries[entryNum].date}"
                editor.putFloat("weight-$entryNum", entries[entryNum].weight).apply()
                displayWeights += "\n${entries[entryNum].weight}"
                entryNum++
                editor.putInt("entries", entryNum).apply()
            } catch (e: NumberFormatException) {
                tvFeedback.text = "Please enter a valid weight and date"
                return@setOnClickListener
            }
            tvFeedback.text = ""
            Toast.makeText(this, "Entry[${entryNum-1}] added", Toast.LENGTH_SHORT).show()
            tvIds.text = displayIds
            tvDates.text = displayDates
            tvWeights.text = displayWeights
        }
        // removeLastEntry
        btnRemoveLastEntry.setOnClickListener {
            entryNum--
            editor.remove("id-$entryNum")
            editor.remove("date-$entryNum")
            editor.remove("weight-$entryNum")
            editor.putInt("entries", entryNum)
            editor.commit()
            entries.removeAt(entryNum)
            // reset display
            displayIds = "ID:"
            displayDates = "Date:"
            displayWeights = "Weight:"
            for (i in entries) {
                displayIds += "\n${i.id}"
                displayDates += "\n${i.date}"
                displayWeights += "\n${i.weight}"
            }
            tvFeedback.text = ""
            tvIds.text = displayIds
            tvDates.text = displayDates
            tvWeights.text = displayWeights
            Toast.makeText(this, "Entry[$entryNum] deleted", Toast.LENGTH_SHORT).show()
        }
        // deleteAllEntries
        btnDeleteAllEntries.setOnClickListener {
            while (entries.size > 0) {
                entryNum--
                editor.remove("id-$entryNum")
                editor.remove("date-$entryNum")
                editor.remove("weight-$entryNum")
                editor.putInt("entries", entryNum)
                editor.commit()
                entries.removeAt(entryNum)
            }
            // reset display
            displayIds = "ID:"
            displayDates = "Date:"
            displayWeights = "Weight:"
            tvFeedback.text = ""
            Toast.makeText(this, "All entries deleted", Toast.LENGTH_SHORT).show()
            tvIds.text = displayIds
            tvDates.text = displayDates
            tvWeights.text = displayWeights
        }

        applyColorScheme()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_settings) {
            Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, Settings::class.java)
            startActivity(intent)
        } else if (item.itemId == R.id.menuRandomTheme) {
            var colorSchemeId : Int = Random.nextInt(0,11)
            getSharedPreferences("data", MODE_PRIVATE).edit().putInt("colorSchemeId", colorSchemeId).commit()
            applyColorScheme(colorSchemeId)
            Toast.makeText(this, "Random color scheme ${colorSchemes[colorSchemeId]} applied", Toast.LENGTH_SHORT).show()
        } else if (item.itemId == R.id.menuMain) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "Main", Toast.LENGTH_SHORT).show()
        } else if (item.itemId == R.id.menuEnterData) {
            val intent = Intent(this, EnterData::class.java)
            startActivity(intent)
            Toast.makeText(this, "Enter Data", Toast.LENGTH_SHORT).show()
        } else if (item.itemId == R.id.menuDisplayData) {
            val intent = Intent(this, DisplayData::class.java)
            startActivity(intent)
            Toast.makeText(this, "Display Data", Toast.LENGTH_SHORT).show()
        } else {
            return super.onOptionsItemSelected(item)
        }
        return true
    }

    fun applyColorScheme(colorSchemeId : Int = getSharedPreferences("data", MODE_PRIVATE).getInt("colorSchemeId", 1)) {
        when (colorSchemeId) {
            0 -> {
                window.setStatusBarColor(getResources().getColor(R.color.gray7))
                supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.gray7)))
                btnSubmit.background.setTint(resources.getColor(R.color.gray5))
                btnSubmit.setTextColor(resources.getColor(R.color.black))
                btnRemoveLastEntry.background.setTint(resources.getColor(R.color.gray6))
                btnMain.background.setTint(resources.getColor(R.color.gray6))
                btnEnterData.background.setTint(resources.getColor(R.color.gray6))
                btnDisplayData.background.setTint(resources.getColor(R.color.gray6))
                linearLayoutBottom.background.setTint(resources.getColor(R.color.gray9))
            }
            1 -> {
                window.setStatusBarColor(getResources().getColor(R.color.blue7))
                supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.blue7)))
                btnSubmit.background.setTint(resources.getColor(R.color.blue5))
                btnSubmit.setTextColor(resources.getColor(R.color.white))
                btnRemoveLastEntry.background.setTint(resources.getColor(R.color.blue6))
                btnMain.background.setTint(resources.getColor(R.color.blue6))
                btnEnterData.background.setTint(resources.getColor(R.color.blue6))
                btnDisplayData.background.setTint(resources.getColor(R.color.blue6))
                linearLayoutBottom.background.setTint(resources.getColor(R.color.blue9))
            }
            2 -> {
                window.setStatusBarColor(getResources().getColor(R.color.indigo7))
                supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.indigo7)))
                btnSubmit.background.setTint(resources.getColor(R.color.indigo5))
                btnSubmit.setTextColor(resources.getColor(R.color.white))
                btnRemoveLastEntry.background.setTint(resources.getColor(R.color.indigo6))
                btnMain.background.setTint(resources.getColor(R.color.indigo6))
                btnEnterData.background.setTint(resources.getColor(R.color.indigo6))
                btnDisplayData.background.setTint(resources.getColor(R.color.indigo6))
                linearLayoutBottom.background.setTint(resources.getColor(R.color.indigo9))
            }
            3 -> {
                window.setStatusBarColor(getResources().getColor(R.color.purple7))
                supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.purple7)))
                btnSubmit.background.setTint(resources.getColor(R.color.purple5))
                btnSubmit.setTextColor(resources.getColor(R.color.white))
                btnRemoveLastEntry.background.setTint(resources.getColor(R.color.purple6))
                btnMain.background.setTint(resources.getColor(R.color.purple6))
                btnEnterData.background.setTint(resources.getColor(R.color.purple6))
                btnDisplayData.background.setTint(resources.getColor(R.color.purple6))
                linearLayoutBottom.background.setTint(resources.getColor(R.color.purple9))
            }
            4 -> {
                window.setStatusBarColor(getResources().getColor(R.color.pink7))
                supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.pink7)))
                btnSubmit.background.setTint(resources.getColor(R.color.pink5))
                btnSubmit.setTextColor(resources.getColor(R.color.white))
                btnRemoveLastEntry.background.setTint(resources.getColor(R.color.pink6))
                btnMain.background.setTint(resources.getColor(R.color.pink6))
                btnEnterData.background.setTint(resources.getColor(R.color.pink6))
                btnDisplayData.background.setTint(resources.getColor(R.color.pink6))
                linearLayoutBottom.background.setTint(resources.getColor(R.color.pink9))
            }
            5 -> {
                window.setStatusBarColor(getResources().getColor(R.color.red7))
                supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.red7)))
                btnSubmit.background.setTint(resources.getColor(R.color.red5))
                btnSubmit.setTextColor(resources.getColor(R.color.white))
                btnRemoveLastEntry.background.setTint(resources.getColor(R.color.red6))
                btnMain.background.setTint(resources.getColor(R.color.red6))
                btnEnterData.background.setTint(resources.getColor(R.color.red6))
                btnDisplayData.background.setTint(resources.getColor(R.color.red6))
                linearLayoutBottom.background.setTint(resources.getColor(R.color.red9))
            }
            6 -> {
                window.setStatusBarColor(getResources().getColor(R.color.orange7))
                supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.orange7)))
                btnSubmit.background.setTint(resources.getColor(R.color.orange5))
                btnSubmit.setTextColor(resources.getColor(R.color.black))
                btnRemoveLastEntry.background.setTint(resources.getColor(R.color.orange6))
                btnMain.background.setTint(resources.getColor(R.color.orange6))
                btnEnterData.background.setTint(resources.getColor(R.color.orange6))
                btnDisplayData.background.setTint(resources.getColor(R.color.orange6))
                linearLayoutBottom.background.setTint(resources.getColor(R.color.orange9))
            }
            7 -> {
                window.setStatusBarColor(getResources().getColor(R.color.yellow7))
                supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.yellow7)))
                btnSubmit.background.setTint(resources.getColor(R.color.yellow5))
                btnSubmit.setTextColor(resources.getColor(R.color.black))
                btnRemoveLastEntry.background.setTint(resources.getColor(R.color.yellow6))
                btnMain.background.setTint(resources.getColor(R.color.yellow6))
                btnEnterData.background.setTint(resources.getColor(R.color.yellow6))
                btnDisplayData.background.setTint(resources.getColor(R.color.yellow6))
                linearLayoutBottom.background.setTint(resources.getColor(R.color.yellow9))
            }
            8 -> {
                window.setStatusBarColor(getResources().getColor(R.color.green7))
                supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.green7)))
                btnSubmit.background.setTint(resources.getColor(R.color.green5))
                btnSubmit.setTextColor(resources.getColor(R.color.white))
                btnRemoveLastEntry.background.setTint(resources.getColor(R.color.green6))
                btnMain.background.setTint(resources.getColor(R.color.green6))
                btnEnterData.background.setTint(resources.getColor(R.color.green6))
                btnDisplayData.background.setTint(resources.getColor(R.color.green6))
                linearLayoutBottom.background.setTint(resources.getColor(R.color.green9))
            }
            9 -> {
                window.setStatusBarColor(getResources().getColor(R.color.teal7))
                supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.teal7)))
                btnSubmit.background.setTint(resources.getColor(R.color.teal5))
                btnSubmit.setTextColor(resources.getColor(R.color.black))
                btnRemoveLastEntry.background.setTint(resources.getColor(R.color.teal6))
                btnMain.background.setTint(resources.getColor(R.color.teal6))
                btnEnterData.background.setTint(resources.getColor(R.color.teal6))
                btnDisplayData.background.setTint(resources.getColor(R.color.teal6))
                linearLayoutBottom.background.setTint(resources.getColor(R.color.teal9))
            }
            10 -> {
                window.setStatusBarColor(getResources().getColor(R.color.cyan7))
                supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.cyan7)))
                btnSubmit.background.setTint(resources.getColor(R.color.cyan5))
                btnSubmit.setTextColor(resources.getColor(R.color.black))
                btnRemoveLastEntry.background.setTint(resources.getColor(R.color.cyan6))
                btnMain.background.setTint(resources.getColor(R.color.cyan6))
                btnEnterData.background.setTint(resources.getColor(R.color.cyan6))
                btnDisplayData.background.setTint(resources.getColor(R.color.cyan6))
                linearLayoutBottom.background.setTint(resources.getColor(R.color.cyan9))
            }
        }
    }


}