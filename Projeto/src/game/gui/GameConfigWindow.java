package game.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import game.logic.DefaultMaps;
import game.logic.Game;
import game.logic.GameMap;
import game.logic.GuardTypes;
import game.logic.Ogre;
import net.miginfocom.swing.MigLayout;

public class GameConfigWindow extends JFrame{
	
	JTextField textField;
	JLabel lblNewLabel;
	JComboBox comboBox;
	JButton btnNewButton;
	JLabel lblNewLabel_1;
	JButton btnExit;
	
	/** 
	 * Constructor
	 * */
	GameConfigWindow(){
		setTitle("New game");
		setBounds(100, 100, 600, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new MigLayout("", "[][][grow][]", "[][][grow][]"));

		JLabel lblNumberOfOgres = new JLabel("Number of ogres:");
		getContentPane().add(lblNumberOfOgres, "cell 0 0,alignx left");

		textField = new JTextField();
		getContentPane().add(textField, "cell 1 0");
		textField.setColumns(10);

		lblNewLabel = new JLabel("Guard personality:");
		getContentPane().add(lblNewLabel, "flowx,cell 0 1");

		setBtnNewButton(new JButton("New Game"));
		
		comboBox = new JComboBox(GuardTypes.values());
		comboBox.setToolTipText("text\n");
		getContentPane().add(comboBox, "cell 1 1");
		getContentPane().add(getBtnNewButton(), "cell 0 2 4 1,alignx center");

		lblNewLabel_1 = new JLabel("You can start a new game.");
		getContentPane().add(lblNewLabel_1, "cell 0 3 3 1");

		btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//	Exit
				System.exit(0);
			}
		});
		getContentPane().add(btnExit, "cell 3 3,alignx center");
	}
	
	/** 
	 * Starts a game, returns game that started
	 * */
	public Game startGame(){
		int numberOfOgres;
		int selectedIndex = comboBox.getSelectedIndex();

		//	Configure number of ogres
		try { 
			numberOfOgres = Integer.parseInt(this.textField.getText());
		} catch(NumberFormatException e){
			JOptionPane.showMessageDialog(null,"Please type a valid number of ogres, between 1 and 5.");
			return null;
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
			lblNewLabel_1.setText("Game started!");
			//	Enable buttons
			this.enableButtons(true);
			
			//	TODO
			//frmMainWindow.dispose();
			//frmMainWindow  = new GameWindow(game);
			return game;

		} else {
			JOptionPane.showMessageDialog(null,"Number of ogres must be between 1 and 5.");
			return null;
		}
	}
	
	/** 
	 * @brief Enables user buttons
	 * */
	private void enableButtons(boolean enable){
		getBtnNewButton().setEnabled(!enable);
	}

	/**
	 * @return the btnNewButton
	 */
	public JButton getBtnNewButton() {
		return btnNewButton;
	}

	/**
	 * @param btnNewButton the btnNewButton to set
	 */
	public void setBtnNewButton(JButton btnNewButton) {
		this.btnNewButton = btnNewButton;
	}
}
