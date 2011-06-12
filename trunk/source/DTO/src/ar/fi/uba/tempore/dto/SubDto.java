package ar.fi.uba.tempore.dto;

import com.google.gwt.user.client.rpc.IsSerializable;


public class SubDto implements IsSerializable {
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
}
