package game.logic;

import java.util.ArrayList;

public class Game extends Object {
	private Map map;
	private Hero hero;
	private Guard guard;
	private int guardMovesCounter;
	private GameState state;
	private Lever lever;
	private Ogre ogre;
	private Key key;

	public Game(){
		setState(GameState.started);
		map = new Map(10,10);
		hero = new Hero(1,1);
		guard = null;
		setOgre(null);
		setKey(null);
		lever = new Lever(6,8);
		guardMovesCounter = 0;
	}

	public void updateGame(String typed){

		// Checked typed key
		switch (typed) {
		case "w": // Up
			moveHero(0,-1);
			break;
		case "s": // Down
			moveHero(0,1);
			break;
		case "a": // Left
			moveHero(-1,0);
			break;
		case "d": // Right
			moveHero(1,0);
			break;
		case "0":
			//	End game
			setState(GameState.ended);
			break;
		default:
			break;
		}

		// First level logic
		if (this.getState() == GameState.firstlvl){
			firstLevelLogic();
		}
		//	Second level logic
		else if (this.getState() == GameState.secondlvl){
			//	Move ogre
			moveOgre();
			//	Check if moved next to a ogre or a ogre bash
			if(isOgreNear() == true || isClubNear() == true){
				// END GAME, GAME Over
				setState(GameState.ended);
				System.out.println("Game Over!");
			}
		}
	}

	///	First level logic
	public void firstLevelLogic(){
		//		MOVE GUARD
		//	Check guard personality
		//	Rookie
		if(guard instanceof Rookie){
			moveGuard(false);
		} 
		//	Drunken
		else if(guard instanceof Drunken){
			Drunken drunkGuard = (Drunken)guard;
			//	Check if guard is not sleeping
			if (!drunkGuard.isSleeping()){
				int rates = RandomService.getRandomInt(1, 7);
				//	Drunken chance of sleeping
				if (rates == 1){
					drunkGuard.setSleeping(true);
				} else {
					moveGuard(false);
				}
			} 
			//	Guard is sleeping
			else {
				// Decrease sleeptime
				drunkGuard.setSleepTime(drunkGuard.getSleepTime() - 1);
				// If guard sleeping time is over awake him
				if (drunkGuard.getSleepTime() == 0){
					//	Awake guard
					drunkGuard.setSleeping(false);
				}
			}
		}
		// Suspicious guard
		else if (guard instanceof Suspicious){
			Suspicious suspiciousGuard = (Suspicious)guard;
			// Move guard
			moveGuard(suspiciousGuard.isInReverse());

			//	Guard have a random chance to change direction
			int reverseChance = RandomService.getRandomInt(1, 6);
			if (reverseChance == 1){
				//	Change guard direction
				suspiciousGuard.changeDirection();
			}
		}

		//	Game Over check
		//	Check if moved next to a guard
		if(isGuardNear() == true){
			//	Check if guard is not a drunken sleeping
			if (guard instanceof Drunken) {
				Drunken drunkGuard = (Drunken)guard;
				if (drunkGuard.isSleeping()){
					//	If guard is sleeping, doesnt end game.
					return;
				}
			} 
			// END GAME, GAME Over
			setState(GameState.ended);
			System.out.println("Game Over!");
		}
	}

	/// Starts first level
	public void startFirstLevel(){
		//	Start new map
		this.map = new Map(10,10);
		//	Set game state
		setState(GameState.firstlvl);
		//	 Create map layout
		//		Add walls to map
		createWallsFirstLevel();
		//	Add  doors to map
		createDoorsFirstLevel();
		//	Add hero to map
		map.addCell(hero);
		//	Add lever to map
		map.addCell(lever);
		//	Add guard to map
		guard = new Suspicious(8,1);
		//	Set guard moves
		setGuardMoves();
		map.addCell(guard);
		//	Print map in the first time
		System.out.println(map);
	}

