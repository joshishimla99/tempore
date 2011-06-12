package ar.fi.uba.tempore.gwt.server;

import ar.fi.uba.tempore.dto.DemoDto;
import ar.fi.uba.tempore.gwt.client.DemoService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class DemoServiceImpl extends RemoteServiceServlet implements
		DemoService {

	public String callServer(String input, DemoDto demoDto) throws IllegalArgumentException {
		System.err.println(demoDto.toString());
		String serverInfo = getServletContext().getServerInfo();
		String userAgent = getThreadLocalRequest().getHeader("User-Agent");

		// Escape data from the client to avoid cross-site script vulnerabilities.
		input = escapeHtml(input);
		userAgent = escapeHtml(userAgent);

		return "Hello, " + input + "!<br><br>I am running " + serverInfo
				+ ".<br><br>It looks like you are using:<br>" + userAgent +
				"<br><br> <b>La serializacion de datos funciona:</b><br>" + escapeHtml(demoDto.toString());
	}

	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html the html string to escape
	 * @return the escaped string
	 */
	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;").replaceAll("\\n", "<br>");
	}
}
