package game.logic;

import java.util.ArrayList;
/** 
 * Class represents a GameMap
 * */
public class GameMap extends Object {
	private boolean isCompleted;
	private ArrayList<ArrayList<GameObject>> elements;
	private ArrayList<Ogre> ogres;
	private boolean canGuardsAndOgresMove;

	//	Objects
	private Hero hero;
	private Guard guard;

	/**
	 * Constructor create a GameMap based on a map char matrix
	 * @param map Map to be created
	 * */
	public GameMap(char[][] map){
		this.setCompleted(false);
		this.ogres = new ArrayList<Ogre>();
		this.canGuardsAndOgresMove = true;

		//	Populates map with objects
		populateMapWithObjects(map);
	}

	/** 
	 * Imports map
	 * @param map Map be imported
	 * */
	private void populateMapWithObjects(char[][] map){
		int col, row;
		//	Create new matrix
		this.elements = new ArrayList<ArrayList<GameObject>>();

		//	Create lines
		for(row = 0; row < map.length; row++){
			ArrayList<GameObject> line = new ArrayList<GameObject>();

			//	Create rows
			for(col = 0; col < map[row].length; col++){
				char value = map[row][col];
				//	Append object to line
				line.add(elementForKey(value, col, row));
			}

			//	Add line to map
			elements.add(line);
		}
	}

	/** 
	 * Adds a line to the map
	 * */
	public void addLine(){
		ArrayList<GameObject> line = new ArrayList<GameObject>();
		int i;
		//	Add cells
		for(i=0; i < this.getNumberOfRows(); i++){
			line.add(null);
		}
		//	Add line to map
		elements.add(line);
	}

	/** 
	 * Adds a row to the map
	 * */
	public void addRow(){
		int i;
		//	for each row add a cell
		for(i = 0; i < this.getNumberLines(); i++){
			elements.get(i).add(null);
		}
	}

	/** 
	 * Returns the element of a letter
	 * @return GameObject of that letter representation
	 * @param x GameObject x position
	 * @param y GameObject y position
	 * @param value GameObject letter representation
	 * */
	private GameObject elementForKey(char value, int x, int y){
		//	Get cell value
		switch(value){
		//	Hero
		case 'H':
			//	Return hero
			//	Add hero
			setHero(new Hero(x,y));
			return getHero();
			//	Wall
		case 'X':
			//	Add wall
			return new Wall(x,y);
			//	Guard
		case 'l':
			//	Add lever
			return new Lever(x,y);
		case 'k':
			//	Add key
			return new Key(x,y);
			// Door
		case 'I':
			//	Add door
			return new Door(x,y);
		case 'G':
			//	Add guard
			setGuard(new Rookie(x,y));
			return this.guard;
		case 'O':
			//	Add ogre
			Ogre ogre = new Ogre(x,y, false);
			addOgre(ogre);
			return ogre;
		case '*':
			//	Add club
			return new Club(x,y);
		default:
			//	Add empty cell
			return null;
		}
	}

	/** 
	 * Adds an ogre
	 * @param ogre Ogre to be added
	 * */
	public void addOgre(Ogre ogre){
		ogres.add(ogre);
	}
	
	/** 
	 * Check if game map is playable
	 * @return True if game map is playable
	 * */
	public boolean isPlayable(){
		return ((this.getHero() != null) && hasDoor() && hasKeyOrLever());
	}
	
	/**
	 * Check if there is a door in the map
	 * @return True if map has a door
	 */
	public boolean hasDoor(){
		for (ArrayList<GameObject> line: elements){
			for (GameObject obj: line){
				if (obj instanceof Door){
					return true;
				}
			}
		}
		return false;
	}
	
	/** 
	 * Check if there is a key or a lever in the map
	 * @return True if there is a key or a lever in the map
	 * */
	public boolean hasKeyOrLever(){
		for (ArrayList<GameObject> line: elements){
			for (GameObject obj: line){
				if (obj instanceof Key || obj instanceof Lever){
					return true;
				}
			}
		}
		return false;
	}
	
