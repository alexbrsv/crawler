package com.visualmeta.crawler;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests {@link ConsoleConfigParser} and {@link CrawlerConfig} classes
 * 
 * @author Alex Borisov
 * 
 */
public class ConsoleConfigParserTest {

	private ConsoleConfigParser parser = new ConsoleConfigParser();

	@Test
	public void noArgumentsConfig() {
		CrawlerConfig config = parser.parse();
		Assert.assertNull(config);
	}

	@Test
	public void nullArgumentsConfig() {
		CrawlerConfig config = parser.parse((String[]) null);
		Assert.assertNull(config);
	}

	@Test
	public void nullUrlConfig() {
		CrawlerConfig config = parser.parse((String) null);
		Assert.assertNull(config);
	}

	@Test
	public void parseUrlConfig() {
		String url = "http:\\example.com";
		CrawlerConfig config = parser.parse(url);
		Assert.assertNotNull(config);
		Assert.assertEquals(url, config.getUrl());
		Assert.assertEquals(2 * Runtime.getRuntime().availableProcessors(),
				config.getThreadsNumber());
		Assert.assertEquals(1000, config.getUrlsLimit());
	}

	@Test
	public void parseAllArgumentsConfig() {
		String url = "http:\\google.com";
		String threads = "8";
		String limit = "2000";
		CrawlerConfig config = parser.parse(url, threads, limit);
		Assert.assertNotNull(config);
		Assert.assertEquals(url, config.getUrl());
		Assert.assertEquals(8, config.getThreadsNumber());
		Assert.assertEquals(2000, config.getUrlsLimit());
	}
	
	@Test
	public void illegalThreadsNumber() {
		String url = "http:\\example.com";
		String threads = "illegal";
		CrawlerConfig config = parser.parse(url, threads);
		Assert.assertNull(config);
	}
	
	@Test
	public void illegalUrlsLimit() {
		String url = "http:\\example.com";
		String threads = "4";
		String limit = "Not_a_Number";
		CrawlerConfig config = parser.parse(url, threads, limit);
		Assert.assertNull(config);
	}	
}
