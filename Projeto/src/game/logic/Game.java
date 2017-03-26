package game.logic;

import java.util.ArrayList;
/** 
 * Class represents a dungeon keep game
 * */
public class Game extends Object {
	private ArrayList<GameMap> maps;
	private EndStatus endStatus;
	private GameState state;

	// Game instance
	static Game instance;

	/** 
	 * Game constructor
	 * @param gameMap GameMap
	 * */
	public Game(GameMap gameMap){
		setState(GameState.started);
		maps = new ArrayList<GameMap>();
		maps.add(gameMap);
		instance = this;
	}

	/** 
	 * Updates game with typed string
	 * @param typed Typed string
	 * */
	public void updateGame(String typed){
		//	Updates game map
		getCurrentMap().updateGame(typed);
	}
	
	/** 
	 * Returns current map running in the game
	 * @return GameMap Current GameMap
	 * */
	public GameMap getCurrentMap(){
		if (maps.size() != 0) return maps.get(0);
		else return null;
	}
	
	/** 
	 * Prints game on console
	 * */
	public void printGame(){
		if (getCurrentMap() != null){
			System.out.println(getCurrentMap());
		} 
	}

	/** 
	 * Finish current map
	 * */
	public void finishCurrentMap(){
		this.maps.remove(getCurrentMap());
		//	If there is no maps, game is over
		if (maps.size() == 0) {
			//	End game
			gameCompleted();
		}
	}
	
	/** 
	 * Returns true if game is over
	 * @return boolean True if gamestate is over
	 * */
	public boolean isGameOver(){
		return this.getState() == GameState.over;
	}
	
	/** 
	 * Add game map
	 * @param map Map to be added
	 * */
	public void addGameMap(GameMap map){
		maps.add(map);
	}
	/** 
	 * Ends the game with game over
	 * */
	public void gameOver(){
		// END GAME, GAME Over
		setState(GameState.over);
		setEndStatus(EndStatus.DEFEAT);
	}
	/** 
	 * Ends the game with game win
	 * */
	public void gameCompleted(){
		// END GAME, GAME Over
		setState(GameState.over);
		setEndStatus(EndStatus.WIN);
	}

	//	MARK: Getters and Setters

	/**
	 * Get game state
	 * @return the state
	 */
	public GameState getState() {
		return state;
	}

	/**
	 * Set game state
	 * @param state the state to set
	 */
	public void setState(GameState state) {
		this.state = state;
	}

	/**
	 * Return the game end status
	 * @return the endStatus
	 */
	public EndStatus getEndStatus() {
		return endStatus;
	}

	/**
	 * Set game end status
	 * @param endStatus the endStatus to set
	 */
	public void setEndStatus(EndStatus endStatus) {
		this.endStatus = endStatus;
	}
}
