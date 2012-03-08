package ar.fi.uba.tempore.gwt.server.util;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

public class Util {
	
	@SuppressWarnings("rawtypes")
	public static HashMap getHashMapFromSession(String key, HttpSession session) {
		// If the session does not contain anything, create a new HashMap
		if (session.getAttribute(key) == null) {
			session.setAttribute(key, new HashMap());
		}

		// Return the HashMap
		return (HashMap) session.getAttribute(key);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void addSuccessfulQueryToSession(String key, HttpSession session, String k, String value) {
		HashMap mapOfResults = Util.getHashMapFromSession(key, session);
		mapOfResults.put(k, value);
	}
}
