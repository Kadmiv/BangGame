package com.kadmiv.game.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimationLoader {

    public static float STANDARD_DURATION = 0.05f;
    public static float SLOW_DURATION = 0.1f;

    private int sectorWidth;
    private int sectorHeight;
    private float duration = STANDARD_DURATION;


    public AnimationLoader(int sectorWidth, int sectorHeight) {
        this.sectorWidth = sectorWidth;
        this.sectorHeight = sectorHeight;
    }

    public AnimationLoader(int sectorWidth, int sectorHeight, float duration) {
        this(sectorWidth, sectorHeight);
        this.duration = duration;
    }


    public Animation<TextureRegion> parseAnimation(String texturePNG) {
        Texture texture = new Texture(texturePNG);
        return parseAnimation(texture);
    }

    public Animation<TextureRegion> parseAnimation(Texture texture) {
        TextureRegion[][] animRepo = TextureRegion.split(texture, sectorWidth, sectorHeight);

        TextureRegion[] walkFrames = new TextureRegion[animRepo.length * animRepo[0].length];
        int index = 0;
        for (TextureRegion[] line : animRepo) {
            for (TextureRegion part : line) {
                walkFrames[index++] = part;
            }
        }

        // Initialize the Animation with the frame interval and array of frames
        Animation<TextureRegion> animation = new Animation<TextureRegion>(duration, walkFrames);
        return animation;
    }
}
