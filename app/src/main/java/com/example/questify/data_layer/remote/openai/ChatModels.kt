package com.example.questify.data_layer.remote.openai

import kotlinx.serialization.Serializable

@Serializable
data class ChatMessage(
    val role: String,
    val content: String,
)

@Serializable
data class ChatRequest(
    val model: String,
    val messages: List<ChatMessage>,
)

@Serializable
data class ChatChoice(
    val message: ChatMessage,
)

@Serializable
data class ChatResponse(
    val choices: List<ChatChoice>,
)
