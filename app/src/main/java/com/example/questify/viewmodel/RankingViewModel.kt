package com.example.questify.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.questify.data_layer.model.RankingEntry
import com.example.questify.data_layer.repository.RankingRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RankingViewModel(private val repo: RankingRepository) : ViewModel() {

    val ranking: StateFlow<List<RankingEntry>> = repo.observarRanking()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun adicionar(tema: String, pontuacao: Int) = viewModelScope.launch {
        repo.adicionar(tema, pontuacao)
    }

    fun redefinir() = viewModelScope.launch { repo.redefinir() }
}
