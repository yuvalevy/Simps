package ui;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Font;

import javax.swing.AbstractListModel;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;

public class UITest extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UITest() {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		JPanel roomsPanel = new JPanel();
		roomsPanel.setBorder(new LineBorder(new Color(255, 175, 175), 2, true));
		add(roomsPanel);

		JPanel managmentPanel = new JPanel();
		managmentPanel.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		add(managmentPanel);
		managmentPanel.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(Color.MAGENTA, 3));
		managmentPanel.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		Choice currentPlayerChoice = new Choice();
		currentPlayerChoice.setFont(new Font("Arial", Font.PLAIN, 12));
		currentPlayerChoice.setBounds(125, 28, 87, 17);
		currentPlayerChoice.addItem("Player 2");
		currentPlayerChoice.addItem("Player 2");
		currentPlayerChoice.addItem("Player 2");
		panel.add(currentPlayerChoice);

		JLabel lblCurrentPlayerFocus = new JLabel("Current Player Focus");
		lblCurrentPlayerFocus.setBounds(10, 32, 109, 14);
		panel.add(lblCurrentPlayerFocus);

		JList<String> list = new JList<String>();
		list.setValueIsAdjusting(true);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setModel(new AbstractListModel<String>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			String[] values = new String[] { "Non Focus", "Player 1", "Player 2" };

			public int getSize() {
				return values.length;
			}

			public String getElementAt(int index) {
				return values[index];
			}
		});
		list.setSelectedIndex(0);
		list.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		list.setBounds(242, 28, 65, 67);
		panel.add(list);

		JButton btnPauseGame = new JButton("Pause Game");
		managmentPanel.add(btnPauseGame, BorderLayout.SOUTH);
	}
}
