package com.cinnox.itunes.utils

import android.media.AudioAttributes
import android.media.MediaPlayer
import com.cinnox.itunes.entity.MusicEntity

object MusicPlayerManager {

    private val player = MediaPlayer()
    private var currentPlayerMusic: MusicEntity? = null

    init {
        val attributes = AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build()
        player.setAudioAttributes(attributes)
    }

    fun playMusic(musicEntity: MusicEntity) {
        if (currentPlayerMusic == null) {
            player.setDataSource(musicEntity.previewUrl)
            player.prepare()
            player.start()

            currentPlayerMusic = musicEntity
        } else {
            if (musicEntity.previewUrl == currentPlayerMusic?.previewUrl) {
                player.start()
            } else {
                stopMusic()
                playMusic(musicEntity)
            }
        }
    }

    fun pauseMusic() {
        if (currentPlayerMusic != null) {
            player.pause()
        }
    }

    fun stopMusic() {
        if (currentPlayerMusic != null) {
            player.stop()
            player.reset()

            currentPlayerMusic = null
        }
    }

}