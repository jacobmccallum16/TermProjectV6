package com.example.termprojectv6

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.termprojectv6.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var entryNum = 0
    var displayIds = "ID:"
    var displayDates = "Date:"
    var displayWeights = "Weight:"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Utils.applyColorScheme(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportActionBar?.title = resources.getString(R.string.MainActivity_title)
        val btnMain : Button = findViewById(R.id.btnMain)
        btnMain.setOnClickListener { Utils.openMain(this) }
        val btnEnterData : Button = findViewById(R.id.btnEnterData)
        btnEnterData.setOnClickListener { Utils.openEnterData(this) }
        val btnDisplayData : Button = findViewById(R.id.btnDisplayData)
        btnDisplayData.setOnClickListener { Utils.openDisplayData(this) }

        // get data
        val data = getSharedPreferences("data", MODE_PRIVATE) // getter
        val editor = data.edit() // setter
        entryNum = data.getInt("entries", 0)
        val entries : ArrayList<Entry> = ArrayList()
        for (i in 0 until entryNum) {
            entries.add(Entry(data.getInt("id-$i", 0),
                data.getString("date-$i", "n/a").toString(),
                data.getFloat("weight-$i", 0f)))
            displayIds += "\n${entries[i].id}"
            displayDates += "\n${entries[i].date}"
            displayWeights += "\n${entries[i].weight}"
        }
        // Display Data Start
        binding.tvIds.text = displayIds
        binding.tvDates.text = displayDates
        binding.tvWeights.text = displayWeights
        // Display Data End

        // add data
        binding.btnSubmit.setOnClickListener {
            try {
                entries.add(Entry(entryNum, binding.etDate.text.toString(), binding.etWeight.text.toString().toFloat()))
                editor.putInt("id-$entryNum", entries[entryNum].id).apply()
                displayIds += "\n${entries[entryNum].id}"
                editor.putString("date-$entryNum", entries[entryNum].date).apply()
                displayDates += "\n${entries[entryNum].date}"
                editor.putFloat("weight-$entryNum", entries[entryNum].weight).apply()
                displayWeights += "\n${entries[entryNum].weight}"
                entryNum++
                editor.putInt("entries", entryNum).apply()
            } catch (e: NumberFormatException) {
                binding.tvFeedback.text = "Please enter a valid weight and date"
                return@setOnClickListener
            }
            binding.tvFeedback.text = ""
            Toast.makeText(this, "Entry[${entryNum-1}] added", Toast.LENGTH_SHORT).show()
            binding.tvIds.text = displayIds
            binding.tvDates.text = displayDates
            binding.tvWeights.text = displayWeights
        }
        // removeLastEntry
        binding.btnRemoveLastEntry.setOnClickListener {
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
            binding.tvFeedback.text = ""
            binding.tvIds.text = displayIds
            binding.tvDates.text = displayDates
            binding.tvWeights.text = displayWeights
            Toast.makeText(this, "Entry[$entryNum] deleted", Toast.LENGTH_SHORT).show()
        }
        // deleteAllEntries
        binding.btnDeleteAllEntries.setOnClickListener {
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
            binding.tvFeedback.text = ""
            Toast.makeText(this, "All entries deleted", Toast.LENGTH_SHORT).show()
            binding.tvIds.text = displayIds
            binding.tvDates.text = displayDates
            binding.tvWeights.text = displayWeights
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