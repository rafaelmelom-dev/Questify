package com.example.questify.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.questify.ui.components.TelaBase
import com.example.questify.ui.theme.LocalQuestifyColors

private data class Topico(val id: Int, val nome: String, val descricao: String)

private val topicos = listOf(
    Topico(1, "Introdução",
        """
            Bem-vindo ao nosso quiz de perguntas e respostas! 🎉

            Neste jogo, você poderá testar seus conhecimentos em diversos temas. O objetivo é simples: responder corretamente a 10 perguntas no menor tempo possível.

            - Cada pergunta tem 4 alternativas, e apenas uma está correta.
            - A cada acerto, você ganha pontos.
            - Ao final do jogo, sua pontuação será exibida no placar.

            Escolha um tema e desafie-se para alcançar o topo! 🚀
        """.trimIndent()),
    Topico(2, "Temas",
        """
            Aqui você pode escolher o tema do quiz! 🎯

            Disponibilizamos vários tópicos, como:
            ✅ História
            ✅ Ciência
            ✅ Cultura pop
            ✅ E muito mais, você é livre para escolher!

            A cada nova partida, você pode selecionar um tema diferente, tornando o jogo mais dinâmico e divertido.

            💡 Dica: Nas opções, você também pode alterar a dificuldade do jogo para um desafio ainda maior!
        """.trimIndent()),
    Topico(3, "In-game",
        """
            No jogo, você verá a pergunta e as 4 alternativas.

            1️⃣ Escolha a alternativa que acredita ser correta.
            2️⃣ Clique no botão "Confirmar Resposta".
            3️⃣ O jogo indicará se você acertou ou errou:
               - ✅ Acerto: a alternativa ficará com uma borda verde.
               - ❌ Erro: a alternativa errada terá uma borda vermelha, e a correta ficará verde.
            4️⃣ O botão "Próxima Pergunta" será ativado para você seguir no jogo.

            💡 Lembre-se: cada acerto soma pontos ao seu placar, e o tempo também conta! Use boas estratégias para maximizar sua pontuação.
        """.trimIndent()),
    Topico(4, "Placar",
        """
            O placar é atualizado com base nos seus acertos e tempo.

            ✔️ Cada resposta correta aumenta sua pontuação.
            ✔️ No final do quiz, sua pontuação total será exibida.
            ✔️ Em breve, você poderá comparar seu desempenho na tela de "Ranking".
        """.trimIndent()),
    Topico(5, "Regras",
        """
            As regras são simples e diretas:

            🎯 Escolha um tema.
            🎯 Responda às perguntas no menor tempo possível.
            🎯 Cada pergunta tem 4 alternativas, e apenas uma resposta correta.
            🎯 Você pode tentar novamente em outros temas ou até no mesmo.
            🎯 A dificuldade escolhida também influencia nos pontos ganhos.

            O objetivo é testar seus conhecimentos e se divertir com o jogo! 🚀
        """.trimIndent()),
    Topico(6, "Dicas",
        """
            Aqui estão algumas dicas para melhorar sua performance no quiz:

            🔹 Leia cada pergunta com atenção.
            🔹 Elimine as alternativas que você sabe que estão erradas.
            🔹 Tente responder o mais rápido possível para ganhar mais pontos.
            🔹 Cada tema tem suas peculiaridades, então esteja preparado para perguntas de diferentes áreas!

            Boa sorte! 🍀
        """.trimIndent()),
)

@Composable
fun ComoJogarScreen(onVoltar: () -> Unit) {
    val cores = LocalQuestifyColors.current
    TelaBase(titulo = "Como Jogar", onVoltar = onVoltar) { _ ->
        LazyColumn(
            Modifier.fillMaxSize().padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item {
                Spacer(Modifier.height(15.dp))
                Text(
                    "Como Jogar",
                    fontSize = 37.sp,
                    style = MaterialTheme.typography.titleLarge,
                    color = cores.darkBrown,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(Modifier.height(20.dp))
            }
            items(topicos) { t -> TopicoCard(t) }
            item { Spacer(Modifier.height(30.dp)) }
        }
    }
}

@Composable
private fun TopicoCard(t: Topico) {
    val cores = LocalQuestifyColors.current
    var aberto by remember { mutableStateOf(false) }
    Column(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 7.dp)
            .background(cores.cream, RoundedCornerShape(12.dp))
            .clickable { aberto = !aberto }
            .padding(horizontal = 10.dp, vertical = 7.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                "${t.id}.   ${t.nome}",
                style = MaterialTheme.typography.titleMedium,
                color = cores.darkBrown,
            )
            Icon(
                imageVector = if (aberto) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                contentDescription = null,
                tint = cores.darkBrown,
            )
        }
        AnimatedVisibility(visible = aberto) {
            Text(
                t.descricao,
                style = MaterialTheme.typography.bodyLarge,
                color = cores.darkBrown,
                modifier = Modifier.padding(vertical = 10.dp, horizontal = 15.dp),
            )
        }
    }
}

