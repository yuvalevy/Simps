package sims.contolers;

import java.awt.Choice;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;

import sims.basics.GameActions;
import sims.basics.Log;
import sims.basics.Randomaizer;
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

	private final Thread gameThread = new Thread(this);
	private Dimension gameDimension;

	public GuiControler() {

		this.handler = new MouseHandler(this);

		setGameDimention();
		this.gameModule = new World((int) this.gameDimension.getWidth(), (int) this.gameDimension.getHeight());

		this.gameUi = new GuiViewer(this.gameDimension, this.gameModule.getPlayers());

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

	public void guiClick(Point pointClicked) {

		int roomId = this.gameModule.getCurrentRoom();
		GameLocation newLocation = new GameLocation(pointClicked, roomId);

		movePlayer(newLocation);

		Log.WriteLog("One click - room: " + roomId + " moving to " + pointClicked);

	}

	public void guiDoubleClick(Point pointClicked) {

		String currentPlayer = this.gameModule.getPlayerByPoint(pointClicked);

		if (currentPlayer != null) {
			setFocusedPlayer(currentPlayer);
		}

		Log.WriteLog("Double click - current player is " + currentPlayer);

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

		while (this.gameModule.isRunning()) {
			this.gameUi.paint();

			try {
				Thread.sleep(1);
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

		this.gameUi.startGame();
		this.gameModule.startGame();

		this.gameThread.run();

	}
}
