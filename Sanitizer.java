package net.cine.app.util;

import java.util.List;

import org.owasp.html.ElementPolicy;
import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;

public final class Sanitizer {

	public static final String HTML_VALUE = "html";
	public static final String LINK_VALUE = "link";
	
	public static String html(String value) {
		PolicyFactory sanitizer = Sanitizers.FORMATTING.and(Sanitizers.BLOCKS);
		return sanitizer.sanitize(value);
	}

	public static String customHtmlLinks(String value) {
		PolicyFactory linkRewrite = new HtmlPolicyBuilder().allowAttributes("href").onElements("a")
				.requireRelNofollowOnLinks().allowElements(new ElementPolicy() {
					public String apply(String elementName, List<String> attrs) {
						attrs.add("target");
						attrs.add("_blank");
						return "a";
					}
				}, "a").toFactory();
		return linkRewrite.sanitize(value);
	}

	public static String link(String value) {
		PolicyFactory sanitizer = Sanitizers.FORMATTING.and(Sanitizers.LINKS);
		return sanitizer.sanitize(value);
	}
}
