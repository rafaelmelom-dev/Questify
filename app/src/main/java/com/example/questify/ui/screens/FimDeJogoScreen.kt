package com.example.questify.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.questify.audio.LocalAudioManager
import com.example.questify.ui.theme.LocalQuestifyColors
import com.example.questify.viewmodel.RankingViewModel
import java.util.Locale

@Composable
fun FimDeJogoScreen(
    tema: String,
    pontuacaoFinal: Int,
    acertos: Int,
    tempoTotalMs: Long,
    onMenuPrincipal: () -> Unit,
    rankingVm: RankingViewModel,
) {
    val cores = LocalQuestifyColors.current
    val audio = LocalAudioManager.current

    // Salva no ranking apenas uma vez ao entrar na tela
    val salvo = remember { arrayOf(false) }
    LaunchedEffect(Unit) {
        if (!salvo[0]) {
            rankingVm.adicionar(tema, pontuacaoFinal)
            salvo[0] = true
        }
    }

    Box(Modifier.fillMaxSize().background(cores.lightBrown)) {
        Column(
            Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.height(30.dp))
            Text(
                "FIM DE JOGO",
                fontSize = 37.sp,
                style = MaterialTheme.typography.titleLarge,
                color = cores.darkBrown,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(Modifier.height(35.dp))
            Spacer(Modifier.weight(1f))

            Column(
                Modifier
                    .fillMaxWidth()
                    .shadow(10.dp, RoundedCornerShape(20.dp))
                    .background(cores.cream, RoundedCornerShape(20.dp))
                    .padding(16.dp),
            ) {
                Text(
                    tema,
                    style = MaterialTheme.typography.titleSmall,
                    color = cores.darkBrown,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                )
                LinhaResumo("Acertos", acertos.toString())
                LinhaResumo("Pontos", pontuacaoFinal.toString())
                LinhaResumo(
                    "Tempo",
                    String.format(Locale.getDefault(), "%.2f seg", tempoTotalMs / 1000.0),
                )
            }

            Spacer(Modifier.weight(1f))
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .width(200.dp).height(60.dp)
                    .shadow(5.dp, RoundedCornerShape(15.dp))
                    .background(cores.cream, RoundedCornerShape(15.dp))
                    .clickable {
                        audio.tocarSomDeBotao()
                        onMenuPrincipal()
                    },
            ) {
                Text(
                    "Menu Principal",
                    fontSize = 22.sp,
                    style = MaterialTheme.typography.labelLarge,
                    color = cores.darkBrown,
                )
            }
            Spacer(Modifier.height(25.dp))
        }
    }
}

@Composable
private fun LinhaResumo(titulo: String, valor: String) {
    val cores = LocalQuestifyColors.current
    Row(
        Modifier.fillMaxWidth().padding(vertical = 5.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(titulo, style = MaterialTheme.typography.bodyLarge, color = cores.darkBrown)
        Text(valor, style = MaterialTheme.typography.bodyLarge, color = cores.darkBrown)
    }
}
