package ar.fi.uba.tempore.gwt.server;

import java.util.List;

import ar.fi.uba.tempore.dto.UserProjectDTO;
import ar.fi.uba.tempore.gwt.client.UserProjectServicesClient;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class UserProjectServicesClientImpl extends RemoteServiceServlet implements UserProjectServicesClient {

	private static final long serialVersionUID = 3871015150494046391L;

	@Override
	public List<UserProjectDTO> fetch() {
		throw new UnsupportedOperationException("METODO SIN IMPLEMENTAR");
	}

	@Override
	public UserProjectDTO add(UserProjectDTO data) {
		throw new UnsupportedOperationException("METODO SIN IMPLEMENTAR");
	}

	@Override
	public UserProjectDTO update(UserProjectDTO data) {
		throw new UnsupportedOperationException("METODO SIN IMPLEMENTAR");
	}

	@Override
	public void remove(UserProjectDTO data) {
		throw new UnsupportedOperationException("METODO SIN IMPLEMENTAR");
	}
}
