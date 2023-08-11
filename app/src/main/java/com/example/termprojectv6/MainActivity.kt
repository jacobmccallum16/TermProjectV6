package com.example.termprojectv6

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.termprojectv6.Adapter.EntryGroupRecyclerAdapter
import com.example.termprojectv6.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val months = arrayOf("","January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Utils.applyColorScheme(this)
        val binding = ActivityMainBinding.inflate(layoutInflater)
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
        val data = Entries.data(this)
        var entryNum = Entries.getEntryNum(this)
        val numOfEntries0 = resources.getString(R.string.num_of_entries_0)
        val numOfEntries1 = resources.getString(R.string.num_of_entries_1)
        val numOfEntriesPlural = resources.getString(R.string.num_of_entries_plural)
        binding.tvFeedback.text = when (entryNum) {
            0 -> String.format(numOfEntries0, entryNum)
            1 -> String.format(numOfEntries1, entryNum)
            else -> String.format(numOfEntriesPlural, entryNum)
        }
        var entries = Entries.groupByMonth(this)
        // finish layout
        try {
            val layoutManager: RecyclerView.LayoutManager
            val adapter: RecyclerView.Adapter<*>
            val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
            layoutManager = LinearLayoutManager(this)
            recyclerView.layoutManager = layoutManager
            adapter = EntryGroupRecyclerAdapter(entries, Utils.getLocale(this), this)
            recyclerView.adapter = adapter
        } catch (e: Exception) {

        }

//        binding.btnSubmit.setOnClickListener {
//            try {
//                entries.add(Entry(entryNum, binding.etDate.text.toString(), binding.etWeight.text.toString().toFloat()))
//                editor.putInt("id-$entryNum", entries[entryNum].id).apply()
//                displayIds += "\n${entries[entryNum].id}"
//                editor.putString("date-$entryNum", entries[entryNum].date).apply()
//                displayDates += "\n${entries[entryNum].date}"
//                editor.putFloat("weight-$entryNum", entries[entryNum].weight).apply()
//                displayWeights += "\n${entries[entryNum].weight}"
//                entryNum++
//                editor.putInt("entries", entryNum).apply()
//            } catch (e: NumberFormatException) {
//                binding.tvFeedback.text = getString(R.string.invalid_weight_or_date)
//                return@setOnClickListener
//            }
//            binding.tvFeedback.text = ""
//            Toast.makeText(this, "Entry[${entryNum-1}] added", Toast.LENGTH_SHORT).show()
//            binding.tvDates.text = displayDates
//            binding.tvWeights.text = displayWeights
//        }
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
        } else if (item.itemId == R.id.menuSecondActivity) { Utils.openSecondActivity(this)
        } else { return super.onOptionsItemSelected(item)
        }
        return true
    }

}