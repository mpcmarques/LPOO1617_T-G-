package game.logic;

public class GameMap extends Object implements GameMapDelegate {
	private Map map;
	private Hero hero;
	private Guard guard;
	private Lever lever;

	/**
	 * @brief Constructor 
	 * */
	public GameMap(Map map){
		this.setMap(map);
	}

	public GameMap(char[][] map){
		int col, row;

		// Create new map
		this.setMap(new Map(map[0].length, map.length));

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
				case 'G':
					//	Set guard
					setGuard(new Guard(col,row));
					//	Add cell to map
					this.map.addCell(getGuard());
					break;
					//	Ogre
				case 'O':
					break;
					// Lever / Key
				case 'k':
					//	Add lever
					this.map.addCell(new Lever(col,row));
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
	 * @brief Player finished game map
	 * */
	public void completed(){}
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

		// Game over check
		//	Check if moved next to a guard
		if(isGuardNear() == true){
			//	Check if guard is not a drunken sleeping
			if (getGuard() instanceof Drunken) {
				Drunken drunkGuard = (Drunken)getGuard();
				if (drunkGuard.isSleeping()){
					//	If guard is sleeping, doesnt end game.
					return;
				}
			} 
			// END GAME, GAME Over
			Game.instance.setState(GameState.over);
			Game.instance.setEndStatus(EndStatus.DEFEAT);
		}
	}

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
				// If door is closed, open and remove key from hero
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

			// MOVE
			// Clean previous cell
			map.getCells()[hero.getY()][hero.getX()] = null;
			// Update hero y;
			hero.setCoordinate(new Coordinate2d(hero.getX() + x, hero.getY() + y));
			//	Show in next cell
			map.getCells()[hero.getY()][hero.getX()] = hero;
		}
	}

	//	Open all doors
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
	
	public boolean isGuardNear(){
		if(getGuard() == null) return false;
		
		// Check if the guard is above hero
		if (getGuard().getX() == getHero().getX() && getGuard().getY() == getHero().getY()-1){
			return true;
		}
		// Check if the guard is down
		if (getGuard().getX() == getHero().getX() && getGuard().getY() == getHero().getY()+1){
			return true;
		}
		// Check if the guard is on the right
		if (getGuard().getX() == getHero().getX()+1 && getGuard().getY() == getHero().getY()){
			return true;
		}
		//	Check if the guard is on the left
		if (getGuard().getX() == getHero().getX()-1 && getGuard().getY() == getHero().getY()){
			return true;
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
