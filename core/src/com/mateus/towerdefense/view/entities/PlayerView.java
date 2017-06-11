package com.mateus.towerdefense.view.entities;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mateus.towerdefense.TowerDefenseGame;
import com.mateus.towerdefense.controller.entities.PlayerController;
import com.mateus.towerdefense.model.entities.EntityModel;
import com.mateus.towerdefense.model.entities.PlayerModel;
import com.mateus.towerdefense.utility.Constants;

/**
 * The view of the player.
 */
public class PlayerView extends EntityView{

    private Sprite rangeSprite;
    private PlayerModel model;

    /**
     * Building sound.
     */
    private Sound buildSound;

    /**
     * Creates a view belonging to a game.
     *
     * @param game The game this view belongs to. Needed to access the
     *             asset manager to get textures.
     * @param model The player model.
     */
    public PlayerView(TowerDefenseGame game, PlayerModel model) {
        super(game);
        this.model = model;

        // range sprite
        this.rangeSprite = new Sprite((Texture) game.getAssetManager().get("tower/circle.png"));
        this.rangeSprite.setAlpha(0.5f);

        this.buildSound = game.getAssetManager().get("audio/build.mp3");
    }

    @Override
    public void update(EntityModel model) {
        super.update(model);

        // update visibility
        this.setVisible(((PlayerModel)model).isInBuildMode());

        if (isVisible()) {
            if (getSprite() != null) {
                if (!((PlayerModel) model).canBuild()) {
                    getSprite().setColor(Color.RED);
                } else {
                    getSprite().setColor(Color.WHITE);
                }
            } else {
                Sprite towerSprite = new Sprite((Texture) getGame().getAssetManager().get("tower/tower.png"));
                towerSprite.setScale(Constants.PPM * 1.5f);
                setSprite(towerSprite);
            }
        }
        // play build sound
        if (((PlayerModel)model).isDidBuild()){
            buildSound.play();
            ((PlayerModel)model).setDidBuild(false);
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);

        if (model.getTowerToBuild() != null) {


            this.rangeSprite.setSize(model.getTowerToBuild().getRange() * 2,
                    model.getTowerToBuild().getRange() * 2
            );
            this.rangeSprite.setCenter(model.getX(), model.getY());
            this.rangeSprite.draw(batch);
        }
    }

    @Override
    public Sprite createSprite(TowerDefenseGame game) {
        return null;
    }
}
