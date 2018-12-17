package com.kadmiv.game.controll

import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.audio.Sound
import com.kadmiv.game.model.RuntimeRepo
import com.kadmiv.game.model.groups.BattleField
import java.awt.Point

class GameScreenController(var battleField: BattleField) : InputAdapter(), RandomTimer.CallBack {

    companion object {
        fun playSound(sound: Sound) {
            sound.play()
        }
    }

    var timer = RandomTimer.Factory.instance()!!

    init {
        // Set random game timer
        timer.registerCallBack(this)
        timer.start()
    }


    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {

        // Because Y-axis is rotate
        var trueScreenY = (battleField.height - screenY).toInt()

        try {
            var battleFieldPart = battleField.hit(screenX.toFloat(), trueScreenY.toFloat(), true)
            when (battleFieldPart) {
                battleField.firstPlayerField.startButton -> {
                    System.out.println("Bang is First Start Button")
//                    battleField.secondPlayerField.getShoot(battleField.firstPlayerField, mayShoot)
                }
                battleField.firstPlayerField -> {
                    System.out.println("Bang is First Player")
                    battleField.firstPlayerField.getShoot(battleField.secondPlayerField)
                }

                battleField.secondPlayerField -> {
                    System.out.println("Bang is Second Player")
                    battleField.secondPlayerField.getShoot(battleField.firstPlayerField)
                }
                battleField.secondPlayerField.startButton -> {
                    System.out.println("Bang is Second Start Button")
//                    battleField.secondPlayerField.getShoot(battleField.firstPlayerField, mayShoot)
                }

            }
            System.out.println("Point $screenX, $screenY")
        } catch (ex: Exception) {
            ex.stackTrace
        }
        return super.touchDown(screenX, screenY, pointer, button)
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

}