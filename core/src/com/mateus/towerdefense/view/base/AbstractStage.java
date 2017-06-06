package com.mateus.towerdefense.view.base;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mateus.towerdefense.TowerDefenseGame;

/**
 * Class represents an abstract stage
 */
public class AbstractStage extends Stage {

    /**
     * The game this screen belongs to
     */
    private TowerDefenseGame game;

    /**
     * Abstract stage constructor
     * @param game TowerDefense stage game.
     */
    public AbstractStage(TowerDefenseGame game){
        this.game = game;
    }

    /* Getters */

    /**
     * Return stage TowerDefenseGame
     * @return TowerDefenseGame of the current stage
     */
    public TowerDefenseGame getGame() {
        return game;
    }

    /* Setters */

    /**
     * Set the game of the stage
     * @param game TowerDefenseGame to set
     */
    public void setGame(TowerDefenseGame game) {
        this.game = game;
    }
}
