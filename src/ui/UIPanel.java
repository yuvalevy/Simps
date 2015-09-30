package ui;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import sims.module.main.Log;
import sims.module.main.LogLevel;
import sims.module.main.World;

public class UIPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private Choice currentPlayerChoice;

	private final World worldInstance;

	public UIPanel() {

		this.worldInstance = new World();

		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		JPanel roomsPanel = createRoomPanel();
		JPanel managmentPanel = createManagmentPanel();

		this.add(roomsPanel);
		this.add(managmentPanel);

		createFrame();

		Log.WriteLog("Created UIPanel instance", LogLevel.Debug);

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
	 * Creates the game frame and sets visible to true
	 */
	private void createFrame() {

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

		this.currentPlayerChoice = new Choice();
		this.currentPlayerChoice.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				String playerName = UIPanel.this.currentPlayerChoice.getSelectedItem();

				UIPanel.this.worldInstance.setFocusedPlayer(playerName);
			}
		});

		this.currentPlayerChoice.setFont(new Font("Arial", Font.PLAIN, 12));
		this.currentPlayerChoice.setBounds(155, 28, 123, 18);

		// Adding non focus element (when no player has been chosen)
		this.currentPlayerChoice.addItem("Non Focus");

		JLabel lblCurrentPlayerFocus = new JLabel("Current Player Focus");
		lblCurrentPlayerFocus.setBounds(10, 32, 139, 14);
		panel.add(lblCurrentPlayerFocus);
		panel.add(this.currentPlayerChoice);

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
		JPanel roomsPanel = new JPanel(true);
		roomsPanel.setBorder(new LineBorder(new Color(255, 175, 175), 2, true));
		return roomsPanel;
	}

	/**
	 *
	 */
	public void printPlayers() {
		String temp = "";
		for (int i = 0; i < this.currentPlayerChoice.getItemCount(); i++) {
			temp = i + " - " + this.currentPlayerChoice.getItem(i);
			Log.WriteLog(temp, LogLevel.Debug);
		}
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

}
