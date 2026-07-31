package com.example.questify.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.clickable
import com.example.questify.audio.LocalAudioManager
import com.example.questify.ui.components.TelaBase
import com.example.questify.ui.theme.LocalQuestifyColors
import com.example.questify.viewmodel.SettingsViewModel

@Composable
fun JogarScreen(
    onVoltar: () -> Unit,
    onComecar: (String) -> Unit,
    settingsVm: SettingsViewModel,
) {
    val cores = LocalQuestifyColors.current
    val audio = LocalAudioManager.current
    var tema by rememberSaveable { mutableStateOf("") }
    val settings by settingsVm.settings.collectAsState()

    TelaBase(titulo = "Jogar", onVoltar = onVoltar) { _ ->
        Column(
            Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.height(20.dp))
            Text(
                "JOGAR",
                fontSize = 45.sp,
                style = MaterialTheme.typography.titleLarge,
                color = cores.darkBrown,
            )
            Spacer(Modifier.height(20.dp))
            Icon(
                imageVector = Icons.Filled.HelpOutline,
                contentDescription = null,
                tint = cores.darkBrown,
                modifier = Modifier.size(130.dp),
            )
            Spacer(Modifier.height(30.dp))

            TextField(
                value = tema,
                onValueChange = { tema = it },
                placeholder = { Text("Digite o tema aqui...", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth()) },
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyLarge.copy(textAlign = TextAlign.Center, color = cores.darkBrown),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = cores.cream,
                    unfocusedContainerColor = cores.cream,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 50.dp)
                    .shadow(5.dp, RoundedCornerShape(10.dp)),
            )
            Spacer(Modifier.height(12.dp))
            Text(
                "Sobre o que você quer se desafiar?",
                style = MaterialTheme.typography.titleSmall,
                color = cores.darkBrown,
                textAlign = TextAlign.Center,
            )
            Spacer(Modifier.height(4.dp))
            Text(
                "Exemplo: Matemática, Português...",
                style = MaterialTheme.typography.bodyMedium,
                color = cores.darkBrown,
                textAlign = TextAlign.Center,
            )
            Spacer(Modifier.height(40.dp))
            Text(
                "Nível: ${settings.dificuldade}",
                style = MaterialTheme.typography.bodyMedium,
                color = cores.darkBrown,
            )
            Spacer(Modifier.height(30.dp))

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .width(200.dp).height(60.dp)
                    .shadow(5.dp, RoundedCornerShape(15.dp))
                    .background(cores.cream, RoundedCornerShape(15.dp))
                    .clickable {
                        audio.tocarSomDeBotao()
                        if (tema.isNotBlank()) onComecar(tema.trim())
                    },
            ) {
                Text(
                    "Começar",
                    fontSize = 25.sp,
                    style = MaterialTheme.typography.labelLarge,
                    color = cores.darkBrown,
                )
            }
        }
    }
}

