package sims.module.main;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import sims.basics.GameActions;
import sims.basics.Log;
import sims.basics.LogLevel;
import sims.module.objects.Player;
import sims.module.objects.Room;
import sims.module.objects.Toy;
import sims.module.surface.GameLocation;
import sims.module.surface.Map;

public class World implements GameActions {

	private boolean isRunning = false;

	private final ArrayList<Player> players;

	private final WalkingCalculator walkingCalculator;

	private final Map worldMap;

	private Player focusedPlayer;

	public World(Dimension screenDimension, Rectangle cellDefaultSize) {

		this.players = new ArrayList<Player>();

		// TODO: proportion for game width to cell count
		this.worldMap = new Map(screenDimension, cellDefaultSize);

		this.walkingCalculator = new WalkingCalculator(this.worldMap);

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
	public void addRoom(int roomId) {

		int toysCount = ConfigurationManager.getToysDefaultNumberPerRoom();
		this.worldMap.addRoom(roomId, toysCount);

	}

	/**
	 * Determines whether it is time to change rooms. Basically, if the focused
	 * player needs to move to another room, the room should be changed.
	 *
	 * @return The room this game should switch to, of -1 if romm is not
	 *         changing
	 */
	public int changingRoom() {

		if (this.focusedPlayer != null) {

			int playerCurrentRoom = this.focusedPlayer.getCurrentLocation().getRoomId();

			if (playerCurrentRoom != this.worldMap.getFocusedRoom().getRoomId()) {

				return playerCurrentRoom;
			}
		}

		return -1;
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

	public ArrayList<Toy> getFoundToys() {

		return this.worldMap.getFoundToys();

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

	private ArrayList<Toy> getToys() {

		return this.worldMap.getToys();
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

		this.walkingCalculator.planTrip(this.focusedPlayer, newLocation);

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
	public void setFocusedPlayer(String playerName) {

		Player currentPlayer = getPlayer(playerName);
		if (currentPlayer == null) {
			// TODO: Handle error here
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

	public void tick() {

		// Log.WriteLog("Start module tick");

		for (Player player : this.players) {

			player.tick();

		}

		// Log.WriteLog("End module tick");
	}
}
