package sims.module.actions;

public class Hide extends Action {

	Hide() {
		super(ActionIdentifier.Hide);
	}

	@Override
	public boolean isOver() {
		return false;
	}
}
