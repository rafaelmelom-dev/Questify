package com.example.questify.data_layer.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.questify.data_layer.model.RankingEntry

@Entity(tableName = "ranking")
data class RankingEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val tema: String,
    val pontuacao: Int,
    val timestamp: Long,
) {
    fun toModel() = RankingEntry(id, tema, pontuacao, timestamp)

    companion object {
        fun fromModel(e: RankingEntry) = RankingEntity(e.id, e.tema, e.pontuacao, e.timestamp)
    }
}
