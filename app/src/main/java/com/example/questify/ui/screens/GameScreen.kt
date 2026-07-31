package com.example.questify.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.questify.audio.LocalAudioManager
import com.example.questify.ui.theme.LocalQuestifyColors
import com.example.questify.viewmodel.QuizViewModel
import com.example.questify.viewmodel.SettingsViewModel
import kotlinx.coroutines.delay

@Composable
fun GameScreen(
    tema: String,
    onFim: (pontos: Int, acertos: Int, tempoMs: Long) -> Unit,
    onVoltar: () -> Unit,
    quizVm: QuizViewModel,
    settingsVm: SettingsViewModel,
) {
    val cores = LocalQuestifyColors.current
    val audio = LocalAudioManager.current
    val state by quizVm.state.collectAsState()
    val settings by settingsVm.settings.collectAsState()

    // Contagem regressiva inicial de 5s (a busca das perguntas ocorre em paralelo)
    // rememberSaveable: sobrevive à recriação da Activity (rotação) sem replay
    var contagem by rememberSaveable { mutableStateOf(5) }
    LaunchedEffect(Unit) {
        while (contagem > 0) {
            delay(1000)
            contagem -= 1
        }
    }
    LaunchedEffect(Unit) {
        quizVm.iniciar(tema, settings.dificuldade)
    }

    // Estado local por-pergunta (rememberSaveable: sobrevive à rotação)
    var respostaSelecionada by rememberSaveable { mutableStateOf<String?>(null) }
    var respostaConfirmada by rememberSaveable { mutableStateOf(false) }
    var acertouAtual by rememberSaveable { mutableStateOf<Boolean?>(null) }
    var inicioPergunta by rememberSaveable { mutableStateOf(0L) }

    // Acumuladores da partida
    var pontosTotal by rememberSaveable { mutableStateOf(0) }
    var acertosTotal by rememberSaveable { mutableStateOf(0) }
    var tempoTotalMs by rememberSaveable { mutableStateOf(0L) }

    // Marca qual indice já foi "inicializado" localmente, pra distinguir
    // troca real de pergunta de uma simples recomposição pós-rotação
    var indiceInicializado by rememberSaveable { mutableStateOf(-1) }

    // Reset ao trocar de pergunta (não dispara de novo se for só a tela recompondo)
    LaunchedEffect(state.indice, state.perguntas.size) {
        if (state.perguntas.isNotEmpty() && state.indice != indiceInicializado) {
            respostaSelecionada = null
            respostaConfirmada = false
            acertouAtual = null
            inicioPergunta = System.currentTimeMillis()
            indiceInicializado = state.indice
        }
    }

    val pontosPorAcerto = when (settings.dificuldade) {
        "Fácil" -> 10
        "Normal" -> 25
        "Difícil" -> 50
        else -> 10
    }

    Box(
        Modifier.fillMaxSize().background(cores.lightBrown),
        contentAlignment = Alignment.Center,
    ) {
        when {
            contagem > 0 -> ContagemRegressiva(contagem)
            state.carregando -> Column(horizontalAlignment = Alignment.CenterHorizontally) {
                CircularProgressIndicator(color = cores.darkBrown)
                Spacer(Modifier.height(12.dp))
                Text("Carregando perguntas...", color = cores.darkBrown, style = MaterialTheme.typography.bodyLarge)
            }
            state.erro != null -> Column(
                Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    "Falha ao carregar perguntas.",
                    color = cores.darkBrown,
                    style = MaterialTheme.typography.titleSmall,
                    textAlign = TextAlign.Center,
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    state.erro ?: "",
                    color = cores.darkBrown,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                )
                Spacer(Modifier.height(16.dp))
                BotaoAcao("Voltar", Color(0xFF9E9E9E)) { onVoltar() }
            }
            state.perguntaAtual != null -> Column(
                Modifier
                    .fillMaxSize()
                    .padding(top = 40.dp, start = 16.dp, end = 16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                val p = state.perguntaAtual!!
                Text(
                    "Pergunta ${state.indice + 1} de ${state.total}",
                    fontSize = 30.sp,
                    style = MaterialTheme.typography.displayLarge.copy(fontSize = 30.sp),
                    color = cores.darkBrown,
                    textAlign = TextAlign.Center,
                )
                Text(
                    p.pergunta,
                    style = MaterialTheme.typography.titleSmall,
                    color = cores.darkBrown,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 8.dp),
                )
                state.alternativas.forEach { opcao ->
                    Alternativa(
                        texto = opcao,
                        selecionada = respostaSelecionada == opcao,
                        confirmada = respostaConfirmada,
                        correta = respostaConfirmada && opcao == p.respostaCorreta,
                        errada = respostaConfirmada && opcao == respostaSelecionada && opcao != p.respostaCorreta,
                        onClick = {
                            audio.tocarSomDeBotao()
                            if (!respostaConfirmada) respostaSelecionada = opcao
                        },
                    )
                }
                Spacer(Modifier.height(4.dp))
                when {
                    !respostaConfirmada && respostaSelecionada != null -> {
                        BotaoAcao("Confirmar Resposta", Color(0xFF388E3C)) {
                            respostaConfirmada = true
                            val acertou = respostaSelecionada == p.respostaCorreta
                            acertouAtual = acertou
                            if (acertou) {
                                audio.tocarSomDeAcerto()
                                pontosTotal += (pontosPorAcerto * 1000) / ((System.currentTimeMillis() - inicioPergunta) + 1000).toInt()
                                acertosTotal += 1
                            } else {
                                audio.tocarSomDeErro()
                            }
                            tempoTotalMs += (System.currentTimeMillis() - inicioPergunta)
                        }
                    }
                    respostaConfirmada && !state.fim -> {
                        BotaoAcao("Próxima Pergunta", Color(0xFF1976D2)) {
                            audio.tocarSomDeBotao()
                            quizVm.proximaPergunta()
                        }
                    }
                    respostaConfirmada && state.fim -> {
                        BotaoAcao("Finalizar Jogo", Color(0xFFD32F2F)) {
                            audio.tocarSomDeBotao()
                            onFim(pontosTotal, acertosTotal, tempoTotalMs)
                        }
                    }
                }
                Spacer(Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun ContagemRegressiva(n: Int) {
    val cores = LocalQuestifyColors.current
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            "Prepare-se",
            fontSize = 45.sp,
            style = MaterialTheme.typography.titleLarge,
            color = cores.darkBrown,
        )
        Spacer(Modifier.height(30.dp))
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(200.dp)
                .background(cores.darkBrown, CircleShape),
        ) {
            Text(
                n.toString(),
                fontSize = 80.sp,
                color = cores.lightBrown,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@Composable
private fun Alternativa(
    texto: String,
    selecionada: Boolean,
    confirmada: Boolean,
    correta: Boolean,
    errada: Boolean,
    onClick: () -> Unit,
) {
    val cores = LocalQuestifyColors.current
    val cor = when {
        correta -> Color(0xFF388E3C)
        errada -> Color(0xFFD32F2F)
        else -> Color.Transparent
    }
    val fundo = if (selecionada && !confirmada) cores.darkBrown else cores.cream
    val corTexto = if (selecionada && !confirmada) cores.cream else cores.darkBrown

    Box(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .shadow(4.dp, RoundedCornerShape(10.dp))
            .background(fundo, RoundedCornerShape(10.dp))
            .border(BorderStroke(4.dp, cor), RoundedCornerShape(10.dp))
            .clickable { onClick() }
            .padding(16.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            texto,
            style = MaterialTheme.typography.bodyLarge,
            color = corTexto,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun BotaoAcao(texto: String, cor: Color, onClick: () -> Unit) {
    Box(
        Modifier
            .background(cor, RoundedCornerShape(10.dp))
            .clickable { onClick() }
            .padding(horizontal = 20.dp, vertical = 12.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            texto,
            style = MaterialTheme.typography.titleSmall,
            color = Color.White,
        )
    }
}
