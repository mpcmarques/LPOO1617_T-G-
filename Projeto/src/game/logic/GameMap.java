package game.logic;

import java.util.ArrayList;

public class GameMap extends Object {
	private Map map;
	private Hero hero;
	private Guard guard;
	private Lever lever;
	private boolean isCompleted;
	private ArrayList<Ogre> ogres;
	private Key key;

	/**
	 * @brief Constructor a
	 * */
	public GameMap(Map map){
		this.setMap(map);
		this.setCompleted(false);
		this.setOgres(new ArrayList<Ogre>());
	}

	public GameMap(char[][] map){
		int col, row;

		// Create new map
		this.setMap(new Map(map[0].length, map.length));
		this.setCompleted(false);
		this.setOgres(new ArrayList<Ogre>());

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
	 * @brief Player finished game map, need to call super first
	 * */
	public void completed(){
		//	Set completed
		this.setCompleted(true);
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

		//	Hero will move
		heroDidMove();
	}

	public void heroDidMove(){
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
			Game.instance.gameOver();
		}

		//	If moved next to an ogre and hero doesnt have weapon, game over
		Ogre nearOgre = isOgreNear(getHero().getX(), getHero().getY());
		if(nearOgre != null){
			//	If hero has weapon, stun ogre, else, lose game
			if(getHero().hasWeapon()) {
				nearOgre.setStunned(true);
			} else {
				// END GAME, GAME Over
				Game.instance.gameOver();
				return;
			}
		}
		//	If moved adjacent to a ogre club, game is over
		if(isOgreClubNear() == true) {
			// END GAME, GAME Over
			Game.instance.gameOver();
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
			//	Check if moving to a club
			if(map.getCells()[hero.getY()+y][hero.getX()+x] instanceof Club){
				Club club = (Club)map.getCells()[hero.getY()+y][hero.getX()+x];
				//	Get club
				hero.setClub(club);
			}
			//	Check if moving near a ogre
			Ogre ogrenear = isOgreNear(hero.getX()+x, hero.getY()+y);
			if(ogrenear != null){
				//	Stun ogre
				ogrenear.setStunned(true);
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
	//	Check if ogre is adjacent
	public Ogre isOgreNear(int x, int y){
		for(Ogre ogre: getOgres()){
			if (ogre != null){
				// Check if the guard is above hero
				if (ogre.getX() == x && ogre.getY() == y-1){
					return ogre;
				}
				// Check if the guard is down
				if (ogre.getX() == x && ogre.getY() == y+1){
					return ogre;
				}
				// Check if the guard is on the right
				if (ogre.getX() == x+1 && ogre.getY() == y){
					return ogre;
				}
				//	Check if the guard is on the left
				if (ogre.getX() == x-1 && ogre.getY() == y){
					return ogre;
				}
			}
		}
		return null;
	}

	public boolean isOgreClubNear(){
		// Check if ogre is no null
		for(Ogre ogre: ogres){
			if(ogre != null) {
				//	Check if ogre has club
				if (ogre.getClub() != null) {
					// Check if the club up
					if (ogre.getClub().getX() == getHero().getX()  && ogre.getClub().getY() == getHero().getY()-1){
						return true;
					}
					// Check if the club down
					if (ogre.getClub().getX() == getHero().getX() && ogre.getClub().getY() == getHero().getY()+1){
						return true;
					}
					// Check if the club <-
					if (ogre.getClub().getX() == getHero().getX()-1 && ogre.getClub().getY() == getHero().getY()){
						return true;
					}
					//	Check if the club ->
					if (ogre.getClub().getX() == getHero().getX()+1 && ogre.getClub().getY() == getHero().getY()){
						return true;
					}
				}
			}
		}
		return false;
	}


	public void addOgre(Ogre ogre){
		ogres.add(ogre);
	}
	//	Move all ogres
	public void moveOgres(){
		for (Ogre ogre: getOgres()){
			//	If ogre is stunned, he doenst move, but swing bat
			if (ogre.isStunned()) {
				//	Decrease stun counter
				ogre.decreaseStunCounter();
				//	Swing club
				if (ogre.getClub() != null){
					swingClub(ogre);
				}
			} else {
				moveOgre(ogre);
			}
		}
	}
	//	Move a single ogre
	public void moveOgre(Ogre ogre){
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

		//	Check if not moving to a wall or a door
		if (getMap().getCells()[ogre.getY() + y][ogre.getX() + x] == null ||
				getMap().getCells()[ogre.getY() + y][ogre.getX() + x] instanceof Key ||
				getMap().getCells()[ogre.getY() + y][ogre.getX() + x] instanceof Ogre ||
				getMap().getCells()[ogre.getY() + y][ogre.getX() + x] instanceof Club
				){
			//	BEFORE MOVING
			//	If moving out of a key, change ogre letter to "O"
			if (ogre.getY() == key.getY() && ogre.getX() == key.getX()){
				// Change ogre to letter "$"
				ogre.setLetter("O");
				// Previous cell is key
				getMap().getCells()[ogre.getY()][ogre.getX()] = this.key;
			} else {
				// Clean previous cell
				getMap().getCells()[ogre.getY()][ogre.getX()] = null;
			}
			//	Check if moving to a key
			if (ogre.getY() + y == key.getY() && ogre.getX() + x == key.getX()){
				// Change ogre to letter "$"
				ogre.setLetter("$");
			}

			//	MOVE
			// Update ogre position
			ogre.setCoordinate(new Coordinate2d(ogre.getX() + x, ogre.getY() + y));
			//	Show in next cell
			getMap().getCells()[ogre.getY()][ogre.getX()] = ogre;	

			//	Swing club
			if (ogre.getClub() != null){
				swingClub(ogre);
			}
		} else {
			//	Use recursively
			moveOgre(ogre);
		}
	}

	public void swingClub(Ogre ogre){
		int i,x = 0,y = 0;

		// Get random option
		i = RandomService.getRandomInt(1, 4);

		//	Club up
		if (i == 1){
			y = -1;
		}
		//	Club down
		else if (i == 2){
			y = 1;
		}
		//	Club <-
		else if (i == 4){
			x = -1;
		}
		//	Club ->
		else if (i == 3){
			x = 1;
		}

		//	Check if moving to a null cell or a key
		if (getMap().getCells()[ogre.getY() + y][ogre.getX() + x] == null
				|| getMap().getCells()[ogre.getY() + y][ogre.getX() + x] instanceof Key 
				|| getMap().getCells()[ogre.getY() + y][ogre.getX() + x] instanceof Club
				){
			// MOVE
			// Check if moving out of a key
			if (ogre.getClub().getY() == key.getY() && ogre.getClub().getX() == key.getX()){
				// Change key to letter "*"
				ogre.getClub().setLetter("*");
				// Previous cell is key
				getMap().getCells()[ogre.getClub().getY()][ogre.getClub().getX()] = this.key;
			} else {
				// Clean previous cell if it is not a ogre (weapon is on ogre when added)
				if(getMap().getCells()[ogre.getClub().getY()][ogre.getClub().getX()] instanceof Ogre == false){
					getMap().getCells()[ogre.getClub().getY()][ogre.getClub().getX()] = null;
				}
			}

			// Check if moving to a key
			if (ogre.getY() + y == key.getY() && ogre.getX() + x == key.getX()){
				// Change key to letter "$"
				ogre.getClub().setLetter("$");
			}

			// Update club position
			ogre.getClub().setCoordinate(new Coordinate2d(ogre.getX()+x, ogre.getY()+y));
			//	Show in next cell
			getMap().getCells()[ogre.getClub().getY()][ogre.getClub().getX()] = ogre.getClub();	
		} else {
			//	Calculate cell recursive
			swingClub(ogre);
		}
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

	/**
	 * @return the ogres
	 */
	public ArrayList<Ogre> getOgres() {
		return ogres;
	}

	/**
	 * @param ogres the ogres to set
	 */
	public void setOgres(ArrayList<Ogre> ogres) {
		this.ogres = ogres;
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
