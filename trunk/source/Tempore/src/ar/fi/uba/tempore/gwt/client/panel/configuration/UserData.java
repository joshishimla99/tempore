package ar.fi.uba.tempore.gwt.client.panel.configuration;

import java.util.ArrayList;
import java.util.List;

import ar.fi.uba.tempore.dto.UserDTO;

public class UserData {

	private static UserRecord[] records;

	public static UserRecord[] getRecords(List<UserDTO> userList) {
		if (records == null) {
			records = getNewRecords(userList);
		}
		return records;
	}

	public static UserRecord[] getNewRecords(List<UserDTO> userList) {
		UserRecord[] r = new UserRecord[10];
		for (int i = 0; i < userList.size(); i++) {
			UserDTO userDTO = userList.get(i);
			r[i] = new UserRecord(userDTO.getLastName(), 
							userDTO.getName(), 
							userDTO.getCompany(), userDTO.getPhone(), userDTO.getEmail(), 
							userDTO.getCountry(), userDTO.getAddress(),	userDTO.getZipCode(),
							userDTO.getUserName(), userDTO.getPassword(), userDTO.isClient(),
							userDTO.getRole());
		}
		
		return r;
	}
}