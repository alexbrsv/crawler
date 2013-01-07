package com.visualmeta.crawler.logging;

/**
 * A Logger object is used to log messages for a specific system or application
 * component.
 * <p>
 * Works as a wrapper around standard {@link java.util.logging.Logger} to
 * provide printf-style strings formatting. 
 * <br>
 * This form avoids superfluous object creation when the logger for the
 * specified level is disabled.
 * 
 * @author Alex Borisov
 * 
 */
public interface Logger {

	/**
	 * Log a SEVERE message.
	 */
	void severe(String msg, Object... arguments);

	/**
	 * Log a WARNING message.
	 */
	public void warning(String msg, Object... arguments);

	/**
	 * Log an INFO message.
	 */
	public void info(String msg, Object... arguments);

	/**
	 * Log a CONFIG message.
	 */
	public void config(String msg, Object... arguments);

	/**
	 * Log a FINE message.
	 */
	public void fine(String msg, Object... arguments);

	/**
	 * Log a FINER message.
	 */
	public void finer(String msg, Object... arguments);

	/**
	 * Log a FINEST message.
	 */
	public void finest(String msg, Object... arguments);
}
