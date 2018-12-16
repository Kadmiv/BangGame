package com.kadmiv.game.controll

import com.badlogic.gdx.InputAdapter
import com.kadmiv.game.repos.TexturesRepo
import com.kadmiv.game.actors.RandomTimer
import com.kadmiv.game.groups.BattleField
import com.kadmiv.game.groups.PlayerField

class Controller(var battleField: BattleField) : InputAdapter(), RandomTimer.CallBack {

    var mayShoot = false;

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {

        // Because Y-axis is rotate
        var trueScreenY = (battleField.height - screenY).toInt()

        var touch = BattleField.GameTouch(screenX, trueScreenY, System.currentTimeMillis())
        try {
            var playerField = battleField.hit(touch.x.toFloat(), touch.y.toFloat(), true) as PlayerField;
            var haveBullet = playerField.haveBullet
            if (playerField == battleField.firstPlayerField && haveBullet) {
                var player = playerField.player
                player.animation = TexturesRepo.SHOOT.getAnimation(player)
                System.out.println("Bang is First Player")
                playerField.getShoot(battleField.secondPlayerField, mayShoot)

            }
            if (playerField == battleField.secondPlayerField && haveBullet) {

                var player = playerField.player
                player.animation = TexturesRepo.SHOOT.getAnimation(player)
                System.out.println("Bang is Second Player")
                playerField.getShoot(battleField.firstPlayerField, mayShoot)
            }

            System.out.println("Point $screenX, $screenY")
        } catch (ex: Exception) {
            ex.stackTrace
        }
        return super.touchDown(screenX, screenY, pointer, button)
    }

    override fun ready() {
        battleField.getReady()
        mayShoot = false;
        System.out.println("Say ready")
    }

    override fun steady() {
        battleField.getSteady()
        System.out.println("Say steady")
    }

    override fun bang() {
        mayShoot = true
        battleField.getBang()
        System.out.println("Say BANG !!!")
    }
}