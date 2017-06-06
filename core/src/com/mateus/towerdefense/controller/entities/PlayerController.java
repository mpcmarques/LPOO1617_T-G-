package com.mateus.towerdefense.controller.entities;

import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.mateus.towerdefense.controller.GameController;
import com.mateus.towerdefense.model.entities.TowerModel;
import com.mateus.towerdefense.utility.Constants;
import com.mateus.towerdefense.utility.Gdx2dBody;
import com.mateus.towerdefense.utility.MessageType;
import com.mateus.towerdefense.model.entities.PlayerModel;

/**
 * Controls the physics and AI of the player.
 */
public class PlayerController extends EntityController {

    /**
     * PlayerController Constructor.
     *
     * @param world Box2d world.
     * @param model PlayerModel that the controller will control.
     */
    public PlayerController(World world, PlayerModel model) {
        super(world, model,
                Gdx2dBody.createSensor(world, model.getX(), model.getY(), 0.6f, 0.6f, false,
                        Constants.BIT_PLAYER,
                        (short) (Constants.BIT_TOWER | Constants.BIT_WALL)));

        MessageManager.getInstance().addListeners(this,
                MessageType.BUILD_MODE, MessageType.PLAYER_DAMAGE, MessageType.KILLED_MOB
        );
    }

    /**
     * Add gold to the player.
     *
     * @param gold amount to be added.
     */
    public void addGold(int gold) {
        ((PlayerModel) this.getModel()).addGold(gold);
    }

    /**
     * Change model and body position.
     *
     * @param x X position.
     * @param y Y position.
     */
    public void setPosition(float x, float y) {
        this.getBody().setTransform(x, y, getModel().getRotation());
        this.getBody().setAwake(true);
        this.getModel().setPosition(x, y);
    }

    /**
     * Handles a telegram message.
     *
     * @param msg Message to be handled.
     * @return true if the message was handled.
     */
    @Override
    public boolean handleMessage(Telegram msg) {
        switch (msg.message) {
            case MessageType.BUILD_MODE:
                handleBuildModeMessage(msg);
                break;
            case MessageType.PLAYER_DAMAGE:
                handlePlayerDamageMessage(msg);
                break;
            case MessageType.KILLED_MOB:
                addGold((Integer) msg.extraInfo);
                return true;
        }
        return false;
    }

    /**
     * Handle a received damage message.
     *
     * @param msg that will be handled.
     */
    private void handlePlayerDamageMessage(Telegram msg) {
        PlayerModel playerModel = (PlayerModel) getModel();
        int damage = (Integer) msg.extraInfo;
        playerModel.setLife(playerModel.getLife() - damage);
    }

    /**
     * Handle a build mode message.
     *
     * @param msg that will be handled.
     */
    private void handleBuildModeMessage(Telegram msg) {
        PlayerModel playerModel = (PlayerModel) getModel();

        if (!playerModel.isInBuildMode()) {
            if (msg.extraInfo instanceof TowerModel) {
                playerModel.setTowerToBuild((TowerModel) msg.extraInfo);
            }
        }
    }
}
