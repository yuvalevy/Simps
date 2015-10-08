package sims.module.actions;

import javax.swing.ImageIcon;

import sims.basics.Action;
import sims.basics.config.ConfigurationManager;
import sims.module.surface.GameLocation;

public class Search implements Action {

	private static final ActionIdentifier identifier = ActionIdentifier.Search;
	private final GameLocation startingPosition;
	private final ImageIcon icon;
	private final int searchTime;
	private int countDown;
	private boolean isActive;

	Search(GameLocation startingPosition, ImageIcon icons) {

		this.startingPosition = startingPosition;
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

		return this.icon;
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
	}

	@Override
	public void stop() {
		this.isActive = false;
		this.countDown = 0;

	}

	@Override
	public GameLocation tick() {

		if (isActive()) {

			if (this.countDown == 0) {
				return this.startingPosition;
			} else {
				this.countDown--;
			}
		}

		return null;
	}

}
