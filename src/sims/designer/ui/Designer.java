package sims.designer.ui;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.border.MatteBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;

public class Designer extends JPanel {

	private static final long serialVersionUID = 5625046021958313278L;

	private final DefaultMutableTreeNode treePlayersNode;
	private final DefaultMutableTreeNode treeRoomsNode;
	private final DefaultMutableTreeNode treeRootNode;

	public Designer() {
		setLayout(new BorderLayout(0, 0));

		JMenuBar menuBar = new JMenuBar();
		add(menuBar, BorderLayout.NORTH);

		JMenu mnMennu = new JMenu("Designer Menu");
		menuBar.add(mnMennu);

		JMenu mnMap = new JMenu("Map");
		mnMap.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setTreeNode(Designer.this.treeRoomsNode);
			}
		});

		mnMennu.add(mnMap);
		// mntmGameObjects.addActionListener(new ActionListener() {
		// @Override
		// public void actionPerformed(ActionEvent arg0) {
		// setTreeNode(Designer.this.treePlayersNode);
		// }
		// });

		JMenu mnRooms = new JMenu("Rooms");
		mnMap.add(mnRooms);

		JMenuItem mntmAddRoom = new JMenuItem("Add Room");
		mnRooms.add(mntmAddRoom);
		mnRooms.addSeparator();

		JMenu mntmAddFurnitures = new JMenu("Add Furnitures");
		mnRooms.add(mntmAddFurnitures);

		JMenuItem mntmAddTable = new JMenuItem("Add Table");
		mntmAddFurnitures.add(mntmAddTable);

		JMenuItem mntmAddSofa = new JMenuItem("Add Sofa");
		mntmAddFurnitures.add(mntmAddSofa);

		JMenuItem mntmAddSpecialFurniture = new JMenuItem("Add Special Furniture");
		mntmAddFurnitures.add(mntmAddSpecialFurniture);

		JMenuItem mntmAddDoor = new JMenuItem("Add Door");
		mnRooms.add(mntmAddDoor);

		JMenuItem mntmChooseTileStyle = new JMenuItem("Choose Tile Style");
		mnRooms.add(mntmChooseTileStyle);

		JMenuItem mntmChangeRoomDimension = new JMenuItem("Change Room Dimension");
		mnRooms.add(mntmChangeRoomDimension);

		JMenuItem mntmGameObjects = new JMenuItem("Game Objects");

		mnMennu.add(mntmGameObjects);

		JTree tree = new JTree();
		tree.setBorder(new MatteBorder(1, 1, 1, 1, new Color(0, 0, 0)));
		tree.setPreferredSize(new Dimension(150, 72));
		tree.setMinimumSize(new Dimension(150, 0));
		tree.setMaximumSize(new Dimension(150, 72));
		tree.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

		this.treeRootNode = new DefaultMutableTreeNode("Game");

		DefaultTreeModel treeModule = new DefaultTreeModel(this.treeRootNode);
		tree.setModel(treeModule);

		this.treePlayersNode = new DefaultMutableTreeNode("Players");
		this.treeRoomsNode = new DefaultMutableTreeNode("Rooms");

		add(tree, BorderLayout.WEST);

		Canvas canvas = new Canvas();
		add(canvas, BorderLayout.CENTER);

		JButton btnSaveCurrentState = new JButton("Save Current State");
		add(btnSaveCurrentState, BorderLayout.SOUTH);
	}

	public void addToPlayersNode(String str) {

		if (str != null) {
			if (str.length() != 0) {

				this.treePlayersNode.add(new DefaultMutableTreeNode(str));
			}
		}

	}

	public void addToRoomsNode(String str) {

		if (str != null) {
			if (str.length() != 0) {

				this.treeRoomsNode.add(new DefaultMutableTreeNode(str));
			}
		}

	}

	public void setTreeNode(MutableTreeNode node) {

		this.treeRootNode.removeAllChildren();

		this.treeRootNode.add(node);

	}
}
