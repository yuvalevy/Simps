package sims.module.objects;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;

import sims.module.surface.GameLocation;

public class Toy extends GameObject {

	private static int TOYS_COUNT = 1;
	private static final Rectangle TOY_RECT = new Rectangle(44, 44);

	private boolean isFound;

	public Toy(GameLocation toyLocation) {
		super(TOYS_COUNT, TOY_RECT, toyLocation);

		TOYS_COUNT++;
		this.isFound = false;
	}

	@Override
	public Shape getObjectShape() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Shape getObjectShape(Point p) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isFound() {
		return this.isFound;
	}

	public void toyFound() {
		this.isFound = true;
	}

}
