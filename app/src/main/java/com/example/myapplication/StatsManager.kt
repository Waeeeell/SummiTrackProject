package com.example.myapplication

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.tasks.await

private val Context.dataStore by preferencesDataStore(name = "user_stats")

class StatsManager(private val context: Context) {
    private val db = FirebaseFirestore.getInstance()
    private val docRef = db.collection("user_stats").document("global_stats")

    companion object {
        val APP_OPENS = intPreferencesKey("app_opens")
        val ACTIVITY_CLOSES = intPreferencesKey("activity_closes")
        val ITEMS_ADDED = intPreferencesKey("items_added")
        val ITEMS_DELETED = intPreferencesKey("items_deleted")
        val TOTAL_USAGE_TIME_SECONDS = longPreferencesKey("total_usage_time")
    }

    suspend fun syncFromFirestore() {
        try {
            val snapshot = docRef.get().await()
            if (snapshot.exists()) {
                val data = snapshot.data
                if (data != null) {
                    context.dataStore.edit { prefs ->
                        prefs[APP_OPENS] = (data["app_opens"] as? Long)?.toInt() ?: prefs[APP_OPENS] ?: 0
                        prefs[ACTIVITY_CLOSES] = (data["activity_closes"] as? Long)?.toInt() ?: prefs[ACTIVITY_CLOSES] ?: 0
                        prefs[ITEMS_ADDED] = (data["items_added"] as? Long)?.toInt() ?: prefs[ITEMS_ADDED] ?: 0
                        prefs[ITEMS_DELETED] = (data["items_deleted"] as? Long)?.toInt() ?: prefs[ITEMS_DELETED] ?: 0
                        prefs[TOTAL_USAGE_TIME_SECONDS] = (data["total_usage_time"] as? Long) ?: prefs[TOTAL_USAGE_TIME_SECONDS] ?: 0L
                    }
                }
            }
        } catch (e: Exception) {
            // Ignorar error basic
        }
    }

    suspend fun syncToFirestore() {
        try {
            val prefs = context.dataStore.data.first()
            val data = hashMapOf(
                "app_opens" to (prefs[APP_OPENS] ?: 0),
                "activity_closes" to (prefs[ACTIVITY_CLOSES] ?: 0),
                "items_added" to (prefs[ITEMS_ADDED] ?: 0),
                "items_deleted" to (prefs[ITEMS_DELETED] ?: 0),
                "total_usage_time" to (prefs[TOTAL_USAGE_TIME_SECONDS] ?: 0L)
            )
            docRef.set(data).await()
        } catch (e: Exception) {
            // Ignorar error basic
        }
    }

    suspend fun incrementStat(key: androidx.datastore.preferences.core.Preferences.Key<Int>) {
        context.dataStore.edit { prefs ->
            val current = prefs[key] ?: 0
            prefs[key] = current + 1
        }
        syncToFirestore()
    }

    suspend fun addUsageTime(seconds: Long) {
        context.dataStore.edit { prefs ->
            val current = prefs[TOTAL_USAGE_TIME_SECONDS] ?: 0L
            prefs[TOTAL_USAGE_TIME_SECONDS] = current + seconds
        }
        syncToFirestore()
    }

    suspend fun getStat(key: androidx.datastore.preferences.core.Preferences.Key<Int>): Int {
        val prefs = context.dataStore.data.first()
        return prefs[key] ?: 0
    }

    suspend fun getLongStat(key: androidx.datastore.preferences.core.Preferences.Key<Long>): Long {
        val prefs = context.dataStore.data.first()
        return prefs[key] ?: 0L
    }
}
