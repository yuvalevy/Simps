/**
 * 
 */
package ui;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;

/**
 * @author Yuval
 *
 */
public class Map {

	private Cell[][] cells;

	// TODO: Create door spaces from configuration
	private Polygon[] doorsPaces = new Polygon[3];

	/**
	 * Creates default map.
	 */
	public Map() {

		createDefalutMap();
	}

	/**
	 * Creates the clear map with door configuration
	 */
	private void createDefalutMap() {

		for (Polygon polygon : doorsPaces) {
			for (int i = 0; i < polygon.npoints; i++) {
				cells[polygon.xpoints[i]][polygon.ypoints[i]] = CellFactory.getCell(true, true);
			}
		}

		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[i].length; j++) {
				if (cells[i][j] != null) {
					cells[i][j] = CellFactory.getCell(true, false);
				}
			}
		}

	}

	public void drawMap(Graphics g, Point paintLocation) {

		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[i].length; j++) {
				this.cells[i][j].draw(g, paintLocation);

			}

		}
	}
}
