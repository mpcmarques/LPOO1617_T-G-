package game.logic;

/** 
 * Represent a cell in the gameMap
 * */
public class Cell extends Object {
	private GameObject element;
	
	/** 
	 * Constructor
	 * */
	Cell(){
		this.setElement(null);
	}
	
	/** 
	 * Constructor with parameters
	 * */
	Cell(GameObject element){
		this.setElement(element);
	}

	/**
	 * @return the element
	 */
	public GameObject getElement() {
		return element;
	}

	/**
	 * @param element the element to set
	 */
	public void setElement(GameObject element) {
		this.element = element;
	}
}