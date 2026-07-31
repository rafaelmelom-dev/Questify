package com.example.questify.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.questify.audio.AudioManager
import com.example.questify.data_layer.local.AppSettings
import com.example.questify.data_layer.local.SettingsDataStore
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val store: SettingsDataStore,
    private val audio: AudioManager,
) : ViewModel() {

    val settings: StateFlow<AppSettings> = store.settings
        .stateIn(viewModelScope, SharingStarted.Eagerly, AppSettings())

    fun setTemaEscuro(v: Boolean) = viewModelScope.launch { store.setTemaEscuro(v) }
    fun setDificuldade(v: String) = viewModelScope.launch { store.setDificuldade(v) }
    fun setSom(v: Boolean) = viewModelScope.launch {
        store.setSom(v)
        audio.definirHabilitado(v)
    }
}
