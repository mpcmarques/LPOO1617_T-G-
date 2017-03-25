package game.logic;

import java.util.ArrayList;

import game.services.RandomService;

public class LevelOne extends GameMap {

	/** 
	 * Constructor
	 * */
	public LevelOne(char[][] map, GuardTypes guardTyp, boolean isGuardAllowedToMove){
		//	Call super
		super(map);
		GuardTypes guardType = guardTyp;

		// Sort guard type if guard is nil, sort guard type
		if (guardType == null) {
			guardType = GuardTypes.values()[RandomService.getRandomInt(0, 2)];
		}
		
		//	Populates map
		populateMapWithObjects(map, guardType);


		//	Set guard moves
		if(this.getGuard() != null){
			setGuardMoves();
		}
	}
	
	/** 
	 * Populates Gamemap map with objects.
	 * */
	private void populateMapWithObjects(char[][] map, GuardTypes guardType){
		//		Get guard from map
		int row, col;
		for(row = 0; row < map.length; row++){
			for(col = 0; col < map[row].length; col++){
				char value = map[col][row];
				if (value == 'G'){
					switch(guardType.ordinal()){
					case 2:
						setGuard(new Suspicious(row,col));
						break;
					case 0:
						setGuard(new Rookie(row,col));
						break;
					case 1:
						setGuard(new Drunken(row,col));
						break;
					default:
						break;
					}

					//	Add cell to map
					this.addCell(getGuard());
				}
			}
		}
	}
}
