package game.logic;

public class Game {
	static Map map = new Map(10,10);
	static Hero hero = new Hero(1,1);
	static Guard guard = new Guard(8,1);
	static int guardMovesCounter = 0;
	private boolean ended;

	public Game(){
		this.ended = false;
	}

	public boolean getEnded(){
		return this.ended;
	}

	/// Starts the game
	public void start(){
		//	Add hero to kmap
		map.addHero(hero);
		//	Add walls to map
		createWalls();
		//	Add  doors to map
		createDoors();
		//	Add lever to map
		map.addLever(6, 8);
		//	Add guard to map
		map.addGuard(guard);
	}

	public void updateGame(String typed){
		//	Move guard
		moveGuard();

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
		case "0":
			//	End game
			ended = true;
		default:
			break;
		}
	}

	///	Moves hero
	public static void moveHero(int x, int y){
		//	BEFORE MOVING
		// Check if out of bounds
		if (hero.x + x >= 0 
				&& hero.x + x <= map.numberCellsForLine 
				&& hero.y + y >= 0 
				&& hero.y + y <= map.numberLines){
			//	Check if moving to a wall
			if(map.cells[hero.y+y][hero.x+x] instanceof Wall){
				return;
			}
			//	Check if moving to door
			if(map.cells[hero.y+y][hero.x+x] instanceof Door){
				Door door = (Door) map.cells[hero.y+y][hero.x+x];
				// Check if door is closed
				if (door.isOpen() == false){
					return;
				} else {
					//	If it is open and exit, finish map
					if(door.isExit()){
						System.out.println("Game ended!");
						//ended = true;
						return;
					}
				}
			}

			//	Check if moving to a lever
			if(map.cells[hero.y+y][hero.x+x] instanceof Lever){
				System.out.println("Lever pressed!");
				map.openAllDoors();
			}

			// MOVE

			// Clean previous cell
			map.cells[hero.y][hero.x] = null;
			// Update hero y;
			hero.x += x;
			hero.y += y;
			//	Show in next cell
			map.cells[hero.y][hero.x] = hero;

			//	Check if moving to a guard
			if(guardIsNear() == true){
				// END GAME, GAME Over
				System.out.println("Game Over!");
			}
		}
	}

	static public void moveGuard(){
		//		BEFORE MOVING
		// Check if out of bounds
		int x = 0,y = 0;

		if(guard.getMoveCounter() == 0){
			// MOVE LEFT
			y = 0;
			x = -1;
		}
		else if (guard.getMoveCounter() < 5){
			// MOVE DOWN
			y = +1;
			x = 0;
		} 
		else if (guard.getMoveCounter() < 11){
			// MOVE LEFT
			y = 0;
			x = -1;
		}
		else if (guard.getMoveCounter() == 11){
			// MOVE DOWN
			y = 1;
			x = 0;
		}
		else if (guard.getMoveCounter() < 19){
			// MOVE RIGHT
			y = 0;
			x = 1;
		}
		else if(guard.getMoveCounter() < 24) {
			//	MOVE UP
			y = -1;
			x = 0;
		}

		// MOVE

		// Clean previous cell
		map.cells[guard.getY()][guard.getX()] = null;
		// Update guard position
		guard.setX(guard.getX()+x);
		guard.setY(guard.getY()+y);
		//	Show in next cell
		map.cells[guard.getY()][guard.getX()] = guard;

		// Check if original cell
		if(guard.getMoveCounter() == 24){
			//	RESET GUARD COUNTER
			guard.setMoveCounter(0);
		} else {
			guard.setMoveCounter(guard.getMoveCounter() + 1);
		}
	}

	/** 
	 * Check if the hero is on a lever
	 * */
	static boolean isOnLever(){
		if (map.cells[hero.y][hero.x] instanceof Lever){
			return true;
		}
		return false;
	}

	static boolean guardIsNear(){
		// Check if the guard is above
		if (map.cells[hero.y - 1][hero.x] != null 
				&& map.cells[hero.y - 1][hero.x] instanceof Guard){
			return true;
		}
		// Check if the guard is down
		if (map.cells[hero.y + 1][hero.x] != null 
				&& map.cells[hero.y + 1][hero.x] instanceof Guard){
			return true;
		}
		// Check if the guard is on the right
		if (map.cells[hero.y][hero.x + 1] != null 
				&& map.cells[hero.y][hero.x + 1] instanceof Guard){
			return true;
		}
		//	Check if the guard is on the left
		if (map.cells[hero.y][hero.x - 1] != null 
				&& map.cells[hero.y][hero.x - 1] instanceof Guard){
			return true;
		}

		return false;
	}

	///	Create doors
	void createDoors(){
		map.addDoor(4, 1, false);
		map.addDoor(4, 3, false);
		map.addDoor(2, 3, false);
		map.addDoor(0, 5, true);
		map.addDoor(0, 6, true);
		map.addDoor(2, 8, false);
		map.addDoor(4, 8, false);
	}

	/// Adds walls
	public void createWalls(){
		// Add first line
		map.addWall(0, 0);
		map.addWall(1, 0);
		map.addWall(2, 0);
		map.addWall(3, 0);
		map.addWall(4, 0);
		map.addWall(5, 0);
		map.addWall(6, 0);
		map.addWall(7, 0);
		map.addWall(8, 0);
		map.addWall(9, 0);
		//	Add second line
		map.addWall(0, 1);
		map.addWall(6, 1);
		map.addWall(9, 1);
		//	Add third line
		map.addWall(0, 2);
		map.addWall(1, 2);
		map.addWall(2, 2);
		map.addWall(4, 2);
		map.addWall(5, 2);
		map.addWall(6, 2);
		map.addWall(9, 2);
		//	Add fourth line
		map.addWall(0,3);
		map.addWall(6,3);
		map.addWall(9,3);
		//	Add fifth
		map.addWall(0, 4);
		map.addWall(1, 4);
		map.addWall(2, 4);
		map.addWall(4, 4);
		map.addWall(5, 4);
		map.addWall(6, 4);
		map.addWall(9, 4);
		//	Add six and Seven
		map.addWall(9, 5);
		map.addWall(9, 6);
		//	Add eight
		map.addWall(0, 7);
		map.addWall(1, 7);
		map.addWall(2, 7);
		map.addWall(4, 7);
		map.addWall(5, 7);
		map.addWall(6, 7);
		map.addWall(7, 7);
		map.addWall(9, 7);
		//	Add nine
		map.addWall(0, 8);
		map.addWall(5, 8);
		map.addWall(9, 8);
		// Add last line
		map.addWall(0, 9);
		map.addWall(1, 9);
		map.addWall(2, 9);
		map.addWall(3, 9);
		map.addWall(4, 9);
		map.addWall(5, 9);
		map.addWall(6, 9);
		map.addWall(7, 9);
		map.addWall(8, 9);
		map.addWall(9, 9);
	}

	public void printGame(){
		int i, j;
		for(i = 0; i < map.numberLines; i++){
			String line = "|";
			for(j = 0; j < map.numberCellsForLine; j++){
				if (map.cells[i][j] != null){
					//	Has something in the cell
					Cell cell = map.cells[i][j];
					//	Add to line
					line += cell.letter;
					line += "|";
				} else {
					//	Empty cell
					line += " |";
				}
			}
			//	Print line
			System.out.println(line);
		}
		System.out.println("");
	}
}
