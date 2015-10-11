package sims.module.feelings;

public class Feeling {

	private final int capability;
	private int suffered;

	Feeling(int capability) {
		this.capability = capability;
		this.suffered = 0;
	}

	public void increaceSuffering(int suffringAmount) {
		this.suffered += suffringAmount;
	}

	public void initialFeeling() {
		this.suffered = 0;
	}

	public boolean isPlayerSufferedEnough() {
		return this.suffered >= this.capability;
	}
}
