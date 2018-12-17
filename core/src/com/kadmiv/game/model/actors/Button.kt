package com.kadmiv.game.model.actors

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.TextButton


class Button(texture: Texture, text: String) : Group() {

    var text: String = text
    var background: MainActor

    init {
        // Create background image of button
        background = MainActor(texture)
        setPosition(0F, 0F)
        setBounds(this.x, this.y, background.width, background.height)

        //Creation of text for button
        var font = BitmapFont()
        var textButtonStyle = TextButton.TextButtonStyle()
        textButtonStyle.font = font
        var text = TextButton(text, textButtonStyle)
        var textX = (background.width - text.width) / 2
        var textY = (background.height - text.height) / 2
        text.setPosition(textX, textY)

        addActor(background)
        addActor(text)

    }

    override fun hit(x: Float, y: Float, touchable: Boolean): Actor? {
        if (x > 0 && x < width && y > 0 && y < height) {
            return this
        }
        return super.hit(x, y, touchable)
    }

    fun setToCentre(x: Float, y: Float) {
        var xPos = x - width / 2
        var yPos = y - height / 2
        setPosition(xPos, yPos)
    }

    fun relativelyWith(x: Float, y: Float) {
        setPosition(x + this.x, y + this.y)
    }

}