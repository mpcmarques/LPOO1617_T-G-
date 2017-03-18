package game.test;

import static org.junit.Assert.*;

import org.junit.Test;

import game.logic.*;

public class TestDungeonGameLogic {
	
	char[][] map = {{'X','X','X','X','X'},
					{'X','H',' ','G','X'},
					{'I',' ',' ','X','X'},
					{'I','k',' ','X','X'},
					{'X','X','X','X','X'}
	};
	
	@Test
	public void testMoveHeroIntoFreeCell(){
		GameMap gameMap = new GameMap(map);
		Game game = new Game(gameMap);
		assertEquals(new Coordinate2d(1,1), game.getGameMap().getHero().getCoordinate());
	}
	
	@Test
	public void testHeroIsCapturedByGuard(){
		GameMap gameMap = new GameMap(map);
		Game game = new Game(gameMap);
		assertFalse(game.isGameOver());
		game.getGameMap().updateGame("d");
		assertTrue(game.isGameOver());
		assertEquals(EndStatus.DEFEAT, game.getEndStatus());
	}
	
	@Test
	public void testHeroMoveIntoWall(){
		GameMap gameMap = new GameMap(map);
		Game game = new Game(gameMap);
		Coordinate2d originalPosition = game.getGameMap().getHero().getCoordinate();
		game.getGameMap().updateGame("a");
		//	Check if hero didn't move
		assertEquals(originalPosition, game.getGameMap().getHero().getCoordinate());
	}
	
	@Test
	public void testHeroMoveIntoClosedDoor(){
		GameMap gameMap = new GameMap(map);
		Game game = new Game(gameMap);
		// Move hero 1 down 
		game.getGameMap().updateGame("s");
		Coordinate2d originalPosition = game.getGameMap().getHero().getCoordinate();
		// Try to move 1 left -> Should fail 
		game.getGameMap().updateGame("a");
		//	Check if hero didn't move
		assertEquals(originalPosition, game.getGameMap().getHero().getCoordinate());
	}
	
	@Test
	public void testExitDoorsOpenWhenOnLever(){
		GameMap gameMap = new GameMap(map);
		Game game = new Game(gameMap);
		// Move hero down 2 cells -> get lever
		game.getGameMap().updateGame("s");
		game.getGameMap().updateGame("s");
		//	Check if exit doors are open
		assertTrue(game.getGameMap().isExitDoorsOpen());
	}
	
	@Test
	public void testGameMapCompleted(){
		GameMap gameMap = new GameMap(map);
		Game game = new Game(gameMap);
		// Move hero down 2 cells -> get lever than 1 cell left -> end game map
		game.getGameMap().updateGame("s");
		game.getGameMap().updateGame("s");
		game.getGameMap().updateGame("a");
		//	Check if exit doors are open
		assertTrue(game.getGameMap().isCompleted());
	}
}
