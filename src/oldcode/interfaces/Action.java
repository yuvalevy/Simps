/**
 *
 */
package oldcode.interfaces;

import sims.module.objects.Player;

/**
 * @author Yuval
 *
 */
public abstract class Action implements Command {

	protected boolean isInteraptable;
	protected int duration;

	/**
	 *
	 */
	public Action(boolean isInteraptable, int duration) {

		this.isInteraptable = isInteraptable;
		this.duration = duration;
	}

	@Override
	public void excute(Player currentActor) {

	}

}
