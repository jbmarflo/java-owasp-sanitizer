package net.cine.app.util;

import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;

public final class Sanitizer {
	
	public static String html(String value) {
		PolicyFactory sanitizer = Sanitizers.FORMATTING.and(Sanitizers.BLOCKS);
		return sanitizer.sanitize(value);
	}
	
	public static String link(String value) {
		PolicyFactory sanitizer = Sanitizers.FORMATTING.and(Sanitizers.LINKS);
		return sanitizer.sanitize(value);
	}
}
