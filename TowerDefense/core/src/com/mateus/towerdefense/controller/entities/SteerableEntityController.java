package com.mateus.towerdefense.controller.entities;

import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.steer.Proximity;
import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.ai.steer.proximities.RadiusProximity;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mateus.towerdefense.model.entities.EntityModel;
import com.mateus.towerdefense.utility.Box2dLocation;
import com.mateus.towerdefense.utility.Box2dSteeringUtils;

/**
 * Controls a entity that have a steering behavior.
 */
public class SteerableEntityController extends EntityController implements Steerable<Vector2>, Proximity.ProximityCallback<Vector2> {

    private float boundingRadius;
    private boolean tagged;
    private float maxLinearSpeed;
    private float maxLinearAcceleration;
    private float maxAngularSpeed;
    private float maxAngularAcceleration;
    private boolean independentFacing;
    private SteeringBehavior<Vector2> steeringBehavior;
    private RadiusProximity radiusProximity;
    private static final SteeringAcceleration<Vector2> steeringOutput = new SteeringAcceleration<Vector2>(new Vector2());


    SteerableEntityController(World world, EntityModel model, Body body, float boundingRadius, boolean independentFacing) {
        super(world, model, body);

        this.independentFacing = independentFacing;
        this.boundingRadius = boundingRadius;
        this.tagged = false;
    }

    /**
     * Updates the controller.
     * @param delta Delta time.
     */
    @Override
    public void update(float delta) {
        super.update(delta);

        if (steeringBehavior != null) {
            // Calculate steering acceleration
            steeringBehavior.calculateSteering(steeringOutput);

			/*
             * Here you might want to add a motor control layer filtering steering accelerations.
			 *
			 * For instance, a car in a driving game has physical constraints on its movement:
			 * - it cannot turn while stationary
			 * - the faster it moves, the slower it can turn (without going into a skid)
			 * - it can brake much more quickly than it can accelerate
			 * - it only moves in the direction it is facing (ignoring power slides)
			 */

            additionalSteeringCalculation(steeringOutput);

            // Apply steering acceleration to move this agent
            applySteering(delta);
        }


        if (radiusProximity != null) {
            radiusProximity.findNeighbors(this);
        }
    }

    /**
     * If you need to do any additional calculation with the steering, use this method.
     *
     * @param steeringOutput Steering acceleration calculated.
     */
    public void additionalSteeringCalculation(SteeringAcceleration<Vector2> steeringOutput) {
    }

    /**
     * Apply the steering output.
     * @param time Delta time.
     */
    private void applySteering(float time) {
        boolean anyAccelerations = false;

        // Update position and linear velocity.
        if (!steeringOutput.linear.isZero()) {
            // this method internally scales the force by deltaTime
            getBody().applyForceToCenter(steeringOutput.linear, true);
            anyAccelerations = true;
        }

        // Update orientation and angular velocity
        if (this.independentFacing) {
            if (steeringOutput.angular != 0) {
                // this method internally scales the torque by deltaTime
                getBody().applyTorque(steeringOutput.angular, true);
                anyAccelerations = true;
            }
        } else {
            // If we haven't got any velocity, then we can do nothing.
            Vector2 linVel = getLinearVelocity();
            if (!linVel.isZero(getZeroLinearSpeedThreshold())) {
                float newOrientation = vectorToAngle(linVel);
                getBody().setAngularVelocity((newOrientation - getAngularVelocity()) * time); // this is superfluous if independentFacing is always true
                getBody().setTransform(getBody().getPosition(), newOrientation);
            }
        }

        if (anyAccelerations) {
            // body.activate();

            // TODO:
            // Looks like truncating speeds here after applying forces doesn't work as expected.
            // We should likely cap speeds form inside an InternalTickCallback, see
            // http://www.bulletphysics.org/mediawiki-1.5.8/index.php/Simulation_Tick_Callbacks

            // Cap the linear speed
            Vector2 velocity = getBody().getLinearVelocity();
            float currentSpeedSquare = velocity.len2();
            float maxLinearSpeed = getMaxLinearSpeed();
            if (currentSpeedSquare > maxLinearSpeed * maxLinearSpeed) {
                getBody().setLinearVelocity(velocity.scl(maxLinearSpeed / (float) Math.sqrt(currentSpeedSquare)));
            }

            // Cap the angular speed
            float maxAngVelocity = getMaxAngularSpeed();
            if (getBody().getAngularVelocity() > maxAngVelocity) {
                getBody().setAngularVelocity(maxAngVelocity);
            }
        }
    }

