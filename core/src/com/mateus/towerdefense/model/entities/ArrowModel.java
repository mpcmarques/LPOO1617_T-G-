package com.mateus.towerdefense.model.entities;

/**
 * The arrow model.
 */
public class ArrowModel extends EntityModel{
    /**
     * Damage the arrow makes.
     */
    private int damage;
    /**
     * If the arrow has arrived.
     */
    private boolean arrived;

    /**
     * ArrowModel constructor.
     * @param x X position.
     * @param y Y position.
     * @param rotation Arrow rotation.
     * @param damage Arrow damage.
     */
    public ArrowModel(float x, float y, float rotation, int damage) {
        super(x, y, rotation);
        this.damage = damage;
        this.arrived = false;
    }

    /**
     *  Set arrived.
     * @param arrived If the arrow arrived.
     */
    public void setArrived(boolean arrived) {
        this.arrived = arrived;
    }

    /**
     * @return True if the arrow arrived.
     */
    public boolean arrived() {
        return arrived;
    }

    /**
     * @return The arrow damage.
     */
    public int getDamage() {
        return damage;
    }
}
