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
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import sims.basics.GameActions;
import sims.basics.Log;
import sims.module.feelings.FeelingFactory;
import sims.module.main.World;

public class GuiViewer extends JPanel implements GameActions {

	private static final long serialVersionUID = 5844717809226598510L;

	private Choice playerChoice;
	private JButton btnPauseGame;
	private JButton btnAddPlayer;

	private JPanel roomsPanel;
	private JPanel managmentPanel;

	private final Dimension screenSize;

	private final BoardPainter painter;

	private JTable tableFeelings;

	public GuiViewer(Dimension gameDimention, World gameModule) {

		// ArrayList<MovableObject> gamePlayers
		this.screenSize = gameDimention;

		this.painter = new BoardPainter(gameModule);
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
	 */
	public void buildViewer(Choice plyrChoice, JButton gamePauser, JButton playerAdder, MouseListener eventControler) {

		this.playerChoice = plyrChoice;
		this.btnPauseGame = gamePauser;
		this.btnAddPlayer = playerAdder;

		super.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		addMouseListener(eventControler);
		this.roomsPanel = createRoomPanel();
		this.managmentPanel = createManagementPanel(eventControler);

		add(this.managmentPanel);
		add(this.roomsPanel);

	}

	public Component changeTableCellsColor(JTable table, Component c, int row, int column) {

		c.setFont(new Font("Arial", 4, 10));

		String clmnProp = "c" + column;
		String rowProp = "r" + row;

		Integer hitColumn = (Integer) table.getClientProperty(clmnProp);
		Integer hitRow = (Integer) table.getClientProperty(rowProp);

		if ((hitColumn != null) && (column == hitColumn) && (hitRow != null) && (row == hitRow)) {
			c.setBackground(Color.red);
		} else {

			c.setBackground(Color.white);
		}

		return c;
	}

	public Object getFeelingAtPoint(Point pointClicked) {

		int row = this.tableFeelings.rowAtPoint(pointClicked);
		int column = this.tableFeelings.columnAtPoint(pointClicked);

		Object feelingAtPoint = this.tableFeelings.getValueAt(row, column);

		return feelingAtPoint;

	}

	public String getPlayerNameAtPoint(Point pointClicked) {

		int row = this.tableFeelings.rowAtPoint(pointClicked);

		String feelingAtPoint = (String) this.tableFeelings.getValueAt(row, 0);

		return feelingAtPoint;

	}

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
	}

	public void paintManagmentPanel(Component c, Graphics g, JTable table) {

		this.painter.paintManagementPanel(c, g, table);

	}

	public void paintRoomPanel(Component c, Graphics g) {

		this.painter.paintRoom(c, g);
	}

	@Override
	public void pauseGame() {

	}

	@Override
	public void removePlayer(String playerName) {

		try {
			this.playerChoice.remove(playerName);
		} catch (Exception e) {

		}
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

	@Override
	public void stopGame(boolean isWinner) {

		String message = "Game is over and you ";
		if (isWinner) {
			message += "WON :D!";

		} else {
			message += "LOST :(";
		}

		message += System.getProperty("line.separator") + " Thanks for playing";

		JOptionPane.showMessageDialog(null, message, "Bye bye", JOptionPane.INFORMATION_MESSAGE);

		System.exit(0);

	}

	@Override
	public void tick() {

		repaint();
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

		mainFrame.setSize(this.screenSize);

		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		mainFrame.setVisible(true);

		Log.WriteLineLog("Frame created. setVisible(true)");
	}

	/**
	 * Creates the left management panel
	 *
	 * @param feelingsListener
	 *
	 * @return
	 */
	private JPanel createManagementPanel(MouseListener feelingsListener) {

		JPanel managementPanel = new JPanel();
		managementPanel.setBorder(new LineBorder(Color.PINK, 2, true));
		managementPanel.setLayout(new BorderLayout());

		managementPanel.setMaximumSize(getManagementPanelMaxSize());

		JPanel panel = new JPanel() {

			private static final long serialVersionUID = -5827637145228481113L;

			@Override
			protected void paintComponent(Graphics g) {

				super.paintComponent(g);

				JTable table = getTable();

				paintManagmentPanel(this, g, table);
			}

			private JScrollPane getJScrollPane() {

				for (Component comp : getComponents()) {
					if (comp instanceof JScrollPane) {
						return (JScrollPane) comp;
					}
				}

				return null;
			}

			private JTable getJTable(JComponent comp) {

				for (Component subcomp : comp.getComponents()) {
					if (subcomp instanceof JTable) {
						return (JTable) subcomp;
					}
				}

				return null;
			}

			private JTable getTable() {

				JTable table = null;
				JScrollPane pane = getJScrollPane();

				if (pane != null) {
					table = getJTable(pane.getViewport());
				}
				return table;
			}
		};

		panel.setBorder(new LineBorder(Color.GREEN, 3));
		managementPanel.add(panel, BorderLayout.CENTER);
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

		JScrollPane scrol = getFeelingsTable(feelingsListener);

		panel.add(scrol);
		managementPanel.add(this.btnPauseGame, BorderLayout.SOUTH);

		Log.WriteLineLog("Created managment panel");

		return managementPanel;
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

		roomsPanel.setMaximumSize(getRoomPanelMaxSize());

		roomsPanel.setBorder(new LineBorder(new Color(255, 175, 175), 2, true));
		roomsPanel.setMinimumSize(new Dimension(1000, 1000));

		Log.WriteLineLog("Created room panel");

		return roomsPanel;
	}

	private JScrollPane getFeelingsTable(MouseListener feelingsListener) {

		this.tableFeelings = new JTable() {

			private static final long serialVersionUID = 7755646788868082357L;

			@Override
			public Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int column) {

				Component c = super.prepareRenderer(renderer, row, column);

				c = changeTableCellsColor(this, c, row, column);

				return c;
			}
		};

		this.tableFeelings.addMouseListener(feelingsListener);

		JScrollPane scrol = new JScrollPane(this.tableFeelings);
		scrol.setBounds(10, 250, 350, 100);
		scrol.setAutoscrolls(true);

		DefaultTableModel tableModel = (DefaultTableModel) this.tableFeelings.getModel();

		tableModel.addColumn("Player");

		String[] exisingFeelingsNames = FeelingFactory.getExisingFeelingsNames();

		for (int i = 0; i < exisingFeelingsNames.length; i++) {

			String name = exisingFeelingsNames[i];

			tableModel.addColumn(name);

		}
		return scrol;
	}

	private Dimension getManagementPanelMaxSize() {
		int maxWidth = (int) (this.screenSize.getWidth() * 0.3);
		int maxHight = (int) (this.screenSize.getHeight());
		Dimension maxDimension = new Dimension(maxWidth, maxHight);
		return maxDimension;
	}

	private Dimension getRoomPanelMaxSize() {
		int maxWidth = (int) (this.screenSize.getWidth() * 0.7);
		int maxHight = (int) (this.screenSize.getHeight());
		Dimension maxDimension = new Dimension(maxWidth, maxHight);
		return maxDimension;
	}
}