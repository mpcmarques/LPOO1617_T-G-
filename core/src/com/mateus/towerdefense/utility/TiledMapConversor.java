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
     * Create walls based on tiled walls map layer
     */
    private Array<EntityController> createWalls(GameController gameController, TiledMap map) {
        Array<EntityController> walls = new Array<EntityController>();
        // get spawn location
        for (MapObject mapObject : map.getLayers().get("walls").getObjects()) {
            float x = (((RectangleMapObject) mapObject).getRectangle().x) * 2 / 16f;
            float y = (((RectangleMapObject) mapObject).getRectangle().y) * 2 / 16f;
            float height = (((RectangleMapObject) mapObject).getRectangle().getHeight()) * 1 / 16f;
            float width = (((RectangleMapObject) mapObject).getRectangle().getWidth()) * 1 / 16f;

            // create wall
            //RectangleActor TODO
            Body body = Gdx2dBody.createBox(gameController.getWorld(), x + width, y + height, width, height, true, Constants.BIT_WALL, (short) (Constants.BIT_WALL | Constants.BIT_MONSTER));
            //WallController wallController = new WallController(body);
           // walls.add(wallController);
        }
        return walls;
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
