package sims.module.objects;

import java.awt.Polygon;

import sims.module.surface.GameLocation;

public class Door extends GameObject {

	private static int DOORS_COUNT = 1;

	private final GameLocation nextRoomStartingLocation;

	public Door(GameLocation nextRoomStartingLocation, Polygon doorSpace, GameLocation doorLocation) {
		super(DOORS_COUNT, doorSpace, doorLocation);

		DOORS_COUNT++;
		this.nextRoomStartingLocation = nextRoomStartingLocation;
	}

	public GameLocation getNextRoomStartingLocation() {

		return this.nextRoomStartingLocation;
	}

	@Override
	public Polygon getObjectShape() {
		return (Polygon) this.objectSize;
	}

}