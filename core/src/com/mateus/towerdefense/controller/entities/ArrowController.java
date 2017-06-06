package com.mateus.towerdefense.controller.entities;

import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.ai.steer.behaviors.BlendedSteering;
import com.badlogic.gdx.ai.steer.behaviors.Pursue;
import com.badlogic.gdx.ai.steer.behaviors.Seek;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mateus.towerdefense.model.entities.MonsterModel;
import com.mateus.towerdefense.utility.MessageType;
import com.mateus.towerdefense.model.entities.ArrowModel;
import com.mateus.towerdefense.utility.Constants;
import com.mateus.towerdefense.utility.Gdx2dBody;

/**
 * A arrow controller ( in the game the arrow is a seeking missile).
 */
public class ArrowController extends SteerableEntityController {
    /**
     * Arrow target.
     */
    private SteerableEntityController target;

    /**
     * Arrow Controller constructor.
     *
     * @param world  The world that the body will be created.
     * @param model  The arrow model that the controller will manage.
     * @param target The target of the arrow.
     */
    public ArrowController(World world, ArrowModel model, SteerableEntityController target) {
        super(world, model,
                Gdx2dBody.createRound(world, model.getX(), model.getY(), 0.2f, false, Constants.BIT_ARROW, Constants.BIT_MONSTER),
                0.2f,
                false);

        // add steering behaviour
        Arrive<Vector2> arriveSb = new Arrive<Vector2>(this, target);
        arriveSb.setArrivalTolerance(0);
        arriveSb.setDecelerationRadius(0);
        setSteeringBehavior(arriveSb);

        this.setMaxAngularAcceleration(30);
        this.setMaxLinearAcceleration(30);
        this.setMaxAngularSpeed(10);
        this.setMaxLinearSpeed(10);

        // set target
        this.target = target;

        // arrow fixture properties
        this.getBody().getFixtureList().first().setRestitution(1);
        this.getBody().getFixtureList().first().setFriction(0.1f);

        // add listeners
        MessageManager.getInstance().addListeners(this,
                MessageType.HIT);
    }

    /**
     * Updates the arrow controller.
     *
     * @param delta Delta time.
     */
    @Override
    public void update(float delta) {
        super.update(delta);

        // arrow logic
        ArrowModel arrowModel = (ArrowModel) getModel();

        if (arrowModel.arrived()) {

            // send hit message to monster
            MessageManager.getInstance().dispatchMessage(0,
                    null, target, MessageType.HIT,
                    ((ArrowModel) getModel()).getDamage()
            );

            // delete arrow
            setFlaggedToDelete(true);
        } else if (((MonsterModel) target.getModel()).isDead()) {
            // delete arrow
            setFlaggedToDelete(true);
        }
    }
}
