/**
 *
 */
package sims.module.surface;

import java.util.ArrayList;

import sims.basics.Log;
import sims.basics.LogLevel;
import sims.module.objects.Room;

/**
 * @author Yuval
 *
 */
public class Map {

	private final ArrayList<Room> mapRooms;

	private Room focusedRoom;
	private boolean isMapInit = false;

	private final int defaultWidth;
	private final int defaultHight;

	/**
	 * Creates default map.
	 */
	public Map(int width, int hight) {

		this.mapRooms = new ArrayList<Room>();

		this.defaultHight = hight;
		this.defaultWidth = width;

	}

	public void addRoom(int roomId) {

		this.mapRooms.add(new Room(1, this.defaultWidth, this.defaultHight));

	}

	/**
	 * Creates the clear map with door configuration
	 */
	public void createDefalutMap() {

		if (this.mapRooms.size() == 0) {

			Log.WriteLog("There are no rooms.", LogLevel.Error);

			try {
				throw new Exception("Cannot start game. There are no rooms.");
			} catch (Exception e) {
				e.printStackTrace();
			}

			return;

		}

		for (Room room : this.mapRooms) {

			room.createDefalutMap();
		}

		this.isMapInit = true;
	}

	public Room getFocusedRoom() {

		return this.focusedRoom;

	}

	private Room getRoom(int roomId) {

		for (Room room : this.mapRooms) {
			if (room.getRoomId() == roomId) {
				return room;
			}
		}

		return null;

	}

	public int getRoomCount() {

		return this.mapRooms.size();

	}

	public boolean isMapInit() {

		return this.isMapInit;

	}

	public void setFocusedRoom(int roomId) {

		Room currentRoom = getRoom(roomId);

		this.focusedRoom = currentRoom;
	}
}