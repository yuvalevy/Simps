package sims.module.feelings;

public class Feeling {

	private final int capability;
	private int suffered;
	private final String name;

	Feeling(String feelingName, int capability) {
		this.name = feelingName;
		this.capability = capability;
		initialSuffering();
	}

	public String getName() {
		return this.name;
	}

	public void increaceSuffering(int suffringAmount) {
		this.suffered += suffringAmount;
	}

	public void initialSuffering() {
		this.suffered = 0;
	}

	public boolean isDangerous() {
		return ((double) this.suffered / this.capability) >= 0.1;
	}

	public boolean isPlayerSufferedEnough() {
		return this.suffered >= this.capability;
	}

	@Override
	public String toString() {
		return this.suffered + " / " + this.capability;
	}

}
