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

        val statsManager = StatsManager(this)
        lifecycleScope.launch {
            statsManager.syncFromFirestore()
            statsManager.incrementStat(StatsManager.APP_OPENS)
        }

        logo.animate().setDuration(2000).alpha(1f).withEndAction {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}