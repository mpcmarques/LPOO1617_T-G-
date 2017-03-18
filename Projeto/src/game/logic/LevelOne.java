package game.logic;

import java.util.ArrayList;

public class LevelOne extends GameMap {
	private int guardMovesCounter;
	private Lever lever;

	public LevelOne() {
		//	Start new map
		super(new Map(10,10));
		// TODO Auto-generated constructor stub
		startMap();
	}

	/// Starts first level
	public void startMap(){
		//	 Create map layout
		//		Add walls to map
		createWallsFirstLevel();
		//	Add  doors to map
		createDoorsFirstLevel();
		//	Add hero to map
		setHero(new Hero(1,1));
		getMap().addCell(getHero());
		//	Add lever to map
		getMap().addCell(new Lever(6,8));
		//	Add guard to map
		setGuard(new Suspicious(8,1));
		//	Set guard moves
		setGuardMoves();
		getMap().addCell(getGuard());
		//	Print map in the first time
		System.out.println(getMap());
	}

	public void updateGame(String typed){
		//	Update superclass -> makes hero walk
		super.updateGame(typed);

		//	Level logic
		firstLevelLogic();
	}
	
	public void completed(){
		//	Call super
		super.completed();
		
		//	Change map
		Game.instance.changeMap(new LevelTwo());
	}

	///	First level logic
	public void firstLevelLogic(){
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

	///	Create first level doors
	public void createDoorsFirstLevel(){
		//	Create doors
		Door door1 = new Door(4, 1, false);
		Door door2 = new Door(4, 3, false);
		Door door3 = new Door(2, 3, false);
		Door door4 = new Door(0, 5, true);
		Door door5 = new Door(0, 6, true);
		Door door6 = new Door(2, 8, false);
		Door door7 = new Door(4, 8, false);
		//	Add to map
		getMap().addCell(door1);
		getMap().addCell(door2);
		getMap().addCell(door3);
		getMap().addCell(door4);
		getMap().addCell(door5);
		getMap().addCell(door6);
		getMap().addCell(door7);
	}

	/// Create first level walls
	public void createWallsFirstLevel(){
		// Add first line
		getMap().addWall(0, 0);
		getMap().addWall(1, 0);
		getMap().addWall(2, 0);
		getMap().addWall(3, 0);
		getMap().addWall(4, 0);
		getMap().addWall(5, 0);
		getMap().addWall(6, 0);
		getMap().addWall(7, 0);
		getMap().addWall(8, 0);
		getMap().addWall(9, 0);
		//	Add second line
		getMap().addWall(0, 1);
		getMap().addWall(6, 1);
		getMap().addWall(9, 1);
		//	Add third line
		getMap().addWall(0, 2);
		getMap().addWall(1, 2);
		getMap().addWall(2, 2);
		getMap().addWall(4, 2);
		getMap().addWall(5, 2);
		getMap().addWall(6, 2);
		getMap().addWall(9, 2);
		//	Add fourth line
		getMap().addWall(0,3);
		getMap().addWall(6,3);
		getMap().addWall(9,3);
		//	Add fifth
		getMap().addWall(0, 4);
		getMap().addWall(1, 4);
		getMap().addWall(2, 4);
		getMap().addWall(4, 4);
		getMap().addWall(5, 4);
		getMap().addWall(6, 4);
		getMap().addWall(9, 4);
		//	Add six and Seven
		getMap().addWall(9, 5);
		getMap().addWall(9, 6);
		//	Add eight
		getMap().addWall(0, 7);
		getMap().addWall(1, 7);
		getMap().addWall(2, 7);
		getMap().addWall(4, 7);
		getMap().addWall(5, 7);
		getMap().addWall(6, 7);
		getMap().addWall(7, 7);
		getMap().addWall(9, 7);
		//	Add nine
		getMap().addWall(0, 8);
		getMap().addWall(5, 8);
		getMap().addWall(9, 8);
		// Add last line
		getMap().addWall(0, 9);
		getMap().addWall(1, 9);
		getMap().addWall(2, 9);
		getMap().addWall(3, 9);
		getMap().addWall(4, 9);
		getMap().addWall(5, 9);
		getMap().addWall(6, 9);
		getMap().addWall(7, 9);
		getMap().addWall(8, 9);
		getMap().addWall(9, 9);
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
