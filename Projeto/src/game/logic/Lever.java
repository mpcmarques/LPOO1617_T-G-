package game.logic;
public class Lever extends Cell {
	private boolean pressed;

	///	Constructor
	Lever(int x, int y) {
		super("k", x, y);
		setPressed(false);
		// TODO Auto-generated constructor stub
	}

	public boolean isPressed() {
		return pressed;
	}

	public void setPressed(boolean pressed) {
		this.pressed = pressed;
	}
}