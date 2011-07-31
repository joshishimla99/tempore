package ar.fi.uba.tempore.gwt.client.panel.configuration;

import com.smartgwt.client.widgets.grid.ListGridRecord;

public class UserRecord extends ListGridRecord {

	public UserRecord() {
	}

	public UserRecord(String userLastName, String userName, String capital,
			String company) {
		setCompany(company);
		setUserName(userName);
		setPassword(capital);
		setUserLastName(userLastName);
	}

	public UserRecord(String userLastName, String userName, String email) {
		setUserLastName(userLastName);
		setUserName(userName);
		setEmail(email);
	}

	public UserRecord(String userLastName, String userName, String company,
			String phone, String email, String country, String address,
			String zipCode, String user, String password, boolean client,
			String role) {

		setUserName(userName);
		setUserLastName(userLastName);
		setCompany(company);
		setPhone(phone);
		setEmail(email);
		setCountry(country);
		setAddress(address);
		setZipCode(zipCode);
		setUser(user);
		setPassword(password);
		setClient(client);
		setRole(role);
	}

	public void setUserLastName(String userLastName) {
		setAttribute("userLastName", userLastName);
	}

	public String getUserLastName() {
		return getAttributeAsString("userLastName");
	}

	public void setUserName(String userName) {
		setAttribute("userName", userName);
	}

	public String getUserName() {
		return getAttributeAsString("userName");
	}

	public void setCompany(String company) {
		setAttribute("company", company);
	}

	public String getCompany() {
		return getAttributeAsString("company");
	}

	public void setPhone(String phone) {
		setAttribute("phone", phone);
	}

	public String getPhone() {
		return getAttributeAsString("phone");
	}

	public void setEmail(String email) {
		setAttribute("email", email);
	}

	public String getEmail() {
		return getAttributeAsString("email");
	}

	public void setCountry(String country) {
		setAttribute("country", country);
	}

	public String getCountry() {
		return getAttributeAsString("country");
	}

	public void setAddress(String address) {
		setAttribute("address", address);
	}

	public String getAddress() {
		return getAttributeAsString("address");
	}

	public void setZipCode(String zipCode) {
		setAttribute("zipCode", zipCode);
	}

	public String getzipCode() {
		return getAttributeAsString("zipCode");
	}

	public void setUser(String user) {
		setAttribute("user", user);
	}

	public String getUser() {
		return getAttributeAsString("user");
	}

	public void setPassword(String password) {
		setAttribute("password", password);
	}

	public String getPassword() {
		return getAttributeAsString("password");
	}

	public void setClient(boolean client) {
		setAttribute("client", client);
	}

	public boolean getClient() {
		return getAttributeAsBoolean("client");
	}

	public void setRole(String role) {
		setAttribute("role", role);
	}

	public String getRole() {
		return getAttributeAsString("role");
	}

	public String getFieldValue(String field) {
		return getAttributeAsString(field);
	}
}
