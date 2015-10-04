package sims.module.main;

import java.awt.Point;
import java.awt.Polygon;

import sims.module.surface.GameLocation;

public class ConfigurationManager {

	private static String[] ROOM_2_POINTS = new String[] {
			"326#360#360#500#500#655#655#857#857#746#746#855#855#800#884#658#630#300#190#154#154#60#270",
			"128#128#565#565#276#276#128#128#280#280#737#737#826#826#931#931#826#826#673#673#128#20#20" };

	private static String[] ROOM_2_DOOR1 = new String[] { "60#270#326#154", "20#20#128#128" };

	/**
	 *
	 * @param doorIndex
	 *            - 0 means upper door, 1 means lower door
	 * @param roomId
	 * @return
	 */
	public static GameLocation getDoorNextRoomStartingLocation(int roomId, int doorIndex) {

		GameLocation $ = new GameLocation(roomId);

		switch (roomId) {
		case 1:
			$ = new GameLocation(new Point(100, 100), roomId + 1);
			break;
		case 2:
			if (doorIndex == 0) {
				$ = new GameLocation(new Point(100, 100), roomId - 1);
			} else {
				$ = new GameLocation(new Point(100, 100), roomId + 1);
			}
			break;
		}

		return $;
	}

	public static Polygon[] getRoomDoorsPolygons(int roomId, int doorCount) {

		Polygon[] $ = new Polygon[doorCount];

		String[] pdoor1 = null;// , pdoor2 = null;

		switch (roomId) {
		case 1:
			$[0] = new Polygon();
			break;
		case 2:

			pdoor1 = ROOM_2_DOOR1;
			break;

		}

		if (pdoor1 != null) {

			$[0] = makePolygon(pdoor1);

			// if (pdoor2 != null) {
			//
			// doors[1] = makePolygon(pdoor2);
			//
			// }
		}

		return $;
	}

	/**
	 * gets polygons which player CAN step on
	 *
	 * @param roomId
	 * @return
	 */
	public static Polygon getRoomPolygonStepable(int roomId) {

		String[] $ = null;

		switch (roomId) {
		case 2:

			$ = ROOM_2_POINTS;
			break;

		}

		Polygon area = new Polygon();

		if ($ != null) {

			area = makePolygon($);
		}

		return area;
	}

	/**
	 * @param points
	 */
	private static Polygon makePolygon(String[] points) {

		Polygon $ = new Polygon();

		for (int i = 0; i < ((points.length / 2) + 1); i += 2) {

			String[] xs = points[i].split("#");
			String[] ys = points[i + 1].split("#");

			for (int j = 0; j < (xs.length); j++) {

				int x = Integer.parseInt(xs[j]);
				int y = Integer.parseInt(ys[j]);

				$.addPoint(x, y);
			}
		}

		return $;
	}
}