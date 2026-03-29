package com.example.myapplication.stats

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "user_stats_prefs")

class StatsDataStore(private val context: Context) {
    companion object {
        val APP_OPENS = intPreferencesKey("appOpens")
        val HOME_VISITS = intPreferencesKey("homeVisits")
        val STATS_VISITS = intPreferencesKey("statsVisits")
        val ADD_ITEM_COUNT = intPreferencesKey("addItemCount")
        val DELETE_ITEM_COUNT = intPreferencesKey("deleteItemCount")
        val USAGE_MINUTES = longPreferencesKey("usageMinutes")
    }

    val statsFlow: Flow<StatsUiModel> = context.dataStore.data.map { prefs ->
        StatsUiModel(
            appOpens = prefs[APP_OPENS] ?: 0,
            homeVisits = prefs[HOME_VISITS] ?: 0,
            statsVisits = prefs[STATS_VISITS] ?: 0,
            addItemCount = prefs[ADD_ITEM_COUNT] ?: 0,
            deleteItemCount = prefs[DELETE_ITEM_COUNT] ?: 0,
            usageMinutes = prefs[USAGE_MINUTES] ?: 0L
        )
    }

    suspend fun incrementAppOpens() = context.dataStore.edit { it[APP_OPENS] = (it[APP_OPENS] ?: 0) + 1 }
    suspend fun incrementHomeVisits() = context.dataStore.edit { it[HOME_VISITS] = (it[HOME_VISITS] ?: 0) + 1 }
    suspend fun incrementStatsVisits() = context.dataStore.edit { it[STATS_VISITS] = (it[STATS_VISITS] ?: 0) + 1 }
    suspend fun incrementAddItemCount() = context.dataStore.edit { it[ADD_ITEM_COUNT] = (it[ADD_ITEM_COUNT] ?: 0) + 1 }
    suspend fun incrementDeleteItemCount() = context.dataStore.edit { it[DELETE_ITEM_COUNT] = (it[DELETE_ITEM_COUNT] ?: 0) + 1 }

    suspend fun addUsageMinutes(minutes: Long) = context.dataStore.edit {
        val current = it[USAGE_MINUTES] ?: 0L
        it[USAGE_MINUTES] = current + minutes
    }

    suspend fun resetStats() = context.dataStore.edit { it.clear() }

    suspend fun overwriteWithFirestore(firestoreModel: StatsFirestoreModel) {
        context.dataStore.edit { prefs ->
            prefs[APP_OPENS] = firestoreModel.appOpens
            prefs[HOME_VISITS] = firestoreModel.homeVisits
            prefs[STATS_VISITS] = firestoreModel.statsVisits
            prefs[ADD_ITEM_COUNT] = firestoreModel.addItemCount
            prefs[DELETE_ITEM_COUNT] = firestoreModel.deleteItemCount
            prefs[USAGE_MINUTES] = firestoreModel.usageMinutes
        }
    }
}
