package sims.module.surface;

import java.awt.Point;

public class GameLocation {

	private Point location;

	private int roomId;

	public GameLocation(Point location) {

		this.location = location;
		this.roomId = 1;
	}

	public GameLocation(Point location, int roomId) {

		this.location = location;
		this.roomId = roomId;
	}

	/**
	 * Gets Player current location on map
	 *
	 * @return the currentLocation
	 */
	public Point getLocation() {
		return this.location;
	}

	public int getRoomId() {
		return this.roomId;
	}

	public void setLocation(Point p) {
		this.location = p;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

}
