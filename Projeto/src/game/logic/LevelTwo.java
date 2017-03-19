package game.logic;

public class LevelTwo extends GameMap {
	private boolean isOgreAllowedToMove;
	
	public LevelTwo() {
		//	Start new map
		super(new Map(10,10));
		setOgreAllowedToMove(false);
		// Set configurations
		startSecondLevel();
	}
	
	public LevelTwo(char[][] map){
		//	Start new map
		super(map);
		//	Ogre can't move
		setOgreAllowedToMove(false);
	}

	public void heroDidMove(){
		//	Move ogres
		if (isOgreAllowedToMove()){
			moveOgres();
		}

		//	Call super
		super.heroDidMove();
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
		setKey(new Key(8,1));
		//	Add key to map
		getMap().addCell(getKey());

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
		//	Ogres are allowed to move
		setOgreAllowedToMove(true);
		
		//	Add club 
		getMap().addCell(new Club(2,8));
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
}
