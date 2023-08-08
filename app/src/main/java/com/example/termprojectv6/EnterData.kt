package com.example.termprojectv6

import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.random.Random

class EnterData : AppCompatActivity() {

    lateinit var btnSubmit : Button
    lateinit var tvFeedback : TextView
    lateinit var btnMain : Button
    lateinit var btnEnterData : Button
    lateinit var btnDisplayData : Button
    lateinit var linearLayoutBottom : LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Utils.applyColorScheme(this);
        setContentView(R.layout.activity_enter_data)

        supportActionBar?.title = resources.getString(R.string.EnterData_title)

        val etDate : EditText = findViewById(R.id.etDate)
        val etWeight : EditText = findViewById(R.id.etWeight)
        btnSubmit = findViewById(R.id.btnSubmit)

        // navigation
        btnMain = findViewById(R.id.btnMain)
        btnMain.setOnClickListener {
            val i = Intent(this@EnterData, MainActivity::class.java)
            startActivity(i)
        }
        btnEnterData = findViewById(R.id.btnEnterData)
        btnEnterData.setOnClickListener {
            val i = Intent(this@EnterData, EnterData::class.java)
            startActivity(i)
        }
        btnDisplayData = findViewById(R.id.btnDisplayData)
        btnDisplayData.setOnClickListener {
            val intent = Intent(this, DisplayData::class.java)
            startActivity(intent)
        }
        linearLayoutBottom = findViewById(R.id.linearLayoutBottom)
        // other
        tvFeedback = findViewById(R.id.tvFeedback)
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

        btnSubmit.setOnClickListener{
            try {
                entries.add(Entry(entryNum, etDate.text.toString(), etWeight.text.toString().toFloat()))
                editor.putInt("id-$entryNum", entries[entryNum].id).apply()
                editor.putString("date-$entryNum", entries[entryNum].date).apply()
                editor.putFloat("weight-$entryNum", entries[entryNum].weight).apply()
                entryNum++
                editor.putInt("entries", entryNum).apply()
            } catch (e: NumberFormatException) {
                tvFeedback.text = "Please enter a valid weight and date"
                return@setOnClickListener
            }
            tvFeedback.text = ""
            Toast.makeText(this, "Entry[${entryNum-1}] added", Toast.LENGTH_SHORT).show()
        }

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
            Utils.randomizeColorScheme(this)
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

}