package game.test;

import static org.junit.Assert.*;

import org.junit.Test;

import game.logic.*;

public class TestDungeonGameLogic {

	char[][] map1 = {
			{'X','X','X','X','X'},
			{'X','H',' ','G','X'},
			{'I',' ',' ','X','X'},
			{'I','l',' ','X','X'},
			{'X','X','X','X','X'}
	};

	char[][] map2 = {
			{'X','X','X','X','X'},
			{'X','H',' ','O','X'},
			{'I',' ',' ',' ','X'},
			{'I','k',' ',' ','X'},
			{'X','X','X','X','X'}
	};

	//	Game Map

	@Test
	public void testMoveHeroIntoFreeCell(){
		GameMap gameMap = new GameMap(map1);
		Game game = new Game(gameMap);
		assertEquals(new Coordinate2d(1,1), game.getCurrentMap().getHero().getCoordinates());
	}

	@Test
	public void testHeroMoveIntoWall(){
		GameMap gameMap = new GameMap(map1);
		Game game = new Game(gameMap);
		Coordinate2d originalPosition = game.getCurrentMap().getHero().getCoordinates();
		game.getCurrentMap().updateGame("a");
		//	Check if hero didn't move
		assertEquals(originalPosition, game.getCurrentMap().getHero().getCoordinates());
	}

	//	Level one
	@Test
	public void testHeroIsCapturedByGuard(){
		GameMap gameMap = new GameMap(map1);
		Game game = new Game(gameMap);
		assertFalse(game.isGameOver());
		game.getCurrentMap().updateGame("d");
		assertTrue(game.isGameOver());
		assertEquals(EndStatus.DEFEAT, game.getEndStatus());
	}

	@Test
	public void testHeroMoveIntoClosedDoor(){
		GameMap gameMap =  new GameMap(map1);
		Game game = new Game(gameMap);
		// Move hero 1 down 
		game.getCurrentMap().updateGame("s");
		Coordinate2d originalPosition = game.getCurrentMap().getHero().getCoordinates();
		// Try to move 1 left -> Should fail 
		game.getCurrentMap().updateGame("a");
		//	Check if hero didn't move
		assertEquals(originalPosition, game.getCurrentMap().getHero().getCoordinates());
	}

	@Test
	public void testExitDoorsOpenWhenOnLever(){
		GameMap gameMap = new GameMap(map1);
		Game game = new Game(gameMap);
		// Move hero down 2 cells -> get lever
		game.getCurrentMap().updateGame("s");
		game.getCurrentMap().updateGame("s");
		//	Check if exit doors are open
		assertTrue(game.getCurrentMap().isExitDoorsOpen());
	}

	@Test
	public void testGameMapProgresses(){
		GameMap gameMap = new GameMap(map1);
		GameMap map2 = new GameMap(this.map2);
		Game game = new Game(gameMap);
		game.addGameMap(map2);

		// Move hero down 2 cells -> get lever than 1 cell left -> end game map
		game.getCurrentMap().updateGame("s");
		game.getCurrentMap().updateGame("s");
		game.getCurrentMap().updateGame("a");
		//	Check if first level is completed, is on second level
		assertEquals(game.getCurrentMap().toString(), map2.toString());
	}

	///	Second level tests - ogre

	@Test
	public void testMoveHeroIsCapturedByOgre(){
		GameMap gameMap = new GameMap(map2);
		Game game = new Game(gameMap);
		gameMap.setCanGuardsAndOgresMove(false);

		assertFalse(game.isGameOver());
		game.getCurrentMap().updateGame("d");

		//	Check if ogre has not a club
		assertEquals(null,gameMap.getOgres().get(0).getClub());

		assertTrue(game.isGameOver());
		assertEquals(EndStatus.DEFEAT, game.getEndStatus());
	}

	@Test public void testHeroMoveIntoKey(){
		GameMap gameMap = new GameMap(map2);
		Game game = new Game(gameMap);
		//	Move down 2 cells
		game.getCurrentMap().updateGame("s");
		game.getCurrentMap().updateGame("s");
		//	Check if hero letter has changed to "K"
		assertEquals("K", game.getCurrentMap().getHero().getLetter());
		//	Check if hero has key
		assertTrue(game.getCurrentMap().getHero().hasKey());
	}

