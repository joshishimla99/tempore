package ar.fi.uba.tempore.hibernate.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import ar.fi.uba.tempore.dao.ReportDAO;
import ar.fi.uba.tempore.hibernate.TestDAO;

public class TestReportDAO extends TestDAO{
	private ReportDAO rDAO = new ReportDAO();	
	
	
	@Test
	public void testFindById() {
		Date ini = new Date();
		Date end = new Date();
		
		List<Map> pTime = rDAO.getProjectTimes(ini, end);
		System.out.println("Cantidad de Registros: " + pTime.size());
		for (Map p : pTime) {
			
			System.out.println(p.get("name") + ", Horas Cargadas: " + p.get("total"));
		}
	}
	
}
