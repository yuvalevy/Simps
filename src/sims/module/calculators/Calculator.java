package sims.module.calculators;

import sims.module.objects.Player;
import sims.module.surface.GameLocation;

public interface Calculator {

	/**
	 * Determines whether calculator be multi executer
	 */
	boolean canMulti();

	/**
	 * Executes calculator
	 *
	 * @param spaceCells
	 * @param start
	 * @param end
	 */
	void excute(GameLocation start, GameLocation end);

	/**
	 * Adds steps to player
	 *
	 * @param p
	 *            implement on this player
	 * @param start
	 *            start cell
	 * @param end
	 *            end cell
	 * @param currentRoomId
	 */
	void implementSteps(Player p, GameLocation start, GameLocation end);

	/**
	 * Is calculator active now
	 *
	 * @return true when active. otherwise, false.
	 */
	boolean isActive();

}