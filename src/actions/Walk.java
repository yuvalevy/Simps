package actions;

import java.util.LinkedList;

import javax.swing.ImageIcon;

import sims.basics.Action;
import sims.module.surface.GameLocation;

public class Walk implements Action {

	private final LinkedList<GameLocation> steps;

	private boolean isActive;

	private ImageIcon[] icons;
	private int currentPic;

	public Walk() {

		this.steps = new LinkedList<GameLocation>();
		this.currentPic = 0;
	}

	public Walk(ImageIcon... icons) {
		this();
		this.icons = icons;
	}

	public void addStep(GameLocation step) {

		this.steps.add(step);
	}

	/**
	 * Sets the next step to currentLoction. If there are no more steps, nothing
	 * is done
	 *
	 */
	@Override
	public GameLocation execute() {

		start();

		if (this.steps.size() == 0) {
			return null;
		}

		GameLocation newStep = this.steps.removeFirst();

		return newStep;

	}

	@Override
	public ImageIcon getNextImage() {

		ImageIcon $ = this.icons[this.currentPic];
		this.currentPic++;

		if (this.currentPic == this.icons.length) {
			this.currentPic = 0;
		}
		return $;
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
		return this.steps.size() == 0;
	}

	private void start() {
		this.isActive = true;

	}

	private void stop() {
		this.isActive = false;
	}

}
