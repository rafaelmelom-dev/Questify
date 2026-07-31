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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.questify.audio.LocalAudioManager
import com.example.questify.ui.components.TelaBase
import com.example.questify.ui.theme.LocalQuestifyColors
import com.example.questify.viewmodel.RankingViewModel
import com.example.questify.viewmodel.SettingsViewModel

@Composable
fun OpcoesScreen(
    onVoltar: () -> Unit,
    settingsVm: SettingsViewModel,
    rankingVm: RankingViewModel,
) {
    val cores = LocalQuestifyColors.current
    val audio = LocalAudioManager.current
    val s by settingsVm.settings.collectAsState()

    TelaBase(titulo = "Opções", onVoltar = onVoltar) { _ ->
        Column(
            Modifier.fillMaxSize().padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.height(15.dp))
            Text(
                "Configurações",
                fontSize = 37.sp,
                style = MaterialTheme.typography.titleLarge,
                color = cores.darkBrown,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )

            LinhaPicker(
                titulo = "Tema",
                opcoes = listOf("Claro", "Escuro"),
                selecionado = if (s.temaEscuro) "Escuro" else "Claro",
                onSelecionar = {
                    audio.tocarSomDeBotao()
                    settingsVm.setTemaEscuro(it == "Escuro")
                },
            )

            LinhaPicker(
                titulo = "Nível",
                opcoes = listOf("Difícil", "Normal", "Fácil"),
                selecionado = s.dificuldade,
                onSelecionar = {
                    audio.tocarSomDeBotao()
                    settingsVm.setDificuldade(it)
                },
            )

            LinhaToggle(
                titulo = "Som",
                ligado = s.somHabilitado,
                onMudar = {
                    audio.tocarSomDeBotao()
                    settingsVm.setSom(it)
                },
            )

            LinhaBotao(
                titulo = "Placar",
                textoBotao = "Redefinir",
                onClick = {
                    audio.tocarSomDeBotao()
                    rankingVm.redefinir()
                },
            )
        }
    }
}

@Composable
private fun LinhaContainer(conteudo: @Composable () -> Unit) {
    val cores = LocalQuestifyColors.current
    Box(
        Modifier
            .fillMaxWidth()
            .height(70.dp)
            .shadow(5.dp, RoundedCornerShape(12.dp))
            .background(cores.cream, RoundedCornerShape(12.dp))
            .padding(horizontal = 10.dp),
    ) { conteudo() }
}

@Composable
private fun LinhaPicker(
    titulo: String,
    opcoes: List<String>,
    selecionado: String,
    onSelecionar: (String) -> Unit,
) {
    val cores = LocalQuestifyColors.current
    var expandido by remember { mutableStateOf(false) }
    LinhaContainer {
        Row(
            Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                titulo,
                color = cores.darkBrown,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.width(120.dp),
            )
            Box(
                Modifier
                    .width(150.dp).height(40.dp)
                    .background(cores.beige, RoundedCornerShape(8.dp))
                    .clickable { expandido = true }
                    .padding(horizontal = 10.dp),
                contentAlignment = Alignment.CenterStart,
            ) {
                Row(
                    Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(selecionado, color = cores.darkBrown, style = MaterialTheme.typography.bodyLarge)
                    Icon(Icons.Filled.ArrowDropDown, null, tint = cores.darkBrown)
                }
                DropdownMenu(expanded = expandido, onDismissRequest = { expandido = false }) {
                    opcoes.forEach { op ->
                        DropdownMenuItem(
                            text = { Text(op) },
                            onClick = { expandido = false; onSelecionar(op) },
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun LinhaToggle(titulo: String, ligado: Boolean, onMudar: (Boolean) -> Unit) {
    val cores = LocalQuestifyColors.current
    LinhaContainer {
        Row(
            Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                titulo,
                color = cores.darkBrown,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.width(120.dp),
            )
            Switch(
                checked = ligado,
                onCheckedChange = onMudar,
                colors = SwitchDefaults.colors(
                    checkedTrackColor = cores.darkBrown,
                    checkedThumbColor = cores.cream,
                ),
            )
        }
    }
}

@Composable
private fun LinhaBotao(titulo: String, textoBotao: String, onClick: () -> Unit) {
    val cores = LocalQuestifyColors.current
    LinhaContainer {
        Row(
            Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                titulo,
                color = cores.darkBrown,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.width(120.dp),
            )
            Box(
                Modifier
                    .width(150.dp).height(40.dp)
                    .background(cores.beige, RoundedCornerShape(8.dp))
                    .clickable { onClick() },
                contentAlignment = Alignment.Center,
            ) {
                Text(textoBotao, color = cores.darkBrown, style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}
