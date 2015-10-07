package actions;

import javax.swing.ImageIcon;

import sims.basics.Action;
import sims.module.surface.GameLocation;

public class Nothing implements Action {

	private boolean isActive;
	private final ImageIcon defaultPic;

	public Nothing() {
		this.defaultPic = null;
	}

	public Nothing(ImageIcon defaultObjectPic) {
		this.defaultPic = defaultObjectPic;
	}

	@Override
	public GameLocation execute() {

		start();

		return null;
	}

	@Override
	public ImageIcon getNextImage() {
		return this.defaultPic;
	}

	@Override
	public boolean interupt() {
		stop();
		return true;
	}

	@Override
	public boolean isActive() {
		return this.isActive;
	}

	@Override
	public boolean isOver() {
		return this.isActive;
	}

	private void start() {
		this.isActive = true;
	}

	private void stop() {
		this.isActive = false;
	}

}
