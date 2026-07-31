package com.example.questify

import android.app.Application
import com.example.questify.audio.AudioManager
import com.example.questify.data_layer.local.AppDatabase
import com.example.questify.data_layer.local.SettingsDataStore
import com.example.questify.data_layer.remote.QuestionRemoteDataSource
import com.example.questify.data_layer.remote.openai.OpenAIChatApi
import com.example.questify.data_layer.repository.QuestionRepository
import com.example.questify.data_layer.repository.RankingRepository

class AppContainer(app: Application) {
    val database = AppDatabase.get(app)
    val rankingRepository = RankingRepository(database.rankingDao())

    val settingsDataStore = SettingsDataStore(app)
    val audioManager = AudioManager(app)

    private val openAIApi = OpenAIChatApi(
        baseUrl = BuildConfig.AI_BASE_URL,
        apiKey = BuildConfig.AI_API_KEY,
    )
    private val questionRemote = QuestionRemoteDataSource(openAIApi, BuildConfig.AI_MODEL)
    val questionRepository = QuestionRepository(questionRemote)
}

class QuestifyApp : Application() {
    lateinit var container: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        instance = this
        container = AppContainer(this)
    }

    companion object {
        lateinit var instance: QuestifyApp
            private set
    }
}
