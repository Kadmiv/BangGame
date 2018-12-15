package com.kadmiv.game.actors

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor

class Player(x: Float, y: Float, texturePNG: String) : MainActor(texturePNG) {
    init {
        setPosition(x, y)
    }
}