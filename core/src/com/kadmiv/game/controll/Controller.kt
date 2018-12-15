package com.kadmiv.game.controll

import com.badlogic.gdx.InputAdapter
import com.kadmiv.game.groups.BattleField

class Controller(var battleField: BattleField) : InputAdapter() {


    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {

        // Because Y-axis is rotate
        var trueScreenY = (battleField.height - screenY).toInt()

        var touch = BattleField.GameTouch(screenX, trueScreenY, System.currentTimeMillis())
        try {
            var playerField = battleField.hit(touch.x.toFloat(), touch.y.toFloat(), true);
            if (playerField == battleField.firstPlayerField) {
                System.out.println("Bang is First Player")
                battleField.secondPlayerField.addShot(Math.random() > 0.5)
            }
            if (playerField == battleField.secondPlayerField) {
                System.out.println("Bang is Second Player")
                battleField.firstPlayerField.addShot(Math.random() > 0.5)
            }

            System.out.println("Point $screenX, $screenY")
        } catch (ex: Exception) {
            ex.stackTrace
        }
        return super.touchDown(screenX, screenY, pointer, button)
    }
}