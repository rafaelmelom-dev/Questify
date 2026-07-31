package com.example.questify.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.SoundPool
import androidx.compose.runtime.compositionLocalOf
import com.example.questify.R
class AudioManager(private val context: Context) {

    @Volatile private var somHabilitado: Boolean = true
    @Volatile private var playerFundo: MediaPlayer? = null

    private val soundPool: SoundPool = SoundPool.Builder()
        .setMaxStreams(3)
        .setAudioAttributes(
            AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()
        )
        .build()

    private val idClick: Int = soundPool.load(context, R.raw.click_sound, 1)
    private val idCorrect: Int = soundPool.load(context, R.raw.correct_option, 1)
    private val idWrong: Int = soundPool.load(context, R.raw.wrong_option, 1)

    fun definirHabilitado(habilitado: Boolean) {
        somHabilitado = habilitado
        if (habilitado) iniciarSomDeFundo() else pararSomDeFundo()
    }

    fun estaHabilitado(): Boolean = somHabilitado

    fun iniciarSomDeFundo() {
        if (!somHabilitado) return
        if (playerFundo != null) return
        try {
            val mp = MediaPlayer.create(context, R.raw.relaxing_loop) ?: return
            mp.isLooping = true
            mp.setVolume(0.6f, 0.6f)
            mp.start()
            playerFundo = mp
        } catch (_: Exception) { /* falha silenciosa como o original */ }
    }

    fun pararSomDeFundo() {
        playerFundo?.let {
            try { if (it.isPlaying) it.stop() } catch (_: Exception) {}
            it.release()
        }
        playerFundo = null
    }

    fun tocarSomDeBotao() {
        if (!somHabilitado) return
        soundPool.play(idClick, 1f, 1f, 1, 0, 1f)
    }

    fun tocarSomDeAcerto() {
        if (!somHabilitado) return
        soundPool.play(idCorrect, 1f, 1f, 1, 0, 1f)
    }

    fun tocarSomDeErro() {
        if (!somHabilitado) return
        soundPool.play(idWrong, 1f, 1f, 1, 0, 1f)
    }

    fun release() {
        pararSomDeFundo()
        soundPool.release()
    }
}

val LocalAudioManager = compositionLocalOf<AudioManager> {
    error("AudioManager nao provido")
}
