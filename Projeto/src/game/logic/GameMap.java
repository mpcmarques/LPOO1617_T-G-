package game.logic;

public class GameMap extends Object {
	private Map map;
	private Hero hero;
	private boolean isCompleted;

	/**
	 * @brief Constructor
	 * */
	public GameMap(char[][] map){
		int col, row;

		// Create new map
		this.setMap(new Map(map[0].length, map.length));
		this.setCompleted(false);

		//	Iterate through matrix
		for(row = 0; row < map.length; row++){
			for(col = 0; col < map[row].length; col++){
				char value = map[row][col];
				//	Get cell value
				switch(value){
				//	Hero
				case 'H':
					//	Set hero
					setHero(new Hero(col,row));
					//	Add cell to map
					this.map.addCell(this.hero);
					break;
					//	Wall
				case 'X':
					//	Add wall
					this.map.addWall(col, row);
					break;
					//	Guard
				case 'l':
					//	Add lever
					this.map.addCell(new Lever(col,row));
					break;
				case 'k':
					//	Add key
					this.map.addCell(new Key(col,row));
					break;
					// Door
				case 'I':
					//	Add door
					this.map.addCell(new Door(col,row, true));
					break;
				default:
					break;
				}
			}
		}
	}
	/** 
	 * @brief Player finished game map, call game level completion handler
	 * */
	public void completed(){
		//	Set completed
		this.setCompleted(true);
		//	Call game level completion handler
		Game.instance.levelCompleted();
	}

	/** 
	 * @brief Player pressed a lever, opens all doors
	 * */
	public void pressedLever(){
		this.map.openAllDoors();
	}
	/** 
	 * @brief Updates game, need to call the super method to walk hero in subclasses.
	 * */
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
		default:
			break;
		}

		//	Hero moved
		heroDidMove();
	}

	/** 
	 * Game over check, method is called when after the hero moves
	 * */
	public void heroDidMove(){
		// Game over check
	}

	/** 
	 * Hero will move, method is called before hero moves, before hero move logic
	 * @return boolean Hero can make this move
	 * */
	public boolean heroWillMove(int x, int y){
		//		Check if moving to a wall
		if(map.getCells()[hero.getY()+y][hero.getX()+x] instanceof Wall ||
				map.getCells()[hero.getY()+y][hero.getX()+x] instanceof Ogre ||
				map.getCells()[hero.getY()+y][hero.getX()+x] instanceof Guard){
			//	Hero can't move
			return false;
		}
		
		//		Check if moving to door
		if(map.getCells()[hero.getY()+y][hero.getX()+x] instanceof Door){
			Door door = (Door) map.getCells()[hero.getY()+y][hero.getX()+x];
			// If door is closed, open and remove key from hero
			if (door.isOpen() == false){
				//	Check if has key
				if (hero.hasKey()){
					//	Open door
					door.openDoor();
					//	Remove key from hero
					hero.setHaveKey(false);
				}
				// 	Return false, hero cant move
				return false;
			} else {
				//	If the door is open and is a exit, complete map
				if(door.isExit()){
					//	Complete level
					this.completed();
				}
			}
		}
		//	Check if moving to a lever
		if(map.getCells()[hero.getY()+y][hero.getX()+x] instanceof Lever){
			this.pressedLever();
		}
		//	Check if moving to a key
		if(map.getCells()[hero.getY()+y][hero.getX()+x] instanceof Key){
			//	Get key
			hero.setHaveKey(true);
		}
		//	Check if moving to a club
		if(map.getCells()[hero.getY()+y][hero.getX()+x] instanceof Club){
			Club club = (Club)map.getCells()[hero.getY()+y][hero.getX()+x];
			//	Get club
			hero.setClub(club);
		}

		return true;
	}

	public void moveHero(int x, int y){
		//	BEFORE MOVING
		// Check if out of bounds
		if (hero.getX() + x >= 0 
				&& hero.getX() + x <= map.getNumberCellsForLine()
				&& hero.getY() + y >= 0 
				&& hero.getY() + y <= map.getNumberLines()){

			if(heroWillMove(x,y)) {
				// MOVE
				// Clean previous cell
				map.getCells()[hero.getY()][hero.getX()] = null;
				// Update hero y;
				hero.setCoordinate(new Coordinate2d(hero.getX() + x, hero.getY() + y));
				//	Show in next cell
				map.getCells()[hero.getY()][hero.getX()] = hero;
			}
		}
	}

	/** 
	 * @brief Open all doors 
	 * */
	public void openAllDoors(){
		int i,j;
		//	Loop through cells
		for(i = 0; i < map.getNumberLines(); i++){
			for(j = 0; j < map.getNumberCellsForLine(); j++){
				//	Check if it is a door
				if(map.getCells()[i][j] instanceof Door){
					//	Open door
					Door door = (Door)this.map.getCells()[i][j];
					door.openDoor();
				}
			}
		}
	}

	/** 
	 * @brief Returns true if there is a open exit door
	 * */
	public boolean isExitDoorsOpen(){
		int i,j;
		//	Loop through cells
		for(i = 0; i < map.getNumberLines(); i++){
			for(j = 0; j < map.getNumberCellsForLine(); j++){
				//	Check if it is a door
				if(map.getCells()[i][j] instanceof Door){
					//	Check if door is open
					Door door = (Door)this.map.getCells()[i][j];
					//	Return true if exit door is open
					if (door.isExit() == true && door.isOpen()) return true;
				}
			}
		}
		return false;
	}


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
	 * @return the completed
	 */
	public boolean isCompleted() {
		return isCompleted;
	}

	/**
	 * @param completed the completed to set
	 */
	public void setCompleted(boolean completed) {
		this.isCompleted = completed;
	}
}
