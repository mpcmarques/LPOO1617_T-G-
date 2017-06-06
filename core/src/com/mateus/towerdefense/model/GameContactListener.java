package com.mateus.towerdefense.model;

import com.badlogic.gdx.physics.box2d.*;
import com.mateus.towerdefense.model.entities.*;

/**
 * Game contact listener handle contact between entities.
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
        else if (entityModelA instanceof  TowerModel && entityModelB instanceof  PlayerModel){
                ((PlayerModel) fixtureB.getBody().getUserData()).setCanBuild(false);
        }
        else if (entityModelA instanceof CollisionModel && entityModelB instanceof PlayerModel){
                ((PlayerModel) fixtureB.getBody().getUserData()).setCanBuild(false);
        }
        else if (entityModelA instanceof PlayerModel && entityModelB instanceof CollisionModel) {
                ((PlayerModel) fixtureA.getBody().getUserData()).setCanBuild(false);
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

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
