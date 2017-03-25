package game.logic;

public class OgreClub extends Club {
	OgreClub(int x, int y) {
		super(x, y);
	}

	/** 
	 * Don't allow moving to a ogre club
	 * */
	public boolean canMoveTo(){
		return false;
	}
}
