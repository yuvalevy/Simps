package sims.module.actions;

import javax.swing.ImageIcon;

import sims.basics.config.ConfigurationManager;
import sims.module.objects.Door;
import sims.module.objects.Player;
import sims.module.objects.Toy;

public class ActionsFactory {

	private static final int toysLimit = ConfigurationManager.getToysLimit();
	private static final int playersLimit = ConfigurationManager.getPlayersLimit();
	private static final String pathPrefix = ConfigurationManager.getPathPrefix();

	private static ImageIcon getDoorDefaultPic(int objectId) {
		// TODO change?
		return null;
	}

	/**
	 * Creates new Nothing type
	 *
	 * @param className
	 * @return
	 */
	public static Nothing getNothing(String className) {

		ImageIcon icon = null;
		switch (className) {
		case "Player":
			icon = getPlayerDefaultPic(Player.PLAYERS_COUNT);
			break;

		case "Door":
			icon = getDoorDefaultPic(Door.DOORS_COUNT);
			break;

		case "Toy":
			icon = getToyDefaultPic(Toy.TOYS_COUNT);
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

		String playerImgPath = ConfigurationManager.getPlayerImgsPath();

		if (playerId > playersLimit) {
			playerId %= playersLimit;
		}

		playerImgPath = playerImgPath.replace(pathPrefix, "" + playerId);

		// TODO: per player, we need more pics
		ImageIcon image = new ImageIcon(playerImgPath);
		return image;

	}

	private static ImageIcon getToyDefaultPic(int toyId) {

		String toyImgPath = ConfigurationManager.getToyImgsPath();

		if (toyId > toysLimit) {
			toyId %= toysLimit;
		}

		toyImgPath = toyImgPath.replace(pathPrefix, "" + toyId);

		ImageIcon image = new ImageIcon(toyImgPath);

		return image;
	}

	public static Walk getWalk(int playerId) {

		ImageIcon image = getPlayerDefaultPic(playerId);

		return new Walk(image);
	}

}
