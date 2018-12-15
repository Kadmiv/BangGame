package com.kadmiv.game.groups

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Group
import com.kadmiv.game.actors.Bullet
import com.kadmiv.game.actors.Player

class PlayerField(x: Float, y: Float, width: Float, height: Float) : Group() {

    private var player: Player
    private var bulletHoles: Group = Group()
    var timeOfTouch: Long = 0

    init {
        setPosition(x.toFloat(), y.toFloat())
        setSize(width.toFloat(), height.toFloat())

        // Add player texture and set position
        player = Player(0f, 0f, "woody.png")
        var xPos = width / 2
        var yPos = height / 2
        player.setToCentre(xPos, yPos)
        addActor(player)

        // Add bullet list
        addActor(bulletHoles)
    }

    override fun hit(x: Float, y: Float, touchable: Boolean): Actor? {
        if (x > 0 && x < width && y > 0 && y < height) {
            timeOfTouch = System.currentTimeMillis()
            return this
        }
        return null
    }


    fun addShot(win: Boolean) {

        if (win)
            addDeathShot()
        else
            addMissedShot()
    }

    fun addDeathShot() {
        var bulletX = player.width+player.x
        var bulletY = player.height+player.y
        var newBullet = Bullet("blood.png")
        newBullet.setToCentre(bulletX, bulletY)
        newBullet.rotation = (360 * Math.random()).toFloat()
        newBullet.setScale(0.4f)
        addActor(newBullet)
    }

    fun addMissedShot() {
        var bulletX = (Math.random() * width).toFloat()
        var bulletY = (Math.random() * player.height).toFloat() + height / 2
        var randomPicture = (Math.random() * 4).toInt()
        var newBullet = Bullet("bullet.png")

        var playerField = player.hit(bulletX, bulletY, true);
        if (playerField == player) {
            addMissedShot()
            return
        }
//        newBullet.setPosition(x + sprite.x / 2, y + sprite.y / 2)
        newBullet.setToCentre(bulletX, bulletY)
        newBullet.setScale(0.3f)
        addActor(newBullet)
    }


}