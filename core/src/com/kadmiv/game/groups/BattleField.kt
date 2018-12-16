package com.kadmiv.game.groups

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.*
import com.kadmiv.game.actors.MainActor
import com.kadmiv.game.repos.TexturesRepo
import java.io.File


class BattleField(screenWidth: Float, screenHeight: Float) : Group() {


    lateinit var firstPlayerField: PlayerField
    lateinit var secondPlayerField: PlayerField

    init {
        height = screenHeight.toFloat()
        width = screenWidth.toFloat()

        var fon = MainActor(TexturesRepo.GAME_BACKGROUND.texture)
        this.addActor(fon)

        fon.scaleY = screenHeight / fon.height
        fon.scaleX = screenWidth / fon.width

        var fieldHeight = screenHeight / 2
        var fieldWidth = screenWidth

        // Add players
        // Set size and orientation of firs player
        firstPlayerField = PlayerField(0f, 0f, fieldWidth, fieldHeight);
        var centreX = firstPlayerField.width / 2
        var centreY = firstPlayerField.height / 2
        firstPlayerField.setOrigin(centreX, centreY)
        firstPlayerField.rotation = 180f
        firstPlayerField.flipPlayerX(true)
        addActor(firstPlayerField)

        // Set size and orientation of second player
        secondPlayerField = PlayerField(0f, fieldHeight, fieldWidth, fieldHeight);
        addActor(secondPlayerField)

    }

    fun getReady() {

    }

    fun getSteady() {

    }

    fun getBang() {

    }


    data class GameTouch(val x: Int, val y: Int, val time: Long)
}