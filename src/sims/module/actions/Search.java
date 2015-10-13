package sims.module.actions;

import javax.swing.ImageIcon;

import sims.basics.Action;
import sims.basics.configurations.ConfigurationManager;
import sims.module.surface.GameLocation;

public class Search extends Action {

	// private GameLocation searchStartingPosition;
	private final int searchTime;
	private int countDown;

	Search(Action preAction, ImageIcon icon) {

		super(ActionIdentifier.Search, preAction, icon);
		this.searchTime = ConfigurationManager.getPlayerSearchTime();

		stop();
	}

	@Override
	public boolean canInterrupt() {
		return false;
	}

	@Override
	public boolean isOver() {
		return this.countDown == 0;
	}

	@Override
	public void start() {

		super.start();
		this.countDown = this.searchTime;
	}

	@Override
	public void stop() {

		super.stop();
		this.countDown = 0;
		// this.searchStartingPosition = null;
	}

	@Override
	public GameLocation tick() {

		if (isActive()) {

			if (this.preAction.isOver()) {

				this.preAction.stop();

				if (this.countDown == 0) {
					// return this.searchStartingPosition;
				} else {
					this.countDown--;
				}
			} else {

				GameLocation preActionResult = this.preAction.tick();

				if (preActionResult != null) {
					// this.searchStartingPosition = preActionResult;
				}

				return preActionResult;
			}

		}

		return null;
	}

}
