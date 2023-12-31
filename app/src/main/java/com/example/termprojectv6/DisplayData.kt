package com.example.termprojectv6

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.termprojectv6.databinding.ActivityDisplayDataBinding
import com.github.mikephil.charting.charts.BarChart
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

    private lateinit var chart : BarChart

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

        chart = findViewById(R.id.chart)
        chart.setDrawBarShadow(false)
        chart.setDrawValueAboveBar(true)
        chart.description.isEnabled = false
        chart.setMaxVisibleValueCount(60)
        chart.setPinchZoom(false)
        chart.setDrawGridBackground(false)
        chart.xAxis.granularity = 1f
        chart.animateX(250)
        try {
            if (Entries.getDisplayPreference(this) == "All") {
                displayAllData()
                binding.btnDisplayAll.background.setTint(Utils.getColor(this, 5))
            } else if (Entries.getDisplayPreference(this) == "Monthly") {
                displayMonthlyData()
                binding.btnDisplayMonthly.background.setTint(Utils.getColor(this, 5))
            } else {
                displayFutureData()
                binding.btnDisplayFuture.background.setTint(Utils.getColor(this, 5))
            }
        }
        catch (_: Exception) {

        }
        chart.setBackgroundColor(Color.WHITE)
        chart.legend.isEnabled = true
        chart.axisLeft.isEnabled = true
        chart.axisRight.isEnabled = false


        binding.btnDisplayAll.setOnClickListener {
            selectAllData()
        }
        binding.btnDisplayMonthly.setOnClickListener {
            selectMonthlyData()
        }
        binding.btnDisplayFuture.setOnClickListener {
            selectFutureData()
        }

    }

    private fun passAllData(entries: ArrayList<Entry>) {
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
            set1 = BarDataSet(values, resources.getString(R.string.legendWeightInLbs))
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
    private fun passMonthlyData(entries: ArrayList<EntryGroup>) {
        val valuesLow = ArrayList<BarEntry>()
        val valuesAvg = ArrayList<BarEntry>()
        val valuesHigh = ArrayList<BarEntry>()
        for (i in 0 until entries.size) {
            valuesLow.add(BarEntry(i.toFloat(), entries[i].minWeight))
            valuesAvg.add(BarEntry(i.toFloat(), entries[i].avgWeight))
            valuesHigh.add(BarEntry(i.toFloat(), entries[i].maxWeight))
        }
        val set1 = BarDataSet(valuesLow, resources.getString(R.string.legendLowestWeight))
        val set2 = BarDataSet(valuesAvg, resources.getString(R.string.legendAverageWeight))
        val set3 = BarDataSet(valuesHigh, resources.getString(R.string.legendHighestWeight))
        val gradientFills1: MutableList<GradientColor> = ArrayList()
        val gradientFills2: MutableList<GradientColor> = ArrayList()
        val gradientFills3: MutableList<GradientColor> = ArrayList()
        gradientFills1.add(GradientColor(Utils.getColor(this,10), Utils.getColor(this, 7)))
        gradientFills2.add(GradientColor(Utils.getColor(this,10), Utils.getColor(this, 5)))
        gradientFills3.add(GradientColor(Utils.getColor(this,10), Utils.getColor(this, 3)))
        set1.color = Utils.getColor(this, 7)
        set2.color = Utils.getColor(this, 5)
        set3.color = Utils.getColor(this, 3)
        set1.gradientColors = gradientFills1
        set2.gradientColors = gradientFills2
        set3.gradientColors = gradientFills3
        val dataSets = ArrayList<IBarDataSet>()
        dataSets.add(set3)
        dataSets.add(set2)
        dataSets.add(set1)
        val barData = BarData(dataSets)
        barData.setValueTextSize(10f)
        barData.barWidth = 0.9f
        chart.data = barData
    }

    private fun selectAllData() {
        Entries.setDisplayPreference(this, "All")
        Utils.recreateActivity(this)
    }
    private fun selectMonthlyData() {
        Entries.setDisplayPreference(this, "Monthly")
        Utils.recreateActivity(this)
    }
    private fun selectFutureData() {
        Entries.setDisplayPreference(this, "Future")
        Utils.recreateActivity(this)
    }

    private fun displayAllData() {
        val entries = Entries.getEntriesSortByOld(this)
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
    }
    private fun displayMonthlyData() {
        val entries = Entries.groupByMonth(this)
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
    }
    private fun displayFutureData() {
        var entries = Entries.groupByMonth(this)
        entries = Entries.predictionComplex(entries)
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