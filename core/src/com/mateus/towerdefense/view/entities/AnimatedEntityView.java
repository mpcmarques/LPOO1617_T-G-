package com.mateus.towerdefense.view.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mateus.towerdefense.TowerDefenseGame;
import com.mateus.towerdefense.model.entities.EntityModel;

/**
 * Class represented a animated sprite
 */
public abstract class AnimatedEntityView extends EntityView {

    private Animation<TextureRegion> animation;
    private float stateTime;

    /**
     * Creates an animated view belonging to a game.
     *
     * @param game      The game this view belongs.
     * @param texture   The texture this view will animate.
     * @param frameCols The number of cols of the animation sprite sheet.
     * @param frameRows The number of rows of the animation sprite sheet.
     */
    AnimatedEntityView(TowerDefenseGame game, Texture texture, int frameCols, int frameRows) {
        super(game);
        this.stateTime = 0;

        this.animation = createAnimation(game, texture, frameCols, frameRows);
    }

    /**
     * Updates the view.
     *
     * @param model the model used to update this view.
     */
    @Override
    public void update(EntityModel model) {
        super.update(model);

        stateTime += Gdx.graphics.getDeltaTime();

        // change texture region
        if (animation != null)
            getSprite().setRegion(animation.getKeyFrame(stateTime, true));
    }

    /**
     * Create the sprite animation.
     *
     * @param game      Tower defense game.
     * @param frameCols Frame column number.
     * @param frameRows Frame rows number.
     * @return Sprite animation.
     */
    public abstract Animation<TextureRegion> createAnimation(TowerDefenseGame game, Texture texture, int frameCols, int frameRows);

    // Mark: Getters and Setters

    /**
     * @return The view animation.
     */
    public Animation<TextureRegion> getAnimation() {
        return animation;
    }

    /**
     * Set the view animation.
     *
     * @param animation Animation to be set.
     */
    public void setAnimation(Animation<TextureRegion> animation) {
        this.animation = animation;
    }

    /**
     * @return The state time.
     */
    public float getStateTime() {
        return stateTime;
    }

    /**
     * Set the state time.
     *
     * @param stateTime State time to be set.
     */
    public void setStateTime(float stateTime) {
        this.stateTime = stateTime;
    }
}
