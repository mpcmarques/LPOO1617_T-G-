package com.mateus.towerdefense.utility;

import com.badlogic.gdx.math.Vector2;

/**
 * Class hold utilities for box2d
 */
public final class Box2dSteeringUtils {
    /**
     * Calculate a vector to an angle.
     * @param vector Vector to calculate.
     * @return Angle.
     */
    public static float vectorToAngle (Vector2 vector) {
        return (float)Math.atan2(vector.y, vector.x);
    }

    /**
     * Calculate an angle to a vector.
     * @param outVector Vector.
     * @param angle  Angle.
     * @return Vector.
     */
    public static Vector2 angleToVector (Vector2 outVector, float angle) {
        outVector.x = (float)Math.sin(angle);
        outVector.y = (float)Math.cos(angle);
        return outVector;
    }
}