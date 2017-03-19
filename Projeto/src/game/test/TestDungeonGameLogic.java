package game.test;

import static org.junit.Assert.*;

import org.junit.Test;

import game.logic.*;

public class TestDungeonGameLogic {

	char[][] map = {{'X','X','X','X','X'},
			{'X','H',' ','G','X'},
			{'I',' ',' ','X','X'},
			{'I','l',' ','X','X'},
			{'X','X','X','X','X'}
	};

	char[][] map2 = {{'X','X','X','X','X'},
			{'X','H',' ','O','X'},
			{'I',' ',' ',' ','X'},
			{'I','k',' ',' ','X'},
			{'X','X','X','X','X'}
	};

	//	Game Map
	
	@Test
	public void testMoveHeroIntoFreeCell(){
		GameMap gameMap = new GameMap(map);
		Game game = new Game(gameMap);
		assertEquals(new Coordinate2d(1,1), game.getGameMap().getHero().getCoordinate());
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
	
	//	Level one
	@Test
	public void testHeroIsCapturedByGuard(){
		GameMap gameMap = new LevelOne(map);
		Game game = new Game(gameMap);
		assertFalse(game.isGameOver());
		game.getGameMap().updateGame("d");
		assertTrue(game.isGameOver());
		assertEquals(EndStatus.DEFEAT, game.getEndStatus());
	}

	@Test
	public void testHeroMoveIntoClosedDoor(){
		GameMap gameMap = new LevelOne(map);
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
		GameMap gameMap = new LevelOne(map);
		Game game = new Game(gameMap);
		// Move hero down 2 cells -> get lever
		game.getGameMap().updateGame("s");
		game.getGameMap().updateGame("s");
		//	Check if exit doors are open
		assertTrue(game.getGameMap().isExitDoorsOpen());
	}

	@Test
	public void testGameMapProgresses(){
		GameMap gameMap = new LevelOne(map);
		Game game = new Game(gameMap);
		// Move hero down 2 cells -> get lever than 1 cell left -> end game map
		game.getGameMap().updateGame("s");
		game.getGameMap().updateGame("s");
		game.getGameMap().updateGame("a");
		//	Check if first level is completed, is on second level
		assertFalse(game.getGameMap() instanceof LevelOne);
	}

	///	Second level tests - ogre

	@Test
	public void testMoveHeroIsCapturedByOgre(){
		GameMap gameMap = new LevelTwo(map2);
		Game game = new Game(gameMap);
		assertFalse(game.isGameOver());
		game.getGameMap().updateGame("d");
		assertTrue(game.isGameOver());
		assertEquals(EndStatus.DEFEAT, game.getEndStatus());
	}

	@Test public void testHeroMoveIntoKey(){
		GameMap gameMap = new LevelTwo(map2);
		Game game = new Game(gameMap);
		//	Move down 2 cells
		game.getGameMap().updateGame("s");
		game.getGameMap().updateGame("s");
		//	Check if hero letter has changed to "K"
		assertEquals("K", game.getGameMap().getHero().getLetter());
		//	Check if hero has key
		assertTrue(game.getGameMap().getHero().hasKey());
	}

	@Test public void testHeroMoveIntoClosedDoorWithoutKey(){
		GameMap gameMap = new LevelTwo(map2);
		Game game = new Game(gameMap);
		//	Hero must not have the key
		assertFalse(game.getGameMap().getHero().hasKey());
		// Move hero 1 down 
		game.getGameMap().updateGame("s");
		// Try to move 1 left -> Unlock door
		game.getGameMap().updateGame("a");
		//	Check if door isn't open
		assertFalse(game.getGameMap().isExitDoorsOpen());
	}

	@Test public void testHeroMoveIntoClosedDoorWithKey(){
		//	Creates game and map
		GameMap gameMap = new LevelTwo(map2);
		Game game = new Game(gameMap);

		//	Hero must not have the key
		assertFalse(game.getGameMap().getHero().hasKey());

		// Move hero 2 down  -> get key
		game.getGameMap().updateGame("s");
		game.getGameMap().updateGame("s");

		//	Check if hero has key
		assertTrue(game.getGameMap().getHero().hasKey());
		// Try to move 1 left -> Unlock door
		game.getGameMap().updateGame("a");
		//	Check if door is open
		assertTrue(game.getGameMap().isExitDoorsOpen());
	}

	@Test public void testHeroFinishedGame(){
		//		Creates game and map
		GameMap gameMap = new LevelTwo(map2);
		Game game = new Game(gameMap);

		//	Hero must not have the key
		assertFalse(game.getGameMap().getHero().hasKey());

		// Move hero 2 down  -> get key
		game.getGameMap().updateGame("s");
		game.getGameMap().updateGame("s");

		//	Check if hero has key
		assertTrue(game.getGameMap().getHero().hasKey());
		//	Move 1 left -> Unlock door
		game.getGameMap().updateGame("a");
		
		//	Check if door is open
		assertTrue(game.getGameMap().isExitDoorsOpen());
	
		//	Move 1 left -> Finish game
		game.getGameMap().updateGame("a");
		
		//	Check if game is over
		assertTrue(game.isGameOver());
		assertEquals(game.getState(), GameState.over);
		//	Check if player won
		assertEquals(game.getEndStatus(), EndStatus.WIN);
	}
}
