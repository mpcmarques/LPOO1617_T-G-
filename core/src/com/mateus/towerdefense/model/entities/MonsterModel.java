package com.mateus.towerdefense.model.entities;

/**
 * The monster model.
 */
public class MonsterModel extends EntityModel{
    /**
     * Monster life.
     */
    private int life;
    /**
     * If the monster is dead.
     */
    private boolean dead;
    /**
     * If the monster received damage.
     */
    private boolean receivedDamage;
    /**
     * The monster attack power.
     */
    private int attackPower;
    /**
     * If the monster arrived at the destination.
     */
    private  boolean arrived;

    /**
     * Creates the monster model
     * @param x X position.
     * @param y Y position.
     * @param rotation Rotation.
     * @param life Monster life.
     * @param attackPower Monster attack power.
     */
    public MonsterModel(float x, float y, float rotation, int life, int attackPower) {
        super(x, y, rotation);
        this.life = life;
        this.dead = false;
        this.receivedDamage = false;
        this.attackPower = attackPower;
        this.arrived = false;
    }

    /**
     * Set the monster arrived.
     * @param arrived If the moster arrived.
     */
    public void setArrived(boolean arrived) {
        this.arrived = arrived;
    }

    /**
     *
     * @return If the monster arrived.
     */
    public boolean isArrived() {
        return arrived;
    }

    /**
     *
     * @return The monster attack power.
     */
    public int getAttackPower() {
        return attackPower;
    }

    /**
     * Set monster attack power.
     * @param attackPower New monster attack power.
     */
    public void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }

    /**
     * @return If the monster received damage.
     */
    public boolean isReceivedDamage() {
        return receivedDamage;
    }

    /**
     * Damages the monster.
     * @param receivedDamage Damage to be applied to the monster.
     */
    public void setReceivedDamage(boolean receivedDamage) {
        this.receivedDamage = receivedDamage;
    }

    /**
     * Monster damage received callback.
     * @param damage Damage received.
     */
    public void receivedDamage(float damage){
        // discount life
        this.life -= damage;
        this.receivedDamage = true;

        // callback
        if(life <= 0){
            life = 0;

            dead = true;
        }
    }

    /**
     *
     * @return True if the monster is dead.
     */
    public boolean isDead() {
        return dead;
    }
}
