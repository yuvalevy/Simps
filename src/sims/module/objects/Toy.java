package sims.module.objects;

import java.awt.Point;
import java.awt.Rectangle;

import sims.module.actions.ActionsFactory;
import sims.module.surface.GameLocation;

public class Toy extends GameObject {

	public static int TOYS_COUNT = 0;
	private static final Rectangle TOY_RECT = new Rectangle(128, 128);

	public static Rectangle getObjectShape(Point p) {

		Rectangle currentSpace = new Rectangle(p, TOY_RECT.getSize());
		return currentSpace;

	}

	private boolean isFound;

	public Toy(GameLocation toyLocation) {
		super(TOYS_COUNT, TOY_RECT, toyLocation, ActionsFactory.getNothing("Toy"));

		TOYS_COUNT++;
		this.isFound = false;
	}

	@Override
	public Rectangle getObjectShape() {

		Point p = this.currentLocation.getLocation();
		return getObjectShape(p);
	}

	public boolean isFound() {
		return this.isFound;
	}

	void toyFound() {
		this.isFound = true;
	}

}
