package ui;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class UIPanel extends JPanel {

	private Choice currentPlayerChoice;

	public UIPanel() {

		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		JPanel roomsPanel = createRoomPanel();
		JPanel managmentPanel = createManagmentPanel();

		add(roomsPanel);
		add(managmentPanel);

	}

	/**
	 * Creates the left management panel
	 * 
	 * @return
	 */
	private JPanel createManagmentPanel() {

		JPanel managmentPanel = new JPanel();
		managmentPanel.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		managmentPanel.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(Color.MAGENTA, 3));
		managmentPanel.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		currentPlayerChoice = new Choice();
		currentPlayerChoice.setFont(new Font("Arial", Font.PLAIN, 12));
		currentPlayerChoice.setBounds(155, 28, 123, 18);

		// Adding non focus element (when no player has been chosen)
		currentPlayerChoice.addItem("Non Focus");

		JLabel lblCurrentPlayerFocus = new JLabel("Current Player Focus");
		lblCurrentPlayerFocus.setBounds(10, 32, 139, 14);
		panel.add(lblCurrentPlayerFocus);
		panel.add(currentPlayerChoice);

		// JList<String> list = new JList<String>();
		// list.setValueIsAdjusting(true);
		// list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		// list.setModel(new AbstractListModel<String>() {
		// /**
		// *
		// */
		// private static final long serialVersionUID = 1L;
		// String[] values = new String[] { "Non Focus", "Player 1", "Player 2"
		// };
		//
		// public int getSize() {
		// return values.length;
		// }
		//
		// public String getElementAt(int index) {
		// return values[index];
		// }
		// });
		// list.setSelectedIndex(0);
		// list.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		// list.setBounds(242, 28, 65, 67);
		// panel.add(list);

		JButton btnPauseGame = new JButton("Pause Game");
		managmentPanel.add(btnPauseGame, BorderLayout.SOUTH);
		return managmentPanel;
	}

	/**
	 * Creates the right room panel
	 * 
	 * @return
	 */
	private JPanel createRoomPanel() {
		JPanel roomsPanel = new JPanel();
		roomsPanel.setBorder(new LineBorder(new Color(255, 175, 175), 2, true));
		return roomsPanel;
	}

	/**
	 * Creates the game frame and sets visible to true
	 */
	public void createFrame() {

		JFrame mainFrame = new JFrame("Family");
		mainFrame.getContentPane().add(this);

		/**
		 * Copied code
		 */

		// size of the screen
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		// height of the task bar
		int taskBarSize = Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration()).bottom;

		/**
		 * End coping
		 */

		mainFrame.setSize((int) screenSize.getWidth(), (int) screenSize.getHeight() - taskBarSize);

		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		mainFrame.setVisible(true);
	}

	/**
	 * Sets the choice selection to be for this player
	 * 
	 * @param playerName
	 *            player name
	 */
	public void setFocusedPlayer(String playerName) {

		this.currentPlayerChoice.select(playerName);

	}

	/**
	 * Adds new player to choice list
	 * 
	 * @param playerName
	 *            the name of the player to display
	 * @return the player id
	 */
	public int addPlayer(String playerName) {

		this.currentPlayerChoice.add(playerName);
		return this.currentPlayerChoice.getItemCount() - 1;
	}

	/**
	 * 
	 */
	public void printPlayers() {
		for (int i = 0; i < this.currentPlayerChoice.getItemCount(); i++) {
			System.out.println(i + " - " + this.currentPlayerChoice.getItem(i));

		}
	}

}
