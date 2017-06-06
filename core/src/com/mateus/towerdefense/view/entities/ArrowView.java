package com.mateus.towerdefense.view.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mateus.towerdefense.TowerDefenseGame;
import com.mateus.towerdefense.utility.Constants;

/**
 * Created by mateuspedroza on 02/06/17.
 */
public class ArrowView extends EntityView {
    /**
     * Creates a view belonging to a game.
     *
     * @param game the game this view belongs to. Needed to access the
     *             asset manager to get textures.
     */
    public ArrowView(TowerDefenseGame game) {
        super(game);

    }

    @Override
    public Sprite createSprite(TowerDefenseGame game) {
        Sprite sprite = new Sprite((Texture) game.getAssetManager().get("projectiles/missile.png"));
        sprite.setScale(Constants.PPM);
        return sprite;
    }
}
