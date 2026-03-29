package com.example.myapplication.stats

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.R
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
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class StatsActivity : AppCompatActivity() {

    private val viewModel: StatsViewModel by viewModels()

    private lateinit var tvMetrics: TextView
    private lateinit var tvEnergy: TextView
    private lateinit var barChart: BarChart
    private lateinit var pieChart: PieChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats)

        tvMetrics = findViewById(R.id.tvMetrics)
        tvEnergy = findViewById(R.id.tvEnergy)
        barChart = findViewById(R.id.barChart)
        pieChart = findViewById(R.id.pieChart)

        findViewById<Button>(R.id.btnSync).setOnClickListener {
            viewModel.syncToFirestore()
            viewModel.syncFromFirestore()
        }

        findViewById<Button>(R.id.btnReset).setOnClickListener {
            viewModel.resetStats()
        }

        lifecycleScope.launch {
            val dataStore = StatsDataStore(applicationContext)
            dataStore.incrementStatsVisits()
            
            viewModel.statsState.collectLatest { stats ->
                updateUi(stats)
                setupBarChart(stats)
                setupPieChart(stats)
            }
        }
    }

    private fun updateUi(stats: StatsUiModel) {
        tvMetrics.text = """
            Aperturas App: ${stats.appOpens}
            Rutas Añadidas: ${stats.addItemCount}
            Rutas Eliminadas: ${stats.deleteItemCount}
            Tiempo Total (minutos): ${stats.usageMinutes}
        """.trimIndent()

        val usageHours = stats.usageMinutes / 60.0
        val estimatedKwh = usageHours * 0.015
        val estimatedCo2Kg = estimatedKwh * 0.23

        tvEnergy.text = String.format("Energía Estimada: %.4f kWh\nCO2 Estimado: %.4f Kg", estimatedKwh, estimatedCo2Kg)
    }

    private fun setupBarChart(stats: StatsUiModel) {
        val entries = ArrayList<BarEntry>()
        entries.add(BarEntry(0f, stats.appOpens.toFloat()))
        entries.add(BarEntry(1f, stats.addItemCount.toFloat()))
        entries.add(BarEntry(2f, stats.deleteItemCount.toFloat()))

        val dataSet = BarDataSet(entries, "Volumen General")
        dataSet.colors = listOf(Color.parseColor("#4CAF50"), Color.parseColor("#FF9800"), Color.parseColor("#E91E63"))
        dataSet.valueTextSize = 12f
        dataSet.valueTextColor = Color.BLACK

        val barData = BarData(dataSet)
        barChart.data = barData

        val labels = listOf("Aperturas", "Añadidos", "Borrados")
        val xAxis = barChart.xAxis
        xAxis.valueFormatter = IndexAxisValueFormatter(labels)
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.granularity = 1f
        xAxis.setDrawGridLines(false)

        barChart.description.isEnabled = false
        barChart.legend.isEnabled = true
        barChart.axisLeft.axisMinimum = 0f
        barChart.axisRight.isEnabled = false
        barChart.animateY(800)
        barChart.invalidate()
    }

    private fun setupPieChart(stats: StatsUiModel) {
        val entries = ArrayList<PieEntry>()
        if (stats.homeVisits == 0 && stats.statsVisits == 0) {
            entries.add(PieEntry(1f, "Sin Datos"))
        } else {
            entries.add(PieEntry(stats.homeVisits.toFloat(), "Home"))
            entries.add(PieEntry(stats.statsVisits.toFloat(), "Estadísticas"))
        }

        val dataSet = PieDataSet(entries, "Distribución de Visitas")
        dataSet.colors = listOf(Color.parseColor("#2196F3"), Color.parseColor("#9C27B0"))
        dataSet.valueTextSize = 14f
        dataSet.valueTextColor = Color.WHITE

        val pieData = PieData(dataSet)
        pieChart.data = pieData
        pieChart.description.isEnabled = false
        pieChart.legend.isEnabled = true
        pieChart.centerText = "Pantallas"
        pieChart.setCenterTextSize(16f)
        pieChart.animateY(800)
        pieChart.invalidate()
    }
}
