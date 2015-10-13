package sims.module.objects;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.ImageIcon;

import sims.basics.Log;
import sims.module.actions.Action;
import sims.module.actions.ActionIdentifier;
import sims.module.actions.ActionsFactory;
import sims.module.actions.Nothing;
import sims.module.surface.GameLocation;

public abstract class GameObject {

	protected ArrayList<Action> objectActions;
	protected Action currentAction;
	private final ActionIdentifier defaultActionIdentifier;

	protected GameLocation currentLocation;
	protected Shape objectSize;

	protected int objectId;

	protected GameObject(int objectId, Shape objShape, GameLocation startingLocation, String className,
			Action... objectActions) {

		this.objectId = objectId;
		this.objectSize = objShape;
		this.currentLocation = startingLocation;

		this.objectActions = new ArrayList<>();

		Nothing defaultAction = ActionsFactory.getNothing(className);
		this.objectActions.add(defaultAction);
		this.objectActions.addAll(Arrays.asList(objectActions));

		this.defaultActionIdentifier = defaultAction.getIdentifier();

		this.currentAction = defaultAction;
		this.currentAction.start();
	}

	public void addAction(Action action) {

		Action tempAction = getAction(action.getIdentifier());
		if (tempAction != null) {
			removeAction(tempAction);
		}
		this.objectActions.add(action);
	}

	public boolean canInterruptAction() {
		return this.currentAction.canInterrupt();
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

	public void removeAction(Action tempAction) {

		if (tempAction != null) {
			this.objectActions.remove(tempAction);
		}
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
			Log.WriteLineLog("Cannot change action{" + identifier + "}. current action{"
					+ this.currentAction.getIdentifier() + "} is not intteruptable");
			return false;
		}

		Action action = getAction(identifier);

		if (action != null) {

			Log.WriteLineLog("{" + getObjectId() + "} - stopping {" + this.currentAction.getIdentifier()
					+ "} and starting {" + identifier + "}");
			this.currentAction.stop();

			setAction(action);

			return true;

		}

		Log.WriteLineLog("Cannot find action " + identifier);
		return false;
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

		if (this.currentAction.canInterrupt()) {
			return true;
		}

		return false;
	}

	/**
	 * @param identifier
	 * @param a
	 * @return
	 */
	private Action getAction(ActionIdentifier identifier) {

		for (Action action : this.objectActions) {
			if (action.isAction(identifier)) {

				return action;
			}
		}
		return null;
	}

	/**
	 * @param action
	 * @param identifier
	 */
	private void setAction(Action action) {

		this.currentAction = action;
		this.currentAction.start();

		Log.WriteLineLog(getObjectId() + " stating " + this.currentAction.getIdentifier());
	}

	private void setDefaultAction() {

		trySetAction(this.defaultActionIdentifier);
	}

}