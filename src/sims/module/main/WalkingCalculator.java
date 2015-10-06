package sims.module.main;

import sims.basics.Log;
import sims.module.calculators.Calculator;
import sims.module.calculators.Dijkstra;
import sims.module.calculators.Line;
import sims.module.objects.Player;
import sims.module.surface.GameLocation;
import sims.module.surface.Map;

class WalkingCalculator {

	Map worldMap;
	private Calculator calculator;

	public WalkingCalculator(Map worldMap) {

		initCalculator(worldMap);

	}

	private void initCalculator(Map worldMap) {

		String calcName = ConfigurationManager.getCalculatorName();

		switch (calcName) {

		case "dijkstra":
			this.calculator = new Dijkstra(worldMap);
			break;

		case "line":
			this.calculator = new Line(worldMap);
			break;
		}
	}

	public void planTrip(Player player, GameLocation finalLocation) {

		// TODO: dijestra

		this.calculator.excute(player.getCurrentLocation(), finalLocation);
		this.calculator.implementSteps(player, player.getCurrentLocation(), finalLocation);

		// GameLocation currentLocation = player.getCurrentLocation();
		//
		// if (currentLocation.getRoomId() == finalLocation.getRoomId()) {
		//
		// // Same room movement
		//
		// // planStraightLineTrip(player, currentLocation, finalLocation);
		//
		// planDijkstraTrip(player, currentLocation, finalLocation);
		//
		// } else {
		//
		// // Other room movement
		//
		// }

		Log.WriteLog("Added all steps to " + player.getPlayerName());
	}

}