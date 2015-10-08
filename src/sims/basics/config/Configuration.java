package sims.basics.config;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Configuration {

	private static final String ROOM_BUNDLE_NAME = "sims.basics.rooms";
	private static final String GENERAL_BUNDLE_NAME = "sims.basics.general";

	private static final ResourceBundle ROOM_BUNDLE = ResourceBundle.getBundle(ROOM_BUNDLE_NAME);
	private static final ResourceBundle GENERAL_BUNDLE = ResourceBundle.getBundle(GENERAL_BUNDLE_NAME);

	public static int getGeneralIntValue(String key) {
		try {
			return Integer.parseInt(GENERAL_BUNDLE.getString(key));
		} catch (MissingResourceException e) {
			e.printStackTrace();
			return -1;
		}
	}

	public static String getGeneralStringValue(String key) {
		try {
			return GENERAL_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			e.printStackTrace();
			return '!' + key + '!';
		}
	}

	public static int getRoomIntValue(String key) {
		try {
			return Integer.parseInt(ROOM_BUNDLE.getString(key));
		} catch (MissingResourceException e) {
			e.printStackTrace();
			return -1;
		}
	}

	public static String getRoomStringValue(String key) {
		try {
			return ROOM_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			e.printStackTrace();
			return '!' + key + '!';
		}
	}

}
