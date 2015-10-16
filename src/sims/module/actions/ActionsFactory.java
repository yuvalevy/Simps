package sims.module.actions;

import javax.swing.ImageIcon;

import sims.basics.configurations.ConfigurationManager;
import sims.module.feelings.Feeling;
import sims.module.objects.Door;
import sims.module.objects.Player;
import sims.module.objects.Toy;
import sims.module.surface.GameLocation;
import sims.viewers.ImagesProvider;

public class ActionsFactory {

	public static void getFun(Walk walker, Feeling feeling) {

		ImageIcon image = ImagesProvider.getPlayerFunImages();

		GameLocation feelingLocation = ConfigurationManager.getFunLocation();

		// return new Fun(feelingLocation, walker, feeling, image);
	}

	public static Hide getHide() {

		return new Hide();
	}

	public static Nothing getNothing(String className) {

		ImageIcon icon = null;
		switch (className) {
		case "Player":
			icon = ImagesProvider.getPlayerDefaultImage(Player.PLAYERS_COUNT);
			break;

		case "Door":
			icon = ImagesProvider.getDoorDefaultImage(Door.DOORS_COUNT);
			break;

		case "Toy":
			icon = ImagesProvider.getToyDefaultImage(Toy.TOYS_COUNT);
			break;

		default:
			break;
		}
		return new Nothing(icon);

	}

	public static Search getSearch(Action preAction) {

		ImageIcon image = ImagesProvider.getPlayerSearchImages();

		return new Search(preAction, image);
	}

	public static Walk getWalk(int playerId) {

		ImageIcon[] image = ImagesProvider.getPlayerWalkImages(playerId);

		return new Walk(image);
	}

	public static WatchTV getWatchTV(Walk walker, Feeling feeling) {

		ImageIcon image = ImagesProvider.getPlayerTVImages();

		GameLocation feelingLocation = ConfigurationManager.getTVLocation();

		return new WatchTV(feelingLocation, walker, feeling, image);
	}

}
