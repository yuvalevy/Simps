package sims.module.actions;

import javax.swing.ImageIcon;

import sims.basics.configurations.ConfigurationManager;

public class Search extends Action {

	Search(Action preAction, ImageIcon icon) {
		super(ActionIdentifier.Search, ConfigurationManager.getPlayerSearchTime(), preAction, icon);

		stop();
	}

	@Override
	public boolean canInterrupt() {
		return false;
	}

}
