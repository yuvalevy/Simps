package sims.module.calculators;

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

	private Cell room1start, room1end, room2start, room2end;

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
		this.startCell = null;
		this.endCell = null;

		initializeCells();

		innerExcute(start, end);

	}

	private void executeSingleRoom(Room currentRoom) {

		if (this.startCell == this.endCell) {
			Log.WriteLog("Start == End");
			return;
		}
		Log.WriteLog("Start single");
		Log.WriteLog("start cell: " + this.startCell.getCoordinate());
		Log.WriteLog("end cell: " + this.endCell.getCoordinate());

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
		Log.WriteLog("End single");
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

		ArrayList<GameLocation> allSteps = new ArrayList<>();

		Log.WriteLog("Implementing");

		convert(start, end);

		Log.WriteLog("STARTCELL " + this.startCell.getCoordinate());
		Log.WriteLog("ENDCELL " + this.endCell.getCoordinate());

		Log.WriteLog(" ");
		Cell nextCell = this.startCell;
		GameLocation nextLocation = this.startCell.getCoordinate();

		// nextCell = this.room2end;
		// nextLocation = this.room2end.getCoordinate();

		do {

			Log.WriteLog("Step++ " + nextLocation);

			allSteps.add(nextLocation);
			p.addStep(nextLocation);

			nextCell = nextCell.getPreviousCell();
			nextLocation = nextCell.getCoordinate();

		} while (nextCell.getPreviousCell() != nextCell);

		Log.WriteLog("Step++ " + nextLocation);

		allSteps.add(nextLocation);
		p.addStep(nextLocation);

		// this.doorPotention =
		// currentRoom.getNextRoom(helpPointer.getCoordinate());

		this.isActive = false;
		Log.WriteLog("");

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

		Log.WriteLog("Starting dijkstra");

		ArrayList<GameLocation> doors = this.worldMap.getRoomsRoad(start.getRoomId(), end.getRoomId());

		doors.add(0, start);
		doors.add(end);

		Cell temp = this.endCell;

		Log.WriteLog("---------");
		for (int i = 0; i < doors.size(); i++) {

			Log.WriteLog("STEP " + i + " " + doors.get(i));
		}
		Log.WriteLog("---------");

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

			// if (i == 2) {
			// this.startCell.setPreviousCell(this.startCell);
			// }

			this.endCell.setPreviousCell(this.startCell);

			// if (i == 0) {
			// this.room1start = this.startCell;
			// this.room1end = this.endCell;
			// } else {
			// this.room2start = this.startCell;
			// this.room2end = this.endCell;

			// int roomIndex = doors.get(i).getRoomId();

			// executeSingleRoom(this.worldMap.getRoom(roomIndex));

			if (temp != null) {
				// Log.WriteLog("startCell:" + this.startCell.getCoordinate() +
				// " his prev IS " + temp.getCoordinate());
				// Log.WriteLog("temp pre: " +
				// temp.getPreviousCell().getCoordinate());

				temp.setPreviousCell(this.endCell);
				// this.startCell.setPreviousCell(this.startCell);

			}

			temp = this.startCell;
		}

		temp.setPreviousCell(temp);

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