    /**
     * Handles a telegram message.
     * @param msg Message.
     * @return If the massage was handled.
     */
    @Override
    public boolean handleMessage(Telegram msg) {
        return false;
    }

    /**
     * Reports a nearby neighbor.
     * @param neighbor Neighbor that is nearby.
     * @return  If the report was handled.
     */
    @Override
    public boolean reportNeighbor(Steerable<Vector2> neighbor) {
        return false;
    }

    @Override
    public Vector2 getLinearVelocity() {
        return getBody().getLinearVelocity();
    }

    @Override
    public float getAngularVelocity() {
        return getBody().getAngularVelocity();
    }

    @Override
    public float getBoundingRadius() {
        return boundingRadius;
    }

    @Override
    public boolean isTagged() {
        return tagged;
    }

    @Override
    public void setTagged(boolean tagged) {
        this.tagged = tagged;
    }

    @Override
    public float getZeroLinearSpeedThreshold() {
        return 0.001f;
    }

    @Override
    public void setZeroLinearSpeedThreshold(float value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public float getMaxLinearSpeed() {
        return maxLinearSpeed;
    }

    @Override
    public void setMaxLinearSpeed(float maxLinearSpeed) {
        this.maxLinearSpeed = maxLinearSpeed;
    }

    @Override
    public float getMaxLinearAcceleration() {
        return maxLinearAcceleration;
    }

    @Override
    public void setMaxLinearAcceleration(float maxLinearAcceleration) {
        this.maxLinearAcceleration = maxLinearAcceleration;
    }

    @Override
    public float getMaxAngularSpeed() {
        return maxAngularSpeed;
    }

    @Override
    public void setMaxAngularSpeed(float maxAngularSpeed) {
        this.maxAngularSpeed = maxAngularSpeed;
    }

    @Override
    public float getMaxAngularAcceleration() {
        return maxAngularAcceleration;
    }

    @Override
    public void setMaxAngularAcceleration(float maxAngularAcceleration) {
        this.maxAngularAcceleration = maxAngularAcceleration;
    }

    @Override
    public Vector2 getPosition() {
        return getBody().getPosition();
    }

    @Override
    public float getOrientation() {
        return getBody().getAngle();
    }

    @Override
    public void setOrientation(float orientation) {
        getBody().setTransform(getPosition(), orientation);
    }

    @Override
    public float vectorToAngle(Vector2 vector) {
        return Box2dSteeringUtils.vectorToAngle(vector);
    }

    @Override
    public Vector2 angleToVector(Vector2 outVector, float angle) {
        return Box2dSteeringUtils.angleToVector(outVector, angle);
    }

    @Override
    public Location<Vector2> newLocation() {
        return new Box2dLocation();
    }

    public void setSteeringBehavior(SteeringBehavior<Vector2> steeringBehavior) {
        this.steeringBehavior = steeringBehavior;
    }


    public void setRadiusProximity(RadiusProximity radiusProximity) {
        this.radiusProximity = radiusProximity;
    }

    public RadiusProximity getRadiusProximity() {
        return radiusProximity;
    }

    public SteeringBehavior<Vector2> getSteeringBehavior() {
        return steeringBehavior;
    }

    public static SteeringAcceleration<Vector2> getSteeringOutput() {
        return steeringOutput;
    }
}
