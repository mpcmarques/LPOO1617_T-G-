package game.logic;

public class Map {
	private int numberCellsForLine;
	private int numberLines;
	private Cell[][] cells;
	
	public Map(int numberCellsForLine, int numberLines){
		//	Initialize variables
		this.numberCellsForLine = numberCellsForLine;
		this.numberLines = numberLines;
		cells = new Cell[numberLines][numberCellsForLine];
	}
	
	public void addCell(Cell cell){
		this.cells[cell.getY()][cell.getX()] = cell;
	}
	
	public void addWall(int x, int y){
		this.cells[y][x] = new Wall(x,y);
	}
	
	//	Open all doors
	public void openAllDoors(){
		int i,j;
		//	Loop through cells
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
	
	public String toString(){
		String string = "";
		int i, j;
		for(i = 0; i < numberLines; i++){
			String line = "|";
			for(j = 0; j < numberCellsForLine; j++){
				if (cells[i][j] != null){
					//	Has something in the cell
					Cell cell = cells[i][j];
					//	Add to line
					line += cell.getLetter();
					line += "|";
				} else {
					//	Empty cell
					line += " |";
				}
			}
			//	Add line
			string += line + "\n";
		}
		return string;
	}

	/**
	 * @return the numberCellsForLine
	 */
	public int getNumberCellsForLine() {
		return numberCellsForLine;
	}

	/**
	 * @param numberCellsForLine the numberCellsForLine to set
	 */
	public void setNumberCellsForLine(int numberCellsForLine) {
		this.numberCellsForLine = numberCellsForLine;
	}

	/**
	 * @return the numberLines
	 */
	public int getNumberLines() {
		return numberLines;
	}

	/**
	 * @param numberLines the numberLines to set
	 */
	public void setNumberLines(int numberLines) {
		this.numberLines = numberLines;
	}

	/**
	 * @return the cells
	 */
	public Cell[][] getCells() {
		return cells;
	}

	/**
	 * @param cells the cells to set
	 */
	public void setCells(Cell[][] cells) {
		this.cells = cells;
	}
}
