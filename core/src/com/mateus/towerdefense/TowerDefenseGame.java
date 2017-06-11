package com.mateus.towerdefense;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mateus.towerdefense.view.menu.MenuScreen;

/**
 * Tower defense game.
 */
public class TowerDefenseGame extends Game {
    /**
     * Manages the game assets
     */
    private AssetManager assetManager;

    /**
     * The sprite batch used for drawing to the screen
     */
    private SpriteBatch batch;

    /**
     * Creates a new game and set the current screen
     */
    @Override
    public void create() {
        assetManager = new AssetManager();
        batch = new SpriteBatch();


        loadGameAssets();

        setScreen(new MenuScreen(this));
    }

    /**
     * Load all game assets.
     */
    private void loadGameAssets() {

        loadTextures();

        loadSounds();

        loadSkin();

        getAssetManager().finishLoading();
    }

    /**
     * Loads all game textures.
     */
    private void loadTextures() {
        getAssetManager().load("tower/tower.png", Texture.class);
        getAssetManager().load("tower/circle.png", Texture.class);
        getAssetManager().load("tower/base.png", Texture.class);
        getAssetManager().load("tower/top.png", Texture.class);
        getAssetManager().load("monsters/zombie_walk.png", Texture.class);
        getAssetManager().load("projectiles/missile.png", Texture.class);
    }

    /**
     * Loads all game sounds.
     */
    private void loadSounds() {
        getAssetManager().load("audio/theme_music.mp3", Music.class);
        getAssetManager().load("audio/mob_damage.ogg", Sound.class);
        getAssetManager().load("audio/arrow_shoot.ogg", Sound.class);
        getAssetManager().load("audio/boss.wav", Music.class);
        getAssetManager().load("audio/walk.wav", Sound.class);
        getAssetManager().load("audio/build.mp3", Sound.class);
        getAssetManager().load("audio/menu_click.ogg", Sound.class);
        getAssetManager().load("audio/menu_theme.mp3", Music.class);
        getAssetManager().load("audio/dead_sound.mp3", Sound.class);
    }

    /**
     * Loads game skin.
     */
    private void loadSkin() {
        // skin
        getAssetManager().load("skin/flat-earth-ui.atlas", TextureAtlas.class);
        getAssetManager().load("skin/flat-earth-ui.json",
                Skin.class,
                new SkinLoader.SkinParameter("skin/flat-earth-ui.atlas"));

        // finish loading
    }

    /**
     * Returns the asset manager.
     *
     * @return the asset manager
     */
    public AssetManager getAssetManager() {
        return assetManager;
    }

    /**
     * Returns the sprite batch
     *
     * @return the sprite batch
     */
    public SpriteBatch getBatch() {
        return batch;
    }

    /**
     * Disposes of all assets.
     */
    @Override
    public void dispose() {
        super.dispose();

        batch.dispose();
        getAssetManager().dispose();
    }
}
