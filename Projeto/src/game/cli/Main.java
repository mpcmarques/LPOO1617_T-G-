package game.cli;
import java.util.*;
import game.logic.*;

public class Main {

	public static void main(String[] args) {
		System.out.println("Game Started!");
		// Start a new game
		Game game = new Game();
		//	Starts first level
		game.startFirstLevel();

		// Start user input scanner
		Scanner user_input = new Scanner( System.in );
		String typed;	
		
		//	Game Loop
		while(game.getState() != GameState.ended){
			// Ask for user input
			typed = user_input.next();
			//  Update game
			game.updateGame(typed);
			//	Print game
			game.printGame();
		}

		//		close scanner
		user_input.close();
		System.out.println("Game finished!");
	}
}
