package com.mateus.towerdefense.view.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.mateus.towerdefense.TowerDefenseGame;
import com.mateus.towerdefense.controller.GameController;
import com.mateus.towerdefense.model.GameModel;
import com.mateus.towerdefense.model.entities.PlayerModel;
import com.mateus.towerdefense.model.entities.WaveModel;
import com.mateus.towerdefense.utility.TiledMapConversor;
import com.mateus.towerdefense.view.base.AbstractScreen;

/**
 * GameScreen is the screen that runs the game.
 */
public class GameScreen extends AbstractScreen {

    /**
     * HUD Stage that goes above the main stage.
     */
    private HUDView hudView;

    /**
     * GameScreen Constructor.
     *
     * @param game the game this view belongs to. Needed to access the
     *             asset manager to get textures.
     */
    public GameScreen(TowerDefenseGame game) {
        super(game);

        TiledMap tiledMap = new TmxMapLoader().load("maps/top-down.tmx");

        GameController gameController = createNewGame(tiledMap);

        GameView gameView = new GameView(game,
                gameController.getModel(),
                gameController,
                tiledMap
        );
        setStage(gameView);

        this.hudView = new HUDView(game, gameController.getModel());

        //  Sets the stage as its input processor
        InputMultiplexer multiplex = new InputMultiplexer(gameView, hudView);
        Gdx.input.setInputProcessor(multiplex);

    }

    /**
     * Creates a new game to be used by the screen.
     * @return The game model the screen will use.
     */
    private GameController createNewGame(TiledMap map){
        PlayerModel playerModel = new PlayerModel(0, 0, 0, 100, 20);

        GameModel gameModel = new GameModel(playerModel, TiledMapConversor.createWaypoints(map));
        gameModel.addWaveModel(new WaveModel(2, 15));
        gameModel.addWaveModel(new WaveModel(4, 30));
        gameModel.addWaveModel(new WaveModel(8, 45));

        GameController  gameController = new GameController(gameModel);
        TiledMapConversor.createCollision(gameController, map);

        return  gameController;
    }

    /**
     * Renders the screen.
     *
     * @param delta DeltaTime.
     */
    @Override
    public void render(float delta) {
        super.render(delta);

        //  draw HUD
        hudView.act(delta);

        // update HUD
        hudView.draw();
    }

    /**
     * Resize the screen.
     *
     * @param width  New screen width.
     * @param height New screen height.
     */
    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        hudView.getViewport().update(width, height, true);
    }

    /**
     * Disposed unused instances.
     */
    @Override
    public void dispose() {
        super.dispose();

        // dispose stage
        hudView.dispose();
    }
}

