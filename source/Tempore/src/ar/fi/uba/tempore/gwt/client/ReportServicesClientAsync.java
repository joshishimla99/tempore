package ar.fi.uba.tempore.gwt.client;

import java.util.Date;
import java.util.List;

import ar.fi.uba.tempore.dto.reports.ProjectsTimesDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ReportServicesClientAsync {
	void getProjectsTimes(Date dateIni, Date dateEnd, AsyncCallback<List<ProjectsTimesDTO>> callback);
}
