package com.kadmiv.game.controll

import com.badlogic.gdx.Input
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.scenes.scene2d.*
import com.kadmiv.game.model.RuntimeRepo
import com.kadmiv.game.model.actors.Button
import com.kadmiv.game.model.groups.PlayerField
import com.kadmiv.game.model.groups.PlayerField.RoundCallBack
import com.kadmiv.game.screens.GameScreen
import com.kadmiv.game.screens.StartScreen
import java.lang.Thread.sleep

class GameScreenController(screen: GameScreen) : InputListener(), RandomTimer.TimeCallBack, RoundCallBack {

    var screen = screen
    private var playersReady = 0
    var playersCount = 0;

    companion object {
        fun playSound(sound: Sound) {
            sound.play()
        }
    }

    var timer = RandomTimer.Factory.instance()!!

    init {
        // Registration of Random timer callbacks
        timer.registerCallBack(this)
        // Registration of Score timer callbacks
        screen.firstPlayerField.registerCallBack(this)
        screen.secondPlayerField.registerCallBack(this)
    }


    override fun touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Boolean {


        System.out.println("Point $x, $y")
        var battleFieldPart = event.target

        when (battleFieldPart) {
            //Player touch *Ready button*
            screen.firstStartButton -> {
                deleteButton(screen.firstStartButton)
            }
            screen.secondStartButton -> {
                deleteButton(screen.secondStartButton)
            }
            screen.exitButton -> {
                exitFromGame()
                System.out.println("Exit")
            }
            screen.newGameButton -> {
                setNewBattle()
                System.out.println("New battel")
            }
            // Player shoots
            screen.firstPlayerField -> {
                System.out.println("Bang if First player")
                screen.firstPlayerField.getShoot(screen.secondPlayerField, playersReady >= playersCount)
            }
            screen.secondPlayerField -> {
                System.out.println("Bang is second Player ")
                screen.secondPlayerField.getShoot(screen.firstPlayerField, playersReady >= playersCount)
            }
        }
        return true
    }

    private fun deleteButton(button: Button) {

        button.remove()

        when (button) {
            screen.firstStartButton -> {
                screen.firstPlayerField.playerStart()
                GameScreenController.playSound(RuntimeRepo.audioRepo["ready_click"]!!);
                // For just case - I have some bug with this
                screen.firstPlayerField.haveBullet = true
                screen.secondPlayerField.haveBullet = true
                getStart()

            }
            screen.secondStartButton -> {
                screen.secondPlayerField.playerStart()
                GameScreenController.playSound(RuntimeRepo.audioRepo["ready_click"]!!);

                // For just case - I have some bug with this
                screen.firstPlayerField.haveBullet = true
                screen.secondPlayerField.haveBullet = true
                getStart()
            }
        }

    }

    private fun getStart() {
        playersReady++
        if (playersReady >= playersCount)
            timer.start()
    }

    fun keyDown(keycode: Int): Boolean {
        if (keycode == Input.Keys.BACK) {
            System.out.println("Is Back Button Pressed")
        }
        return false
    }

    override fun ready() {
        playSound(RuntimeRepo.audioRepo["ready"]!!);
    }

    override fun steady() {
        playSound(RuntimeRepo.audioRepo["steady"]!!);
    }

    override fun bang() {
        playSound(RuntimeRepo.audioRepo["finish_him"]!!);
    }

    override fun getNextRound() {
        System.out.println("1P : ${screen.firstPlayerField.playerScore} 2P : ${screen.secondPlayerField.playerScore}")
        System.out.println("Next round")
        Thread(Runnable {
            sleep(2000)
            screen.mainStage.addActor(screen.secondStartButton)
            screen.mainStage.addActor(screen.firstStartButton)
        }).start()
        playersReady = 0

    }

    override fun getNewGame() {
//        Thread(Runnable {
        screen.mainStage.addActor(screen.newGameButton)
        screen.mainStage.addActor(screen.exitButton)
//        }).start()
    }

    private fun setNewBattle() {
        GameScreenController.playSound(RuntimeRepo.audioRepo["ready_click"]!!);
        screen.dispose()
        var startScreen = GameScreen(screen.game, playersCount)
        screen.game.setScreen(startScreen);
        System.out.println("Start screen screen created ")
    }

    private fun exitFromGame() {
        GameScreenController.playSound(RuntimeRepo.audioRepo["ready_click"]!!);
        screen.dispose()
        var startScreen = StartScreen(screen.game)
        screen.game.setScreen(startScreen);
        System.out.println("Start screen screen created ")
    }

}