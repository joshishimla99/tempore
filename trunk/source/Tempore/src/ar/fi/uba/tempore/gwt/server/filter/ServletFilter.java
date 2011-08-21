package ar.fi.uba.tempore.gwt.server.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.Logger;

import ar.fi.uba.tempore.dao.util.HibernateUtil;

public class ServletFilter implements javax.servlet.Filter {
	private final Logger log = Logger.getLogger(this.getClass());
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		if (log.isDebugEnabled()){
			log.debug("******* Inicializando FILTRO *********");
		}
		HibernateUtil.getSessionFactory();
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,	FilterChain chain) throws IOException, ServletException {
		Long start = 0L;
		if (log.isDebugEnabled()){
			start = System.currentTimeMillis();
			log.debug("******* Transaccion Iniciada *********");
		}
		
		chain.doFilter(request, response);
		
		if (log.isDebugEnabled()){
			log.debug("******* Tranasccion Terminada ("+(System.currentTimeMillis()-start)+" ms) *********");			
		}
	}
	
	@Override
	public void destroy() {
		if (log.isDebugEnabled()){
			log.debug("******* FILTRO Destruido *********");
		}
	}
}
