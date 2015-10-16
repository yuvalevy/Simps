/**
 *
 */
package sims.module.surface;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import sims.basics.Log;
import sims.basics.LogLevel;
import sims.module.calculators.DijkstraElement;

/**
 * @author Yuval
 *
 */
public class Cell extends DijkstraElement {

	static Rectangle CELL_RECT;

	private final ArrayList<CellProperty> properties;

	private final ArrayList<NeighborRelationship> neighbors;

	private final GameLocation location;

	/**
	 * @param coordinate
	 * @param cellType
	 */
	public Cell(GameLocation location, CellProperty... properties) {

		if (CELL_RECT == null) {

			Log.WriteLineLog("Cell size was not initialized. ", LogLevel.ERROR);
		}

		this.properties = new ArrayList<CellProperty>(5);
		this.neighbors = new ArrayList<>(8);

		for (CellProperty property : properties) {
			this.properties.add(property);
		}

		this.location = location;

	}

	public static Rectangle getCellSize() {

		return Cell.CELL_RECT;
	}

	public static void setCellSize(Rectangle cellsize) {

		Cell.CELL_RECT = cellsize;
	}

	public void addTupleNeighbors(Cell neighborCell, double distance) {

		addNeighbor(new NeighborRelationship(neighborCell, distance));
		neighborCell.addNeighbor(new NeighborRelationship(this, distance));

	}

	public boolean containsProperty(CellProperty prop) {

		return this.properties.contains(prop);

	}

	/**
	 * Gets the closest between c and this (starting from this) that contains
	 * the CellProperty prop
	 *
	 * @param c
	 * @param prop
	 * @return
	 */
	public Cell getClosesetCell(Cell c, CellProperty prop) {

		double minimum = Double.MAX_VALUE;
		Cell closest = null;

		for (NeighborRelationship neigh : c.getNeighbors(prop)) {

			Cell neighborCell = neigh.getNighborCell();

			double distance = getDistance(neighborCell);
			if (distance < minimum) {

				minimum = distance;
				closest = neighborCell;
			}
		}

		if (closest == null) {

			closest = getNeighborsClosestCell(prop, c.getNeighbors());

		}

		return closest;
	}

	/**
	 * @return the gameLocation
	 */
	public GameLocation getCoordinate() {

		return this.location;

	}

	public ArrayList<NeighborRelationship> getNeighbors() {
		return this.neighbors;
	}

	public ArrayList<NeighborRelationship> getNeighbors(CellProperty prop) {
		ArrayList<NeighborRelationship> $ = new ArrayList<>();

		for (NeighborRelationship neighborRelationship : getNeighbors()) {
			if (neighborRelationship.getNighborCell().containsProperty(prop)) {
				$.add(neighborRelationship);
			}
		}

		return $;
	}

	/**
	 * @param p
	 *            Point to be checked
	 *
	 * @return true if cell contains point. false otherwise
	 */
	public boolean isOnCell(Point p) {

		Point loc = this.location.getLocation();
		Rectangle currentSpace = new Rectangle(loc, Cell.CELL_RECT.getSize());

		return currentSpace.contains(p);
	}

	private void addNeighbor(NeighborRelationship neighbor) {

		if (!containsNieghbors(neighbor.getNighborCell())) {

			this.neighbors.add(neighbor);

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

	private double getDistance(Cell secondCell) {

		Point secondCellLocation = secondCell.getCoordinate().getLocation();

		return secondCellLocation.distance(this.location.getLocation());
	}

	private Cell getNeighborsClosestCell(CellProperty prop, ArrayList<NeighborRelationship> neighbors) {

		Cell closest = null;
		double minimum = Double.MAX_VALUE;

		for (NeighborRelationship relation : neighbors) {

			Cell cell = relation.getNighborCell();

			for (NeighborRelationship neigh : cell.getNeighbors(prop)) {

				Cell neighborCell = neigh.getNighborCell();
				double distance = getDistance(neighborCell);

				if (distance < minimum) {

					minimum = distance;
					closest = neighborCell;
				}

			}
		}

		if (closest == null) {

			ArrayList<NeighborRelationship> arrayList = getNeighborsNeighbors(neighbors);

			closest = getNeighborsClosestCell(prop, arrayList);
		}

		return closest;
	}

	private ArrayList<NeighborRelationship> getNeighborsNeighbors(ArrayList<NeighborRelationship> neighbors) {

		ArrayList<NeighborRelationship> arrayList = new ArrayList<>();

		for (NeighborRelationship neighborRelationship : neighbors) {

			Cell cell = neighborRelationship.getNighborCell();

			arrayList.addAll(cell.getNeighbors());
		}

		return arrayList;
	}

}