	@Test public void testHeroMoveIntoClosedDoorWithoutKey(){
		GameMap gameMap = new GameMap(DefaultMaps.map2);
		Game game = new Game(gameMap);
		//	Hero must not have the key
		assertFalse(game.getCurrentMap().getHero().hasKey());
		// Move hero 1 down 
		game.getCurrentMap().updateGame("s");
		// Try to move 1 left -> Unlock door
		game.getCurrentMap().updateGame("a");
		//	Check if door isn't open
		assertFalse(game.getCurrentMap().isExitDoorsOpen());
	}

	@Test public void testHeroMoveIntoClosedDoorWithKey(){
		//	Creates game and map
		GameMap gameMap = new GameMap(map2);
		Game game = new Game(gameMap);
		gameMap.setCanGuardsAndOgresMove(false);

		//	Hero must not have the key
		assertFalse(game.getCurrentMap().getHero().hasKey());

		// Move hero 2 down  -> get key
		game.getCurrentMap().updateGame("s");
		game.getCurrentMap().updateGame("s");

		//	Check if hero has key
		assertTrue(game.getCurrentMap().getHero().hasKey());
		// Try to move 1 left -> Unlock door
		game.getCurrentMap().updateGame("a");
		//	Check if door is open
		assertTrue(game.getCurrentMap().isExitDoorsOpen());
	}

	@Test public void testHeroFinishedGame(){
		//		Creates game and map
		GameMap gameMap = new GameMap(map2);
		Game game = new Game(gameMap);
		gameMap.setCanGuardsAndOgresMove(false);

		//	Hero must not have the key
		assertFalse(game.getCurrentMap().getHero().hasKey());

		// Move hero 2 down  -> get key
		game.getCurrentMap().updateGame("s");
		game.getCurrentMap().updateGame("s");

		//	Check if hero has key
		assertTrue(game.getCurrentMap().getHero().hasKey());
		//	Move 1 left -> Unlock door
		game.getCurrentMap().updateGame("a");
		//	Check if hero don't have key anymore
		assertFalse(game.getCurrentMap().getHero().hasKey());

		//	Check if door is open
		assertTrue(game.getCurrentMap().isExitDoorsOpen());

		//	Move 1 left -> Finish game
		game.getCurrentMap().updateGame("a");

		//	Check if game is over
		assertEquals(game.getState(), GameState.over);
		//	Check if player won
		assertEquals(EndStatus.WIN, EndStatus.WIN);
	}

