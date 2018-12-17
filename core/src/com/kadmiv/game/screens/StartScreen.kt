package com.kadmiv.game.screens

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.*
import com.kadmiv.game.model.actors.MainActor
import com.kadmiv.game.controll.StartScreenController
import com.kadmiv.game.model.RuntimeRepo
import com.badlogic.gdx.scenes.scene2d.InputListener


class StartScreen(game: Game) : InputAdapter(), Screen {

    lateinit var mainStage: Stage
    lateinit var startButton: Group
    lateinit var gameBackground: MainActor
    lateinit var buttonBackground : MainActor
    var width = Gdx.graphics.width.toFloat()
    var height = Gdx.graphics.height.toFloat();

    init {

        var height = Gdx.graphics.height.toFloat();

        gameBackground = MainActor(RuntimeRepo.textureRepo["game_background"]!!)
        gameBackground.scaleY = height / gameBackground.height
        gameBackground.scaleX = width / gameBackground.width

        startButton = Group()
        buttonBackground = MainActor(RuntimeRepo.textureRepo["button"]!!)
        startButton.addActor(buttonBackground)
        buttonBackground.addListener(object : InputListener() {
            override fun touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                System.out.println("Start Button is Pressed ")
                return true
            }

            override fun touchDragged(event: InputEvent, x: Float, y: Float, pointer: Int) {
            }

            override fun touchUp(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int) {
                System.out.println("Start Button is Pressed ")

            }

        })
//        var font = BitmapFont()
////        var skin = Skin()
////        buttonAtlas = TextureAtlas(Gdx.files.internal("buttons/buttons.pack"))
////        skin.t
//        var textButtonStyle = TextButton.TextButtonStyle()
//        textButtonStyle.font = font
////        textButtonStyle.up = skin.getDrawable("up-button")
////        textButtonStyle.down = skin.getDrawable("down-button")
////        textButtonStyle.checked = skin.getDrawable("checked-button")
//        startButton = TextButton("Start", textButtonStyle)


        // Create Stage and add actors
        mainStage = Stage()
        mainStage.addActor(gameBackground)
        mainStage.addActor(startButton)
        // Set game controller
        var controller = StartScreenController(this);
        Gdx.input.inputProcessor = controller


    }

    override fun render(delta: Float) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
//        camera.update();
        mainStage.draw()
//        screen.act(Gdx.graphics.deltaTime)
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