	public void setGuardMoves(){
		ArrayList<Coordinate2d> newPositions = new ArrayList<Coordinate2d>();
		int x = 7, y = 1; // First move
		int i;

		// Move left 1 cell
		Coordinate2d first = new Coordinate2d(x,y);
		newPositions.add(first);
		//	Move down 4 cells
		for (i = 0; i < 4; i++){
			y += 1;
			Coordinate2d coord = new Coordinate2d(x,y);
			newPositions.add(coord);
		}
		//	Move left 6 cells
		for (i = 0; i < 6; i++){
			x -= 1;
			Coordinate2d coord = new Coordinate2d(x,y);
			newPositions.add(coord);
		}
		// Move down
		y += 1;
		Coordinate2d second = new Coordinate2d(x,y);
		newPositions.add(second);
		// Move right 7 cells
		for (i = 0; i < 7; i++){
			x += 1;
			Coordinate2d coord = new Coordinate2d(x,y);
			newPositions.add(coord);
		}
		// Move up 5 cells
		for (i = 0; i < 5; i++){
			y -= 1;
			Coordinate2d coord = new Coordinate2d(x,y);
			newPositions.add(coord);
		}

		// Replace guard moves
		guard.setMovePositions(newPositions);
	}

	///	Finishes first level and starts second level
	public void startSecondLevel(){
		//		Start new map
		this.map = new Map(10,10);
		//		Set game state
		setState(GameState.secondlvl);
		//	Create map layout
		//	Add walls to map
		createWallsSecondLevel();
		//	Add  doors to map
		createDoorsSecondLevel();
		//	Change hero coordinates
		hero = new Hero(1,8);
		//	Add hero to map
		map.addCell(hero);
		//	Create new key
		this.key = new Key(8,1);
		//	Add key to map
		map.addCell(key);
		//	Remove guard
		guard = null;
		//	Add ogre to map
		ogre = new Ogre(4,1);
		//	Add ogre weapon
		ogre.addClub();
		//	Add ogre to map
		map.addCell(ogre);
	}

	///	Moves hero
	public void moveHero(int x, int y){
		//	BEFORE MOVING
		// Check if out of bounds
		if (hero.getX() + x >= 0 
				&& hero.getX() + x <= map.getNumberCellsForLine()
				&& hero.getY() + y >= 0 
				&& hero.getY() + y <= map.getNumberLines()){
			//	Check if moving to a wall
			if(map.getCells()[hero.getY()+y][hero.getX()+x] instanceof Wall){
				return;
			}
			//	Check if moving to door
			if(map.getCells()[hero.getY()+y][hero.getX()+x] instanceof Door){
				Door door = (Door) map.getCells()[hero.getY()+y][hero.getX()+x];
				// Check if door is closed
				if (door.isOpen() == false){
					//	Check if has key
					if (hero.isHaveKey()){
						//	Open door
						door.openDoor();
						//	Remove key from hero
						hero.setHaveKey(false);
					}
					return;
				} else {
					//	If it is open and exit, finish map
					if(door.isExit()){
						//	End first level 
						if (this.getState() == GameState.firstlvl){
							startSecondLevel();
							return;
						}
						// End second level
						else if (this.getState() == GameState.secondlvl){
							setState(GameState.ended);
						}
					}
				}
			}

			// MOVE
			//	Check if moving to a lever
			if(map.getCells()[hero.getY()+y][hero.getX()+x] instanceof Lever){
				//	If level 1
				if (getState() == GameState.firstlvl){
					map.openAllDoors();
				}
				//	If level 2
				if (getState() == GameState.secondlvl){
					hero.setHaveKey(true);
					map.openAllDoors();
				}
			}
			//	Check if moving to a key
			if(map.getCells()[hero.getY()+y][hero.getX()+x] instanceof Key){
				//	If level 2
				if (getState() == GameState.secondlvl){
					hero.setHaveKey(true);
				}
			}

			// Clean previous cell
			map.getCells()[hero.getY()][hero.getX()] = null;
			// Update hero y;
			hero.setCoordinate(new Coordinate2d(hero.getX() + x, hero.getY() + y));
			//	Show in next cell
			map.getCells()[hero.getY()][hero.getX()] = hero;
		}
	}

