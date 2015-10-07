package sims.contolers;

import java.awt.Choice;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;

import sims.basics.GameActions;
import sims.basics.Randomaizer;
import sims.module.main.ConfigurationManager;
import sims.module.main.World;
import sims.module.surface.GameLocation;
import sims.viewers.GuiViewer;

public class GuiControler implements GameActions, Runnable {

	private final World gameModule;
	private final GuiViewer gameUi;
	private final MouseHandler handler;

	private Choice playerChoice;
	private JButton btnPauseGame;
	private JButton btnAddPlayer;

	private final Thread gameThread;
	private Dimension gameDimension;

	public GuiControler() {

		// TODO: Configuration
		final Rectangle cellDefaultSize = ConfigurationManager.getCellDefaultSize();

		this.gameThread = new Thread(this);

		setGameDimention();

		this.handler = new MouseHandler(this, (int) (this.gameDimension.width * 0.3));

		this.gameModule = new World(this.gameDimension, cellDefaultSize);

		this.gameUi = new GuiViewer(this.gameDimension, this.gameModule);

		buildGameControlers();

		this.gameUi.buildViewer(this.playerChoice, this.btnPauseGame, this.btnAddPlayer, this.handler);

	}

	@Override
	public boolean addPlayer(String playerName) {

		if (this.gameModule.addPlayer(playerName)) {
			if (this.gameUi.addPlayer(playerName)) {
				setFocusedPlayer(playerName);
				return true;
			}
		}

		removePlayer(playerName);
		return false;

	}

	@Override
	public void addRoom(int roomId, int toysCount) {

		this.gameModule.addRoom(roomId, toysCount);
		this.gameUi.addRoom(roomId, toysCount);

		setFocusedRoom(roomId);
	}

	private void buildGameControlers() {

		// Pause Game
		this.btnPauseGame = new JButton("Pause Game");
		this.btnPauseGame.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				pauseGame();
			}
		});

		// Player Choice
		this.playerChoice = new Choice();
		this.playerChoice.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				setFocusedPlayer(GuiControler.this.playerChoice.getSelectedItem());
			}
		});

		// Player Adder
		this.btnAddPlayer = new JButton("Add Player ->");
		this.btnAddPlayer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String name = "Yuval" + Randomaizer.getPlayerNumber();
				addPlayer(name);
			}
		});

	}

	public void createDefalutMap() {

		this.gameModule.createDefalutMap();

	}

	public void guiClick(Point pointClicked) {

		int roomId = this.gameModule.getCurrentRoom();
		GameLocation newLocation = new GameLocation(pointClicked, roomId);

		movePlayer(newLocation);

	}

	public void guiDoubleClick(Point pointClicked) {

		String currentPlayer = this.gameModule.getPlayerByPoint(pointClicked);

		if (currentPlayer != null) {
			setFocusedPlayer(currentPlayer);
		}

	}

	@Override
	public void movePlayer(GameLocation newLocation) {

		this.gameModule.movePlayer(newLocation);
		this.gameUi.movePlayer(newLocation);

	}

	@Override
	public void pauseGame() {

		this.gameThread.interrupt();

		this.gameModule.pauseGame();
		this.gameUi.pauseGame();
	}

	@Override
	public void removePlayer(String playerName) {

		this.gameModule.removePlayer(playerName);
		this.gameUi.removePlayer(playerName);

	}

	@Override
	public void run() {

		int sleepTime = ConfigurationManager.getSleepTime();

		while (this.gameModule.isRunning()) {

			tick();

			int changing = this.gameModule.changingRoom();

			if (changing != -1) {

				setFocusedRoom(changing);

			}

			try {

				Thread.sleep(sleepTime);

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void setFocusedPlayer(String playerName) {

		this.gameUi.setFocusedPlayer(playerName);
		this.gameModule.setFocusedPlayer(playerName);

	}

	@Override
	public void setFocusedRoom(int roomId) {

		this.gameUi.setFocusedRoom(roomId);
		this.gameModule.setFocusedRoom(roomId);

	}

	/**
	 * Sets game dimension by screen size
	 */
	private void setGameDimention() {
		// size of the screen
		this.gameDimension = Toolkit.getDefaultToolkit().getScreenSize();

		// height of the task bar
		int taskBarSize = 40;// Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration()).bottom;
		this.gameDimension.setSize((int) this.gameDimension.getWidth(),
				((int) this.gameDimension.getHeight()) - taskBarSize);
	}

	@Override
	public void startGame() {

		// Conditions before starting a game:

		if (this.gameModule.getRoomCount() == 0) {

			return;

		}

		this.gameUi.startGame();
		this.gameModule.startGame();

		this.gameThread.start();

	}

	/**
	 * ticks game module and viewer
	 */
	public void tick() {

		// Log.WriteLog("Start tick");

		this.gameModule.tick();
		this.gameUi.tick();

		// Log.WriteLog("End tick");
	}
}
