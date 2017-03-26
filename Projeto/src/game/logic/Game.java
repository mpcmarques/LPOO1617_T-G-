package game.logic;

import java.util.ArrayList;

import game.services.RandomService;

public class Game extends Object {
	private ArrayList<GameMap> maps;
	private EndStatus endStatus;
	private GameState state;

	// Game instance
	static Game instance;

	/** 
	 * Constructor
	 * */
	public Game(GameMap gameMap){
		setState(GameState.started);
		maps = new ArrayList<GameMap>();
		maps.add(gameMap);
		instance = this;
	}

	/** 
	 * Updates game with typed string
	 * */
	public void updateGame(String typed){
		//	Updates game map
		getCurrentMap().updateGame(typed);
	}
	
	/** 
	 * Returns current map running in the game
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
	 * */
	public boolean isGameOver(){
		return this.getState() == GameState.over;
	}
	
	/** 
	 * Add game map
	 * @param Map to be added
	 * */
	public void addGameMap(GameMap map){
		maps.add(map);
	}

	public void gameOver(){
		// END GAME, GAME Over
		setState(GameState.over);
		setEndStatus(EndStatus.DEFEAT);
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
