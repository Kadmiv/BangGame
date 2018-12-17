package com.kadmiv.game

import com.badlogic.gdx.*
import com.kadmiv.game.model.RuntimeRepo
import com.kadmiv.game.screens.GameScreen
import com.kadmiv.game.screens.StartScreen


class MainClass : Game() {

    companion object {
        val WIDTH = 480
        val HEIGHT = 800
        val TITLE = "Bang Game"
        var startScreen: Screen? = null
        var gameScreen: Screen? = null
    }

    override fun create() {
        //Load game files to repo
        RuntimeRepo()
        // Creation of game screens (activity)
        startScreen = StartScreen(this)
        gameScreen = GameScreen(this)
//        setScreen(startScreen)
        setScreen(gameScreen)
    }
}