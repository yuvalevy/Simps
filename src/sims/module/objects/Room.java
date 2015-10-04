package sims.module.objects;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;

import sims.module.main.ConfigurationManager;
import sims.module.surface.Cell;
import sims.module.surface.CellFactory;
import sims.module.surface.CellType;

public class Room {

	private static int roomCount = 0;
	private int roomId;

	private final Cell[][] cells;

	// TODO: Create door spaces from configuration and remove
	private final Polygon[] doorsPlaces;
	private Polygon stepablePolygon;

	public Room(int doors, int width, int hight) {

		roomCount++;
		setRoomId(roomCount);

		this.doorsPlaces = new Polygon[doors];
		// TODO: cell count is configurable for future flexibility
		this.cells = new Cell[width][hight];

	}

	public void createDefalutMap() {

		// TODO: return
		for (int i = 0; i < this.doorsPlaces.length; i++) {
			this.doorsPlaces[i] = new Polygon();
			// for (int i = 0; i < polygon.npoints; i++) {
			// this.cells[polygon.xpoints[i]][polygon.ypoints[i]] =
			// CellFactory.getCell(true, true);
			// }
		}

		Rectangle cellsize = Cell.getCellSize();

		this.stepablePolygon = ConfigurationManager.getRoomPolygonStepable(this.roomId);

		for (int i = 0; i < this.cells.length; i++) {

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
		for (Polygon polygon : this.doorsPlaces) {

			if (polygon.intersects(playerRect)) {

				return CellFactory.getCellType(true, false);

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

	public boolean idOnDoor(Point objectLocation) {

		for (Polygon door : this.doorsPlaces) {

			if (door.contains(objectLocation)) {

				return true;

			}

		}

		return false;

	}

	public void setRoomId(int roomId) {

		this.roomId = roomId;

	}
}
