
package com.gemalto.contactdirectory.web.pages.login;

import org.apache.wicket.Component;
import org.apache.wicket.PageParameters;
import org.apache.wicket.model.IModel;

public class LoginSelfCare extends Login {

	public LoginSelfCare(PageParameters parameters) {
		super(parameters);
	}

	protected Component getSignInForm(IModel model) {
		return new SigninSelfCareForm(model);
	}

	private boolean isUsernameValid(String username) {
		return true;
	}

	private class SigninSelfCareForm extends SignInForm {
		public SigninSelfCareForm(IModel model) {
			super(model);
		}

		protected void onSubmit() {
			String username = getUsername();
			if (isUsernameValid(username)) {
				super.onSubmit();
			}
		}
	}

}
