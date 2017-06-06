package com.mateus.towerdefense.utility;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.ai.utils.Location;

public class Box2dLocation implements Location<Vector2> {

    private Vector2 position;
    private float orientation;

    public Box2dLocation () {
        this.position = new Vector2();
        this.orientation = 0;
    }

    @Override
    public Vector2 getPosition () {
        return position;
    }

    @Override
    public float getOrientation () {
        return orientation;
    }

    @Override
    public void setOrientation (float orientation) {
        this.orientation = orientation;
    }

    @Override
    public Location<Vector2> newLocation () {
        return new Box2dLocation();
    }

    @Override
    public float vectorToAngle (Vector2 vector) {
        return Box2dSteeringUtils.vectorToAngle(vector);
    }

    @Override
    public Vector2 angleToVector (Vector2 outVector, float angle) {
        return Box2dSteeringUtils.angleToVector(outVector, angle);
    }
}
