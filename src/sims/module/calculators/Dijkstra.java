package sims.module.calculators;

import java.util.ArrayList;

import sims.basics.Log;
import sims.module.objects.Door;
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

	private void convert(GameLocation start, GameLocation end) {

		if (start.getRoomId() != end.getRoomId()) {
			Log.WriteLineLog("Dijkstra.convert - not same room");
			// return;
		}

		/**
		 * this "mistake" is on propose! player holds FIFO set so steps should
		 * be inserted in the same way.
		 */

		this.startCell = this.worldMap.getCell(start);
		this.endCell = this.worldMap.getCell(end);

	}

	/**
	 * Excutes if not active
	 */
	@Override
	public void excute(GameLocation start, GameLocation end) {

		if (!this.isActive) {
			this.isActive = true;
		} else {
			return;
		}

		if (!this.worldMap.isMapInit()) {
			return;
		}

		initClass();

		innerExcute(start, end);

	}

	private void executeSingleRoom(Room currentRoom) {

		if (this.startCell == this.endCell) {
			Log.WriteLineLog("executeSingleRoom: Start == End");
			return;
		}

		Log.WriteLog("-Start single room dikjstra-");

		Cell[][] spaceCells = currentRoom.getCells();

		Cell helpPointer = this.startCell;
		helpPointer.setPreviousCell(this.startCell);
		helpPointer.setDistanceFromStart(0);

		while (helpPointer != this.endCell) {

			helpPointer.setVisited(true);
			for (NeighborRelationship relationship : helpPointer.getNeighbors()) {

				Cell relationCell = relationship.getNighborCell();

				if (!relationCell.isVisited() && (relationCell
						.getDistanceFromStart() > (helpPointer.getDistanceFromStart() + relationship.getDistance()))) {

					relationCell.setDistanceFromStart(helpPointer.getDistanceFromStart() + relationship.getDistance());

					relationCell.setPreviousCell(helpPointer);
				}
			}

			helpPointer = getClosestCell(spaceCells);

		}
		Log.WriteLog("-End single room dikjstra   ");

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

		Log.WriteLog("-Implementing");

		convert(start, end);

		Cell nextCell = this.startCell;
		GameLocation nextLocation = this.startCell.getCoordinate();

		do {

			// Log.WriteLog("Step++ " + nextLocation);

			p.addStep(nextLocation);

			nextCell = nextCell.getPreviousCell();
			nextLocation = nextCell.getCoordinate();

		} while (nextCell.getPreviousCell() != nextCell);

		// Log.WriteLog("Step++ " + nextLocation);

		Door nextDoor = this.worldMap.getDoor(nextLocation);
		if (nextDoor != null) {

			nextLocation = nextDoor.getNextRoomStartingLocation();
		}

		p.addStep(nextLocation);

		initClass();
		Log.WriteLineLog("-Finish implementing");
		this.isActive = false;

	}

	/**
	 *
	 */
	private void initClass() {

		this.startCell = null;
		this.endCell = null;

		initializeCells();
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

		Log.WriteLineLog("Finished init");

	}

	/**
	 * Search algorithm for the shortest way from cell start to cell end
	 */
	private void innerExcute(GameLocation start, GameLocation end) {

		Log.WriteLog("START all dijkstra");

		ArrayList<GameLocation> doors = this.worldMap.getRoomsRoad(start.getRoomId(), end.getRoomId());

		doors.add(0, start);
		doors.add(end);

		Cell temp = this.endCell;

		// Log.WriteLog("---------");
		// for (int i = 0; i < doors.size(); i++) {
		//
		// Log.WriteLog("STEP " + i + " " + doors.get(i));
		// }
		// Log.WriteLog("---------");

		// for (int i = doors.size() - 1; i > 0; i--) {
		//
		// convert(doors.get(i - 1), doors.get(i));
		//
		// if (i == 1) {
		// this.startCell.setPreviousCell(this.startCell);
		// }
		//
		// this.endCell.setPreviousCell(this.startCell);

		for (int i = 0; i < (doors.size() - 1); i += 2) {

			convert(doors.get(i + 1), doors.get(i));

			int roomIndex = doors.get(i).getRoomId();

			executeSingleRoom(this.worldMap.getRoom(roomIndex));

			if (temp != null) {

				temp.setPreviousCell(this.endCell);

			}

			temp = this.startCell;
		}

		temp.setPreviousCell(temp);

		Log.WriteLineLog("END all dijkstra");

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
