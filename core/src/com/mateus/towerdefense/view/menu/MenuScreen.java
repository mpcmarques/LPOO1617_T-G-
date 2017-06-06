package com.mateus.towerdefense.view.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.mateus.towerdefense.TowerDefenseGame;
import com.mateus.towerdefense.view.base.AbstractScreen;

/**
 * The screen that manages the menu stage.
 */
public class MenuScreen extends AbstractScreen {
    /**
     * MenuScreen constructor.
     *
     * @param game Tower Defense Game.
     */
    public MenuScreen(TowerDefenseGame game) {
        super(game);

        setStage(new MenuStage(game));


        Gdx.input.setInputProcessor(getStage());
    }
}
