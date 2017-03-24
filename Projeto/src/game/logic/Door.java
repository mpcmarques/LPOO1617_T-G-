package game.logic;
public class Door extends Cell {
	private boolean isOpen;

	///	Constructor
	public Door(int x, int y) {
		super("I", x, y);
		setOpen(false);
	}

	public boolean isOpen() {
		return isOpen;
	}

	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}
	
	//	Opens the door
	public void openDoor(){
		this.setOpen(true);
		this.setLetter("S");
	}
}