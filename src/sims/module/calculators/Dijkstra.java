package sims.module.calculators;

import java.awt.Point;

import sims.basics.Log;
import sims.module.objects.Player;
import sims.module.objects.Room;
import sims.module.surface.Cell;
import sims.module.surface.GameLocation;
import sims.module.surface.Map;
import sims.module.surface.NeighborRelationship;

public class Dijkstra implements Calculator {

	private final Map worldMap;
	private boolean isActive;
	private Cell startCell;
	private Cell endCell;

	public Dijkstra(Map worldMap) {

		this.worldMap = worldMap;
		this.isActive = false;

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see sims.module.main.calculators.Calculator#isMulti()
	 */
	@Override
	public boolean canMulti() {
		return false;
	}

	private void covert(GameLocation start, GameLocation end) {

		this.startCell = this.worldMap.getCell(start);
		this.endCell = this.worldMap.getCell(end);

	}

	/**
	 * Excutes if not active
	 */
	@Override
	public void excute(GameLocation start, GameLocation end) {

		if (this.isActive) {
			return;
		}

		this.isActive = true;

		/**
		 * this "mistake" is on propose! player holds FIFO set so steps should
		 * be inserted in the same way.
		 */
		// covert(start, end);
		covert(end, start);

		innerExcute();

	}

	/**
	 *
	 * Returns the closest Cell to initialized startCell that was not yet
	 * visited.
	 *
	 * @param spaceCells
	 *            : Array of cells
	 * @return the cell whose 'dijDistanceFromStart' parameter is the smallest
	 */
	private Cell getClosestCell(Cell[][] spaceCells) {

		double min = Double.MAX_VALUE;

		Cell cellPointer = null;

		for (Cell[] line : spaceCells) {
			for (Cell currentCell : line) {

				if (!currentCell.isVisited() && (currentCell.getDistanceFromStart() < min)) {
					cellPointer = currentCell;
					min = currentCell.getDistanceFromStart();
				}

			}
		}

		// System.out.println("min = x:"+ c.x+" y:"+c.y+ " num="+c.num);
		return cellPointer;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see sims.module.main.calculators.Calculator#implementSteps(sims.module.
	 * objects.Player, sims.module.surface.Cell, int)
	 */
	@Override
	public void implementSteps(Player p, GameLocation start, GameLocation end) {

		covert(start, end);

		Cell nextCell = this.startCell;
		Point nextLocation = this.startCell.getCoordinate();

		while (nextCell.getPreviousCell() != nextCell) {

			// Log.WriteLog("Step++ " + nextLocation);

			nextCell = nextCell.getPreviousCell();
			nextLocation = nextCell.getCoordinate();
			p.addStep(new GameLocation(nextLocation, start.getRoomId()));

		}

		this.isActive = false;

	}

	/**
	 * Initialize the cells in all map's rooms.
	 */
	private void initializeCells() {

		for (Room room : this.worldMap.getRooms()) {

			for (Cell[] lines : room.getCells()) {
				for (Cell item : lines) {

					item.initializeForDijkstra();
				}
			}
		}

		Log.WriteLog("Finished init");

	}

	/**
	 * Search algorithm for the shortest way from cell start to cell end
	 */
	private void innerExcute() {

		if (this.startCell == this.endCell) {
			Log.WriteLog("Start == End");
			return;
		}

		Log.WriteLog("Starting dijkstra");

		initializeCells();

		Cell[][] spaceCells = this.worldMap.getFocusedRoom().getCells();

		Cell helpPointer = this.startCell;
		helpPointer.setPreviousCell(this.startCell);
		helpPointer.setDistanceFromStart(0);

		while (helpPointer != this.endCell) {

			// Log.WriteLog("helpPointer = " +
			// helpPointer.getDistanceFromStart() + " " +
			// helpPointer.getCoordinate() + " --Neighbors " +
			// helpPointer.getNeighbors().size());

			helpPointer.setVisited(true);
			for (NeighborRelationship relationship : helpPointer.getNeighbors()) {

				Cell relationCell = relationship.getNighborCell();

				if (!relationCell.isVisited() && (relationCell
						.getDistanceFromStart() > (helpPointer.getDistanceFromStart() + relationship.getDistance()))) {

					relationCell.setDistanceFromStart(helpPointer.getDistanceFromStart() + relationship.getDistance());

					relationCell.setPreviousCell(helpPointer);
				}
			}

			// Log.WriteLog("Trying to find the closest");
			helpPointer = getClosestCell(spaceCells);

		}

		Log.WriteLog("END DIJK");

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see sims.module.main.calculators.Calculator#isActive()
	 */
	@Override
	public boolean isActive() {
		return this.isActive;
	}

}
