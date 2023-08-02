package com.example.termprojectv6

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import org.w3c.dom.Text
import kotlin.random.Random

class Settings : AppCompatActivity() {

    lateinit var tvFeedback : TextView
    lateinit var btnSaveSettings : Button
    lateinit var btnMain : Button
    lateinit var btnEnterData : Button
    lateinit var btnDisplayData : Button
    lateinit var linearLayoutBottom : LinearLayout
    val colorSchemes : Array<String> = arrayOf("Gray", "Blue", "Indigo", "Purple", "Pink",
        "Red", "Orange", "Yellow", "Green", "Teal", "Cyan")

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        supportActionBar?.title = supportActionBar?.title.toString() + " - Settings"
        tvFeedback = findViewById(R.id.tvFeedback)
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

        val data = getSharedPreferences("data", MODE_PRIVATE) // getter
        val editor = data.edit() // setter

        val spinnerColorScheme : Spinner = findViewById(R.id.spinnerColorScheme)
        var colorSchemeId = data.getInt("colorSchemeId", 0)
        var colorScheme = colorSchemes[colorSchemeId]
        btnSaveSettings = findViewById(R.id.btnSaveSettings)
        spinnerColorScheme.setSelection(colorSchemeId)
        tvFeedback.text = "Current color scheme: ${colorSchemes[colorSchemeId]}"
        btnSaveSettings.setOnClickListener {
            colorScheme = spinnerColorScheme.selectedItem.toString()
            colorSchemeId = spinnerColorScheme.selectedItemPosition
            editor.putInt("colorSchemeId", colorSchemeId).commit()
            tvFeedback.text = "Color scheme set to: ${colorSchemes[colorSchemeId]}"
            applyColorScheme()
        }
        // set color
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
                btnSaveSettings.background.setTint(resources.getColor(R.color.gray5))
                btnSaveSettings.setTextColor(resources.getColor(R.color.black))
                btnMain.background.setTint(resources.getColor(R.color.gray6))
                btnEnterData.background.setTint(resources.getColor(R.color.gray6))
                btnDisplayData.background.setTint(resources.getColor(R.color.gray6))
                linearLayoutBottom.background.setTint(resources.getColor(R.color.gray9))
            }
            1 -> {
                window.setStatusBarColor(getResources().getColor(R.color.blue7))
                supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.blue7)))
                btnSaveSettings.background.setTint(resources.getColor(R.color.blue5))
                btnSaveSettings.setTextColor(resources.getColor(R.color.white))
                btnMain.background.setTint(resources.getColor(R.color.blue6))
                btnEnterData.background.setTint(resources.getColor(R.color.blue6))
                btnDisplayData.background.setTint(resources.getColor(R.color.blue6))
                linearLayoutBottom.background.setTint(resources.getColor(R.color.blue9))
            }
            2 -> {
                window.setStatusBarColor(getResources().getColor(R.color.indigo7))
                supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.indigo7)))
                btnSaveSettings.background.setTint(resources.getColor(R.color.indigo5))
                btnSaveSettings.setTextColor(resources.getColor(R.color.white))
                btnMain.background.setTint(resources.getColor(R.color.indigo6))
                btnEnterData.background.setTint(resources.getColor(R.color.indigo6))
                btnDisplayData.background.setTint(resources.getColor(R.color.indigo6))
                linearLayoutBottom.background.setTint(resources.getColor(R.color.indigo9))
            }
            3 -> {
                window.setStatusBarColor(getResources().getColor(R.color.purple7))
                supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.purple7)))
                btnSaveSettings.background.setTint(resources.getColor(R.color.purple5))
                btnSaveSettings.setTextColor(resources.getColor(R.color.white))
                btnMain.background.setTint(resources.getColor(R.color.purple6))
                btnEnterData.background.setTint(resources.getColor(R.color.purple6))
                btnDisplayData.background.setTint(resources.getColor(R.color.purple6))
                linearLayoutBottom.background.setTint(resources.getColor(R.color.purple9))
            }
            4 -> {
                window.setStatusBarColor(getResources().getColor(R.color.pink7))
                supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.pink7)))
                btnSaveSettings.background.setTint(resources.getColor(R.color.pink5))
                btnSaveSettings.setTextColor(resources.getColor(R.color.white))
                btnMain.background.setTint(resources.getColor(R.color.pink6))
                btnEnterData.background.setTint(resources.getColor(R.color.pink6))
                btnDisplayData.background.setTint(resources.getColor(R.color.pink6))
                linearLayoutBottom.background.setTint(resources.getColor(R.color.pink9))
            }
            5 -> {
                window.setStatusBarColor(getResources().getColor(R.color.red7))
                supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.red7)))
                btnSaveSettings.background.setTint(resources.getColor(R.color.red5))
                btnSaveSettings.setTextColor(resources.getColor(R.color.white))
                btnMain.background.setTint(resources.getColor(R.color.red6))
                btnEnterData.background.setTint(resources.getColor(R.color.red6))
                btnDisplayData.background.setTint(resources.getColor(R.color.red6))
                linearLayoutBottom.background.setTint(resources.getColor(R.color.red9))
            }
            6 -> {
                window.setStatusBarColor(getResources().getColor(R.color.orange7))
                supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.orange7)))
                btnSaveSettings.background.setTint(resources.getColor(R.color.orange5))
                btnSaveSettings.setTextColor(resources.getColor(R.color.black))
                btnMain.background.setTint(resources.getColor(R.color.orange6))
                btnEnterData.background.setTint(resources.getColor(R.color.orange6))
                btnDisplayData.background.setTint(resources.getColor(R.color.orange6))
                linearLayoutBottom.background.setTint(resources.getColor(R.color.orange9))
            }
            7 -> {
                window.setStatusBarColor(getResources().getColor(R.color.yellow7))
                supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.yellow7)))
                btnSaveSettings.background.setTint(resources.getColor(R.color.yellow5))
                btnSaveSettings.setTextColor(resources.getColor(R.color.black))
                btnMain.background.setTint(resources.getColor(R.color.yellow6))
                btnEnterData.background.setTint(resources.getColor(R.color.yellow6))
                btnDisplayData.background.setTint(resources.getColor(R.color.yellow6))
                linearLayoutBottom.background.setTint(resources.getColor(R.color.yellow9))
            }
            8 -> {
                window.setStatusBarColor(getResources().getColor(R.color.green7))
                supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.green7)))
                btnSaveSettings.background.setTint(resources.getColor(R.color.green5))
                btnSaveSettings.setTextColor(resources.getColor(R.color.white))
                btnMain.background.setTint(resources.getColor(R.color.green6))
                btnEnterData.background.setTint(resources.getColor(R.color.green6))
                btnDisplayData.background.setTint(resources.getColor(R.color.green6))
                linearLayoutBottom.background.setTint(resources.getColor(R.color.green9))
            }
            9 -> {
                window.setStatusBarColor(getResources().getColor(R.color.teal7))
                supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.teal7)))
                btnSaveSettings.background.setTint(resources.getColor(R.color.teal5))
                btnSaveSettings.setTextColor(resources.getColor(R.color.black))
                btnMain.background.setTint(resources.getColor(R.color.teal6))
                btnEnterData.background.setTint(resources.getColor(R.color.teal6))
                btnDisplayData.background.setTint(resources.getColor(R.color.teal6))
                linearLayoutBottom.background.setTint(resources.getColor(R.color.teal9))
            }
            10 -> {
                window.setStatusBarColor(getResources().getColor(R.color.cyan7))
                supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.cyan7)))
                btnSaveSettings.background.setTint(resources.getColor(R.color.cyan5))
                btnSaveSettings.setTextColor(resources.getColor(R.color.black))

                btnMain.background.setTint(resources.getColor(R.color.cyan6))
                btnEnterData.background.setTint(resources.getColor(R.color.cyan6))
                btnDisplayData.background.setTint(resources.getColor(R.color.cyan6))
                linearLayoutBottom.background.setTint(resources.getColor(R.color.cyan9))
            }
        }
    }

}