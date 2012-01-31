package ar.fi.uba.tempore.gwt.client;

import java.util.Date;
import java.util.List;

import ar.fi.uba.tempore.dto.reports.ProjectsTimesDTO;
import ar.fi.uba.tempore.dto.reports.TasksTimesDTO;
import ar.fi.uba.tempore.dto.reports.UsersTimesDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ReportServicesClientAsync {
	void getProjectsTimes(Date dateIni, Date dateEnd, AsyncCallback<List<ProjectsTimesDTO>> callback);

	void getUsersTimes(Integer ProjectId, Date dateIni, Date dateEnd,
			AsyncCallback<List<UsersTimesDTO>> callback);

	void getPrimaryTaskTimes(Integer projectId, Date from, Date to,
			AsyncCallback<List<TasksTimesDTO>> callback);

	void getUserActivity(Integer userId, Date ini, Date end,
			AsyncCallback<List<ProjectsTimesDTO>> callback);

}
