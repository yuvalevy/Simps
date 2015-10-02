package sims.viewers;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

import sims.module.objects.Player;

public class ImagesPainter {

	private final Map<String, ImageIcon> playersImages;
	private final ArrayList<ImageIcon> roomsImages;

	private int startingPaintWidth;
	private final ArrayList<Player> gamePlayers;

	private int currentRoom;

	public ImagesPainter(final ArrayList<Player> gamePlayers) {

		this.playersImages = new HashMap<String, ImageIcon>();
		this.roomsImages = new ArrayList<ImageIcon>();

		this.gamePlayers = gamePlayers;
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

			}

		}

	}

	public void paintRoom(Component c, Graphics g) {

		// String temp =
		// "515,487@530,474@545,461@560,448@575,435@590,422@605,409@620,396@635,383@650,370@665,357@680,344@695,331@710,318@725,305";
		//
		// for (String point : temp.split("@")) {
		// String[] splt = point.split(",");
		//
		// int x = Integer.parseInt(splt[0]) - this.startingPaintWidth, y =
		// Integer.parseInt(splt[1]);
		//
		// g.drawString(splt[0] + "X" + splt[1], x, y);
		//
		// }
		//
		// g.drawString("--500X500--", 500 - this.startingPaintWidth, 500);
		// g.drawString("--764X453--", 764 - this.startingPaintWidth, 453);
		// g.drawLine(500, 500, 764, 453);

		paintFloor(c, g);
		paintFurnitures(c, g);
		paintPlayers(c, g);

	}

	public void removePlayer(String playerName) {

		this.playersImages.remove(playerName);
	}

	public void setFocusedRoom(int roomId) {

		this.currentRoom = roomId;

	}

	public void setStartingPaintWidth(int width) {

		this.startingPaintWidth = width;
	}

}
