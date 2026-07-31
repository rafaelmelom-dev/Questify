package com.example.questify.data_layer.repository

import com.example.questify.data_layer.model.Pergunta
import com.example.questify.data_layer.remote.QuestionRemoteDataSource

class QuestionRepository(private val remote: QuestionRemoteDataSource) {
    suspend fun buscarPerguntas(tema: String, dificuldade: String): List<Pergunta> =
        remote.buscarPerguntas(tema, dificuldade)
}
