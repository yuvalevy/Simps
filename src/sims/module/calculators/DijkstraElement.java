package sims.module.calculators;

import sims.module.surface.Cell;

public class DijkstraElement {

	private Cell dijPreviousCell;
	private double dijDistanceFromStart;
	private boolean dijVisited;

	public double getDistanceFromStart() {
		return this.dijDistanceFromStart;
	}

	public Cell getPreviousCell() {
		return this.dijPreviousCell;
	}

	/**
	 * Initialize element's parameters
	 *
	 * Params: dijDistanceFromStart, dijVisited, dijPreviousCell
	 *
	 */
	public void initializeProperties() {

		this.dijDistanceFromStart = Double.MAX_VALUE - 1;
		this.dijVisited = false;
		this.dijPreviousCell = null;

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
