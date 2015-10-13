package sims.module.objects;

import java.awt.Point;
import java.awt.Rectangle;

import sims.basics.Log;
import sims.basics.configurations.ConfigurationManager;
import sims.module.actions.Action;
import sims.module.actions.ActionsFactory;
import sims.module.actions.Search;
import sims.module.actions.WatchTV;
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
		addWatchTV();
	}

	public static Rectangle getObjectShape(Point p) {

		Rectangle currentSpace = new Rectangle(p, PLAYER_RECT.getSize());
		return currentSpace;
	}

	public void addSearch() {

		Search search = ActionsFactory.getSearch(getWalker());

		addAction(search);
	}

	public void addWatchTV() {

		WatchTV tv = ActionsFactory.getWatchTV(getWalker(), this.feelings[0]);

		addAction(tv);
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

	public boolean isPlayerSufferedEnough() {

		for (Feeling feeling : this.feelings) {
			if (feeling.isPlayerSufferedEnough()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void tick() {
		super.tick();
		increaceSuffering(FeelingFactory.getIncreacingAmount());
	}

	public GameLocation trySetAction(Feeling feeling) {

		Action action = getAction(feeling);

		if (action != null) {

			if (super.trySetAction(action.getIdentifier())) {
				return action.getActionDestination();
			}
		}

		Log.WriteLineLog("No action holds this feeling");
		return null;
	}

	private Action getAction(Feeling feel) {

		for (Action action : this.objectActions) {

			if (action.getFeeling() == feel) {
				return action;
			}
		}

		return null;
	}
}
