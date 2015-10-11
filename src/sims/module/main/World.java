package sims.module.main;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import sims.basics.GameActions;
import sims.basics.Log;
import sims.basics.LogLevel;
import sims.module.actions.ActionIdentifier;
import sims.module.objects.GameObject;
import sims.module.objects.Player;
import sims.module.surface.GameLocation;
import sims.module.surface.Map;
import sims.module.surface.Room;

public class World implements GameActions {

	private int unfoundToys;
	private boolean isRunning = false;
	private Player focusedPlayer;

	private final ArrayList<Player> players;
	private final WalkingCalculator walkingCalculator;
	private final Map worldMap;

	public World(Dimension screenDimension, Rectangle cellDefaultSize) {

		this.players = new ArrayList<Player>();

		this.worldMap = new Map(screenDimension, cellDefaultSize);

		this.walkingCalculator = new WalkingCalculator(this.worldMap);

		this.unfoundToys = 0;

		Log.WriteLineLog("Created World instance");
	}

	@Override
	public boolean addPlayer(String playerName) {

		if (getPlayer(playerName) != null) {
			return false;
		}

		this.players.add(new Player(playerName, getCurrentRoom()));

		return true;
	}

	@Override
	public void addRoom(int roomId, int toysCount) {
		this.unfoundToys += toysCount;
		this.worldMap.addRoom(roomId, toysCount);

	}

	public void createDefalutMap() {

		this.worldMap.createDefalutMap();

	}

	public int getCurrentRoom() {

		if (this.worldMap.getFocusedRoom() == null) {
			return 1;
		}
		return this.worldMap.getFocusedRoom().getRoomId();

	}

	public Player getFocusedPlayer() {
		return this.focusedPlayer;
	}

	public ArrayList<GameObject> getGameObjects() {
		ArrayList<GameObject> $ = new ArrayList<>();

		$.addAll(this.players);
		$.addAll(this.worldMap.getToys());

		return $;
	}

	public Map getMap() {
		return this.worldMap;
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
			if (player.contains(p)) {
				return player.getPlayerName();
			}
		}
		return null;
	}

	public ArrayList<Player> getPlayers() {
		return this.players;
	}

	public void getRoom(int roomId) {

		this.worldMap.getRoom(roomId);

	}

	public int getRoomCount() {

		return this.worldMap.getRoomCount();

	}

	public ArrayList<Room> getRooms() {

		return this.worldMap.getRooms();

	}

	public int getUnfoundToys() {
		return this.unfoundToys;
	}

	public boolean isRunning() {
		return this.isRunning;
	}

	@Override
	public void movePlayer(GameLocation newLocation) {

		if (this.focusedPlayer == null) {
			Log.WriteLineLog("Cannot move - No player in focuse");
			return;
		}

		if (!this.focusedPlayer.trySetAction(ActionIdentifier.Walk)) {
			Log.WriteLineLog("Cant start walking, other action is not over.");
		} else {
			this.walkingCalculator.planTrip(this.focusedPlayer, newLocation);
		}
	}

	/**
	 * Determines whether it is time to change rooms. Basically, if the focused
	 * player needs to move to another room, the room should be changed.
	 *
	 * @return The room this game should switch to, of -1 if romm is not
	 *         changing
	 */
	public int needRoomChaning() {

		if (this.focusedPlayer != null) {

			int playerCurrentRoom = this.focusedPlayer.getCurrentLocation().getRoomId();

			if (playerCurrentRoom != this.worldMap.getFocusedRoom().getRoomId()) {

				return playerCurrentRoom;
			}
		}

		return -1;
	}

	@Override
	public void pauseGame() {

		this.isRunning = false;

	}

	@Override
	public void removePlayer(String playerName) {

		Player tempPlayer = getPlayer(playerName);
		if (tempPlayer != null) {

			this.players.remove(tempPlayer);
		}
	}

	@Override
	public void sendPlayerSearching(GameLocation newLocation) {

		if (this.focusedPlayer == null) {
			return;
		}

		movePlayer(newLocation);

		if (this.focusedPlayer.trySetAction(ActionIdentifier.Search)) {

			if (this.worldMap.tryFindToy(newLocation)) {
				Log.WriteLineLog("FOUND-------------");
				this.unfoundToys--;
			}
		}

	}

	@Override
	public void setFocusedPlayer(String playerName) {

		Player currentPlayer = getPlayer(playerName);
		if (currentPlayer == null) {
			Log.WriteLineLog("Could not find player " + playerName, LogLevel.Error);
		}

		this.focusedPlayer = currentPlayer;

		Log.WriteLineLog("Focused player is " + playerName, LogLevel.Debug);

	}

	@Override
	public void setFocusedRoom(int roomId) {

		this.worldMap.setFocusedRoom(roomId);

		Log.WriteLineLog("Focused room is " + roomId);
	}

	@Override
	public void startGame() {
		this.isRunning = true;
	}

	@Override
	public void stopGame(boolean isWinner) {

		this.isRunning = false;

	}

	@Override
	public void tick() {

		for (Player player : this.players) {

			player.tick();

		}
	}

}
