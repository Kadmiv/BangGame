package com.kadmiv.game.groups

import com.badlogic.gdx.scenes.scene2d.*
import com.kadmiv.game.actors.MainActor


class BattleField(screenWidth: Float, screenHeight: Float) : Group() {


    lateinit var firstPlayerField: PlayerField
    lateinit var secondPlayerField: PlayerField

    init {
        height = screenHeight.toFloat()
        width = screenWidth.toFloat()

        var fon = MainActor("fon_texture.png")
        this.addActor(fon)

        fon.scaleY = screenHeight / fon.height
        fon.scaleX = screenWidth / fon.width

        var fieldHeight = screenHeight / 2
        var fieldWidth = screenWidth

        // Add players
        firstPlayerField = PlayerField(0f, 0f, fieldWidth, fieldHeight);
        addActor(firstPlayerField)

        secondPlayerField = PlayerField(0f, fieldHeight, fieldWidth, fieldHeight);
        addActor(secondPlayerField)

    }


    data class GameTouch(val x: Int, val y: Int, val time: Long)
}