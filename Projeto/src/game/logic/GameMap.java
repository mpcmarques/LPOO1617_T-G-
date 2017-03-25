package game.logic;

import java.util.ArrayList;

import game.services.RandomService;

public class GameMap extends Object {
	private boolean isCompleted;
	private ArrayList<ArrayList<GameObject>> elements;

	//	Objects
	private Hero hero;
	private Guard guard;

	/** 
	 * Default constructor, create a empty map
	 * */
	/*public GameMap(int numberCellsForLine, int numberLines){
		// Initialize variables
		this.setCompleted(false);
		this.setNumberCellsForLine(numberCellsForLine);
		this.setNumberLines(numberLines);
		cells = new ArrayList<ArrayList<Cell>>();
	}*/

	/**
	 * @brief Constructor create a GameMap based on a char
	 * */
	public GameMap(char[][] map){
		this.setCompleted(false);

		//	Populates map with objects
		populateMapWithObjects(map);
	}

	/** 
	 * Populates map with objects
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
	 * Returns the element of a letter
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
		default:
			//	Add empty cell
			return null;
		}
	}

	/** 
	 * @brief Player finished game map, call game level completion handler
	 * */
	public void completed(){
		//	Set completed
		this.setCompleted(true);
		//	Call game level completion handler
		Game.instance.setGamemapCompleted(true);
	}

	/** 
	 * @brief Player pressed a lever, opens all doors
	 * */
	public void pressedLever(){
		this.openAllDoors();
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
		//	Guard logic
		if (this.getGuard() != null){
			guardLogic();
		}
	}

	/** 
	 * Guard logic
	 * */
	private void guardLogic(){
		
		//	Moves guard
		if (guard.getMovePositions() != null){
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
	 * @returns boolean Returns true if guard is adjacent
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
	 * 
	 * */
	public boolean isNearWall(int x, int y){
		// Check if object is above
		if (getElementAt(x,y-1) != null && getElementAt(x,y-1) instanceof Wall){
			return true;
		}
		//	Check if object is down
		else if (getElementAt(x,y+1) != null && getElementAt(x,y+1)  instanceof Wall){
			return true;
		}
		else if (getElementAt(x+1,y) != null && getElementAt(x+1,y)  instanceof Wall){
			return true;
		}
		else if (getElementAt(x-1,y) != null && getElementAt(x-1,y)  instanceof Wall){
			return true;
		} else {
			return false;
		}
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
	 * @brief Moves guard
	 * @parameter reverse Should reverse
	 * */
	public void moveGuard(boolean reverse){
		//	BEFORE MOVING
		// Get new x and y;
		int x,y;
		x = getGuard().getMovePositions().get(getGuard().getMoveCounter()).getX();
		y = getGuard().getMovePositions().get(getGuard().getMoveCounter()).getY();
		
		if (moveElement(guard.getX(), guard.getY(), x, y)){
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
		} else {
			System.out.println("Guard can't move!");
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
	 * Hero will move, method is called before hero moves, before hero move logic
	 * @return boolean Hero can make this move
	 * */
	public boolean heroWillMove(int x, int y){

		//	Check if moving to a wall
		Coordinate2d heroPosition = getHero().getCoordinates();

		//	Cell element that will move to
		GameObject obj = getElementAt(heroPosition.getX() + x, heroPosition.getY()+y);

		//	Check if element that is going to move to is null
		if (obj != null){
			//	Do element action before moving
			elementDoAction(obj);

			// Can move to element
			if (obj.canMoveTo()){ 
				return true;
			} else {
				return false;
			}
		} 
		return true;
	}

	/** 
	 * Do element action
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
	}

	public void moveHero(int x, int y){
		//	BEFORE MOVING
		// Check if out of bounds
		Coordinate2d heroPosition = hero.getCoordinates();

		if (heroPosition.getX() + x >= 0 
				&& heroPosition.getX() + x <= getNumberOfRows()
				&& heroPosition.getY() + y >= 0 
				&& heroPosition.getY() + y <= getNumberLines()){

			if(heroWillMove(x,y)) {
				// MOVE
				// Clean previous cell
				moveElement(hero.getX(), hero.getY(), heroPosition.getX() + x, heroPosition.getY() + y);
			}
		}
	}

	/** 
	 * @return Element at x,y
	 * @param x Column number
	 * @param y Line number
	 * */
	public GameObject getElementAt(int x, int y){
		if (this.elements.get(y).get(x) != null){
			return this.elements.get(y).get(x);
		} else {
			return null;
		}
	}

	/** 
	 * Remove game object at x,y
	 * */
	public GameObject removeElementAt(int x,int y){
		GameObject toReturn = this.elements.get(y).set(x, null);
		return toReturn;
	}

	/** 
	 * Adds an element to the game
	 * */
	public void addElementAt(GameObject element, int x, int y){
		this.elements.get(y).set(x, element);
	}

	/** 
	 * Move element
	 * */
	public boolean moveElement(int x0, int y0, int xF, int yF){
		if (getElementAt(xF, yF) == null || 
				getElementAt(xF , yF).canMoveTo()
				){
			// MOVE
			// Clean previous cell
			GameObject obj = removeElementAt(x0, y0);
			
			//	Update object
			obj.setCoordinates(new Coordinate2d(xF,yF));

			//	Show in next cell
			addElementAt(obj, xF, yF);

			return true;
		} else {
			// Can't move object
			return false;
		}
	}



	/** 
	 * @brief Open all doors
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
	 * @brief Returns true if there is a open exit door
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
	 * @return the numberLines
	 */
	public int getNumberLines() {
		return elements.size();
	}

	public int getNumberOfRows(){
		return elements.get(0).size();
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
	 * @return the elements
	 */
	public ArrayList<ArrayList<GameObject>> getElements() {
		return elements;
	}

	/**
	 * @param elements the elements to set
	 */
	public void setElements(ArrayList<ArrayList<GameObject>> elements) {
		this.elements = elements;
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
}
