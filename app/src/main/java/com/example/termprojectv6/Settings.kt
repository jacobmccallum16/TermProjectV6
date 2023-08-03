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
        applyColorScheme()
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
            recreateActivity()
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
            0 -> { setTheme(R.style.AppTheme_Gray) }
            1 -> { setTheme(R.style.AppTheme_Blue) }
            2 -> { setTheme(R.style.AppTheme_Indigo) }
            3 -> { setTheme(R.style.AppTheme_Purple) }
            4 -> { setTheme(R.style.AppTheme_Pink) }
            5 -> { setTheme(R.style.AppTheme_Red) }
            6 -> { setTheme(R.style.AppTheme_Orange) }
            7 -> { setTheme(R.style.AppTheme_Yellow) }
            8 -> { setTheme(R.style.AppTheme_Green) }
            9 -> { setTheme(R.style.AppTheme_Teal) }
            10 -> { setTheme(R.style.AppTheme_Cyan) }
        }
    }
    fun recreateActivity() {
        val intent = Intent(this, this.javaClass)
        this.startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        this.finish()
    }
}