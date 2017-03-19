package game.logic;

public class Game extends Object {
	private GameMap gameMap;
	private EndStatus endStatus;
	private GameState state;
	private boolean isFirstLevelCompleted;
	
	// Game instance
	static Game instance;

	public Game(GameMap gameMap){
		setState(GameState.started);
		setGameMap(gameMap);
		instance = this;
		setFirstLevelCompleted(false);
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
	
	public boolean isGameOver(){
		return this.getState() == GameState.over;
	}

	public void gameOver(){
		// END GAME, GAME Over
		setState(GameState.over);
		setEndStatus(EndStatus.DEFEAT);
	}
	
	public void levelCompleted(){
		//	Is on first level
		if( this.getGameMap() instanceof LevelOne){
			changeMap(new LevelTwo());
		} else {
			//	Finish game
			gameCompleted();
		}
	}
	
	public void gameCompleted(){
		// END GAME, GAME Over
		setState(GameState.over);
		setEndStatus(EndStatus.WIN);
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

	/**
	 * @return the isFirstLevelCompleted
	 */
	public boolean isFirstLevelCompleted() {
		return isFirstLevelCompleted;
	}

	/**
	 * @param isFirstLevelCompleted the isFirstLevelCompleted to set
	 */
	public void setFirstLevelCompleted(boolean isFirstLevelCompleted) {
		this.isFirstLevelCompleted = isFirstLevelCompleted;
	}
}
