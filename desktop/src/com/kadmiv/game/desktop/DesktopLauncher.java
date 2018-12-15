package com.kadmiv.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.kadmiv.game.MainClass;


public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        float div =  1.2f;
        config.width = (int) (MainClass.Companion.getWIDTH() / div);
        config.height = (int) (MainClass.Companion.getHEIGHT() / div);
        config.title = MainClass.Companion.getTITLE();
        new LwjglApplication(new MainClass(), config);
    }
}
