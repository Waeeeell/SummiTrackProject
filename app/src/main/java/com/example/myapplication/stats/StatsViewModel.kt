package com.example.myapplication.stats

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class StatsViewModel(application: Application) : AndroidViewModel(application) {
    val dataStore = StatsDataStore(application)
    private val repository = StatsFirestoreRepository()

    val statsState: StateFlow<StatsUiModel> = dataStore.statsFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = StatsUiModel()
    )

    fun syncFromFirestore() {
        viewModelScope.launch {
            val remote = repository.getStats()
            if (remote != null) {
                dataStore.overwriteWithFirestore(remote)
            }
        }
    }

    fun syncToFirestore() {
        viewModelScope.launch {
            val current = dataStore.statsFlow.first()
            repository.saveStats(current)
        }
    }

    fun resetStats() {
        viewModelScope.launch {
            dataStore.resetStats()
            repository.saveStats(StatsUiModel())
        }
    }
}
