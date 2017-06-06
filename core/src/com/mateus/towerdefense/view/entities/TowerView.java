package com.mateus.towerdefense.view.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mateus.towerdefense.TowerDefenseGame;
import com.mateus.towerdefense.model.entities.EntityModel;
import com.mateus.towerdefense.model.entities.TowerModel;
import com.mateus.towerdefense.utility.Constants;

/**
 * The view of a tower.
 */
public class TowerView extends EntityView {

    private Sprite rangeSprite;

    /**
     * Tower View Constructor.
     *
     * @param game The game that will hold the view.
     */
    public TowerView(TowerDefenseGame game) {
        super(game);

        // range sprite
        this.rangeSprite = new Sprite((Texture) game.getAssetManager().get("circle.png"));
        this.rangeSprite.setAlpha(0.2f);
    }

    @Override
    public void update(EntityModel model) {
        super.update(model);

        if (model instanceof TowerModel) {
            TowerModel towerModel = (TowerModel) model;

            this.rangeSprite.setSize(towerModel.getRange(), towerModel.getRange());
            this.rangeSprite.setCenter(towerModel.getX(), towerModel.getY());
        }
    }

    @Override
    public Sprite createSprite(TowerDefenseGame game) {
        Texture texture = game.getAssetManager().get("tower.png");
        Sprite sprite = new Sprite(texture);
        sprite.setScale(Constants.PPM * 1.5f);
        return sprite;
    }
}
