package game.gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import game.logic.DefaultMaps;
import game.logic.Game;
import game.logic.GameMap;
import game.logic.GuardTypes;
import game.logic.Ogre;
import net.miginfocom.swing.MigLayout;

public class MainViewController {

	private GameConfigWindow gameConfigWindow;
	private GameWindow gameWindow;
	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainViewController window = new MainViewController();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainViewController() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		showGameConfigWindow();
	}
	
	/** 
	 * Show game configuration window
	 * */
	private void showGameConfigWindow(){
		if (gameConfigWindow != null) gameConfigWindow.dispose();
		
		gameConfigWindow = new GameConfigWindow();
		gameConfigWindow.getContentPane().setLayout(new MigLayout("", "[grow]", "[grow]"));
		gameConfigWindow.setTitle("New game");
		gameConfigWindow.pack();
		gameConfigWindow.setVisible(true);
		gameConfigWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		gameConfigWindow.getBtnNewButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Create new game
				startGame();
			}
		});
		
		if (frame != null) frame.dispose();
		frame = gameConfigWindow;
	}
	
	
	/** 
	 * Starts a game, returns game that started
	 * */
	public void startGame(){
		int numberOfOgres;
		int selectedIndex = this.gameConfigWindow.comboBox.getSelectedIndex();

		//	Configure number of ogres
		try { 
			numberOfOgres = Integer.parseInt(gameConfigWindow.textField.getText());
		} catch(NumberFormatException e){
			JOptionPane.showMessageDialog(null,"Please type a valid number of ogres, between 1 and 5.");
			return;
		}

		//	Create game at level one
		GameMap levelOne = new GameMap(DefaultMaps.map1);
		GameMap levelTwo = new GameMap(DefaultMaps.map2);
		//	Create game
		Game game = new Game(levelOne);
		game.addGameMap(levelTwo);
		
		//	Configure guard
		levelOne.changeGuardType(GuardTypes.values()[selectedIndex]);

		// Configure maps
		levelOne.setCanGuardsAndOgresMove(true);
		levelOne.setGuardDefaultMoves();
		levelTwo.setCanGuardsAndOgresMove(true);
		levelTwo.addClubToOgres();

		//	Verify number of ogres
		if (0 < numberOfOgres && numberOfOgres <= 5) {
			//	Config number of ogres
			for (int i = 0; i < numberOfOgres-1; i++){
				levelTwo.addOgre(new Ogre(levelTwo.getOgres().get(0).getX(), levelTwo.getOgres().get(0).getY(), true));
			}
			// Show game window
			//	Show game on screen;
			gameConfigWindow.lblNewLabel_1.setText("Game started!");
			//	Enable buttons
			this.enableButtons(true);
			
			//	Opens game frame
			showGameWindow(game);
		} else {
			JOptionPane.showMessageDialog(null,"Number of ogres must be between 1 and 5.");
			return;
		}
	}
	
	/** 
	 * @brief Enables user buttons
	 * */
	private void enableButtons(boolean enable){
		gameConfigWindow.getBtnNewButton().setEnabled(!enable);
	}
	
	/** 
	 * Show game window
	 * @param game	Game that will be shown in the window
	 * */
	private void showGameWindow(Game game){
		if (gameWindow != null) gameWindow.dispose();
		gameWindow = new GameWindow(game);
		
		//	Button new game
		gameWindow.btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Show config
				showGameConfigWindow();
			}
		});
		
		if (frame != null) frame.dispose();
		frame = gameConfigWindow;
	}
}
