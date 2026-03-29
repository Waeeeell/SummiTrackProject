package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)
        val logo = findViewById<ImageView>(R.id.logo)

        logo.alpha = 0f

        val dataStore = com.example.myapplication.stats.StatsDataStore(this)
        val repository = com.example.myapplication.stats.StatsFirestoreRepository()
        lifecycleScope.launch {
            val remote = repository.getStats()
            if (remote != null) {
                dataStore.overwriteWithFirestore(remote)
            }
            dataStore.incrementAppOpens()
        }

        logo.animate().setDuration(2000).alpha(1f).withEndAction {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}