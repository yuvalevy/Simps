package sims.module.main;

import java.awt.Point;

import sims.basics.Log;
import sims.module.objects.Player;
import sims.module.surface.GameLocation;

public class WalkingCalculator {

	static int pix = 20;

	private static double dotsDistance(Point end, Point walk) {

		double distance;

		distance = Math.sqrt(((end.x - walk.x) * (end.x - walk.x)) + ((end.y - walk.y) * (end.y - walk.y)));

		return distance;

	}

	public static Point getStraightLineTrip(Point start, Point end) {

		double endX = end.getX(), endY = end.getY(), startX = start.getX(), startY = start.getY();

		double dx = Math.abs(endX - startX), dy = Math.abs(endY - startY);

		double totalPathLength = Math.sqrt((dx * dx) + (dy * dy));

		double alfaR = Math.asin(dx / totalPathLength);

		int dX = (int) Math.abs((Math.sin(alfaR) * pix));
		int dY = (int) Math.abs((Math.cos(alfaR) * pix));

		if (!toDown(startY, endY)) {
			dY *= -1;
		}

		if (!toRight(startX, endX)) {
			dX *= -1;
		}

		Point dStep = new Point(dX, dY);

		return dStep;
	}

	public static void PlanTrip(Player player, GameLocation finalLocation) {

		// TODO: dijestra

		GameLocation currentLocation = player.getCurrentLocation();

		if (currentLocation.getRoomId() == finalLocation.getRoomId()) {

			// Same room movement
			Point currentP = currentLocation.getLocation();
			Point destP = finalLocation.getLocation();

			Point newLocation = new Point(currentP);

			Point dp = getStraightLineTrip(currentP, destP);
			double pointDistance = Double.MAX_VALUE;
			double secondPointDistance = 0.0;
			boolean isEndOfLine = false;

			int currentRoom = currentLocation.getRoomId();

			while (!isEndOfLine) {

				int newx = newLocation.x + dp.x;
				int newy = newLocation.y + dp.y;

				newLocation = new Point(newx, newy);

				secondPointDistance = dotsDistance(destP, newLocation);

				if (secondPointDistance < pointDistance) {

					Log.WriteLog("Added step " + newx + "," + newy);

					player.addStep(new GameLocation(newLocation, currentRoom));

					pointDistance = secondPointDistance;

				} else {

					isEndOfLine = true;

				}

			}

		} else {

			// Other room movement

		}

		// player.addStep(finalLocation);

		Log.WriteLog("Added all steps to + " + player.getPlayerName());
	}

	private static boolean toDown(double y, double desty) {

		if (desty > y) {
			return true;
		}

		return false;

	}

	private static boolean toRight(double x, double destx) {

		if (destx > x) {
			return true;
		}

		return false;

	}

}