package game.logic;

/** 
 * Class represents a door in the game
 * */
public class Door extends GameObject {
	private boolean isOpen;

	/** 
	 * Constructor
	 * */
	public Door(int x, int y) {
		super(x,y);
		setOpen(false);
	}
	
	@Override public String getLetter(){
		if(isOpen){ 
			return "S";
		}
		else return "I";
	}
	
	@Override
	public boolean canMoveTo() {
		if (isOpen){
			return true;
		} else {
			return false;
		}
	}
	
	/** 
	 * @return True if the door is open
	 * */
	public boolean isOpen() {
		return isOpen;
	}
	
	/** 
	 * Set door isOpen
	 * */
	private void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}
	
	/** 
	 * Opens the door
	 * */
	public void openDoor(){
		this.setOpen(true);
	}
}