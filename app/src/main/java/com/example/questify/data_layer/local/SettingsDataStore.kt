package com.example.questify.data_layer.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.settingsDataStore by preferencesDataStore(name = "questify_settings")

data class AppSettings(
    val temaEscuro: Boolean = false,
    val dificuldade: String = "Normal",
    val somHabilitado: Boolean = true,
)

class SettingsDataStore(private val context: Context) {
    private val KEY_TEMA_ESCURO = booleanPreferencesKey("tema_escuro")
    private val KEY_DIFICULDADE = stringPreferencesKey("dificuldade")
    private val KEY_SOM = booleanPreferencesKey("som_habilitado")

    val settings: Flow<AppSettings> = context.settingsDataStore.data.map { p ->
        AppSettings(
            temaEscuro = p[KEY_TEMA_ESCURO] ?: false,
            dificuldade = p[KEY_DIFICULDADE] ?: "Normal",
            somHabilitado = p[KEY_SOM] ?: true,
        )
    }

    suspend fun setTemaEscuro(v: Boolean) =
        context.settingsDataStore.edit { it[KEY_TEMA_ESCURO] = v }.let { Unit }

    suspend fun setDificuldade(v: String) =
        context.settingsDataStore.edit { it[KEY_DIFICULDADE] = v }.let { Unit }

    suspend fun setSom(v: Boolean) =
        context.settingsDataStore.edit { it[KEY_SOM] = v }.let { Unit }
}
