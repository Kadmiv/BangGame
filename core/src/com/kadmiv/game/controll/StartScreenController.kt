package com.kadmiv.game.controll

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.scenes.scene2d.*
import com.kadmiv.game.screens.GameScreen
import com.kadmiv.game.screens.StartScreen
import io.reactivex.internal.disposables.DisposableHelper.dispose

class StartScreenController(screen: StartScreen) : InputListener() {

    var screen = screen

    override fun touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Boolean {


        System.out.println("Point $x, $y")
        var screenPart = event.target

        when (screenPart) {
            screen.startButton -> {
                screen.dispose()
                var gameScreen = GameScreen(screen.game, 2)
                screen.game.setScreen(gameScreen);
                System.out.println("Game screen created ")
            }
        }
        return true
    }

    override fun keyDown(event: InputEvent?, keycode: Int): Boolean {
        if (keycode == Input.Keys.BACK) {
            Gdx.app.exit()
        }
        return false
    }
}