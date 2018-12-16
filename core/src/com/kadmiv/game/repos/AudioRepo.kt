package com.kadmiv.game.repos

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.g2d.Animation
import com.kadmiv.game.actors.AnimationLoader

enum class AudioRepo(val sound: String) {
    DEATH_1("${Paths.SOUND_DEATH_PATH}death_1.mp3"),
    DEATH_2("${Paths.SOUND_DEATH_PATH}death_2.mp3"),
    DEATH_3("${Paths.SOUND_DEATH_PATH}death_3.mp3"),
    DEATH_4("${Paths.SOUND_DEATH_PATH}death_4.mp3"),

    SHOOT_1("${Paths.SOUND_BULLET_PATH}bullet_1.mp3"),
    SHOOT_2("${Paths.SOUND_BULLET_PATH}bullet_2.mp3"),
    SHOOT_3("${Paths.SOUND_BULLET_PATH}bullet_3.mp3"),
    SHOOT_4("${Paths.SOUND_BULLET_PATH}bullet_4.mp3"),

    READY_COMMAND("${Paths.SOUND_BULLET_PATH}ready.mp3"),
    SREADY_COMMAND("${Paths.SOUND_BULLET_PATH}steady.mp3"),
    BANG_COMMAND("${Paths.SOUND_BULLET_PATH}finis_him.mp3"),

    WIN_1("${Paths.SOUND_ADDITIONAL_PATH}win_all_1.mp3"),
    WIN_2("${Paths.SOUND_ADDITIONAL_PATH}win_all_2.mp3"),
    WIN_3("${Paths.SOUND_ADDITIONAL_PATH}win_all_3.mp3"),
    WIN_4("${Paths.SOUND_ADDITIONAL_PATH}win_all_4.mp3"),
    READY("${Paths.SOUND_ADDITIONAL_PATH}ready_click.mp3");

    fun getSong(): Sound {
        return Gdx.audio.newSound(Gdx.files.internal(this.sound))
    }

}