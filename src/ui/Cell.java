/**
 * 
 */
package ui;

import java.awt.Graphics;
import java.awt.Point;
import java.util.HashMap;

/**
 * @author Yuval
 *
 */
public class Cell {

	private CellType cellType;
	// private Point coordinate;

	/**
	 * @param coordinate
	 * @param cellType
	 */
	public Cell(CellType cellType) {
		this.cellType = cellType;
	}

	/**
	 * @return the cellType
	 */
	public CellType getCellType() {
		return cellType;
	}

	/**
	 * @param cellType
	 *            the cellType to set
	 */
	public void setCellType(CellType cellType) {
		this.cellType = cellType;
	}

	public void draw(Graphics g, Point paintLocation) {
		// TODO draw cell by CellType

	}

}

class CellFactory {
	private static final HashMap<CellType, Cell> cellsByCellType = new HashMap<CellType, Cell>();

	public static Cell getCell(boolean isStepable, boolean isDoor) {
		CellType type = new CellType(isStepable, isDoor);

		Cell cell = (Cell) cellsByCellType.get(type);

		if (cell == null) {
			cell = new Cell(type);
			cellsByCellType.put(type, cell);
		}

		return cell;
	}

}