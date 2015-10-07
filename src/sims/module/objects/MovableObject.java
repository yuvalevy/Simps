package sims.module.objects;

import java.awt.Point;
import java.awt.Shape;

import actions.Walk;
import sims.basics.Action;
import sims.basics.ActionsFactory;
import sims.module.surface.GameLocation;

public abstract class MovableObject extends GameObject {

	private final Walk walker;

	protected MovableObject(int objectId, Shape objShape, Point startingPoint, int staringRoom,
			Action... objectAction) {
		super(objectId, objShape, new GameLocation(startingPoint, staringRoom), objectAction);

		this.walker = ActionsFactory.getWalk(getObjectId());
		super.addAction(this.walker);

	}

	/**
	 * Adds step to object
	 *
	 * @param step
	 *            step in the shape of GameObject
	 */
	public void addStep(GameLocation step) {

		this.walker.addStep(step);

	}

}
