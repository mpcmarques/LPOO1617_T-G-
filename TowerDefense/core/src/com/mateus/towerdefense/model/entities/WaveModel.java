package com.mateus.towerdefense.model.entities;

import com.badlogic.gdx.utils.Array;
import com.mateus.towerdefense.model.entities.EntityModel;
import com.mateus.towerdefense.model.entities.MonsterModel;

import javax.swing.text.html.parser.Entity;

/**
 * Created by mateuspedroza on 03/06/17.
 */
public class WaveModel{
    private int numberOfMonsters;
    private float timeToSpawn;

    public WaveModel(int numberOfMonsters, float timeToSpawn) {
        this.numberOfMonsters = numberOfMonsters;
        this.timeToSpawn = timeToSpawn;
    }

    public void removeTimeToSpawn(float time){
        this.timeToSpawn -= time;

        if(timeToSpawn < 0) timeToSpawn = 0;
    }

    public int getNumberOfMonsters() {
        return numberOfMonsters;
    }

    public float getTimeToSpawn() {
        return timeToSpawn;
    }

    public boolean isReadyToSpawn(){
        return this.timeToSpawn == 0;
    }
}
