package sims.module.calculators;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import sims.basics.Log;
import sims.module.objects.Player;
import sims.module.surface.CellProperty;
import sims.module.surface.GameLocation;
import sims.module.surface.Map;

public class Line implements Calculator {

	private ArrayList<Point> playerSteps;
	private final Map worldMap;

	final int pix = 20;
	private boolean isActive;

	public Line(Map worldMap) {
		this.worldMap = worldMap;
	}

	@Override
	public boolean canMulti() {
		return true;
	}

	private double dotsDistance(Point end, Point walk) {

		double distance;

		distance = Math.sqrt(((end.x - walk.x) * (end.x - walk.x)) + ((end.y - walk.y) * (end.y - walk.y)));

		return distance;

	}

	@Override
	public void excute(GameLocation start, GameLocation end) {

		this.playerSteps = new ArrayList<>();

		this.isActive = true;

		planStraightLineTrip(start, end);

		this.isActive = false;
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

	@Override
	public void implementSteps(Player p, GameLocation start, GameLocation end) {

		boolean newRoom = false;
		GameLocation last = null;
		for (Point point : this.playerSteps) {

			Rectangle playerRect = p.getObjectRectangle(point);

			CellProperty prop = this.worldMap.getFocusedRoom().getAreaCellType(playerRect);

			if ((prop == CellProperty.Stepable) || (prop == CellProperty.Door)) {

				if (prop == CellProperty.Door) {
					newRoom = true;
				}

				last = new GameLocation(point, this.worldMap.getFocusedRoom().getRoomId());

				p.addStep(last);

				Log.WriteLineLog("Step Added");

			} else {

				Log.WriteLineLog("Step not stepable");

				break;
			}
		}

		if (newRoom) {

			GameLocation roomStartLocation = this.worldMap.getFocusedRoom().getNextRoom(last.getLocation());
			p.addStep(roomStartLocation);

			Log.WriteLineLog("New room it is, new room: " + roomStartLocation.getRoomId());
		}

	}

	@Override
	public boolean isActive() {
		return this.isActive;
	}

	/**
	 * Plans the player's straight line trip
	 *
	 * @param player
	 * @param currentLocation
	 * @param finalLocation
	 */
	private void planStraightLineTrip(GameLocation currentLocation, GameLocation finalLocation) {

		Point currentP = currentLocation.getLocation();
		Point destP = finalLocation.getLocation();

		Point newLocation = new Point(currentP);

		Point dp = getStraightLineTrip(currentP, destP);
		double pointDistance = Double.MAX_VALUE;
		double secondPointDistance = 0.0;

		boolean reasonToStop = false;

		while (!reasonToStop) {

			int newx = newLocation.x + dp.x;
			int newy = newLocation.y + dp.y;

			newLocation = new Point(newx, newy);

			Log.WriteLineLog("NEW step " + newLocation);

			secondPointDistance = dotsDistance(destP, newLocation);

			if ((secondPointDistance < pointDistance)) {

				this.playerSteps.add(newLocation);
				pointDistance = secondPointDistance;

			} else {

				Log.WriteLineLog("End of line");

				reasonToStop = true;

			}

		}
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
