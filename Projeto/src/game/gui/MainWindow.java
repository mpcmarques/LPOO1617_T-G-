package game.gui;


import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import game.logic.*;
import net.miginfocom.swing.MigLayout;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import java.awt.Font;

public class MainWindow {

	private JFrame frmMainWindow;
	private JTextField textField;
	private JLabel lblNewLabel;
	private JComboBox comboBox;
	private JButton btnNewButton;
	private JLabel lblNewLabel_1;
	private JButton btnExit;
	private GamePanel gamePanel;

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
		showGameConfigWindow();
	}
	
	/** 
	 * Show game config
	 * */
	private void showGameConfigWindow(){
		//	Dispose previous window
		if (frmMainWindow != null){
			frmMainWindow.dispose();
		}
		
		frmMainWindow = new JFrame();
		frmMainWindow.setTitle("New game");
		frmMainWindow.setBounds(100, 100, 600, 200);
		frmMainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMainWindow.getContentPane().setLayout(new MigLayout("", "[][][grow][]", "[][][grow][]"));

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
				frmMainWindow.getContentPane().add(btnNewButton, "cell 0 2 4 1,alignx center");

		lblNewLabel_1 = new JLabel("You can start a new game.");
		frmMainWindow.getContentPane().add(lblNewLabel_1, "cell 0 3 3 1");
				
						btnExit = new JButton("Exit");
						btnExit.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								//	Exit
								System.exit(0);
							}
						});
						frmMainWindow.getContentPane().add(btnExit, "cell 3 3,alignx center");
	}

	/**
	 * Starts game
	 * */
	private void startGame(){
		int numberOfOgres;
		int selectedIndex = comboBox.getSelectedIndex();
		
		//	Create game at level one
		GameMap levelOne = new LevelOne(DefaultMaps.map1, GuardTypes.values()[selectedIndex], true);
		Game game = new Game(levelOne);
		
		//	Configure number of ogres
		try { 
			numberOfOgres = Integer.parseInt(this.textField.getText());
		} catch(NumberFormatException e){
			JOptionPane.showMessageDialog(null,"Please type a valid number of ogres, between 1 and 5.");
			return;
		}

		//	Verify number of ogres
		if (0 < numberOfOgres && numberOfOgres <= 5) {
			// Show game window
			frmMainWindow.dispose();
			frmMainWindow  = new GameWindow(game);
			
		} else {
			JOptionPane.showMessageDialog(null,"Number of ogres must be between 1 and 5.");
			return;
		}

		//	Show game on screen;
		lblNewLabel_1.setText("Game started!");

		//	Enable buttons
		this.enableButtons(true);
	}

	/** 
	 * @brief Enables user buttons
	 * */
	private void enableButtons(boolean enable){
		btnNewButton.setEnabled(!enable);
	}
	
	
	/**
	 * @return the gamePanel
	 */
	public GamePanel getGamePanel() {
		return gamePanel;
	}

	/**
	 * @param gamePanel the gamePanel to set
	 */
	public void setGamePanel(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}
}
