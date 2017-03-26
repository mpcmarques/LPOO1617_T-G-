package game.test;

import static org.junit.Assert.*;

import org.junit.Test;

import game.logic.*;

/** 
 * Game testing class
 * */
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


	/** 
	 * Test if hero can move into a free cell
	 * */
	@Test
	public void testMoveHeroIntoFreeCell(){
		GameMap gameMap = new GameMap(map1);
		Game game = new Game(gameMap);
		assertEquals(new Coordinate2d(1,1), game.getCurrentMap().getHero().getCoordinates());
	}

	/** 
	 * Test if hero can't move to a wall
	 * */
	@Test
	public void testHeroMoveIntoWall(){
		GameMap gameMap = new GameMap(map1);
		Game game = new Game(gameMap);
		Coordinate2d originalPosition = game.getCurrentMap().getHero().getCoordinates();
		game.getCurrentMap().updateGame("a");
		//	Check if hero didn't move
		assertEquals(originalPosition, game.getCurrentMap().getHero().getCoordinates());
	}

	/** 
	 * Test if hero is captured by a guard
	 * */
	@Test
	public void testHeroIsCapturedByGuard(){
		GameMap gameMap = new GameMap(map1);
		Game game = new Game(gameMap);
		assertFalse(game.isGameOver());
		game.getCurrentMap().updateGame("d");
		assertTrue(game.isGameOver());
		assertEquals(EndStatus.DEFEAT, game.getEndStatus());
		assertTrue(game.getCurrentMap().isGuardNear());
	}


	/**
	 * Test if hero moved into a closed door
	 *  */
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
	/** 
	 * Test if hero moved to a lever and opened doors
	 * */
	@Test
	public void testExitDoorsOpenWhenOnLever(){
		GameMap gameMap = new GameMap(map1);
		Game game = new Game(gameMap);
		// Move hero down 2 cells -> get lever
		game.getCurrentMap().updateGame("s");
		// Check if can move down
		assertTrue((game.getCurrentMap().getElementAt(game.getCurrentMap().getHero().getX(), game.getCurrentMap().getHero().getY()+1)).canMoveTo());
		//	Check if moved down
		game.getCurrentMap().updateGame("s");
		//	Check if exit doors are open
		assertTrue(game.getCurrentMap().isExitDoorsOpen());
	}
	/** 
	 * Test if the game changes map when the actual finishes
	 * */
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

	/** 
	 * Test if hero is captured by ogre
	 * */
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
		assertTrue(game.getCurrentMap().isOgreNear(game.getCurrentMap().getHero().getX(), game.getCurrentMap().getHero().getY()) != null);
		assertEquals(EndStatus.DEFEAT, game.getEndStatus());
	}
	
	@Test(timeout = 1000) public void testRandomService(){
		boolean n1 = false,n2 = false,n3 = false;
		int random;
		while(!n1 || !n2 || !n3){
			random = RandomService.getRandomInt(0, 2);
			
			if(random == 0) n1 = true;
			if(random == 1) n2 = true;
			if(random == 2) n3 = true;
		}
	}
	
	/** 
	 * Test if hero move into key and get key
	 * */
	@Test public void testHeroMoveIntoKey(){
		GameMap gameMap = new GameMap(map2);
		Game game = new Game(gameMap);
		
		//	Hero isn't with key
		assertFalse(game.getCurrentMap().getHero().hasKey());
		
		//	Move down 2 cells
		game.getCurrentMap().updateGame("s");
		
		//	Assert that can move to key
		assertTrue((game.getCurrentMap().getElementAt(game.getCurrentMap().getHero().getX(), game.getCurrentMap().getHero().getY()+1)) instanceof Key);
		assertTrue(((Key)game.getCurrentMap().getElementAt(game.getCurrentMap().getHero().getX(), game.getCurrentMap().getHero().getY()+1)).canMoveTo());
		assertEquals("k",((Key)game.getCurrentMap().getElementAt(game.getCurrentMap().getHero().getX(), game.getCurrentMap().getHero().getY()+1)).getLetter());
		
		game.getCurrentMap().updateGame("s");
		
		//	Check if hero letter has changed to "K"
		assertEquals("K", game.getCurrentMap().getHero().getLetter());
		assertTrue(game.getCurrentMap().getHero().hasKey());
	}
	/** 
	 * Test if hero can't move to a close door without a key
	 * */
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
	/** 
	 * Test if hero can move to a closed door with key
	 * */
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
		//	Check if door is closed
		assertEquals("I", game.getCurrentMap().getElementAt(game.getCurrentMap().getHero().getX()-1, game.getCurrentMap().getHero().getY()).getLetter());
		assertFalse(game.getCurrentMap().isExitDoorsOpen());
		// Try to move 1 left -> Unlock door
		game.getCurrentMap().updateGame("a");
		//	Check if door is open
		assertTrue(game.getCurrentMap().isExitDoorsOpen());
		assertEquals("S", game.getCurrentMap().getElementAt(game.getCurrentMap().getHero().getX()-1, game.getCurrentMap().getHero().getY()).getLetter());
	}

	/** 
	 * Test if finished game
	 * */
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
		assertEquals(GameState.over, game.getState());
		//	Check if player won
		assertEquals(EndStatus.WIN, game.getEndStatus());
	}

	/** 
	 * Test ogre random movement
	 * */
	@Test(timeout=1000) public void testOgreRandomMovement(){
		//	Creates game and map
		GameMap gameMap = new GameMap(DefaultMaps.map2);

		Game game = new Game(gameMap);

		boolean movedUp = false, movedDown = false, movedRight = false, movedLeft = false;
		
		assertFalse(gameMap.getOgres().get(0).isOnKey());
		assertFalse(gameMap.getOgres().get(0).isStunned());
		assertEquals("O",gameMap.getOgres().get(0).getLetter());
		assertEquals(0, gameMap.getOgres().get(0).getStunCounter());
		assertFalse(gameMap.getOgres().get(0).canMoveTo());

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

	/** 
	 * Test ogre random club swing
	 * */
	@Test(timeout = 1000) public void testOgreRandomClubSwing(){
		//	Creates game and map
		GameMap gameMap = new GameMap(DefaultMaps.map2);
		Game game = new Game(gameMap);
		//	Add club to ogres
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
	/** 
	 * Test if hero is being killed by ogre club
	 * */
	@Test(timeout = 1000) public void testHeroKilledByOgreClub(){
		//		Creates game and map
		GameMap gameMap = new GameMap(DefaultMaps.map2);
		Game game = new Game(gameMap);
		gameMap.addClubToOgres();
		
		//	Assert that ogre is not in a key or stunned when he is created
		assertFalse(gameMap.getOgres().get(0).isOnKey());
		assertFalse(gameMap.getOgres().get(0).isStunned());
		
		boolean heroIsKilledByOgre = false;

		//	Test if ogre moves to key he changes to "$"
		while(!heroIsKilledByOgre){

			// Do action
			game.updateGame("a");

			if(game.getCurrentMap().isOgreClubNear() && game.isGameOver()){
				heroIsKilledByOgre = true;
			}
		}
	}

	/** 
	 * Test if hero can't move to pilar
	 * */
	@Test public void heroCantMoveToPilar(){
		//	Creates game and map
		GameMap gameMap = new GameMap(DefaultMaps.map2);
		Game game = new Game(gameMap);

		Hero hero = gameMap.getHero();
		//	Adds pilar above hero
		gameMap.addElementAt(new Pilar(hero.getX(), hero.getY()-1), hero.getX(), hero.getY()-1);
		//	Check if pilar was added
		assertTrue(gameMap.getElementAt(hero.getX(), hero.getY()-1) instanceof Pilar);

		//	Try to move hero up
		game.updateGame("w");
		//	Assert that hero can't move, pilar still on top of him
		assertTrue(gameMap.getElementAt(hero.getX(), hero.getY()-1) instanceof Pilar);
	}

	/** */
	@Test public void testGameIsIniting(){
		//		Creates game and map
		GameMap gameMap = new GameMap(DefaultMaps.map2);
		Game game = new Game(gameMap);
		
		//	Assert that game is started
		assertEquals(GameState.started, game.getState());
		assertFalse(game.isGameOver());
		assertFalse(game.getCurrentMap().isCompleted());
		assertTrue(game.getCurrentMap().isPlayable());
		assertTrue(game.getCurrentMap().canGuardsAndOgresMove());
	}

	/** 
	 * Test if ogre moved into a key
	 * */
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
	/** 
	 * Test ogre club moved into a key
	 * */
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

	/** 
	 * Test guard random pick
	 * */
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

	/** 
	 * Check if drunken guard is getting drunk and suspecious guard is getting suspecious
	 * */
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

	/** 
	 * Check if hero moved into a club
	 * */
	@Test public void testHeroMoveIntoBat(){
		//		Creates game and map
		GameMap gameMap = new GameMap(DefaultMaps.map2);
		Game game = new Game(gameMap);

		//	Check if hero doesn't have club
		assertFalse(gameMap.getHero().isHasClub());
		assertEquals("H", gameMap.getHero().getLetter());
		
		//	Check if club is on the right
		assertTrue(gameMap.getElementAt(gameMap.getHero().getX()+1, gameMap.getHero().getY()) instanceof Club);
		assertEquals("*",gameMap.getElementAt(gameMap.getHero().getX()+1, gameMap.getHero().getY()).getLetter());
		assertTrue("*",gameMap.getElementAt(gameMap.getHero().getX()+1, gameMap.getHero().getY()).canMoveTo());
		
		//	Move hero one cell right
		game.updateGame("d");

		//	Check if hero got club
		assertTrue(gameMap.getHero().isHasClub());
		assertEquals("A", gameMap.getHero().getLetter());
	}
	
	/** 
	 * Check if two coordinates 2d are equal
	 * */
	@Test public void testCoodinate2dEquals(){
		Coordinate2d c1 = new Coordinate2d(0,0);
		Coordinate2d c2 = new Coordinate2d(0,0);
		assertEquals(c1,c2);
	}
	
	/** 
	 * Test drunken guard
	 * */
	@Test public void testDrunkenGuard(){
		Drunken guard = new Drunken(0,0);
		
		assertFalse(guard.isSleeping());
		assertEquals(0, guard.getSleepTime());
		assertEquals("G", guard.getLetter());
		assertFalse(guard.canMoveTo());
		
		// Get guard drunk
		guard.setSleeping(true);
		
		//	Check
		assertTrue(guard.isSleeping());
		assertTrue(0 < guard.getSleepTime());
		assertEquals("g", guard.getLetter());
	}

	/** 
	 * Check if hero stun ogre successfully
	 * */
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
		
		//	Decrease stun counter
		game.getCurrentMap().getOgres().get(0).decreaseStunCounter();
		game.getCurrentMap().getOgres().get(0).decreaseStunCounter();
		assertFalse(gameMap.getOgres().get(0).isStunned());
		assertEquals("O", gameMap.getOgres().get(0).getLetter());
		
	}

	/** 
	 * Test if all doors are opening properly
	 * */
	@Test public void openAllDoors(){
		// Creates game and map
		GameMap gameMap = new GameMap(map1);
		Game game = new Game(gameMap);		

		assertFalse(gameMap.isExitDoorsOpen());
		gameMap.openAllDoors();
		assertTrue(gameMap.isExitDoorsOpen());
	}
}
