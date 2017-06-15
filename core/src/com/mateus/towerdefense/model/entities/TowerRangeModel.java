package com.mateus.towerdefense.model.entities;


public class TowerRangeModel extends EntityModel {

    private TowerModel towerModel;

    /**
     * Constructs a model with a position and a rotation.
     *
     * @param tower    The tower that the range belongs.
     */
    public TowerRangeModel(TowerModel tower) {
        super(tower.getX(), tower.getY(), 0);
        this.towerModel = tower;
    }

    public TowerModel getTowerModel() {
        return towerModel;
    }

    public void setTowerModel(TowerModel towerModel) {
        this.towerModel = towerModel;
    }
}
