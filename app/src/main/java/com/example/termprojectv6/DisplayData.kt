package com.example.termprojectv6

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.termprojectv6.databinding.ActivityDisplayDataBinding
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.model.GradientColor
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.max
import kotlin.math.min

class DisplayData : AppCompatActivity() {

    lateinit var chart : BarChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Utils.applyColorScheme(this)
        val binding = ActivityDisplayDataBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportActionBar?.title = resources.getString(R.string.DisplayData_title)
        val btnMain : Button = findViewById(R.id.btnMain)
        btnMain.setOnClickListener { Utils.openMain(this) }
        val btnEnterData : Button = findViewById(R.id.btnEnterData)
        btnEnterData.setOnClickListener { Utils.openEnterData(this) }
        val btnDisplayData : Button = findViewById(R.id.btnDisplayData)
        btnDisplayData.setOnClickListener { Utils.openDisplayData(this) }

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
//        val l = chart.getLegend();
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
        chart.xAxis.granularity = 1f
//        chart.animateY(1000)
        if (Entries.getDisplayPreference(this) == "Monthly") {
            displayMonthlyData()
        } else {
            displayAllData()
        }
        chart.setBackgroundColor(Color.WHITE)
        chart.legend.isEnabled = true
        chart.axisLeft.isEnabled = true
        chart.axisRight.isEnabled = false


        binding.btnAll.setOnClickListener {
            selectAllData()
        }
        binding.btnMonthly.setOnClickListener {
            selectMonthlyData()
        }

    }

    fun passAllData(entries: ArrayList<Entry>) {
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
            val gradientFills: MutableList<GradientColor> = ArrayList()
            gradientFills.add(GradientColor(Utils.getColor(this,10), Utils.getColor(this, 5)))
            gradientFills.add(GradientColor(Utils.getColor(this,9), Utils.getColor(this, 4)))
            gradientFills.add(GradientColor(Utils.getColor(this,8), Utils.getColor(this, 3)))
            gradientFills.add(GradientColor(Utils.getColor(this,9), Utils.getColor(this, 4)))
            set1.color = Utils.getColor(this, 5)
            set1.gradientColors = gradientFills
            val dataSets = ArrayList<IBarDataSet>()
            dataSets.add(set1)
            val barData = BarData(dataSets)
            barData.setValueTextSize(10f)
            barData.barWidth = 0.9f
            chart.data = barData
        }
    }
    fun passMonthlyData(entries: ArrayList<EntryGroup>) {
        val valuesLow = ArrayList<BarEntry>()
        val valuesAvg = ArrayList<BarEntry>()
        val valuesHigh = ArrayList<BarEntry>()
        for (i in 0 until entries.size) {
            valuesLow.add(BarEntry(i.toFloat(), entries[i].minWeight))
            valuesAvg.add(BarEntry(i.toFloat(), entries[i].avgWeight))
            valuesHigh.add(BarEntry(i.toFloat(), entries[i].maxWeight))
        }
        val set1 = BarDataSet(valuesLow, "Lowest weight")
        val set2 = BarDataSet(valuesAvg, "Average Weight")
        val set3 = BarDataSet(valuesHigh, "Highest Weight")
//        val gradientFills1: MutableList<GradientColor> = ArrayList()
//        val gradientFills2: MutableList<GradientColor> = ArrayList()
//        val gradientFills3: MutableList<GradientColor> = ArrayList()
//        gradientFills1.add(GradientColor(Utils.getColor(this,10), Utils.getColor(this, 6)))
//        gradientFills1.add(GradientColor(Utils.getColor(this,9), Utils.getColor(this, 5)))
//        gradientFills1.add(GradientColor(Utils.getColor(this,8), Utils.getColor(this, 4)))
//        gradientFills1.add(GradientColor(Utils.getColor(this,9), Utils.getColor(this, 5)))
//        gradientFills2.add(GradientColor(Utils.getColor(this,10), Utils.getColor(this, 5)))
//        gradientFills2.add(GradientColor(Utils.getColor(this,9), Utils.getColor(this, 4)))
//        gradientFills2.add(GradientColor(Utils.getColor(this,8), Utils.getColor(this, 3)))
//        gradientFills2.add(GradientColor(Utils.getColor(this,9), Utils.getColor(this, 4)))
//        gradientFills3.add(GradientColor(Utils.getColor(this,10), Utils.getColor(this, 4)))
//        gradientFills3.add(GradientColor(Utils.getColor(this,9), Utils.getColor(this, 3)))
//        gradientFills3.add(GradientColor(Utils.getColor(this,8), Utils.getColor(this, 2)))
//        gradientFills3.add(GradientColor(Utils.getColor(this,9), Utils.getColor(this, 3)))
        set1.color = Utils.getColor(this, 7)
//        set1.gradientColors = gradientFills1
        set2.color = Utils.getColor(this, 5)
//        set2.gradientColors = gradientFills2
        set3.color = Utils.getColor(this, 3)
//        set3.gradientColors = gradientFills3
        val dataSets = ArrayList<IBarDataSet>()
        dataSets.add(set3)
        dataSets.add(set2)
        dataSets.add(set1)
        val barData = BarData(dataSets)
        barData.setValueTextSize(10f)
        barData.barWidth = 0.9f
        chart.data = barData
    }

    fun selectAllData() {
        Entries.setDisplayPreference(this, "All")
        Utils.recreateActivity(this)
    }
    fun selectMonthlyData() {
        Entries.setDisplayPreference(this, "Monthly")
        Utils.recreateActivity(this)
    }

    fun displayAllData() {
        val entries = Entries.getEntries(this)
        var axisMin = entries[0].weight
        var axisMax = entries[0].weight
        for (i in 0 until entries.size) {
            axisMin = min(axisMin, entries[i].weight)
            axisMax = max(axisMax, entries[i].weight)
        }
        axisMin = floor((axisMin-2.5f)/5) * 5
        axisMax = ceil((axisMax+2.5f)/5) * 5
        chart.axisLeft.axisMinimum = axisMin
        chart.axisLeft.axisMaximum = axisMax
        passAllData(entries)
        Toast.makeText(this, "Displaying All Data", Toast.LENGTH_SHORT).show()
    }
    fun displayMonthlyData() {
        var entries = Entries.groupByMonth(this)
        entries = Entries.predictionSimple(this, entries)
        var axisMin = entries[0].minWeight
        var axisMax = entries[0].maxWeight
        for (i in 0 until entries.size) {
            axisMin = min(axisMin, entries[i].minWeight)
            axisMax = max(axisMax, entries[i].maxWeight)
        }
        axisMin = floor((axisMin-2.5f)/5) * 5
        axisMax = ceil((axisMax+2.5f)/5) * 5
        chart.axisLeft.axisMinimum = axisMin
        chart.axisLeft.axisMaximum = axisMax
        passMonthlyData(entries)
        Toast.makeText(this, "Displaying Monthly Data", Toast.LENGTH_SHORT).show()
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