/**
 *
 */
package oldcode.commands;

import oldcode.interfaces.Action;
import sims.module.objects.Player;

/**
 * @author Yuval
 *
 */
public class Bend extends Action {

	/**
	 *
	 */
	public Bend() {

		// TODO: Duration for Bend
		super(false, 0);
	}

	@Override
	public void excute(Player currentPlayer) {
		// currentPlayer.bending();
	}

}
