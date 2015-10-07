package sims.module.objects;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.ImageIcon;

import actions.Nothing;
import sims.basics.Action;
import sims.module.surface.GameLocation;

public abstract class GameObject {

	protected ArrayList<Action> objectActions;
	protected int currentAction;
	private final int defaultAction;
	protected GameLocation currentLocation;
	protected Shape objectSize;

	protected int objectId;

	protected GameObject(int objectId, Shape objShape, GameLocation startingLocation, Action... objectActions) {

		this.objectId = objectId;
		this.objectSize = objShape;
		this.currentLocation = startingLocation;

		this.objectActions = new ArrayList<>();

		this.objectActions.addAll(Arrays.asList(objectActions));
		this.defaultAction = objectActions.length;
		addAction(new Nothing());

	}

	protected void addAction(Action action) {

		this.objectActions.add(action);
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

	public void executeAction() {

		Action action = this.objectActions.get(this.currentAction);
		GameLocation actionLocation = action.execute();

		if (actionLocation != null) {
			this.currentLocation = actionLocation;
		}

		if (action.isOver()) {
			setDefaultAction();
		}
	}

	private Action getCurrentAction() {
		return this.objectActions.get(this.currentAction);
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
		return this.objectActions.get(this.currentAction).getNextImage();
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
		return this.objectActions.get(this.currentAction).interupt();
	}

	private void setDefaultAction() {
		this.currentAction = this.defaultAction;
	}

}