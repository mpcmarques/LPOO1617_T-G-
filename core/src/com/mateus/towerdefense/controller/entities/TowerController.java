package com.mateus.towerdefense.controller.entities;

import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.behaviors.Face;
import com.badlogic.gdx.ai.steer.behaviors.ReachOrientation;
import com.badlogic.gdx.ai.steer.behaviors.Seek;
import com.badlogic.gdx.ai.steer.proximities.RadiusProximity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mateus.towerdefense.controller.GameController;
import com.mateus.towerdefense.model.entities.*;
import com.mateus.towerdefense.utility.Constants;
import com.mateus.towerdefense.utility.Gdx2dBody;
import com.mateus.towerdefense.utility.MessageType;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;


/**
 * Controls a tower model.
 */
public class TowerController extends SteerableEntityController {

    /**
     * Reference to the game controller.
     */
    private GameController gameController;

    /**
     * The cannon body.
     */
    private Body cannonBody;

    /**
     * The tower range body sensor.
     */
    private Body rangeSensor;

    /**
     * Tower Controller constructor.
     *
     * @param world          World that the controller belongs to.
     * @param model          Model that will be controlled.
     * @param monsters       Array of monsters.
     * @param gameController The game controller.
     */
    public TowerController(World world, TowerModel model, Array<SteerableEntityController> monsters, GameController gameController) {
        super(world,
                model,
                Gdx2dBody.createRound(world, model.getX(), model.getY(),  0.4f, false,
                        Constants.BIT_TOWER,
                        (short) (Constants.BIT_PLAYER | Constants.BIT_TOWER)),
                1, true
        );

        this.gameController = gameController;

        // cannon body
        this.cannonBody = Gdx2dBody.createBox(world, model.getX(), model.getY(),
                0.5f,0.5f, true,
                Constants.BIT_TOWER,
                (short) (Constants.BIT_PLAYER | Constants.BIT_MONSTER)
        );
        this.cannonBody.setUserData(
                new CollisionModel(model.getX(), model.getY(), 0.5f, 0.5f)
        );

        // tower range body
        this.rangeSensor = Gdx2dBody.createRound(world, model.getX(), model.getY(), model.getRange(),
                true, Constants.BIT_TOWER_SENSOR, Constants.BIT_MONSTER
        );
        this.rangeSensor.setUserData(
                new TowerRangeModel(model)
        );
        Gdx2dBody.markAsSensor(this.rangeSensor);

        this.setMaxLinearSpeed(10);
        this.setMaxLinearAcceleration(10);
        this.setMaxAngularSpeed(10);
        this.setMaxAngularAcceleration(10);
        this.setOrientation((float) (Math.PI/2));
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        TowerModel towerModel =  (TowerModel) getModel();

        // update tower attack delay
        towerModel.passAttackDelayTime(delta);

        if (towerModel.getTarget() != null){
            //targetMonster(towerModel.getTarget());
        }

    }


    @Override
    public boolean handleMessage(Telegram msg) {
        switch (msg.message){
            case MessageType.TARGET_MONSTER:
               targetMonster((MonsterController) msg.extraInfo);
            break;
        }
        return false;
    }

    private void targetMonster(MonsterController monster){
        // points missile at target
        Face<Vector2> faceSb = new Face<Vector2>(this);
        faceSb.setAlignTolerance(0.1f);
        faceSb.setDecelerationRadius(2);
        setSteeringBehavior(faceSb);
        faceSb.setTarget(monster);
    }

    /**
     * Reports a nearby neighbor.
     *
     * @param neighbor Reported neighbor.
     * @return If the neighbor was handled.
     */
    @Override
    public boolean reportNeighbor(Steerable<Vector2> neighbor) {
        if (neighbor instanceof MonsterController && ((TowerModel) getModel()).towerCanAttack()) {

            // points missile at target
            Face<Vector2> faceSb = new Face<Vector2>(this);
            faceSb.setAlignTolerance(0.1f);
            faceSb.setDecelerationRadius(2);
            setSteeringBehavior(faceSb);
            faceSb.setTarget(neighbor);

            // attack
            attack((MonsterController) neighbor, ((TowerModel) getModel()).getAttackDamage());
            return true;
        }
        return false;
    }

    /**
     * Attack a monster.
     *
     * @param target The monster.
     * @param damage The damage the attack will make.
     */
    private void attack(SteerableEntityController target, int damage) {

        // create arrow
        ArrowController arrowController = new ArrowController(getWorld(),
                new ArrowModel(getPosition().x, getPosition().y, getOrientation(), damage),
                target);


        gameController.addEntityController(arrowController);
        gameController.getModel().addEntityModel(arrowController.getModel());


        // reset wait
        ((TowerModel) getModel()).resetCurrentAttackSeconds();
    }
}
