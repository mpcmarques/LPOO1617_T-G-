
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
	public void addDoor(int x, int y){
		this.cells[y][x] = new Door();
	}
	
	public void addGuard(Guard guard){
		this.cells[guard.y][guard.x] = guard;
	}
}
