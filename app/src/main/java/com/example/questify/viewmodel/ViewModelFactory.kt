package com.example.questify.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.questify.QuestifyApp


class ViewModelFactory : ViewModelProvider.Factory {
    private val c = QuestifyApp.instance.container

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = when {
        modelClass.isAssignableFrom(QuizViewModel::class.java) ->
            QuizViewModel(c.questionRepository) as T
        modelClass.isAssignableFrom(RankingViewModel::class.java) ->
            RankingViewModel(c.rankingRepository) as T
        modelClass.isAssignableFrom(SettingsViewModel::class.java) ->
            SettingsViewModel(c.settingsDataStore, c.audioManager) as T
        else -> throw IllegalArgumentException("VM desconhecida: ${modelClass.name}")
    }
}
