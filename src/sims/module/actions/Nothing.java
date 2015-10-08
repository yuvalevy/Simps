package sims.module.actions;

import javax.swing.ImageIcon;

import sims.basics.Action;
import sims.module.surface.GameLocation;

public class Nothing implements Action {

	private static ActionIdentifier identifier = ActionIdentifier.Nothing;
	private boolean isActive;
	private final ImageIcon defaultPic;

	Nothing(ImageIcon defaultObjectPic) {
		this.defaultPic = defaultObjectPic;
	}

	@Override
	public ActionIdentifier getIdentifier() {
		return Nothing.identifier;
	}

	@Override
	public ImageIcon getNextImage() {
		return this.defaultPic;
	}

	@Override
	public boolean canInterrupt() {
		return true;
	}

	@Override
	public boolean isAction(ActionIdentifier identifier) {
		return Nothing.identifier == identifier;
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
