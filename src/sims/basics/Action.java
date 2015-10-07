package sims.basics;

import javax.swing.ImageIcon;

import sims.module.surface.GameLocation;

public interface Action {

	GameLocation execute();

	ImageIcon getNextImage();

	boolean interupt();

	boolean isActive();

	boolean isOver();
}