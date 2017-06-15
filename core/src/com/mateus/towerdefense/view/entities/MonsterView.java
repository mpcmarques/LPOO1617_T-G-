package com.mateus.towerdefense.view.entities;

import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mateus.towerdefense.TowerDefenseGame;
import com.mateus.towerdefense.model.entities.EntityModel;
import com.mateus.towerdefense.model.entities.MonsterModel;
import com.mateus.towerdefense.utility.MessageType;

/**
 * View of a Monster
 */
public class MonsterView extends AnimatedEntityView implements Telegraph {

    /**
     * Sound played when damaged.
     */
    private Sound damageSound;
    /**
     * Sound played when dead.
     */
    private Sound deadSound;

    /**
     * MonsterView Constructor
     *
     * @param game      TowerDefenseGame of the monster
     * @param texture   Texture of the monster
     * @param frameCols Number of cols of the monster animation sprite sheet.
     * @param frameRows Number of rows of the monster animation sprite sheet.
     */
    public MonsterView(TowerDefenseGame game, Texture texture, int frameCols, int frameRows) {
        super(game, texture, frameCols, frameRows);


        Sprite skeletonSprite = new Sprite(getAnimation().getKeyFrame(0));
        skeletonSprite.setSize(2f, 2f);
        setSprite(skeletonSprite);


        this.damageSound = game.getAssetManager().get("audio/mob_damage.ogg");
        this.deadSound = game.getAssetManager().get("audio/dead_sound.mp3");

        MessageManager.getInstance().addListener(this, MessageType.KILLED_MOB);
    }

    @Override
    public void update(EntityModel model) {
        super.update(model);

        MonsterModel monsterModel = (MonsterModel) model;

        if (monsterModel.isReceivedDamage()) {

            this.damageSound.play();
            monsterModel.setReceivedDamage(false);
        }
    }

    /**
     * Creates an animation
     *
     * @param game      the game this view belongs to. Needed to access the
     *                  asset manager to get textures.
     * @param texture   Texture of the animation
     * @param frameCols Number of cols of the animation sprite sheet.
     * @param frameRows Number of rows of the animation sprite sheet.
     * @return The animation that will be used.
     */
    @Override
    public Animation<TextureRegion> createAnimation(TowerDefenseGame game, Texture texture, int frameCols, int frameRows) {
        // split the texture into frame
        TextureRegion[][] textRegion = TextureRegion.split(texture, texture.getWidth() / frameCols, texture.getHeight() / frameRows);

        // put the frames into a uni-dimensional array
        TextureRegion[] frames = new TextureRegion[frameCols * frameRows];
        int index = 0;
        for (int i = 0; i < frameRows; i++) {
            for (int j = 0; j < frameCols; j++) {
                frames[index] = textRegion[i][j];
                index++;
            }
        }

        // create the animation
        return new Animation<TextureRegion>(0.05f, frames);
    }

    /**
     * Creates the view sprite.
     *
     * @param game the game this view belongs to. Needed to access the
     *             asset manager to get textures.
     * @return Sprite that will be used on the MonsterView.
     */
    @Override
    public Sprite createSprite(TowerDefenseGame game) {
        return new Sprite();
    }

    @Override
    public boolean handleMessage(Telegram msg) {
        if (msg.message == MessageType.KILLED_MOB){
            deadSound.play();
            return true;
        }
        return  false;
    }
}
