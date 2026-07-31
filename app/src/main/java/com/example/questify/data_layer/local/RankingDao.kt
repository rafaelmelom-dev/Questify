package com.example.questify.data_layer.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RankingDao {
    @Query("SELECT * FROM ranking ORDER BY pontuacao DESC, timestamp DESC")
    fun observarTodos(): Flow<List<RankingEntity>>

    @Query("SELECT * FROM ranking ORDER BY pontuacao DESC, timestamp DESC")
    suspend fun listarTodos(): List<RankingEntity>

    @Insert
    suspend fun inserir(entry: RankingEntity): Long

    @Query("DELETE FROM ranking")
    suspend fun limpar()
}
