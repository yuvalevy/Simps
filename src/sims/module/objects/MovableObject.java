package sims.module.objects;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.LinkedList;

import sims.module.surface.GameLocation;

public abstract class MovableObject {

	protected GameLocation currentLocation;
	protected Rectangle objectSize;
	protected LinkedList<GameLocation> steps;

	protected MovableObject(GameLocation startingLocation, Rectangle objRect) {

		// currentLocation. setCurrentRoomId(0);
		this.steps = new LinkedList<>();
		this.objectSize = objRect;
		this.currentLocation = startingLocation;

	}

	/**
	 * Adds step to object
	 *
	 * @param step
	 *            step in the shape of GameObject
	 */
	public void addStep(GameLocation step) {

		this.steps.offer(step);

	}

	/**
	 * Returns object current location
	 *
	 * @return current location
	 */
	public GameLocation getCurrentLocation() {

		return this.currentLocation;

	}

	/**
	 * Returns the next step to be stepped on, and changes currentLoction If
	 * there are no more steps, returns currentLocation
	 * 
	 * @return next step to be stepped on
	 */
	public GameLocation getNextStep() {

		if (this.steps.size() == 0) {
			return this.currentLocation;
		}

		GameLocation newStep = this.steps.pop();
		this.currentLocation = newStep;

		return newStep;
	}

	/**
	 *
	 * @param p
	 *            Point to be checked
	 * @return true if object on point. false otherwise
	 */
	public boolean isOnObject(Point p) {

		Point location = this.currentLocation.getLocation();
		Rectangle currentSpace = new Rectangle(location, this.objectSize.getSize());

		return currentSpace.contains(p);
	}

}
