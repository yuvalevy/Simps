import java.util.HashMap;
import java.util.Map;

import objects.Player;
import ui.UIPanel;

public class World {

	private static Map<Integer, Player> players;

	private static UIPanel gamePanel;

	public static boolean addNewPlayer(Player playerName) {

		if (containsPlayer(playerName.getPlayerName())) {
			return false;
		}

		int playerId = gamePanel.addPlayer(playerName.getPlayerName());
		players.put(playerId, playerName);

		return true;
	}

	private static boolean containsPlayer(String playerName) {
		for (Player player : players.values()) {
			if (player.getPlayerName() == playerName) {
				return true;
			}
		}
		return false;
	}

	public static void main(String[] args) {

		players = new HashMap<Integer, Player>();

		gamePanel = new UIPanel();
		gamePanel.createFrame();

		String playerName = "Yuval";
		Player newPlayer = new Player(playerName);
		addNewPlayer(newPlayer);
		gamePanel.setFocusedPlayer(playerName);

		gamePanel.printPlayers();
	}

}
