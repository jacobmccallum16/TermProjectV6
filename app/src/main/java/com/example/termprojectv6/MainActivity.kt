package com.example.termprojectv6

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.termprojectv6.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

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
        var entryNum = Entries.getEntryNum(this)
        val numOfEntries0 = resources.getString(R.string.num_of_entries_0)
        val numOfEntries1 = resources.getString(R.string.num_of_entries_1)
        val numOfEntriesPlural = resources.getString(R.string.num_of_entries_plural)
        binding.tvFeedback.text = when (entryNum) {
            0 -> String.format(numOfEntries0, entryNum)
            1 -> String.format(numOfEntries1, entryNum)
            else -> String.format(numOfEntriesPlural, entryNum)
        }

        try {
            val entries = Entries.groupByMonth(this)
            val sortBy = Entries.getMainSortBy(this)
            if (sortBy == "New") {
                entries.reverse()
                binding.btnSortByNew.background.setTint(Utils.getColor(this, 5))
            } else {
                binding.btnSortByOld.background.setTint(Utils.getColor(this, 5))
            }
            val layoutManager: RecyclerView.LayoutManager
            val adapter: RecyclerView.Adapter<*>
            val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
            layoutManager = LinearLayoutManager(this)
            recyclerView.layoutManager = layoutManager
            adapter = EntryGroupRecyclerAdapter(entries, this)
            recyclerView.adapter = adapter
        } catch (_: Exception) {

        }

        binding.btnSubmit.setOnClickListener{
            try {
                val created : Boolean = Entries.createEntry(this, binding.etDate.text.toString(), binding.etWeight.text.toString().toFloat())
                if (created) {
                    entryNum = Entries.getEntryNum(this)
                    binding.tvFeedback.text = ""
                    Toast.makeText(this, resources.getString(R.string.entryAddedToast), Toast.LENGTH_SHORT).show()
                } else {
                    binding.tvFeedback.text = resources.getString(R.string.invalidDateInput)
                }
                Utils.recreateActivity(this)
            } catch (e: NumberFormatException) {
                binding.tvFeedback.text = getString(R.string.invalid_weight_or_date)
                return@setOnClickListener
            } catch (e: Exception) {
                binding.tvFeedback.text = getString(R.string.invalid_weight_or_date)
                return@setOnClickListener
            }
        }

        binding.btnSortByOld.setOnClickListener {
            Entries.setMainSortBy(this, "Old")
            Utils.recreateActivity(this)
        }
        binding.btnSortByNew.setOnClickListener {
            Entries.setMainSortBy(this, "New")
            Utils.recreateActivity(this)
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