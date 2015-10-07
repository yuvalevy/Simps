package sims;

import sims.basics.Log;
import sims.basics.LogLevel;
import sims.contolers.GuiControler;
import sims.module.main.ConfigurationManager;

public class Program {

	public static void main(String[] args) {

		Log.WriteLineLog("Starting game!", LogLevel.Debug);

		GuiControler controler = new GuiControler();

		int toysCount = ConfigurationManager.getToysDefaultNumberPerRoom();

		controler.addRoom(1, toysCount);
		controler.addRoom(2, toysCount);

		controler.createDefalutMap();

		controler.startGame();
	}
}
