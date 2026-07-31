package com.example.questify.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.questify.ui.components.TelaBase
import com.example.questify.ui.theme.LocalQuestifyColors
import com.example.questify.viewmodel.RankingViewModel

@Composable
fun RankingScreen(onVoltar: () -> Unit, vm: RankingViewModel) {
    val cores = LocalQuestifyColors.current
    val lista by vm.ranking.collectAsState()

    TelaBase(titulo = "Ranking", onVoltar = onVoltar) { _ ->
        Column(
            Modifier.fillMaxSize().padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.height(15.dp))
            Text(
                "Leaderboard",
                fontSize = 45.sp,
                style = MaterialTheme.typography.titleLarge,
                color = cores.darkBrown,
            )
            Spacer(Modifier.height(20.dp))

            Box(
                Modifier
                    .fillMaxWidth()
                    .shadow(10.dp, RoundedCornerShape(20.dp))
                    .background(cores.cream, RoundedCornerShape(20.dp))
                    .padding(horizontal = 20.dp, vertical = 15.dp),
            ) {
                Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(
                            "Tema",
                            style = MaterialTheme.typography.titleSmall,
                            color = cores.darkBrown,
                        )
                        Text(
                            "Pontos",
                            style = MaterialTheme.typography.titleSmall,
                            color = cores.darkBrown,
                        )
                    }
                    HorizontalDivider(color = cores.darkBrown.copy(alpha = 0.3f))
                    if (lista.isEmpty()) {
                        Text(
                            "Nenhuma partida registrada ainda.",
                            style = MaterialTheme.typography.bodyLarge,
                            color = cores.darkBrown,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth().padding(vertical = 20.dp),
                        )
                    } else {
                        LazyColumn(
                            contentPadding = PaddingValues(vertical = 4.dp),
                            modifier = Modifier.fillMaxWidth().height(500.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp),
                        ) {
                            items(lista) { r ->
                                Row(
                                    Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Text(
                                        r.tema,
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = cores.darkBrown,
                                        maxLines = 1,
                                        modifier = Modifier.weight(1f),
                                    )
                                    Text(
                                        r.pontuacao.toString(),
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = cores.darkBrown,
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
