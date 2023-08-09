package com.example.termprojectv6

import android.app.Activity
import android.content.Context
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
        // other
        // recycler view code
        val layoutManager: RecyclerView.LayoutManager
        val adapter: RecyclerView.Adapter<*>
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        // get data
        val data = Entries.data(this)
        var entryNum = Entries.getEntryNum(this)
        binding.tvFeedback.text = "${entryNum} entries"
        var entries2 = Entries.getEntries(this)
        // finish layout
        layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        adapter = RecyclerAdapter(entries2, this)
        recyclerView.adapter = adapter

        binding.btnSubmit.setOnClickListener{
            try {
                entries2 = Entries.createEntry(this, entries2, binding.etDate.text.toString(), binding.etWeight.text.toString().toFloat())
                entryNum++
                binding.tvFeedback.text = ""
                Utils.recreateActivity(this)
                Toast.makeText(this, "Entry[${entryNum-1}] added", Toast.LENGTH_SHORT).show()
            } catch (e: NumberFormatException) {
                binding.tvFeedback.text = getString(R.string.invalid_weight_or_date)
                return@setOnClickListener
            }
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
        } else if (item.itemId == R.id.menuSecondActivity) { Utils.openSecondActivity(this)
        } else { return super.onOptionsItemSelected(item)
        }
        return true
    }

    override fun onDeleteClick(entryId: Int) {
        deleteEntry(entryId)
    }
    fun deleteEntry(id: Int) {
        val data = Entries.data(this)
        val entryNum = Entries.getEntryNum(this)
        data.edit().remove("id2-$id").commit()
        data.edit().remove("date2-$id").commit()
        data.edit().remove("weight2-$id").commit()
        data.edit().remove("year2-$id").commit()
        data.edit().remove("month2-$id").commit()
        data.edit().remove("day2-$id").commit()
        data.edit().putInt("entries2", entryNum - 1).commit()
        Utils.recreateActivity(this)
        Toast.makeText(this, "Entry[${id}] deleted", Toast.LENGTH_SHORT).show()
    }

}