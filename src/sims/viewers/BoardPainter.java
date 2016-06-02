package sims.viewers;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import sims.basics.Log;
import sims.basics.LogLevel;
import sims.module.feelings.Feeling;
import sims.module.main.World;
import sims.module.objects.GameObject;
import sims.module.objects.Player;
import sims.module.surface.Cell;
import sims.module.surface.CellProperty;
import sims.module.surface.Map;

public class BoardPainter {

	private final ArrayList<ImageIcon> roomsImages;

	private final World game;
	private final Map gameMap;

	private int currentRoom;

	public BoardPainter(World game) {

		this.game = game;
		this.gameMap = game.getMap();

		this.roomsImages = new ArrayList<ImageIcon>();

	}

	// TODO I don't think i need room id
	// I can use - roomsImages.size()... need more trust here
	public void addRoom(int roomIndex) {

		ImageIcon newRoom = ImagesProvider.getRoomImage(roomIndex);

		this.roomsImages.add(newRoom);

	}

	public void paintManagementPanel(Component c, Graphics g, JTable table) {

		paintFeelingsTable(table);

		g.setColor(Color.black);
		g.setFont(new Font("Arial", 1, 20));
		g.drawString("Only " + this.game.getUnfoundToys() + " toy left to find!!", 20, 170);

		g.setColor(Color.red);
		g.setFont(new Font("Arial", 4, 20));
		g.drawString(this.gameMap.getFocusedRoom().getUnfoundToys() + " in this room!!:)", 50, 200);

	}

	public void paintRoom(Component c, Graphics g) {

		paintFloor(c, g);
		paintFurnitures(c, g);

		paintGameObject(c, g);

		drawDebug(g);

	}

	public void setFocusedRoom(int roomId) {

		this.currentRoom = roomId;

	}

	private void drawDebug(Graphics g) {

		if (Log.getLogLevel() == LogLevel.DEBUG) {

			/**
			 * draw surface
			 */

			Rectangle recCell = Cell.getCellSize();

			// Color[] colors = new Color[] { Color.BLUE, Color.YELLOW,
			// Color.MAGENTA, Color.ORANGE, Color.CYAN };
			// int index = 0, colorIndex = 0;

			for (Cell[] cellsRow : this.gameMap.getFocusedRoom().getCells()) {
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

					Point coor = cell.getCoordinate().getLocation();

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
					// g.drawString("" + cell.getNeighbors().size(), coor.x +
					// 10,
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
	}

	/**
	 * @param gameObject
	 * @return
	 */
	private boolean isCurrentRoom(GameObject gameObject) {
		return gameObject.getCurrentLocation().getRoomId() == this.currentRoom;
	}

	private void paintByPoint(ImageIcon ic, Point p, Component c, Graphics g) {

		ic.paintIcon(c, g, p.x, p.y);
	}

	private void paintFeelingsTable(JTable table) {

		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();

		ArrayList<Player> players = this.game.getPlayers();

		tableModel.setRowCount(players.size());

		for (int i = 0; i < players.size(); i++) {

			Player player = players.get(i);

			tableModel.setValueAt(player.getPlayerName(), i, 0);

			Feeling[] feelings = player.getFeelings();

			for (int j = 0; j < feelings.length; j++) {

				int clmn = j + 1;

				String clmnProp = "c" + clmn;
				String rowProp = "r" + i;

				if (feelings[j].isDangerous()) {

					table.putClientProperty(clmnProp, clmn);
					table.putClientProperty(rowProp, i);

				} else {
					table.putClientProperty(clmnProp, null);
					table.putClientProperty(rowProp, null);
				}

				tableModel.setValueAt(feelings[j], i, clmn);
			}

		}
	}

	private void paintFloor(Component c, Graphics g) {

		ImageIcon floor = this.roomsImages.get(this.currentRoom - 1);

		paintByPoint(floor, new Point(0, 0), c, g);

	}

	private void paintFurnitures(Component c, Graphics g) {
	}

	private void paintGameObject(Component c, Graphics g) {

		for (GameObject gameObject : this.game.getGameObjects()) {

			if (isCurrentRoom(gameObject)) {

				Point playerPoint = gameObject.getCurrentLocation().getLocation();
				ImageIcon playerIcon = gameObject.getNextImage();

				if (playerIcon != null) {
					paintByPoint(playerIcon, playerPoint, c, g);
				}
			}

		}

	}

}
