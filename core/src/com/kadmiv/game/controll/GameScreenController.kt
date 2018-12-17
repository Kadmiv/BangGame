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
        getStart()
        button.remove()
        GameScreenController.playSound(RuntimeRepo.audioRepo["ready_click"]!!);

        // For just case - I have some bug with this
        screen.firstPlayerField.haveBullet = true
        screen.secondPlayerField.haveBullet = true

        when (button) {
            screen.firstStartButton -> {
                screen.firstPlayerField.playerStart()

            }
            screen.secondStartButton -> {
                screen.secondPlayerField.playerStart()
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

    override fun getNextRound(score: Int, player: PlayerField) {
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
        screen.dispose()
        var mainScreen = StartScreen(screen.game)
        screen.game.screen = mainScreen;
        System.out.println("Start screen created")
    }

}