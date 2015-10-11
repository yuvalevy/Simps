package sims.basics.config;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

import sims.module.surface.GameLocation;

public class ConfigurationManager {

	public static int getBoredCapability() {
		return Configuration.getInt("simps.feelings.boredcapability");

	}

	public static String getCalculatorName() {

		return Configuration.getString("simps.general.calculatorname"); //$NON-NLS-1$

	}

	public static Rectangle getCellDefaultSize() {

		int x = Configuration.getInt("simps.general.cellxdefaultsize");
		int y = Configuration.getInt("simps.general.cellydefaultsize");

		return new Rectangle(x, y);
	}

	public static GameLocation getDoorLocation(int roomId, int doorIndex) {

		GameLocation $ = new GameLocation(roomId);

		int x = Configuration.getInt("simps.rooms.Room" + roomId + ".Door" + doorIndex + ".doorxlocation"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		int y = Configuration.getInt("simps.rooms.Room" + roomId + ".Door" + doorIndex + ".doorylocation"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

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

		int x = Configuration.getInt("simps.rooms.Room" + roomId + ".Door" + doorIndex + ".nextdoorxlocation");
		int y = Configuration.getInt("simps.rooms.Room" + roomId + ".Door" + doorIndex + ".nextdoorylocation");

		int nextRoomIndex = Configuration.getInt("simps.rooms.Room" + roomId + ".Door" + doorIndex + ".nextroomindex");

		return new GameLocation(x, y, nextRoomIndex);
	}

	public static int getFeelingsIncreacingAmount() {
		return Configuration.getInt("simps.feelings.feelingsincreacingamount");
	}

	public static int getHave2PeeCapability() {
		return Configuration.getInt("simps.feelings.have2peecapability");
	}

	public static int getHungryCapability() {
		return Configuration.getInt("simps.feelings.hungrycapability");
	}

	public static String getPathPrefix() {
		return Configuration.getString("simps.general.pathprefix");
	}

	public static String getPlayerImgsPath() {

		return Configuration.getString("simps.players.playerimgpath");

	}

	public static String getPlayerSearchImgsPath() {
		return Configuration.getString("simps.players.playersearchimgspath");
	}

	public static int getPlayerSearchTime() {

		return Configuration.getInt("simps.general.playersearchtime");

	}

	public static Rectangle getPlayerSize() {

		String $ = getPlayerImgsPath();

		ImageIcon icon = new ImageIcon($);
		int h = icon.getIconHeight(), w = icon.getIconWidth();

		return new Rectangle(h, w);
	}

	/**
	 * Depends on how much images in the folder
	 *
	 * @return
	 */
	public static int getPlayersLimit() {
		return Configuration.getInt("simps.general.playerslimit");
	}

	public static Point getPlayerStartingPoint() {

		int x = Configuration.getInt("simps.players.playerstartingxpoint");
		int y = Configuration.getInt("simps.players.playerstartingypoint");

		return new Point(x, y);
	}

	public static Polygon[] getRoomDoorsPolygons(int roomId, int doorCount) {

		Polygon[] $ = new Polygon[doorCount];

		for (int i = 0; i < doorCount; i++) {

			$[i] = ConfigurationManager.makePolygon(
					new String[] { Configuration.getString("simps.rooms.Room" + roomId + ".Door" + i + ".xdoorpoints"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
							Configuration.getString("simps.rooms.Room" + roomId + ".Door" + i + ".ydoorpoints") }); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

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

		$[0] = Configuration.getString("simps.rooms.Room" + roomId + ".xroompoints"); //$NON-NLS-1$ //$NON-NLS-2$
		$[1] = Configuration.getString("simps.rooms.Room" + roomId + ".yroompoints"); //$NON-NLS-1$ //$NON-NLS-2$

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

		return Configuration.getInt("simps.general.roomslimit");
	}

	public static int getSleepTime() {
		return Configuration.getInt("simps.general.sleeptime");

	}

	public static int getTiredCapability() {
		return Configuration.getInt("simps.feelings.tiredcapability");
	}

	public static String getToyImgsPath() {
		return Configuration.getString("simps.general.toyimgspath");
	}

	public static int getToysDefaultNumberPerRoom() {

		return Configuration.getInt("simps.general.toysdefaultnumberperroom");
	}

	/**
	 * Depends on how much images in the folder
	 *
	 * @return
	 */
	public static int getToysLimit() {
		return Configuration.getInt("simps.general.toyslimit");
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
