package com.mateus.towerdefense.utility;

import com.badlogic.gdx.physics.box2d.*;

/**
 * Class creates box2d bodies.
 */
public class Gdx2dBody {
    /**
     * Creates a round body
     * @param world World that will have this body
     * @param x X position
     * @param y Y position
     * @param radius Radius of the body
     * @param isStatic If the body is static
     * @param cBits Collision bits
     * @param mBits Mask bits
     * @return Body created
     */
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
        fixtureDef.density = 1f;      // how heavy is the ground
        fixtureDef.friction =  1f;    // how slippery is the ground
        fixtureDef.restitution =  1f; // how bouncy is the ground
        fixtureDef.filter.categoryBits =   cBits;     // It is
        fixtureDef.filter.maskBits =    mBits;       // Collides with

        // Attach fixture to body
        body.createFixture(fixtureDef);

        // Dispose of shape
        circle.dispose();

        return body;
    }

    /**
     * Creates a round body
     * @param world World that will have this body
     * @param x X position
     * @param y Y position
     * @param width Width of the body
     * @param height Height of the body
     * @param isStatic If the body is static
     * @param cBits Collision bits
     * @param mBits Mask bits
     * @return Body created
     */
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
        fixtureDef.density = 1f;      // how heavy is the ground
        fixtureDef.friction =  1f;    // how slippery is the ground
        fixtureDef.restitution =  1f; // how bouncy is the ground
        fixtureDef.filter.categoryBits =   cBits;     // It is
        fixtureDef.filter.maskBits =    mBits;       // Collides with

        // Attach fixture to body
        body.createFixture(fixtureDef);

        // Dispose of circle shape
        rectangle.dispose();

        return body;
    }

    /**
     * Creates a sensor
     * @param world World that will have this body
     * @param x X position
     * @param y Y position
     * @param width Width of the body
     * @param height Height of the body
     * @param isStatic If the body is static
     * @param cBits Collision bits
     * @param mBits Mask bits
     * @return Body created
     */
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

    /**
     * Mark a body as a sensor.
     * @param body Body to mark as a sensor.
     */
    public static void markAsSensor(Body body){
        for (Fixture fix: body.getFixtureList())
            fix.setSensor(true);
    }
}
