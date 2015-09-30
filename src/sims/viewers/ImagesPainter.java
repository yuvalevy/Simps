package sims.viewers;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

import sims.basics.Log;
import sims.module.objects.Player;

public class ImagesPainter {

	private final Map<String, ImageIcon> playersImages;
	private final ArrayList<ImageIcon> roomsImages;

	private int startingPaintWidth;

	public ImagesPainter() {
		this.playersImages = new HashMap<String, ImageIcon>();
		this.roomsImages = new ArrayList<ImageIcon>();
	}

	public void addPlayer(String playerName) {

		int pIndex = this.playersImages.size();
		ImageIcon newPlayer = new ImageIcon("images/players/" + pIndex + ".gif");

		this.playersImages.put(playerName, newPlayer);
	}

	private void paintByPoint(ImageIcon ic, Point p, Component c, Graphics g) {

		ic.paintIcon(c, g, p.x - this.startingPaintWidth, p.y);
	}

	private void paintFloor(Component c, Graphics g, int roomId) {

		if (this.roomsImages.size() == 0) {
			// return;
		}

		@SuppressWarnings("unused")
		ImageIcon floor; // = this.roomsImages.get(roomId);

		// TODO: To be deleted - delete @SuppressWarnings("unused")
		floor = new ImageIcon("images/room1.jpg");

		// paintByPoint(floor, new Point(0, 0), c, g);
	}

	private void paintFurnitures(Component c, Graphics g, int roomId) {
	}

	private void paintPlayers(Component c, Graphics g, int roomId, ArrayList<Player> gamePlayers) {

		if (this.playersImages.size() == 0) {
			return;
		}

		for (int i = 0; i < this.playersImages.size(); i++) {

			Player currentPlayer = gamePlayers.get(i);
			Point playerPoint = currentPlayer.getNextStep().getLocation();
			ImageIcon playerIcon = this.playersImages.get(currentPlayer.getPlayerName());

			paintByPoint(playerIcon, playerPoint, c, g);
			Log.WriteLog("Painted player " + currentPlayer.getPlayerName());
		}
	}

	public void paintRoom(Component c, Graphics g, int roomId, ArrayList<Player> gamePlayers) {

		paintFloor(c, g, roomId);
		paintFurnitures(c, g, roomId);
		paintPlayers(c, g, roomId, gamePlayers);

	}

	public void removePlayer(String playerName) {

		this.playersImages.remove(playerName);
	}

	public void setStartingPaintWidth(int width) {

		this.startingPaintWidth = width;
	}
}
