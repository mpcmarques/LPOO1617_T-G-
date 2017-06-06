package com.mateus.towerdefense.view.base;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mateus.towerdefense.TowerDefenseGame;

/**
 * Class is a abstract class to be used in ui with scene2d
 */
public abstract class AbstractScene2dStage extends AbstractStage{
    private Table table;
    private Skin skin;

    /**
     * Constructor
     * @param game TowerDefenseGame of the scene.
     */
    public AbstractScene2dStage(TowerDefenseGame game) {
        super(game);
        this.table = new Table();
        this.skin = createSkin();
    }


    /**
     * Method create a skin for the stage.
     * @return Skin to be applied to the stage.
     */
    public abstract Skin createSkin();

    /**
     * Dispose after use
     */
    @Override
    public void dispose() {
        super.dispose();

        skin.dispose();
    }

    /**
     * Returns the scene table
     * @return Table of the scene
     */
    public Table getTable() {
        return table;
    }

    /**
     * Returns the skin of the stage
     * @return Skin of the stage
     */
    public Skin getSkin() {
        return skin;
    }

    /**
     * Set the stage table
     * @param table Table to be set
     */
    public void setTable(Table table) {
        this.table = table;
    }

    /**
     * Set stage skin
     * @param skin Skin to be set
     */
    public void setSkin(Skin skin) {
        this.skin = skin;
    }
}
