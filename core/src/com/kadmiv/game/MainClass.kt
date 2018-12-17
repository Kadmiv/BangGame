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
    }

    override fun create() {
        //Load game files to repo
        RuntimeRepo()

        startScreen = StartScreen(this)
        setScreen(startScreen)
    }
}