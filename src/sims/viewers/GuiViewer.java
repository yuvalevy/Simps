package sims.viewers;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import sims.basics.GameActions;
import sims.basics.Log;
import sims.module.main.World;
import sims.module.surface.GameLocation;

public class GuiViewer extends JPanel implements GameActions {

	private static final long serialVersionUID = 5844717809226598510L;

	private Choice playerChoice;
	private JButton btnPauseGame;
	private JButton btnAddPlayer;

	private JPanel roomsPanel;
	private JPanel managmentPanel;

	private final Dimension screenSize;

	private final ImagesPainter painter;

	public GuiViewer(Dimension gameDimention, World gameModule) {

		// ArrayList<MovableObject> gamePlayers
		this.screenSize = gameDimention;

		this.painter = new ImagesPainter(gameModule);
		Log.WriteLineLog("Created WorldViewer instance");

	}

	@Override
	public boolean addPlayer(String playerName) {

		this.playerChoice.add(playerName);
		return true;
	}

	@Override
	public void addRoom(int roomId, int toysCount) {

		this.painter.addRoom(roomId);

	}

	/**
	 * Builds the world viewer gui with all game elements. Whom controls the
	 * viewer, activates all elements changes
	 *
	 * @param plyrChoice
	 *            The player choicer
	 * @param gamePauser
	 *            The 'Pause Game' button
	 * @throws Exception
	 *             If one of the parameters sent is null
	 */
	public void buildViewer(Choice plyrChoice, JButton gamePauser, JButton playerAdder, MouseListener eventControler) {

		this.playerChoice = plyrChoice;
		this.btnPauseGame = gamePauser;
		this.btnAddPlayer = playerAdder;

		super.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		addMouseListener(eventControler);
		this.roomsPanel = createRoomPanel();
		this.managmentPanel = createManagmentPanel();

		this.add(this.managmentPanel);
		this.add(this.roomsPanel);

	}

	/**
	 * Creates the game frame and sets visible to true
	 */
	private void createFrame() {

		JFrame mainFrame = new JFrame("Family");
		mainFrame.add(this);

		/**
		 * Copied code
		 */

		// size of the screen
		// this.screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		//
		// // height of the task bar
		// int taskBarSize =
		// Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration()).bottom;
		// (int) screenSize.getWidth(), (int) screenSize.getHeight() -
		// taskBarSize

		mainFrame.setSize(this.screenSize);

		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		mainFrame.setVisible(true);

		Log.WriteLineLog("Frame created. setVisible(true)");
	}

	/**
	 * Creates the left management panel
	 *
	 * @return
	 */
	private JPanel createManagmentPanel() {

		JPanel managmentPanel = new JPanel();
		managmentPanel.setBorder(new LineBorder(Color.PINK, 2, true));
		managmentPanel.setLayout(new BorderLayout());

		int maxWidth = (int) (this.screenSize.getWidth() * 0.3);
		int maxHight = (int) (this.screenSize.getHeight());
		Dimension maxDimension = new Dimension(maxWidth, maxHight);
		managmentPanel.setMaximumSize(maxDimension);

		JPanel panel = new JPanel() {

			private static final long serialVersionUID = -5827637145228481113L;

			@Override
			protected void paintComponent(Graphics g) {

				super.paintComponent(g);

				paintManagmentPanel(this, g);
			}
		};
		panel.setBorder(new LineBorder(Color.GREEN, 3));
		managmentPanel.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		JLabel lblPlayerFocus = new JLabel("Player Focus");
		lblPlayerFocus.setBounds(10, 30, 80, 14);

		this.playerChoice.setFont(new Font("Arial", Font.PLAIN, 12));
		this.playerChoice.setBounds(100, 28, 100, 18);
		// Adding non focus element (when no player has been chosen)
		this.playerChoice.addItem("Non Focus");

		this.btnAddPlayer.setBounds(220, 30, 130, 17);

		panel.add(lblPlayerFocus);
		panel.add(this.playerChoice);
		panel.add(this.btnAddPlayer);

		managmentPanel.add(this.btnPauseGame, BorderLayout.SOUTH);

		Log.WriteLineLog("Created managment panel");

		return managmentPanel;
	}

	/**
	 * Creates the right room panel
	 *
	 * @return
	 */
	private JPanel createRoomPanel() {

		JPanel roomsPanel = new JPanel() {

			private static final long serialVersionUID = -5827637145228481113L;

			@Override
			protected void paintComponent(Graphics g) {

				super.paintComponent(g);

				paintRoomPanel(this, g);

			}
		};

		int maxWidth = (int) (this.screenSize.getWidth() * 0.7);
		int maxHight = (int) (this.screenSize.getHeight());
		Dimension maxDimension = new Dimension(maxWidth, maxHight);
		roomsPanel.setMaximumSize(maxDimension);

		roomsPanel.setBorder(new LineBorder(new Color(255, 175, 175), 2, true));
		roomsPanel.setMinimumSize(new Dimension(1000, 1000));

		Log.WriteLineLog("Created room panel");

		return roomsPanel;
	}

	@Override
	public void movePlayer(GameLocation newLocation) {
		// gui controller does not implements this func

	}

	@Override
	public void paintComponent(Graphics g) {

		// Log.WriteLog("Start GuiViewer paintComponent ");

		super.paintComponent(g);

		paintManagmentPanel(this, g);

		// Log.WriteLog("End GuiViewer paintComponent ");

	}

	public void paintManagmentPanel(Component c, Graphics g) {

		this.painter.paintManagmentPanel(c, g);

		// Log.WriteLog("Paint manage panel");

	}

	public void paintRoomPanel(Component c, Graphics g) {

		this.painter.paintRoom(c, g);

		// Log.WriteLog("Paint room panel");
	}

	@Override
	public void pauseGame() {
		// TODO Auto-generated method stub

	}

	@Override
	public void removePlayer(String playerName) {

		try {
			this.playerChoice.remove(playerName);
		} catch (Exception e) {

		}
	}

	@Override
	public void sendPlayerSearching(Point pointClicked) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setFocusedPlayer(String playerName) {

		this.playerChoice.select(playerName);
	}

	@Override
	public void setFocusedRoom(int roomId) {

		this.painter.setFocusedRoom(roomId);

	}

	@Override
	public void startGame() {

		createFrame();

	}

	public void tick() {

		// Log.WriteLog("Start GUI tick");
		repaint();
		// Log.WriteLog("End GUI tick");
	}

}