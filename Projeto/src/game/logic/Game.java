package game.logic;

public class Game extends Object {
	private Map map;
	private Hero hero;
	private Guard guard;
	private int guardMovesCounter;
	private GameState state;
	private Lever lever;

	public Game(){
		setState(GameState.running);
		map = new Map(10,10);
		hero = new Hero(1,1);
		guard = new Guard(8,1);
		lever = new Lever(6,8);
		guardMovesCounter = 0;
	}

	/// Starts the game
	public void start(){
		//	 Create map layout
		//		Add walls to map
		createWalls();
		//	Add  doors to map
		createDoors();
		//	Add hero to map
		map.addHero(hero);
		//	Add lever to map
		map.addLever(lever);
		//	Add guard to map
		map.addGuard(guard);
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
		//		Move guard
		moveGuard();

		//		Check if moving to a guard
		if(isGuardNear() == true){
			// END GAME, GAME Over
			System.out.println("Game Over!");
		}
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
					return;
				} else {
					//	If it is open and exit, finish map
					if(door.isExit()){
						System.out.println("Game ended!");
						//ended = true;
						return;
					}
				}
			}

			//	Check if moving to a lever
			if(map.getCells()[hero.getY()+y][hero.getX()+x] instanceof Lever){
				System.out.println("Lever pressed!");
				map.openAllDoors();
			}

			// MOVE

			// Clean previous cell
			map.getCells()[hero.getY()][hero.getX()] = null;
			// Update hero y;
			hero.setX(hero.getX() + x);
			hero.setY(hero.getY() + y);;
			//	Show in next cell
			map.getCells()[hero.getY()][hero.getX()] = hero;
		}
	}

	public void moveGuard(){
		//		BEFORE MOVING
		// Check if out of bounds
		int x = 0,y = 0;

		if(guard.getMoveCounter() == 0){
			// MOVE LEFT
			y = 0;
			x = -1;
		}
		else if (guard.getMoveCounter() < 5){
			// MOVE DOWN
			y = +1;
			x = 0;
		} 
		else if (guard.getMoveCounter() < 11){
			// MOVE LEFT
			y = 0;
			x = -1;
		}
		else if (guard.getMoveCounter() == 11){
			// MOVE DOWN
			y = 1;
			x = 0;
		}
		else if (guard.getMoveCounter() < 19){
			// MOVE RIGHT
			y = 0;
			x = 1;
		}
		else if(guard.getMoveCounter() < 24) {
			//	MOVE UP
			y = -1;
			x = 0;
		}

		// MOVE

		// Clean previous cell
		map.getCells()[guard.getY()][guard.getX()] = null;
		// Update guard position
		guard.setX(guard.getX()+x);
		guard.setY(guard.getY()+y);
		//	Show in next cell
		map.getCells()[guard.getY()][guard.getX()] = guard;

		// Check if original cell
		if(guard.getMoveCounter() == 24){
			//	RESET GUARD COUNTER
			guard.setMoveCounter(0);
		} else {
			guard.setMoveCounter(guard.getMoveCounter() + 1);
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
		if (guard.getX() == hero.getX()+1 && guard.getY() == hero.getY()){
			return true;
		}

		return false;
	}

	///	Create doors
	void createDoors(){
		map.addDoor(4, 1, false);
		map.addDoor(4, 3, false);
		map.addDoor(2, 3, false);
		map.addDoor(0, 5, true);
		map.addDoor(0, 6, true);
		map.addDoor(2, 8, false);
		map.addDoor(4, 8, false);
	}

	/// Adds walls
	public void createWalls(){
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
}
