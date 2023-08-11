package com.example.termprojectv6

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.termprojectv6.Adapter.DataAdapter
import com.example.termprojectv6.Model.DataModel
import com.example.termprojectv6.Utilility.DatabaseHelper
import com.example.termprojectv6.databinding.ActivitySecondBinding
import java.util.Collections.reverse

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding
    lateinit var recyclerView: RecyclerView
    lateinit var db: DatabaseHelper
    lateinit var dataList: List<DataModel>
    lateinit var adapter: DataAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Utils.applyColorScheme(this)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportActionBar?.title = resources.getString(R.string.SecondActivity_title)
        val btnMain: Button = findViewById(R.id.btnMain)
        btnMain.setOnClickListener { Utils.openMain(this) }
        val btnEnterData: Button = findViewById(R.id.btnEnterData)
        btnEnterData.setOnClickListener { Utils.openEnterData(this) }
        val btnDisplayData: Button = findViewById(R.id.btnDisplayData)
        btnDisplayData.setOnClickListener { Utils.openDisplayData(this) }

        // get data
        recyclerView = findViewById(R.id.recyclerView)
        db = DatabaseHelper(this@SecondActivity)
        dataList = java.util.ArrayList<DataModel>()
        adapter = DataAdapter(db, this@SecondActivity)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = this.adapter

        dataList = db.allEntries
        reverse(dataList)
        adapter.setEntries(dataList)

        val etDate : EditText = findViewById(R.id.etDate)
        val etWeight : EditText = findViewById(R.id.etWeight)

        binding.btnSubmit.setOnClickListener({
            val item = DataModel()
            item.date = binding.etDate.text.toString()
            item.weight = binding.etWeight.text.toString().toFloat()
            db.insertEntry(item)
            Utils.recreateActivity(this)
            Toast.makeText(this, "Entry added to database", Toast.LENGTH_SHORT).show()
        })
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