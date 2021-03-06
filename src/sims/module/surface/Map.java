/**
 *
 */
package sims.module.surface;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;

import sims.basics.Log;
import sims.basics.LogLevel;
import sims.module.objects.Door;
import sims.module.objects.Toy;

/**
 * @author Yuval
 *
 */
public class Map {

	private final ArrayList<Room> mapRooms;

	private Room focusedRoom;
	private boolean isMapInit = false;

	private final int roomDefaultWidth;
	private final int roomDefaultHeight;

	/**
	 * Creates default map.
	 */
	public Map(Dimension screenDimension, Rectangle defaultCellSize) {

		Cell.setCellSize(defaultCellSize);

		this.mapRooms = new ArrayList<Room>();

		// this.roomDefaultHight = ((int) (screenDimension.height * 0.7)) /
		// defaultCellSize.height;
		this.roomDefaultHeight = screenDimension.height / defaultCellSize.height;

		this.roomDefaultWidth = screenDimension.width / defaultCellSize.width;

	}

	public void addRoom(int roomId, int toysCount) {

		this.mapRooms.add(new Room(1, toysCount, this.roomDefaultWidth, this.roomDefaultHeight));

	}

	/**
	 * Creates the clear map with door configuration
	 */
	public void createDefalutMap() {

		if (this.mapRooms.size() == 0) {

			Log.WriteLineLog("There are no rooms.", LogLevel.ERROR);

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

	public Cell getCell(GameLocation start) {

		Room currentRoom = getRoom(start.getRoomId());

		return currentRoom.getCell(start.getLocation());
	}

	public Door getDoor(GameLocation objectLocation) {

		return getRoom(objectLocation.getRoomId()).getDoor(objectLocation.getLocation());

	}

	public Room getFocusedRoom() {

		return this.focusedRoom;

	}

	public Room getRoom(int roomId) {

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

	public ArrayList<Room> getRooms() {

		return this.mapRooms;
	}

	public ArrayList<GameLocation> getRoomsRoad(int roomId, int roomId2) {

		ArrayList<GameLocation> $ = new ArrayList<>();

		int adder = 0;
		if (roomId == roomId2) {
			return $;
		}
		if (roomId < roomId2) {
			adder = 1;
		} else {
			adder = -1;
		}

		int nextRoomId = roomId;
		Room startRoom = null;

		do {
			startRoom = getRoom(nextRoomId);
			nextRoomId += adder;
			Door nextDoor = startRoom.getDoor(nextRoomId);

			$.add(nextDoor.getCurrentLocation());
			$.add(nextDoor.getNextRoomStartingLocation());

		} while (nextRoomId != roomId2);

		return $;

	}

	public ArrayList<Toy> getToys() {

		ArrayList<Toy> $ = new ArrayList<>();

		for (Room room : this.mapRooms) {

			$.addAll(Arrays.asList(room.getToys()));
		}

		return $;
	}

	public boolean isMapInit() {

		return this.isMapInit;

	}

	public void setFocusedRoom(int roomId) {

		Room currentRoom = getRoom(roomId);

		this.focusedRoom = currentRoom;
	}

	public boolean tryFindToy(GameLocation newLocation) {

		return getRoom(newLocation.getRoomId()).tryFindToy(newLocation.getLocation());
	}
}