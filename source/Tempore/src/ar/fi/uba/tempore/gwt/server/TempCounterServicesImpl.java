package ar.fi.uba.tempore.gwt.server;

import java.util.Date;

import org.apache.log4j.Logger;
import org.dozer.DozerBeanMapper;

import ar.fi.uba.tempore.dao.TaskUserDAO;
import ar.fi.uba.tempore.dao.TempCounterDAO;
import ar.fi.uba.tempore.dto.TempCounterDTO;
import ar.fi.uba.tempore.entity.Task;
import ar.fi.uba.tempore.entity.TaskUser;
import ar.fi.uba.tempore.entity.TempCounter;
import ar.fi.uba.tempore.entity.User;
import ar.fi.uba.tempore.gwt.client.TempCounterServicesClient;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class TempCounterServicesImpl extends RemoteServiceServlet implements TempCounterServicesClient {
	private static final long serialVersionUID = 7476117264486326360L;
	private static final Integer PLAY = 1;
	private static final Integer PAUSE = 2;
	
	private final Logger log = Logger.getLogger(this.getClass());
	private final DozerBeanMapper mapper = new DozerBeanMapper();

	@Override
	public TempCounterDTO getActualState(Integer userId){
		TempCounterDAO tcDAO = new TempCounterDAO();
		log.info("THREAD = " + this.getThreadLocalRequest());
		log.info("STATE COUNTER - " + userId + " - " + this.getThreadLocalRequest().getAttribute("username"));
		TempCounter tc = tcDAO.findById(userId);
		
		TempCounterDTO tcDTO = tc==null?null:mapper.map(tc, TempCounterDTO.class);
		if (tcDTO != null && tcDTO.getControl() == PLAY){
			//Esta en play, cuento desde cuando esta inicializado
			tcDTO.setTimeAcumulated(tcDTO.getTimeAcumulated()+System.currentTimeMillis()-tcDTO.getTimeIni());
		}

		return tcDTO;
	}

	@Override
	public TempCounterDTO start (Integer userId, Integer taskId){
		TempCounterDAO tcDAO = new TempCounterDAO();
		
		log.info("START COUNTER - " + userId + " - " + taskId);
		TempCounter tc = tcDAO.findById(userId);
		
		if (tc != null && tc.getControl() == PLAY){
			log.error("El contador ya esta iniciado, no puede iniciarce nuevamente");
		} else {
			if (tc == null){
				//Contador nuevo
				log.info("Nuevo conteo");
				tc = new TempCounter();
				tc.setUserId(userId);
				tc.setTimeAcumulated(0L);
			} else {
				//contador existe (reinicio)
				log.info("Continuo conteo existente");
			}
			tc.setTask(new Task(taskId));		
			tc.setTimeIni(System.currentTimeMillis());
			tc.setControl(PLAY);

			tc = tcDAO.makePersistent(tc);
		}
		
		TempCounterDTO tcDTO = mapper.map(tc , TempCounterDTO.class);
		
		return tcDTO ;
	}

	@Override
	public TempCounterDTO pause (Integer userId){
		TempCounterDAO tcDAO = new TempCounterDAO();
		TempCounter tc = tcDAO.findById(userId);

		if (tc == null){
			log.error("El contador no existe, no puede realizarce la puasa a un contador inexistente");
		} else {
			if (tc.getControl() != PLAY){
				log.error("El contador no cuenta con el estado PLAY para poder pausarlo");
			} else {
				tc.setTimeAcumulated(tc.getTimeAcumulated() +  System.currentTimeMillis() - tc.getTimeIni());
				tc.setTimeIni(0L);
				tc.setControl(PAUSE);
			}
		}
		
		TempCounterDTO tcDTO = mapper.map(tc , TempCounterDTO.class);
		return tcDTO;
	}

	@Override
	public Long save (Integer userId) {
		TempCounterDAO tcDAO = new TempCounterDAO();
		TaskUserDAO tuDAO = new TaskUserDAO();
		
		TempCounter tc = tcDAO.findById(userId);
		Long timeSaved = null;
		if (tc == null){
			log.error("No existe contador para guardar");
		} else {
			timeSaved = tc.getTimeAcumulated();
			if (tc.getTimeIni() != 0L){
				timeSaved +=  System.currentTimeMillis() - tc.getTimeIni();
			}

			Long factor = Math.round((double)timeSaved/TimeServicesImpl.QUINCE_MIN);
			timeSaved = factor*TimeServicesImpl.QUINCE_MIN;
			if (timeSaved == 0L){
				timeSaved = TimeServicesImpl.QUINCE_MIN;
			}
			
			TaskUser tu = new TaskUser();
			tu.setUser(new User(tc.getUserId()));
			tu.setTask(new Task(tc.getTask().getId()));
			tu.setHourCount(timeSaved);
			tu.setDate(new Date());
			tu.setComment("Horas cargadas desde el contador de horas");
			tuDAO.makePersistent(tu);
			
			//Limpio registro temporal del contador
			tcDAO.delete(tc);
		}
		return timeSaved;
	}
	
	@Override
	public Long cancel (Integer userId){
		TempCounterDAO tcDAO = new TempCounterDAO();
		TempCounter tc = tcDAO.findById(userId);
		Long timeCanceled = null;
		if (tc == null){
			log.error("No existe contador para cancelar");
		} else {
			timeCanceled = tc.getTimeAcumulated() + System.currentTimeMillis() - tc.getTimeIni();
			//Limpio registro temporal del contador
			tcDAO.delete(tc);
		}
		return timeCanceled;
	}

}
