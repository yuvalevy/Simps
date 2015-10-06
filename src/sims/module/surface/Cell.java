/**
 *
 */
package sims.module.surface;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

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

	private final ArrayList<CellProperty> properties;

	private final Point coordinate;

	/**
	 * @param coordinate
	 * @param cellType
	 */
	public Cell(Point coordinate, CellProperty... properties) {

		if (CELL_RECT == null) {

			Log.WriteLog("Cell size was not init. Initilizing to (10,10)", LogLevel.Error);

			// CELL_RECT = new Rectangle(10, 10);

		}

		this.properties = new ArrayList<CellProperty>();

		for (CellProperty property : properties) {
			this.properties.add(property);
		}

		this.coordinate = coordinate;

	}

	public boolean containsProperty(CellProperty prop) {

		return this.properties.contains(prop);

	}

	/**
	 * @return the coordinate
	 */
	public Point getCoordinate() {

		return this.coordinate;

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

}