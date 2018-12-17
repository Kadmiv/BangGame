package com.kadmiv.game.controll

import com.badlogic.gdx.InputAdapter
import com.kadmiv.game.screens.StartScreen

class StartScreenController(startScreen: StartScreen) : InputAdapter() {

    var screen = startScreen

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {

        // Because Y-axis is rotate
        var trueScreenY = (screen.height - screenY).toInt()

        try {
            var screenPart = screen.mainStage.hit(screenX.toFloat(), trueScreenY.toFloat(), true)
            when (screenPart) {
                screen.startButton -> {
                    System.out.println("Start Button is Pressed ")
                    System.out.println("Point $screenX, $trueScreenY")
                }
            }

        } catch (ex: Exception) {
            ex.stackTrace
        }
        return super.touchDown(screenX, screenY, pointer, button)
    }

}