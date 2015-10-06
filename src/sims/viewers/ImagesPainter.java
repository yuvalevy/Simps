package sims.viewers;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

import sims.basics.Log;
import sims.basics.LogLevel;
import sims.module.objects.Player;
import sims.module.objects.Room;
import sims.module.surface.Cell;
import sims.module.surface.CellProperty;

public class ImagesPainter {

	private final Map<String, ImageIcon> playersImages;
	private final ArrayList<ImageIcon> roomsImages;

	private int startingPaintWidth;
	private final ArrayList<Player> gamePlayers;
	private final ArrayList<Room> gameRooms;

	private int currentRoom;

	public ImagesPainter(final ArrayList<Player> gamePlayers, final ArrayList<Room> gameRooms) {

		this.playersImages = new HashMap<String, ImageIcon>();
		this.roomsImages = new ArrayList<ImageIcon>();

		this.gamePlayers = gamePlayers;
		this.gameRooms = gameRooms;

	}

	public void addPlayer(String playerName) {

		int playerIndex = this.playersImages.size();
		ImageIcon newPlayer = new ImageIcon("images/players/" + playerIndex + ".gif");

		this.playersImages.put(playerName, newPlayer);
	}

	// TODO I dont think i need room id
	// I can use - roomsImages.size()... need more trust here
	public void addRoom(int roomId) {

		ImageIcon newRoom = new ImageIcon("images/rooms/room" + roomId + ".jpg");

		this.roomsImages.add(newRoom);

	}

	private void drawDebug(Graphics g) {

		/**
		 * draw surface
		 */

		Rectangle recCell = Cell.getCellSize();

		// Color[] colors = new Color[] { Color.BLUE, Color.YELLOW,
		// Color.MAGENTA, Color.ORANGE, Color.CYAN };
		// int index = 0, colorIndex = 0;

		for (Cell[] cellsRow : this.gameRooms.get(this.currentRoom - 1).getCells()) {
			for (Cell cell : cellsRow) {

				if (cell.containsProperty(CellProperty.Stepable)) {

					if (cell.containsProperty(CellProperty.Door)) {
						g.setColor(Color.green);
					} else {
						g.setColor(Color.blue);
					}

				} else {
					g.setColor(Color.red);
				}

				Point coor = cell.getCoordinate();

				g.drawRect(coor.x, coor.y, recCell.width, recCell.height);

				/**
				 * Draw relationships
				 */
				// g.setColor(colors[colorIndex]);
				// index++;
				// colorIndex++;
				// if (colorIndex == colors.length) {
				// colorIndex = 0;
				// }
				// // if ((coor.x == 0) && (coor.y == 0)) {
				// if ((index % 17) == 0) {
				//
				// g.drawString("" + cell.getNeighbors().size(), coor.x + 10,
				// coor.y + 10);
				// for (NeighborRelationship rela : cell.getNeighbors()) {
				//
				// Point relaPoint = rela.getNighborCell().getCoordinate();
				// g.drawLine(coor.x + (recCell.width / 2), coor.y +
				// (recCell.height / 2),
				// relaPoint.x + (recCell.width / 2), relaPoint.y +
				// (recCell.height / 2));
				//
				// }
				// // }
				// }

				/**
				 * end Draw relationships
				 */
			}
		}

		/**
		 * end draw surface
		 */

		/**
		 * draw poly
		 */

		// String[] sober = new String[]/** room2 */
		// {
		// "326#360#360#500#500#655#655#857#857#746#746#855#855#800#884#658#630#300#190#154#154#60#270",
		// "128#128#565#565#276#276#128#128#280#280#737#737#826#826#931#931#826#826#673#673#128#20#20"
		// };
		// for (int i = 0; i < ((sober.length / 2) + 1); i += 2) {
		//
		// String[] xs = sober[i].split("#");
		// String[] ys = sober[i + 1].split("#");
		//
		// Polygon area = new Polygon();
		//
		// for (int j = 0; j < (xs.length); j++) {
		//
		// int x = Integer.parseInt(xs[j]);
		// int y = Integer.parseInt(ys[j]);
		//
		// g.setColor(Color.RED);
		// g.drawString(x + "X" + y, x, y);
		// area.addPoint(x, y);
		// }
		//
		// g.setColor(Color.BLACK);
		// g.drawPolygon(area);
		//
		// }

		/**
		 * end draw poly
		 */

	}

	private void paintByPoint(ImageIcon ic, Point p, Component c, Graphics g) {

		ic.paintIcon(c, g, p.x - this.startingPaintWidth, p.y);
	}

	private void paintFloor(Component c, Graphics g) {

		ImageIcon floor = this.roomsImages.get(this.currentRoom - 1);

		paintByPoint(floor, new Point(this.startingPaintWidth, 0), c, g);

	}

	private void paintFurnitures(Component c, Graphics g) {
	}

	private void paintPlayers(Component c, Graphics g) {

		if (this.playersImages.size() == 0) {
			return;
		}

		for (int i = 0; i < this.playersImages.size(); i++) {

			Player currentPlayer = this.gamePlayers.get(i);

			if (currentPlayer.getCurrentLocation().getRoomId() == this.currentRoom) {

				Point playerPoint = currentPlayer.getCurrentLocation().getLocation();
				ImageIcon playerIcon = this.playersImages.get(currentPlayer.getPlayerName());

				paintByPoint(playerIcon, playerPoint, c, g);
				Rectangle playerRect = currentPlayer.getObjectRectangle();

				g.setColor(Color.pink);
				g.drawRect(playerRect.x, playerRect.y, playerRect.width, playerRect.height);

			}

		}

	}

	public void paintRoom(Component c, Graphics g) {

		paintFloor(c, g);
		paintFurnitures(c, g);
		paintPlayers(c, g);

		if (Log.getLogLevel() == LogLevel.Debug) {
			drawDebug(g);
		}
	}

	public void removePlayer(String playerName) {

		this.playersImages.remove(playerName);
	}

	public void setFocusedRoom(int roomId) {

		this.currentRoom = roomId;

	}

	public void setStartingPaintWidth(int width) {

		this.startingPaintWidth = 0;
		Log.WriteLog("ImagesPainter.setStartingPaintWidth = " + width);
	}

}
