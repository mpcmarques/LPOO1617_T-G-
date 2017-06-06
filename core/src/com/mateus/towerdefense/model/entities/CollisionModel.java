package com.mateus.towerdefense.model.entities;

/**
 * Represents an object that a player can't build on top.
 */
public class CollisionModel extends EntityModel {

    /**
     * Constructs a model with a position and a rotation.
     *
     * @param x        The x-coordinate of this entity in meters.
     * @param y        The y-coordinate of this entity in meters.
     */
    public CollisionModel(float x, float y, float width, float height) {
        super(x, y, 0);
    }



}
