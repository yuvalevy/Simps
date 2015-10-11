package sims.module.feelings;

import sims.basics.config.ConfigurationManager;

class Bored extends Feeling {

	Bored(int capability) {
		super(capability);

	}
}

class Have2Pee extends Feeling {

	Have2Pee(int capability) {
		super(capability);

	}
}

class Hungry extends Feeling {

	Hungry(int capability) {
		super(capability);

	}
}

class Tired extends Feeling {

	Tired(int capability) {
		super(capability);

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