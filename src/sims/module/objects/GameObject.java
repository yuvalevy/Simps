package sims.module.objects;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;

import sims.module.surface.GameLocation;

public abstract class GameObject {

	protected GameLocation currentLocation;
	protected Shape objectSize;

	protected int objectId;

	protected GameObject(int objectId, Shape objShape, GameLocation startingLocation) {

		this.objectId = objectId;
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
	 *
	 * @param r
	 *            Rectanle to be checked
	 * @return true if object on rectangle. false otherwise
	 */
	public boolean contains(Rectangle r) {

		return this.objectSize.contains(r);

	}

	/**
	 * Returns object current location
	 *
	 * @return current location
	 */
	public GameLocation getCurrentLocation() {

		return this.currentLocation;

	}

	public int getObjectId() {
		return this.objectId;
	}

	/**
	 * Returns object current shape space
	 *
	 * @return
	 */
	public Shape getObjectShape() {
		return this.objectSize;
	}

	public boolean intersects(Rectangle r) {

		return this.objectSize.intersects(r);

	}
}