package com.mateus.towerdefense.view.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.mateus.towerdefense.TowerDefenseGame;

/**
 * Abstract class is the base of the tower defense game screen
 */
public abstract class AbstractScreen extends ScreenAdapter {
    /**
     * The tower defense game of the screen.
     */
    private TowerDefenseGame game;
    /**
     * The stage the screen will use.
     */
    private AbstractStage stage;

    /**
     * AbstractScreen constructor.
     * @param game Tower Defense Game that will be used.
     */
    public AbstractScreen(TowerDefenseGame game){
        this.game = game;
    }

    /**
     * Renders the screen.
     * @param delta Delta time.
     */
    @Override
    public void render(float delta) {
        super.render(delta);

        Gdx.gl.glClearColor(42 / 255f, 42 / 255f, 42 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);


        if (stage != null){
            stage.act();
            stage.draw();
        }
    }

    /**
     * Disposed after use.
     */
    @Override
    public void dispose() {
        super.dispose();
        stage.dispose();
    }

    /**
     * Resize the screen.
     * @param width New width.
     * @param height New height.
     */
    @Override
    public void resize(int width, int height) {
        getStage().getViewport().update(width, height, true);
    }

    /* GETTERS */

    /**
     * @return The tower defense game of the screen.
     */
    public TowerDefenseGame getGame() {
        return game;
    }

    /**
     * @return The stage of this screen.
     */
    public AbstractStage getStage() {
        return stage;
    }

    /* SETTERS */

    /**
     * Set the game of this screen.
     * @param game Game to be set.
     */
    public void setGame(TowerDefenseGame game) {
        this.game = game;
    }

    /**
     * Set the stage of this screen.
     * @param stage Stage to be set.
     */
    public void setStage(AbstractStage stage) {
        this.stage = stage;
    }

}
