package com.kadmiv.game.controll

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.scenes.scene2d.*
import com.kadmiv.game.model.RuntimeRepo
import com.kadmiv.game.screens.GameScreen
import com.kadmiv.game.screens.StartScreen
import io.reactivex.internal.disposables.DisposableHelper.dispose
import kotlin.system.exitProcess

class StartScreenController(screen: StartScreen) : InputListener() {

    var screen = screen

    companion object {
        fun playSound(sound: Sound) {
            sound.play()
        }
    }

    override fun touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Boolean {


        System.out.println("Point $x, $y")
        var screenPart = event.target

        when (screenPart) {
            screen.startButton -> {
                StartScreenController.playSound(RuntimeRepo.audioRepo["ready_click"]!!);
                screen.dispose()
                var gameScreen = GameScreen(screen.game, 2)
                screen.game.setScreen(gameScreen);
                System.out.println("Game screen created ")
            }
            screen.exitButton -> {
                cloaseGame()
            }
        }
        return true
    }

    override fun keyDown(event: InputEvent?, keycode: Int): Boolean {
        if (keycode == Input.Keys.BACK) {
            cloaseGame()
        }
        return false
    }

    private fun cloaseGame() {
        screen.dispose()
        Gdx.app.exit()
        System.exit(0)
        System.out.println("Back key pressed")
        exitProcess(0)
    }
}