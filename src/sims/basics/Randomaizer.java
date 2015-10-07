package sims.basics;

import java.util.ArrayList;
import java.util.Random;

import sims.module.surface.Cell;
import sims.module.surface.CellProperty;
import sims.module.surface.GameLocation;

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

	public static GameLocation getRandomPlace(Cell[][] cells, CellProperty property) {

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

		} while (chosen.containsProperty(property) && !locations.contains($));

		return $;
	}
}
