package sims.module.actions;

import javax.swing.ImageIcon;

import sims.basics.configurations.ConfigurationManager;
import sims.module.feelings.Feeling;
import sims.module.surface.GameLocation;

public class WatchTV extends Action {

	WatchTV(GameLocation feelingLocation, Walk walker, Feeling bored, ImageIcon icon) {
		super(ActionIdentifier.WatchTV, ConfigurationManager.getPlayerTVTime(), feelingLocation, walker, bored, icon);
	}

	@Override
	public boolean canInterrupt() {
		return false;
	}

}
