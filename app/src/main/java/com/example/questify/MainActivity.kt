package com.example.questify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.example.questify.audio.LocalAudioManager
import com.example.questify.navigation.AppNavigation
import com.example.questify.ui.theme.QuestifyTheme
import com.example.questify.viewmodel.SettingsViewModel
import com.example.questify.viewmodel.ViewModelFactory

class MainActivity : ComponentActivity() {

    private val settingsVm: SettingsViewModel by lazy {
        ViewModelProvider(this, ViewModelFactory())[SettingsViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val audio = QuestifyApp.instance.container.audioManager

        setContent {
            val settings by settingsVm.settings.collectAsState()

            LaunchedEffect(settings.somHabilitado) {
                audio.definirHabilitado(settings.somHabilitado)
            }

            QuestifyTheme(darkTheme = settings.temaEscuro) {
                CompositionLocalProvider(LocalAudioManager provides audio) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        contentWindowInsets = WindowInsets.systemBars,
                    ) { innerPadding ->
                        Box(modifier = Modifier.padding(innerPadding)) {
                            AppNavigation(settingsVm = settingsVm)
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isFinishing) QuestifyApp.instance.container.audioManager.release()
    }
}
