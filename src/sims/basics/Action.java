package sims.basics;

import javax.swing.ImageIcon;

import sims.module.actions.ActionIdentifier;
import sims.module.surface.GameLocation;

public interface Action {

	boolean canInterrupt();

	ActionIdentifier getIdentifier();

	ImageIcon getNextImage();

	boolean isAction(ActionIdentifier identifier);

	boolean isActive();

	boolean isOver();

	void start();

	void stop();

	GameLocation tick();
}