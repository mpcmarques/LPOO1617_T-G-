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
		GameMap gameMap = new LevelOne(map);
		Game game = new Game(gameMap);
		assertEquals(new Coordinate2d(1,1), game.getGameMap().getHero().getCoordinate());
	}

	@Test
	public void testHeroMoveIntoWall(){
		GameMap gameMap = new LevelOne(map);
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

	@Test(timeout=1000) public void testOgreRandomMovement(){
		//	Creates game and map
		LevelTwo gameMap = new LevelTwo();
		gameMap.setOgreAllowedToMove(true);

		Game game = new Game(gameMap);

		boolean movedUp = false, movedDown = false, movedRight = false, movedLeft = false;

		while(!movedUp || !movedDown || !movedRight || !movedLeft){
			// Save first ogre last position
			Coordinate2d ogrePosition = game.getGameMap().getOgres().get(0).getCoordinate();

			// Do action
			game.updateGame("");

			//	If ogre moved -1 y
			if (game.getGameMap().getOgres().get(0).getCoordinate().getX() == ogrePosition.getX()
					&& game.getGameMap().getOgres().get(0).getCoordinate().getY() == ogrePosition.getY()-1){
				movedUp = true;
			}

			//	If ogre moved +1 y
			if (game.getGameMap().getOgres().get(0).getCoordinate().getX() == ogrePosition.getX()
					&& game.getGameMap().getOgres().get(0).getCoordinate().getY() == ogrePosition.getY()+1){
				movedDown = true;
			}

			//	If ogre moved +1 x
			if (game.getGameMap().getOgres().get(0).getCoordinate().getX() == ogrePosition.getX()+1
					&& game.getGameMap().getOgres().get(0).getCoordinate().getY() == ogrePosition.getY()){
				movedRight = true;
			}

			//	If ogre moved -1 x
			if (game.getGameMap().getOgres().get(0).getCoordinate().getX() == ogrePosition.getX()-1
					&& game.getGameMap().getOgres().get(0).getCoordinate().getY() == ogrePosition.getY()){
				movedLeft = true;
			}
		}

		//	Assertions
		assertTrue(movedUp);
		assertTrue(movedDown);
		assertTrue(movedRight);
		assertTrue(movedLeft);
	}

	@Test(timeout = 1000) public void testOgreRandomClubSwing(){
		//	Creates game and map
		GameMap gameMap = new LevelTwo();
		Game game = new Game(gameMap);


		boolean movedUp = false, movedDown = false, movedRight = false, movedLeft = false;

		while(!movedUp || !movedDown || !movedRight || !movedLeft){

			// Do action
			game.updateGame("a");

			// get ogre position
			Coordinate2d ogrePosition = game.getGameMap().getOgres().get(0).getCoordinate();

			//	If ogre club is on top
			if (game.getGameMap().getOgres().get(0).getClub().getCoordinate().getX() == ogrePosition.getX()
					&& game.getGameMap().getOgres().get(0).getClub().getCoordinate().getY() == ogrePosition.getY()-1){
				movedUp = true;
			}

			//	If ogre club is down
			if (game.getGameMap().getOgres().get(0).getClub().getCoordinate().getX() == ogrePosition.getX()
					&& game.getGameMap().getOgres().get(0).getClub().getCoordinate().getY() == ogrePosition.getY()+1){
				movedDown = true;
			}

			//	If ogre club is on the right
			if (game.getGameMap().getOgres().get(0).getClub().getCoordinate().getX() == ogrePosition.getX()+1
					&& game.getGameMap().getOgres().get(0).getClub().getCoordinate().getY() == ogrePosition.getY()){
				movedRight = true;
			}

			//	If ogre club is on the left
			if (game.getGameMap().getOgres().get(0).getClub().getCoordinate().getX() == ogrePosition.getX()-1
					&& game.getGameMap().getOgres().get(0).getClub().getCoordinate().getY() == ogrePosition.getY()){
				movedLeft = true;
			}
		}

		//		Assertions
		assertTrue(movedUp);
		assertTrue(movedDown);
		assertTrue(movedRight);
		assertTrue(movedLeft);
	}

	@Test(timeout = 1000) public void testGuardRandomPick(){
		//	Creates game and map
		LevelOne gameMap = new LevelOne();
		Game game = new Game(gameMap);


		boolean isDrunken = false, isRookie = false, isSuspicious = false;

		while(!isDrunken || !isRookie || !isSuspicious){

			// Do action
			gameMap.startMap();

			//	If guard is drunken
			if(gameMap.getGuard() instanceof Drunken){
				isDrunken = true;
			}

			//	If guard is rookie
			if(gameMap.getGuard() instanceof Rookie){
				isRookie = true;
			}
			//	If guard is suspicious
			if(gameMap.getGuard() instanceof Suspicious){
				isSuspicious = true;
			}
		}

		//		Assertions
		assertTrue(isRookie);
		assertTrue(isSuspicious);
		assertTrue(isDrunken);
	}

	//	Check if drunken is getting drunk and suspicious is getting suspicious
	@Test(timeout = 1000) public void testGuardRandomPropriety(){
		//	Creates game and map
		LevelOne gameMap = new LevelOne();
		Game game = new Game(gameMap);

		boolean isDrunken = false, isSuspicious = false;

		while(!isDrunken || !isSuspicious){

			// Do action
			gameMap.startMap();

			//	If guard is drunken and got drunk
			if(gameMap.getGuard() instanceof Drunken){
				//	Test if guard sleeps
				while(!((Drunken) gameMap.getGuard()).isSleeping()){
					game.updateGame("a");
				}
				//	Test if guard stops sleeping
				while(((Drunken) gameMap.getGuard()).isSleeping()){
					game.updateGame("a");
				}
				// Stop sleeping
				isDrunken = true;
			}

			//	If guard is suspicious and got suspicious
			if(gameMap.getGuard() instanceof Suspicious){
				//	Test if guard gets suspecious
				while(!((Suspicious) gameMap.getGuard()).isInReverse()){
					game.updateGame("a");
				}
				//	Test if guard stops getting suspicious
				while(((Suspicious) gameMap.getGuard()).isInReverse()){
					game.updateGame("a");
				}
				isSuspicious = true;
			}
		}

		//		Assertions
		assertTrue(isSuspicious);
		assertTrue(isDrunken);
	}

	@Test public void testHeroMoveIntoBat(){
		//		Creates game and map
		LevelTwo gameMap = new LevelTwo();
		Game game = new Game(gameMap);

		//	Check if hero doesn't have club
		assertFalse(gameMap.getHero().hasWeapon());
		assertEquals("H", gameMap.getHero().getLetter());
		//	Move hero one cell right
		game.updateGame("d");

		//	Check if hero got club
		assertTrue(gameMap.getHero().hasWeapon());
		assertEquals("A", gameMap.getHero().getLetter());
	}

	@Test public void testHeroStunOgre(){
		//	Creates game and map
		LevelTwo gameMap = new LevelTwo(map2);
		Game game = new Game(gameMap);

		//	Check if hero doesn't have club
		assertFalse(gameMap.getHero().hasWeapon());
		assertEquals("H", gameMap.getHero().getLetter());
		
		//	Give hero a club
		gameMap.getHero().setClub(new Club(gameMap.getHero().getX(),gameMap.getHero().getY()));

		//	Check if hero got club
		assertTrue(gameMap.getHero().hasWeapon());
		assertEquals("A", gameMap.getHero().getLetter());
		
		//	Move hero to stun ogre
		game.updateGame("d");
		
		//	Check if ogre is stunned
		assertTrue(gameMap.getOgres().get(0).isStunned());
		assertEquals("8", gameMap.getOgres().get(0).getLetter());
	}
}
