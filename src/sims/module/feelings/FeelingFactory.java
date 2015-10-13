package sims.module.feelings;

import sims.basics.config.ConfigurationManager;

class Bored extends Feeling {

	static String name = "Bored";

	Bored(int capability) {
		super("Bored", capability);

	}
}

class Have2Pee extends Feeling {

	static String name = "Have2Pee";

	Have2Pee(int capability) {
		super("Have2Pee", capability);

	}
}

class Hungry extends Feeling {

	static String name = "Hungry";

	Hungry(int capability) {
		super("Hungry", capability);

	}
}

class Tired extends Feeling {

	static String name = "Tired";

	Tired(int capability) {
		super(name, capability);

	}
}

public class FeelingFactory {

	public static Bored getBored() {
		return new Bored(ConfigurationManager.getBoredCapability());
	}

	/**
	 * Returns the number of feelings existing in the package
	 *
	 * @return
	 */
	public static int getExisingFeelingCount() {
		return 4;
	}

	public static String[] getExisingFeelingsNames() {

		String[] feelingsNames = new String[getExisingFeelingCount()];

		feelingsNames[0] = Bored.name;
		feelingsNames[1] = Have2Pee.name;
		feelingsNames[2] = Hungry.name;
		feelingsNames[3] = Tired.name;

		return feelingsNames;
	}

	public static Have2Pee getHave2Pee() {
		return new Have2Pee(ConfigurationManager.getHave2PeeCapability());
	}

	public static Hungry getHungry() {
		return new Hungry(ConfigurationManager.getHungryCapability());
	}

	public static int getIncreacingAmount() {

		return ConfigurationManager.getFeelingsIncreacingAmount();
	}

	public static Tired getTired() {
		return new Tired(ConfigurationManager.getTiredCapability());
	}
}