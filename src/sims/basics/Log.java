package sims.basics;

public class Log {

	private static LogLevel logLevel;

	public static LogLevel getLogLevel() {
		return logLevel;
	}

	public static void setLogLevel(LogLevel level) {
		Log.logLevel = level;
	}

	/**
	 * Writes log in LogLevel.Debug mode
	 *
	 * @param message
	 */
	public static void WriteLineLog(String message) {

		Log.WriteLineLog(message, LogLevel.Debug);

	}

	/**
	 *
	 * @param message
	 * @param level
	 */
	public static void WriteLineLog(String message, LogLevel level) {

		if (level == LogLevel.Debug) {

			System.out.println(message);

		} else if (level == logLevel) {

			System.err.println(message);

		}

	}

	/**
	 * Writes log in LogLevel.Debug mode
	 *
	 * @param message
	 */
	public static void WriteLog(String message) {

		Log.WriteLog(message, LogLevel.Debug);

	}

	/**
	 *
	 * @param message
	 * @param level
	 */
	public static void WriteLog(String message, LogLevel level) {

		if (level == LogLevel.Debug) {

			System.out.print(message);

		} else if (level == logLevel) {

			System.err.print(message);

		}

	}

}
