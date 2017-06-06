package com.mateus.towerdefense.utility;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.mateus.towerdefense.controller.entities.EntityController;
import com.mateus.towerdefense.controller.GameController;

/**
 * Transform tiled map layers into usable stuff, like bodies and objects.
 */
public class TiledMapParser {

    /**
     * Creates the collision bodies based on the tiled map layer.
     * @param gameController The game controller.
     * @param map Tiled map.
     */
    public static void createCollision(GameController gameController, TiledMap map) {

        // get layer
        for (MapObject mapObject : map.getLayers().get("collision").getObjects()) {
            float x = (((RectangleMapObject) mapObject).getRectangle().x) * Constants.PPM;
            float y = (((RectangleMapObject) mapObject).getRectangle().y) * Constants.PPM;
            float height = (((RectangleMapObject) mapObject).getRectangle().getHeight()) * Constants.PPM/2;
            float width = (((RectangleMapObject) mapObject).getRectangle().getWidth()) * Constants.PPM/2;

            // create collision
            gameController.createCollisionBody(x +width, y + height, width, height);
        }
    }

    /**
     * Creates the path based on the tiled map layer.
     * @param map Tiled map.
     * @return The path the monsters should walk.
     */
    public static Array<Vector2> createWaypoints(TiledMap map) {
        Array<Vector2> wayPoints = new Array<Vector2>();

        for (MapObject mapObject : map.getLayers().get("waypoints").getObjects()) {
            float x = (((RectangleMapObject) mapObject).getRectangle().x) * Constants.PPM;
            float y = (((RectangleMapObject) mapObject).getRectangle().y) * Constants.PPM;
            float height = (((RectangleMapObject) mapObject).getRectangle().getHeight()) * Constants.PPM;
            float width = (((RectangleMapObject) mapObject).getRectangle().getWidth()) * Constants.PPM;

            // create waypoint
            wayPoints.add(new Vector2(x + width/2, y + height/2));
        }

        return wayPoints;
    }
}
