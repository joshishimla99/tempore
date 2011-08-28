package ar.fi.uba.tempore.gwt.client.panel.configuration;

import com.smartgwt.client.widgets.grid.ListGridRecord;


public class ClientRecord extends ListGridRecord {

	public ClientRecord() {
	}

	public ClientRecord(String Name, String Address, String Country, String State, String Zip, String FiscalNumber, String Phone) {

		setName(Name);
		setAddress(Address);
		setCountry(Country);
		setState(State);
		setZip(Zip);
		setFiscalNumber(FiscalNumber);
		setPhone(Phone);
	}
	
	public void setName(String Name) {
		setAttribute("clientName", Name);
	}
	
	public void setAddress(String Address) {
		setAttribute("clientAddress", Address);
	}
	public void setCountry(String Country) {
		setAttribute("clientCountry", Country);
	}
	public void setState(String State) {
		setAttribute("clientState", State);
	}
	public void setZip(String Zip) {
		setAttribute("clientZip", Zip);
	}
	public void setFiscalNumber(String FiscalNumber) {
		setAttribute("clientFiscalNumber", FiscalNumber);
	}
	public void setPhone(String Phone) {
		setAttribute("clientPhone", Phone);
	}
	
	public String getName() {
		return getAttributeAsString("clientName");
	}
	
	public String getAddress() {
		return getAttributeAsString("clientAddress");
	}
	
	public String getCountry() {
		return getAttributeAsString("clientCountry");
	}
	
	public String getState() {
		return getAttributeAsString("clientState");
	}
	
	public String getZip() {
		return getAttributeAsString("clientZip");
	}
	
	public String getFiscalNumber() {
		return getAttributeAsString("clientFiscalNumber");
	}
	
	public String getPhone() {
		return getAttributeAsString("clientPhone");
	}

}

