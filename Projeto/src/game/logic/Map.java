package game.logic;

public class Map {
	int numberCellsForLine;
	int numberLines;
	
	Cell[][] cells;
	
	public Map(int numberCellsForLine, int numberLines){
		//	Initialize variables
		this.numberCellsForLine = numberCellsForLine;
		this.numberLines = numberLines;
		cells = new Cell[numberLines][numberCellsForLine];
	}
	
	public void addHero(Hero hero){
		this.cells[hero.y][hero.x] = hero;
	}
	
	public void addWall(int x, int y){
		this.cells[y][x] = new Wall(x,y);
	}
	
	/// Adds a lever to map
	public void addLever(int x,int y){
		//Create lever
		this.cells[y][x] = new Lever();
	}
	
	///	Adds a door to map
	public void addDoor(int x, int y, boolean isExit){
		this.cells[y][x] = new Door(isExit);
	}
	
	public void addGuard(Guard guard){
		this.cells[guard.getY()][guard.getX()] = guard;
	}
	
	//	Open all doors
	public void openAllDoors(){
		int i,j;
		//	Loop throught cells
		for(i = 0; i < this.numberLines; i++){
			for(j = 0; j < this.numberCellsForLine; j++){
				//	Check if it is a door
				if(this.cells[i][j] instanceof Door){
					//	Open door
					Door door = (Door)this.cells[i][j];
					door.openDoor();
				}
			}
		}
	}
}
