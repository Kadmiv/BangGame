package com.kadmiv.game

import com.badlogic.gdx.*
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.scenes.scene2d.Stage
import com.kadmiv.game.controll.Controller
import com.kadmiv.game.groups.BattleField
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.kadmiv.game.actors.AnimatedActor
import com.kadmiv.game.actors.AnimationLoader
import com.kadmiv.game.actors.Player
import com.kadmiv.game.actors.RandomTimer


class MainClass : InputAdapter(), ApplicationListener {

    companion object {
        val WIDTH = 480
        val HEIGHT = 800
        val TITLE = "Bang Game"
    }


    lateinit var camera: OrthographicCamera
    lateinit var battleField: BattleField
    lateinit var mainStage: Stage

    override fun create() {
        var screenWidth = Gdx.graphics.width.toFloat();
        var screenHeight = Gdx.graphics.height.toFloat();

//        camera = OrthographicCamera()
//        camera.setToOrtho(true, screenWidth, screenHeight)
//        val viewp = FitViewport(screenWidth, screenHeight, camera)
//        var batch = SpriteBatch()
//        mainStage = Stage(viewp, batch)

        mainStage = Stage()
        battleField = BattleField(screenWidth, screenHeight)
        mainStage.addActor(battleField)
        // Set game controller
        var controller = Controller(battleField);
        Gdx.input.inputProcessor = controller
        // Set random game timer
        var timer = RandomTimer()
        timer.registerCallBack(controller)
        timer.start()
    }

    override fun render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
//        camera.update();
        mainStage.draw()
//        mainStage.act(Gdx.graphics.deltaTime)
    }

    override fun pause() {}
    override fun resume() {}
    override fun resize(width: Int, height: Int) {}

    override fun dispose() {
        Gdx.input.inputProcessor = null
    }
}