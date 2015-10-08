package sims.basics;

import javax.swing.ImageIcon;

import sims.module.actions.ActionIdentifier;
import sims.module.surface.GameLocation;

public interface Action {

	ActionIdentifier getIdentifier();

	ImageIcon getNextImage();

	boolean interupt();

	boolean isAction(ActionIdentifier identifier);

	boolean isActive();

	boolean isOver();

	GameLocation tick();
}