	/**	
	 * @brief Moves guard
	 * @parameter reverse Should reverse
	 * */
	/// @parameter 
	public void moveGuard(boolean reverse){
		//	BEFORE MOVING
		// Get new x and y;
		int x,y;
		x = guard.getMovePositions().get(guard.getMoveCounter()).getX();
		y = guard.getMovePositions().get(guard.getMoveCounter()).getY();

		// MOVE

		// Clean previous cell
		map.getCells()[guard.getY()][guard.getX()] = null;
		// Update guard position
		guard.setCoordinate(new Coordinate2d(x,y));
		//	Show in next cell
		map.getCells()[guard.getY()][guard.getX()] = guard;

		// AFTER MOVING
		// If is not reverse, add counter
		if (!reverse) {
			// Check if end of moves
			if(guard.getMoveCounter() == guard.getMovePositions().size()-1){
				//	Reset guard move counter
				guard.setMoveCounter(0);
			} else {
				//	Increase move counter
				guard.setMoveCounter(guard.getMoveCounter() + 1);
			}
		} 
		//	It is reversed, subtract counter
		else {
			// Check if the first move
			if(guard.getMoveCounter() == 0){
				//	Reset guard move counter to last
				guard.setMoveCounter(guard.getMovePositions().size()-1);
			} else {
				//	Decrease
				guard.setMoveCounter(guard.getMoveCounter() - 1);
			}
		}
	}

	public void moveOgre(){
		int i,x = 0,y = 0;

		// Get random option
		i = RandomService.getRandomInt(1, 4);

		//	Move up
		if (i == 1){
			y = -1;
		}
		//	Move down
		else if (i == 2){
			y = 1;
		}
		//	Move left
		else if (i == 3){
			x = -1;
		}
		//	Move right
		else if (i == 4){
			x = 1;
		}

		//	Check if moving to a null cell
		if (map.getCells()[getOgre().getY() + y][getOgre().getX() + x] == null ||
				map.getCells()[getOgre().getY() + y][getOgre().getX() + x] instanceof Key){
			// MOVE
			//			Check if moving out of a key
			if (getOgre().getY() == key.getY() && getOgre().getX() == key.getX()){
				// Change ogre to letter "$"
				ogre.setLetter("O");
				// Previous cell is key
				map.getCells()[ogre.getY()][ogre.getX()] = this.key;
			} else {
				// Clean previous cell
				map.getCells()[ogre.getY()][ogre.getX()] = null;
			}
			//	Check if moving to a key
			if (getOgre().getY() + y == key.getY() && getOgre().getX() + x == key.getX()){
				// Change ogre to letter "$"
				ogre.setLetter("$");
			}
			// Update guard position
			ogre.setCoordinate(new Coordinate2d(ogre.getX() + x, ogre.getY() + y));
			//	Show in next cell
			map.getCells()[ogre.getY()][ogre.getX()] = ogre;	
			//	Swing club
			if (ogre.getClub() != null){
				swingClub();
			}
		} else {
			//	Use recursively
			moveOgre();
		}
	}

	public void swingClub(){
		int i,x = 0,y = 0;

		// Get random option
		i = RandomService.getRandomInt(1, 4);

		//	Club in ->up
		if (i == 1){
			x = 1;
			y = -1;
		}
		//	Club in ->down
		else if (i == 2){
			x = 1;
			y = 1;
		}
		//	Club in <-up
		else if (i == 4){
			x = -1;
			y = -1;
		}
		//	Club in <-down
		else if (i == 3){
			x = -1;
			y = 1;
		}

		//	Check if moving to a null cell or a key
		if (map.getCells()[getOgre().getY() + y][getOgre().getX() + x] == null
				|| map.getCells()[getOgre().getY() + y][getOgre().getX() + x] instanceof Key){
			// MOVE
			// Check if moving out of a key
			if (getOgre().getClub().getY() == key.getY() && getOgre().getClub().getX() == key.getX()){
				// Change key to letter "*"
				ogre.getClub().setLetter("*");
				// Previous cell is key
				map.getCells()[ogre.getClub().getY()][ogre.getClub().getX()] = this.key;
			} else {
				// Clean previous cell
				map.getCells()[ogre.getClub().getY()][ogre.getClub().getX()] = null;
			}

			// Check if moving to a key
			if (getOgre().getY() + y == key.getY() && getOgre().getX() + x == key.getX()){
				// Change key to letter "$"
				ogre.getClub().setLetter("$");
			}

			// Update club position
			ogre.getClub().setCoordinate(new Coordinate2d(ogre.getX()+x, ogre.getY()+y));
			//	Show in next cell
			map.getCells()[ogre.getClub().getY()][ogre.getClub().getX()] = ogre.getClub();	
		} else {
			//	Calculate cell recursive
			swingClub();
		}
	}

