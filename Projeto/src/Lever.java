public class Lever extends Cell {
	private boolean pressed;

	///	Constructor
	Lever() {
		super("k");
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