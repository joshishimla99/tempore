package ar.fi.uba.tempore.gwt.client;

import ar.fi.uba.tempore.dto.TempCounterDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;


public interface TempCounterServicesClientAsync {
	void getActualState(Integer userId, AsyncCallback<TempCounterDTO> callback);

	void start(Integer userId, Integer taskId, AsyncCallback<TempCounterDTO> callback);

	void pause(Integer userId, AsyncCallback<TempCounterDTO> callback);

	void save(Integer userId, AsyncCallback<Long> callback);

	void cancel(Integer userId, AsyncCallback<Long> callback);
}
