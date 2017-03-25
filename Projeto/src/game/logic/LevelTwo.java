package game.logic;

import java.util.ArrayList;

import game.services.RandomService;

public class LevelTwo extends GameMap {
	private boolean isOgreAllowedToMove;
	private ArrayList<Ogre> ogres;
	private Key key;

	/** Constructor */
	public LevelTwo(char[][] map, boolean isOgreAllowedToMove, int numberOfOgres){
		//	Start new map
		super(map);
		//	New ogres array
		this.setOgres(new ArrayList<Ogre>());

		//	Set ogre movement 
		setOgreAllowedToMove(isOgreAllowedToMove);

		//	Create ogres and objects
		this.populateMapWithObjects(map, numberOfOgres);

		//	If one ogre, just show a club if he has one, otherwise move all ogres
		if (isOgreAllowedToMove){
			if (this.getOgres().size() == 1){
				if (getOgres().get(0).getClub() != null){
					swingClub(getOgres().get(0));
				}
			} else {
				moveOgres();
			}
		}
	}

	/** 
	 * Populates gameMap with objects
	 * */
	private void populateMapWithObjects(char[][] map, int numberOfOgres){
		//		Get ogres from map
		int row, col;
		for(row = 0; row < map.length; row++){
			for(col = 0; col < map[row].length; col++){
				char value = map[col][row];
				if (value == 'O'){
					//	 Add ogre
					int i;
					for(i = 0; i < numberOfOgres; i++){
						Ogre ogre = new Ogre(row,col,isOgreAllowedToMove);
						addOgre(ogre);
						//	Add ogre to map
						this.addCell(ogre);
					}
				} else if (value == 'k'){
					//	Add key
					Key key = new Key(row,col);
					setKey(key);
					this.addCell(key);
				} else if (value == '*'){
					//	Add club
					Club club = new Club(row,col);
					this.addCell(club);
				}
			}
		}
	}

	/** 
	 * Before hero moves logic
	 * */
	public boolean heroWillMove(int x, int y){
		//	Check if moving near a ogre
		Ogre ogrenear = isOgreNear(getHero().getX()+x, getHero().getY()+y);
		if(ogrenear != null){
			//	Stun ogre
			ogrenear.setStunned(true);
		}
		//	Check if moving to a key
		if(getMap().getCells()[getHero().getY()+y][getHero().getX()+x] instanceof Key){
			//	Get key
			getHero().setHaveKey(true);
			//	Remove key from map
			//	Check if moving to a key
			if(getMap().getCells()[getHero().getY()+y][getHero().getX()+x] instanceof Key){
				//	Get key
				getHero().setHaveKey(true);
				//	Remove key from map
				this.setKey(null);
			}
		}

		return super.heroWillMove(x, y);
	}

	/** 
	 * After hero moves logic
	 * */
	public void heroDidMove(){
		// Call super
		super.heroDidMove();

		//	Move ogres
		if (isOgreAllowedToMove()){
			moveOgres();
		}

		//		If moved next to an ogre and hero doesnt have weapon, game over
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

	/** 
	 * @return boolean True if club is adjacent to hero.
	 * */
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
					else if (ogre.getClub().getX() == getHero().getX() && ogre.getClub().getY() == getHero().getY()+1){
						return true;
					}
					// Check if the club <-
					else if (ogre.getClub().getX() == getHero().getX()-1 && ogre.getClub().getY() == getHero().getY()){
						return true;
					}
					//	Check if the club ->
					else if (ogre.getClub().getX() == getHero().getX()+1 && ogre.getClub().getY() == getHero().getY()){
						return true;
					}
				}
			}
		}
		return false;
	}


	/** 
	 * @brief Adds an ogre to level two
	 * */
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
		if ((ogre.getX() + x >= 0 
				&& ogre.getX() + x <= getMap().getNumberCellsForLine()
				&& ogre.getY() + y >= 0 
				&& ogre.getY() + y <= getMap().getNumberLines())
				&&
				//	Check if not moving to a wall or a door
				(getMap().getCells()[ogre.getY() + y][ogre.getX() + x] == null ||
				getMap().getCells()[ogre.getY() + y][ogre.getX() + x] instanceof Key ||
				getMap().getCells()[ogre.getY() + y][ogre.getX() + x] instanceof Ogre ||
				getMap().getCells()[ogre.getY() + y][ogre.getX() + x] instanceof Club)
				){
			//	BEFORE MOVING
			//	If moving out of a key, change ogre letter to "O"
			if (this.key != null && (ogre.getY() == key.getY() && ogre.getX() == key.getX())){
				// Change ogre to letter "$"
				ogre.setLetter("O");
				// Previous cell is key
				getMap().getCells()[ogre.getY()][ogre.getX()] = this.key;
			} else {
				// Clean previous cell
				getMap().getCells()[ogre.getY()][ogre.getX()] = null;
			}
			//	Check if moving to a key
			if (this.key != null && (ogre.getY() + y == key.getY() && ogre.getX() + x == key.getX())){
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

		if ((ogre.getX() + x >= 0 
				&& ogre.getX() + x <= getMap().getNumberCellsForLine()
				&& ogre.getY() + y >= 0 
				&& ogre.getY() + y <= getMap().getNumberLines())
				&&
				//	Check if not moving to a wall or a door
				(getMap().getCells()[ogre.getY() + y][ogre.getX() + x] == null ||
				getMap().getCells()[ogre.getY() + y][ogre.getX() + x] instanceof Key ||
				getMap().getCells()[ogre.getY() + y][ogre.getX() + x] instanceof Ogre ||
				getMap().getCells()[ogre.getY() + y][ogre.getX() + x] instanceof Club)
				){
			// MOVE
			// Check if moving out of a key
			if (this.key != null && (ogre.getClub().getY() == key.getY() && ogre.getClub().getX() == key.getX())){
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
			if (this.key != null && (ogre.getY() + y == key.getY() && ogre.getX() + x == key.getX())){
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
	 * Check if ogre is adjacent to x and y position
	 * */
	public Ogre isOgreNear(int x, int y){
		for(Ogre ogre: getOgres()){
			if (ogre != null){
				// Check if the guard is above hero
				if (ogre.getX() == x && ogre.getY() == y-1){
					return ogre;
				}
				// Check if the guard is down
				else if (ogre.getX() == x && ogre.getY() == y+1){
					return ogre;
				}
				// Check if the guard is on the right
				else if (ogre.getX() == x+1 && ogre.getY() == y){
					return ogre;
				}
				//	Check if the guard is on the left
				else if (ogre.getX() == x-1 && ogre.getY() == y){
					return ogre;
				}
			}
		}
		return null;
	}

	/**
	 * @return the isOgreAllowedToMove
	 */
	public boolean isOgreAllowedToMove() {
		return isOgreAllowedToMove;
	}

	/**
	 * @param isOgreAllowedToMove the isOgreAllowedToMove to set
	 */
	public void setOgreAllowedToMove(boolean isOgreAllowedToMove) {
		this.isOgreAllowedToMove = isOgreAllowedToMove;
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
