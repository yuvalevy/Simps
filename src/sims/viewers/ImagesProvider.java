package sims.viewers;

import javax.swing.ImageIcon;

import sims.basics.configurations.ConfigurationManager;

public class ImagesProvider {

	private static final int toysLimit = ConfigurationManager.getToysLimit();
	private static final int playersLimit = ConfigurationManager.getPlayersLimit();
	private static final int roomsLimit = ConfigurationManager.getRoomsLimit();
	private static final String pathPrefix = ConfigurationManager.getPathPrefix();

	public static ImageIcon getDoorDefaultImage(int objectId) {
		// TODO change?
		return null;
	}

	public static ImageIcon getPlayerDefaultImage(int playerId) {

		String playerImgPath = ConfigurationManager.getPlayerImgsPath();

		if (playerId > playersLimit) {
			playerId %= playersLimit;
		}

		playerImgPath = playerImgPath.replace(pathPrefix, "" + playerId);

		// TODO: per player, we need more pics
		ImageIcon image = new ImageIcon(playerImgPath);
		return image;

	}

	public static ImageIcon getPlayerSearchImages() {

		String searchImgPath = ConfigurationManager.getPlayerSearchImgsPath();

		ImageIcon image = new ImageIcon(searchImgPath);

		return image;
	}

	public static ImageIcon getRoomImage(int roomIndex) {
		if (roomIndex > roomsLimit) {
			roomIndex %= roomsLimit;
		}

		ImageIcon newRoom = new ImageIcon("images/rooms/room" + roomIndex + ".jpg");
		return newRoom;
	}

	public static ImageIcon getToyDefaultImage(int toyId) {

		String toyImgPath = ConfigurationManager.getToyImgsPath();

		if (toyId > toysLimit) {
			toyId %= toysLimit;
		}

		toyImgPath = toyImgPath.replace(pathPrefix, "" + toyId);

		ImageIcon image = new ImageIcon(toyImgPath);

		return image;
	}

}
