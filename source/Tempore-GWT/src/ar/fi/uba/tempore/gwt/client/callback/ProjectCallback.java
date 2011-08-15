package ar.fi.uba.tempore.gwt.client.callback;

import java.util.List;

import ar.fi.uba.tempore.dto.ProjectDTO;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class ProjectCallback implements AsyncCallback<List<ProjectDTO>> {

	private List<ProjectDTO> lista;

	public ProjectCallback(List<ProjectDTO> lista) {
		this.lista = lista;
	}

	public void onFailure(Throwable caught) {
		Window.alert("Proyecto Failure");
	}

	public void onSuccess(List<ProjectDTO> result) {
		Window.alert("Proyecto Success, " + result.get(0).getName());
	}
}


