package com.mateus.towerdefense.view.menu;

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
import com.mateus.towerdefense.view.base.AbstractScene2dStage;
import com.mateus.towerdefense.view.game.GameScreen;

/**
 * MenuStage is the stage that is opened with game, showing a option to start it.
 */
public class MenuStage extends AbstractScene2dStage {

    /**
     * Start game button.
     */
    private TextButton startButton;
    /**
     * Exit game button.
     */
    private TextButton exitButton;
    /**
     * Click sound button.
     */
    private Sound clickSound;
    /**
     * Background music.
     */
    private Music backgroundMusic;

    private final boolean DEBUG_MODE = false;

    /**
     * MenuStage constructor.
     *
     * @param game the game this view belongs to. Needed to access the
     *             asset manager to get textures.
     */
    public MenuStage(TowerDefenseGame game) {
        super(game);

        setupTable();

        setupWidgets();

        setupListeners();

        setupAudio();
    }

    /**
     * Creates the skin that will be used by the stage.
     *
     * @return The skin that will be used by the stage.
     */
    @Override
    public Skin createSkin() {
        return getGame().getAssetManager().get("skin/flat-earth-ui.json");
    }

    /**
     * Setup stage music.
     */
    private void setupAudio() {
        this.clickSound = getGame().getAssetManager().get("audio/menu_click.ogg");

        this.backgroundMusic = getGame().getAssetManager().get("audio/menu_theme.mp3");
        this.backgroundMusic.setVolume(0.3f);
        this.backgroundMusic.setLooping(true);
        this.backgroundMusic.play();
    }

    /**
     * Setup stage table.
     */
    private void setupTable() {
        // table
        getTable().defaults().pad(8);

        getTable().setFillParent(true);
        getTable().setDebug(DEBUG_MODE);
        addActor(getTable());
    }

    /**
     * Setup stage widgets.
     */
    private void setupWidgets() {
        Label label = new Label("TOWER DEFENSE", getSkin(), "title");
        label.setAlignment(Align.center);
        getTable().add(label).fill()
                .minWidth(Gdx.graphics.getWidth() / 3)
                .minHeight(Gdx.graphics.getHeight() / 4);

        getTable().row();

        startButton = new TextButton("START", getSkin());
        getTable().add(startButton).minWidth(Gdx.graphics.getWidth() / 4);

        getTable().row();

        exitButton = new TextButton("EXIT", getSkin());
        getTable().add(exitButton).minWidth(Gdx.graphics.getWidth() / 4);
    }

    /**
     * Setup stage listeners.
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

        startButton.addListener(new ClickListener() {
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
     * Dispose unused instances.
     */
    @Override
    public void dispose() {
        super.dispose();

        clickSound.dispose();
        backgroundMusic.dispose();
    }
}
