package sims.module.actions;

import javax.swing.ImageIcon;

import sims.basics.Action;
import sims.basics.config.ConfigurationManager;
import sims.module.surface.GameLocation;

public class Search implements Action {

	private static final ActionIdentifier identifier = ActionIdentifier.Search;

	private final Action preAction;
	private GameLocation searchStartingPosition;
	private final ImageIcon icon;
	private final int searchTime;
	private int countDown;
	private boolean isActive;

	Search(Action preAction, ImageIcon icons) {

		this.preAction = preAction;
		this.searchTime = ConfigurationManager.getPlayerSearchTime();

		stop();
		this.icon = icons;
	}

	@Override
	public boolean canInterrupt() {
		return false;
	}

	@Override
	public ActionIdentifier getIdentifier() {
		return identifier;
	}

	@Override
	public ImageIcon getNextImage() {

		if (!isActive()) {
			return null;
		}

		if (this.preAction.isOver()) {
			return this.icon;
		} else {
			return this.preAction.getNextImage();
		}

	}

	@Override
	public boolean isAction(ActionIdentifier identifier) {
		return Search.identifier == identifier;
	}

	@Override
	public boolean isActive() {
		return this.isActive;
	}

	@Override
	public boolean isOver() {

		return this.countDown == 0;
	}

	@Override
	public void start() {
		this.isActive = true;
		this.countDown = this.searchTime;

		// Starting pre action
		this.preAction.start();
	}

	@Override
	public void stop() {

		this.isActive = false;
		this.countDown = 0;
		this.searchStartingPosition = null;
	}

	@Override
	public GameLocation tick() {

		if (isActive()) {

			// If preAction is finished, search count down is starting
			if (this.preAction.isOver()) {

				this.preAction.stop();

				if (this.countDown == 0) {
					return this.searchStartingPosition;
				} else {
					this.countDown--;
				}
			}
			// If preAction is not finished, tick() it and return result
			else {

				GameLocation preActionResult = this.preAction.tick();

				if (preActionResult != null) {
					this.searchStartingPosition = preActionResult;
				}

				return preActionResult;
			}

		}

		return null;
	}

}
