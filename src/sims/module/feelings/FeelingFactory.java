package sims.module.feelings;

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

	public static Bored getBored(int capability) {
		return new Bored(capability);
	}

	public static Have2Pee getHave2Pee(int capability) {
		return new Have2Pee(capability);
	}

	public static Hungry getHungry(int capability) {
		return new Hungry(capability);
	}

	public static Tired getTired(int capability) {
		return new Tired(capability);
	}
}