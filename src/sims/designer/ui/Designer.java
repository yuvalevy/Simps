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
import sims.designer.demos.DemoWorld;

public class Designer extends JPanel {

	private static final long serialVersionUID = 5625046021958313278L;

	private DefaultMutableTreeNode treePlayersNode;
	private DefaultMutableTreeNode treeRootNode;

	private final GeneralSettingsPanel pGeneral;

	private final RoomsCanvas pRooms;

	private PropertiesComponent currentComponent;

	private JTree tree;

	public Designer(DemoWorld demoWorld) {

		setLayout(new BorderLayout(0, 0));

		createMenu();
		createTree();
		createSaveButton();

		this.pGeneral = new GeneralSettingsPanel(demoWorld.getDemoSettings());
		this.pRooms = new RoomsCanvas();

		this.pGeneral.setVisible(false);
		this.pRooms.setVisible(false);

	}

	public void addToPlayersNode(String str) {

		if (str != null) {
			if (str.length() != 0) {

				this.treePlayersNode.add(new DefaultMutableTreeNode(str));
			}
		}

	}

	private void change2Rooms() {

		if (!this.pRooms.isVisible()) {
			Log.WriteLineLog("2) pRooms is not visible... :(");

			changeComponent(this.pRooms);
		}
	}

	private void change2Settings() {

		if (!this.pGeneral.isVisible()) {
			Log.WriteLineLog("2) pGeneral is not visible... :(");

			setTreeNode(null);
			changeComponent(this.pGeneral);
		}
	}

	private void changeComponent(PropertiesComponent component) {

		Log.WriteLineLog("3) changing to panel " + ((Component) component).getName());

		PropertiesComponent temp = this.currentComponent;

		if (temp != null) {
			temp.setVisible(false);
			remove((Component) this.currentComponent);
			clearTree();
		}

		this.currentComponent = component;

		add((Component) this.currentComponent, BorderLayout.CENTER);
		setTreeNode(this.currentComponent.getTreeNodes());

		this.currentComponent.setVisible(true);

		Log.WriteLineLog("pGeneral isVisible " + this.pGeneral.isVisible());
		Log.WriteLineLog("pRooms isVisible " + this.pRooms.isVisible());

		revalidate();
	}

	private void clearTree() {
		this.treeRootNode.removeAllChildren();
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

	private void createSaveButton() {

		JButton btnSaveCurrentState = new JButton("Save Current State");

		btnSaveCurrentState.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});

		add(btnSaveCurrentState, BorderLayout.SOUTH);

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

		add(this.tree, BorderLayout.WEST);
	}

	private void setTreeNode(MutableTreeNode treeNode) {

		collapseTree();

		if (treeNode == null) {
			return;
		}

		// for (MutableTreeNode treeNode : nodes) {

		this.treeRootNode.add(treeNode);
		// }
	}

}
