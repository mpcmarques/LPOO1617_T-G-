package com.mateus.towerdefense;

import static org.junit.Assert.*;
import com.badlogic.gdx.ai.steer.utils.paths.LinePath;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mateus.towerdefense.controller.GameController;
import com.mateus.towerdefense.controller.entities.ArrowController;
import com.mateus.towerdefense.controller.entities.MonsterController;
import com.mateus.towerdefense.model.GameModel;
import com.mateus.towerdefense.model.GameStatus;
import com.mateus.towerdefense.model.entities.*;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Game tests class.
 */
@RunWith(GdxTestRunner.class)
public class TowerDefenseTests {
    /**
     * Waypoints to be used in the monster path.
     * @return Monster path.
     */
    private Array<Vector2> WAYPOINTS() {
        Array<Vector2> waypoints = new Array<Vector2>();
        waypoints.add(new Vector2(0, 0));
        waypoints.add(new Vector2(0, 50));
        return waypoints;
    }

    /**
     * Test if the game is being created successfully.
     */
    @Test
    public void testGameStartUp() {
        GameModel gameModel = new GameModel(
                new PlayerModel(0, 0, 0, 100, 10),
                WAYPOINTS()
        );
        GameController gameController = new GameController(gameModel);

        gameController.update(1);

        assertEquals(100, ((PlayerModel) gameController.getPlayerController().getModel()).getGold());
    }

    /**
     * Test if towers are being created successfully.
     */
    @Test
    public void buildTowerTest() {
        int startingGold = 100;

        GameModel gameModel = new GameModel(
                new PlayerModel(0, 0, 0, startingGold, 10),
                WAYPOINTS()
        );
        GameController gameController = new GameController(gameModel);

        // test if the player have the gold
        assertEquals(100, ((PlayerModel) gameController.getPlayerController().getModel()).getGold());

        TowerModel towerModel = new TowerModel(10, 10, 1, 1, 1, 1);
        gameController.buildTower(towerModel);

        // test if tower is in place
        assertTrue(10 == gameModel.getTowerModels().get(0).getX());
        assertTrue(10 == gameModel.getTowerModels().get(0).getX());

        // test if money was removed
        assertTrue(startingGold > ((PlayerModel) gameController.getPlayerController().getModel()).getGold());
    }

    /**
     * Creates a wave and test if game ends when monsters arrive at the end of the path.
     */
    @Test
    public void testGameEndsWhenWaveArrive() {
        int timeToWave = 10;

        GameModel gameModel = new GameModel(
                new PlayerModel(0, 0, 0, 100, 10), WAYPOINTS()
        );
        // add one wave
        gameModel.addWaveModel(new WaveModel(1, timeToWave));

        GameController gameController = new GameController(gameModel);

        //update game controller until monster completes path and ends game
        while (GameStatus.ENDED != gameController.getModel().getStatus()) {
            gameController.update(1);
        }
    }

    /**
     * Test if waves are being summoned, and checks if whe the wave ends it is removed the list of next waves.
     */
    @Test
    public void testWave() {
        int startingGold = 100;
        int timeToWave = 10;
        GameModel gameModel = null;

        gameModel = new GameModel(
                new PlayerModel(0, 0, 0, startingGold, 10), WAYPOINTS()
        );
        // add one wave
        gameModel.addWaveModel(new WaveModel(1, timeToWave));


        GameController gameController = new GameController(gameModel);

        assertTrue(timeToWave == gameController.getModel().getWaveDelay());

        while (0 < gameController.getModel().getWaveModels().size) {
            gameController.update(1);
        }

        // summoned wave
        assertEquals((long) 0, (long) gameController.getModel().getWaveDelay());
        // cleared wave from list
        assertEquals(0, gameController.getModel().getWaveModels().size);

    }

    /**
     * Test if tower is attacking a monster when he passes nearby it.
     */
    @Test public void testTowerAttack(){
        GameModel gameModel = new GameModel(
                new PlayerModel(0, 0, 0, 100, 10), WAYPOINTS()
        );

        gameModel.addWaveModel(new WaveModel(1, 0));

        GameController gameController = new GameController(gameModel);

        TowerModel tower1 = new TowerModel(0,10,5,10,1,10);
        TowerModel tower2 = new TowerModel(0,2,5,10,1,10);
        gameController.buildTower(tower1);
        gameController.buildTower(tower2);


        while (gameModel.getMonsterModels().size == 0
                || !gameModel.getMonsterModels().first().isReceivedDamage())
            gameController.update(1);
    }

    /**
     * Test if the monster is being killed successfully.
     */
    @Test public void testMonsterIsKilled(){
        GameModel gameModel = new GameModel(
                new PlayerModel(0, 0, 0, 100, 10), WAYPOINTS()
        );

        GameController controller = new GameController(gameModel);

        MonsterModel monsterModel =  new MonsterModel(0,0,0,10,10);

        MonsterController monsterController = new MonsterController(controller.getWorld(), monsterModel, new LinePath<Vector2>(WAYPOINTS()));

        controller.addEntityController(monsterController);

        monsterModel.receivedDamage(10);

        assertTrue(monsterModel.isDead());

        controller.update(1);

        assertTrue(monsterController.isFlaggedToDelete());

        assertTrue(controller.getEntityControllers().size == 0);
    }

    /**
     * Test if AI missile is following the monster with arrive behavior, and killing it when arrived.
     */
    @Test public void testMissileHittingMonster(){
        GameModel gameModel = new GameModel(
                new PlayerModel(0, 0, 0, 100, 10), WAYPOINTS()
        );

        GameController controller = new GameController(gameModel);

        MonsterModel monsterModel =  new MonsterModel(0,0,0,10,10);

        MonsterController monsterController = new MonsterController(controller.getWorld(),
                monsterModel,
                new LinePath<Vector2>(WAYPOINTS())
        );

        ArrowModel arrowModel = new ArrowModel(0,0,0,10);
        ArrowController arrowController = new ArrowController(controller.getWorld(),
                arrowModel,
                monsterController
        );

        controller.addEntityController(monsterController);
        controller.addEntityController(arrowController);

        while (!arrowModel.arrived()){
            controller.update(1);
        }

        assertTrue(monsterModel.isDead());
        arrowController.update(1);
        assertTrue(arrowController.isFlaggedToDelete());
    }

    /**
     * Test if when a monster completes it path, it damages the player life
     */
    @Test public void testMonsterCompletingPathDamagePlayer(){
        GameModel gameModel = new GameModel(
                new PlayerModel(0, 0, 0, 100, 10), WAYPOINTS()
        );

        GameController controller = new GameController(gameModel);

        MonsterModel monsterModel =  new MonsterModel(0,0,0,10,10);

        MonsterController monsterController = new MonsterController(controller.getWorld(),
                monsterModel,
                new LinePath<Vector2>(WAYPOINTS())
        );

        controller.addEntityController(monsterController);

        while (!monsterModel.isArrived()){
            controller.update(1);
        }

        assertEquals(0,gameModel.getPlayerModel().getLife());
        assertEquals(GameStatus.ENDED, gameModel.getStatus());
    }
}
