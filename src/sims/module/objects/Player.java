package sims.module.objects;

import java.awt.Point;
import java.awt.Rectangle;

import sims.module.actions.ActionsFactory;
import sims.module.actions.Search;
import sims.module.surface.GameLocation;

public class Player extends MovableObject {

	public static int PLAYERS_COUNT = 1;
	// TODO: Add configuration for point location
	private static final Point PLAYER_STARTING_POINT = new Point(200, 200);
	private static final Rectangle PLAYER_RECT = new Rectangle(44, 44);

	private final String playerName;

	public Player(String playerName, int inRoom) {
		super(PLAYERS_COUNT, PLAYER_RECT, new GameLocation(PLAYER_STARTING_POINT, inRoom), "Player");

		PLAYERS_COUNT++;
		this.playerName = playerName;

		addSearch();
	}

	public static Rectangle getObjectShape(Point p) {

		Rectangle currentSpace = new Rectangle(p, PLAYER_RECT.getSize());
		return currentSpace;
	}

	public void addSearch() {

		Search search = ActionsFactory.getSearch(getWalker());

		addAction(search);
	}

	@Override
	public boolean contains(Point p) {

		Rectangle objRect = new Rectangle(p, PLAYER_RECT.getSize());
		return objRect.contains(p);

	}

	@Override
	public Rectangle getObjectShape() {

		Point p = this.currentLocation.getLocation();
		return getObjectShape(p);
	}

	public String getPlayerName() {

		return this.playerName;

	}
}
