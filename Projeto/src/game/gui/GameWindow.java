package game.gui;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import game.logic.*;
import net.miginfocom.swing.MigLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GameWindow extends JFrame { 

	// MARK: PROPRIETIES
	private Game game;
	private GameScreenPanel gamePanel;

	//	Buttons
	JButton btnRemoveElement;
	JButton btnEditGame;
	JButton btnApply;
	JButton btnAddWall;
	JButton btnAddDoor;
	JButton btnAddNewRow;
	JButton btnAddLever;
	JButton btnAddGuard;
	JButton btnNewButton;
	JButton btnAddOgre;
	JButton btnAddPilar;

	/**
	 * Create the frame.
	 */
	public GameWindow(Game game) {

		// create new window
		setTitle("Game Window");
		setBounds(100, 100, 900, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//	Creates game panel
		GameScreenPanel panel = new GameScreenPanel(game);
		//setContentPane(panel);
		setGamePanel(panel);
		//	Create game
		setGame(game);

		getContentPane().setLayout(new MigLayout("", "[][][][][grow]", "[grow][grow][][][][][][grow][][][grow][][][grow][][grow]"));

		this.btnEditGame = new JButton("Edit game");
		btnEditGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//	Edit game
				editGame();
			}
		});
		btnEditGame.setFocusable(false);
		getContentPane().add(btnEditGame, "cell 0 0,alignx left,aligny center");
		getContentPane().add(panel, "cell 4 0 1 16,grow");

		panel.requestFocusInWindow();
		panel.setFocusable(true);

		JLabel lblGeneral = new JLabel("General");
		lblGeneral.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(lblGeneral, "cell 0 2,alignx left");

		this.btnRemoveElement = new JButton("Remove");
		btnRemoveElement.setEnabled(false);
		btnRemoveElement.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//	Remove element
				gamePanel.setMouseAction(GameWindowEditMouseActions.removeElement);
			}
		});
		btnRemoveElement.setFocusable(false);

		getContentPane().add(btnRemoveElement, "cell 0 3");

		this.btnAddDoor = new JButton("Add Door");
		btnAddDoor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//	Add door
				gamePanel.setMouseAction(GameWindowEditMouseActions.addDoor);
			}
		});
		btnAddDoor.setFocusable(false);
		btnAddDoor.setEnabled(false);
		getContentPane().add(btnAddDoor, "cell 0 4,alignx left,aligny center");

		this.btnAddPilar = new JButton("Add Pilar");
		btnAddPilar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//	Add pilar
				gamePanel.setMouseAction(GameWindowEditMouseActions.addPilar);
			}
		});
		btnAddPilar.setFocusable(false);
		btnAddPilar.setEnabled(false);
		getContentPane().add(btnAddPilar, "cell 1 4,alignx left");

		this.btnAddWall = new JButton("Add Wall");
		btnAddWall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//	Add Wall
				gamePanel.setMouseAction(GameWindowEditMouseActions.addWall);
			}
		});
		btnAddWall.setEnabled(false);
		btnAddWall.setFocusable(false);
		getContentPane().add(btnAddWall, "cell 0 5,alignx left");

		JButton btnAddHero = new JButton("Add Hero");
		btnAddHero.setEnabled(false);
		getContentPane().add(btnAddHero, "cell 1 5,alignx left");

		this.btnAddNewRow = new JButton("Add New Row");
		btnAddNewRow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//	Add New Row

			}
		});
		btnAddNewRow.setEnabled(false);
		btnAddNewRow.setFocusable(false);
		getContentPane().add(btnAddNewRow, "cell 0 6,alignx left");

		JButton btnAddNewColumn = new JButton("Add New Column");
		btnAddNewColumn.setEnabled(false);
		getContentPane().add(btnAddNewColumn, "cell 1 6,alignx left");

		JLabel lblFirstLevel = new JLabel("First Level");
		getContentPane().add(lblFirstLevel, "cell 0 8,alignx left");

		this.btnAddLever = new JButton("Add Lever");
		btnAddLever.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//	Add lever
				gamePanel.setMouseAction(GameWindowEditMouseActions.addLever);
			}
		});
		btnAddLever.setEnabled(false);
		btnAddLever.setFocusable(false);
		getContentPane().add(btnAddLever, "cell 0 9,alignx left,aligny center");

		this.btnAddGuard = new JButton("Add Guard");
		btnAddGuard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//	Add lever
				gamePanel.setMouseAction(GameWindowEditMouseActions.addGuard);
			}
		});
		btnAddGuard.setEnabled(false);
		btnAddGuard.setFocusable(false);
		getContentPane().add(btnAddGuard, "cell 1 9,alignx left");

		JLabel lblSecondLevel = new JLabel("Second Level");
		getContentPane().add(lblSecondLevel, "cell 0 11,alignx left");

		this.btnAddOgre = new JButton("Add Ogre");
		btnAddOgre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//	Add ogre
				gamePanel.setMouseAction(GameWindowEditMouseActions.addOgre);
			}
		});
		btnAddOgre.setFocusable(false);
		btnAddOgre.setEnabled(false);
		getContentPane().add(btnAddOgre, "cell 0 12");

		this.btnNewButton = new JButton("Add Key");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//	Add key
				gamePanel.setMouseAction(GameWindowEditMouseActions.addKey);
			}
		});
		btnNewButton.setFocusable(false);
		btnNewButton.setEnabled(false);
		getContentPane().add(btnNewButton, "cell 1 12");

		JLabel lblApplyChanges = new JLabel("Apply Changes");
		getContentPane().add(lblApplyChanges, "cell 0 14,alignx left");

		this.btnApply = new JButton("Apply");
		btnApply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Apply game changes
				applyGameChanges();
			}
		});
		btnApply.setEnabled(false);
		btnApply.setFocusable(false);
		getContentPane().add(btnApply, "cell 0 15,alignx left");

		setVisible(true);
	}

	/** 
	 * Updates game panel with new game screen
	 * */
	private void updateGamePanel(String str){
		//	Updates game
		this.game.updateGame(str);
		//	Updates map with new game
		//this.gamePanel.setMap(game.getGameMap().getMap());
		//this.gamePanel.repaint();

		//	Printmap on console
		System.out.println(game.printGame());
	}

	//	Edit game 
	/** 
	 * Enables game editing
	 * */
	private void editGame(){
		//	Stop game
		gamePanel.setEditing(true);

		//	Enable / Disable proper buttons
		this.enableEditingButtons(true);
	}
	/** 
	 * Enable / Disable editing buttons
	 * */
	private void enableEditingButtons(boolean value){
		this.btnRemoveElement.setEnabled(value);
		this.btnEditGame.setEnabled(!value);
		this.btnApply.setEnabled(value);
		this.btnAddWall.setEnabled(value);
		this.btnAddDoor.setEnabled(value);
		this.btnAddLever.setEnabled(value);
		this.btnAddGuard.setEnabled(value);
		this.btnNewButton.setEnabled(value);
		this.btnAddOgre.setEnabled(value);
		this.btnAddPilar.setEnabled(value);
	}
	/** 
	 * Apply editing changes
	 * */
	private void applyGameChanges(){
		//	Resume game
		gamePanel.setEditing(false);

		//	Enable / Disable editing buttons
		this.enableEditingButtons(false);
	}

	/**
	 * @return the gamePanel
	 */
	public GameScreenPanel getGamePanel() {
		return gamePanel;
	}
	/**
	 * @param panel the gamePanel to set
	 */
	public void setGamePanel(GameScreenPanel panel) {
		this.gamePanel = panel;
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
