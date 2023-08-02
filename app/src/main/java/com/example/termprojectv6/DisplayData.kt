package com.example.termprojectv6

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.marginLeft
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.model.GradientColor
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.max
import kotlin.math.min
import kotlin.math.round
import kotlin.random.Random

class DisplayData : AppCompatActivity() {

    lateinit var btnMain : Button
    lateinit var btnEnterData : Button
    lateinit var btnDisplayData : Button
    lateinit var linearLayoutBottom : LinearLayout
    lateinit var entries : ArrayList<Entry>
    var entryNum = 0
    var colorSchemeId = 0
    var colorStartId = 0
    var colorEndId = 0
    var axisMax = 0f
    val colorSchemes : Array<String> = arrayOf("Gray", "Blue", "Indigo", "Purple", "Pink",
        "Red", "Orange", "Yellow", "Green", "Teal", "Cyan")

    // chart
    lateinit var chart : BarChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_data)

        supportActionBar?.title = resources.getString(R.string.DisplayData_title)
        // navigation
        btnMain = findViewById(R.id.btnMain)
        btnMain.setOnClickListener {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
        }
        btnEnterData = findViewById(R.id.btnEnterData)
        btnEnterData.setOnClickListener {
            val i = Intent(this, EnterData::class.java)
            startActivity(i)
        }
        btnDisplayData = findViewById(R.id.btnDisplayData)
        btnDisplayData.setOnClickListener {
            val intent = Intent(this, DisplayData::class.java)
            startActivity(intent)
        }
        linearLayoutBottom = findViewById(R.id.linearLayoutBottom)

        // get data
        val data = getSharedPreferences("data", MODE_PRIVATE) // getter
        val editor = data.edit() // setter
        entryNum = data.getInt("entries", 0)
        colorSchemeId = data.getInt("colorSchemeId", 0)
        entries = ArrayList()
        for (i in 0 until entryNum) {
            entries.add(Entry(data.getInt("id-$i", 0),
                data.getString("date-$i", "n/a").toString(),
                data.getFloat("weight-$i", 0f)))
            axisMax = max(axisMax, entries[i].weight)
        }
        var axisMin = entries[0].weight
        for (i in 0 until entries.size) {
            axisMin = min(axisMin, entries[i].weight)
        }
        axisMin = floor((axisMin-2.5f)/5) * 5
        axisMax = ceil((axisMax+2.5f)/5) * 5


        // chart example
        chart = findViewById(R.id.chart)
        chart.setDrawBarShadow(false)
        chart.setDrawValueAboveBar(true)
        chart.getDescription().isEnabled = false
        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        chart.setMaxVisibleValueCount(60)
        // scaling can now only be done on x- and y-axis separately
        chart.setPinchZoom(false)
        chart.setDrawGridBackground(false)
        //        Legend l = chart.getLegend();
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
//        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
//        l.setDrawInside(false);
//        l.setForm(Legend.LegendForm.SQUARE);
//        l.setFormSize(9f);
//        l.setTextSize(11f);
//        l.setXEntrySpace(4f);
//        setData(entries.size, 200f)
        chart.axisLeft.setStartAtZero(false)
        chart.axisLeft.axisMinimum = axisMin
        chart.axisLeft.axisMaximum = axisMax
        chart.xAxis.granularity = 1f
