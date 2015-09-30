package sims.module.main;

import sims.basics.Log;
import sims.module.objects.Player;
import sims.module.surface.GameLocation;

public abstract class WalkingCalculator {

	public static void PlanTrip(Player player, GameLocation finalLocation) {

		// TODO: dijestra
		player.addStep(finalLocation);

		Log.WriteLog("Added all steps to + " + player.getPlayerName());
	}
}
