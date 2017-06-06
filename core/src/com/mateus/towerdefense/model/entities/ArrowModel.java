package com.mateus.towerdefense.model.entities;

/**
 * Created by mateuspedroza on 02/06/17.
 */
public class ArrowModel extends EntityModel{

    private int damage;
    private boolean arrived;

    public ArrowModel(float x, float y, float rotation, int damage) {
        super(x, y, rotation);
        this.damage = damage;
        this.arrived = false;
    }

    public void setArrived(boolean arrived) {
        this.arrived = arrived;
    }

    public boolean arrived() {
        return arrived;
    }

    public int getDamage() {
        return damage;
    }
}
