package com.example.questify.data_layer.remote

import com.example.questify.data_layer.model.Pergunta
import com.example.questify.data_layer.remote.openai.ChatMessage
import com.example.questify.data_layer.remote.openai.ChatRequest
import com.example.questify.data_layer.remote.openai.OpenAIChatApi
import kotlinx.serialization.json.Json

class QuestionRemoteDataSource(
    private val api: OpenAIChatApi,
    private val model: String,
) {
    private val json = Json { ignoreUnknownKeys = true; isLenient = true }

    suspend fun buscarPerguntas(tema: String, dificuldade: String): List<Pergunta> {
        val prompt = montarPrompt(tema, dificuldade)
        val req = ChatRequest(
            model = model,
            messages = listOf(ChatMessage(role = "user", content = prompt)),
        )
        val resp = api.chatCompletion(req)
        val content = resp.choices.firstOrNull()?.message?.content.orEmpty()
        return parsePerguntas(content)
    }

    private fun montarPrompt(tema: String, dificuldade: String): String =
        "Preciso de um array JSON com 10 perguntas e 4 respostas DISTINTAS sobre $tema " +
        "com dificuldade $dificuldade. Cada pergunta deve conter as chaves: 'pergunta', " +
        "um array de 3 respostas erradas chamado 'respostasErradas', e a resposta correta em " +
        "'respostaCorreta'. As respostas devem ter no maximo 10 palavras. " +
        "Lembre-se, todas as 4 alternativas devem ser DISTINTAS. Retorne APENAS o JSON."

    private fun parsePerguntas(raw: String): List<Pergunta> {
        val limpo = raw
            .replace("```json", "")
            .replace("```", "")
            .trim()
        val inicio = limpo.indexOf('[')
        val fim = limpo.lastIndexOf(']')
        if (inicio < 0 || fim <= inicio) return emptyList()
        val jsonArray = limpo.substring(inicio, fim + 1)
        return runCatching { json.decodeFromString<List<Pergunta>>(jsonArray) }
            .getOrDefault(emptyList())
    }
}
