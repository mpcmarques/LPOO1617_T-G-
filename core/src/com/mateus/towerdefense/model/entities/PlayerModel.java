package com.mateus.towerdefense.model.entities;

/**
 * The player model.
 */
public class PlayerModel extends EntityModel {
    /**
     * If the player can build.
     */
    private boolean canBuild;
    /**
     * Player gold.
     */
    private int gold;
    /**
     * Player tower that will build.
     */
    private TowerModel towerToBuild;
    /**
     * Player life.
     */
    private int life;

    /** Tracks if the player build,  used by the view.
     *
     */
    private boolean didBuild;

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
        this.didBuild = false;
    }

    /**
     * Set if the player can build.
     * @param canBuild Player can build.
     */
    public void setCanBuild(boolean canBuild) {
        if (isInBuildMode())
            this.canBuild = canBuild;
    }

    /**
     * @return If the player build something.
     */
    public boolean isDidBuild() {
        return didBuild;
    }

    /**
     * Set if the player build something.
     * @param didBuild If the player build something.
     */
    public void setDidBuild(boolean didBuild) {
        this.didBuild = didBuild;
    }

    /**
     *
     * @return True if the player can build.
     */
    public boolean canBuild() {
        return this.canBuild;
    }

    /**
     * Removes gold from the player.
     * @param gold Gold to be removed.
     */
    public void removeGold(int gold) {
        this.gold -= gold;
        if (this.gold <= 0)
            this.gold = 0;
    }

    /**
     *
     * @return Player life.
     */
    public int getLife() {
        return life;
    }

    /**
     * Set a new player's life.
     * @param life New player's life.
     */
    public void setLife(int life) {
        this.life = life;
        if (this.life < 0) this.life = 0;
    }

    /**
     * Add gold to the player.
     * @param gold Amount of gold to be added.
     */
    public void addGold(int gold) {
        this.gold += gold;
    }

    /**
     * @return The player's gold.
     */
    public int getGold() {
        return gold;
    }

    /**
     *
     * @return If the player is in build mode.
     */
    public boolean isInBuildMode() {
        return this.towerToBuild != null;
    }

    /**
     *
     * @return The tower that the player will build.
     */
    public TowerModel getTowerToBuild() {
        return towerToBuild;
    }

    /**
     * Set a tower to the player build.
     * @param towerToBuild The tower that the player will build.
     */
    public void setTowerToBuild(TowerModel towerToBuild) {
        this.towerToBuild = towerToBuild;
    }
}
