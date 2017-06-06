package com.mateus.towerdefense.controller;

import com.badlogic.gdx.ai.GdxAI;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.ai.steer.utils.paths.LinePath;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mateus.towerdefense.controller.entities.*;
import com.mateus.towerdefense.model.entities.*;
import com.mateus.towerdefense.utility.Constants;
import com.mateus.towerdefense.utility.Gdx2dBody;
import com.mateus.towerdefense.utility.MessageType;
import com.mateus.towerdefense.model.*;

/**
 * Controls the physics and ai aspect of the game.
 */
public class GameController {

    /**
     * The physics world controlled by this controller.
     */
    private World world;
    /**
     * The controller have information about the player.
     */
    private PlayerController playerController;
    /**
     * The controller have information about all controlled entities.
     */
    private Array<SteerableEntityController> entityControllers;
    /**
     * The model of the game.
     */
    private GameModel model;

    /**
     * GameController constructor.
     * @param model The model of the game.
     */
    public GameController(GameModel model) {
        this.model = model;

        world = new World(new Vector2(0, 0), true);

        world.setContactListener(new GameContactListener());

        entityControllers = new Array<SteerableEntityController>();

        playerController = new PlayerController(world, model.getPlayerModel());
    }

    /**
     * Calculates the next physics step of duration delta (in seconds).
     *
     * @param delta The size of this physics step in seconds.
     */
    public void update(float delta) {

        if (getModel().getStatus() == GameStatus.RUNNING) {

            GdxAI.getTimepiece().update(delta);          // Update AI

            // step messages
            MessageManager.getInstance().update();

            // step world
            world.step(delta, 6, 2);


            // wave logic
            waveLogic(delta);

            // update controllers
            for (SteerableEntityController entityController : entityControllers) {
                entityController.update(delta);

                if (entityController.isFlaggedToDelete()) {
                    entityControllers.removeValue(entityController, false);
                    model.getEntities().removeValue(entityController.getModel(), false);
                    world.destroyBody(entityController.getBody());
                }
            }

            // update models
            Array<Body> bodies = new Array<Body>();
            world.getBodies(bodies);

            for (Body body : bodies) {
                if (body.getUserData() != null) {
                    ((EntityModel) body.getUserData()).setPosition(body.getPosition().x, body.getPosition().y);
                    ((EntityModel) body.getUserData()).setRotation(body.getAngle());
                }
            }

            // end game
            if (((PlayerModel) playerController.getModel()).getLife() == 0) {
                model.setStatus(GameStatus.ENDED);
            } else if (getModel().getWaveDelay() == 0 && getModel().getWaveModels().size == 0 && getModel().getMonsterModels().size == 0){
                model.setStatus(GameStatus.VICTORY);
            }
        }
    }

    /**
     * Handles all wave logic
     * @param delta Delta time
     */
    private void waveLogic(float delta) {

        if (model.getWaveModels().size != 0) {
            WaveModel currentWave = model.getWaveModels().first();

            if (!currentWave.isReadyToSpawn())
                currentWave.removeTimeToSpawn(delta);
            else {
                spawnWave(currentWave);
                model.getWaveModels().removeIndex(0);
            }
        }
    }

    /**
     * Spawn a Wave Model
     * @param waveModel Wave Model to spawn
     */
    private void spawnWave(WaveModel waveModel){

        for (int i = 0; i < waveModel.getNumberOfMonsters(); i++){
            spawnMonster(model.getSpawnPosition().x,
                    model.getSpawnPosition().y+i
            );
        }
    }

    /**
     * Handles a moused moved input.
     * @param x New mouse x position.
     * @param y New mouse y position.
     */
    public void handleMouseMoved(float x, float y) {
        playerController.setPosition(x, y);
    }

    /**
     * Handles a touch down input on the game view.
     * @param x Touch position X.
     * @param y Touch position Y.
     */
    public void handleTouchDown(float x, float y) {
        PlayerModel playerModel = (PlayerModel) playerController.getModel();
        if (playerModel.isInBuildMode() && playerModel.canBuild()) {
            TowerModel towerModel = playerModel.getTowerToBuild();
            towerModel.setPosition(x,y);

            buildTower(towerModel);
        }
    }

    /**
     * Build a tower
     * @param towerModel TowerModel to be build
     */
    public void buildTower(TowerModel towerModel) {
        TowerController towerController = new TowerController(getWorld(),
                towerModel,
                entityControllers,
                this
                );


        this.model.getPlayerModel().removeGold(((TowerModel) towerController.getModel()).getPrice());
        this.model.getPlayerModel().setTowerToBuild(null);
        this.model.addEntityModel(towerController.getModel());

        this.entityControllers.add(towerController);
    }

    /**
     * Creates a collision body on x and y with the height and width. A collision body
     * is a body that is used to avoid a player to build on top of it.
     * @param x X position.
     * @param y Y position.
     * @param width Width of the body.
     * @param height Height of the body.
     */
    public void createCollisionBody(float x, float y, float width, float height){
        Body body = Gdx2dBody.createBox(getWorld(), x , y , width, height, true, Constants.BIT_WALL,  Constants.BIT_PLAYER);
        CollisionModel collisionModel = new CollisionModel(x, y, width, height);
        body.setUserData(collisionModel);
    }

    /**
     * Adds entity controller to the list
     * @param entityController Entity Controller to be added
     */
    public void addEntityController(SteerableEntityController entityController){
        this.entityControllers.add(entityController);
    }

    /**
     * Spawn a monster at x and y position of the map.
     * @param x X spawn position.
     * @param y Y spawn position.
     */
    public void spawnMonster(float x, float y) {
        LinePath<Vector2> linePath = new LinePath<Vector2>(model.getWayPoints(), true);

        // create monster controller
        MonsterModel monsterModel = new MonsterModel(
                x, y, 0, 40, 10
        );

        MonsterController monsterController = new MonsterController(
                getWorld(), monsterModel, linePath
        );

        model.addEntityModel(monsterController.getModel());
        entityControllers.add(monsterController);
    }

    /* MARK: getters */

    /**
     * Returns the controller box2d world.
     * @return Controller's box2d world.
     */
    public World getWorld() {
        return world;
    }

    /**
     * Returns the player controller this controller use.
     * @return The player controller this controller use.
     */
    public PlayerController getPlayerController() {
        return playerController;
    }

    /**
     * Return the model of the controller.
     * @return The model of the controller.
     */
    public GameModel getModel() {
        return model;
    }

    /**
     * Return steerable entities controllers that this control updates.
     * @return Steerable entities controllers.
     */
    public Array<SteerableEntityController> getEntityControllers() {
        return entityControllers;
    }
}
