package game.gui;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import game.logic.*;
import net.miginfocom.swing.MigLayout;

public class GameWindow extends JFrame { 
	
	// MARK: PROPRIETIES
	private Game game;
	private GameScreenPanel gamePanel;

	/**
	 * Create the frame.
	 */
	public GameWindow(Game game) {
		
		// create new window
		setTitle("Game Window");
		setBounds(100, 100, 600, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//	Creates game panel
		GameScreenPanel panel = new GameScreenPanel(game);
		//setContentPane(panel);
		setGamePanel(panel);
		//	Create game
		setGame(game);
		
		getContentPane().setLayout(new MigLayout("", "[grow]", "[grow]"));
		getContentPane().add(panel, "grow");
		
		panel.requestFocusInWindow();
		panel.setFocusable(true);
		
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
