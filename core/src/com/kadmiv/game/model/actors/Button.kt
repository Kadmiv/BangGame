package com.kadmiv.game.model.actors

import com.badlogic.gdx.graphics.Texture


class Button(texture: Texture, text: String) : MainActor(texture) {

    var text: String = text

    var backGround: MainActor

    init {
        backGround = MainActor(texture)
////        this.setSize(backGround.width,backGround.height)
//        addActor(backGround)
//
//        var font = BitmapFont()
////        var skin = Skin()
////        buttonAtlas = TextureAtlas(Gdx.files.internal("buttons/buttons.pack"))
////        skin.t
//        var textButtonStyle = TextButtonStyle()
//        textButtonStyle.font = font
////        textButtonStyle.up = skin.getDrawable("up-button")
////        textButtonStyle.down = skin.getDrawable("down-button")
////        textButtonStyle.checked = skin.getDrawable("checked-button")
//        var button = TextButton(text, textButtonStyle)
//        addActor(button)

    }


}