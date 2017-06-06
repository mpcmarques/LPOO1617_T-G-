package com.mateus.towerdefense.view.entities;

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
 * Created by mateuspedroza on 01/06/17.
 */
public class PlayerView extends EntityView{

    private Sprite rangeSprite;

    /**
     * Creates a view belonging to a game.
     *
     * @param game the game this view belongs to. Needed to access the
     *             asset manager to get textures.
     */
    public PlayerView(TowerDefenseGame game) {
        super(game);

        // range sprite
        this.rangeSprite = new Sprite((Texture) game.getAssetManager().get("circle.png"));
        this.rangeSprite.setAlpha(0.5f);
    }

    @Override
    public void update(EntityModel model) {
        super.update(model);

        // update visibility
        this.setVisible(((PlayerModel)model).isInBuildMode());

        if (isVisible()) {
            // TODO Range sprite
            /*this.rangeSprite.setSize(1, 1);
            this.rangeSprite.setCenter( playerModel.getX(), playerModel.getY());
            this.rangeSprite.draw(batch);*/
            if (getSprite() != null) {
                if (!((PlayerModel) model).canBuild()) {
                    getSprite().setColor(Color.RED);
                } else {
                    getSprite().setColor(Color.WHITE);
                }
            }
        }
    }

    @Override
    public Sprite createSprite(TowerDefenseGame game) {
        return null;
    }
}
