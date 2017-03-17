package game.logic;

public class Ogre extends Cell{
	private Club club;
	
	Ogre(int x, int y) {
		super("O", x, y);
		// TODO Auto-generated constructor stub
	}
	
	public void addClub(){
		this.club = new Club(getX(),getY());
	}

	/**
	 * @return the club
	 */
	public Club getClub() {
		return club;
	}

	/**
	 * @param club the club to set
	 */
	public void setClub(Club club) {
		this.club = club;
	}

}
