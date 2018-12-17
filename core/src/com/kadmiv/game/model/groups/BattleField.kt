package com.kadmiv.game.model.groups

import com.badlogic.gdx.scenes.scene2d.*
import com.kadmiv.game.model.actors.MainActor
import com.kadmiv.game.controll.GameScreenController
import com.kadmiv.game.controll.RandomTimer
import com.kadmiv.game.model.RuntimeRepo


class BattleField(screenWidth: Float, screenHeight: Float) : Group(), RandomTimer.CallBack {


    lateinit var firstPlayerField: PlayerField
    lateinit var secondPlayerField: PlayerField
    var timer = RandomTimer.Factory.instance()!!

    init {
        height = screenHeight
        width = screenWidth

        var gameBackground = MainActor(RuntimeRepo.textureRepo["game_background"]!!)
        this.addActor(gameBackground)

        gameBackground.scaleY = height / gameBackground.height
        gameBackground.scaleX = width / gameBackground.width

        var fieldHeight = height / 2
        var fieldWidth = width

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


    override fun ready() {
    }

    override fun steady() {
    }

    override fun bang() {
    }

}