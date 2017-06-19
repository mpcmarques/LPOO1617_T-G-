package com.mateus.towerdefense.view.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mateus.towerdefense.TowerDefenseGame;
import com.mateus.towerdefense.controller.GameController;
import com.mateus.towerdefense.model.GameModel;
import com.mateus.towerdefense.model.GameStatus;
import com.mateus.towerdefense.model.entities.*;
import com.mateus.towerdefense.utility.Constants;
import com.mateus.towerdefense.view.base.AbstractStage;
import com.mateus.towerdefense.view.entities.*;
import com.mateus.towerdefense.view.menu.GameFinishedScreen;

/**
 * View of a game.
 */
public class GameView extends AbstractStage {
    /**
     * If it is true, will debug the view.
     */
    private final boolean DEBUG_MODE = false;

    /**
     * The model drawn by this screen
     */
    private GameModel model;

    /**
     * The controller of this screen
     */
    private GameController controller;

    /**
     * Tiled map renderer
     */
    private OrthogonalTiledMapRenderer renderer;

    /**
     * Tiled map
     */
    private TiledMap tiledMapView;

    /**
     * Player View
     */
    private PlayerView playerView;

    /**
     * Tower View
     */
    private TowerView towerView;

    /**
     * Monster View.
     */
    private MonsterView monsterView;

    /**
     * Arrow View.
     */
    private ArrowView arrowView;

    /**
     * Debug Renderer.
     */
    private Box2DDebugRenderer debugRenderer;

    /**
     * Background music.
     */
    private Music backgroundMusic;

    /**
     * GameView Constructor.
     *
     * @param game       Game the view will show.
     * @param model      Game model.
     * @param controller Game controller.
     * @param map        Game map.
     */
    public GameView(TowerDefenseGame game, GameModel model, GameController controller, TiledMap map) {
        super(game);

        this.model = model;
        this.controller = controller;
        this.tiledMapView = map;

        this.playerView = new PlayerView(game, getModel().getPlayerModel());
        this.monsterView = new MonsterView(
                game,
                (Texture) game.getAssetManager().get("monsters/zombie_walk.png"),
                4,
                4
        );
        this.towerView = new TowerView(game);
        this.arrowView = new ArrowView(game);

        calculateNewMapViewport();

        //  renderer
        renderer = new OrthogonalTiledMapRenderer(this.tiledMapView, Constants.PPM);
        debugRenderer = new Box2DDebugRenderer();

        // background music
        this.backgroundMusic = game.getAssetManager().get("audio/theme_music.mp3");
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.25f);
        backgroundMusic.play();

        // add listeners
        addListeners();
    }

    /**
     * Add listeners to the view.
     */
    private void addListeners() {
        addListener(new InputListener() {

            @Override
            public boolean mouseMoved(InputEvent event, float x, float y) {
                controller.handleMouseMoved(x, y);
                return super.mouseMoved(event, x, y);
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                controller.handleTouchDown(x, y);
                return false;
            }
        });
    }

    /**
     * Updates the view.
     *
     * @param delta Delta time.
     */
    @Override
    public void act(float delta) {
        super.act(delta);

        if (model.getStatus() == GameStatus.RUNNING) {

            controller.update(delta); // update controller

            //  renders map
            renderer.setView((OrthographicCamera) getCamera());
            renderer.render();

            getGame().getBatch().setProjectionMatrix(getCamera().combined);
            getGame().getBatch().begin();
            drawEntities(delta);
            getGame().getBatch().end();

            if (DEBUG_MODE) {
                debugRenderer.render(controller.getWorld(), ((OrthographicCamera) getCamera()).combined);
                debugAI(delta);
            }
        } else {
            backgroundMusic.stop();
            getGame().setScreen(new GameFinishedScreen(getGame(), getModel()));
        }
    }

    /**
     * Draw ai debugging if DEBUG_MODE is true.
     *
     * @param delta Delta time.
     */
    private void debugAI(float delta) {
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(getCamera().combined);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        for (TowerModel towerModel : model.getTowerModels()) {
            shapeRenderer.circle(towerModel.getX(), towerModel.getY(),
                    towerModel.getRange()
            );
        }

        shapeRenderer.end();
    }

    /**
     * Calculate a new camera viewport based on map size
     */
    private void calculateNewMapViewport() {
        //  save map height and width
        MapProperties prop = tiledMapView.getProperties();
        int mapWidth = prop.get("width", Integer.class);
        int mapHeight = prop.get("height", Integer.class);

        // viewport
        float ratio = ((float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth());
        setViewport(new StretchViewport(mapWidth, mapHeight));
    }

    /**
     * Draws the entities to the screen.
     * @param delta Delta time.
     */
    private void drawEntities(float delta) {
        // update than draw entities
        // towers
        for (TowerModel towerModel : model.getTowerModels()) {
            towerView.update(towerModel);
            towerView.draw(getGame().getBatch());
        }

        // monsters
        monsterView.animate(delta);
        for (MonsterModel monsterModel : getModel().getMonsterModels()) {
            monsterView.update(monsterModel);
            monsterView.draw(getGame().getBatch());
        }

        // arrows
        for (ArrowModel arrowModel : getModel().getArrowModels()) {
            arrowView.update(arrowModel);
            arrowView.draw(getGame().getBatch());
        }

        // player
        playerView.update(model.getPlayerModel());
        playerView.draw(getGame().getBatch());
    }

    /**
     * Dispose after use.
     */
    @Override
    public void dispose() {
        super.dispose();

        renderer.dispose();
        debugRenderer.dispose();
    }

    /* MARK: Getters */

    /**
     * @return The model of the game view.
     */
    public GameModel getModel() {
        return model;
    }

    /**
     * Returns the controller of the game view.
     *
     * @return The controller of the game view.
     */
    public GameController getController() {
        return controller;
    }
}
