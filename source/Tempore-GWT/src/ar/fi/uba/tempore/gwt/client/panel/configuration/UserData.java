package ar.fi.uba.tempore.gwt.client.panel.configuration;

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
		for (int i = 0; i < userList.size(); i++) {
			records[i] = new UserRecord(userList.get(i).getLastName(), userList.get(i).getName(), 
					userList.get(i).getCompany(), userList.get(i).getPhone(), userList.get(i).getEmail(), 
					userList.get(i).getCountry(), userList.get(i).getAddress(),	userList.get(i).getZipCode(),
					userList.get(i).getUserName(), userList.get(i).getPassword(), userList.get(i).isClient(),
					userList.get(i).getRole());
		}
		return records;
	}
}