package com.example.myapplication

import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import kotlinx.coroutines.launch

class StatsActivity : AppCompatActivity() {

    private lateinit var barChart: BarChart
    private lateinit var pieChart: PieChart
    private lateinit var tvEnergy: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats)

        barChart = findViewById(R.id.barChart)
        pieChart = findViewById(R.id.pieChart)
        tvEnergy = findViewById(R.id.tvEnergy)

        loadStats()
    }

    private fun loadStats() {
        val statsManager = StatsManager(this)
        
        lifecycleScope.launch {
            val appOpens = statsManager.getStat(StatsManager.APP_OPENS)
            val activityCloses = statsManager.getStat(StatsManager.ACTIVITY_CLOSES)
            val itemsAdded = statsManager.getStat(StatsManager.ITEMS_ADDED)
            val itemsDeleted = statsManager.getStat(StatsManager.ITEMS_DELETED)
            val totalUsageSeconds = statsManager.getLongStat(StatsManager.TOTAL_USAGE_TIME_SECONDS)

            setupBarChart(appOpens, activityCloses, itemsAdded, itemsDeleted)
            setupPieChart(itemsAdded, itemsDeleted)
            calculateEnergy(totalUsageSeconds)
        }
    }

    private fun setupBarChart(opens: Int, closes: Int, added: Int, deleted: Int) {
        val entries = ArrayList<BarEntry>()
        entries.add(BarEntry(0f, opens.toFloat()))
        entries.add(BarEntry(1f, closes.toFloat()))
        entries.add(BarEntry(2f, added.toFloat()))
        entries.add(BarEntry(3f, deleted.toFloat()))

        val dataSet = BarDataSet(entries, "Estadístiques Generals")
        val colors = listOf(Color.parseColor("#4CAF50"), Color.parseColor("#2196F3"), Color.parseColor("#FF9800"), Color.parseColor("#E91E63"))
        dataSet.colors = colors
        dataSet.valueTextSize = 12f
        dataSet.valueTextColor = Color.BLACK

        val barData = BarData(dataSet)
        barChart.data = barData

        val labels = listOf("Obertures", "Tancaments", "Afegits", "Esborrats")
        val xAxis = barChart.xAxis
        xAxis.valueFormatter = IndexAxisValueFormatter(labels)
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.granularity = 1f
        xAxis.setDrawGridLines(false)

        barChart.description.isEnabled = false
        barChart.legend.isEnabled = true
        barChart.axisLeft.axisMinimum = 0f
        barChart.axisRight.isEnabled = false
        barChart.animateY(1000)
        barChart.invalidate()
    }

    private fun setupPieChart(added: Int, deleted: Int) {
        val entries = ArrayList<PieEntry>()
        if (added == 0 && deleted == 0) {
            entries.add(PieEntry(1f, "Sense dades"))
        } else {
            entries.add(PieEntry(added.toFloat(), "Afegides"))
            entries.add(PieEntry(deleted.toFloat(), "Eliminades"))
        }

        val dataSet = PieDataSet(entries, "Activitat Elements")
        val colors = listOf(Color.parseColor("#8BC34A"), Color.parseColor("#F44336"))
        dataSet.colors = colors
        dataSet.valueTextSize = 14f
        dataSet.valueTextColor = Color.WHITE

        val pieData = PieData(dataSet)
        pieChart.data = pieData
        pieChart.description.isEnabled = false
        pieChart.legend.isEnabled = true
        pieChart.centerText = "Elements"
        pieChart.setCenterTextSize(16f)
        pieChart.animateY(1000)
        pieChart.invalidate()
    }

    private fun calculateEnergy(totalSeconds: Long) {
        val hours = totalSeconds / 3600.0
        // Estimación aprox: 0.05 kWh por cada hora de uso de dispositivo.
        val kwh = hours * 0.05
        // Factor de emisión tipo: 0.2 kg CO2 por kWh.
        val co2 = kwh * 0.2

        val formattedText = String.format("Temps d'ús: %.2fh | Energia: %.4f kWh | CO2: %.4f kg", hours, kwh, co2)
        tvEnergy.text = formattedText
    }
}
