package game.cli;
import java.util.*;

import game.logic.Game;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Game game = new Game();
		game.start();

		while(game.getEnded() != true){
			String typed;
			// Ask for user input
			// Start scanner
			Scanner user_input = new Scanner( System.in );
			//	Ask user inputs
			typed = user_input.next();

			//  Update game
			game.updateGame(typed);
			//	Print game
			game.printGame();

			//	close scanner
			user_input.close();
		}
	}

	public String askUserInput(){
		//		Start scanner
		Scanner user_input = new Scanner( System.in );
		String typed;

		//		Ask user inputs
		typed = user_input.next();



		//	End game
		user_input.close();

		return typed;
	}

}
