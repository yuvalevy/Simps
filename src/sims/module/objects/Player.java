package sims.module.objects;

import java.awt.Point;
import java.awt.Rectangle;

import sims.basics.config.ConfigurationManager;
import sims.module.actions.ActionsFactory;
import sims.module.actions.Search;
import sims.module.feelings.Feeling;
import sims.module.feelings.FeelingFactory;
import sims.module.surface.GameLocation;

public class Player extends MovableObject {

	public static int PLAYERS_COUNT = 1;
	private static final Point PLAYER_STARTING_POINT = ConfigurationManager.getPlayerStartingPoint();
	private static final Rectangle PLAYER_RECT = ConfigurationManager.getPlayerSize();

	private final String playerName;

	private final Feeling[] feelings;

	public Player(String playerName, int inRoom) {
		super(PLAYERS_COUNT, PLAYER_RECT, new GameLocation(PLAYER_STARTING_POINT, inRoom), "Player");

		PLAYERS_COUNT++;
		this.playerName = playerName;

		this.feelings = new Feeling[FeelingFactory.getExisingFeelingCount()];

		createFeeling();
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

	public void createFeeling() {

		this.feelings[0] = FeelingFactory.getBored();
		this.feelings[1] = FeelingFactory.getHave2Pee();
		this.feelings[2] = FeelingFactory.getHungry();
		this.feelings[3] = FeelingFactory.getTired();

	}

	public Feeling[] getFeelings() {

		return this.feelings;
	}

	@Override
	public Rectangle getObjectShape() {

		Point p = this.currentLocation.getLocation();
		return getObjectShape(p);
	}

	public String getPlayerName() {

		return this.playerName;

	}

	public void increaceSuffering(int suffringAmount) {

		for (Feeling feeling : this.feelings) {
			feeling.increaceSuffering(suffringAmount);
		}
	}

	@Override
	public void tick() {
		super.tick();
		increaceSuffering(FeelingFactory.getIncreacingAmount());
	}
}
