package sims;

import java.util.ArrayList;

class Cell {

	public Cell prev;
	public int distanceFromStart;
	public int id;
	public boolean visit;

	public ArrayList<NeighborRelationship> neighbors;

	public Cell(int id) {

		this.id = id;

		this.neighbors = new ArrayList<NeighborRelationship>();
	}

	public void addNeighbor(NeighborRelationship neighbor) {

		if (!containsNieghbors(neighbor.getNighborCell())) {

			this.neighbors.add(neighbor);

		}

	}

	/**
	 * Adds all neighbors to current cell
	 *
	 * @param nei
	 */
	public void addNeighbors(Cell... nei) {

		for (Cell cell : nei) {

			NeighborRelationship temp = new NeighborRelationship(cell, cell.id);
			addNeighbor(temp);

			temp = new NeighborRelationship(this, cell.id);
			cell.addNeighbor(temp);
		}

	}

	/**
	 * Adds all neighbors to current cell
	 *
	 * @param nei
	 */
	public void addNeighbors(NeighborRelationship... nei) {

		for (NeighborRelationship cell : nei) {
			addNeighbor(cell);
			cell.getNighborCell().addNeighbor(new NeighborRelationship(this, cell.getDistance()));
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

}

class CellHolder {

	public ArrayList<Cell> allCells;

	public CellHolder(Cell startCell, Cell endCell) {

		this.allCells = new ArrayList<Cell>();

		Cell cell1 = new Cell(4);
		Cell cell3 = new Cell(2);
		Cell cell2 = new Cell(5);
		startCell.addNeighbors(cell1, cell2, cell3);

		cell2.addNeighbors(endCell);

		Cell cell4 = new Cell(1);
		cell3.addNeighbors(cell4);

		cell4.addNeighbors(endCell);

		addCells(startCell, endCell, cell1, cell2, cell3, cell4);

	}

	public void addCells(Cell... cells) {
		for (Cell cell : cells) {
			this.allCells.add(cell);
		}
	}

	public ArrayList<Cell> getAllCells() {
		return this.allCells;
	}
}

public class DijkstraDebuger {

	/**
	 * Search algorithm for the shortest way from cell start to cell end
	 *
	 * @param list
	 *            : Array list of cells
	 * @param start
	 *            : The start Cell
	 * @param end
	 *            : The end Cell
	 */
	public static void dijkstra(ArrayList<Cell> list, Cell start, Cell end) {

		init(list);

		Cell helpPointer = start;
		helpPointer.prev = start;
		helpPointer.distanceFromStart = 0;

		while (helpPointer != end) {

			helpPointer.visit = true;
			for (NeighborRelationship relationship : helpPointer.neighbors) {

				Cell relationCell = relationship.getNighborCell();

				if (!relationCell.visit && (relationCell.distanceFromStart > (helpPointer.distanceFromStart
						+ relationship.getDistance()))) {

					relationCell.distanceFromStart = helpPointer.distanceFromStart + relationship.getDistance();

					relationCell.prev = helpPointer;
				}
			}

			helpPointer = smallestCell(list);

		}

	}

	/**
	 * Resets the cells in the array list to: num = 1000 visit = false
	 *
	 * @param list
	 */
	public static void init(ArrayList<Cell> list) {
		for (Cell item : list) {
			item.distanceFromStart = 1000;
			item.visit = false;
			item.prev = null;
		}
	}

	public static void main(String[] args) {

		Cell startCell = new Cell(30);
		Cell endCell = new Cell(3);
		CellHolder holder = new CellHolder(startCell, endCell);

		dijkstra(holder.getAllCells(), startCell, endCell);

	}

	/**
	 * @param n
	 *            : Array of cells
	 * @return the cell whose 'num' is smallest
	 */
	public static Cell smallestCell(ArrayList<Cell> n) {
		int min = 2000;
		Cell c = null;
		for (Cell item : n) {
			if (!item.visit && (item.distanceFromStart < min)) {
				c = item;
				min = item.distanceFromStart;
			}
		}

		// System.out.println("min = x:"+ c.x+" y:"+c.y+ " num="+c.num);
		return c;
	}

}

class NeighborRelationship {

	private final Cell neighbor;
	private final int cellsDistance;

	public NeighborRelationship(Cell neighborPoiner, int cellsDistance) {
		this.neighbor = neighborPoiner;
		this.cellsDistance = cellsDistance;
	}

	public int getDistance() {
		return this.cellsDistance;
	}

	public Cell getNighborCell() {
		return this.neighbor;
	}

}
