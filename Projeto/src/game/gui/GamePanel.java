package game.gui;

import java.awt.Color;
import java.awt.Graphics;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import game.logic.*;


public class GamePanel extends JPanel {

	//	Proprieties
	private BufferedImage wallImage;
	private BufferedImage heroImage;
	private BufferedImage closedDoorImage;
	private BufferedImage openDoorImage;
	private BufferedImage guardImage;
	private BufferedImage keyImage;
	private BufferedImage leverImage;
	private BufferedImage floorImage;
	private BufferedImage guardSleepingImage;
	private Map map;


	/**
	 * Create the panel.
	 */
	public GamePanel(Map map) {
		this.map = map;
		//	Load images
		wallImage = loadBufferedImage("/wall.jpg");
		setKeyImage(loadBufferedImage("/key.png"));
		setLeverImage(loadBufferedImage("/lever.png"));
		setClosedDoorImage(loadBufferedImage("/door_closed.png"));
		setOpenDoorImage(loadBufferedImage("/door_open.png"));
		setHeroImage(loadBufferedImage("/hero.png"));
		setGuardImage(loadBufferedImage("/guard.png"));
		setFloorImage(loadBufferedImage("/floor.png"));
		this.guardSleepingImage = loadBufferedImage("/guard_sleeping.png");
	}


	/** 
	 * 
	 * */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);  

		//	Paint panel
		this.paintPanel(g);
	}
	
	/** 
	 * Paints all game panel
	 * */
	public void paintPanel(Graphics g){
		//	Calculate cell dimensions
		int dimensionX = this.getWidth()/map.getNumberCellsForLine();
		int dimensionY = this.getHeight()/map.getNumberLines();

		//	Paint floor
		this.paintFloor(g, dimensionX, dimensionY);

		//	Paint objects
		this.paintObjects(g, dimensionX, dimensionY);
	}

	/** 
	 * Paints map with floor texture
	 * */
	private void paintFloor(Graphics g, int dimensionX, int dimensionY){
		int x,y;
		int finalX = dimensionX / 3;
		int finalY = dimensionY / 3;

		//	Loop throught map cells
		for(x = 0; x < map.getNumberCellsForLine() * 3 ; x++){
			for(y = 0; y < map.getNumberLines()  * 3 ; y++){
				//	paints floor
				g.drawImage(floorImage, x * finalX, y * finalY, finalX, finalY, this);
			}
		}
	}

	/** 
	 * Paints second level objects
	 * */
	private void paintObjects(Graphics g, int dimensionX, int dimensionY){
		int x,y;
		//		Loop throught data
		for(x = 0; x < map.getNumberCellsForLine(); x++){
			for(y = 0; y < map.getNumberLines(); y++){
				//	If it is wall
				if (map.getCells()[y][x] instanceof Wall){
					g.drawImage(wallImage, x * dimensionX, y * dimensionY, dimensionX, dimensionY, this);
				}
				//	If it is a hero
				else if (map.getCells()[y][x] instanceof Hero){
					g.drawImage(heroImage, x * dimensionX, y * dimensionY, dimensionX, dimensionY, this);
				}
				//	If it is a door
				else if (map.getCells()[y][x] instanceof Door){
					Door door = (Door)map.getCells()[y][x];
					//	Check door sprite
					if (door.isOpen()){
						g.drawImage(openDoorImage, x * dimensionX, y * dimensionY, dimensionX, dimensionY, this);
					} else {
						g.drawImage(closedDoorImage, x * dimensionX, y * dimensionY, dimensionX, dimensionY, this);
					}
				}
				//	If it is guard
				else if (map.getCells()[y][x] instanceof Guard){
					Guard guard = (Guard)map.getCells()[y][x];
					//	Check if guard is sleeping
					if (guard instanceof Drunken && ((Drunken)guard).isSleeping()){
						g.drawImage(guardSleepingImage, x * dimensionX, y * dimensionY, dimensionX, dimensionY, this);
					} else {
						//	Guard is not sleeping
						g.drawImage(guardImage, x * dimensionX, y * dimensionY, dimensionX, dimensionY, this);
					}
				}
				//	If it is lever
				else if (map.getCells()[y][x] instanceof Lever){
					g.drawImage(leverImage, x * dimensionX, y * dimensionY, dimensionX, dimensionY, this);
				}
				// If it is key
				else if (map.getCells()[y][x] instanceof Key){
					g.drawImage(keyImage, x * dimensionX, y * dimensionY, dimensionX, dimensionY, this);
				}
			}
		}
	}

	private BufferedImage loadBufferedImage(String string){
		try
		{
			BufferedImage bi = ImageIO.read(this.getClass().getResource(string));
			return bi;
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * @return the heroImage
	 */
	public BufferedImage getHeroImage() {
		return heroImage;
	}


	/**
	 * @param heroImage the heroImage to set
	 */
	public void setHeroImage(BufferedImage heroImage) {
		this.heroImage = heroImage;
	}


	/**
	 * @return the closedDoorImage
	 */
	public BufferedImage getClosedDoorImage() {
		return closedDoorImage;
	}


	/**
	 * @param closedDoorImage the closedDoorImage to set
	 */
	public void setClosedDoorImage(BufferedImage closedDoorImage) {
		this.closedDoorImage = closedDoorImage;
	}


	/**
	 * @return the openDoorImage
	 */
	public BufferedImage getOpenDoorImage() {
		return openDoorImage;
	}


	/**
	 * @param openDoorImage the openDoorImage to set
	 */
	public void setOpenDoorImage(BufferedImage openDoorImage) {
		this.openDoorImage = openDoorImage;
	}


	/**
	 * @return the guardImage
	 */
	public BufferedImage getGuardImage() {
		return guardImage;
	}


	/**
	 * @param guardImage the guardImage to set
	 */
	public void setGuardImage(BufferedImage guardImage) {
		this.guardImage = guardImage;
	}


	/**
	 * @return the keyImage
	 */
	public BufferedImage getKeyImage() {
		return keyImage;
	}


	/**
	 * @param keyImage the keyImage to set
	 */
	public void setKeyImage(BufferedImage keyImage) {
		this.keyImage = keyImage;
	}


	/**
	 * @return the leverImage
	 */
	public BufferedImage getLeverImage() {
		return leverImage;
	}


	/**
	 * @param leverImage the leverImage to set
	 */
	public void setLeverImage(BufferedImage leverImage) {
		this.leverImage = leverImage;
	}


	/**
	 * @return the floorImage
	 */
	public BufferedImage getFloorImage() {
		return floorImage;
	}


	/**
	 * @param floorImage the floorImage to set
	 */
	public void setFloorImage(BufferedImage floorImage) {
		this.floorImage = floorImage;
	}
	/**
	 * @return the wallImage
	 */
	public BufferedImage getWallImage() {
		return wallImage;
	}


	/**
	 * @param wallImage the wallImage to set
	 */
	public void setWallImage(BufferedImage wallImage) {
		this.wallImage = wallImage;
	}


	/**
	 * @return the map
	 */
	public Map getMap() {
		return map;
	}


	/**
	 * @param map the map to set
	 */
	public void setMap(Map map) {
		this.map = map;
	}
}
