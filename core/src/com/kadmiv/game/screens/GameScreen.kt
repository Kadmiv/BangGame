package com.kadmiv.game.screens

import com.badlogic.gdx.*
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.scenes.scene2d.Stage
import com.kadmiv.game.controll.GameScreenController
import com.kadmiv.game.model.groups.BattleField

class GameScreen(game: Game) : InputAdapter(), Screen {

    lateinit var camera: OrthographicCamera
    lateinit var battleField: BattleField
    lateinit var mainStage: Stage

    init {

        var screenWidth = Gdx.graphics.width.toFloat();
        var screenHeight = Gdx.graphics.height.toFloat();

        mainStage = Stage()
        battleField = BattleField(screenWidth, screenHeight)
        mainStage.addActor(battleField)
        // Set game controller
        var controller = GameScreenController(battleField);
        Gdx.input.inputProcessor = controller


    }

    override fun render(delta: Float) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
//        camera.update();
        mainStage.draw()
//        mainStage.act(Gdx.graphics.deltaTime)
    }

    override fun dispose() {
        Gdx.input.inputProcessor = null
    }

    override fun pause() {}
    override fun resume() {}
    override fun resize(width: Int, height: Int) {}
    override fun hide() {}
    override fun show() {}


}