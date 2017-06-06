package com.mateus.towerdefense.model.entities;

/**
 * Created by mateuspedroza on 01/06/17.
 */
public class PlayerModel extends EntityModel {
    private boolean canBuild;
    private int gold;
    private TowerModel towerToBuild;
    private int life;

    /**
     * Constructs a model with a position and a rotation.
     *
     * @param x            The x-coordinate of this entity in meters.
     * @param y            The y-coordinate of this entity in meters.
     * @param rotation     The current rotation of this entity in radians.
     * @param startingGold The player starting gold.
     * @param life         Starting life.
     */
    public PlayerModel(float x, float y, float rotation, int startingGold, int life) {
        super(x, y, rotation);
        this.gold = startingGold;
        this.canBuild = true;
        this.life = life;
    }

    public void setCanBuild(boolean canBuild) {
        this.canBuild = canBuild;
    }

    public boolean canBuild() {
        return this.canBuild;
    }

    public void removeGold(int gold) {
        this.gold -= gold;
        if (this.gold <= 0)
            this.gold = 0;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
        if (this.life < 0) this.life = 0;
    }

    public void addGold(int gold) {
        this.gold += gold;
    }

    public int getGold() {
        return gold;
    }

    public boolean isInBuildMode() {
        return this.towerToBuild != null;
    }

    public TowerModel getTowerToBuild() {
        return towerToBuild;
    }

    public void setTowerToBuild(TowerModel towerToBuild) {
        this.towerToBuild = towerToBuild;
    }
}
