package sims.module.actions;

import java.util.LinkedList;

import javax.swing.ImageIcon;

import sims.basics.Action;
import sims.module.surface.GameLocation;

public class Walk implements Action {

	private static ActionIdentifier identifier = ActionIdentifier.Walk;

	private final LinkedList<GameLocation> steps;

	private boolean isActive;

	private final ImageIcon[] icons;
	private int currentPic;

	Walk(ImageIcon... icons) {

		this.steps = new LinkedList<GameLocation>();
		this.currentPic = 0;
		this.icons = icons;
		stop();
	}

	public void addStep(GameLocation step) {

		this.steps.add(step);
	}

	@Override
	public boolean canInterrupt() {
		return true;
	}

	@Override
	public ActionIdentifier getIdentifier() {
		return Walk.identifier;
	}

	@Override
	public ImageIcon getNextImage() {

		if (!isActive()) {
			return null;
		}
		ImageIcon $ = this.icons[this.currentPic];
		this.currentPic++;

		if (this.currentPic == this.icons.length) {
			this.currentPic = 0;
		}
		return $;
	}

	@Override
	public boolean isAction(ActionIdentifier identifier) {
		return Walk.identifier == identifier;
	}

	@Override
	public boolean isActive() {
		return this.isActive;
	}

	@Override
	public boolean isOver() {
		return this.steps.isEmpty();

	}

	@Override
	public void start() {
		this.isActive = true;

	}

	@Override
	public void stop() {

		this.steps.clear();
		this.isActive = false;
	}

	/**
	 * Sets the next step to currentLoction. If there are no more steps, nothing
	 * is done
	 *
	 */
	@Override
	public GameLocation tick() {

		if (isOver()) {

			return null;
		}

		GameLocation newStep = this.steps.removeFirst();

		return newStep;

	}

}
