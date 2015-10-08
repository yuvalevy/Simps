package sims.basics.config;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;

import sims.module.surface.GameLocation;

public class ConfigurationManager {

	public static String getCalculatorName() {

		return Configuration.getGeneralStringValue("simps.calculatorname"); //$NON-NLS-1$

	}

	public static Rectangle getCellDefaultSize() {

		int x = Configuration.getGeneralIntValue("simps.cellxdefaultsize");
		int y = Configuration.getGeneralIntValue("simps.cellydefaultsize");

		return new Rectangle(x, y);
	}

	public static GameLocation getDoorLocation(int roomId, int doorIndex) {

		GameLocation $ = new GameLocation(roomId);

		int x = Configuration.getRoomIntValue("simps.Room" + roomId + ".Door" + doorIndex + ".doorxlocation"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		int y = Configuration.getRoomIntValue("simps.Room" + roomId + ".Door" + doorIndex + ".doorylocation"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

		Point location = new Point(x, y);

		if (doorIndex == 0) {
			$.setLocation(location);
		}

		return $;
	}

	/**
	 *
	 * @param doorIndex
	 *            0 means upper door, 1 means lower door
	 * @param roomId
	 * @return
	 */
	public static GameLocation getDoorNextRoomStartingLocation(int roomId, int doorIndex) {

		GameLocation $ = null;

		int x = Configuration.getRoomIntValue("simps.Room" + roomId + ".Door" + doorIndex + ".nextdoorxlocation");
		int y = Configuration.getRoomIntValue("simps.Room" + roomId + ".Door" + doorIndex + ".nextdoorylocation");

		Point location = new Point(x, y);

		if (doorIndex == 0) {
			$ = new GameLocation(location, roomId - 1);
		} else {
			$ = new GameLocation(location, roomId + 1);
		}

		return $;
	}

	/**
	 * Depends on how much images in the folder
	 *
	 * @return
	 */
	public static int getPlayersLimit() {
		return Configuration.getGeneralIntValue("simps.playerslimit");
	}

	public static Polygon[] getRoomDoorsPolygons(int roomId, int doorCount) {

		Polygon[] $ = new Polygon[doorCount];

		for (int i = 0; i < doorCount; i++) {

			$[i] = ConfigurationManager.makePolygon(new String[] {
					Configuration.getRoomStringValue("simps.Room" + roomId + ".Door" + i + ".xdoorpoints"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					Configuration.getRoomStringValue("simps.Room" + roomId + ".Door" + i + ".ydoorpoints") }); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

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

		String[] $ = new String[2];

		$[0] = Configuration.getRoomStringValue("simps.Room" + roomId + ".xroompoints"); //$NON-NLS-1$ //$NON-NLS-2$
		$[1] = Configuration.getRoomStringValue("simps.Room" + roomId + ".yroompoints"); //$NON-NLS-1$ //$NON-NLS-2$

		Polygon area = new Polygon();

		// if (!$[0].startsWith("!") && !$[1].startsWith("!"))

		area = ConfigurationManager.makePolygon($);

		return area;
	}

	/**
	 * Depends on how much images in the folder
	 *
	 * @return
	 */
	public static int getRoomsLimit() {

		return Configuration.getGeneralIntValue("simps.roomslimit");
	}

	public static int getSleepTime() {
		return Configuration.getGeneralIntValue("simps.sleeptime");

	}

	public static int getToysDefaultNumberPerRoom() {

		return Configuration.getGeneralIntValue("simps.toysdefaultnumberperroom");
	}

	/**
	 * Depends on how much images in the folder
	 *
	 * @return
	 */
	public static int getToysLimit() {
		return Configuration.getGeneralIntValue("simps.toyslimit");

	}

	/**
	 * @param points
	 */
	public static Polygon makePolygon(String[] points) {

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
