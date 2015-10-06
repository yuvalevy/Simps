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

	private final ArrayList<NeighborRelationship> neighbors;

	private final Point coordinate;

	private Cell dijPreviousCell;
	private double dijDistanceFromStart;
	private boolean dijVisited;

	/**
	 * @param coordinate
	 * @param cellType
	 */
	public Cell(Point coordinate, CellProperty... properties) {

		if (CELL_RECT == null) {

			Log.WriteLog("Cell size was not init. Initilizing to (10,10)", LogLevel.Error);

			// CELL_RECT = new Rectangle(10, 10);

		}

		this.properties = new ArrayList<CellProperty>(5);
		this.neighbors = new ArrayList<>(8);

		for (CellProperty property : properties) {
			this.properties.add(property);
		}

		this.coordinate = coordinate;

	}

	private void addNeighbor(NeighborRelationship neighbor) {

		if (!containsNieghbors(neighbor.getNighborCell())) {

			this.neighbors.add(neighbor);

		}

	}

	public void addTupleNeighbors(Cell neighborCell, double distance) {

		if (neighborCell.containsProperty(CellProperty.Stepable) && containsProperty(CellProperty.Stepable)) {

			addNeighbor(new NeighborRelationship(neighborCell, distance));
			neighborCell.addNeighbor(new NeighborRelationship(this, distance));
		}
	}

	private boolean containsNieghbors(Cell c) {

		for (NeighborRelationship neighborRelationship : this.neighbors) {

			if (neighborRelationship.getNighborCell() == c) {
				return true;
			}
		}

		return false;
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

	public double getDistanceFromStart() {
		return this.dijDistanceFromStart;
	}

	public ArrayList<NeighborRelationship> getNeighbors() {
		return this.neighbors;
	}

	public Cell getPreviousCell() {
		return this.dijPreviousCell;
	}

	/**
	 * Initialize cell's Dijkstra parameters
	 *
	 * Params: dijDistanceFromStart, dijVisited, dijPreviousCell
	 *
	 */
	public void initializeForDijkstra() {

		this.dijDistanceFromStart = Double.MAX_VALUE - 1;
		this.dijVisited = false;
		this.dijPreviousCell = null;

	}

	// /**
	// * Adds all neighbors to current cell
	// *
	// * @param nei
	// */
	// public void addNeighbors(Cell... nei) {
	//
	// for (Cell cell : nei) {
	//
	// NeighborRelationship temp = new NeighborRelationship(cell, cell.id);
	// addNeighbor(temp);
	//
	// temp = new NeighborRelationship(this, cell.id);
	// cell.addNeighbor(temp);
	// }
	//
	// }

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

	public boolean isVisited() {
		return this.dijVisited;
	}

	public void setDistanceFromStart(double dijDistanceFromStart) {
		this.dijDistanceFromStart = dijDistanceFromStart;
	}

	public void setPreviousCell(Cell dijPreviousCell) {
		this.dijPreviousCell = dijPreviousCell;
	}

	public void setVisited(boolean isVisited) {
		this.dijVisited = isVisited;
	}

}