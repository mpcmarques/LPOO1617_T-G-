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

    /**
     * Steerable Entity Controller constructor.
     *
     * @param world             World that the entity belongs to.
     * @param model             Entity model.
     * @param body              The body this will be controlled.
     * @param boundingRadius    The entity bounding radius.
     * @param independentFacing If the entity is independent facing.
     */
    SteerableEntityController(World world, EntityModel model, Body body, float boundingRadius, boolean independentFacing) {
        super(world, model, body);

        this.independentFacing = independentFacing;
        this.boundingRadius = boundingRadius;
        this.tagged = false;
    }

    /**
     * Updates the controller.
     *
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
     *
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
     *
     * @param msg Message.
     * @return If the massage was handled.
     */
    @Override
    public boolean handleMessage(Telegram msg) {
        return false;
    }

    /**
     * Reports a nearby neighbor.
     *
     * @param neighbor Neighbor that is nearby.
     * @return If the report was handled.
     */
    @Override
    public boolean reportNeighbor(Steerable<Vector2> neighbor) {
        return false;
    }

    /**
     * @return The body linear velocity.
     */
    @Override
    public Vector2 getLinearVelocity() {
        return getBody().getLinearVelocity();
    }

    /**
     * @return The body angular velocity.
     */
    @Override
    public float getAngularVelocity() {
        return getBody().getAngularVelocity();
    }

    /**
     * @return The entity bounding radius.
     */
    @Override
    public float getBoundingRadius() {
        return boundingRadius;
    }

    /**
     * @return If the entity is tagged.
     */
    @Override
    public boolean isTagged() {
        return tagged;
    }

    /**
     * @param tagged Set the entity tagged.
     */
    @Override
    public void setTagged(boolean tagged) {
        this.tagged = tagged;
    }

    /**
     * @return The zero linear speed threshold.
     */
    @Override
    public float getZeroLinearSpeedThreshold() {
        return 0.001f;
    }

    /**
     * Set the zero linear speed threshold.
     *
     * @param value Value to be set.
     */
    @Override
    public void setZeroLinearSpeedThreshold(float value) {
        throw new UnsupportedOperationException();
    }

    /**
     * @return The max linear speed.
     */
    @Override
    public float getMaxLinearSpeed() {
        return maxLinearSpeed;
    }

    /**
     * @param maxLinearSpeed The max linear speed.
     */
    @Override
    public void setMaxLinearSpeed(float maxLinearSpeed) {
        this.maxLinearSpeed = maxLinearSpeed;
    }

    /**
     * @return The max linear accelaration.
     */
    @Override
    public float getMaxLinearAcceleration() {
        return maxLinearAcceleration;
    }

    /**
     * Set the max linear acceleration.
     *
     * @param maxLinearAcceleration The new max linear acceleration.
     */
    @Override
    public void setMaxLinearAcceleration(float maxLinearAcceleration) {
        this.maxLinearAcceleration = maxLinearAcceleration;
    }

    /**
     * @return The max angular speed.
     */
    @Override
    public float getMaxAngularSpeed() {
        return maxAngularSpeed;
    }

    /**
     * Set the max angular speed.
     *
     * @param maxAngularSpeed The new angular speed.
     */
    @Override
    public void setMaxAngularSpeed(float maxAngularSpeed) {
        this.maxAngularSpeed = maxAngularSpeed;
    }

    /**
     * @return The max angular acceleration.
     */
    @Override
    public float getMaxAngularAcceleration() {
        return maxAngularAcceleration;
    }

    /**
     * Set the max angular acceleration.
     *
     * @param maxAngularAcceleration The new max angular acceleration.
     */
    @Override
    public void setMaxAngularAcceleration(float maxAngularAcceleration) {
        this.maxAngularAcceleration = maxAngularAcceleration;
    }

    /**
     * @return The body position.
     */
    @Override
    public Vector2 getPosition() {
        return getBody().getPosition();
    }

    /**
     * @return The body orientation.
     */
    @Override
    public float getOrientation() {
        return getBody().getAngle();
    }

    /**
     * Set a new orientation.
     *
     * @param orientation New body orientation.
     */
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

    /**
     * @return A new box2d location.
     */
    @Override
    public Location<Vector2> newLocation() {
        return new Box2dLocation();
    }

    /**
     * Set a new steering behavior.
     *
     * @param steeringBehavior The new steering behavior.
     */
    public void setSteeringBehavior(SteeringBehavior<Vector2> steeringBehavior) {
        this.steeringBehavior = steeringBehavior;
    }

    /**
     * Set radius proximity.
     *
     * @param radiusProximity The new radius proximity.
     */
    public void setRadiusProximity(RadiusProximity radiusProximity) {
        this.radiusProximity = radiusProximity;
    }

    /**
     * @return The radius proximity.
     */
    public RadiusProximity getRadiusProximity() {
        return radiusProximity;
    }

    /**
     * @return The steering behavior.
     */
    public SteeringBehavior<Vector2> getSteeringBehavior() {
        return steeringBehavior;
    }

    /**
     * @return The steering output.
     */
    public static SteeringAcceleration<Vector2> getSteeringOutput() {
        return steeringOutput;
    }
}
