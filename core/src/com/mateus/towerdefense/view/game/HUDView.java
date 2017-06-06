package com.mateus.towerdefense.view.game;

import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.mateus.towerdefense.TowerDefenseGame;
import com.mateus.towerdefense.model.GameModel;
import com.mateus.towerdefense.model.entities.TowerModel;
import com.mateus.towerdefense.utility.MessageType;
import com.mateus.towerdefense.view.base.AbstractScene2dStage;

/**
 * HUD of the game.
 */
public class HUDView extends AbstractScene2dStage {
    /**
     * If it is true, scene2d is lunched in debug mode.
     */
    private final boolean DEBUG_MODE = false;
    /**
     * A label showing how much is left for next wave.
     */
    private Label waveDelayLabel;
    /**
     * A label showing the player money.
     */
    private Label moneyLabel;
    /**
     * A button to build tower.
     */
    private TextButton buildButton;
    /**
     * A inner table for better arrangement of the UI.
     */
    private Table innerTable;
    /**
     * The game model.
     */
    private GameModel model;
    /**
     * A label to show how much waves are remaining.
     */
    private Label waveProgressLabel;
    /**
     * A sound played when a button is pressed.
     */
    private Sound buttonPressedSound;
    /**
     * A label showing the player remaining life.
     */
    private Label lifeNumberLabel;

    /**
     * HUDView Constructor
     *
     * @param game  the game this view belongs to.
     * @param model the game model.
     */
    HUDView(TowerDefenseGame game, GameModel model) {
        super(game);

        this.model = model;

        setupTable();

        setupWidgets();

        setupListeners();

        buttonPressedSound = game.getAssetManager().get("audio/menu_click.ogg");
    }

    /**
     * Add and setup table.
     */
    private void setupTable() {
        // table
        getTable().defaults().pad(8);
        getTable().defaults().expand().fillX();
        getTable().defaults().top();
        getTable().setFillParent(true);
        getTable().setDebug(DEBUG_MODE);
        addActor(getTable());

        // inner table
        this.innerTable = new Table();
        innerTable.defaults().pad(2);
        innerTable.defaults().expand().fill();
        innerTable.setBackground(getSkin().getDrawable("list"));
        innerTable.setColor(0.8f, 0.4f, 0, 1);
        innerTable.setDebug(DEBUG_MODE);
        getTable().add(innerTable);
        getTable().row();
    }

    /**
     * Add table widgets.
     */
    private void setupWidgets() {
        // wave delay counter
        waveDelayLabel = new Label("Time to wave: 0", getSkin());
        waveDelayLabel.setAlignment(Align.center);
        waveDelayLabel.setColor(Color.BROWN);

        // money counter
        moneyLabel = new Label("0", getSkin());
        moneyLabel.setAlignment(Align.center);
        moneyLabel.setColor(Color.BROWN);


        // current Wave
        this.waveProgressLabel = new Label("No waves remaining", getSkin());
        waveProgressLabel.setAlignment(Align.center);
        waveProgressLabel.setColor(Color.ROYAL);

        Label lifeBarLabel = new Label("LIFE", getSkin());
        lifeBarLabel.setAlignment(Align.center);
        lifeBarLabel.setColor(Color.RED);

        Label moneyTextLabel = new Label("MONEY", getSkin());
        moneyTextLabel.setAlignment(Align.center);
        moneyTextLabel.setColor(Color.YELLOW);

        this.lifeNumberLabel = new Label("", getSkin());
        lifeNumberLabel.setAlignment(Align.center);
        lifeNumberLabel.setColor(Color.RED);

        // add stuff to inner table
        innerTable.add(waveProgressLabel).height(15).width(150);
        innerTable.add(lifeBarLabel).height(15).width(250);
        innerTable.add(moneyTextLabel).height(15).width(150);
        innerTable.row();
        innerTable.add(waveDelayLabel).height(30).width(150);
        innerTable.add(lifeNumberLabel).height(30).width(250);
        innerTable.add(moneyLabel).height(30).width(150);

        // build button
        this.buildButton = new TextButton("BUILD TOWER", getSkin(), "default");
        buildButton.setColor(Color.BROWN);
        buildButton.getLabel().setColor(Color.WHITE);
        getTable().add(buildButton).width(250).height(40).bottom();
    }

    /**
     * Setup HUD button listeners.
     */
    private void setupListeners() {
        /* BUTTON LISTENER */
        buildButton.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                TowerModel towerModel = new TowerModel(0, 0, 4, 10, 1.2f, 50);

                if (model.getPlayerModel().getGold() >= towerModel.getPrice()) {
                    buttonPressedSound.play();
                    MessageManager.getInstance().dispatchMessage(MessageType.BUILD_MODE, towerModel);
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * Update the view.
     *
     * @param delta Delta time.
     */
    @Override
    public void act(float delta) {
        super.act(delta);


        setMoneyLabel(model.getPlayerModel().getGold());
        setWaveDelayLabel((int) model.getWaveDelay());
        setWavesRemainingLabel(model.getWaveModels().size);
        setLifeNumberLabel(model.getPlayerModel().getLife());
    }

    /**
     * Set wave delay label text.
     *
     * @param waveDelay Time to wave.
     */
    private void setWaveDelayLabel(int waveDelay) {
        waveDelayLabel.setText("Time to wave: " + waveDelay);
    }

    /**
     * Set life number label text.
     *
     * @param life Life of the player.
     */
    private void setLifeNumberLabel(int life) {
        this.lifeNumberLabel.setText("" + life);
    }

    /**
     * Set money label text.
     *
     * @param money Money to be shown.
     */
    private void setMoneyLabel(int money) {
        moneyLabel.setText("" + money);
    }

    /**
     * Set waves remaining label text.
     *
     * @param waves Number of waves to be shown.
     */
    private void setWavesRemainingLabel(int waves) {
        this.waveProgressLabel.setText(waves + " remaining");
    }

    /**
     * Returns the game model.
     *
     * @return The game model of the view.
     */
    public GameModel getModel() {
        return model;
    }

    /**
     * Creates a skin to be used in the view.
     *
     * @return Skin to be used in the view.
     */
    @Override
    public Skin createSkin() {
        return getGame().getAssetManager().get("skin/flat-earth-ui.json"); // Load skin
    }
}
