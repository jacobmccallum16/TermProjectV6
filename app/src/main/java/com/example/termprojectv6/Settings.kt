package com.example.termprojectv6

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.termprojectv6.databinding.ActivitySettingsBinding

class Settings : AppCompatActivity() {

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Utils.applyColorScheme(this)
        val binding = ActivitySettingsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportActionBar?.title = resources.getString(R.string.Settings_title)
        val btnMain : Button = findViewById(R.id.btnMain)
        btnMain.setOnClickListener { Utils.openMain(this) }
        val btnEnterData : Button = findViewById(R.id.btnEnterData)
        btnEnterData.setOnClickListener { Utils.openEnterData(this) }
        val btnDisplayData : Button = findViewById(R.id.btnDisplayData)
        btnDisplayData.setOnClickListener { Utils.openDisplayData(this) }

        val data = getSharedPreferences("data", Context.MODE_PRIVATE)
        val colorSchemeId = Utils.getColorScheme(this)
        binding.spinnerColorScheme.setSelection(colorSchemeId)
        binding.spinnerColorMode.setSelection(data.getInt("colorModeId", 0))
        binding.spinnerLanguage.setSelection(data.getInt("languageId", 0))
        val colorSchemes = resources.getStringArray(R.array.txtColorScheme)
        val currentColorSchemeFormat = resources.getString(R.string.currentColourScheme)
        binding.tvFeedback.text = String.format(currentColorSchemeFormat, colorSchemes[colorSchemeId])

        binding.btnSaveSettings.setOnClickListener {
            Utils.updateSettings(this, binding.spinnerColorScheme.selectedItemPosition,
                binding.spinnerColorMode.selectedItemPosition,
                binding.spinnerLanguage.selectedItemPosition)}

        binding.btnDeleteAllEntries.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle(resources.getString(R.string.alertDeleteAllTitle))
            builder.setMessage(resources.getString(R.string.alertDeleteAllMessage))
            builder.setPositiveButton(resources.getString(R.string.alertDeleteAllPositive)) { _, _ ->
                Entries.deleteAllEntries(this)
            }
            builder.setNegativeButton(resources.getString(R.string.alertDeleteAllNegative)) { _, _ ->
                Toast.makeText(this, resources.getString(R.string.alertDeleteAllNegativeToast), Toast.LENGTH_SHORT).show()
            }
            builder.show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_settings) { Utils.openSettings(this)
        } else if (item.itemId == R.id.menuRandomTheme) { Utils.randomizeColorScheme(this)
        } else { return super.onOptionsItemSelected(item)
        }
        return true
    }

}