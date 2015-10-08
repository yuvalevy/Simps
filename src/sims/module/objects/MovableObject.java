package sims.module.objects;

import java.awt.Point;
import java.awt.Shape;

import sims.basics.Action;
import sims.module.actions.ActionsFactory;
import sims.module.actions.Walk;
import sims.module.surface.GameLocation;

public abstract class MovableObject extends GameObject {

	private final Walk walker;

	protected MovableObject(int objectId, Shape objShape, Point startingPoint, int staringRoom, Action defalutAction,
			Action... objectActions) {
		super(objectId, objShape, new GameLocation(startingPoint, staringRoom), defalutAction, objectActions);
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
