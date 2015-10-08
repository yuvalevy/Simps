package sims.module.objects;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.ImageIcon;

import sims.basics.Action;
import sims.basics.Log;
import sims.module.actions.ActionIdentifier;
import sims.module.surface.GameLocation;

public abstract class GameObject {

	protected ArrayList<Action> objectActions;
	protected Action currentAction;
	private final ActionIdentifier defaultActionIdentifier;
	protected GameLocation currentLocation;
	protected Shape objectSize;

	protected int objectId;

	protected GameObject(int objectId, Shape objShape, GameLocation startingLocation, Action defaultAction,
			Action... objectActions) {

		this.objectId = objectId;
		this.objectSize = objShape;
		this.currentLocation = startingLocation;

		this.objectActions = new ArrayList<>();

		this.objectActions.add(defaultAction);
		this.objectActions.addAll(Arrays.asList(objectActions));

		this.defaultActionIdentifier = defaultAction.getIdentifier();

		this.currentAction = defaultAction;

	}

	protected void addAction(Action action) {

		this.objectActions.add(action);
	}

	/**
	 * If the action is not over and it's not interruptible, action cannot be
	 * changed
	 *
	 * @return true if it is allow to change action. false otherwise
	 */
	private boolean canChangeAction() {
		if (this.currentAction.isOver()) {
			return true;
		}

		if (this.currentAction.interupt()) {
			return true;
		}

		return false;
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

	public ImageIcon getNextImage() {
		return this.currentAction.getNextImage();
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

	public boolean interuptAction() {
		return this.currentAction.interupt();
	}

	private void setDefaultAction() {

		trySetAction(this.defaultActionIdentifier);
	}

	public void tick() {

		GameLocation actionLocation = this.currentAction.tick();

		if (actionLocation != null) {
			this.currentLocation = actionLocation;
		}

		if (this.currentAction.isOver()) {
			setDefaultAction();
		}
	}

	/**
	 * Trying to change the current action. If it is not allowed nothing happen.
	 *
	 * @param identifier
	 * @return true is changing was successful. false otherwise
	 */
	public boolean trySetAction(ActionIdentifier identifier) {

		if (!canChangeAction()) {
			return false;
		}

		for (Action action : this.objectActions) {
			if (action.isAction(identifier)) {

				this.currentAction = action;
				Log.WriteLineLog("Yuval" + this.objectId + "CurrentAction = " + identifier);
				return true;
			}
		}

		Log.WriteLineLog("Cannot find action " + identifier);
		return false;
	}

}