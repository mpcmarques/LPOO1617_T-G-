package game.logic;

public class LevelTwo extends GameMap {
	private Key key;
	private Ogre ogre;
	
	public LevelTwo() {
		//	Start new map
		super(new Map(10,10));
		// TODO Auto-generated constructor stub
		startSecondLevel();
	}
	
	public void updateGame(String typed){
		//	Update superclass -> makes hero walk
		super.updateGame(typed);

		//	Level logic
		secondLevelLogic();
	}
	
	public void completed(){
		super.completed();
		
		//
		Game.instance.finishGame();
	}

	///	Finishes first level and starts second level
	public void startSecondLevel(){
		//	Create map layout
		//	Add walls to map
		createWallsSecondLevel();
		//	Add  doors to map
		createDoorsSecondLevel();
		//	Change hero coordinates
		setHero(new Hero(1,8));
		//	Add hero to map
		getMap().addCell(getHero());
		//	Create new key
		this.key = new Key(8,1);
		//	Add key to map
		getMap().addCell(key);
		//	Add ogre to map
		ogre = new Ogre(4,1);
		//	Add ogre weapon
		ogre.addClub();
		//	Add ogre to map
		getMap().addCell(ogre);
	}

	/// Second level logic
	public void secondLevelLogic(){
		//	Move ogre
		moveOgre();
		//	Check if moved next to a ogre or a ogre bash
		if(isOgreNear() == true || isClubNear() == true){
			// END GAME, GAME Over
			System.out.println("Game Over!");
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
		if (getMap().getCells()[getOgre().getY() + y][getOgre().getX() + x] == null ||
				getMap().getCells()[getOgre().getY() + y][getOgre().getX() + x] instanceof Key){
			// MOVE
			//			Check if moving out of a key
			if (getOgre().getY() == key.getY() && getOgre().getX() == key.getX()){
				// Change ogre to letter "$"
				ogre.setLetter("O");
				// Previous cell is key
				getMap().getCells()[ogre.getY()][ogre.getX()] = this.key;
			} else {
				// Clean previous cell
				getMap().getCells()[ogre.getY()][ogre.getX()] = null;
			}
			//	Check if moving to a key
			if (getOgre().getY() + y == key.getY() && getOgre().getX() + x == key.getX()){
				// Change ogre to letter "$"
				ogre.setLetter("$");
			}
			// Update guard position
			ogre.setCoordinate(new Coordinate2d(ogre.getX() + x, ogre.getY() + y));
			//	Show in next cell
			getMap().getCells()[ogre.getY()][ogre.getX()] = ogre;	
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
		if (getMap().getCells()[getOgre().getY() + y][getOgre().getX() + x] == null
				|| getMap().getCells()[getOgre().getY() + y][getOgre().getX() + x] instanceof Key){
			// MOVE
			// Check if moving out of a key
			if (getOgre().getClub().getY() == key.getY() && getOgre().getClub().getX() == key.getX()){
				// Change key to letter "*"
				ogre.getClub().setLetter("*");
				// Previous cell is key
				getMap().getCells()[ogre.getClub().getY()][ogre.getClub().getX()] = this.key;
			} else {
				// Clean previous cell
				getMap().getCells()[ogre.getClub().getY()][ogre.getClub().getX()] = null;
			}

			// Check if moving to a key
			if (getOgre().getY() + y == key.getY() && getOgre().getX() + x == key.getX()){
				// Change key to letter "$"
				ogre.getClub().setLetter("$");
			}

			// Update club position
			ogre.getClub().setCoordinate(new Coordinate2d(ogre.getX()+x, ogre.getY()+y));
			//	Show in next cell
			getMap().getCells()[ogre.getClub().getY()][ogre.getClub().getX()] = ogre.getClub();	
		} else {
			//	Calculate cell recursive
			swingClub();
		}
	}

	public boolean isOgreNear(){
		// Check if the guard is above hero
		if (ogre.getX() == getHero().getX() && ogre.getY() == getHero().getY()-1){
			return true;
		}
		// Check if the guard is down
		if (ogre.getX() == getHero().getX() && ogre.getY() == getHero().getY()+1){
			return true;
		}
		// Check if the guard is on the right
		if (ogre.getX() == getHero().getX()+1 && ogre.getY() == getHero().getY()){
			return true;
		}
		//	Check if the guard is on the left
		if (ogre.getX() == getHero().getX()+1 && ogre.getY() == getHero().getY()){
			return true;
		}
		return false;
	}

	public boolean isClubNear(){
		//	Check if ogre has club
		if (ogre.getClub() != null) {
			// Check if the club ->up
			if (ogre.getClub().getX() == getHero().getX() +1 && ogre.getClub().getY() == getHero().getY()-1){
				return true;
			}
			// Check if the club ->down
			if (ogre.getClub().getX() == getHero().getX() +1&& ogre.getClub().getY() == getHero().getY()+1){
				return true;
			}
			// Check if the club <-up
			if (ogre.getClub().getX() == getHero().getX()-1 && ogre.getClub().getY() == getHero().getY()-1){
				return true;
			}
			//	Check if the club <-down
			if (ogre.getClub().getX() == getHero().getX()-1 && ogre.getClub().getY() == getHero().getY()+1){
				return true;
			}
		}
		return false;
	}

	/// Create doors second level
	public void createDoorsSecondLevel(){
		Door door = new Door(0,1,true);
		getMap().addCell(door);
	}

	public void createWallsSecondLevel(){
		int i;
		// Add first row walls
		for (i = 0; i < 10; i++){
			getMap().addWall(i, 0);
		}
		//	Right wall column
		for(i = 1; i < 10; i++){
			getMap().addWall(9, i);
		}
		//	Left wall column
		for(i = 2; i < 10; i++){
			getMap().addWall(0, i);
		}
		//	Last wall row
		for(i = 1; i < 9; i++){
			getMap().addWall(i, 9);
		}
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
}
