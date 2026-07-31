package com.example.questify.data_layer.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Pergunta(
    val pergunta: String,
    @SerialName("respostasErradas") val respostasErradas: List<String>,
    @SerialName("respostaCorreta") val respostaCorreta: String,
)
