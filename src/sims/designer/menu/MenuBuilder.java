package sims.designer.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import sims.basics.Log;
import sims.designer.ui.Designer;

public class MenuBuilder {

	public static JMenuBar createMenu(Designer designerUi) {

		JMenuBar menuBar = new JMenuBar();

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
				designerUi.change2Settings();
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
				designerUi.change2Rooms();
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

		return menuBar;
	}
}
