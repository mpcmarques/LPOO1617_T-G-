package com.mateus.towerdefense.controller.entities;

import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mateus.towerdefense.model.entities.EntityModel;

/**
 * Abstract class that implements Box2d and Ai.
 */
public abstract class EntityController implements Telegraph {
    /**
     * The model the controller manages.
     */
    private EntityModel model;
    /**
     * The world that belongs to this controller
     */
    private World world;
    /**
     * The body that the controller manages.
     */
    private Body body;
    /**
     * Delete a controller.
     */
    private boolean flaggedToDelete;

    /**
     * EntityController constructor.
     *
     * @param world The world that will belong to this controller.
     * @param model The model the controller will manage.
     * @param body  The body the controller will manage.
     */
    EntityController(World world, EntityModel model, Body body) {
        this.model = model;
        this.world = world;
        this.body = body;
        this.flaggedToDelete = false;

        if (body != null)
            this.body.setUserData(model);
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
     * Updates the controller.
     *
     * @param delta Delta time.
     */
    public void update(float delta) {
    }

    /**
     * @return The world that belongs to the controller.
     */
    public World getWorld() {
        return world;
    }

    /**
     * @return The body that this controller manages.
     */
    public Body getBody() {
        return body;
    }

    /**
     * @return The model this controller manages.
     */
    public EntityModel getModel() {
        return model;
    }

    /**
     * @return If this controller is flagged to be deleted.
     */
    public boolean isFlaggedToDelete() {
        return this.flaggedToDelete;
    }

    /**
     * Changes the body that this controller will manage.
     *
     * @param body The body this controller will manage.
     */
    public void setBody(Body body) {
        this.body = body;
    }

    /**
     * Set if this controller should be deleted.
     *
     * @param flaggedToDelete Set if this controller should be deleted.
     */
    public void setFlaggedToDelete(boolean flaggedToDelete) {
        this.flaggedToDelete = flaggedToDelete;
    }
}
