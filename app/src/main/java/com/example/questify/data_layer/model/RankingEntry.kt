package com.example.questify.data_layer.model

data class RankingEntry(
    val id: Long = 0,
    val tema: String,
    val pontuacao: Int,
    val timestamp: Long = System.currentTimeMillis(),
)
