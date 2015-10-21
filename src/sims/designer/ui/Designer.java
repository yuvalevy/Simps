package sims.designer.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Cursor;
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

import sims.basics.Log;

public class Designer extends JPanel {

	private static final long serialVersionUID = 5625046021958313278L;

	private DefaultMutableTreeNode treePlayersNode;
	private DefaultMutableTreeNode treeRoomsNode;
	private DefaultMutableTreeNode treeRootNode;

	private final GeneralSettingsPanel pGeneral;

	private final RoomsCanvas pRooms;

	private Component currentComponent;

	private JTree tree;

	public Designer() {

		setLayout(new BorderLayout(0, 0));

		createMenu();
		createTree();

		JButton btnSaveCurrentState = new JButton("Save Current State");
		add(btnSaveCurrentState, BorderLayout.SOUTH);

		this.pGeneral = new GeneralSettingsPanel();
		this.pRooms = new RoomsCanvas();

		this.pGeneral.setVisible(false);
		this.pRooms.setVisible(false);

		// change2Rooms();
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

	private void change2Rooms() {

		if (!this.pRooms.isVisible()) {
			Log.WriteLineLog("2) pRooms is not visible... :(");

			setTreeNode(this.treeRoomsNode);
			changePanel(this.pRooms);
		}
	}

	private void change2Settings() {

		if (!this.pGeneral.isVisible()) {
			Log.WriteLineLog("2) pGeneral is not visible... :(");

			setTreeNode(null);
			changePanel(this.pGeneral);
		}
	}

	private void changePanel(Component component) {

		Log.WriteLineLog("3) changing to panel " + component.getName());

		Component temp = this.currentComponent;

		if (temp != null) {
			temp.setVisible(false);
			remove(this.currentComponent);

		}

		this.currentComponent = component;
		add(this.currentComponent, BorderLayout.CENTER);
		this.currentComponent.setVisible(true);

		Log.WriteLineLog("pGeneral isVisible " + this.pGeneral.isVisible());
		Log.WriteLineLog("pRooms isVisible " + this.pRooms.isVisible());

		for (Component element : getComponents()) {
			Log.WriteLineLog(element.getClass() + " - " + element.getName() + " -vsbla -" + element.isVisible());
		}

		revalidate();
	}

	private void collapseTree() {
		this.tree.collapseRow(0);
	}

	private void createMenu() {
		JMenuBar menuBar = new JMenuBar();
		add(menuBar, BorderLayout.NORTH);

		JMenu mnMennu = new JMenu("Designer Menu");
		menuBar.add(mnMennu);

		JMenu mnMap = new JMenu("Map");

		JMenu mntmgGeneral = new JMenu("General");

		mnMennu.add(mntmgGeneral);

		JMenuItem mntmShowAllSettings = new JMenuItem("Show All Settings");
		mntmShowAllSettings.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Log.WriteLineLog("1) changing to settings");
				change2Settings();
			}
		});
		mntmgGeneral.add(mntmShowAllSettings);

		mnMennu.add(mnMap);

		JMenu mnRooms = new JMenu("Rooms");
		mnMap.add(mnRooms);

		JMenuItem mntmManageRooms = new JMenuItem("Manage Rooms");
		mntmManageRooms.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				change2Rooms();
			}
		});
		mnRooms.add(mntmManageRooms);

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
	}

	private void createTree() {

		this.tree = new JTree();
		this.tree.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		this.tree.setBorder(new MatteBorder(1, 1, 1, 1, new Color(0, 0, 0)));
		this.tree.setPreferredSize(new Dimension(150, 72));
		this.tree.setMinimumSize(new Dimension(150, 0));
		this.tree.setMaximumSize(new Dimension(150, 72));
		this.tree.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

		this.treeRootNode = new DefaultMutableTreeNode("Game");

		DefaultTreeModel treeModule = new DefaultTreeModel(this.treeRootNode);
		this.tree.setModel(treeModule);

		this.treePlayersNode = new DefaultMutableTreeNode("Players");
		this.treeRoomsNode = new DefaultMutableTreeNode("Rooms");

		add(this.tree, BorderLayout.WEST);
	}

	private void setTreeNode(MutableTreeNode node) {

		this.treeRootNode.removeAllChildren();

		collapseTree();

		if (node == null) {
			return;
		}

		this.treeRootNode.add(node);
	}

}
