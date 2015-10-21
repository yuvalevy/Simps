package sims.designer.ui;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

import sims.basics.Log;
import sims.basics.configurations.ConfigurationManager;

public class RoomsCanvas extends Canvas {

	private static final long serialVersionUID = -895688287708489724L;
	private final Image img;

	public RoomsCanvas() {
		super();
		this.img = new ImageIcon(ConfigurationManager.getPlayerSearchImgPath()).getImage();
	}

	@Override
	public void paint(Graphics g) {
		Log.WriteLineLog("RoomsCanvas.paint");
		// super.paint(g);
		g.drawImage(this.img, 0, 0, null);

	}
}
