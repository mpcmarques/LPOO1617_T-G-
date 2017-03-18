package game.logic;

public class Game extends Object {
	private GameMap gameMap;
	private EndStatus endStatus;
	private GameState state;
	
	// Game instance
	static Game instance;

	public Game(GameMap gameMap){
		setState(GameState.started);
		setGameMap(gameMap);
		instance = this;
	}

	public void updateGame(String typed){
		//	Updates game map
		gameMap.updateGame(typed);
	}

	/** 
	 * Prints game on screen
	 * */
	public void printGame(){
		System.out.println(gameMap.getMap());
	}
	
	/** 
	 * Changes map
	 * */
	public void changeMap(GameMap map){
		this.setGameMap(map);
	}
	
	public void finishGame(){
		this.setState(GameState.over);
	}
	
	public boolean isGameOver(){
		return this.getState() == GameState.over;
	}

	//	MARK: Getters and Setters

	/**
	 * @return the state
	 */
	public GameState getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(GameState state) {
		this.state = state;
	}

	
	/**
	 * @return the gameMap
	 */
	public GameMap getGameMap() {
		return gameMap;
	}

	/**
	 * @param gameMap the gameMap to set
	 */
	public void setGameMap(GameMap gameMap) {
		this.gameMap = gameMap;
	}

	/**
	 * @return the endStatus
	 */
	public EndStatus getEndStatus() {
		return endStatus;
	}

	/**
	 * @param endStatus the endStatus to set
	 */
	public void setEndStatus(EndStatus endStatus) {
		this.endStatus = endStatus;
	}
}
