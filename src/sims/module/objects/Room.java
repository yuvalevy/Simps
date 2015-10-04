package sims.module.objects;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;

import sims.module.main.ConfigurationManager;
import sims.module.surface.Cell;
import sims.module.surface.CellFactory;
import sims.module.surface.CellType;
import sims.module.surface.GameLocation;

public class Room {

	private static int roomCount = 0;
	private final Cell[][] cells;

	private int roomId;
	private final Door[] doors;
	private Polygon stepablePolygon;

	public Room(int doorsCount, int width, int hight) {

		roomCount++;
		setRoomId(roomCount);

		this.doors = new Door[doorsCount];

		Polygon[] doorsPolys = ConfigurationManager.getRoomDoorsPolygons(this.roomId, doorsCount);

		for (int i = 0; i < doorsPolys.length; i++) {

			GameLocation nextDoor = ConfigurationManager.getDoorNextRoomStartingLocation(this.roomId, i);
			this.doors[i] = new Door(nextDoor, doorsPolys[i]);

		}

		// TODO: cell count is configurable for future flexibility
		this.cells = new Cell[width][hight];

	}

	public void createDefalutMap() {

		Rectangle cellsize = Cell.getCellSize();

		for (int i = 0; i < this.cells.length; i++) {

			for (int j = 0; j < this.cells[i].length; j++) {

				Point coordinate = new Point(i * cellsize.width, j * cellsize.height);

				for (Door door : this.doors) {

					if (door.getDoorSpace().contains(coordinate)) {

						this.cells[i][j] = CellFactory.getCell(true, true, coordinate);
						break;
					}
				}
			}
		}

		this.stepablePolygon = ConfigurationManager.getRoomPolygonStepable(this.roomId);

		for (int i = 0; i < this.cells.length; i++)

		{

			for (int j = 0; j < this.cells[i].length; j++) {

				if (this.cells[i][j] == null) {

					Point coordinate = new Point(i * cellsize.width, j * cellsize.height);

					if (this.stepablePolygon.contains(coordinate)) {

						this.cells[i][j] = CellFactory.getCell(true, false, coordinate);

					} else {

						this.cells[i][j] = CellFactory.getCell(false, false, coordinate);

					}
				}

			}
		}

	}

	public CellType getAreaCellType(Rectangle playerRect) {

		// Only intersects... means only touching it
		for (Door door : this.doors) {

			if (door.getDoorSpace().intersects(playerRect)) {

				return CellFactory.getCellType(true, true);

			}

		}

		// Contains it all
		if (this.stepablePolygon.contains(playerRect)) {

			return CellFactory.getCellType(true, false);

		}

		return CellFactory.getCellType(false, false);
	}

	public Cell[][] getCells() {

		return this.cells;

	}

	public Door getDoor(Point objectLocation) {

		for (Door door : this.doors) {

			if (door.getDoorSpace().contains(objectLocation)) {

				return door;

			}

		}

		return null;

	}

	public GameLocation getNextRoom(Point locationOnDoor) {

		Door $ = getDoor(locationOnDoor);

		return $.getNextRoomStartingLocation();
	}

	/*
	 * public CellType getCellType(Point cellLocation) {
	 *
	 * CellType type = null;
	 *
	 * int i = cellLocation.x / Cell.getCellSize().width; int j = cellLocation.y
	 * / Cell.getCellSize().height;
	 *
	 * Cell chosen = this.cells[i][j];
	 *
	 * type = chosen.getCellType();
	 *
	 * return type;
	 *
	 * }
	 */
	public int getRoomId() {
		return this.roomId;
	}

	public void setRoomId(int roomId) {

		this.roomId = roomId;

	}
}
