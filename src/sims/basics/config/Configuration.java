package sims.basics.config;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Configuration {

	private static final String ROOM_BUNDLE_NAME = "sims.basics.config.rooms";
	private static final String GENERAL_BUNDLE_NAME = "sims.basics.config.general";
	private static final String PLAYER_BUNDLE_NAME = "sims.basics.config.players";

	private static final ResourceBundle ROOM_BUNDLE = ResourceBundle.getBundle(ROOM_BUNDLE_NAME);
	private static final ResourceBundle GENERAL_BUNDLE = ResourceBundle.getBundle(GENERAL_BUNDLE_NAME);
	private static final ResourceBundle PLAYER_BUNDLE = ResourceBundle.getBundle(PLAYER_BUNDLE_NAME);

	private static ResourceBundle getBundle(String key) {

		if (key.startsWith("simps.rooms")) {
			return ROOM_BUNDLE;
		}

		if (key.startsWith("simps.players")) {
			return PLAYER_BUNDLE;
		}

		if (key.startsWith("simps.general")) {
			return GENERAL_BUNDLE;
		}

		return null;
	}

	public static int getInt(String key) {
		try {
			return Integer.parseInt(getBundle(key).getString(key));
		} catch (MissingResourceException e) {
			e.printStackTrace();
			return -1;
		}
	}

	public static String getString(String key) {

		try {
			return getBundle(key).getString(key);
		} catch (MissingResourceException e) {
			e.printStackTrace();
			return '!' + key + '!';
		}

	}

}