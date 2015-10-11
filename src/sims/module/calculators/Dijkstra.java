package sims.module.calculators;

import java.util.ArrayList;

import sims.basics.Log;
import sims.module.objects.Door;
import sims.module.objects.Player;
import sims.module.surface.Cell;
import sims.module.surface.CellProperty;
import sims.module.surface.GameLocation;
import sims.module.surface.Map;
import sims.module.surface.NeighborRelationship;
import sims.module.surface.Room;

public class Dijkstra implements Calculator {

	private final Map worldMap;
	private boolean isActive;

	private Cell startCell;
	private Cell endCell;

	public Dijkstra(Map worldMap) {

		this.worldMap = worldMap;
		this.isActive = false;

	}

	@Override
	public boolean canMulti() {
		return false;
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

	@Override
	public void implementSteps(Player p, GameLocation start, GameLocation end) {

		convert(start, end);

		Cell nextCell = this.startCell;
		GameLocation nextLocation = nextCell.getCoordinate();

		if (nextCell.getPreviousCell() != null) {

			do {

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
		}
		initClass();
		// Log.WriteLineLog("-Finish implementing");
		this.isActive = false;

	}

	@Override
	public boolean isActive() {
		return this.isActive;
	}

	private void convert(GameLocation start, GameLocation end) {

		this.startCell = this.worldMap.getCell(start);
		this.endCell = this.worldMap.getCell(end);

	}

	private void executeSingleRoom(Room currentRoom) {

		if (this.startCell == this.endCell) {
			Log.WriteLineLog("executeSingleRoom: Start == End");
			return;
		}

		Cell[][] spaceCells = currentRoom.getCells();

		if (!isCellStepable(this.startCell)) {

			this.startCell = this.endCell.getClosesetCell(this.startCell, CellProperty.Stepable);
		}

		Cell helpPointer = this.startCell;
		helpPointer.setPreviousCell(this.startCell);
		helpPointer.setDistanceFromStart(0);

		while (helpPointer != this.endCell) {

			helpPointer.setVisited(true);
			for (NeighborRelationship relationship : helpPointer.getNeighbors(CellProperty.Stepable)) {

				Cell relationCell = relationship.getNighborCell();

				if (!relationCell.isVisited() && (relationCell
						.getDistanceFromStart() > (helpPointer.getDistanceFromStart() + relationship.getDistance()))) {

					relationCell.setDistanceFromStart(helpPointer.getDistanceFromStart() + relationship.getDistance());

					relationCell.setPreviousCell(helpPointer);
				}
			}

			helpPointer = getClosestCellToStart(spaceCells);

			if (helpPointer == null) {

				helpPointer = this.endCell;
				Log.WriteLineLog(getClass() + "Dijkstra.executeSingleRoom Problem");
				Log.WriteLineLog(getClass() + "Dijkstra.executeSingleRoom " + this.endCell);

			}
		}
		// Log.WriteLog("-End single room dikjstra ");

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
	private Cell getClosestCellToStart(Cell[][] spaceCells) {

		double min = Double.MAX_VALUE;

		Cell cellPointer = null;

		for (Cell[] line : spaceCells) {
			for (Cell currentCell : line) {

				if (!currentCell.isVisited() && (currentCell.getDistanceFromStart() < min)
						&& currentCell.containsProperty(CellProperty.Stepable)) {
					cellPointer = currentCell;
					min = currentCell.getDistanceFromStart();
				}

			}
		}

		return cellPointer;

	}

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

					item.initializeProperties();
				}
			}
		}

	}

	/**
	 * Search algorithm for the shortest way from cell start to cell end
	 */
	private void innerExcute(GameLocation start, GameLocation end) {

		ArrayList<GameLocation> doors = this.worldMap.getRoomsRoad(start.getRoomId(), end.getRoomId());

		doors.add(0, start);
		doors.add(end);

		Cell temp = this.endCell;

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

	}

	private boolean isCellStepable(Cell calculatedCell) {

		return calculatedCell.containsProperty(CellProperty.Stepable);

	}

}
