package ar.fi.uba.tempore.gwt.client.panel.configuration;

import com.smartgwt.client.widgets.grid.ListGridRecord;


public class ClientRecord extends ListGridRecord {

	public ClientRecord() {
	}

	public ClientRecord(String Name, String Address, String Country, String State, String Zip, String FiscalNumber, String Phone) {

		setClientName(Name);
		setClientAddress(Address);
		setClientCountry(Country);
		setClientState(State);
		setClientZip(Zip);
		setClientFiscalNumber(FiscalNumber);
		setClientPhone(Phone);
	}
	
	public void setClientName(String Name) {
		setAttribute("clientName", Name);
	}
	
	public void setClientAddress(String Address) {
		setAttribute("clientAddress", Address);
	}
	public void setClientCountry(String Country) {
		setAttribute("clientCountry", Country);
	}
	public void setClientState(String State) {
		setAttribute("clientState", State);
	}
	public void setClientZip(String Zip) {
		setAttribute("clientZip", Zip);
	}
	public void setClientFiscalNumber(String FiscalNumber) {
		setAttribute("clientFiscalNumber", FiscalNumber);
	}
	public void setClientPhone(String Phone) {
		setAttribute("clientPhone", Phone);
	}

}

