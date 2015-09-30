package sims.contolers;

import java.util.ArrayList;
import java.util.Random;

public class Randomaizer {

	private static ArrayList<Integer> players = new ArrayList<Integer>();

	public static int getPlayerNumber() {

		if (players.size() == 7) {
			return -1;
		}
		int num = 0;

		do {
			num = new Random().nextInt(7);
		} while (players.contains(num));

		players.add(num);
		return num;
	}
}
