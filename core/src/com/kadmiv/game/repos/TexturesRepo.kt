package com.kadmiv.game.repos

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.kadmiv.game.actors.AnimationLoader

enum class TexturesRepo(val texture: String, val playMode: Animation.PlayMode, val duration: Float) {

    DEATH_1("${Paths.TEXTURES_DEATH_PATH.path}death_1.png", Animation.PlayMode.NORMAL, AnimationLoader.STANDARD_DURATION),
    DEATH_2("${Paths.TEXTURES_DEATH_PATH.path}death_2.png", Animation.PlayMode.NORMAL, AnimationLoader.STANDARD_DURATION),
    DEATH_3("${Paths.TEXTURES_DEATH_PATH.path}death_3.png", Animation.PlayMode.NORMAL, AnimationLoader.STANDARD_DURATION),
    DEATH_4("${Paths.TEXTURES_DEATH_PATH.path}death_4.png", Animation.PlayMode.NORMAL, AnimationLoader.STANDARD_DURATION),

    COWARD_1("${Paths.TEXTURES_DO_ELSE_PATH.path}coward_1.png", Animation.PlayMode.NORMAL, AnimationLoader.STANDARD_DURATION),
    COWARD_2("${Paths.TEXTURES_DO_ELSE_PATH.path}coward_2.png", Animation.PlayMode.LOOP, AnimationLoader.STANDARD_DURATION),
    MISSED_TO("${Paths.TEXTURES_DO_ELSE_PATH.path}missed_to.png", Animation.PlayMode.LOOP, AnimationLoader.STANDARD_DURATION),
    NOTHING_HAPPENED("${Paths.TEXTURES_DO_ELSE_PATH.path}nothing_happened.png", Animation.PlayMode.NORMAL, AnimationLoader.STANDARD_DURATION),
    READY("${Paths.TEXTURES_DO_ELSE_PATH.path}ready.png", Animation.PlayMode.NORMAL, AnimationLoader.STANDARD_DURATION),
    SHOOT("${Paths.TEXTURES_DO_ELSE_PATH.path}shoot.png", Animation.PlayMode.NORMAL, AnimationLoader.SLOW_DURATION),

    WIN_ALL_1("${Paths.TEXTURES_WIN_PATH.path}win_all_1.png", Animation.PlayMode.LOOP, AnimationLoader.STANDARD_DURATION),
    WIN_ALL_2_1("${Paths.TEXTURES_WIN_PATH.path}win_all_2_2.png", Animation.PlayMode.NORMAL, AnimationLoader.STANDARD_DURATION),
    WIN_ALL_2_2("${Paths.TEXTURES_WIN_PATH.path}win_all_2_2.png", Animation.PlayMode.LOOP, AnimationLoader.STANDARD_DURATION),
    WIN_ROUND("${Paths.TEXTURES_WIN_PATH.path}win_round.png", Animation.PlayMode.NORMAL, AnimationLoader.STANDARD_DURATION),

    GAME_BACKGROUND("${Paths.MAIN_TEXTURES_PATH.path}game_background.png", Animation.PlayMode.NORMAL, 0F),
    BULLET_DOT("${Paths.TEXTURES_BULLET_PATH.path}bullet.png", Animation.PlayMode.NORMAL, 0F),
    COMMANDS_BACKGROUND("${Paths.TEXTURES_COMMANDS_PATH.path}command_background.png", Animation.PlayMode.NORMAL, 0F);

    fun getAnimation(sectorWidth: Int, sectorHeight: Int): Animation<TextureRegion> {
        var loader = AnimationLoader(sectorWidth, sectorHeight)
        return loader.parseAnimation(this.texture)
    }

    fun getAnimation(actor: Actor): Animation<TextureRegion> {
        var loader = AnimationLoader(actor.width.toInt(), actor.height.toInt())
        var animation = loader.parseAnimation(this.texture)
        animation.playMode = this.playMode
        return animation
    }
}