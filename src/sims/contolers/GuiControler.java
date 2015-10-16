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
import sims.basics.GameState;
import sims.basics.Log;
import sims.basics.Randomaizer;
import sims.basics.configurations.ConfigurationManager;
import sims.module.main.World;
import sims.module.surface.GameLocation;
import sims.viewers.GuiViewer;

public class GuiControler implements GameActions, Runnable {

	private final World gameModule;
	private final GuiViewer gameUi;

	private Choice playerChoice;
	private JButton btnPauseGame;
	private JButton btnAddPlayer;

	private final Thread gameThread;
	private Dimension gameDimension;

	private GameState gameState;

	public GuiControler() {

		final Rectangle cellDefaultSize = ConfigurationManager.getCellDefaultSize();

		this.gameThread = new Thread(this);
		this.gameState = null;

		setGameDimention();

		MouseHandler handler = new MouseHandler(this, (int) (this.gameDimension.width * 0.3));

		this.gameModule = new World(this.gameDimension, cellDefaultSize);

		this.gameUi = new GuiViewer(this.gameDimension, this.gameModule);

		buildGameControlers();

		this.gameUi.buildViewer(this.playerChoice, this.btnPauseGame, this.btnAddPlayer, handler);

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

	@Override
	public void changeGameState(GameState state) {

		Log.WriteLineLog("Game State changed: " + state);
		this.gameState = state;

		switch (state) {

		case STARTED:
			if (!startGame()) {

				this.gameState = null;
				return;
			}
			break;

		case PAUSING:
			pauseGame();
			break;

		default:
			break;

		}

		this.gameModule.changeGameState(state);
		this.gameUi.changeGameState(state);
	}

	public void createDefalutMap() {

		this.gameModule.createDefalutMap();

	}

	public void guiLeftClick(Point pointClicked, boolean controlPressed) {

		int roomId = this.gameModule.getCurrentRoom();
		GameLocation newLocation = new GameLocation(pointClicked, roomId);

		if (controlPressed) {
			sendPlayerSearching(newLocation);
		} else {
			movePlayer(newLocation);
		}

	}

	public void guiRightClick(Point pointClicked) {

		String currentPlayer = this.gameModule.getPlayerByPoint(pointClicked);

		if (currentPlayer != null) {
			Log.WriteLineLog("Setting " + currentPlayer + " to be focused.");
			setFocusedPlayer(currentPlayer);
		}

	}

	public void initialFeeling(Point pointClicked) {

		Object obj = this.gameUi.getFeelingAtPoint(pointClicked);
		String playerName = this.gameUi.getPlayerNameAtPoint(pointClicked);

		setFocusedPlayer(playerName);

		this.gameModule.initialFeeling(obj);

	}

	public void movePlayer(GameLocation newLocation) {

		this.gameModule.movePlayer(newLocation, true);

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

			try {

				Thread.sleep(sleepTime);

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void sendPlayerSearching(GameLocation newLocation) {

		this.gameModule.sendPlayerSearching(newLocation);
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
	 * ticks game module and viewer
	 */
	@Override
	public void tick() {

		if (this.gameState != GameState.STARTED) {
			return;
		}

		this.gameModule.tick();
		this.gameUi.tick();

		tryChangeRoom();

		GameState tempState = isGameOver();

		if (tempState != null) {

			changeGameState(tempState);
		}
	}

	private void buildGameControlers() {

		// Pause Game
		this.btnPauseGame = new JButton("Pause Game");
		this.btnPauseGame.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				changeGameState(GameState.PAUSING);
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

	private GameState isGameOver() {
		return this.gameModule.isGameOver();
	}

	private void pauseGame() {

		this.gameThread.interrupt();
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

	private boolean startGame() {

		// Conditions before starting a game:

		if (this.gameModule.getRoomCount() == 0) {

			return false;

		}

		this.gameThread.start();

		return true;

	}

	/**
	 * If it is needed to change room, the room is changing. otherwise, nothing
	 * is happening
	 */
	private void tryChangeRoom() {
		int changing = this.gameModule.needRoomChanging();

		if (changing != -1) {

			setFocusedRoom(changing);

		}
	}
}