	/** 
	 * Check if the hero is on a lever
	 * */
	public boolean isOnLever(){
		if (map.getCells()[hero.getY()][hero.getX()] instanceof Lever){
			return true;
		}
		return false;
	}

	public boolean isGuardNear(){
		// Check if the guard is above hero
		if (guard.getX() == hero.getX() && guard.getY() == hero.getY()-1){
			return true;
		}
		// Check if the guard is down
		if (guard.getX() == hero.getX() && guard.getY() == hero.getY()+1){
			return true;
		}
		// Check if the guard is on the right
		if (guard.getX() == hero.getX()+1 && guard.getY() == hero.getY()){
			return true;
		}
		//	Check if the guard is on the left
		if (guard.getX() == hero.getX()-1 && guard.getY() == hero.getY()){
			return true;
		}
		return false;
	}

	public boolean isOgreNear(){
		// Check if the guard is above hero
		if (ogre.getX() == hero.getX() && ogre.getY() == hero.getY()-1){
			return true;
		}
		// Check if the guard is down
		if (ogre.getX() == hero.getX() && ogre.getY() == hero.getY()+1){
			return true;
		}
		// Check if the guard is on the right
		if (ogre.getX() == hero.getX()+1 && ogre.getY() == hero.getY()){
			return true;
		}
		//	Check if the guard is on the left
		if (ogre.getX() == hero.getX()+1 && ogre.getY() == hero.getY()){
			return true;
		}
		return false;
	}

	public boolean isClubNear(){
		//	Check if ogre has club
		if (ogre.getClub() != null) {
			// Check if the club ->up
			if (ogre.getClub().getX() == hero.getX() +1 && ogre.getClub().getY() == hero.getY()-1){
				return true;
			}
			// Check if the club ->down
			if (ogre.getClub().getX() == hero.getX() +1&& ogre.getClub().getY() == hero.getY()+1){
				return true;
			}
			// Check if the club <-up
			if (ogre.getClub().getX() == hero.getX()-1 && ogre.getClub().getY() == hero.getY()-1){
				return true;
			}
			//	Check if the club <-down
			if (ogre.getClub().getX() == hero.getX()-1 && ogre.getClub().getY() == hero.getY()+1){
				return true;
			}
		}
		return false;
	}

	///	Create first level doors
	void createDoorsFirstLevel(){
		//	Create doors
		Door door1 = new Door(4, 1, false);
		Door door2 = new Door(4, 3, false);
		Door door3 = new Door(2, 3, false);
		Door door4 = new Door(0, 5, true);
		Door door5 = new Door(0, 6, true);
		Door door6 = new Door(2, 8, false);
		Door door7 = new Door(4, 8, false);
		//	Add to map
		map.addCell(door1);
		map.addCell(door2);
		map.addCell(door3);
		map.addCell(door4);
		map.addCell(door5);
		map.addCell(door6);
		map.addCell(door7);
	}

	/// Create doors second level
	void createDoorsSecondLevel(){
		Door door = new Door(0,1,true);
		map.addCell(door);
	}

