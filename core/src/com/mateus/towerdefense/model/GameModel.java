package com.mateus.towerdefense.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mateus.towerdefense.model.entities.*;

/**
 * The game model.
 */
public class GameModel {
    /**
     * A player model.
     */
    private PlayerModel playerModel;
    /**
     * The waves.
     */
    private Array<WaveModel> waveModels;
    /***
     * The entities this model have.
     */
    private Array<EntityModel> entities;
    /**
     * The path the monsters will walk.
     */
    private Array<Vector2> wayPoints;
    /**
     * The game status.
     */
    private GameStatus status;

    /**
     * GameModel constructor.
     *
     * @param playerModel The player model.
     * @param wayPoints   The monster's path.
     */
    public GameModel(PlayerModel playerModel, Array<Vector2> wayPoints) {
        this.playerModel = playerModel;
        this.entities = new Array<EntityModel>();
        this.waveModels = new Array<WaveModel>();
        this.wayPoints = wayPoints;
        this.status = GameStatus.RUNNING;
    }

    /* GETTERS */

    /**
     * @return The monster's path.
     */
    public Array<Vector2> getWayPoints() {
        return wayPoints;
    }

    /**
     * @return Monster models.
     */
    public Array<MonsterModel> getMonsterModels() {
        Array<MonsterModel> monsterModels = new Array<MonsterModel>();
        for (EntityModel model : entities) {
            if (model instanceof MonsterModel)
                monsterModels.add((MonsterModel) model);
        }
        return monsterModels;
    }

    /**
     * @return The monster's spawn position.
     */
    public Vector2 getSpawnPosition() {
        return wayPoints.first();
    }

    /**
     * Adds a wave model.
     *
     * @param waveModel Wave model to be added.
     */
    public void addWaveModel(WaveModel waveModel) {
        this.waveModels.add(waveModel);
    }

    /**
     * Returns the time to the next spawn.
     *
     * @return Time to the next spawn.
     */
    public float getWaveDelay() {
        if (waveModels.size != 0)
            return waveModels.first().getTimeToSpawn();
        else
            return 0;
    }

    /**
     * @return The wave models.
     */
    public Array<WaveModel> getWaveModels() {
        return waveModels;
    }

    /**
     * Adds an entity model.
     *
     * @param entity Entity model to be added.
     */
    public void addEntityModel(EntityModel entity) {
        this.entities.add(entity);
    }

    /**
     * @return The entities.
     */
    public Array<EntityModel> getEntities() {
        return entities;
    }

    /**
     * @return The tower models.
     */
    public Array<TowerModel> getTowerModels() {
        Array<TowerModel> towerModels = new Array<TowerModel>();
        for (EntityModel model : entities) {
            if (model instanceof TowerModel)
                towerModels.add((TowerModel) model);
        }
        return towerModels;
    }

    /**
     * @return The arrow models.
     */
    public Array<ArrowModel> getArrowModels() {
        Array<ArrowModel> arrowModels = new Array<ArrowModel>();
        for (EntityModel model : entities) {
            if (model instanceof ArrowModel)
                arrowModels.add((ArrowModel) model);
        }
        return arrowModels;
    }

    /**
     * @return The player model.
     */
    public PlayerModel getPlayerModel() {
        return playerModel;
    }

    /**
     * @return The game status.
     */
    public GameStatus getStatus() {
        return status;
    }

    /**
     * Set the game status.
     *
     * @param status The game new status.
     */
    public void setStatus(GameStatus status) {
        this.status = status;
    }
}
