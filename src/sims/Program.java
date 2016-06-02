package sims;

import sims.basics.GameState;
import sims.basics.Log;
import sims.basics.LogLevel;
import sims.basics.configurations.ConfigurationManager;
import sims.contolers.GuiControler;

public class Program {

	public static void main(String[] args) {

		Log.WriteLineLog("Starting game!", LogLevel.DEBUG);
		Log.setLogLevel(LogLevel.DEBUG);
		GuiControler controler = new GuiControler();

		int toysCount = ConfigurationManager.getToysDefaultNumberPerRoom();

		controler.addRoom(1, toysCount);
		controler.addRoom(2, toysCount);

		controler.createDefalutMap();

		controler.changeGameState(GameState.STARTED);
	}
}
