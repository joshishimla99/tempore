package ar.fi.uba.tempore.gwt.server;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.dozer.DozerBeanMapper;

import ar.fi.uba.tempore.dao.ReportDAO;
import ar.fi.uba.tempore.dto.reports.ProjectsTimesDTO;
import ar.fi.uba.tempore.entity.reports.ProjectsTimes;
import ar.fi.uba.tempore.gwt.client.ReportServicesClient;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ReportServicesImpl extends RemoteServiceServlet implements ReportServicesClient {

	private static final long serialVersionUID = -632059708650593031L;
	private final Logger log = Logger.getLogger(this.getClass());
	private final DozerBeanMapper mapper = new DozerBeanMapper();
	
	private final ReportDAO report = new ReportDAO();
	
	@Override
	public List<ProjectsTimesDTO> getProjectsTimes(Date dateIni, Date dateEnd) {
		log.info("REPORTE - GetProjectsTimes ["+dateIni+"],["+dateEnd+"]");
		
//		DateFormat format = new SimpleDateFormat("dd-MM-yyyyy");
//		dateEnd = new Date();
//		try {
//			dateIni = format.parse("01-01-2000");
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		List<ProjectsTimes> projectsTimesList = report.getProjectsTimes(dateIni, dateEnd);
		
		List<ProjectsTimesDTO> dtoList = new ArrayList<ProjectsTimesDTO>(projectsTimesList.size());
		for (ProjectsTimes e : projectsTimesList){
			ProjectsTimesDTO dto = mapper.map(e, ProjectsTimesDTO.class);
			dtoList.add(dto);
		}
		
		return dtoList; 
	}
}
