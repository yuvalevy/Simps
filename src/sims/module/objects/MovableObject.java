package sims.module.objects;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.LinkedList;

import sims.module.surface.GameLocation;

public class MovableObject {

	protected GameLocation currentLocation;
	protected Rectangle objectSize;
	protected LinkedList<GameLocation> steps;

	protected MovableObject(Point startingPoint, int staringRoom, Rectangle objRect) {

		this.steps = new LinkedList<GameLocation>();
		this.objectSize = objRect;
		this.currentLocation = new GameLocation(startingPoint, staringRoom);

	}

	/**
	 * Adds step to object
	 *
	 * @param step
	 *            step in the shape of GameObject
	 */
	public void addStep(GameLocation step) {

		this.steps.add(step);

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
	 * Returns object current rectangle space
	 *
	 * @return
	 */
	public Rectangle getObjectRectangle() {

		Point location = this.currentLocation.getLocation();

		return getObjectRectangle(location);
	}

	/**
	 * Returns object current rectangle space
	 *
	 * @return
	 */
	public Rectangle getObjectRectangle(Point p) {

		Rectangle currentSpace = new Rectangle(p, this.objectSize.getSize());

		return currentSpace;
	}

	/**
	 *
	 * @param p
	 *            Point to be checked
	 * @return true if object on point. false otherwise
	 */
	public boolean isOnObject(Point p) {

		Rectangle currentSpace = getObjectRectangle();

		return currentSpace.contains(p);
	}

	/**
	 * Sets the *next step to currentLoction. If there are no more steps,
	 * nothing is done
	 *
	 */
	protected void setNextStep() {

		if (this.steps.size() == 0) {
			return;
		}

		GameLocation newStep = this.steps.removeFirst();

		this.currentLocation = newStep;

	}

}
