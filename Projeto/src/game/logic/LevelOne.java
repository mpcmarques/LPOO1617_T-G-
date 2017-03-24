package game.logic;

import java.util.ArrayList;

public class LevelOne extends GameMap {
	private int guardMovesCounter;
	private boolean isGuardAllowedToMove;
	private Guard guard;
	private Lever lever;

	/** 
	 * Constructor
	 * */
	public LevelOne(char[][] map, GuardTypes guardTyp, boolean isGuardAllowedToMove){
		//	Call super
		super(map);
		GuardTypes guardType = guardTyp;

		// Sort guard type if guard is nil, sort guard type
		if (guardType == null) {
			guardType = GuardTypes.values()[RandomService.getRandomInt(0, 2)];
		}
		
		//	Populates map
		populateMapWithObjects(map, guardType);

		// Set guard allowed to move
		setGuardAllowedToMove(isGuardAllowedToMove);

		//	Set guard moves
		if(this.getGuard() != null){
			setGuardMoves();
		}
	}
	
	/** 
	 * Populates Gamemap map with objects.
	 * */
	private void populateMapWithObjects(char[][] map, GuardTypes guardType){
		//		Get guard from map
		int row, col;
		for(row = 0; row < map.length; row++){
			for(col = 0; col < map[row].length; col++){
				char value = map[col][row];
				if (value == 'G'){
					switch(guardType.ordinal()){
					case 2:
						setGuard(new Suspicious(row,col));
						break;
					case 0:
						setGuard(new Rookie(row,col));
						break;
					case 1:
						setGuard(new Drunken(row,col));
						break;
					default:
						break;
					}

					//	Add cell to map
					this.getMap().addCell(getGuard());
				}
			}
		}
	}

	public void heroDidMove(){
		// Game over check
		//	Moves guard
		if (isGuardAllowedToMove() == true){
			moveGuardLogic();
		}

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
	}

	///	First level logic
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
				// Decrease sleeptime
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
				suspiciousGuard.changeDirection();
			}
		}
	}

	public void setGuardMoves(){
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
	 * @brief Moves guard
	 * @parameter reverse Should reverse
	 * */
	/// @parameter 
	public void moveGuard(boolean reverse){
		//	BEFORE MOVING
		// Get new x and y;
		int x,y;
		x = getGuard().getMovePositions().get(getGuard().getMoveCounter()).getX();
		y = getGuard().getMovePositions().get(getGuard().getMoveCounter()).getY();

		// MOVE

		// Clean previous cell
		getMap().getCells()[getGuard().getY()][getGuard().getX()] = null;
		// Update guard position
		getGuard().setCoordinate(new Coordinate2d(x,y));
		//	Show in next cell
		getMap().getCells()[getGuard().getY()][getGuard().getX()] = getGuard();

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
	 * @return the isGuardAllowedToMove
	 */
	public boolean isGuardAllowedToMove() {
		return isGuardAllowedToMove;
	}

	/**
	 * @param isGuardAllowedToMove the isGuardAllowedToMove to set
	 */
	public void setGuardAllowedToMove(boolean isGuardAllowedToMove) {
		this.isGuardAllowedToMove = isGuardAllowedToMove;
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
