package sims.basics;

import java.util.ArrayList;
import java.util.Random;

import sims.module.objects.Toy;
import sims.module.surface.Cell;
import sims.module.surface.CellProperty;
import sims.module.surface.GameLocation;
import sims.module.surface.Room;

public class Randomaizer {

	private static ArrayList<Integer> players = new ArrayList<Integer>();
	private static ArrayList<GameLocation> locations = new ArrayList<GameLocation>();

	private static final Random rand = new Random();

	public static int getPlayerNumber() {

		if (players.size() == 7) {
			return -1;
		}
		int num = 0;

		do {
			num = rand.nextInt(7);
		} while (players.contains(num));

		players.add(num);
		return num;
	}

	public static GameLocation getRandomPlace(Room room, CellProperty property) {

		Cell[][] cells = room.getCells();
		int w = cells.length, h = cells[0].length;

		int randI = rand.nextInt(w);
		int randJ = rand.nextInt(h);

		Cell chosen = null;
		GameLocation $ = null;

		do {

			chosen = cells[randI][randJ];

			randI = rand.nextInt(w);
			randJ = rand.nextInt(h);

			$ = chosen.getCoordinate();

		} while (!room.isFullyOnPropery(Toy.getObjectShape($.getLocation()), property) && locations.contains($));

		return $;
	}

}
