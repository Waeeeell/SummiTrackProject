package com.example.myapplication.stats

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class StatsFirestoreRepository {
    private val db = FirebaseFirestore.getInstance()
    private val docRef = db.collection("users").document("demoUser").collection("stats").document("global")

    suspend fun getStats(): StatsFirestoreModel? {
        return try {
            val snapshot = docRef.get().await()
            if (snapshot.exists()) {
                snapshot.toObject(StatsFirestoreModel::class.java)
            } else null
        } catch (e: Exception) {
            null
        }
    }

    suspend fun saveStats(stats: StatsUiModel) {
        try {
            val model = StatsFirestoreModel(
                appOpens = stats.appOpens,
                homeVisits = stats.homeVisits,
                statsVisits = stats.statsVisits,
                addItemCount = stats.addItemCount,
                deleteItemCount = stats.deleteItemCount,
                usageMinutes = stats.usageMinutes
            )
            docRef.set(model).await()
        } catch (e: Exception) {
            // Ignorar en entorno demo/local sin conexion real
        }
    }
}
