package com.mateus.towerdefense.view.entities;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mateus.towerdefense.TowerDefenseGame;
import com.mateus.towerdefense.model.entities.EntityModel;
import com.mateus.towerdefense.model.entities.TowerModel;
import com.mateus.towerdefense.utility.Constants;

/**
 * Created by mateuspedroza on 02/06/17.
 */
public class TowerView extends EntityView {

    private Sound arrowShootSound;
    private Sprite rangeSprite;

    public TowerView(TowerDefenseGame game) {
        super(game);

        // arrow sounds
        this.arrowShootSound = game.getAssetManager().get("audio/arrow_shoot.ogg");

        // range sprite
        this.rangeSprite = new Sprite((Texture) game.getAssetManager().get("circle.png"));
        this.rangeSprite.setAlpha(0.2f);
    }

    @Override
    public void update(EntityModel model) {
        super.update(model);

        if (model instanceof  TowerModel) {
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
