package com.visualmeta.crawler.task;

import java.io.InputStream;
import java.net.URL;

/**
 * Interface of page content handler.
 * <br>
 * Implementation should provide a strategy 
 * how to handle a web page.
 * <br>
 * Crawler calls {@link #handle(String, InputStream)} method after
 * it extracts content reader for a URL.
 * 
 * @author Alex Borisov
 *
 */
public interface PageHandler {

	public void handle(URL pageUrl, InputStream contentStream);
	
}