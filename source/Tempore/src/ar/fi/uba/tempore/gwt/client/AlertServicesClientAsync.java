package ar.fi.uba.tempore.gwt.client;

import java.util.List;

import ar.fi.uba.tempore.dto.AlertDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface AlertServicesClientAsync {

	void getAlerts(AsyncCallback<List<AlertDTO>> callback);

	void updateSaveAlert(AlertDTO alertDTO, AsyncCallback<AlertDTO> callback);
	
}
