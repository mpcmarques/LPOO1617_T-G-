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
 * Created by mateuspedroza on 02/06/17.
 */
public class TiledMapConversor {

    public static Vector2 findCrystalPosition(TiledMap map, String layer){
        // crystal
        MapObject crystalObject = map.getLayers().get(layer).getObjects().get(0);
        if (crystalObject instanceof RectangleMapObject) {
            float crystalX = (((RectangleMapObject) crystalObject).getRectangle().getX()) * Constants.PPM;
            float crystalY = (((RectangleMapObject) crystalObject).getRectangle().getY()) * Constants.PPM;
            float height = (((RectangleMapObject) crystalObject).getRectangle().getHeight()) * 1 / 16f;
            float width = (((RectangleMapObject) crystalObject).getRectangle().getWidth()) * 1 / 16f;

            return new Vector2(crystalX + width, crystalY + height);
        }
        return null;
    }

    /**
     * Create collision bodies based on a tiled map layer.
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
