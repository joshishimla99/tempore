package com.gemalto.contactdirectory.web.panels;

import com.gemalto.webcare.common.web.panel.WebcarePanel;


/**
 * Home panel.
 * 
 * Contains links to main application's functionalities.
 * 
 */
public class BigIconsPanel extends WebcarePanel {

	/**
	 * @param id
	 *            panel identifier
	 */
	public BigIconsPanel(String id) {
		super(id);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void addApplicationDependentComponents() {
		super.addApplicationDependentComponents();
		add(createAuthPageLink("backupConfirmation"));
		add(createAuthPageLink("restoreConfirmation"));
		add(createAuthPageLink("synchronizeConfirmation"));
		add(createAuthPageLink("manageContacts"));	
	}
	
}
