package com.mateus.towerdefense.model.entities;


/**
 * The wave model, it represents a wave of monsters.
 */
public class WaveModel{
    private int numberOfMonsters;
    private float timeToSpawn;

    /**
     * Wave model constructor.
     * @param numberOfMonsters Number of wave monsters.
     * @param timeToSpawn Time to spawn the wave.
     */
    public WaveModel(int numberOfMonsters, float timeToSpawn) {
        this.numberOfMonsters = numberOfMonsters;
        this.timeToSpawn = timeToSpawn;
    }

    /**
     * Remove time from the time to spawn the wave.
     * @param time Time to be removed.
     */
    public void removeTimeToSpawn(float time){
        this.timeToSpawn -= time;

        if(timeToSpawn < 0) timeToSpawn = 0;
    }

    /**
     *
     * @return The number of monsters.
     */
    public int getNumberOfMonsters() {
        return numberOfMonsters;
    }

    /**
     *
     * @return The time to spawn.
     */
    public float getTimeToSpawn() {
        return timeToSpawn;
    }

    /**
     *
     * @return If the wave is ready to spawn.
     */
    public boolean isReadyToSpawn(){
        return this.timeToSpawn == 0;
    }
}
