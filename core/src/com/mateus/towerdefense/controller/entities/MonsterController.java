package com.mateus.towerdefense.controller.entities;

import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.ai.steer.behaviors.FollowPath;
import com.badlogic.gdx.ai.steer.behaviors.Seek;
import com.badlogic.gdx.ai.steer.utils.paths.LinePath;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mateus.towerdefense.utility.Box2dLocation;
import com.mateus.towerdefense.utility.MessageType;
import com.mateus.towerdefense.model.entities.MonsterModel;
import com.mateus.towerdefense.utility.Constants;
import com.mateus.towerdefense.utility.Gdx2dBody;

/**
 * Controls monster behavior and steerable AI.
 */
public class MonsterController extends SteerableEntityController {

    /**
     * The monster line path.
     */
    private LinePath<Vector2> path;

    /**
     * MonsterController constructor.
     *
     * @param world The world this controller belongs to.
     * @param model The model of the controller.
     * @param path  The path the monster will follow.
     */
    public MonsterController(World world, MonsterModel model, LinePath<Vector2> path) {
        super(world, model,
                Gdx2dBody.createRound(world, model.getX(), model.getY(), 0.4f, false,
                        Constants.BIT_MONSTER,
                        (short) (Constants.BIT_ARROW | Constants.BIT_MONSTER | Constants.BIT_TOWER_SENSOR)),
                0.5f,
                false);

        this.path = path;

        // set path follow behavior
        FollowPath<Vector2, LinePath.LinePathParam> pathSb = new FollowPath<Vector2, LinePath.LinePathParam>(this, path, 1);
        setSteeringBehavior(pathSb);

        this.setMaxLinearSpeed(4);
        this.setMaxLinearAcceleration(8);
        this.setMaxAngularSpeed(4);
        this.setMaxAngularAcceleration(8);

        // add listener
        MessageManager.getInstance().addListeners(this,
                MessageType.HIT,
                MessageType.STOP_MOVING,
                MessageType.CONTINUE_MOVING);
    }

    /**
     * If you need to do any additional calculation with the steering, use this method.
     *
     * @param steeringOutput Steering acceleration calculated.
     */
    @Override
    public void additionalSteeringCalculation(SteeringAcceleration<Vector2> steeringOutput) {

        if (getSteeringBehavior() instanceof Arrive) {

            if (path.getEndPoint().dst2(getPosition()) < 0.5) {

                MessageManager.getInstance().dispatchMessage(null,
                        MessageType.PLAYER_DAMAGE,
                        ((MonsterModel) getModel()).getAttackPower()
                );

                ((MonsterModel) getModel()).setArrived(true);
            }
        }
    }

    /**
     * Handles a telegram message.
     *
     * @param msg Message.
     * @return If the massage was handled.
     */
    @Override
    public boolean handleMessage(Telegram msg) {
        switch (msg.message) {
            case MessageType.SAY_MSG:
                System.out.println("Hi");
                return true;
            case MessageType.STOP_MOVING:
                break;
            case MessageType.CONTINUE_MOVING:
                break;
            case MessageType.HIT:
                ((MonsterModel) getModel()).receivedDamage((Integer) msg.extraInfo);
                break;
        }
        return false;
    }

    /**
     * Updates the controller.
     *
     * @param delta Delta time.
     */
    @Override
    public void update(float delta) {
        super.update(delta);

        MonsterModel monsterModel = (MonsterModel) getModel();

        if (monsterModel.isDead() || monsterModel.isArrived())
            kill();
    }

    /**
     * Kills the monster.
     */
    private void kill() {
        // kills monster

        // give money to player
        MessageManager.getInstance().dispatchMessage(null, MessageType.KILLED_MOB, 20);

        // remove monster
        this.setFlaggedToDelete(true);
    }
}
