package sims.module.objects;

import java.awt.Polygon;

import sims.module.surface.Cell;
import sims.module.surface.CellFactory;

public class Room {

	private static int roomCount = 0;
	private int roomId;

	private final Cell[][] cells;

	// TODO: Create door spaces from configuration and remove
	// @SuppressWarnings("unused")
	@SuppressWarnings("unused")
	private final Polygon[] doorsPlaces;

	public Room(int doors, int width, int hight) {

		roomCount++;
		setRoomId(roomCount);

		this.doorsPlaces = new Polygon[doors];
		// TODO: cell count is configurable for future flexibility
		this.cells = new Cell[width][hight];
	}

	public void createDefalutMap() {
		// TODO: return
		// for (Polygon polygon : this.doorsPaces) {
		// for (int i = 0; i < polygon.npoints; i++) {
		// this.cells[polygon.xpoints[i]][polygon.ypoints[i]] =
		// CellFactory.getCell(true, true);
		// }
		// }

		for (int i = 0; i < this.cells.length; i++) {
			for (int j = 0; j < this.cells[i].length; j++) {
				if (this.cells[i][j] == null) {
					this.cells[i][j] = CellFactory.getCell(true, false);
				}
			}
		}

	}

	public int getRoomId() {
		return this.roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

}
