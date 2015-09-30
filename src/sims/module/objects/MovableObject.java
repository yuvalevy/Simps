package sims.module.objects;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.LinkedList;

import sims.basics.Log;
import sims.module.surface.GameLocation;

public abstract class MovableObject {

	protected GameLocation currentLocation;
	protected Rectangle objectSize;
	protected LinkedList<GameLocation> steps;

	protected MovableObject(GameLocation startingLocation, Rectangle objRect) {
		// currentLocation. setCurrentRoomId(0);
		this.steps = new LinkedList<>();
		this.objectSize = objRect;
		move(startingLocation);
	}

	public void addSteps(GameLocation step) {
		this.steps.offer(step);
	}

	public GameLocation getCurrentLocation() {
		return this.currentLocation;
	}

	public GameLocation getNextStep() {

		Log.WriteLog("Getting next step for player");
		if (this.steps.size() == 0) {
			return this.currentLocation;
		}

		GameLocation newStep = this.steps.pop();
		move(newStep);

		return newStep;
	}

	public boolean isOnObject(Point p) {

		Point location = this.currentLocation.getLocation();
		Rectangle currentSpace = new Rectangle(location, this.objectSize.getSize());

		return currentSpace.contains(p);
	}

	/**
	 * Sets a new location for Player on map
	 *
	 * @param newLocation
	 *            the newLocation to set
	 */
	public void move(GameLocation newLocation) {

		// if (this.steps.peek() != null) {
		//
		// }

		this.currentLocation = newLocation;

		// if (newLocation.getRoomId() == this.currentLocation.getRoomId()) {
		//
		// Mabye use GameLocationFactory
		//
		// } else {
		//
		// }

		// this.currentLocation.setLocation(newLocation);
	}

}
