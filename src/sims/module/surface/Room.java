package sims.module.surface;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;

import sims.basics.Randomaizer;
import sims.basics.configurations.ConfigurationManager;
import sims.module.actions.ActionIdentifier;
import sims.module.objects.Door;
import sims.module.objects.Toy;

public class Room {

	private static int roomCount = 0;
	private final Cell[][] roomCells;

	private int roomId;
	private Door[] doors;
	private Polygon stepablePolygon;
	private final Toy[] toys;
	private int unfoundToys;

	public Room(int doorsCount, int toysCount, int width, int hight) {

		roomCount++;
		setRoomId(roomCount);

		createDoors(doorsCount);

		// TODO: cell count is configurable for future flexibility
		this.roomCells = new Cell[width][hight];
		this.toys = new Toy[toysCount];
		this.unfoundToys = toysCount;
	}

	Door getDoor(Point locationOnDoor) {

		for (Door door : this.doors) {
			if (door.contains(locationOnDoor)) {
				return door;
			}
		}

		return null;
	}

	/**
	 * Gets all toys in this room
	 *
	 * @return
	 */
	Toy[] getToys() {

		return this.toys;
	}

	boolean tryFindToy(Point p) {

		for (Toy toy : this.toys) {
			if (toy.contains(p)) {
				if (toy.trySetAction(ActionIdentifier.Nothing)) {
					this.unfoundToys--;
					return true;
				}
			}
		}
		return false;
	}

	public void createDefalutMap() {

		Rectangle cellSize = Cell.getCellSize();

		createDoorsCells(cellSize);

		this.stepablePolygon = ConfigurationManager.getRoomPolygonStepable(this.roomId);

		createNonDoorCells(cellSize);

		makeNeighbors();

		putToy();

	}

	/**
	 * Returns area main cell property.
	 *
	 * @param objRect
	 * @return If objRect is intersects with doorSpace, returns
	 *         CellProperty.Door. If objRect is fully contained in
	 *         stepablePolygon, returns CellProperty.Stepable. Otherwise,
	 *         returns CellProperty.NoProperty.
	 */
	public CellProperty getAreaCellType(Rectangle objRect) {

		// Only intersects... means only touching it
		for (Door door : this.doors) {
			if (door.intersects(objRect)) {
				return CellProperty.Door;
			}
		}

		// Contains it all
		if (isFullyOnPropery(objRect, CellProperty.Stepable)) {
			return CellProperty.Stepable;
		}

		return CellProperty.NoProperty;
	}

	public Cell getCell(Point p) {

		for (int i = 0; i < this.roomCells.length; i++) {
			for (int j = 0; j < this.roomCells[i].length; j++) {
				if (this.roomCells[i][j].isOnCell(p)) {
					return this.roomCells[i][j];
				}
			}
		}
		return null;
	}

	public Cell[][] getCells() {

		return this.roomCells;

	}

	public Door getDoor(int nextRoomId) {

		for (Door door : this.doors) {
			if (door.getNextRoomStartingLocation().getRoomId() == nextRoomId) {
				return door;
			}
		}

		return null;

	}

	public GameLocation getNextRoom(Point locationOnDoor) {

		Door $ = getDoor(locationOnDoor);

		if ($ == null) {
			return null;
		}

		return $.getNextRoomStartingLocation();
	}

	public int getRoomId() {
		return this.roomId;
	}

	public int getUnfoundToys() {
		return this.unfoundToys;
	}

	public boolean isFullyOnPropery(Rectangle objRect, CellProperty property) {

		boolean step = this.stepablePolygon.contains(objRect);

		switch (property) {
		case Door:

			for (Door door : this.doors) {
				if (door.contains(objRect)) {
					return true;
				}
			}

			break;
		case Stepable:
			return step;
		case NoProperty:
			return (!step);
		}
		return false;
	}

	/**
	 * @param doorsCount
	 */
	private void createDoors(int doorsCount) {
		doorsCount = ConfigurationManager.getRoomDoorsCount(this.roomId);

		this.doors = new Door[doorsCount];

		Polygon[] doorsPolys = ConfigurationManager.getRoomDoorsPolygons(this.roomId, doorsCount);

		for (int i = 0; i < doorsPolys.length; i++) {

			GameLocation nextDoor = ConfigurationManager.getDoorNextRoomStartingLocation(this.roomId, i);
			GameLocation doorLocation = ConfigurationManager.getDoorLocation(this.roomId, i);

			this.doors[i] = new Door(nextDoor, doorsPolys[i], doorLocation);

		}
	}

