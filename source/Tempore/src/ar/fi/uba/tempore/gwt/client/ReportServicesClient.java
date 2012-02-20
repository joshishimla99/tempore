package ar.fi.uba.tempore.gwt.client;
import java.util.Date;
import java.util.List;
import java.util.Map;


import ar.fi.uba.tempore.dto.reports.ProjectsTimesDTO;
import ar.fi.uba.tempore.dto.reports.TasksTimesDTO;
import ar.fi.uba.tempore.dto.reports.UsersTimesDTO;

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

	public List<UsersTimesDTO> getUsersTimes(Integer projectId, Date dateIni, Date dateEnd);
	
	public List<TasksTimesDTO> getPrimaryTaskTimes (Integer projectId ,Date from, Date to);
	
	public List<ProjectsTimesDTO> getUserActivity (Integer userId, Date ini, Date end);

	public Map<String, Map<Integer, Long>> getProjectTaskTypeByTime(Integer projectId,	Date ini, Date end);

}
