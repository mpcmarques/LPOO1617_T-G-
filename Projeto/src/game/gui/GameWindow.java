package game.gui;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import game.logic.*;
import net.miginfocom.swing.MigLayout;

public class GameWindow extends JFrame implements KeyListener {
	
	// MARK: PROPRIETIES
	private Game game;
	private GamePanel gamePanel;


	/**
	 * Create the frame.
	 */
	public GameWindow(Game game) {
		//	Add key listener
		addKeyListener(this);
		
		// create new window
		setTitle("Game Window");
		setBounds(100, 100, 600, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//	Creates game panel
		GamePanel panel = new GamePanel(game.getGameMap().getMap());
		panel.requestFocusInWindow();
		setContentPane(panel);
		setGamePanel(panel);
		//	Create game
		setGame(game);
		
		getContentPane().setLayout(new MigLayout("", "[grow]", "[grow]"));
		setVisible(true);
	}
	
	/** 
	 * Updates game panel with new game screen
	 * */
	private void updateGamePanel(String str){
		//	Updates game
		this.game.updateGame(str);
		//	Updates map with new game
		this.gamePanel.setMap(game.getGameMap().getMap());
		this.gamePanel.repaint();
	}
	
	
	//	MARK: Keyboard delegates
	
	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		String cmd = "";
		System.out.println(e.getKeyCode());
		
		switch(e.getKeyCode()){
		//	Up
		case 38:
			cmd = "w";
			break;
		//	Down
		case 40:
			cmd = "s";
			break;
		//	Right
		case 39:
			cmd = "d";
			break;
		//	Left
		case 37:
			cmd = "a";
			break;
		// Esc
		case 27:
			break;
		}
		
		if (cmd != ""){
			updateGamePanel(cmd);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {}
	

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
