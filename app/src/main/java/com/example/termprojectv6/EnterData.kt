package com.example.termprojectv6

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.termprojectv6.databinding.ActivityEnterDataBinding

class EnterData : AppCompatActivity(), EntryItemClickListener {

    private lateinit var binding: ActivityEnterDataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Utils.applyColorScheme(this)
        binding = ActivityEnterDataBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportActionBar?.title = resources.getString(R.string.EnterData_title)
        val btnMain : Button = findViewById(R.id.btnMain)
        btnMain.setOnClickListener { Utils.openMain(this) }
        val btnEnterData : Button = findViewById(R.id.btnEnterData)
        btnEnterData.setOnClickListener { Utils.openEnterData(this) }
        val btnDisplayData : Button = findViewById(R.id.btnDisplayData)
        btnDisplayData.setOnClickListener { Utils.openDisplayData(this) }


        val layoutManager: RecyclerView.LayoutManager
        val adapter: RecyclerView.Adapter<*>
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)

        var entryNum = Entries.getEntryNum(this)
        binding.tvFeedback.text = when (entryNum) {
            0 -> String.format(resources.getString(R.string.num_of_entries_0), entryNum)
            1 -> String.format(resources.getString(R.string.num_of_entries_1), entryNum)
            else -> String.format(resources.getString(R.string.num_of_entries_plural), entryNum)
        }
        val entries : ArrayList<Entry>
        val sortBy = Entries.getEnterDataSortBy(this)
        if (sortBy == "New") {
            entries = Entries.getEntriesSortByNew(this)
            binding.btnSortByNew.background.setTint(Utils.getColor(this, 5))
        } else if (sortBy == "Old") {
            entries = Entries.getEntriesSortByOld(this)
            binding.btnSortByOld.background.setTint(Utils.getColor(this, 5))
        } else {
            entries = Entries.getEntries(this)
            entries.sortWith(compareByDescending { it.id })
            binding.btnSortByRecent.background.setTint(Utils.getColor(this, 5))
        }

        layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        adapter = RecyclerAdapter(entries, this, this)
        recyclerView.adapter = adapter

        binding.btnSubmit.setOnClickListener{
            try {
                val created : Boolean = Entries.createEntry(this, entries, binding.etDate.text.toString(), binding.etWeight.text.toString().toFloat())
                if (created) {
                    entryNum = Entries.getEntryNum(this)
                    binding.tvFeedback.text = ""
                    Toast.makeText(this, resources.getString(R.string.entryAddedToast), Toast.LENGTH_SHORT).show()
                } else {
                    binding.tvFeedback.text = getString(R.string.invalidDateInput)
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
            Entries.setEnterDataSortBy(this, "Old")
            Utils.recreateActivity(this)
        }
        binding.btnSortByRecent.setOnClickListener {
            Entries.setEnterDataSortBy(this, "ID")
            Utils.recreateActivity(this)
        }
        binding.btnSortByNew.setOnClickListener {
            Entries.setEnterDataSortBy(this, "New")
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

    override fun onDeleteClick(entryId: Int) {
        deleteEntry(entryId)
    }
    @SuppressLint("ApplySharedPref")
    fun deleteEntry(id: Int) {
        val data = Entries.data(this)
        val entryNum = Entries.getEntryNum(this)
        data.edit().remove("id-$id").commit()
        data.edit().remove("date-$id").commit()
        data.edit().remove("weight-$id").commit()
        data.edit().remove("year-$id").commit()
        data.edit().remove("month-$id").commit()
        data.edit().remove("day-$id").commit()
        data.edit().putInt("entryNum", entryNum - 1).commit()
        Utils.recreateActivity(this)
        Toast.makeText(this, resources.getString(R.string.entryDeletedToast), Toast.LENGTH_SHORT).show()
    }

}