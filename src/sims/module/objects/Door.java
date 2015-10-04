package sims.module.objects;

import java.awt.Polygon;

import sims.module.surface.GameLocation;

public class Door {

	private final GameLocation nextRoomStartingLocation;
	private final Polygon doorSpace;

	public Door(GameLocation nextRoomStartingLocation, Polygon doorSpace) {

		this.nextRoomStartingLocation = nextRoomStartingLocation;
		this.doorSpace = doorSpace;
	}

	public Polygon getDoorSpace() {
		return this.doorSpace;
	}

	public GameLocation getNextRoomStartingLocation() {

		return this.nextRoomStartingLocation;
	}
}