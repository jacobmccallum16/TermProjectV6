package com.example.termprojectv6

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Spinner
import com.example.termprojectv6.databinding.ActivitySettingsBinding

class Settings : AppCompatActivity() {

    val colorSchemes : Array<String> = arrayOf("Gray", "Blue", "Indigo", "Purple", "Pink",
        "Red", "Orange", "Yellow", "Green", "Teal", "Cyan")

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Utils.applyColorScheme(this)
        val binding = ActivitySettingsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportActionBar?.title = supportActionBar?.title.toString() + " - Settings"
        val btnMain : Button = findViewById(R.id.btnMain)
        btnMain.setOnClickListener { Utils.openMain(this) }
        val btnEnterData : Button = findViewById(R.id.btnEnterData)
        btnEnterData.setOnClickListener { Utils.openEnterData(this) }
        val btnDisplayData : Button = findViewById(R.id.btnDisplayData)
        btnDisplayData.setOnClickListener { Utils.openDisplayData(this) }

        val data = getSharedPreferences("data", MODE_PRIVATE) // getter
        val editor = data.edit() // setter

        val spinnerColorScheme : Spinner = findViewById(R.id.spinnerColorScheme)
        var colorSchemeId = data.getInt("colorSchemeId", 0)
        spinnerColorScheme.setSelection(colorSchemeId)
        binding.tvFeedback.text = "Current color scheme: ${colorSchemes[colorSchemeId]}"
        binding.btnSaveSettings.setOnClickListener {
            colorSchemeId = spinnerColorScheme.selectedItemPosition
            Utils.updateColorScheme(this, colorSchemeId)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_settings) { Utils.openSettings(this)
        } else if (item.itemId == R.id.menuRandomTheme) { Utils.randomizeColorScheme(this)
        } else if (item.itemId == R.id.menuMain) { Utils.openMain(this)
        } else if (item.itemId == R.id.menuEnterData) { Utils.openEnterData(this)
        } else if (item.itemId == R.id.menuDisplayData) { Utils.openDisplayData(this)
        } else { return super.onOptionsItemSelected(item)
        }
        return true
    }

}