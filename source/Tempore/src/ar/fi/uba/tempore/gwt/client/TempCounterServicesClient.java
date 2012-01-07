package ar.fi.uba.tempore.gwt.client;

import ar.fi.uba.tempore.dto.TempCounterDTO;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("TempCounterServicesClient")
public interface TempCounterServicesClient  extends RemoteService{
	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
	public static class Util {
		private static TempCounterServicesClientAsync instance;
		public static TempCounterServicesClientAsync getInstance(){
			if (instance == null) {
				instance = GWT.create(TempCounterServicesClient.class);
			}
			return instance;
		}
	}

	public TempCounterDTO getActualState(Integer userId);

	public TempCounterDTO start(Integer userId, Integer taskId);

	public TempCounterDTO pause(Integer userId);

	public Long save(Integer userId);

	public Long cancel(Integer userId);	
}
