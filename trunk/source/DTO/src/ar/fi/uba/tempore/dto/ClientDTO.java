package ar.fi.uba.tempore.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ClientDTO implements IsSerializable{

	private Integer id;
	private String clientName;
	private String clientAddress;
	private String clientCountry;
	private String clientState;
	private String clientZip;
	private String clientFiscalNumber;
	private String clientPhone;

	public void setName(String clientName) {
		this.clientName = clientName;
	}
	
	public void setAddress(String clientAddress) {
		this.clientAddress = clientAddress;
	}
	
	public void setCountry(String clientCountry) {
		this.clientCountry = clientCountry;
	}
	
	public void setState(String clientState) {
		this.clientState = clientState;
	}
	
	public void setZip(String clientZip) {
		this.clientZip = clientZip;
	}
	
	public void setFiscalNumber(String clientFiscalNumber) {
		this.clientFiscalNumber = clientFiscalNumber;
	}
	
	public void setPhone(String clientPhone) {
		this.clientPhone = clientPhone;
	}
	
	public String getName() {
		return clientName;
	}
	
	public String getAddress() {
		return clientAddress;
	}
	
	public String getCountry() {
		return clientCountry;
	}
	
	public String getState() {
		return clientState;
	}
	
	public String getZip() {
		return clientZip;
	}
	
	public String getFiscalNumber() {
		return clientFiscalNumber;
	}
	
	public String getPhone() {
		return clientPhone;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
