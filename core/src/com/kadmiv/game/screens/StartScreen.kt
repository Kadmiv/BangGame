package com.kadmiv.game.screens

import com.badlogic.gdx.*
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.*
import com.kadmiv.game.model.actors.MainActor
import com.kadmiv.game.model.RuntimeRepo
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.utils.Align
import com.kadmiv.game.controll.StartScreenController
import com.kadmiv.game.model.actors.Button


class StartScreen(game: Game) : InputAdapter(), Screen {

    var mainStage: Stage
    var game = game
    lateinit var startButton: Button
    lateinit var exitButton: Button

    var width = Gdx.graphics.width.toFloat()
    var height = Gdx.graphics.height.toFloat();

    init {

        var gameBackground = MainActor(RuntimeRepo.textureRepo["game_background"]!!)
        gameBackground.scaleX = width / gameBackground.width
        gameBackground.scaleY = height / gameBackground.height

        mainStage = Stage()

        // Create "Exit" button
        exitButton = Button(RuntimeRepo.textureRepo["button"]!!, "Exit")
        exitButton.setToCentre(width / 2 - exitButton.width, width / 2 - exitButton.height)
        exitButton.touchable = Touchable.enabled

        // Add Start first player button
        startButton = Button(RuntimeRepo.textureRepo["button"]!!, "Let's GO")
        startButton.setOrigin(Align.center)
        startButton.setToCentre(width / 2, width / 2)

        // Add actors
        mainStage.addActor(gameBackground)
        mainStage.addActor(startButton)
//        mainStage.addActor(exitButton)

        // Add input listener
        var controller = StartScreenController(this)
        startButton.addListener(controller)
        Gdx.input.inputProcessor = mainStage
        Gdx.input.isCatchBackKey = true;

    }

    override fun render(delta: Float) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        mainStage.draw()
    }

    override fun dispose() {
        Gdx.input.inputProcessor = null
        mainStage.dispose()
    }

    override fun pause() {}
    override fun resume() {}
    override fun resize(width: Int, height: Int) {}
    override fun hide() {}
    override fun show() {}
}