	/// Create first level walls
	public void createWallsFirstLevel(){
		// Add first line
		map.addWall(0, 0);
		map.addWall(1, 0);
		map.addWall(2, 0);
		map.addWall(3, 0);
		map.addWall(4, 0);
		map.addWall(5, 0);
		map.addWall(6, 0);
		map.addWall(7, 0);
		map.addWall(8, 0);
		map.addWall(9, 0);
		//	Add second line
		map.addWall(0, 1);
		map.addWall(6, 1);
		map.addWall(9, 1);
		//	Add third line
		map.addWall(0, 2);
		map.addWall(1, 2);
		map.addWall(2, 2);
		map.addWall(4, 2);
		map.addWall(5, 2);
		map.addWall(6, 2);
		map.addWall(9, 2);
		//	Add fourth line
		map.addWall(0,3);
		map.addWall(6,3);
		map.addWall(9,3);
		//	Add fifth
		map.addWall(0, 4);
		map.addWall(1, 4);
		map.addWall(2, 4);
		map.addWall(4, 4);
		map.addWall(5, 4);
		map.addWall(6, 4);
		map.addWall(9, 4);
		//	Add six and Seven
		map.addWall(9, 5);
		map.addWall(9, 6);
		//	Add eight
		map.addWall(0, 7);
		map.addWall(1, 7);
		map.addWall(2, 7);
		map.addWall(4, 7);
		map.addWall(5, 7);
		map.addWall(6, 7);
		map.addWall(7, 7);
		map.addWall(9, 7);
		//	Add nine
		map.addWall(0, 8);
		map.addWall(5, 8);
		map.addWall(9, 8);
		// Add last line
		map.addWall(0, 9);
		map.addWall(1, 9);
		map.addWall(2, 9);
		map.addWall(3, 9);
		map.addWall(4, 9);
		map.addWall(5, 9);
		map.addWall(6, 9);
		map.addWall(7, 9);
		map.addWall(8, 9);
		map.addWall(9, 9);
	}

	public void createWallsSecondLevel(){
		int i;
		// Add first row walls
		for (i = 0; i < 10; i++){
			map.addWall(i, 0);

		}
		//	Right wall column
		for(i = 1; i < 10; i++){
			map.addWall(9, i);
		}
		//	Left wall column
		for(i = 2; i < 10; i++){
			map.addWall(0, i);
		}
		//	Last wall row
		for(i = 1; i < 9; i++){
			map.addWall(i, 9);
		}
	}

	/** 
	 * Prints game on screen
	 * */
	public void printGame(){
		System.out.println(map);
	}

	//	MARK: Getters and Setters
	/**
	 * @return the map
	 */
	public Map getMap() {
		return map;
	}
	/**
	 * @param map the map to set
	 */
	public void setMap(Map map) {
		this.map = map;
	}
	/**
	 * @return the hero
	 */
	public Hero getHero() {
		return hero;
	}
	/**
	 * @param hero the hero to set
	 */
	public void setHero(Hero hero) {
		this.hero = hero;
	}
	/**
	 * @return the guard
	 */
	public Guard getGuard() {
		return guard;
	}
	/**
	 * @param guard the guard to set
	 */
	public void setGuard(Guard guard) {
		this.guard = guard;
	}
	/**
	 * @return the guardMovesCounter
	 */
	public int getGuardMovesCounter() {
		return guardMovesCounter;
	}
	/**
	 * @param guardMovesCounter the guardMovesCounter to set
	 */
	public void setGuardMovesCounter(int guardMovesCounter) {
		this.guardMovesCounter = guardMovesCounter;
	}

	/**
	 * @return the state
	 */
	public GameState getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(GameState state) {
		this.state = state;
	}

	/**
	 * @return the lever
	 */
	public Lever getLever() {
		return lever;
	}

	/**
	 * @param lever the lever to set
	 */
	public void setLever(Lever lever) {
		this.lever = lever;
	}

	/**
	 * @return the ogre
	 */
	public Ogre getOgre() {
		return ogre;
	}

	/**
	 * @param ogre the ogre to set
	 */
	public void setOgre(Ogre ogre) {
		this.ogre = ogre;
	}

	/**
	 * @return the key
	 */
	public Key getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(Key key) {
		this.key = key;
	}
}
