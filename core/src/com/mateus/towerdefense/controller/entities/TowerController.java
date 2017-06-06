package com.mateus.towerdefense.controller.entities;

import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.proximities.RadiusProximity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mateus.towerdefense.controller.GameController;
import com.mateus.towerdefense.model.entities.ArrowModel;
import com.mateus.towerdefense.model.entities.TowerModel;
import com.mateus.towerdefense.utility.Constants;
import com.mateus.towerdefense.utility.Gdx2dBody;


/**
 * Controls a tower model.
 */
public class TowerController extends SteerableEntityController {
    /**
     * Reference to the game controller.
     */
    private GameController gameController;

    /**
     * Tower Controller constructor.
     * @param world World that the controller belongs to.
     * @param model Model that will be controlled.
     * @param monsters Array of monsters.
     * @param gameController The game controller.
     */
    public TowerController(World world, TowerModel model, Array<SteerableEntityController> monsters, GameController gameController) {
        super(world,
                model,
                Gdx2dBody.createBox(world, model.getX(), model.getY(), 0.5f, 0.5f, true,
                        Constants.BIT_TOWER,
                        (short)(Constants.BIT_PLAYER | Constants.BIT_TOWER)),
                1,false
        );

        this.gameController = gameController;
        // proximity
        this.setRadiusProximity(new RadiusProximity<Vector2>(this, monsters, model.getRange()));
    }

    public void update(float delta){
        super.update(delta);

        // update tower attack delay
        ((TowerModel)getModel()).passAttackDelayTime(delta);
    }

    /**
     * Reports a nearby neighbor.
     * @param neighbor Reported neighbor.
     * @return If the neighbor was handled.
     */
    @Override
    public boolean reportNeighbor(Steerable<Vector2> neighbor) {
        if(neighbor instanceof MonsterController && ((TowerModel)getModel()).towerCanAttack()){
            // attack
            attack((MonsterController)neighbor, ((TowerModel)getModel()).getAttackDamage());
            return true;
        }
        return false;
    }

    /**
     * Attack a monster.
     * @param target The monster.
     * @param damage The damage the attack will make.
     */
    private void attack(SteerableEntityController target, int damage){

        // create arrow
        ArrowController arrowController = new ArrowController(getWorld(),
                new ArrowModel(getPosition().x, getPosition().y, 0,damage),
                target);

        // add arrow to stage
        //getStage().addActor(arrow);
        gameController.addEntityController(arrowController);
        gameController.getModel().addEntityModel(arrowController.getModel());


        // shoot sound TODO
        //arrowShootSound.play();

        // reset wait
        ((TowerModel)getModel()).resetCurrentAttackSeconds();
    }
}