	/**
	 * Creates all room's door area
	 *
	 * @param cellsize
	 */
	private void createDoorsCells(Rectangle cellsize) {

		for (int i = 0; i < this.roomCells.length; i++) {
			for (int j = 0; j < this.roomCells[i].length; j++) {

				Point coordinate = new Point(i * cellsize.width, j * cellsize.height);

				GameLocation location = new GameLocation(coordinate, this.roomId);

				for (Door door : this.doors) {

					if (door.contains(coordinate)) {

						this.roomCells[i][j] = new Cell(location, CellProperty.Stepable, CellProperty.Door);
						break;

					}
				}
			}
		}
	}

	/**
	 * Creates all room's non-door area
	 *
	 * @param cellSize
	 */
	private void createNonDoorCells(Rectangle cellSize) {

		for (int i = 0; i < this.roomCells.length; i++) {
			for (int j = 0; j < this.roomCells[i].length; j++) {
				if (this.roomCells[i][j] == null) {

					Point coordinate = new Point(i * cellSize.width, j * cellSize.height);
					GameLocation location = new GameLocation(coordinate, this.roomId);

					if (this.stepablePolygon.contains(coordinate)) {

						this.roomCells[i][j] = new Cell(location, CellProperty.Stepable);

					} else {

						this.roomCells[i][j] = new Cell(location, CellProperty.NoProperty);

					}
				}
			}
		}
	}

	private void makeNeighbors() {

		final double verticalMove = 10, ofekiMove = 10,
				diagonalMove = Math.sqrt((verticalMove * verticalMove) + (ofekiMove * ofekiMove));

		for (int i = 1; i < (this.roomCells.length - 1); i++) {
			for (int j = 1; j < (this.roomCells[i].length - 1); j++) {

				// [i-1][j-1] [i-1][j] [i-1][j+1]
				// [i][j-1] ****** [i][j+1]
				// [i+1][j-1] [i+1][j] [i+1][j+1]

				this.roomCells[i][j].addTupleNeighbors(this.roomCells[i - 1][j - 1], diagonalMove);
				this.roomCells[i][j].addTupleNeighbors(this.roomCells[i - 1][j], ofekiMove);
				this.roomCells[i][j].addTupleNeighbors(this.roomCells[i - 1][j + 1], diagonalMove);

				this.roomCells[i][j].addTupleNeighbors(this.roomCells[i][j - 1], verticalMove);
				this.roomCells[i][j].addTupleNeighbors(this.roomCells[i][j + 1], verticalMove);

				this.roomCells[i][j].addTupleNeighbors(this.roomCells[i + 1][j - 1], diagonalMove);
				this.roomCells[i][j].addTupleNeighbors(this.roomCells[i + 1][j], ofekiMove);
				this.roomCells[i][j].addTupleNeighbors(this.roomCells[i + 1][j + 1], diagonalMove);

			}
		}

		// Frame

		for (int i = 0; i < (this.roomCells.length - 1); i++) {
			int endJ = this.roomCells[i].length - 1;

			this.roomCells[i][endJ].addTupleNeighbors(this.roomCells[i + 1][endJ], ofekiMove);
			this.roomCells[i][0].addTupleNeighbors(this.roomCells[i + 1][0], ofekiMove);

		}

		for (int j = 0; j < (this.roomCells[0].length - 1); j++) {
			int endI = this.roomCells.length - 1;

			this.roomCells[0][j].addTupleNeighbors(this.roomCells[0][j + 1], 1);
			this.roomCells[endI][j].addTupleNeighbors(this.roomCells[endI][j + 1], 1);
		}

	}

	private void putToy() {

		for (int i = 0; i < this.toys.length; i++) {

			GameLocation randomLocation = Randomaizer.getRandomPlace(this, CellProperty.NoProperty);

			// Log.WriteLineLog("Adding toy[" + i + "] in room " + this.roomId +
			// " Location: " + randomLocation);
			this.toys[i] = new Toy(randomLocation);
		}

	}

	private void setRoomId(int roomId) {

		this.roomId = roomId;

	}

}
