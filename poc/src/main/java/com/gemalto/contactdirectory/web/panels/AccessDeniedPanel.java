/**
 * 
 */
package com.gemalto.contactdirectory.web.panels;

import org.apache.wicket.markup.html.link.BookmarkablePageLink;

import com.gemalto.webcare.common.web.panel.WebcarePanel;

/**
 * Access denied panel.
 * 
 * @author jarias
 */
public class AccessDeniedPanel extends WebcarePanel {

	/**
	 * @param id
	 *            panel identifier
	 */
	public AccessDeniedPanel(String id) {
		super(id);
	}

	/**
	 * {@inheritDoc}
	 */
	public void addApplicationDependentComponents() {
		add(new BookmarkablePageLink("login", getLinkClass("logout")));
	}
}
