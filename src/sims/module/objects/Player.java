package sims.module.objects;

import java.awt.Point;
import java.awt.Rectangle;

import sims.module.surface.GameLocation;

public class Player extends MovableObject {

	// TODO: Add configuration for point location
	private static final Point PLAYER_STARTING_POINT = new Point(500, 500);
	private static final GameLocation PLAYER_STARTING_LOCATION = new GameLocation(PLAYER_STARTING_POINT, 1);
	private static final Rectangle PLAYER_RECT = new Rectangle(44, 44);

	private final String playerName;

	public Player(String playerName) {
		super(PLAYER_STARTING_LOCATION, PLAYER_RECT);

		this.playerName = playerName;
	}

	public String getPlayerName() {
		return this.playerName;
	}

}
