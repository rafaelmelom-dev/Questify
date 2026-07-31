package com.example.questify.data_layer.repository

import com.example.questify.data_layer.local.RankingDao
import com.example.questify.data_layer.local.RankingEntity
import com.example.questify.data_layer.model.RankingEntry
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RankingRepository(private val dao: RankingDao) {

    fun observarRanking(): Flow<List<RankingEntry>> =
        dao.observarTodos().map { list -> list.map { it.toModel() } }

    suspend fun adicionar(tema: String, pontuacao: Int) {
        dao.inserir(
            RankingEntity(
                tema = tema,
                pontuacao = pontuacao,
                timestamp = System.currentTimeMillis(),
            )
        )
    }

    suspend fun redefinir() = dao.limpar()
}
