package com.mateus.towerdefense.model;

import com.badlogic.gdx.physics.box2d.*;
import com.mateus.towerdefense.model.entities.*;

/**
 * Game contact listener handles contact between entities.
 */
public class GameContactListener implements ContactListener {

    /**
     * Called when a body begin contact with another body.
     *
     * @param contact Contact that happened.
     */
    @Override
    public void beginContact(Contact contact) {

        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        EntityModel entityModelA = (EntityModel) fixtureA.getBody().getUserData();
        EntityModel entityModelB = (EntityModel) fixtureB.getBody().getUserData();

        /* Player */
        if (entityModelA instanceof PlayerModel && entityModelB instanceof TowerModel) {
                ((PlayerModel) fixtureA.getBody().getUserData()).setCanBuild(false);
        }
        else if (entityModelA instanceof TowerModel && entityModelB instanceof  PlayerModel){
                ((PlayerModel) fixtureB.getBody().getUserData()).setCanBuild(false);
        }
        else if (entityModelA instanceof CollisionModel && entityModelB instanceof PlayerModel){
                ((PlayerModel) fixtureB.getBody().getUserData()).setCanBuild(false);
        }
        else if (entityModelA instanceof PlayerModel && entityModelB instanceof CollisionModel) {
                ((PlayerModel) fixtureA.getBody().getUserData()).setCanBuild(false);
        }

        else if (entityModelA instanceof TowerRangeModel && entityModelB instanceof MonsterModel){
            handleTowerTarget((TowerRangeModel)entityModelA, (MonsterModel)entityModelB);
        }
        else if (entityModelA instanceof  MonsterModel && entityModelB instanceof  TowerRangeModel){
            handleTowerTarget((TowerRangeModel)entityModelB, (MonsterModel)entityModelA);
        }

        /* ARROW */
        else if ((entityModelA instanceof ArrowModel && entityModelB instanceof MonsterModel)) {

            handleArrowHit((ArrowModel) entityModelA, (MonsterModel) entityModelB);

        } else if (entityModelA instanceof MonsterModel && entityModelB instanceof ArrowModel) {

            handleArrowHit((ArrowModel) entityModelB, (MonsterModel) entityModelA);

        }
    }

    /**
     * Called when a body ends contact with another body.
     *
     * @param contact Contact that happened.
     */
    @Override
    public void endContact(Contact contact) {

        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        EntityModel entityModelA = (EntityModel) fixtureA.getBody().getUserData();
        EntityModel entityModelB = (EntityModel) fixtureB.getBody().getUserData();

        if (entityModelA instanceof PlayerModel && entityModelB instanceof TowerModel) {
                ((PlayerModel) fixtureA.getBody().getUserData()).setCanBuild(true);
        }
        else if (entityModelA instanceof  TowerModel && entityModelB instanceof  PlayerModel){
                ((PlayerModel) fixtureB.getBody().getUserData()).setCanBuild(true);
        }
        else if (entityModelA instanceof CollisionModel && entityModelB instanceof PlayerModel){
                ((PlayerModel) fixtureB.getBody().getUserData()).setCanBuild(true);
        }
        else if (entityModelA instanceof PlayerModel && entityModelB instanceof CollisionModel) {
                ((PlayerModel) fixtureA.getBody().getUserData()).setCanBuild(true);
        }
    }

    /**
     * Handles an arrow monster collision.
     *
     * @param arrow   Arrow that hit the monster.
     * @param monster Monster that was hit by the arrow.
     */
    private void handleArrowHit(ArrowModel arrow, MonsterModel monster) {
        arrow.setArrived(true);
        monster.receivedDamage(arrow.getDamage());
    }

    /**
     * Handles when a monster enters in the tower range radius.
     * @param monsterModel Monster that entered in the tower range radius.
     * @param towerRangeModel Tower Range model that belongs to the body.
     */
    private void handleTowerTarget(TowerRangeModel towerRangeModel, MonsterModel monsterModel){
        towerRangeModel.getTowerModel().setTarget(monsterModel);
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
