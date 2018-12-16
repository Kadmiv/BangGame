package com.kadmiv.game.actors;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import org.jetbrains.annotations.NotNull;

import static java.lang.Thread.sleep;

public class Player extends AnimatedActor {

    public final static int ANIM_WIDTH = 210;
    public final static int ANIM_HEIGHT = 140;

    public void playSong(@NotNull Sound song) {
        song.play();
    }

    //This function need for change animation for new if action contains less one animation

}
