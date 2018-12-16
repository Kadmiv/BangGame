package com.kadmiv.game.groups

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Group
import com.kadmiv.game.actors.*
import com.kadmiv.game.repos.TexturesRepo
import com.kadmiv.game.repos.AudioRepo

class PlayerField(x: Float, y: Float, width: Float, height: Float) : Group() {

    var player = Player()
    var bulletHoles: Group = Group()
    var timeOfTouch: Long = 0
    var haveBullet = true;

    init {
        setPosition(x.toFloat(), y.toFloat())
        setSize(width.toFloat(), height.toFloat())

        // Set player settings
        var animation = TexturesRepo.READY.getAnimation(Player.ANIM_WIDTH, Player.ANIM_HEIGHT)
        player.animation = animation
        player.setRun(true)

        //Position
        var xPos = width / 2
        var yPos = height / 2
        player.setToCentre(xPos, yPos)
        addActor(player)
        // Add bullet list
        addActor(bulletHoles)
    }

    fun flipPlayerX(setFlip: Boolean) {
        player.flipX = setFlip
    }

    fun flipPlayerY(setFlip: Boolean) {
        player.flipY = setFlip
    }

    override fun hit(x: Float, y: Float, touchable: Boolean): Actor? {
        if (x > 0 && x < width && y > 0 && y < height) {
            timeOfTouch = System.currentTimeMillis()
            return this
        }
        return null
    }


    fun addBulletDot(win: Boolean) {

        var bulletX = (Math.random() * width).toFloat()
        var bulletY = (Math.random() * player.height).toFloat() + height / 2
        var randomPicture = (Math.random() * 4).toInt()
        var newBullet = Bullet(TexturesRepo.BULLET_DOT.texture)

        var playerField = player.hit(bulletX, bulletY, true);
        if (playerField == player) {
            addBulletDot(win)
            return
        }
//        newBullet.setPosition(x + sprite.x / 2, y + sprite.y / 2)
        newBullet.setToCentre(bulletX, bulletY)
        newBullet.setScale(0.3f)
        addActor(newBullet)
    }


    fun getShoot(anotherPlayerField: PlayerField, mayShoot: Boolean) {
        var anotherPlayer = anotherPlayerField.player
        if (haveBullet) {
            player.playAfter(TexturesRepo.SHOOT.getAnimation(player))
            player.playSong(AudioRepo.SHOOT_1.getSong());
            if (!mayShoot) {
                anotherPlayerField.addBulletDot(Math.random() > 0.5)
                if (anotherPlayerField.haveBullet) {
                    player.playAfter(TexturesRepo.COWARD_1.getAnimation(player))
                    player.playAfter(TexturesRepo.COWARD_2.getAnimation(player))

                } else {
                    player.playAfter(TexturesRepo.MISSED_TO.getAnimation(player))
                }
            } else {
                if (anotherPlayerField.haveBullet) {
                    player.playAfter(TexturesRepo.DEATH_1.getAnimation(player))
                    anotherPlayer.playAfter(TexturesRepo.WIN_ALL_2_1.getAnimation(player))
                } else {
                    anotherPlayer.playAfter(TexturesRepo.DEATH_1.getAnimation(player))
                    player.playAfter(TexturesRepo.WIN_ALL_2_1.getAnimation(player))
                }
                player.playSong(AudioRepo.DEATH_1.getSong());
            }
        }

        haveBullet = false;
    }


}