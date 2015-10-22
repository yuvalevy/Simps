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
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.border.MatteBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;

import sims.basics.Log;
import sims.designer.demos.DemoWorld;
import sims.designer.menu.MenuBuilder;

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

		JMenuBar menuBar = MenuBuilder.createMenu(this);
		add(menuBar, BorderLayout.NORTH);

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

	public void change2Rooms() {
		changeComponent(this.pRooms);
	}

	public void change2Settings() {
		changeComponent(this.pGeneral);
	}

	private void changeComponent(PropertiesComponent component) {

		if (!component.isVisible()) {
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
	}

	private void clearTree() {
		this.treeRootNode.removeAllChildren();
	}

	private void collapseTree() {
		this.tree.collapseRow(0);
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
