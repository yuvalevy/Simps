/**
 *
 */
package sims.module.surface;

import java.awt.Point;
import java.awt.Rectangle;

import sims.basics.Log;
import sims.basics.LogLevel;

/**
 * @author Yuval
 *
 */
public class Cell {

	static Rectangle CELL_RECT;

	public static Rectangle getCellSize() {

		return Cell.CELL_RECT;
	}

	public static void setCellSize(Rectangle cellsize) {

		Cell.CELL_RECT = cellsize;
	}

	private final CellType cellType;

	private final Point coordinate;

	/**
	 * @param coordinate
	 * @param cellType
	 */
	public Cell(CellType cellType, Point coordinate) {

		if (CELL_RECT == null) {

			Log.WriteLog("Cell size was not init. Initilizing to (10,10)", LogLevel.Error);

			// CELL_RECT = new Rectangle(10, 10);

		}

		this.cellType = cellType;
		this.coordinate = coordinate;

	}

	/**
	 * @return the cellType
	 */
	public CellType getCellType() {

		return this.cellType;

	}

	/**
	 * @return the coordinate
	 */
	public Point getCoordinate() {

		return this.coordinate;

	}

	/**
	 * @return the isDoor
	 */
	public boolean isDoor() {

		return this.cellType.isDoor();

	}

	/**
	 * @param p
	 *            Point to be checked
	 *
	 * @return true if cell contains point. false otherwise
	 */
	public boolean isOnCell(Point p) {

		Rectangle currentSpace = new Rectangle(this.coordinate, Cell.CELL_RECT.getSize());

		return currentSpace.contains(p);
	}

	/**
	 * @return the isStepable
	 */
	public boolean isStepable() {

		return this.cellType.isStepable();

	}

}