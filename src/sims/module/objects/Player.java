package sims.module.objects;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;

public class Player extends MovableObject {

	// TODO: Add configuration for point location
	private static final Point PLAYER_STARTING_POINT = new Point(200, 200);
	private static final Rectangle PLAYER_RECT = new Rectangle(44, 44);

	private final String playerName;

	public Player(String playerName, int inRoom) {

		super(PLAYER_STARTING_POINT, inRoom, PLAYER_RECT);

		this.playerName = playerName;
	}

	@Override
	public Shape getObjectShape() {

		Point p = this.currentLocation.getLocation();
		return getObjectShape(p);
	}

	@Override
	public Shape getObjectShape(Point p) {

		Shape currentSpace = new Rectangle(p, this.objectSize.getBounds().getSize());
		return currentSpace;
	}

	public String getPlayerName() {

		return this.playerName;

	}

	@Override
	public boolean isOnObject(Point p) {

		Rectangle playerCurrentSpace = (Rectangle) getObjectShape();

		return playerCurrentSpace.contains(p);
	}

	public void tick() {

		setNextStep();

	}

}
