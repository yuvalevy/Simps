/**
 *
 */
package sims.module.surface;

import java.util.ArrayList;

import sims.module.objects.Room;

/**
 * @author Yuval
 *
 */
public class Map {

	private final ArrayList<Room> mapRooms;

	private Room focusedRoom;
	private boolean isMapInit = false;

	/**
	 * Creates default map.
	 */
	public Map(int width, int hight) {

		this.mapRooms = new ArrayList<Room>();

		this.mapRooms.add(new Room(1, width, hight));

		setFocusedRoom(this.mapRooms.get(0));

		createDefalutMap();
	}

	/**
	 * Creates the clear map with door configuration
	 */
	private void createDefalutMap() {

		for (Room room : this.mapRooms) {

			room.createDefalutMap();
		}

		this.isMapInit = true;
	}

	public Room getFocusedRoom() {
		return this.focusedRoom;
	}

	public boolean isMapInit() {
		return this.isMapInit;
	}

	public void setFocusedRoom(Room focusedRoom) {
		this.focusedRoom = focusedRoom;
	}

}
