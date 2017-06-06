package com.mateus.towerdefense.view.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mateus.towerdefense.TowerDefenseGame;
import com.mateus.towerdefense.model.entities.EntityModel;

/**
 * A abstract view capable of holding a sprite with a certain
 * position and rotation.
 *
 * This view is able to update its data based on a entity model.
 */
abstract class EntityView{
    private Sprite sprite;
    private boolean visible;

    /**
     * Creates a view belonging to a game.
     *
     * @param game the game this view belongs to. Needed to access the
     *             asset manager to get textures.
     */
    EntityView(TowerDefenseGame game){
        this.sprite = createSprite(game);
        this.visible = true;

        //this.sprite.setOrigin(getWidth() / 2, getHeight() / 2);
    }


    /**
     * Draws the sprite from this view using a sprite batch.
     *
     * @param batch The sprite batch to be used for drawing.
     */
    public void draw(SpriteBatch batch){
        if (sprite != null && visible)
            this.sprite.draw(batch);
    }

    /**
     * Abstract method that creates the view sprite. Concrete
     * implementation should extend this method to create their
     * own sprites.
     *
     * @param game the game this view belongs to. Needed to access the
     *             asset manager to get textures.
     * @return the sprite representing this view.
     */
    public abstract Sprite createSprite(TowerDefenseGame game);

    /**
     * Updates this view based on a certain model.
     *
     * @param model the model used to update this view
     */
    public void update(EntityModel model) {
        //sprite.setPosition(model.getX() / Constants.PPM * 2, model.getY() / Constants.PPM*2);
        if (sprite != null) {
            sprite.setCenter(model.getX(), model.getY());
            sprite.setOriginCenter();
            sprite.setRotation((float) (model.getRotation() * 180 / Math.PI));
        }
    }

    /**
     * Set the view sprite.
     * @param sprite Sprite to be set.
     */
    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    /**
     * Get the view sprite.
     * @return The view sprite.
     */
    public Sprite getSprite() {
        return sprite;
    }

    /**
     * @return If the view is visible.
     */
    public boolean isVisible(){
        return this.visible;
    }

    /**
     * Set the view visible.
     * @param visible View should be visible.
     */
    public void setVisible(boolean visible){
        this.visible = visible;
    }
}
