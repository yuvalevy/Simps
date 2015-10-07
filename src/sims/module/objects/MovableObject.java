package sims.module.objects;

import java.awt.Point;
import java.awt.Shape;
import java.util.LinkedList;

import sims.module.surface.GameLocation;

public abstract class MovableObject extends GameObject {

	protected LinkedList<GameLocation> steps;

	protected MovableObject(int objectId, Shape objShape, Point startingPoint, int staringRoom) {
		super(objectId, objShape, new GameLocation(startingPoint, staringRoom));

		this.steps = new LinkedList<GameLocation>();

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
	 * Sets the next step to currentLoction. If there are no more steps, nothing
	 * is done
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
