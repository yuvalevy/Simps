package sims;

import sims.basics.Log;
import sims.basics.LogLevel;
import sims.contolers.GuiControler;

public class Program {

	public static void main(String[] args) {

		Log.WriteLog("Starting game!", LogLevel.Debug);

		GuiControler controler = new GuiControler();

		controler.addRoom(1);
		controler.addRoom(2);

		controler.createDefalutMap();

		controler.startGame();
	}
}
