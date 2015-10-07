package sims.module.objects;

import java.awt.Point;
import java.awt.Rectangle;
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
	 *
	 * @param p
	 *            Point to be checked
	 * @return true if object on point. false otherwise
	 */
	public boolean contains(Point p) {

		return this.objectSize.contains(p);

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

	public boolean intersects(Rectangle r) {

		return this.objectSize.intersects(r);

	}

}