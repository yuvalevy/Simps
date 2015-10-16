package sims.basics;

public interface GameActions {

	public boolean addPlayer(String playerName);

	public void addRoom(int roomId, int toyCount);

	public void changeGameState(GameState state);

	public void removePlayer(String playerName);

	public void setFocusedPlayer(String playerName);

	public void setFocusedRoom(int roomId);

	public void tick();
}
