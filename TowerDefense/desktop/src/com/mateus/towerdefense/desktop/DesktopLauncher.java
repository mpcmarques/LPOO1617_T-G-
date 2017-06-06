package com.mateus.towerdefense.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mateus.towerdefense.TowerDefenseGame;

public class DesktopLauncher {

    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Tower Defense";
        config.height = 720;
        config.width = 1080;
        //config.useHDPI = true;
        //config.fullscreen = true; //force fullscreen
        new LwjglApplication(new TowerDefenseGame(), config);
    }
}
