package sims.module.surface;

public class NeighborRelationship {

	private final Cell neighbor;
	private final double cellsDistance;

	public NeighborRelationship(Cell neighborPoiner, double cellsDistance) {
		this.neighbor = neighborPoiner;
		this.cellsDistance = cellsDistance;
	}

	public double getDistance() {
		return this.cellsDistance;
	}

	public Cell getNighborCell() {
		return this.neighbor;
	}

}
