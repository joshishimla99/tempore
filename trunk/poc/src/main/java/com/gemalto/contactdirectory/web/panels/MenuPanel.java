/**
 * 
 */
package com.gemalto.contactdirectory.web.panels;

import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.PageLink;
import org.apache.wicket.markup.html.panel.Panel;

import com.gemalto.webcare.common.web.panel.WebcarePanel;

/**
 * Menu panel for the application.
 * 
 * @author jarias
 */
public class MenuPanel extends WebcarePanel {

	/**
	 * Default no args constructor.
	 * 
	 * By convention, it uses the id <strong><code>menu</code></strong>.
	 */
	public MenuPanel() {
		this("menu");
	}

	/**
	 * @param id
	 *            identifier
	 */
	public MenuPanel(String id) {
		super(id);
	}

	/**
	 * {@inheritDoc}
	 */
	public void addApplicationDependentComponents() {
		add(new BookmarkablePageLink("logoutLink", getLinkClass("logout")));
	}



}
