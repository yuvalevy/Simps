package sims.basics;

import sims.module.surface.GameLocation;

public interface GameActions {

	public boolean addPlayer(String playerName);

	public void movePlayer(GameLocation newLocation);

	public void pauseGame();

	public void removePlayer(String playerName);

	public void setFocusedPlayer(String playerName);
}
