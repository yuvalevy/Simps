package sims.module.actions;

import javax.swing.ImageIcon;

import sims.basics.Action;
import sims.module.surface.GameLocation;

public class Hide implements Action {

	private static ActionIdentifier identifier = ActionIdentifier.Hide;
	private boolean isActive;

	Hide() {
	}

	@Override
	public ActionIdentifier getIdentifier() {

		return identifier;
	}

	@Override
	public ImageIcon getNextImage() {
		return null;
	}

	@Override
	public boolean canInterupt() {
		return true;
	}

	@Override
	public boolean isAction(ActionIdentifier identifier) {
		return Hide.identifier == identifier;
	}

	@Override
	public boolean isActive() {
		return this.isActive;
	}

	@Override
	public boolean isOver() {
		return false;
	}

	@Override
	public void start() {
		this.isActive = true;
	}

	@Override
	public void stop() {
		this.isActive = false;
	}

	@Override
	public GameLocation tick() {
		return null;
	}

}