	/** 
	 * Adds an hero to the map
	 * @param hero Hero to be added
	 * */
	public void addHero(Hero hero){
		//	Remove hero from map
		if(this.hero != null){
			removeElementAt(getHero().getX(), getHero().getY());
		}
		//	Add hero
		addElementAt(hero, hero.getX(), hero.getY());
		setHero(hero);
	}
	/** 
	 * Removes an ogre
	 * @param ogre Ogre to be added
	 * */
	public void removeOgre(Ogre ogre){
		//	If ogre has a club, remove club from map
		if (ogre.getClub() != null){
			removeElementAt(ogre.getClub().getX(), ogre.getClub().getY());
		}
		ogres.remove(ogre);
	}

	/** 
	 * Adds a guard and shows in the map
	 * @param guard Guard to be added
	 * */
	public void addGuard(Guard guard){
		//		Remove previous guard -> only allow 1 guard
		if (getGuard() != null){
			removeElementAt(getGuard().getX(), getGuard().getY());
		}
		setGuard(guard);
		addElementAt(guard, guard.getX(), guard.getY());
	}

	/** 
	 * Changes guard type
	 * @param type GuardType
	 * */
	public void changeGuardType(GuardTypes type){
		if (guard != null){
			switch(type){
			case Drunken:
				guard = new Drunken(guard.getX(),guard.getY());
				addElementAt(this.guard, guard.getX(), guard.getY());
				break;
			case Rookie:
				guard = new Rookie(guard.getX(),guard.getY());
				addElementAt(this.guard, guard.getX(), guard.getY());
				break;
			case Suspicious:
				guard = new Suspicious(guard.getX(),guard.getY());
				addElementAt(this.guard, guard.getX(), guard.getY());
				break;
			}
		}
	}

	/** 
	 * Player finished game map, function call game level completion handler
	 * */
	public void completed(){
		//	Set completed
		this.setCompleted(true);

		//	Call game end map
		Game.instance.finishCurrentMap();
	}

	/** 
	 * Player pressed a lever, opens all doors
	 * */
	public void pressedLever(){
		this.openAllDoors();
	}

	/** 
	 * Add club to ogres
	 * */
	public void addClubToOgres(){
		for(Ogre ogre: ogres){
			ogre.addClub();
		}
	}

	/** 
	 * Updates game, need to call the super method to walk hero in subclasses.
	 * @param typed Typed string
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
		//	Guard logic
		if (this.getGuard() != null){
			guardLogic();
		}

		//	Ogre logic
		ogreLogic();
	}

	/** 
	 * Hero will move, method is called before hero moves, before hero move logic
	 * @return boolean Hero can make this move
	 * @param x Hero delta x position
	 * @param y Hero delta y position
	 * */
	public boolean heroWillMove(int x, int y){
		//	Get hero coordinates
		Coordinate2d heroPosition = getHero().getCoordinates();

		//	Cell element that will move to
		GameObject obj = getElementAt(heroPosition.getX() + x, heroPosition.getY()+y);

		//	If moved next to an ogre and has weapon, stun him
		Ogre nearOgre = isOgreNear(getHero().getX()+x, getHero().getY()+y);
		if(nearOgre != null){
			//	If hero has weapon, stun ogre
			if(getHero().isHasClub()) {
				nearOgre.setStunned(true);
			} 
		}

		//	Check if element that is going to move to is null
		if (obj != null){
			boolean toReturn;

			// Check if can move to object before doing any action
			if (obj.canMoveTo()){ 
				toReturn = true;
			} else {
				toReturn = false;
			}
			//	Do element action before moving
			elementDoAction(obj);

			return toReturn;
		} 
		return true;
	}
	/** 
	 * Move hero
	 * @param x Delta x
	 * @param y Delta y
	 * */
	public void moveHero(int x, int y){
		//	BEFORE MOVING
		// Check if out of bounds
		Coordinate2d heroPosition = hero.getCoordinates();

		if (isAllowedToGoTo(heroPosition.getX() + x,heroPosition.getY() + y)){
			if(heroWillMove(x,y)) {
				// MOVE
				// Clean previous cell
				moveElement(hero, heroPosition.getX() + x, heroPosition.getY() + y);
			}
		}
	}

