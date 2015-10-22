package sims.designer.ui;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.tree.DefaultMutableTreeNode;

import sims.basics.Log;
import sims.basics.configurations.ConfigurationManager;

public class RoomsCanvas extends Canvas implements PropertiesComponent {

	private static final long serialVersionUID = -895688287708489724L;
	private final Image img;
	private final DefaultMutableTreeNode treeRoomsNode;

	public RoomsCanvas() {
		super();

		this.treeRoomsNode = new DefaultMutableTreeNode("Rooms");
		this.img = new ImageIcon(ConfigurationManager.getPlayerSearchImgPath()).getImage();
	}

	public void addToRoomsNode(String str) {

		if (str != null) {
			if (str.length() != 0) {

				this.treeRoomsNode.add(new DefaultMutableTreeNode(str));
			}
		}

	}

	@Override
	public DefaultMutableTreeNode getTreeNodes() {

		return this.treeRoomsNode;
	}

	@Override
	public void paint(Graphics g) {

		Log.WriteLineLog("RoomsCanvas.paint");
		g.drawImage(this.img, 0, 0, null);

	}

	public void saveChanges() {
		// TODO Auto-generated method stub

	}
}
