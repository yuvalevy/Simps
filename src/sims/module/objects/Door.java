package sims.module.objects;

import java.awt.Polygon;

import sims.module.surface.GameLocation;

public class Door {

	private final GameLocation nextRoomStartingLocation;
	private final Polygon doorSpace;
	private final GameLocation doorLocation;

	public Door(GameLocation nextRoomStartingLocation, Polygon doorSpace, GameLocation doorLocation) {

		this.nextRoomStartingLocation = nextRoomStartingLocation;
		this.doorSpace = doorSpace;
		this.doorLocation = doorLocation;
	}

	public GameLocation getDoorLocation() {
		return this.doorLocation;
	}

	public Polygon getDoorSpace() {
		return this.doorSpace;
	}

	public GameLocation getNextRoomStartingLocation() {

		return this.nextRoomStartingLocation;
	}
}