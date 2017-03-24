package game.logic;
/** 
 * Class represents a game cell
 * */
public class Lever extends Cell {
	private boolean pressed;

	/** 
	 * Constructor
	 * */
	public Lever(int x, int y) {
		super("k", x, y);
		setPressed(false);
	}
	
	/** 
	 * @return boolean Lever is pressed
	 * */
	public boolean isPressed() {
		return pressed;
	}
	
	/** 
	 * Set lever pressed / not pressed
	 * */
	public void setPressed(boolean pressed) {
		this.pressed = pressed;
	}
}