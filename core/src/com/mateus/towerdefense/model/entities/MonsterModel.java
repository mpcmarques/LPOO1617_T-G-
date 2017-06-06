package com.mateus.towerdefense.model.entities;

/**
 * Created by mateuspedroza on 02/06/17.
 */
public class MonsterModel extends EntityModel{

    private int life;
    private boolean dead;
    private boolean receivedDamage;
    private int attackPower;
    private  boolean arrived;

    public MonsterModel(float x, float y, float rotation, int life, int attackPower) {
        super(x, y, rotation);
        this.life = life;
        this.dead = false;
        this.receivedDamage = false;
        this.attackPower = attackPower;
        this.arrived = false;
    }

    public void setArrived(boolean arrived) {
        this.arrived = arrived;
    }

    public boolean isArrived() {
        return arrived;
    }

    public int getAttackPower() {
        return attackPower;
    }

    public void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }

    public boolean isReceivedDamage() {
        return receivedDamage;
    }

    public void setReceivedDamage(boolean receivedDamage) {
        this.receivedDamage = receivedDamage;
    }

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


    public boolean isDead() {
        return dead;
    }
}
