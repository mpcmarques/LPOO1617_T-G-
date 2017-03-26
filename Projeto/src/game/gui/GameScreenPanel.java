package game.gui;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.InputMap;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.KeyStroke;

import game.logic.*;
import net.miginfocom.swing.MigLayout;

/** 
 * It's the game panel, where the game is draw and where the game responds to clicks and keyboard
 * */
public class GameScreenPanel extends JPanel implements KeyListener, MouseListener {

	private int cellDimensionX;
	private int cellDimensionY;
	private Game game;
	private GameWindowEditMouseActions mouseAction;
	private boolean isEditing;

	/**
	 * Create the panel.
	 */
	public GameScreenPanel(Game game) {
		//	Add listeners
		this.addListeners();

		//	Setup object
		this.game = game;
		this.isEditing = false;
		this.cellDimensionX = 0;
		this.cellDimensionY = 0;
		this.mouseAction = GameWindowEditMouseActions.none;
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
		if (game.getCurrentMap() != null){
			//	Calculate cell dimensions
			cellDimensionX = this.getWidth()/game.getCurrentMap().getNumberOfRows();
			cellDimensionY = this.getHeight()/game.getCurrentMap().getNumberLines();

			//	Paint floor
			this.paintFloor(g, cellDimensionX, cellDimensionY);

			//	Paint objects
			this.paintObjects(g, cellDimensionX, cellDimensionY);
		}

		if (game.isGameOver()){
			// Final game sentence
			if (game.getEndStatus() == EndStatus.DEFEAT) {
				System.out.println("GAME OVER!!!!");
				g.drawImage(ImageService.shared.getGameOverImage(), 0, 0,500,600, this);
			} else {
				System.out.println("YOU WON!!!!");
				g.drawImage(ImageService.shared.getYouWonImage(), 0, 0, 600,600,  this);
			}
		}
	}

