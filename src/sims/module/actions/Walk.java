package sims.module.actions;

import java.util.LinkedList;

import javax.swing.ImageIcon;

import sims.module.surface.GameLocation;

public class Walk extends Action {

	private final LinkedList<GameLocation> steps;

	Walk(ImageIcon... icons) {
		super(ActionIdentifier.Walk, icons);

		this.steps = new LinkedList<GameLocation>();
	}

	public void addStep(GameLocation step) {

		this.steps.add(step);
	}

	@Override
	public boolean isOver() {
		return this.steps.isEmpty();

	}

	@Override
	public void stop() {
		super.stop();
		this.steps.clear();

	}

	/**
	 * Sets the next step to currentLoction. If there are no more steps, nothing
	 * is done
	 *
	 */
	@Override
	public GameLocation tick() {

		if (isOver()) {

			return super.tick();
		}

		GameLocation newStep = this.steps.removeFirst();

		return newStep;

	}

}
