package com.mateus.towerdefense.view.menu;

import com.badlogic.gdx.Gdx;
import com.mateus.towerdefense.TowerDefenseGame;
import com.mateus.towerdefense.model.GameModel;
import com.mateus.towerdefense.view.base.AbstractScreen;

/**
 * The screen that manages the Game Finished Stage.
 */
public class GameFinishedScreen extends AbstractScreen {
    /**
     * GameFinishedScreen constructor.
     *
     * @param game          Tower Defense Game.
     * @param gameEndStatus The status that the game ended.
     */
    public GameFinishedScreen(TowerDefenseGame game, GameModel gameEndStatus) {
        super(game);

        setStage(new GameFinishedStage(game, gameEndStatus));

        Gdx.input.setInputProcessor(getStage());
    }
}
