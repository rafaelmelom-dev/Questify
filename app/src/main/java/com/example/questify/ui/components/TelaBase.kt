package com.example.questify.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.questify.ui.theme.LocalQuestifyColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaBase(
    titulo: String,
    onVoltar: (() -> Unit)? = null,
    content: @Composable (PaddingValues) -> Unit,
) {
    val cores = LocalQuestifyColors.current
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = cores.lightBrown,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        titulo,
                        style = MaterialTheme.typography.titleMedium,
                        color = cores.darkBrown,
                    )
                },
                navigationIcon = {
                    if (onVoltar != null) {
                        IconButton(onClick = onVoltar) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Voltar",
                                tint = cores.darkBrown,
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = cores.lightBrown,
                    titleContentColor = cores.darkBrown,
                    navigationIconContentColor = cores.darkBrown,
                ),
            )
        },
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(cores.lightBrown)
                .padding(padding),
        ) {
            content(padding)
        }
    }
}
