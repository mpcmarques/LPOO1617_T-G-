package game.cli;
import java.util.*;
import game.logic.*;

public class Main {

	public static void main(String[] args) {
		System.out.println("Game Started!");
		//	Creates the first level
		// Start a new game
		Game game = new Game(new GameMap(DefaultMaps.map1));
		game.getGameMap().randomGuardType();
		game.getGameMap().setGuardDefaultMoves();

		// Print map
		game.printGame();

		// Start user input scanner
		Scanner user_input = new Scanner( System.in );
		String typed;	

		//	Game Loop
		while(game.getState() != GameState.over){

			// Ask for user input
			typed = user_input.next();
			//  Update game
			game.updateGame(typed);

			//	If first map is completed, change to map 2
			if (game.isGamemapCompleted()){
				game.changeMap(new GameMap(DefaultMaps.map2));
			}

			//	Print game
			game.printGame();
		}

		//		close scanner
		user_input.close();

		// Final game sentence
		if (game.getEndStatus() == EndStatus.DEFEAT) {
			System.out.println("GAME OVER!!!!");
		} else {
			System.out.println("YOU WON!!!!");
		}
	}
}
