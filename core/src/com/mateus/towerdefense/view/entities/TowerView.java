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

    private Sprite baseSprite;

    /**
     * Tower View Constructor.
     *
     * @param game The game that will hold the view.
     */
    public TowerView(TowerDefenseGame game) {
        super(game);

        // range sprite
        this.rangeSprite = new Sprite((Texture) game.getAssetManager().get("tower/circle.png"));
        this.rangeSprite.setAlpha(0.2f);

        this.baseSprite = new Sprite((Texture) game.getAssetManager().get("tower/base.png"));
        this.baseSprite.setScale(Constants.PPM / 1.3f);
    }

    @Override
    public void update(EntityModel model) {
        super.update(model);

        if (model instanceof TowerModel) {
            TowerModel towerModel = (TowerModel) model;

            this.rangeSprite.setSize(towerModel.getRange()*2, towerModel.getRange()*2);
            this.rangeSprite.setCenter(towerModel.getX(), towerModel.getY());
            this.rangeSprite.draw(getGame().getBatch());

            this.baseSprite.setCenter(towerModel.getX(), towerModel.getY());
            this.baseSprite.draw(getGame().getBatch());
        }
    }

    @Override
    public Sprite createSprite(TowerDefenseGame game) {
        Texture texture = game.getAssetManager().get("tower/top.png");
        Sprite sprite = new Sprite(texture);
        sprite.setScale(Constants.PPM / 1.3f);
        return sprite;
    }
}