//        chart.animateY(1000)
        passData(entries, colorSchemeId);
        chart.setBackgroundColor(Color.WHITE)
        chart.legend.isEnabled = false
        chart.axisLeft.isEnabled = false
        chart.axisRight.isEnabled = false
        applyColorScheme()
    }

    fun passData(entries: ArrayList<Entry>, colorSchemeId: Int) {
        val values = ArrayList<BarEntry>()
        for (i in 0 until entries.size) {
            values.add(BarEntry(i.toFloat(), entries[i].weight))
        }
        val set1 : BarDataSet
        if (chart.data != null && chart.data.dataSetCount > 0) {
            set1 = chart.data.getDataSetByIndex(0) as BarDataSet
            set1.values = values
            chart.data.notifyDataChanged()
            chart.notifyDataSetChanged()
        } else {
            set1 = BarDataSet(values, "Weight in lbs")
            if (colorSchemeId == 0) { colorStartId = resources.getColor(R.color.gray9);
                colorEndId = resources.getColor(R.color.gray4)}
            if (colorSchemeId == 1) { colorStartId = resources.getColor(R.color.blue9);
                colorEndId = resources.getColor(R.color.blue4)}
            if (colorSchemeId == 2) { colorStartId = resources.getColor(R.color.indigo9);
                colorEndId = resources.getColor(R.color.indigo4)}
            if (colorSchemeId == 3) { colorStartId = resources.getColor(R.color.purple9);
                colorEndId = resources.getColor(R.color.purple4)}
            if (colorSchemeId == 4) { colorStartId = resources.getColor(R.color.pink9);
                colorEndId = resources.getColor(R.color.pink4)}
            if (colorSchemeId == 5) { colorStartId = resources.getColor(R.color.red9);
                colorEndId = resources.getColor(R.color.red4)}
            if (colorSchemeId == 6) { colorStartId = resources.getColor(R.color.orange9);
                colorEndId = resources.getColor(R.color.orange4)}
            if (colorSchemeId == 7) { colorStartId = resources.getColor(R.color.yellow9);
                colorEndId = resources.getColor(R.color.yellow4)}
            if (colorSchemeId == 8) { colorStartId = resources.getColor(R.color.green9);
                colorEndId = resources.getColor(R.color.green4)}
            if (colorSchemeId == 9) { colorStartId = resources.getColor(R.color.teal9);
                colorEndId = resources.getColor(R.color.teal4)}
            if (colorSchemeId == 10) { colorStartId = resources.getColor(R.color.cyan9);
                colorEndId = resources.getColor(R.color.cyan4)}
            set1.setGradientColor(colorStartId, colorEndId)
            val dataSets = ArrayList<IBarDataSet>()
            dataSets.add(set1)
            val barData = BarData(dataSets)
            barData.setValueTextSize(10f)
            barData.barWidth = 0.9f
            chart.data = barData
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
            var colorSchemeId : Int = Random.nextInt(0,11)
            getSharedPreferences("data", MODE_PRIVATE).edit().putInt("colorSchemeId", colorSchemeId).commit()
            applyColorScheme(colorSchemeId)
            Toast.makeText(this, "Random color scheme ${colorSchemes[colorSchemeId]} applied", Toast.LENGTH_SHORT).show()
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

    fun applyColorScheme(colorSchemeId : Int = getSharedPreferences("data", MODE_PRIVATE).getInt("colorSchemeId", 1)) {
        when (colorSchemeId) {
            0 -> {
                window.setStatusBarColor(getResources().getColor(R.color.gray7))
                supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.gray7)))
                btnMain.background.setTint(resources.getColor(R.color.gray6))
                btnEnterData.background.setTint(resources.getColor(R.color.gray6))
                btnDisplayData.background.setTint(resources.getColor(R.color.gray6))
                linearLayoutBottom.background.setTint(resources.getColor(R.color.gray9))
            }
            1 -> {
                window.setStatusBarColor(getResources().getColor(R.color.blue7))
                supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.blue7)))
                chart.setBorderColor(resources.getColor(R.color.blue1))
                btnMain.background.setTint(resources.getColor(R.color.blue6))
                btnEnterData.background.setTint(resources.getColor(R.color.blue6))
                btnDisplayData.background.setTint(resources.getColor(R.color.blue6))
                linearLayoutBottom.background.setTint(resources.getColor(R.color.blue9))
            }
            2 -> {
                window.setStatusBarColor(getResources().getColor(R.color.indigo7))
                supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.indigo7)))
                btnMain.background.setTint(resources.getColor(R.color.indigo6))
                btnEnterData.background.setTint(resources.getColor(R.color.indigo6))
                btnDisplayData.background.setTint(resources.getColor(R.color.indigo6))
                linearLayoutBottom.background.setTint(resources.getColor(R.color.indigo9))
            }
            3 -> {
                window.setStatusBarColor(getResources().getColor(R.color.purple7))
                supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.purple7)))
                btnMain.background.setTint(resources.getColor(R.color.purple6))
                btnEnterData.background.setTint(resources.getColor(R.color.purple6))
                btnDisplayData.background.setTint(resources.getColor(R.color.purple6))
                linearLayoutBottom.background.setTint(resources.getColor(R.color.purple9))
            }
            4 -> {
                window.setStatusBarColor(getResources().getColor(R.color.pink7))
                supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.pink7)))
                btnMain.background.setTint(resources.getColor(R.color.pink6))
                btnEnterData.background.setTint(resources.getColor(R.color.pink6))
                btnDisplayData.background.setTint(resources.getColor(R.color.pink6))
                linearLayoutBottom.background.setTint(resources.getColor(R.color.pink9))
            }
            5 -> {
                window.setStatusBarColor(getResources().getColor(R.color.red7))
                supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.red7)))
                btnMain.background.setTint(resources.getColor(R.color.red6))
                btnEnterData.background.setTint(resources.getColor(R.color.red6))
                btnDisplayData.background.setTint(resources.getColor(R.color.red6))
                linearLayoutBottom.background.setTint(resources.getColor(R.color.red9))
            }
            6 -> {
                window.setStatusBarColor(getResources().getColor(R.color.orange7))
                supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.orange7)))
                btnMain.background.setTint(resources.getColor(R.color.orange6))
                btnEnterData.background.setTint(resources.getColor(R.color.orange6))
                btnDisplayData.background.setTint(resources.getColor(R.color.orange6))
                linearLayoutBottom.background.setTint(resources.getColor(R.color.orange9))
            }
            7 -> {
                window.setStatusBarColor(getResources().getColor(R.color.yellow7))
                supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.yellow7)))
                btnMain.background.setTint(resources.getColor(R.color.yellow6))
                btnEnterData.background.setTint(resources.getColor(R.color.yellow6))
                btnDisplayData.background.setTint(resources.getColor(R.color.yellow6))
                linearLayoutBottom.background.setTint(resources.getColor(R.color.yellow9))
            }
            8 -> {
                window.setStatusBarColor(getResources().getColor(R.color.green7))
                supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.green7)))
                btnMain.background.setTint(resources.getColor(R.color.green6))
                btnEnterData.background.setTint(resources.getColor(R.color.green6))
                btnDisplayData.background.setTint(resources.getColor(R.color.green6))
                linearLayoutBottom.background.setTint(resources.getColor(R.color.green9))
            }
            9 -> {
                window.setStatusBarColor(getResources().getColor(R.color.teal7))
                supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.teal7)))
                btnMain.background.setTint(resources.getColor(R.color.teal6))
                btnEnterData.background.setTint(resources.getColor(R.color.teal6))
                btnDisplayData.background.setTint(resources.getColor(R.color.teal6))
                linearLayoutBottom.background.setTint(resources.getColor(R.color.teal9))
            }
            10 -> {
                window.setStatusBarColor(getResources().getColor(R.color.cyan7))
                supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.cyan7)))
                btnMain.background.setTint(resources.getColor(R.color.cyan6))
                btnEnterData.background.setTint(resources.getColor(R.color.cyan6))
                btnDisplayData.background.setTint(resources.getColor(R.color.cyan6))
                linearLayoutBottom.background.setTint(resources.getColor(R.color.cyan9))
            }
        }
    }


}