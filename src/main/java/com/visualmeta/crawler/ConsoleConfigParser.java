package com.visualmeta.crawler;

/**
 * Simple crawler config parser from console arguments.
 * 
 * @author Alex Borisov
 *
 */
public class ConsoleConfigParser {

	private final static int MIN_ARGS_NUMBER = 1;
	private final static int DEFAULT_URLS_LIMIT = 1000;
	private final static int DEFAULT_PROCESSORS_MULTIPILER = 2;
	
	private final static int ARG_URL = 0;
	private final static int ARG_THREADS = 1;
	private final static int ARG_LIMIT = 2;
	
	CrawlerConfig parse(String... args) {
		
		if (args == null || args.length < MIN_ARGS_NUMBER) {
			printUsage(null);
			return null;
		}
		
		String url = args[ARG_URL];
		if (url  == null) {
			printUsage("%nurl cannot be null.%n");
			return null;			
		}
		
		int threadsNumber;		
		if (args.length > ARG_THREADS) {
			
			try {
				threadsNumber = Integer.valueOf(args[ARG_THREADS]);
			} catch (NumberFormatException ex) {				
				printUsage("%nInvalid threadsNumber " + args[ARG_THREADS] + "%n");
				return null;
			}
		} else {
			threadsNumber = getDefaultThreadsNumber();
		}
		
		int urlsLimit;
		if (args.length > ARG_LIMIT) {
			try {
				urlsLimit = Integer.valueOf(args[ARG_LIMIT]);
			} catch (NumberFormatException ex) {				
				printUsage("%nInvalid urlsLimit " + args[ARG_LIMIT] + "%n");
				return null;
			}				
		} else {
			urlsLimit = DEFAULT_URLS_LIMIT;
		}
		
		return new CrawlerConfig(url, threadsNumber, urlsLimit);
	}
	
	private void printUsage(String suffix) {
		String usage = "Usage:%nCrawler url [threadsNumber urlsLimit]%n" +
			"where%n\turl - start URL,%n\tthreadsNumber - number of threads " +
			"(optional, default value for this computer is %s)," + 
			"%n\turlsLimit - number of URLs crawler should stop after " +
			"(optional, default value is %s).%n%n" +
			"Example: \"Crawler http://www.visual-meta.com 10 1000\"%n" + 
			(suffix != null? suffix : "");	
		System.out.printf(usage, getDefaultThreadsNumber(),
			DEFAULT_URLS_LIMIT);
	}
	
	private int getDefaultThreadsNumber() {
		return Runtime.getRuntime().availableProcessors() * 
			DEFAULT_PROCESSORS_MULTIPILER;
	}
}
