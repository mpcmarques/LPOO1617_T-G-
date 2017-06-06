package com.mateus.towerdefense.model.entities;

/**
 * Created by mateuspedroza on 02/06/17.
 */
public class TowerModel extends EntityModel {

    private float range;
    private float attackDelay;
    private float currentAttackSeconds;
    private int attackDamage;
    private int price;

    /**
     * Constructs a tower model with a position and a rotation.
     *
     * @param x            The x-coordinate of this entity in meters.
     * @param y            The y-coordinate of this entity in meters.
     * @param range        Tower attack range
     * @param attackDamage Tower attack damage.
     * @param attackDelay  Delay between attacks.
     * @param price         Tower gold price.
     */
    public TowerModel(float x, float y, float range, int attackDamage, float attackDelay, int price) {
        super(x, y, 0);

        this.range = range;
        this.attackDelay = attackDelay;
        this.attackDamage = attackDamage;
        this.currentAttackSeconds = 0;
        this.price = price;
    }

    /**
     * Remove time that the tower is waiting to attack
     * @param delta Time to remove
     */
    public void passAttackDelayTime(float delta){
        currentAttackSeconds -= delta;

        if(currentAttackSeconds <= 0) this.currentAttackSeconds = 0;
    }

    public boolean towerCanAttack(){
        return  currentAttackSeconds == 0;
    }

    public void resetCurrentAttackSeconds() {
        this.currentAttackSeconds = attackDelay;
    }

    public float getRange() {
        return range;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public int getPrice() {
        return price;
    }
}
