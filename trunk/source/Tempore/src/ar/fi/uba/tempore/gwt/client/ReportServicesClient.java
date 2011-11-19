package ar.fi.uba.tempore.gwt.client;
import java.util.Date;
import java.util.List;

import ar.fi.uba.tempore.dto.reports.ProjectsTimesDTO;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("ReportServices")
public interface ReportServicesClient extends RemoteService {
	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
	public static class Util {
		private static ReportServicesClientAsync instance;
		public static ReportServicesClientAsync getInstance(){
			if (instance == null) {
				instance = GWT.create(ReportServicesClient.class);
			}
			return instance;
		}
	}
	
	public List<ProjectsTimesDTO> getProjectsTimes (Date dateIni, Date dateEnd);

}
