package com.mateus.towerdefense.model.entities;

/**
 * The tower model.
 */
public class TowerModel extends EntityModel {

    private float range;
    private float attackDelay;
    private float currentAttackSeconds;
    private int attackDamage;
    private int price;
    private MonsterModel target;

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

    /**
     *
     * @return If the tower can attack.
     */
    public boolean towerCanAttack(){
        return  currentAttackSeconds == 0;
    }

    /**
     * Reset tower attack waiting time.
     */
    public void resetCurrentAttackSeconds() {
        this.currentAttackSeconds = attackDelay;
    }

    /**
     *
     * @return Tower range.
     */
    public float getRange() {
        return range;
    }

    /**
     * Set tower target.
     * @param target Monster target.
     */
    public void setTarget(MonsterModel target) {
        this.target = target;
    }

    /**
     *
     * @return The tower target.
     */
    public MonsterModel getTarget() {
        return target;
    }

    /**
     *
     * @return Tower attack damage.
     */
    public int getAttackDamage() {
        return attackDamage;
    }

    /**
     *
     * @return Tower price.
     */
    public int getPrice() {
        return price;
    }
}
