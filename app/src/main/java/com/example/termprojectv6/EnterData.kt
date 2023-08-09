package com.example.termprojectv6

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

class EnterData : AppCompatActivity() {

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
        val data = getSharedPreferences("data", Context.MODE_PRIVATE)
        val editor = data.edit() // setter
        var entryNum = data.getInt("entries", 0)
        val entries : ArrayList<Entry> = ArrayList()
        for (i in 0 until entryNum) {
            entries.add(Entry(data.getInt("id-$i", 0),
                data.getString("date-$i", "n/a").toString(),
                data.getFloat("weight-$i", 0f)))
        }
        // finish layout
        layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        adapter = RecyclerAdapter(data.getInt("colorSchemeId", 1), entries)
        recyclerView.adapter = adapter

        binding.btnSubmit.setOnClickListener{
            try {
                entries.add(Entry(entryNum, binding.etDate.text.toString(), binding.etWeight.text.toString().toFloat()))
                editor.putInt("id-$entryNum", entries[entryNum].id).apply()
                editor.putString("date-$entryNum", entries[entryNum].date).apply()
                editor.putFloat("weight-$entryNum", entries[entryNum].weight).apply()
                entryNum++
                editor.putInt("entries", entryNum).apply()
            } catch (e: NumberFormatException) {
                binding.tvFeedback.text = "Please enter a valid weight and date"
                return@setOnClickListener
            }
            binding.tvFeedback.text = ""
            Toast.makeText(this, "Entry[${entryNum-1}] added", Toast.LENGTH_SHORT).show()
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