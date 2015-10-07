package sims.basics;

import javax.swing.ImageIcon;

import actions.Nothing;
import actions.Walk;
import sims.module.main.ConfigurationManager;

public class ActionsFactory {

	private static final int toysLimit = ConfigurationManager.getToysLimit();
	private static final int playersLimit = ConfigurationManager.getPlayersLimit();
	private static final int roomsLimit = ConfigurationManager.getRoomsLimit();

	public static <T> Nothing getNothing(Class<T> type, int objectId) {

		ImageIcon icon = null;
		switch (type.getName()) {
		case "Player":
			icon = getPlayerDefaultPic(objectId);
			break;

		default:
			break;
		}
		return new Nothing(icon);

	}

	/**
	 * @param playerId
	 * @return
	 */
	private static ImageIcon getPlayerDefaultPic(int playerId) {
		if (playerId > playersLimit) {
			playerId %= playersLimit;
		}

		// TODO: per player, we need more pics
		ImageIcon image = new ImageIcon("images/players/" + playerId + ".gif");
		return image;
	}

	public static Walk getWalk(int playerId) {

		ImageIcon image = getPlayerDefaultPic(playerId);

		return new Walk(image);
	}
}
