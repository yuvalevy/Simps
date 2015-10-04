package sims.module.main;

import java.awt.Polygon;

public class ConfigurationManager {

	private static String[] ROOM_2_POINTS = new String[] {
			"326#360#360#500#500#655#655#857#857#746#746#855#855#800#884#658#630#300#190#154#154#60#270",
			"128#128#565#565#276#276#128#128#280#280#737#737#826#826#931#931#826#826#673#673#128#20#20" };

	/**
	 * gets polygons which player CAN step on
	 *
	 * @param roomId
	 * @return
	 */

	public static Polygon getRoomPolygonStepable(int roomId) {

		String[] points = null;

		switch (roomId) {
		case 2:

			points = ROOM_2_POINTS;
			break;

		}

		Polygon area = new Polygon();

		if (points != null) {

			for (int i = 0; i < ((points.length / 2) + 1); i += 2) {

				String[] xs = points[i].split("#");
				String[] ys = points[i + 1].split("#");

				for (int j = 0; j < (xs.length); j++) {

					int x = Integer.parseInt(xs[j]);
					int y = Integer.parseInt(ys[j]);

					area.addPoint(x, y);
				}
			}
		}
		return area;
	}

}
