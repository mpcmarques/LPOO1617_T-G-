package game.logic;

public class Rookie extends Guard {
	public Rookie(int x, int y){
		super(x,y);
	}

	@Override
	public String getLetter() {
		return "G";
	}
	
	@Override
	public boolean canMoveTo() {
		return false;
	}
}
