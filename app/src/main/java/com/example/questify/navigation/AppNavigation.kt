package com.example.questify.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.questify.ui.screens.ComoJogarScreen
import com.example.questify.ui.screens.FimDeJogoScreen
import com.example.questify.ui.screens.GameScreen
import com.example.questify.ui.screens.HomeScreen
import com.example.questify.ui.screens.JogarScreen
import com.example.questify.ui.screens.OpcoesScreen
import com.example.questify.ui.screens.RankingScreen
import com.example.questify.viewmodel.SettingsViewModel
import com.example.questify.viewmodel.ViewModelFactory
import java.net.URLDecoder

@Composable
fun AppNavigation(settingsVm: SettingsViewModel) {
    val nav = rememberNavController()
    val factory = remember { ViewModelFactory() }

    NavHost(navController = nav, startDestination = Routes.HOME) {
        composable(Routes.HOME) {
            HomeScreen(
                onJogar = { nav.navigate(Routes.JOGAR) },
                onComoJogar = { nav.navigate(Routes.COMO_JOGAR) },
                onOpcoes = { nav.navigate(Routes.OPCOES) },
                onRanking = { nav.navigate(Routes.RANKING) },
            )
        }
        composable(Routes.JOGAR) {
            JogarScreen(
                onVoltar = { nav.popBackStack() },
                onComecar = { tema -> nav.navigate(Routes.game(tema)) },
                settingsVm = settingsVm,
            )
        }
        composable(Routes.COMO_JOGAR) {
            ComoJogarScreen(onVoltar = { nav.popBackStack() })
        }
        composable(Routes.OPCOES) {
            OpcoesScreen(
                onVoltar = { nav.popBackStack() },
                settingsVm = settingsVm,
                rankingVm = viewModel(factory = factory),
            )
        }
        composable(Routes.RANKING) {
            RankingScreen(
                onVoltar = { nav.popBackStack() },
                vm = viewModel(factory = factory),
            )
        }
        composable(
            route = Routes.GAME,
            arguments = listOf(navArgument("tema") { type = NavType.StringType }),
        ) { entry ->
            val tema = URLDecoder.decode(entry.arguments?.getString("tema").orEmpty(), "UTF-8")
            GameScreen(
                tema = tema,
                onFim = { pontos, acertos, tempoMs ->
                    nav.navigate(Routes.fim(tema, pontos, acertos, tempoMs)) {
                        popUpTo(Routes.HOME) { inclusive = false }
                    }
                },
                onVoltar = { nav.popBackStack() },
                quizVm = viewModel(factory = factory),
                settingsVm = settingsVm,
            )
        }
        composable(
            route = Routes.FIM,
            arguments = listOf(
                navArgument("tema") { type = NavType.StringType },
                navArgument("pontos") { type = NavType.IntType },
                navArgument("acertos") { type = NavType.IntType },
                navArgument("tempoMs") { type = NavType.LongType },
            ),
        ) { entry ->
            val tema = URLDecoder.decode(entry.arguments?.getString("tema").orEmpty(), "UTF-8")
            val pontos = entry.arguments?.getInt("pontos") ?: 0
            val acertos = entry.arguments?.getInt("acertos") ?: 0
            val tempoMs = entry.arguments?.getLong("tempoMs") ?: 0L
            FimDeJogoScreen(
                tema = tema,
                pontuacaoFinal = pontos,
                acertos = acertos,
                tempoTotalMs = tempoMs,
                onMenuPrincipal = {
                    nav.popBackStack(Routes.HOME, inclusive = false)
                },
                rankingVm = viewModel(factory = factory),
            )
        }
    }
}

