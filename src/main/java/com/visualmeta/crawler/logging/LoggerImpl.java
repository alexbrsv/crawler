package com.visualmeta.crawler.logging;

import java.util.logging.Level;

/**
 * {@link Logger} implementation, a wrapper around standard
 * {@link java.util.logging.Logger}
 * 
 * @author Alex Borisov
 * 
 */
public class LoggerImpl implements Logger {

	private final java.util.logging.Logger delegatee;

	LoggerImpl(String name) {
		this(java.util.logging.Logger.getLogger(name));
	}

	LoggerImpl(java.util.logging.Logger delegatee) {
		this.delegatee = delegatee;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.visualmeta.crawler.logging.Logger#severe(java.lang.String,
	 * java.lang.String[])
	 */
	@Override
	public void severe(String msg, Object... arguments) {
		log(Level.SEVERE, msg, arguments);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.visualmeta.crawler.logging.Logger#warning(java.lang.String,
	 * java.lang.String[])
	 */
	@Override
	public void warning(String msg, Object... arguments) {
		log(Level.WARNING, msg, arguments);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.visualmeta.crawler.logging.Logger#info(java.lang.String,
	 * java.lang.String[])
	 */
	@Override
	public void info(String msg, Object... arguments) {
		log(Level.INFO, msg, arguments);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.visualmeta.crawler.logging.Logger#config(java.lang.String,
	 * java.lang.String[])
	 */
	@Override
	public void config(String msg, Object... arguments) {
		log(Level.CONFIG, msg, arguments);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.visualmeta.crawler.logging.Logger#fine(java.lang.String,
	 * java.lang.String[])
	 */
	@Override
	public void fine(String msg, Object... arguments) {
		log(Level.FINE, msg, arguments);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.visualmeta.crawler.logging.Logger#finer(java.lang.String,
	 * java.lang.String[])
	 */
	@Override
	public void finer(String msg, Object... arguments) {
		log(Level.FINER, msg, arguments);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.visualmeta.crawler.logging.Logger#finest(java.lang.String,
	 * java.lang.String[])
	 */
	@Override
	public void finest(String msg, Object... arguments) {
		log(Level.FINEST, msg, arguments);
	}

	private void log(Level level, String msg, Object... arguments) {
		if (!delegatee.isLoggable(level)) {
			return;
		}
		
		String message;
		if (arguments.length > 0) {
			message = String.format(msg, escapeArguments(arguments));
		} else {
			message = msg;
		}
		
		delegatee.logp(level, delegatee.getName(), null, message);
	}
	
	/**
	 * Replaces '%' sign with '%%' to use in
	 * {@link java.util.Formatter#format(String, Object...))} 
	 */
	private Object[] escapeArguments(Object... args) {
		Object[] result = new String[args.length];
		
		for (int i = 0; i < result.length; i++) {
			if (args[i] != null) {
				result[i] = args[i].toString().replaceAll("%", "%%");	
			}
		}
		return result;
	}

}
