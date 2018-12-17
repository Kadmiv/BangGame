package com.kadmiv.game.model.actors

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor

open class MainActor(texture: Texture) : Actor() {
    lateinit var textureRegion: TextureRegion

    init {
        setSize(texture.width.toFloat(), texture.height.toFloat())
        textureRegion = TextureRegion(texture)
    }

    override fun draw(batch: Batch, parentAlpha: Float) {
        batch.setColor(1f, 1f, 1f, parentAlpha);
        batch.draw(textureRegion, x, y, width * scaleX, height * scaleY)
    }

    fun setToCentre(x: Float, y: Float) {
        var xPos = x - width / 2
        var yPos = y - height / 2
        setPosition(xPos, yPos)
    }

    override fun hit(x: Float, y: Float, touchable: Boolean): Actor? {
        if (x > 0 && x < width && y > 0 && y < height) {
//            System.out.println("Hint in ${this.javaClass}")
            return this
        }
        return null
    }
}