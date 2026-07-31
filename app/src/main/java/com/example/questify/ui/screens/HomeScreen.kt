package com.example.questify.ui.screens

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.questify.R
import com.example.questify.audio.LocalAudioManager
import com.example.questify.ui.theme.LocalQuestifyColors

@Composable
fun HomeScreen(
    onJogar: () -> Unit,
    onComoJogar: () -> Unit,
    onOpcoes: () -> Unit,
    onRanking: () -> Unit,
) {
    val cores = LocalQuestifyColors.current
    val audio = LocalAudioManager.current

    val transicao = rememberInfiniteTransition(label = "cubo")
    val angulo by transicao.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
        ),
        label = "rot",
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(cores.lightBrown),
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.height(70.dp))
            Text(
                text = "QUESTIFY",
                style = MaterialTheme.typography.displayLarge,
                color = cores.darkBrown,
            )
            Spacer(Modifier.height(30.dp))
            Image(
                painter = painterResource(R.drawable.ic_cube),
                contentDescription = null,
                modifier = Modifier.size(105.dp).rotate(angulo),
            )
            Spacer(Modifier.height(40.dp))
            Column(
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(bottom = 70.dp),
            ) {
                MenuBotao("Jogar") { audio.tocarSomDeBotao(); onJogar() }
                MenuBotao("Como Jogar") { audio.tocarSomDeBotao(); onComoJogar() }
                MenuBotao("Opções") { audio.tocarSomDeBotao(); onOpcoes() }
                MenuBotao("Ranking") { audio.tocarSomDeBotao(); onRanking() }
            }
        }
    }
}

@Composable
private fun MenuBotao(texto: String, onClick: () -> Unit) {
    val cores = LocalQuestifyColors.current
    val interaction = remember { MutableInteractionSource() }
    val pressed by interaction.collectIsPressedAsState()
    val escala by animateFloatAsState(if (pressed) 1.05f else 1f, label = "esc")

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .width(300.dp).height(60.dp)
            .scale(escala)
            .shadow(5.dp, RoundedCornerShape(12.dp))
            .background(cores.cream, RoundedCornerShape(12.dp))
            .clickable(interactionSource = interaction, indication = null) { onClick() },
    ) {
        Text(
            text = texto,
            fontSize = 30.sp,
            style = MaterialTheme.typography.titleMedium,
            color = cores.darkBrown,
            textAlign = TextAlign.Center,
        )
    }
}
