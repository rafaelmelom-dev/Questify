package com.example.questify.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.questify.data_layer.model.Pergunta
import com.example.questify.data_layer.repository.QuestionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class QuizUiState(
    val carregando: Boolean = false,
    val perguntas: List<Pergunta> = emptyList(),
    val indice: Int = 0,
    val alternativas: List<String> = emptyList(),
    val erro: String? = null,
) {
    val perguntaAtual: Pergunta? get() = perguntas.getOrNull(indice)
    val fim: Boolean get() = perguntas.isNotEmpty() && indice >= perguntas.size - 1
    val total: Int get() = perguntas.size
}

class QuizViewModel(private val repo: QuestionRepository) : ViewModel() {

    private val _state = MutableStateFlow(QuizUiState())
    val state: StateFlow<QuizUiState> = _state.asStateFlow()

    fun iniciar(tema: String, dificuldade: String) {
        val atual = _state.value
        if (atual.carregando || atual.perguntas.isNotEmpty()) return
        _state.value = QuizUiState(carregando = true)
        viewModelScope.launch {
            runCatching { repo.buscarPerguntas(tema, dificuldade) }
                .onSuccess { perguntas ->
                    _state.value = QuizUiState(
                        carregando = false,
                        perguntas = perguntas,
                        indice = 0,
                        alternativas = embaralhar(perguntas.firstOrNull()),
                    )
                }
                .onFailure { e ->
                    _state.value = QuizUiState(erro = e.message ?: "Erro ao buscar perguntas")
                }
        }
    }

    fun proximaPergunta() {
        val s = _state.value
        if (s.indice < s.perguntas.size - 1) {
            val novoIdx = s.indice + 1
            _state.value = s.copy(
                indice = novoIdx,
                alternativas = embaralhar(s.perguntas[novoIdx]),
            )
        }
    }

    private fun embaralhar(p: Pergunta?): List<String> {
        if (p == null) return emptyList()
        return (p.respostasErradas + p.respostaCorreta).shuffled()
    }
}
