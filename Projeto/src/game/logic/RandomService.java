package game.logic;
import java.util.Random;

/** 
 * This class represents a service that can return a random number
 * */
public class RandomService {
	private static Random random = new Random();
	
	
	
	/** 
	 * Get a random number between min and max
	 * @return int Random value
	 * @param min Minimum number
	 * @param max Maximum number
	 * */
	public static int getRandomInt(int min, int max)
	{
	  return random.nextInt(max - min + 1) + min;
	}
}
