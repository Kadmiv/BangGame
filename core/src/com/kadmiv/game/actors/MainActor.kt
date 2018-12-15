package com.kadmiv.game.actors

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor

open class MainActor(texturePNG: String) : Actor() {
    var texture: Texture = Texture(texturePNG)
    var textureRegion = TextureRegion(texture)

    init {
        width = texture.width.toFloat()
        height = texture.height.toFloat()
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