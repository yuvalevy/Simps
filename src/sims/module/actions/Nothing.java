package sims.module.actions;

import javax.swing.ImageIcon;

public class Nothing extends Action {

	Nothing(ImageIcon defaultObjectPic) {
		super(ActionIdentifier.Nothing, defaultObjectPic);
	}

	@Override
	public boolean isOver() {
		return false;
	}
}
