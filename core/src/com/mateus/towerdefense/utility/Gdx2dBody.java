package com.mateus.towerdefense.utility;

import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by mateuspedroza on 04/05/17.
 */
public class Gdx2dBody {

    public static Body createRound(World world, float x, float y, float radius, boolean isStatic, short cBits, short mBits){
        Body body;

        // Create the ball body definition
        BodyDef bodyDef = new BodyDef();

        if (isStatic){
            bodyDef.type = BodyDef.BodyType.StaticBody;
        } else {
            bodyDef.type = BodyDef.BodyType.DynamicBody;
        }

        // Create the ball body
        body = world.createBody(bodyDef);
        body.setTransform(x, y, 0);

        // Create rectangular shape
        CircleShape circle = new CircleShape();
        circle.setRadius(radius);

        // Create ground fixture
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = .5f;      // how heavy is the ground
        fixtureDef.friction =  .5f;    // how slippery is the ground
        fixtureDef.restitution =  .5f; // how bouncy is the ground
        fixtureDef.filter.categoryBits =   cBits;     // It is
        fixtureDef.filter.maskBits =    mBits;       // Collides with

        // Attach fixture to body
        body.createFixture(fixtureDef);

        // Dispose of shape
        circle.dispose();

        return body;
    }

    static public Body createBox(World world, float x, float y, float width, float height, boolean isStatic, short cBits, short mBits){
        Body body;

        // Create the ball body definition
        BodyDef bodyDef = new BodyDef();

        if (isStatic)
            bodyDef.type = BodyDef.BodyType.StaticBody;
        else
            bodyDef.type = BodyDef.BodyType.DynamicBody;

        // Create the body
        body = world.createBody(bodyDef);
        body.setTransform(x, y,  0);

        // Create rectangular shape
        PolygonShape rectangle = new PolygonShape();
        rectangle.setAsBox(width, height);

        // Create ground fixture
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = rectangle;
        fixtureDef.density = .5f;      // how heavy is the ground
        fixtureDef.friction =  .5f;    // how slippery is the ground
        fixtureDef.restitution =  .5f; // how bouncy is the ground
        fixtureDef.filter.categoryBits =   cBits;     // It is
        fixtureDef.filter.maskBits =    mBits;       // Collides with

        // Attach fixture to body
        body.createFixture(fixtureDef);

        // Dispose of circle shape
        rectangle.dispose();

        return body;
    }

    public static Body createSensor(World world, float x, float y, float width, float height, boolean isStatic, short cBits, short mBits){
        Body body;

        // Create the ball body definition
        BodyDef bodyDef = new BodyDef();

        if (isStatic)
            bodyDef.type = BodyDef.BodyType.StaticBody;
        else
            bodyDef.type = BodyDef.BodyType.DynamicBody;

        // Create the body
        body = world.createBody(bodyDef);
        body.setTransform(x, y,  0);

        // Create rectangular shape
        PolygonShape rectangle = new PolygonShape();
        rectangle.setAsBox(width, height);

        // Create ground fixture
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = rectangle;
        fixtureDef.isSensor = true;
        fixtureDef.filter.categoryBits =   cBits;     // It is
        fixtureDef.filter.maskBits =    mBits;       // Collides with

        // Attach fixture to body
        body.createFixture(fixtureDef);

        // Dispose of circle shape
        rectangle.dispose();

        return body;
    }
}
