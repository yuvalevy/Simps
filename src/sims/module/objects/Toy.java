package sims.module.objects;

import java.awt.Point;
import java.awt.Rectangle;

import sims.module.actions.ActionIdentifier;
import sims.module.actions.ActionsFactory;
import sims.module.surface.GameLocation;

public class Toy extends GameObject {

	public static int TOYS_COUNT = 0;
	private static final Rectangle TOY_RECT = new Rectangle(128, 128);

	public Toy(GameLocation toyLocation) {
		super(TOYS_COUNT, TOY_RECT, toyLocation, "Toy", ActionsFactory.getHide());

		TOYS_COUNT++;
		trySetAction(ActionIdentifier.Hide);
	}

	public static Rectangle getObjectShape(Point p) {

		Rectangle currentSpace = new Rectangle(p, TOY_RECT.getSize());
		return currentSpace;

	}

	@Override
	public boolean contains(Point p) {

		Point location = this.currentLocation.getLocation();

		Rectangle objRect = new Rectangle(location, TOY_RECT.getSize());
		return objRect.contains(p);

	}

	@Override
	public Rectangle getObjectShape() {

		Point p = this.currentLocation.getLocation();
		return getObjectShape(p);
	}
}
