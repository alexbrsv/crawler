package com.visualmeta.crawler.task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML.Attribute;
import javax.swing.text.html.HTML.Tag;
import javax.swing.text.html.HTMLEditorKit.ParserCallback;
import javax.swing.text.html.parser.ParserDelegator;

import com.visualmeta.crawler.logging.Logger;
import com.visualmeta.crawler.logging.LoggerFactory;

/**
 * {@link PageHandler} which extracts all the links from a page and put all
 * links to a queue for further crawling
 * 
 * @author Alex Borisov
 * 
 */
public class ExtractLinksHandler implements PageHandler {

	private static Logger logger = LoggerFactory
			.getLogger(ExtractLinksHandler.class);

	private final Queue<URL> linkQueue;

	public ExtractLinksHandler(Queue<URL> linkQueue) {
		this.linkQueue = linkQueue;
	}

	@Override
	public void handle(URL pageUrl, InputStream contentStream) {
		logger.finer("Started reading content from %s", pageUrl);

		Reader contentReader = new BufferedReader(new InputStreamReader(
				contentStream));

		List<URL> links;
		try {
			links = extractLinks(pageUrl, contentReader);
			logger.finer("Links extracted from from %s: %s", pageUrl, links);
		} catch (IOException e) {
			logger.severe("Exception on links extraction from %s: %s", pageUrl,
					e);
			return;
		}

		if (!links.isEmpty()) {
			linkQueue.addAll(links);
		}
	}

	/**
	 * Extracts all the links from a page.
	 * 
	 * Implementation notes: It's probably not the best idea to use
	 * javax.swing.* classes in not UI-related code, but given a restriction to
	 * use only JDK-provided means and in order not to reinvent the wheel
	 * {@link javax.swing.text.html.parser.ParserDelegator} is used in this
	 * method.
	 */
	private List<URL> extractLinks(final URL url, Reader reader)
			throws IOException {
		final List<URL> links = new LinkedList<URL>();
		ParserDelegator parserDelegator = new ParserDelegator();

		ParserCallback parserCallback = new ParserCallback() {

			@Override
			public void handleStartTag(Tag tag, MutableAttributeSet attribute,
					int pos) {
				if (Tag.A == tag) {
					Object href = attribute.getAttribute(Attribute.HREF);
					if (href instanceof String) {
						addUrlLink(url, (String) href, links);
					}
				}
			}
		};
		parserDelegator.parse(reader, parserCallback, true);
		return links;
	}

	/**
	 * Adds a linkUrl to a list of links if it's correct URL link.
	 */
	private void addUrlLink(URL pageUrl, String linkUrl, List<URL> links) {
		try {
			URL link = new URL(linkUrl);
			links.add(link);
			logger.finest("Page %s: found link %s", pageUrl, linkUrl);
		} catch (MalformedURLException e) {
			logger.finest("Page %s: url won't be processed %s", pageUrl,
					linkUrl);
		}
	}

}