package sims.module.main;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import sims.basics.Log;
import sims.module.objects.Player;
import sims.module.objects.Room;
import sims.module.surface.CellType;
import sims.module.surface.GameLocation;

class WalkingCalculator {

	final int pix = 20;

	// אנכי אופקי אלכסון
	private final int verticalMove, horizontalMove, diagonalMove;

	private final ArrayList<Room> gameRooms;

	public WalkingCalculator(Rectangle cellDefaultSize, ArrayList<Room> gameRooms) {
		this.verticalMove = cellDefaultSize.height;
		this.horizontalMove = cellDefaultSize.width;

		this.diagonalMove = (int) Math
				.sqrt((this.verticalMove * this.verticalMove) + (this.horizontalMove * this.horizontalMove));

		this.gameRooms = gameRooms;
	}

	private double dotsDistance(Point end, Point walk) {

		double distance;

		distance = Math.sqrt(((end.x - walk.x) * (end.x - walk.x)) + ((end.y - walk.y) * (end.y - walk.y)));

		return distance;

	}

	private Point getStraightLineTrip(Point start, Point end) {

		double endX = end.getX(), endY = end.getY(), startX = start.getX(), startY = start.getY();

		double dx = Math.abs(endX - startX), dy = Math.abs(endY - startY);

		double totalPathLength = Math.sqrt((dx * dx) + (dy * dy));

		double alfaR = Math.asin(dx / totalPathLength);

		int dX = (int) Math.abs((Math.sin(alfaR) * this.pix));
		int dY = (int) Math.abs((Math.cos(alfaR) * this.pix));

		if (!toDown(startY, endY)) {
			dY *= -1;
		}

		if (!toRight(startX, endX)) {
			dX *= -1;
		}

		Point dStep = new Point(dX, dY);

		return dStep;
	}

	/**
	 * Plans the player's straight line trip
	 *
	 * @param player
	 * @param currentLocation
	 * @param finalLocation
	 */
	private void planStraightLineTrip(Player player, GameLocation currentLocation, GameLocation finalLocation) {

		Point currentP = currentLocation.getLocation();
		Point destP = finalLocation.getLocation();

		Point newLocation = new Point(currentP);

		Point dp = getStraightLineTrip(currentP, destP);
		double pointDistance = Double.MAX_VALUE;
		double secondPointDistance = 0.0;

		int currentRoomId = currentLocation.getRoomId();

		Room currentRoom = this.gameRooms.get(currentRoomId - 1);

		boolean reasonToStop = false;
		boolean newRoom = false;
		// TODO: this is stupid
		// also no need for 2 booleans

		while (!reasonToStop) {

			int newx = newLocation.x + dp.x;
			int newy = newLocation.y + dp.y;

			newLocation = new Point(newx, newy);

			Log.WriteLog("NEW step " + newLocation);

			secondPointDistance = dotsDistance(destP, newLocation);

			if ((secondPointDistance < pointDistance)) {

				Rectangle playerRect = player.getObjectRectangle(newLocation);

				CellType type = currentRoom.getAreaCellType(playerRect);
				if (type.isStepable()) {

					if (type.isDoor()) {
						newRoom = true;
					}

					Log.WriteLog("Step Added");

					player.addStep(new GameLocation(newLocation, currentRoomId));

					pointDistance = secondPointDistance;

				} else {

					Log.WriteLog("Step not stepable");

					reasonToStop = true;

				}
			} else {

				Log.WriteLog("End of line");

				reasonToStop = true;

			}

			if (newRoom) {

				GameLocation roomStartLocation = currentRoom.getNextRoom(newLocation);
				player.addStep(roomStartLocation);
				reasonToStop = true;

				Log.WriteLog("New room it is, new room: " + roomStartLocation.getRoomId());
			}
		}
	}

	public void planTrip(Player player, GameLocation finalLocation) {

		// TODO: dijestra

		GameLocation currentLocation = player.getCurrentLocation();

		if (currentLocation.getRoomId() == finalLocation.getRoomId()) {

			// Same room movement
			planStraightLineTrip(player, currentLocation, finalLocation);

		} else {

			// Other room movement

		}

		// player.addStep(finalLocation);

		Log.WriteLog("Added all steps to " + player.getPlayerName());
	}

	private boolean toDown(double y, double desty) {

		if (desty > y) {
			return true;
		}

		return false;

	}

	private boolean toRight(double x, double destx) {

		if (destx > x) {
			return true;
		}

		return false;

	}

}