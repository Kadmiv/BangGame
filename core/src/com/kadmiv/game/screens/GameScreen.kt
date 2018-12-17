package com.kadmiv.game.screens

import com.badlogic.gdx.*
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.utils.Align
import com.kadmiv.game.controll.GameScreenController
import com.kadmiv.game.controll.RandomTimer
import com.kadmiv.game.model.RuntimeRepo
import com.kadmiv.game.model.actors.Button
import com.kadmiv.game.model.actors.MainActor
import com.kadmiv.game.model.groups.PlayerField


class GameScreen(game: Game, countPlayer: Int) : InputAdapter(), Screen {

    var game = game
    lateinit var mainStage: Stage
    lateinit var firstPlayerField: PlayerField
    lateinit var secondPlayerField: PlayerField

    lateinit var firstStartButton: Button
    lateinit var secondStartButton: Button

    lateinit var firstScore: Button
    lateinit var secondScore: Button

    lateinit var commandsView: Button

    lateinit var newGameButton: Button
    lateinit var exitButton: Button

    var timer = RandomTimer.Factory.instance()!!

    init {

        var screenWidth = Gdx.graphics.width.toFloat();
        var screenHeight = Gdx.graphics.height.toFloat();

        mainStage = Stage()

        var gameBackground = MainActor(RuntimeRepo.textureRepo["game_background"]!!)
        mainStage.addActor(gameBackground)

        gameBackground.scaleX = screenWidth / gameBackground.width
        gameBackground.scaleY = screenHeight / gameBackground.height

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
        mainStage.addActor(firstPlayerField)

        // Set size and orientation of second player
        secondPlayerField = PlayerField(0f, fieldHeight, fieldWidth, fieldHeight);
        mainStage.addActor(secondPlayerField)

        // Add Start first player button
        firstStartButton = Button(RuntimeRepo.textureRepo["button"]!!, "Start")
        firstStartButton.setToCentre(firstPlayerField.width / 2, firstPlayerField.height / 4)
        mainStage.addActor(firstStartButton)

        // Add Start second player button
        secondStartButton = Button(RuntimeRepo.textureRepo["button"]!!, "Start")
        secondStartButton.setToCentre(secondPlayerField.width / 2, screenHeight - secondPlayerField.height / 4)
        secondStartButton.setOrigin(Align.center)
        secondStartButton.rotation = 180F
        mainStage.addActor(secondStartButton)

        // Create "New game" button
        newGameButton = Button(RuntimeRepo.textureRepo["button"]!!, "New Game")
        newGameButton.setToCentre(screenWidth - firstPlayerField.width / 4, firstPlayerField.height / 2)

        // Create "Exit" button
        exitButton = Button(RuntimeRepo.textureRepo["button"]!!, "Exit")
        exitButton.setToCentre(secondPlayerField.width / 4, secondPlayerField.height / 2)

        // Create first player score info panel
        firstScore = Button(RuntimeRepo.textureRepo["button"]!!, "5")
        firstScore.setToCentre(firstPlayerField.width - firstScore.width, screenHeight / 2)

        // Create second player score info panel
        secondScore = Button(RuntimeRepo.textureRepo["button"]!!, "5")
        secondScore.setToCentre(secondPlayerField.x + secondScore.width, screenHeight / 2)
        secondScore.setOrigin(Align.center)
        secondScore.rotation = 180F

        // Create of game commands
        commandsView = Button(RuntimeRepo.textureRepo["command_background"]!!, "Steady")
        commandsView.setToCentre(screenWidth / 2, screenHeight / 2)

        // Initialization of game controller
        var controller = GameScreenController(this);
        controller.playersCount = countPlayer
        mainStage.addListener(controller)
        Gdx.input.inputProcessor = mainStage
        Gdx.input.isCatchBackKey = true;

    }

    override fun render(delta: Float) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        mainStage.draw()
    }

    override fun dispose() {
        mainStage.dispose()
        Gdx.input.inputProcessor = null
    }

    override fun pause() {}
    override fun resume() {}
    override fun resize(width: Int, height: Int) {}
    override fun hide() {}
    override fun show() {}

}