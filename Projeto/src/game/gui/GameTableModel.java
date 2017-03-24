package game.gui;

import javax.swing.table.AbstractTableModel;

import game.logic.*;

public class GameTableModel extends AbstractTableModel {
	//	Properties
	private Map map;

	/** 
	 * Constructor
	 * */
	public GameTableModel(Map map){
		setMap(map);
	}

	@Override
	public int getRowCount() {
		return map.getNumberLines();
	}

	@Override
	public int getColumnCount() {
		return map.getNumberCellsForLine();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return null;
	}
	
	public Cell getCellAt(int x, int y) {
		return map.getCells()[y][x];
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
