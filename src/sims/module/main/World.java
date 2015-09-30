package sims.module.main;

import java.awt.Point;
import java.util.ArrayList;

import sims.basics.GameActions;
import sims.basics.Log;
import sims.basics.LogLevel;
import sims.module.objects.Player;
import sims.module.surface.GameLocation;
import sims.module.surface.Map;

public class World implements GameActions {

	private boolean isRunning = false;

	private final ArrayList<Player> players;

	private final Map worldMap;

	private Player focusedPlayer;

	public World(int gameWidth, int gameHight) {

		this.players = new ArrayList<Player>();
		setRunning(true);

		// TODO: proportion for game width to cell count
		this.worldMap = new Map(gameWidth, gameHight);

		Log.WriteLog("Created World instance");
	}

	@Override
	public boolean addPlayer(String playerName) {

		if (getPlayer(playerName) != null) {
			return false;
		}

		this.players.add(new Player(playerName));
		return true;
	}

	/**
	 * Returns the player in the specific point. If there are no player found,
	 * returns null.
	 *
	 * @param p
	 *            Point to search player
	 * @return Player at the point or null
	 */
	public String getPlayerByPoint(Point p) {
		for (Player player : this.players) {
			if (player.isOnObject(p)) {
				return player.getPlayerName();
			}
		}
		return null;
	}

	public int getCurrentRoom() {
		return this.worldMap.getFocusedRoom().getRoomId();
	}

	public Player getFocusedPlayer() {
		return this.focusedPlayer;
	}

	/**
	 * Returns the player object by his name
	 *
	 * @param playerName
	 * @return the player object
	 */
	public Player getPlayer(String playerName) {

		for (Player player : this.players) {
			if (player.getPlayerName().equals(playerName)) {
				return player;
			}
		}
		return null;
	}

	public ArrayList<Player> getPlayers() {
		return this.players;
	}

	public boolean isRunning() {
		return this.isRunning;
	}

	@Override
	public void movePlayer(GameLocation newLocation) {

		if (this.focusedPlayer == null) {
			return;
		}

		WalkingCalculator.PlanTrip(this.focusedPlayer, newLocation);

	}

	@Override
	public void pauseGame() {
		// TODO Auto-generated method stub

	}

	@Override
	public void removePlayer(String playerName) {

		Player tempPlayer = getPlayer(playerName);
		if (tempPlayer != null) {

			this.players.remove(tempPlayer);
		}
	}

	private void setFocusedPlayer(Player focusedPlayer) {
		this.focusedPlayer = focusedPlayer;
	}

	@Override
	public void setFocusedPlayer(String playerName) {

		Player currentPlayer = getPlayer(playerName);
		if (currentPlayer == null) {
			// TODO: Handle error here
			Log.WriteLog("Could not find player " + playerName, LogLevel.Error);
		}

		setFocusedPlayer(currentPlayer);

		Log.WriteLog("Focused player is " + playerName, LogLevel.Debug);

	}

	private void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

}
