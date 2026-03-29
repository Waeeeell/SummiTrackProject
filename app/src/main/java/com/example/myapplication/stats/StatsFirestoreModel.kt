package com.example.myapplication.stats

data class StatsFirestoreModel(
    val appOpens: Int = 0,
    val homeVisits: Int = 0,
    val statsVisits: Int = 0,
    val addItemCount: Int = 0,
    val deleteItemCount: Int = 0,
    val usageMinutes: Long = 0L
)