	/** 
	 * Ogre logic
	 * */
	private void ogreLogic(){
		//	Move ogres
		if (this.canGuardsAndOgresMove)moveOgres();

		//	If moved next to an ogre and hero doesnt have weapon, game over
		Ogre nearOgre = isOgreNear(getHero().getX(), getHero().getY());
		if(nearOgre != null){
			//	If hero has weapon, stun ogre, else, lose game
			if(getHero().isHasClub()) {
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
	 * Guard logic
	 * */
	private void guardLogic(){

		//	Moves guard
		if (guard.getMovePositions() != null && this.canGuardsAndOgresMove){
			moveGuardLogic();
		}

		//	Check if moved next to a guard
		if(isGuardNear() == true){
			//	Check if guard is not a drunken sleeping
			if (getGuard() instanceof Drunken) {
				Drunken drunkGuard = (Drunken)getGuard();
				if (drunkGuard.isSleeping()){
					//	If guard is sleeping, doesn't end game.
					return;
				}
			} 
			// END GAME, GAME Over
			Game.instance.gameOver();
		}
	}

	/** 
	 * Check if guard is near
	 * @return True if guard is adjacent
	 * */
	public boolean isGuardNear(){
		if(getGuard() == null){
			return false;
		}
		// Check if the guard is above hero
		else if (getGuard().getX() == getHero().getX() && getGuard().getY() == getHero().getY()-1){
			return true;
		}
		// Check if the guard is down
		else if (getGuard().getX() == getHero().getX() && getGuard().getY() == getHero().getY()+1){
			return true;
		}
		// Check if the guard is on the right
		else if (getGuard().getX() == getHero().getX()+1 && getGuard().getY() == getHero().getY()){
			return true;
		}
		//	Check if the guard is on the left
		else if (getGuard().getX() == getHero().getX()-1 && getGuard().getY() == getHero().getY()){
			return true;
		} else {
			return false;
		}
	}

	/** 
	 * Check if ogre is adjacent to x and y position
	 * @return Ogre near ogre
	 * @param x XPosition to check if ogre is near 
	 * @param y YPosition to check if ogre is near
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
	 * Returns true if ogre club is near hero
	 * @return True if ogre club is near hero
	 * */
	public boolean isOgreClubNear(){
		// Check if the guard is above hero
		if (getElementAt(getHero().getX(), getHero().getY()-1) instanceof OgreClub){
			return true;
		}
		// Check if the guard is down
		else if (getElementAt(getHero().getX(), getHero().getY()+1) instanceof OgreClub){
			return true;
		}
		// Check if the guard is on the right
		else if (getElementAt(getHero().getX()+1, getHero().getY()) instanceof OgreClub){
			return true;
		}
		//	Check if the guard is on the left
		else if (getElementAt(getHero().getX()-1, getHero().getY()) instanceof OgreClub){
			return true;
		}
		return false;
	}

	/** 
	 * Moves guard based on his type
	 * */
	public void moveGuardLogic(){
		//		MOVE GUARD
		//	Check guard personality
		//	Rookie
		if(getGuard() instanceof Rookie){
			moveGuard(false);
		} 
		//	Drunken
		else if(getGuard() instanceof Drunken){
			Drunken drunkGuard = (Drunken)getGuard();
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
				// Decrease sleep time
				drunkGuard.setSleepTime(drunkGuard.getSleepTime() - 1);
				// If guard sleeping time is over awake him
				if (drunkGuard.getSleepTime() == 0){
					//	Awake guard
					drunkGuard.setSleeping(false);
				}
			}
		}
		// Suspicious guard
		else if (getGuard() instanceof Suspicious){
			Suspicious suspiciousGuard = (Suspicious)getGuard();
			// Move guard
			moveGuard(suspiciousGuard.isInReverse());

			//	Guard have a random chance to change direction
			int reverseChance = RandomService.getRandomInt(1, 6);
			if (reverseChance == 1){
				//	Change guard direction
				suspiciousGuard.reverseDirection();
			}
		}
	}

	/**	
	 * Moves guard
	 * @param reverse Should reverse
	 * */
	public void moveGuard(boolean reverse){
		//	BEFORE MOVING
		// Get new x and y;
		int x,y;
		x = getGuard().getMovePositions().get(getGuard().getMoveCounter()).getX();
		y = getGuard().getMovePositions().get(getGuard().getMoveCounter()).getY();

		if (moveElement(guard, x, y)){
			// AFTER MOVING
			// If is not reverse, add counter
			if (!reverse) {
				// Check if end of moves
				if(getGuard().getMoveCounter() == getGuard().getMovePositions().size()-1){
					//	Reset guard move counter
					getGuard().setMoveCounter(0);
				} else {
					//	Increase move counter
					getGuard().setMoveCounter(getGuard().getMoveCounter() + 1);
				}
			} 
			//	It is reversed, subtract counter
			else {
				// Check if the first move
				if(getGuard().getMoveCounter() == 0){
					//	Reset guard move counter to last
					getGuard().setMoveCounter(getGuard().getMovePositions().size()-1);
				} else {
					//	Decrease
					getGuard().setMoveCounter(getGuard().getMoveCounter() - 1);
				}
			}
		} 
	}

	/** 
	 * Random a guard type
	 * */
	public void randomGuardType(){
		if (this.getGuard() != null) {
			GuardTypes guardType = GuardTypes.values()[RandomService.getRandomInt(0, 2)];

			switch(guardType.ordinal()){
			case 2:
				setGuard(new Suspicious(guard.getX(),guard.getY()));
				addElementAt(this.guard, guard.getX(), guard.getY());
				break;
			case 0:
				setGuard(new Rookie(guard.getX(),guard.getY()));
				addElementAt(this.guard, guard.getX(), guard.getY());
				break;
			case 1:
				setGuard(new Drunken(guard.getX(),guard.getY()));
				addElementAt(this.guard, guard.getX(), guard.getY());
				break;
			default:
				break;
			}
		}
	}
	/** 
	 * Set default guard moves
	 * */
	public void setGuardDefaultMoves(){
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
		getGuard().setMovePositions(newPositions);
	}


	/** 
	 * Do a action if game object needs a action, function is called when a hero is moving to a game object
	 * @param element GameObject to make action
	 * */
	public void elementDoAction(GameObject element){
		//		Check if moving to door
		if(element instanceof Door){
			Door door = (Door)element;
			// If door is closed, open and remove key from hero
			if (door.isOpen() == false){
				//	Check if has key
				if (getHero().hasKey()){
					//	Open door
					door.openDoor();
					//	Remove key from hero
					getHero().setHasKey(false);
					return;
				}
			} else {
				//	Complete level
				this.completed();
			}
		}
		//	Check if moving to a lever
		else if(element instanceof Lever){
			this.pressedLever();
		}
		//	Check if moving to a club
		else if(element instanceof Club){
			//	Get club
			getHero().setHasClub(true);
		}
		//	Check if moving to a key
		else if(element instanceof Key){
			//	Get key
			getHero().setHasKey(true);
		}
	}

	/** 
	 * Move all ogres randomly
	 * */
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

	/** 
	 * Move a single ogre
	 * @param ogre Ogre that will move randomly
	 * */
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

		GameObject element = getElementAt(ogre.getX() + x,ogre.getY() + y);

		if (element == null || element instanceof Ogre || element instanceof OgreClub){
			//	If moving out of a key, add key to previous cell
			if (ogre.isOnKey()){
				//	Ogre is not on key anymore
				ogre.setOnKey(false);
				//	Add key to previous cell
				addElementAt(new Key(ogre.getX(),ogre.getY()), ogre.getX(),ogre.getY());
				//	Add ogre on next cell
				addElementAt(ogre, ogre.getX()+x,ogre.getY()+y);
			} else {
				// Remove last ogre
				if (getElementAt(ogre.getX(), ogre.getY()) instanceof Ogre){
					removeElementAt(ogre.getX(), ogre.getY());
				}
				//	Add ogre
				addElementAt(ogre, ogre.getX() +x, ogre.getY()+y);
			}
			//	Swing club
			if (ogre.getClub() != null){
				swingClub(ogre);
			}
			//	If instance of key
		} else if (element instanceof Key){
			// Check if moving to a key
			ogre.setOnKey(true);
			//	Move ogre
			moveElement(ogre, ogre.getX()+x, ogre.getY()+y);
			//	Swing club
			if (ogre.getClub() != null){
				swingClub(ogre);
			}
		} else {
			//	Use recursively
			moveOgre(ogre);
		}
	}

	/** 
	 * Swing ogre club
	 * @param ogre Ogre that will swing club
	 * */
	private void swingClub(Ogre ogre){
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

		GameObject element = getElementAt(ogre.getX() + x,ogre.getY() + y);

		if (element == null || (element instanceof OgreClub)){
			//	If moving out of a key, add key to previous cell
			if (ogre.getClub().isOnKey()){
				Coordinate2d clubLocation = ogre.getClub().getCoordinates();
				//	Ogre is not on key anymore
				ogre.getClub().setOnKey(false);
				//	Add club to next cell
				addElementAt(ogre.getClub(), ogre.getX() +x, ogre.getY()+y);
				//	Add key to previous cell
				addElementAt(new Key(clubLocation.getX(),clubLocation.getY()), clubLocation.getX(),clubLocation.getY());
			} else {
				// Remove last club
				if (getElementAt(ogre.getClub().getX(), ogre.getClub().getY()) instanceof OgreClub){
					removeElementAt(ogre.getClub().getX(), ogre.getClub().getY());
				}
				//	Add club
				addElementAt(ogre.getClub(), ogre.getX() +x, ogre.getY()+y);
			}
		} else if (element instanceof Key){
			// Check if moving to a key
			ogre.getClub().setOnKey(true);

			// Remove last club
			if (getElementAt(ogre.getClub().getX(), ogre.getClub().getY()) instanceof OgreClub){
				removeElementAt(ogre.getClub().getX(), ogre.getClub().getY());
			}
			//	Add club
			addElementAt(ogre.getClub(), ogre.getX() +x, ogre.getY()+y);
		} else {
			//	Use recursively
			swingClub(ogre);
		}
	}


	/**
	 * Gets game object at x and y position, returns null if ther is no game object
	 * @return GameObject at x,y
	 * @param x Column number
	 * @param y Line number
	 * */
	public GameObject getElementAt(int x, int y){
		if ((x >= 0  && y >= 0 && x < this.getNumberOfRows() && y < this.getNumberLines() )
				&& (this.elements.get(y).get(x) != null)){
			return this.getElements().get(y).get(x);
		}
		else {
			return null;
		}
	}

	/** 
	 * Remove game object at x,y
	 * @return GameObject removed
	 * @param x X position
	 * @param y Y position
	 * */
	public GameObject removeElementAt(int x,int y){
		if (!isAllowedToGoTo(x,y)) return null;

		GameObject toReturn = this.elements.get(y).set(x, null);
		return toReturn;
	}

	/** 
	 * Adds an element to the game
	 * @param element GameObject to be added
	 * @param x X Position that will be added
	 * @param y Y Position that will be added
	 * */
	public void addElementAt(GameObject element, int x, int y){
		if (!isAllowedToGoTo(x,y)) return;

		element.setCoordinates(new Coordinate2d(x,y));
		this.elements.get(y).set(x, element);
	}

	/** 
	 * Check if x and y is out of bounds
	 * @return true if x and y is not out of bounds
	 * @param x X coordinate
	 * @param y y Coordinate
	 * */
	private boolean isAllowedToGoTo(int x, int y){
		if ((x >= 0 && x < getNumberOfRows() && y >= 0 && y < getNumberLines())) return true;
		else return false;
	}

	/** 
	 * Move game object
	 * @param xF Final x
	 * @param yF Final y
	 * @param element GameObject that will be moved
	 * @return True if game object can be moved
	 * */
	public boolean moveElement(GameObject element, int xF, int yF){
		if (isAllowedToGoTo(xF,yF) && (getElementAt(xF, yF) == null || 
				getElementAt(xF , yF).canMoveTo()
				)){
			// MOVE
			// Get previous cell
			GameObject obj = getElementAt(element.getX(), element.getY());

			//	If object is on screen
			if(obj != null && obj.getClass() == element.getClass()){
				//	Remove object on screen
				removeElementAt(element.getX(), element.getY());
				//	Show in next cell
				addElementAt(obj, xF, yF);
			} else {
				//	Show in next cell
				addElementAt(element, xF, yF);
			}

			//	Update object
			if (obj != null) {
				obj.setCoordinates(new Coordinate2d(xF,yF));
				//	Show in next cell
				addElementAt(obj, xF, yF);
			}

			return true;
		} else {
			// Can't move object
			return false;
		}
	}



	/** 
	 * Open all doors
	 * */
	public void openAllDoors(){
		//	Loop through cells
		for(ArrayList<GameObject> line : this.elements){
			for(GameObject cell: line){
				//	Check if it is a door
				if(cell instanceof Door){
					//	Open door
					Door door = (Door)cell;
					//	Return true if exit door is open
					door.openDoor();
				}
			}
		}
	}

	/** 
	 * Returns true if there is a open exit door
	 * @return True if there is a open exit door
	 * */
	public boolean isExitDoorsOpen(){
		// Loop through lines
		for(ArrayList<GameObject> line : this.elements){
			//	Loop through rows
			for(GameObject cell: line){
				//	Check if it is a door
				if(cell instanceof Door){
					//	Get door
					Door door = (Door)cell;
					//	Return true if exit door is open
					if(door.isOpen()) return true;
				}
			}
		}
		return false;
	}

	/** 
	 * @return String The Gamemap in a string
	 * */
	public String toString(){
		String string = "";
		int i, j;
		for(i = 0; i < this.elements.size(); i++){
			String line = "|";
			for(j = 0; j < this.elements.get(i).size(); j++){
				if (elements.get(i).get(j) != null){
					//	Has something in the cell
					GameObject element = elements.get(i).get(j);
					//	Add to line
					line += element.getLetter();
					line += " ";
				} else {
					//	Empty cell
					line += "  ";
				}
			}
			//	Add line
			string += line + "|\n";
		}
		return string;
	}


	/**
	 * Return true if the game map is completed
	 * @return the completed
	 */
	public boolean isCompleted() {
		return isCompleted;
	}

	/**
	 * Set game map completed
	 * @param completed the completed to set
	 */
	public void setCompleted(boolean completed) {
		this.isCompleted = completed;
	}

	/**
	 * Get number of lines
	 * @return the numberLines
	 */
	public int getNumberLines() {
		return elements.size();
	}
	/** 
	 * Get number of rows
	 * @return int Number of rows
	 * */
	public int getNumberOfRows(){
		return elements.get(0).size();
	}

	/**
	 * Get the guard
	 * @return the guard
	 */
	public Guard getGuard() {
		return guard;
	}

	/**
	 * Set guard
	 * @param guard the guard to set
	 */
	public void setGuard(Guard guard) {
		this.guard = guard;
	}

	/**
	 * Returns the game objects in the map
	 * @return the elements
	 */
	public ArrayList<ArrayList<GameObject>> getElements() {
		return elements;
	}

	/**
	 * Set game objects
	 * @param elements the elements to set
	 */
	public void setElements(ArrayList<ArrayList<GameObject>> elements) {
		this.elements = elements;
	}

	/**
	 * Returns the hero
	 * @return the hero
	 */
	public Hero getHero() {
		return hero;
	}

	/**
	 * Set hero
	 * @param hero the hero to set
	 */
	public void setHero(Hero hero) {
		this.hero = hero;
	}

	/**
	 * Return the ogres ArrayList
	 * @return the ogres
	 */
	public ArrayList<Ogre> getOgres() {
		return ogres;
	}

	/**
	 * Set ogres
	 * @param ogres the ogres to set
	 */
	public void setOgres(ArrayList<Ogre> ogres) {
		this.ogres = ogres;
	}

	/**
	 * Returns true if guards and ogres can move
	 * @return the canGuardsAndOgresMove
	 */
	public boolean canGuardsAndOgresMove() {
		return canGuardsAndOgresMove;
	}

	/**
	 * Set if guard and ogres can move
	 * @param canGuardsAndOgresMove the canGuardsAndOgresMove to set
	 */
	public void setCanGuardsAndOgresMove(boolean canGuardsAndOgresMove) {
		this.canGuardsAndOgresMove = canGuardsAndOgresMove;
	}
}
