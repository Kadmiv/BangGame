package com.kadmiv.game.model.groups

import com.badlogic.gdx.graphics.g2d.Animation
import com.kadmiv.game.model.actors.*
import com.kadmiv.game.controll.GameScreenController
import com.kadmiv.game.controll.RandomTimer
import com.kadmiv.game.model.RuntimeRepo
import java.lang.Thread.sleep
import com.badlogic.gdx.scenes.scene2d.*
import com.kadmiv.game.screens.StartScreen
import io.reactivex.Observable
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit


class PlayerField(x: Float, y: Float, width: Float, height: Float) : Group(), RandomTimer.TimeCallBack {

    var player = Player()
    var timer: RandomTimer = RandomTimer.Factory.instance()

    var timeOfTouch: Long = 0
    var haveBullet = true
    var mayShoot = false;
    lateinit var touch: PlayerTouch
    var isDeath = false
    var playerScore = 0
    val MAX_WIN_NUM = 2

    open interface RoundCallBack {
        fun getNextRound()
        fun getNewGame()
    }

    lateinit var roundCallBack: RoundCallBack

    fun registerCallBack(roundCallBack: RoundCallBack) {
        this.roundCallBack = roundCallBack
    }

    init {
        // Registration CallBacks from Random timer
        timer.registerCallBack(this)

        setPosition(x, y)
        setBounds(this.x, this.y, width, height)
        touchable = Touchable.enabled

        // Set player settings
        var animation = RuntimeRepo.getAnimation(Player.ANIM_WIDTH, Player.ANIM_HEIGHT, "ready")
        player.animation = animation
        player.setRun(false)

        //Position
        var xPos = width / 2
        var yPos = height / 2
        player.setToCentre(xPos, yPos)
        addActor(player)
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


    fun addBulletDot() {
        // Random position of bullet dot
        var bulletX = (Math.random() * width).toFloat()
        var bulletY = (Math.random() * player.height).toFloat() + height / 2

        var newBullet = Bullet(RuntimeRepo.textureRepo["bullet"]!!)

        var playerField = player.hit(bulletX, bulletY, true);
        if (playerField == player) {
            addBulletDot()
            return
        }
        newBullet.setToCentre(bulletX, bulletY)
        newBullet.setScale(0.3f)
        addActor(newBullet)
    }

    fun playerStart() {
        mayShoot = false;
        // Set player settings
        var animation = RuntimeRepo.getAnimation(Player.ANIM_WIDTH, Player.ANIM_HEIGHT, "ready")
        player.animation = animation
        player.setRun(true)
    }

    // gameWasStart - needed so that the touchscreen does not react between battles
    fun getShoot(anotherPlayerField: PlayerField, gameWasStart: Boolean) {

        var anotherPlayer = anotherPlayerField.player
        if (!haveBullet || !gameWasStart)
            return

        var randomN: Int = Math.ceil(Math.random() * 4).toInt()
        haveBullet = false;

        // Get Shoot effects
        GameScreenController.playSound(RuntimeRepo.audioRepo["bullet_$randomN"]!!);
        player.toNextAnimation(RuntimeRepo.getAnimation(player, "shoot"))

        if (mayShoot) {
            // Set time of touch
            touch.finishTime = System.currentTimeMillis()

            // Another player has not yet clicked on the screen
            if (anotherPlayerField.touch.getTimeReaction() < 0) {
                // Run animations
                anotherPlayer.toNextAnimation(RuntimeRepo.getAnimation(player, "death_$randomN"))
                player.toNextAnimation(RuntimeRepo.getAnimation(player, "win_round"))
                // Run sounds
                GameScreenController.playSound(RuntimeRepo.audioRepo["death_$randomN"]!!);
                // Increment wins
                playerScore++

                // Restore bullet
                haveBullet = true
                anotherPlayerField.haveBullet = true

                if (MAX_WIN_NUM == playerScore) {
                    GameScreenController.playSound(RuntimeRepo.audioRepo["win_all_$randomN"]!!);
                    randomN = Math.ceil(Math.random() * 2).toInt()
                    var winAnimation = RuntimeRepo.getAnimation(player, "win_all_$randomN")
                    player.toNextAnimation(winAnimation, Animation.PlayMode.LOOP)

                    // To Start menu
                    roundCallBack.getNewGame()

                } else {
                    roundCallBack.getNextRound()
                }
            }

        } else {
            //If the player shot earlier than necessary
            sleep(5)
            anotherPlayerField.addBulletDot()
            if (anotherPlayerField.haveBullet) {
                player.toNextAnimation(RuntimeRepo.getAnimation(player, "coward_1"))
                player.toNextAnimation(RuntimeRepo.getAnimation(player, "coward_2"), Animation.PlayMode.LOOP)

            }
            // If another player shot earlier than necessary too
            else {
                player.toNextAnimation(RuntimeRepo.getAnimation(player, "missed_too"), Animation.PlayMode.LOOP)
                anotherPlayer.animation = RuntimeRepo.getAnimation(player, "nothing_happened")
                timer.stop()
                // Restore bullet
                haveBullet = true
                anotherPlayerField.haveBullet = true
                roundCallBack.getNextRound()
            }
        }

    }


    override fun ready() {
        System.out.println("Say ready")
    }

    override fun steady() {
        System.out.println("Say steady")
    }

    override fun bang() {
        touch = PlayerTouch(System.currentTimeMillis())
        mayShoot = true
        System.out.println("Say BANG !!!")
    }

    data class PlayerTouch(val startTime: Long) {
        var finishTime: Long = 0

        fun getTimeReaction(): Long {
            return finishTime - startTime
        }
    }

}

