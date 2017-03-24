package game.gui;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.InputMap;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.KeyStroke;

import game.logic.Cell;
import game.logic.Club;
import game.logic.Door;
import game.logic.Drunken;
import game.logic.Game;
import game.logic.Guard;
import game.logic.Hero;
import game.logic.Key;
import game.logic.Lever;
import game.logic.Ogre;
import game.logic.Wall;

import net.miginfocom.swing.MigLayout;

/** 
 * It's the game panel, it's composed by the background where the game is draw and a table to represent mouse clicked events.
 * */

public class GameScreenPanel extends JPanel implements KeyListener, MouseListener {

	private JTable table;

	private GameTableModel gameTableModel;
	private int cellDimensionX;
	private int cellDimensionY;
	private Game game;
	
	/**
	 * Create the panel.
	 */
	public GameScreenPanel(Game game) {
		//	Add listeners
		this.addListeners();
		
		//	Setup object
		this.setGameTableModel(new GameTableModel(game.getGameMap().getMap()));
		this.game = game;
		this.cellDimensionX = 0;
		this.cellDimensionY = 0;
		setLayout(new MigLayout("", "[grow]", "[grow]"));
	}
	
	/** 
	 * Add listeners
	 * */
	private void addListeners(){
		addKeyListener(this);
		addMouseListener(this);
	}

	/** 
	 * Paints components
	 * */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);  

		paintPanel(g);
	}

	/** 
	 * Paints all game panel
	 * */
	public void paintPanel(Graphics g){
		//	Calculate cell dimensions
		cellDimensionX = this.getWidth()/gameTableModel.getColumnCount();
		cellDimensionY = this.getHeight()/gameTableModel.getRowCount();

		//	Paint floor
		this.paintFloor(g, cellDimensionX, cellDimensionY);
		
		//	Paint objects
		this.paintObjects(g, cellDimensionX, cellDimensionY);
	}

	/** 
	 * Paints map with floor texture
	 * */
	private void paintFloor(Graphics g, int dimensionX, int dimensionY){
		int x,y;
		int finalX = dimensionX / 3;
		int finalY = dimensionY / 3;

		//	Loop trough map cells
		for(x = 0; x < gameTableModel.getColumnCount() * 3 ; x++){
			for(y = 0; y < gameTableModel.getRowCount()  * 3 ; y++){
				//	paints floor
				g.drawImage(ImageService.shared.getFloorImage(), x * finalX, y * finalY, finalX, finalY, this);
			}
		}
	}

	/** 
	 * Paints second level objects
	 * */
	private void paintObjects(Graphics g, int dimensionX, int dimensionY){
		int x,y;
		//		Loop throught data
		for(y = 0; y < gameTableModel.getRowCount(); y++){
			for(x = 0; x < gameTableModel.getColumnCount() ; x++){
				Cell cell = gameTableModel.getCellAt(x,y);
				
				//	If it is wall
				if (cell instanceof Wall){
					g.drawImage(ImageService.shared.getWallImage(), x * dimensionX, y * dimensionY, dimensionX, dimensionY, this);
				}
				//	If it is a hero
				else if (cell instanceof Hero){
					Hero hero = (Hero)cell;
					//	If hero is with key
					if (hero.hasKey()){
						g.drawImage(ImageService.shared.getHeroKeyImage(), x * dimensionX, y * dimensionY, dimensionX, dimensionY, this);
					} 
					//	If hero is with weapon
					else if (hero.hasWeapon()){
						g.drawImage(ImageService.shared.getHeroClubImage(), x * dimensionX, y * dimensionY, dimensionX, dimensionY, this);
					}
					//	Hero don't have nothing
					else {
						g.drawImage(ImageService.shared.getHeroImage(), x * dimensionX, y * dimensionY, dimensionX, dimensionY, this);
					}
				}
				//	If it is a door
				else if (cell instanceof Door){
					Door door = (Door)cell;
					//	Check door sprite
					if (door.isOpen()){
						g.drawImage(ImageService.shared.getOpenDoorImage(), x * dimensionX, y * dimensionY, dimensionX, dimensionY, this);
					} else {
						g.drawImage(ImageService.shared.getClosedDoorImage(), x * dimensionX, y * dimensionY, dimensionX, dimensionY, this);
					}
				}
				//	If it is guard
				else if (cell instanceof Guard){
					Guard guard = (Guard)cell;
					//	Check if guard is sleeping
					if (guard instanceof Drunken && ((Drunken)guard).isSleeping()){
						g.drawImage(ImageService.shared.getGuardSleepingImage(), x * dimensionX, y * dimensionY, dimensionX, dimensionY, this);
					} else {
						//	Guard is not sleeping
						g.drawImage(ImageService.shared.getGuardImage(), x * dimensionX, y * dimensionY, dimensionX, dimensionY, this);
					}
				}
				//	If it is lever
				else if (cell instanceof Lever){
					g.drawImage(ImageService.shared.getLeverImage(), x * dimensionX, y * dimensionY, dimensionX, dimensionY, this);
				}
				// If it is key
				else if (cell instanceof Key){
					g.drawImage(ImageService.shared.getKeyImage(), x * dimensionX, y * dimensionY, dimensionX, dimensionY, this);
				}
				//	If it is ogre
				else if (cell instanceof Ogre){
					g.drawImage(ImageService.shared.getOgreImage(), x * dimensionX, y * dimensionY, dimensionX, dimensionY, this);
				}
				//	If it is club
				else if (cell instanceof Club){
					g.drawImage(ImageService.shared.getClubImage(), x * dimensionX, y * dimensionY, dimensionX, dimensionY, this);
				}
			}
		}
	}
	
	/** 
	 * Updates game panel with new game screen
	 * */
	private void updateGamePanel(String str){
		//	Updates game
		this.game.updateGame(str);
		//	Updates map with new game
		this.gameTableModel = new GameTableModel(this.game.getGameMap().getMap());
		this.repaint();
		
		//	Print map on console
		System.out.println(game.printGame());
	}
	
	//	MARK: Keyboard delegates
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		String cmd = "";
		
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
	
	// MARK: Mouse Delegates
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (this.cellDimensionX != 0 && this.cellDimensionY != 0){
			int x = e.getX()/cellDimensionX;
			int y = e.getY()/cellDimensionY;
			System.out.println(gameTableModel.getCellAt(x, y));
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {		
	}

	@Override
	public void mouseReleased(MouseEvent e) {	
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}



	/**
	 * @return the table
	 */
	public JTable getTable() {
		return table;
	}

	/**
	 * @param table the table to set
	 */
	public void setTable(JTable table) {
		this.table = table;
	}

	/**
	 * @return the gameTableModel
	 */
	public GameTableModel getGameTableModel() {
		return gameTableModel;
	}

	/**
	 * @param gameTableModel the gameTableModel to set
	 */
	public void setGameTableModel(GameTableModel gameTableModel) {
		this.gameTableModel = gameTableModel;
	}
}
