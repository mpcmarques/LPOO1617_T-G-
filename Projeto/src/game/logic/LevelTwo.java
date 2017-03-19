package game.logic;

public class LevelTwo extends GameMap {
	private Key key;

	public LevelTwo() {
		//	Start new map
		super(new Map(10,10));
		// TODO Auto-generated constructor stub
		startSecondLevel();
	}

	public void heroDidMove(){
		//	Move ogres
		moveOgres();

		//	Call super
		super.heroDidMove();
	}

	public void completed(){
		super.completed();

		//	Finish game
		Game.instance.gameCompleted();
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
		//	Sort number of ogres to add
		int num = RandomService.getRandomInt(1, 3);
		for(int i = 0; i < num; i++){
			Ogre ogre = new Ogre(4,1, true);
			addOgre(ogre);
			//	Add ogre to map
			getMap().addCell(ogre);
			swingClub(ogre);
		}
		//	Add club 
		getMap().addCell(new Club(2,8));
	}

	//	Move all ogres
	public void moveOgres(){
		for (Ogre ogre: getOgres()){
			//	If ogre is stunned, decrease stun counter
			if (ogre.isStunned()) {
				ogre.decreaseStunCounter();
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
}
