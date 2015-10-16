package sims.viewers;

import java.io.File;
import java.util.ArrayList;

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

	public static ImageIcon getPlayerFunImages() {

		String funImgPath = ConfigurationManager.getPlayerFunImgPath();

		ImageIcon image = new ImageIcon(funImgPath);

		return image;
	}

	public static ImageIcon getPlayerSearchImages() {

		String searchImgPath = ConfigurationManager.getPlayerSearchImgPath();

		ImageIcon image = new ImageIcon(searchImgPath);

		return image;
	}

	public static ImageIcon getPlayerTVImages() {

		String tvImgPath = ConfigurationManager.getPlayerTVImgPath();

		ImageIcon image = new ImageIcon(tvImgPath);

		return image;
	}

	public static ImageIcon[] getPlayerWalkImages(int playerId) {

		String playerWalkImgsPath = ConfigurationManager.getPlayerWalkImgsPath();
		ArrayList<ImageIcon> images = new ArrayList<>();

		for (String filePath : getFiles(playerWalkImgsPath)) {
			images.add(new ImageIcon(filePath));
		}

		return shrinkImages(images);
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

	private static String[] getFiles(String folderPath) {

		File folder = new File(folderPath);
		File[] listOfFiles = folder.listFiles();

		ArrayList<String> files = new ArrayList<>();

		for (File file : listOfFiles) {

			if (file.isFile()) {
				String replace = file.getPath().replace('\\', '/');
				files.add(replace);
			}
		}

		String[] arr = shrinkStrings(files);

		return arr;
	}

	private static ImageIcon[] shrinkImages(ArrayList<ImageIcon> lst) {
		ImageIcon[] arr = new ImageIcon[lst.size()];
		arr = lst.toArray(arr);
		return arr;
	}

	private static String[] shrinkStrings(ArrayList<String> lst) {

		String[] arr = new String[lst.size()];
		arr = lst.toArray(arr);
		return arr;
	}

}
