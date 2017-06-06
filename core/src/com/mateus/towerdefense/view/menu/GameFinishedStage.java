package com.mateus.towerdefense.view.menu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.mateus.towerdefense.TowerDefenseGame;
import com.mateus.towerdefense.model.GameModel;
import com.mateus.towerdefense.model.GameStatus;
import com.mateus.towerdefense.view.base.AbstractScene2dStage;
import com.mateus.towerdefense.view.game.GameScreen;

/**
 * The stage that is presented when the game ends.
 */
public class GameFinishedStage extends AbstractScene2dStage {
    /**
     * A button to restart the game.
     */
    private TextButton restartButton;
    /**
     * A button to exit the game.
     */
    private TextButton exitButton;
    /**
     * A sound to be used when a button is clicked.
     */
    private Sound clickSound;
    /**
     * A background music to play on the menu.
     */
    private Music backgroundMusic;

    /**
     * If it is true, scene2d will run in debug mode.
     */
    private final boolean DEBUG_MODE = false;

    /**
     * GameFinishedStage constructor.
     *
     * @param game      Tower Defense Game.
     * @param gameModel Game Model.
     */
    public GameFinishedStage(TowerDefenseGame game, GameModel gameModel) {
        super(game);

        setupTable();

        setupWidgets(gameModel.getStatus());

        setupListeners();

        setupAudio();
    }

    /**
     * Creates the skin that the stage will use.
     *
     * @return The skin the stage will use.
     */
    @Override
    public Skin createSkin() {
        return getGame().getAssetManager().get("skin/flat-earth-ui.json");
    }

    /**
     * Setup stage music
     */
    private void setupAudio() {
        this.clickSound = getGame().getAssetManager().get("audio/menu_click.ogg");

        this.backgroundMusic = getGame().getAssetManager().get("audio/menu_theme.mp3");
        this.backgroundMusic.setVolume(0.3f);
        this.backgroundMusic.setLooping(true);
        this.backgroundMusic.play();
    }

    /**
     * Setups the stage table.
     */
    private void setupTable() {
        // table
        getTable().defaults().pad(8);

        getTable().setFillParent(true);
        getTable().setDebug(DEBUG_MODE);
        addActor(getTable());
    }

    /**
     * Setups the stage widgets
     *
     * @param gameEndStatus The status that the game ended.
     */
    private void setupWidgets(GameStatus gameEndStatus) {

        Label label;
        if (gameEndStatus == GameStatus.VICTORY) {
            label = new Label("VICTORY", getSkin(), "title");
        } else {
            label = new Label("GAME OVER", getSkin(), "title");
        }
        label.setAlignment(Align.center);
        getTable().add(label).fill()
                .minWidth(Gdx.graphics.getWidth() / 3)
                .minHeight(Gdx.graphics.getHeight() / 4);

        getTable().row();

        restartButton = new TextButton("RESTART", getSkin());
        getTable().add(restartButton).minWidth(Gdx.graphics.getWidth() / 4);

        getTable().row();

        exitButton = new TextButton("EXIT", getSkin());
        getTable().add(exitButton).minWidth(Gdx.graphics.getWidth() / 4);
    }

    /**
     * Create listeners.
     */
    private void setupListeners() {
        exitButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                // play sound
                clickSound.play();
                // exit game
                Gdx.app.exit();

                return true;
            }
        });

        restartButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                // play sound
                clickSound.play();
                // start game
                getGame().setScreen(new GameScreen(getGame()));
                backgroundMusic.stop();

                return true;
            }
        });
    }

    /**
     * Dispose after use.
     */
    @Override
    public void dispose() {
        super.dispose();

        clickSound.dispose();
        backgroundMusic.dispose();
    }
}
