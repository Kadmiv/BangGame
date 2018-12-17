package com.kadmiv.game.model.groups

import com.badlogic.gdx.graphics.g2d.Animation
import com.kadmiv.game.model.actors.*
import com.kadmiv.game.controll.GameScreenController
import com.kadmiv.game.controll.RandomTimer
import com.kadmiv.game.model.RuntimeRepo
import java.lang.Thread.sleep
import com.badlogic.gdx.scenes.scene2d.*


class PlayerField(x: Float, y: Float, width: Float, height: Float) : Group(), RandomTimer.TimeCallBack {

    var player = Player()
    var timer: RandomTimer = RandomTimer.Factory.instance()

    var timeOfTouch: Long = 0
    var haveBullet = true
    var mayShoot = false;
    lateinit var touch: PlayerTouch
    var isDeath = false
    var playerScore = 0
    val MAX_WIN_NUM = 5

    open interface RoundCallBack {
        fun getNextRound(score: Int, player: PlayerField)
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
        haveBullet = true
        // Set player settings
        var animation = RuntimeRepo.getAnimation(Player.ANIM_WIDTH, Player.ANIM_HEIGHT, "ready")
        player.animation = animation
        player.setRun(true)
    }

    fun getShoot(anotherPlayerField: PlayerField, gameWasStart: Boolean) {
        var anotherPlayer = anotherPlayerField.player
        if (haveBullet && gameWasStart) {

            var randomN: Int = Math.ceil(Math.random() * 4).toInt()

            if (!mayShoot) {
                // Get Shoot effects
                GameScreenController.playSound(RuntimeRepo.audioRepo["bullet_$randomN"]!!);
                player.toNextAnimation(RuntimeRepo.getAnimation(player, "shoot"))
                sleep(2)
                anotherPlayerField.addBulletDot()
                if (anotherPlayerField.haveBullet) {
                    player.toNextAnimation(RuntimeRepo.getAnimation(player, "coward_1"))
                    player.toNextAnimation(RuntimeRepo.getAnimation(player, "coward_2"), Animation.PlayMode.LOOP)

                } else {
                    player.toNextAnimation(RuntimeRepo.getAnimation(player, "missed_too"), Animation.PlayMode.LOOP)
                    anotherPlayer.animation = RuntimeRepo.getAnimation(player, "nothing_happened")
                    timer.stop()
                    roundCallBack.getNextRound(playerScore, this)
                }
            } else {
                // Set time of touch
                touch.finishTime = System.currentTimeMillis()

                if (!this.isDeath) {
                    // Get Shoot effects
                    GameScreenController.playSound(RuntimeRepo.audioRepo["bullet_$randomN"]!!);
                    player.toNextAnimation(RuntimeRepo.getAnimation(player, "shoot"))
                    sleep(3)
                    // Another player has not yet clicked on the screen
                    if (anotherPlayerField.touch.getTimeReaction() < 0) {
                        anotherPlayerField.isDeath = true
                        // Run animations
                        anotherPlayer.toNextAnimation(RuntimeRepo.getAnimation(player, "death_$randomN"))
                        player.toNextAnimation(RuntimeRepo.getAnimation(player, "win_round"))
                        // Run sounds
                        GameScreenController.playSound(RuntimeRepo.audioRepo["death_$randomN"]!!);
                        // Increment wins
                        playerScore++
                    }
                    roundCallBack.getNextRound(playerScore, this)
                }
            }

            // Run animation and sound, if player to win in all 5 battles
            if (MAX_WIN_NUM == playerScore) {
                sleep(250)
                GameScreenController.playSound(RuntimeRepo.audioRepo["win_all_$randomN"]!!);
                randomN = Math.ceil(Math.random() * 2).toInt()
                var winAnimation = RuntimeRepo.getAnimation(player, "win_all_$randomN")
                winAnimation.playMode = Animation.PlayMode.LOOP
                player.toNextAnimation(winAnimation)
            }
            haveBullet = false;
        }
    }

    override fun ready() {
        mayShoot = false;
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