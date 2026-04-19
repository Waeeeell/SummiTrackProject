package com.example.myapplication

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch
import java.util.Locale

class menu_navegacion : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var perfil: ImageView
    private lateinit var botttomBar: BottomNavigationView
    private lateinit var viewModel: ActivitatViewModel
    private var speechRecognizer: SpeechRecognizer? = null
    private var tts: TextToSpeech? = null
    private val RECORD_AUDIO_REQUEST_CODE = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu_navegacion)

        // Inicialitzar ViewModel (compartit amb els fragments)
        viewModel = ViewModelProvider(this)[ActivitatViewModel::class.java]

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentRecyclerLista, fragment_home()).commit()

        initComponent()
        initListeners()
        initVoiceRecognition()
        tts = TextToSpeech(this, this)
    }

    private fun initVoiceRecognition() {
        val sharedPreferences = getSharedPreferences("settings", MODE_PRIVATE)
        val voiceEnabled = sharedPreferences.getBoolean("voice_enabled", false)

        if (voiceEnabled) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECORD_AUDIO), RECORD_AUDIO_REQUEST_CODE)
            } else {
                startListening()
            }
        }
    }

    private fun startListening() {
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())

        speechRecognizer?.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {}
            override fun onBeginningOfSpeech() {}
            override fun onRmsChanged(rmsdB: Float) {}
            override fun onBufferReceived(buffer: ByteArray?) {}
            override fun onEndOfSpeech() {}
            override fun onError(error: Int) {
                // Reiniciar si hi ha error per seguir escoltant si cal
                if (error == SpeechRecognizer.ERROR_NO_MATCH || error == SpeechRecognizer.ERROR_SPEECH_TIMEOUT) {
                    startListening()
                }
            }

            override fun onResults(results: Bundle?) {
                val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                if (matches != null && matches.isNotEmpty()) {
                    val command = matches[0].lowercase()
                    handleVoiceCommand(command)
                }
                startListening() // Seguir escoltant
            }

            override fun onPartialResults(partialResults: Bundle?) {}
            override fun onEvent(eventType: Int, params: Bundle?) {}
        })

        speechRecognizer?.startListening(intent)
    }

    private fun handleVoiceCommand(command: String) {
        when {
            command.contains("home") || command.contains("inici") || command.contains("casa") -> {
                botttomBar.selectedItemId = R.id.home
                speak("Anant a l'inici")
            }
            command.contains("actividad") || command.contains("activitat") || command.contains("nova") || command.contains("nueva") -> {
                botttomBar.selectedItemId = R.id.nuevaActividad
                speak("Anant a nova activitat")
            }
            command.contains("ajustes") || command.contains("configuració") || command.contains("settings") -> {
                botttomBar.selectedItemId = R.id.ajustes
                speak("Anant a ajustos")
            }
            command.contains("perfil") -> {
                speak("Anant al perfil")
                val intent = Intent(this, Menu_perfil::class.java)
                startActivity(intent)
            }
        }
    }

    private fun speak(text: String) {
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            tts?.language = Locale.getDefault()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == RECORD_AUDIO_REQUEST_CODE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startListening()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        speechRecognizer?.destroy()
        tts?.stop()
        tts?.shutdown()
    }

    private fun initComponent() {
        perfil = findViewById(R.id.fotoPerfil)
        botttomBar = findViewById(R.id.BottomBar)
        botttomBar.selectedItemId = R.id.home
    }

    private fun initListeners() {
        perfil.setOnClickListener {
            val intent = Intent(this, Menu_perfil::class.java)
            startActivity(intent)
        }

        botttomBar.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentRecyclerLista, fragment_home()).commit()
                    true
                }

                R.id.nuevaActividad -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentRecyclerLista, fragment_add_activity()).commit()
                    true
                }

                R.id.ajustes -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentRecyclerLista, fragment_settings()).commit()
                    true
                }

                else -> false
            }
        }
    }

    private var startTimeMillis: Long = 0L

    override fun onStart() {
        super.onStart()
        startTimeMillis = System.currentTimeMillis()
    }

    override fun onStop() {
        super.onStop()
        val elapsedMinutes = (System.currentTimeMillis() - startTimeMillis) / (1000 * 60)
        if (elapsedMinutes > 0) {
            val dataStore = com.example.myapplication.stats.StatsDataStore(this)
            lifecycleScope.launch {
                dataStore.addUsageMinutes(elapsedMinutes)
            }
        }
    }
}
