package game.services;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

/** 
 * This class loads and holds all images, keeping a shared instance.
 * */
public class ImageService {
	//	Shared instance
	static public ImageService shared = new ImageService();
	
	//	Images
	private BufferedImage wallImage;
	private BufferedImage heroImage;
	private BufferedImage closedDoorImage;
	private BufferedImage openDoorImage;
	private BufferedImage guardImage;
	private BufferedImage keyImage;
	private BufferedImage leverImage;
	private BufferedImage floorImage;
	private BufferedImage guardSleepingImage;
	private BufferedImage ogreImage;
	private BufferedImage clubImage;
	private BufferedImage heroClubImage;
	private BufferedImage heroKeyImage;
	private BufferedImage pilarImage;
	
	/** 
	 * Constructor
	 * */
	ImageService(){
		//	Load images
		this.floorImage = loadBufferedImage("/floor.png");
		this.setWallImage(loadBufferedImage("/wall.png"));
		this.heroKeyImage = loadBufferedImage("/player_key.png");
		this.heroClubImage = loadBufferedImage("/player_club.png");
		this.clubImage = loadBufferedImage("/club.png");
		this.ogreImage = loadBufferedImage("/ogre.png");
		this.guardSleepingImage = loadBufferedImage("/guard_sleeping.png");
		this.keyImage = loadBufferedImage("/key.png");
		this.guardImage = loadBufferedImage("/guard.png");
		this.openDoorImage = loadBufferedImage("/door_open.png");
		this.closedDoorImage = loadBufferedImage("/door_closed.png");
		this.setHeroImage(loadBufferedImage("/player.png"));
		this.leverImage = loadBufferedImage("/lever.png");
		this.setPilarImage(loadBufferedImage("/pilar.png"));
	}
	
	/** 
	 * @brief Loads a image
	 * @return BufferedImage Image loaded
	 * */
	public BufferedImage loadBufferedImage(String string){
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
	 * @return the guardSleepingImage
	 */
	public BufferedImage getGuardSleepingImage() {
		return guardSleepingImage;
	}



	/**
	 * @param guardSleepingImage the guardSleepingImage to set
	 */
	public void setGuardSleepingImage(BufferedImage guardSleepingImage) {
		this.guardSleepingImage = guardSleepingImage;
	}



	/**
	 * @return the ogreImage
	 */
	public BufferedImage getOgreImage() {
		return ogreImage;
	}



	/**
	 * @param ogreImage the ogreImage to set
	 */
	public void setOgreImage(BufferedImage ogreImage) {
		this.ogreImage = ogreImage;
	}



	/**
	 * @return the clubImage
	 */
	public BufferedImage getClubImage() {
		return clubImage;
	}



	/**
	 * @param clubImage the clubImage to set
	 */
	public void setClubImage(BufferedImage clubImage) {
		this.clubImage = clubImage;
	}



	/**
	 * @return the heroClubImage
	 */
	public BufferedImage getHeroClubImage() {
		return heroClubImage;
	}



	/**
	 * @param heroClubImage the heroClubImage to set
	 */
	public void setHeroClubImage(BufferedImage heroClubImage) {
		this.heroClubImage = heroClubImage;
	}



	/**
	 * @return the heroKeyImage
	 */
	public BufferedImage getHeroKeyImage() {
		return heroKeyImage;
	}



	/**
	 * @param heroKeyImage the heroKeyImage to set
	 */
	public void setHeroKeyImage(BufferedImage heroKeyImage) {
		this.heroKeyImage = heroKeyImage;
	}

	/**
	 * @return the pilarImage
	 */
	public BufferedImage getPilarImage() {
		return pilarImage;
	}

	/**
	 * @param pilarImage the pilarImage to set
	 */
	public void setPilarImage(BufferedImage pilarImage) {
		this.pilarImage = pilarImage;
	}

}
