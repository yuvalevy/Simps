package sims.module.objects;

import java.awt.Polygon;

import sims.module.actions.ActionsFactory;
import sims.module.surface.GameLocation;

public class Door extends GameObject {

	public static int DOORS_COUNT = 1;

	private final GameLocation nextRoomStartingLocation;

	public Door(GameLocation nextRoomStartingLocation, Polygon doorSpace, GameLocation doorLocation) {
		super(DOORS_COUNT, doorSpace, doorLocation, ActionsFactory.getNothing("Door"));

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