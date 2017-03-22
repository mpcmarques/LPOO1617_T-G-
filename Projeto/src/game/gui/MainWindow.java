package game.gui;


import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import game.logic.*;

import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import net.miginfocom.swing.MigLayout;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.AbstractAction;
import javax.swing.Action;
import java.awt.Font;

public class MainWindow {

	private JFrame frmMainWindow;
	private JTextField textField;
	private JLabel lblNewLabel;
	private JComboBox comboBox;
	private JButton btnNewButton;
	private JTextArea textArea;
	private JButton btnUp;
	private JButton btnLeft;
	private JButton btnRight;
	private JButton btnDown;
	private JLabel lblNewLabel_1;
	private JButton btnExit;


	// MARK: PROPRIETIES
	private Game game;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frmMainWindow.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmMainWindow = new JFrame();
		frmMainWindow.setTitle("Main Window");
		frmMainWindow.setBounds(100, 100, 600, 600);
		frmMainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMainWindow.getContentPane().setLayout(new MigLayout("", "[grow][][][grow][grow]", "[][][][grow][][][][grow][]"));

		JLabel lblNumberOfOgres = new JLabel("Number of ogres:");
		frmMainWindow.getContentPane().add(lblNumberOfOgres, "cell 0 0,alignx left");

		textField = new JTextField();
		frmMainWindow.getContentPane().add(textField, "cell 1 0");
		textField.setColumns(10);

		lblNewLabel = new JLabel("Guard personality:");
		frmMainWindow.getContentPane().add(lblNewLabel, "flowx,cell 0 1");

		btnNewButton = new JButton("New Game");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Create new game
				startGame();
			}
		});
		comboBox = new JComboBox(GuardTypes.values());
		comboBox.setToolTipText("text\n");
		frmMainWindow.getContentPane().add(comboBox, "cell 1 1");

		textArea = new JTextArea();
		textArea.setFont(new Font("Courier New", Font.BOLD, 15));
		frmMainWindow.getContentPane().add(textArea, "cell 0 2 2 6,grow");
		frmMainWindow.getContentPane().add(btnNewButton, "cell 3 2 2 1,alignx center");

		btnUp = new JButton("Up");
		btnUp.setEnabled(false);
		btnUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//	update game move up
				updateGame("w");
			}
		});
		frmMainWindow.getContentPane().add(btnUp, "cell 3 4 2 1,alignx center");

		btnLeft = new JButton("Left");
		btnLeft.setEnabled(false);
		btnLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//	update game move left
				updateGame("a");
			}
		});
		frmMainWindow.getContentPane().add(btnLeft, "flowx,cell 3 5,alignx center");

		btnRight = new JButton("Right");
		btnRight.setEnabled(false);
		btnRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//	update game move right
				updateGame("d");
			}
		});
		frmMainWindow.getContentPane().add(btnRight, "cell 4 5,alignx center");

		btnDown = new JButton("Down");
		btnDown.setEnabled(false);
		btnDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//	update game move down
				updateGame("s");
			}
		});
		frmMainWindow.getContentPane().add(btnDown, "cell 3 6 2 1,alignx center");

		lblNewLabel_1 = new JLabel("You can start a new game.");
		frmMainWindow.getContentPane().add(lblNewLabel_1, "cell 0 8");

		btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//	Exit
				System.exit(0);
			}
		});
		frmMainWindow.getContentPane().add(btnExit, "cell 3 8 2 1,alignx center");
	}

	/**
	 * Starts game
	 * */
	private void startGame(){
		Guard guard = new Rookie();
		int numberOfOgres;
		int selectedIndex = comboBox.getSelectedIndex();

		GameMap levelOne = new LevelOne(DefaultMaps.map1, GuardTypes.values()[selectedIndex], true);
		setGame(new Game(levelOne));
		
		//	Configure number of ogres
		try { 
			numberOfOgres = Integer.parseInt(this.textField.getText());
		} catch(NumberFormatException e){
			JOptionPane.showMessageDialog(this.frmMainWindow,"Please type a valid number of ogres, between 1 and 5.");
			return;
		}
		
		//	Verify number of ogres
		if (0 < numberOfOgres && numberOfOgres <= 5) {
			game.setNumberOfOgres(numberOfOgres);
		} else {
			JOptionPane.showMessageDialog(this.frmMainWindow,"Number of ogres must be between 1 and 5.");
			return;
		}

		//	Show game on screen;
		textArea.setText(game.printGame());
		lblNewLabel_1.setText("Game started!");

		//	Enable buttons
		this.enableButtons(true);
	}
	
	/** 
	 * Updates game
	 * */
	private void updateGame(String typed){
		if (!game.isGameOver()){
			this.game.updateGame(typed);
			//	Show game on screen;
			textArea.setText(game.printGame());

			if(game.isGameOver()){
				//
				this.enableButtons(false);
				// get final game sentence
				if (game.getEndStatus() == EndStatus.DEFEAT) {
					lblNewLabel_1.setText("Game Over!");
				} else {
					lblNewLabel_1.setText("YOU WON!");
				}
			}
		}
	}

	/** 
	 * @brief Enables user buttons
	 * */
	private void enableButtons(boolean enable){
		btnRight.setEnabled(enable);
		btnLeft.setEnabled(enable);
		btnUp.setEnabled(enable);
		btnDown.setEnabled(enable);
	}

	/**
	 * @return the game
	 */
	public Game getGame() {
		return game;
	}

	/**
	 * @param game the game to set
	 */
	public void setGame(Game game) {
		this.game = game;
	}
}