	/** 
	 * Paints map with floor texture
	 * */
	private void paintFloor(Graphics g, int dimensionX, int dimensionY){
		int x,y;
		int finalX = dimensionX / 2;
		int finalY = dimensionY / 2;

		//	Loop trough map cells
		for(y = 0; y < game.getCurrentMap().getNumberLines()* 2 ; y++){
			for(x = 0; x < game.getCurrentMap().getNumberOfRows()  * 2 ; x++){
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
		for(y = 0; y < game.getCurrentMap().getNumberLines(); y++){
			for(x = 0; x < game.getCurrentMap().getNumberOfRows() ; x++){
				GameObject cell = game.getCurrentMap().getElementAt(x, y);

				//	If it is wall
				if (cell instanceof Wall){
					g.drawImage(ImageService.shared.getWallImage(), x * dimensionX, y * dimensionY, dimensionX, dimensionY, this);
				}
				//	If it is a hero
				else if (cell instanceof Hero){
					paintHero(g,(Hero)cell, x,y, dimensionX, dimensionY);
				}
				//	If it is a door
				else if (cell instanceof Door){
					paintDoor(g,(Door)cell, x,y, dimensionX, dimensionY);
				}
				//	If it is guard
				else if (cell instanceof Guard){
					paintGuard(g,(Guard)cell, x,y, dimensionX, dimensionY);
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
					paintOgre(g, (Ogre)cell, x ,y, dimensionX, dimensionY);
				}
				//	If it is ogre club
				else if (cell instanceof OgreClub){
					g.drawImage(ImageService.shared.getOgreClubImage(), x * dimensionX, y * dimensionY, dimensionX, dimensionY, this);
				}
				//	If it is club
				else if (cell instanceof Club){
					g.drawImage(ImageService.shared.getClubImage(), x * dimensionX, y * dimensionY, dimensionX, dimensionY, this);
				}
				//	If it is pilar
				else if (cell instanceof Pilar){
					g.drawImage(ImageService.shared.getPilarImage(), x * dimensionX, y * dimensionY, dimensionX, dimensionY, this);
				}
			}
		}
	}
	/** 
	 * Paints hero
	 * */
	private void paintHero(Graphics g, Hero hero, int x, int y, int dimensionX, int dimensionY){
		//	If hero is with key
		if (hero.hasKey()){
			g.drawImage(ImageService.shared.getHeroKeyImage(), x * dimensionX, y * dimensionY, dimensionX, dimensionY, this);
		} 
		//	If hero is with weapon
		else if (hero.isHasClub()){
			g.drawImage(ImageService.shared.getHeroClubImage(), x * dimensionX, y * dimensionY, dimensionX, dimensionY, this);
		}
		//	Hero don't have nothing
		else {
			g.drawImage(ImageService.shared.getHeroImage(), x * dimensionX, y * dimensionY, dimensionX, dimensionY, this);
		}
	}
	
	/** 
	 * Paint door
	 * */
	private void paintDoor(Graphics g, Door door, int x, int y, int dimensionX, int dimensionY){
		//	Check door sprite
		if (door.isOpen()){
			g.drawImage(ImageService.shared.getOpenDoorImage(), x * dimensionX, y * dimensionY, dimensionX, dimensionY, this);
		} else {
			g.drawImage(ImageService.shared.getClosedDoorImage(), x * dimensionX, y * dimensionY, dimensionX, dimensionY, this);
		}
	}
	
	/**
	 * Paint guard
	 *  */
	private void paintGuard(Graphics g, Guard guard, int x, int y , int dimensionX, int dimensionY){;
		//	Check if guard is sleeping
		if (guard instanceof Drunken && ((Drunken)guard).isSleeping()){
			g.drawImage(ImageService.shared.getGuardSleepingImage(), x * dimensionX, y * dimensionY, dimensionX, dimensionY, this);
		} else {
			//	Guard is not sleeping
			g.drawImage(ImageService.shared.getGuardImage(), x * dimensionX, y * dimensionY, dimensionX, dimensionY, this);
		}
	}
	
	/** 
	 * Paint ogre
	 * */
	private void paintOgre(Graphics g, Ogre ogre, int x, int y , int dimensionX, int dimensionY){
		if (ogre.isStunned()){
			g.drawImage(ImageService.shared.getOgreStunnedImage(), x * dimensionX, y * dimensionY, dimensionX, dimensionY, this);
		}else {
			g.drawImage(ImageService.shared.getOgreImage(), x * dimensionX, y * dimensionY, dimensionX, dimensionY, this);
		}
	}

	/** 
	 * Updates game panel with new game screen
	 * */
	private void updateGamePanel(String str){
		//	Updates game
		this.game.updateGame(str);
		//	Updates map with new game
		this.repaint();

		//	Print map on console
		game.printGame();
	}

	//	MARK: Keyboard delegates
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(game.isGameOver()) return;

		if (!this.isEditing()){
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
	}
	@Override
	public void keyReleased(KeyEvent e) {}

	// MARK: Mouse Delegates

	@Override
	public void mouseClicked(MouseEvent e) {
		if (this.cellDimensionX != 0 && this.cellDimensionY != 0){
			int x = e.getX()/cellDimensionX;
			int y = e.getY()/cellDimensionY;

			checkMouseAction(x,y);

			//	Reset action
			mouseAction = GameWindowEditMouseActions.none;

			//	Updates screen with new game
			this.repaint();

			//	Print map on console
			game.printGame();
		}
	}
	/** 
	 * Removes element at x and y
	 * */
	private void removeElement(int x, int y){
		GameObject removed = this.game.getCurrentMap().removeElementAt(x, y);
		//	Check if removed an ogre or a guard or hero
		if (removed instanceof Ogre){
			this.game.getCurrentMap().removeOgre((Ogre)removed);
		} else if (removed instanceof Guard){
			this.game.getCurrentMap().setGuard(null);
		} else if (removed instanceof Hero){
			this.game.getCurrentMap().setHero(null);
		}
	}
	
	/** 
	 * Checks mouse action
	 * */
	private void checkMouseAction(int x, int y){
		//		Mouse Edit Action
		//	Remove element
		if (mouseAction == GameWindowEditMouseActions.removeElement){
			removeElement(x,y);
		} 
		//	Add wall
		else if (mouseAction == GameWindowEditMouseActions.addWall){
			this.game.getCurrentMap().addElementAt(new Wall(x,y), x, y);
		}
		//	Add door
		else if (mouseAction == GameWindowEditMouseActions.addDoor){
			Door door = new Door(x,y);
			this.game.getCurrentMap().addElementAt(door, x, y);
		}
		//	Add lever
		else if (mouseAction == GameWindowEditMouseActions.addLever){
			Lever lever = new Lever(x,y);
			this.game.getCurrentMap().addElementAt(lever, x, y);
		}
		//	Add guard
		else if (mouseAction == GameWindowEditMouseActions.addGuard){
			Guard guard = new Rookie(x,y);
			//	Add guard
			this.game.getCurrentMap().addGuard(guard);
		}
		//	Add key
		else if (mouseAction == GameWindowEditMouseActions.addKey){
			Key key = new Key(x,y);
			this.game.getCurrentMap().addElementAt(key, x, y);
		}
		//	Add ogre
		else if (mouseAction == GameWindowEditMouseActions.addOgre){
			Ogre ogre = new Ogre(x,y, true);
			this.game.getCurrentMap().addOgre(ogre);
			this.game.getCurrentMap().addElementAt(ogre, x, y);
		}
		//	Add pilar
		else if (mouseAction == GameWindowEditMouseActions.addPilar){
			Pilar pilar = new Pilar(x,y);
			this.game.getCurrentMap().addElementAt(pilar, x, y);
		}
		//	Add hero
		else if (mouseAction == GameWindowEditMouseActions.addHero){
			Hero hero = new Hero(x,y);
			//	Add hero
			this.game.getCurrentMap().addHero(hero);
		}
		else {
			//	No actions
			return;
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
	 * @return the mouseAction
	 */
	public GameWindowEditMouseActions getMouseAction() {
		return mouseAction;
	}

	/**
	 * @param mouseAction the mouseAction to set
	 */
	public void setMouseAction(GameWindowEditMouseActions mouseAction) {
		this.mouseAction = mouseAction;
	}

	/**
	 * @return the isEditing
	 */
	public boolean isEditing() {
		return isEditing;
	}

	/**
	 * @param isEditing the isEditing to set
	 */
	public void setEditing(boolean isEditing) {
		setMouseAction(GameWindowEditMouseActions.none);
		this.isEditing = isEditing;
	}
}
