package sims.module.objects;

import java.awt.Point;
import java.awt.Shape;

import sims.module.surface.GameLocation;

public abstract class GameObject {

	protected GameLocation currentLocation;
	protected Shape objectSize;

	protected GameObject(Shape objShape, GameLocation startingLocation) {

		this.objectSize = objShape;
		this.currentLocation = startingLocation;
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
	 * Returns object current shape space
	 *
	 * @return
	 */
	public abstract Shape getObjectShape();

	public abstract Shape getObjectShape(Point p);

	/**
	 *
	 * @param p
	 *            Point to be checked
	 * @return true if object on point. false otherwise
	 */
	public abstract boolean isOnObject(Point p);

}