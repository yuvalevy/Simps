package sims.module.calculators;

import java.awt.Point;
import java.util.ArrayList;

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
	// private GameLocation doorPotention;
	private Cell nextDoor;

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
			Log.WriteLog("Dijkstra.convert - not same room");
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

		if (this.isActive) {
			return;
		}

		this.isActive = true;

		initializeCells();

		innerExcute(start, end);

	}

	private void executeSingleRoom(Room currentRoom) {

		if (this.startCell == this.endCell) {
			Log.WriteLog("Start == End");
			return;
		}

		Cell[][] spaceCells = currentRoom.getCells();

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

		Log.WriteLog("Implementing");

		ArrayList<GameLocation> doors = this.worldMap.getRoomsRoad(2, 1);

		this.nextDoor = this.worldMap.getCell(doors.get(1));

		convert(start, end);

		this.endCell.setPreviousCell(this.nextDoor);
		this.nextDoor.setPreviousCell(this.nextDoor);

		Cell nextCell = this.startCell;
		Point nextLocation = this.startCell.getCoordinate();

		while (nextCell.getPreviousCell() != nextCell) {

			// Log.WriteLog("Step++ " + nextLocation);

			nextCell = nextCell.getPreviousCell();
			nextLocation = nextCell.getCoordinate();
			p.addStep(new GameLocation(nextLocation, start.getRoomId()));
		}

		// this.doorPotention =
		// currentRoom.getNextRoom(helpPointer.getCoordinate());

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
	private void innerExcute(GameLocation start, GameLocation end) {

		// if (start.getRoomId() == end.getRoomId()) {

		// Same room movement

		// convert(end, start);
		//
		// executeSingleRoom(this.worldMap.getFocusedRoom());
		//
		// } else {

		// Check for linked room

		ArrayList<GameLocation> doors = this.worldMap.getRoomsRoad(start.getRoomId(), end.getRoomId());

		// this.nextDoor = this.worldMap.getCell(doors.get(0));

		doors = new ArrayList<>();
		doors.add(0, start);
		doors.add(end);

		Cell temp = this.endCell;

		for (int i = 0; i < doors.size(); i += 2) {

			convert(doors.get(i + 1), doors.get(i));

			int roomIndex = doors.get(i).getRoomId();

			if (temp != null) {
				// temp.setPreviousCell(this.startCell);
			}

			executeSingleRoom(this.worldMap.getRoom(roomIndex));
			temp = this.endCell;
		}

		// }

		Log.WriteLog("Starting dijkstra");

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
