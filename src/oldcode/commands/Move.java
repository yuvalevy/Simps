package oldcode.commands;

import java.awt.Point;

import oldcode.interfaces.Action;
import sims.module.objects.Player;

/**
 * @author Yuval
 *
 */
public class Move extends Action {

	private final Point moveToPoint;

	/**
	 * Initial the move action and set isInteraptable to true (default)
	 *
	 * @param moveToParameter:
	 *            The point which the player will move to
	 */

	public Move(Point moveToPoint) {
		// TODO: Duration for Move
		super(true, 1);

		this.moveToPoint = moveToPoint;
	}

	@Override
	public void excute(Player currentPlayer) {
		// TODO: If point is outbounsed do nothing
		// currentPlayer. setCurrentLocation( this.moveToPoint);
	}

}
