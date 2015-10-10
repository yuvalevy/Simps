package sims.basics;

import java.awt.Point;

import sims.module.surface.GameLocation;

public interface GameActions {

	public boolean addPlayer(String playerName);

	public void addRoom(int roomId, int toyCount);

	public void movePlayer(GameLocation newLocation);

	public void pauseGame();

	public void removePlayer(String playerName);

	public void sendPlayerSearching(Point pointClicked);

	public void setFocusedPlayer(String playerName);

	public void setFocusedRoom(int roomId);

	public void startGame();

	public void stopGame(boolean isWinner);
}
