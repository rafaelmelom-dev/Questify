package com.example.questify.navigation

object Routes {
    const val HOME = "home"
    const val JOGAR = "jogar"
    const val COMO_JOGAR = "como_jogar"
    const val OPCOES = "opcoes"
    const val RANKING = "ranking"

    // game/{tema}
    const val GAME = "game/{tema}"
    fun game(tema: String) = "game/${java.net.URLEncoder.encode(tema, "UTF-8")}"

    // fim/{tema}/{pontos}/{acertos}/{tempoMs}
    const val FIM = "fim/{tema}/{pontos}/{acertos}/{tempoMs}"
    fun fim(tema: String, pontos: Int, acertos: Int, tempoMs: Long) =
        "fim/${java.net.URLEncoder.encode(tema, "UTF-8")}/$pontos/$acertos/$tempoMs"
}