	@Test(timeout=1000) public void testOgreRandomMovement(){
		//	Creates game and map
		GameMap gameMap = new GameMap(DefaultMaps.map2);

		Game game = new Game(gameMap);

		boolean movedUp = false, movedDown = false, movedRight = false, movedLeft = false;

		while(!movedUp || !movedDown || !movedRight || !movedLeft){
			// Save first ogre last position
			Coordinate2d ogrePosition = gameMap.getOgres().get(0).getCoordinates();

			// Do action
			game.updateGame("");

			//	If ogre moved -1 y
			if (gameMap.getOgres().get(0).getCoordinates().getX() == ogrePosition.getX()
					&& gameMap.getOgres().get(0).getCoordinates().getY() == ogrePosition.getY()-1){
				movedUp = true;
			}

			//	If ogre moved +1 y
			if (gameMap.getOgres().get(0).getCoordinates().getX() == ogrePosition.getX()
					&& gameMap.getOgres().get(0).getCoordinates().getY() == ogrePosition.getY()+1){
				movedDown = true;
			}

			//	If ogre moved +1 x
			if (gameMap.getOgres().get(0).getCoordinates().getX() == ogrePosition.getX()+1
					&& gameMap.getOgres().get(0).getCoordinates().getY() == ogrePosition.getY()){
				movedRight = true;
			}

			//	If ogre moved -1 x
			if (gameMap.getOgres().get(0).getCoordinates().getX() == ogrePosition.getX()-1
					&& gameMap.getOgres().get(0).getCoordinates().getY() == ogrePosition.getY()){
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
		GameMap gameMap = new GameMap(DefaultMaps.map2);
		Game game = new Game(gameMap);
		//	Add bluc to ogres
		gameMap.addClubToOgres();

		boolean movedUp = false, movedDown = false, movedRight = false, movedLeft = false;

		while(!movedUp || !movedDown || !movedRight || !movedLeft){

			// Do action
			game.updateGame("a");

			// get ogre position
			Coordinate2d ogrePosition = gameMap.getOgres().get(0).getCoordinates();

			//	If ogre club is on top
			if (gameMap.getOgres().get(0).getClub().getCoordinates().getX() == ogrePosition.getX()
					&& gameMap.getOgres().get(0).getClub().getCoordinates().getY() == ogrePosition.getY()-1){
				movedUp = true;
			}

			//	If ogre club is down
			if (gameMap.getOgres().get(0).getClub().getCoordinates().getX() == ogrePosition.getX()
					&& gameMap.getOgres().get(0).getClub().getCoordinates().getY() == ogrePosition.getY()+1){
				movedDown = true;
			}

			//	If ogre club is on the right
			if (gameMap.getOgres().get(0).getClub().getCoordinates().getX() == ogrePosition.getX()+1
					&& gameMap.getOgres().get(0).getClub().getCoordinates().getY() == ogrePosition.getY()){
				movedRight = true;
			}

			//	If ogre club is on the left
			if (gameMap.getOgres().get(0).getClub().getCoordinates().getX() == ogrePosition.getX()-1
					&& gameMap.getOgres().get(0).getClub().getCoordinates().getY() == ogrePosition.getY()){
				movedLeft = true;
			}
		}

		//		Assertions
		assertTrue(movedUp);
		assertTrue(movedDown);
		assertTrue(movedRight);
		assertTrue(movedLeft);
	}

	@Test(timeout = 1000) public void testOgreMovedIntoKey(){
		//	Creates game and map
		GameMap gameMap = new GameMap(DefaultMaps.map2);
		Game game = new Game(gameMap);

		boolean ogreIsInKey= false;
		boolean ogreIsOutOfKey = false;

		//	Test if ogre moves to key he changes to "$"
		while(!ogreIsInKey && !ogreIsOutOfKey){

			// Do action
			game.updateGame("a");

			// get ogre position
			Ogre ogre = gameMap.getOgres().get(0);

			if(ogre.isOnKey() && ogre.getLetter() == "$"){
				//	Get ogre first position
				Coordinate2d ogrePosition = gameMap.getOgres().get(0).getCoordinates();
				
				ogreIsInKey = true;
				
				//	Move ogre
				game.updateGame("a");
				//	Check if he moved out of the key with success
				
				if(ogre.isOnKey() == false 
						&& gameMap.getElementAt(ogrePosition.getX(), ogrePosition.getY()) instanceof Key){
					ogreIsOutOfKey = true;
				}
			}
		}
	}
	
	@Test(timeout = 1000) public void testOgreClubMovedIntoKey(){
		//	Creates game and map
		GameMap gameMap = new GameMap(DefaultMaps.map2);
		Game game = new Game(gameMap);
		gameMap.addClubToOgres();
		
		boolean ogreClubIsInKey= false;
		boolean ogreClubIsOutOfKey = false;

		//	Test if ogre club moves to key he changes to "$"
		while(!ogreClubIsInKey & !ogreClubIsOutOfKey){

			// Do action
			game.updateGame("a");

			// get ogre position
			Ogre ogre = gameMap.getOgres().get(0);

			if(ogre.getClub().isOnKey() && ogre.getClub().getLetter() == "$"){
				//	Get ogre first position
				Coordinate2d ogreClubPosition = gameMap.getOgres().get(0).getClub().getCoordinates();
				
				ogreClubIsInKey = true;
				
				//	Move ogre
				game.updateGame("a");
				//	Check if he moved out of the key with success
				
				if(ogre.getClub().isOnKey() == false 
						&& gameMap.getElementAt(ogreClubPosition.getX(), ogreClubPosition.getY()) instanceof Key){
					ogreClubIsOutOfKey = true;
				}
			}
		}
	}

	@Test(timeout = 1000) public void testGuardRandomPick(){
		//	Creates game and map
		GameMap gameMap = new GameMap(DefaultMaps.map1);
		Game game = new Game(gameMap);

		boolean isDrunken = false, isRookie = false, isSuspicious = false;

		while(!isDrunken){
			//	Random guard type
			gameMap.randomGuardType();

			//	If guard is drunken
			if(gameMap.getGuard() instanceof Drunken){
				isDrunken = true;
			}
		}

		while(!isRookie){
			//	Random guard type
			gameMap.randomGuardType();
			//	If guard is rookie
			if(gameMap.getGuard() instanceof Rookie){
				isRookie = true;
			}
		}

		while(!isSuspicious){
			//	Random guard type
			gameMap.randomGuardType();

			//	If guard is suspicious
			if(gameMap.getGuard() instanceof Suspicious){
				isSuspicious = true;
			}
		}
	}

	//	Check if drunken is getting drunk and suspicious is getting suspicious
	@Test(timeout = 1000) public void testGuardRandomPropriety(){
		//	Creates game and map
		GameMap gameMap = new GameMap(DefaultMaps.map1);
		Game game = new Game(gameMap);
		gameMap.setGuardDefaultMoves();

		boolean isDrunken = false, isSuspicious = false;

		while(!isDrunken){
			// Reload map
			gameMap.randomGuardType();
			gameMap.setGuardDefaultMoves();

			//	If guard is drunken and got drunk
			if(gameMap.getGuard() instanceof Drunken){
				//	Test if guard sleeps
				while(!((Drunken)gameMap.getGuard()).isSleeping()){
					gameMap.updateGame("a");
				}
				//	Test if guard stops sleeping
				while(((Drunken)gameMap.getGuard()).isSleeping()){
					gameMap.updateGame("a");
				}
				// Stop sleeping
				isDrunken = true;
			}
		}

		while(!isSuspicious){
			// Reload map
			gameMap.randomGuardType();
			gameMap.setGuardDefaultMoves();

			//	If guard is suspicious and got suspicious
			if(gameMap.getGuard() instanceof Suspicious){
				//	Test if guard gets suspecious
				while(!((Suspicious) gameMap.getGuard()).isInReverse()){
					gameMap.updateGame("a");
				}
				//	Test if guard stops getting suspicious
				while(((Suspicious) gameMap.getGuard()).isInReverse()){
					gameMap.updateGame("a");
				}
				isSuspicious = true;
			}
		}
	}

	@Test public void testHeroMoveIntoBat(){
		//		Creates game and map
		GameMap gameMap = new GameMap(DefaultMaps.map2);
		Game game = new Game(gameMap);

		//	Check if hero doesn't have club
		assertFalse(gameMap.getHero().isHasClub());
		assertEquals("H", gameMap.getHero().getLetter());
		//	Move hero one cell right
		game.updateGame("d");

		//	Check if hero got club
		assertTrue(gameMap.getHero().isHasClub());
		assertEquals("A", gameMap.getHero().getLetter());
	}

	@Test public void testHeroStunOgre(){
		//	Creates game and map
		GameMap gameMap = new GameMap(map2);
		Game game = new Game(gameMap);

		//	Check if hero doesn't have club
		assertFalse(gameMap.getHero().isHasClub());
		assertEquals("H", gameMap.getHero().getLetter());

		//	Give hero a club
		gameMap.getHero().setHasClub(true);

		//	Check if hero got club
		assertTrue(gameMap.getHero().isHasClub());
		assertEquals("A", gameMap.getHero().getLetter());

		//	Move hero to stun ogre
		game.updateGame("d");

		//	Check if ogre is stunned

		//	Check if ogre has not a club
		assertEquals(null, gameMap.getOgres().get(0).getClub());

		assertTrue(gameMap.getOgres().get(0).isStunned());
		assertEquals("8", gameMap.getOgres().get(0).getLetter());
	}

	/** 
	 * Test if all doors are opening properly
	 * */
	@Test public void openAllDoors(){
		// Creates game and map
		GameMap gameMap = new GameMap(map1);
		Game game = new Game(gameMap);		

		gameMap.openAllDoors();
		assertTrue(gameMap.isExitDoorsOpen());
	}
}
