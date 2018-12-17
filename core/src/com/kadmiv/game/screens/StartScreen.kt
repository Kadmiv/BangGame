package com.kadmiv.game.screens

import com.badlogic.gdx.*
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.*
import com.kadmiv.game.model.actors.MainActor
import com.kadmiv.game.controll.StartScreenController
import com.kadmiv.game.model.RuntimeRepo
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.utils.Align
import com.kadmiv.game.MainClass
import com.kadmiv.game.model.actors.Button


class StartScreen(game: Game) : InputAdapter(), Screen {

    lateinit var mainStage: Stage
    lateinit var startButton: Button

    var width = Gdx.graphics.width.toFloat()
    var height = Gdx.graphics.height.toFloat();

    init {

        mainStage = Stage()

        var gameBackground = MainActor(RuntimeRepo.textureRepo["game_background"]!!)
        mainStage.addActor(gameBackground)

        gameBackground.scaleX = width / gameBackground.width
        gameBackground.scaleY = height / gameBackground.height


        // Add Start first player button
        startButton = Button(RuntimeRepo.textureRepo["button"]!!, "Let's GO")
        startButton.setOrigin(Align.center)
        startButton.setToCentre(width / 2, height / 2)
        mainStage.addActor(startButton)

        startButton.addListener(object : InputListener() {
            override fun touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                dispose()
                var gameScreen = GameScreen(game, 2)
                game.setScreen(gameScreen);
                System.out.println("Game screen created ")

                return true
            }

            override fun keyDown(event: InputEvent, keycode: Int): Boolean {
                if (keycode == Input.Keys.BACK) {
                    System.out.println("Is Back Button Pressed")
                }
                return false
            }
        });

        Gdx.input.inputProcessor = mainStage
        Gdx.input.isCatchBackKey = true;

    }

    override fun render(delta: Float) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
//        camera.update();
        mainStage.draw()
//        screen.act(Gdx.graphics.deltaTime)
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