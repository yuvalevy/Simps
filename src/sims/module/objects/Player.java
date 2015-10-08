package sims.module.objects;

import java.awt.Point;
import java.awt.Rectangle;

import sims.module.actions.ActionsFactory;

public class Player extends MovableObject {

	public static int PLAYERS_COUNT = 1;
	// TODO: Add configuration for point location
	private static final Point PLAYER_STARTING_POINT = new Point(200, 200);
	private static final Rectangle PLAYER_RECT = new Rectangle(44, 44);

	public static Rectangle getObjectShape(Point p) {

		Rectangle currentSpace = new Rectangle(p, PLAYER_RECT.getSize());
		return currentSpace;
	}

	private final String playerName;

	public Player(String playerName, int inRoom) {
		super(PLAYERS_COUNT, PLAYER_RECT, PLAYER_STARTING_POINT, inRoom, ActionsFactory.getNothing("Player"));

		PLAYERS_COUNT++;
		this.playerName = playerName;

	}

	@Override
	public Rectangle getObjectShape() {

		Point p = this.currentLocation.getLocation();
		return getObjectShape(p);
	}

	public String getPlayerName() {

		return this.playerName;

	}

	public boolean interput() {
		return interput();
	}

	@Override
	public void tick() {

		super.tick();
	}

}
