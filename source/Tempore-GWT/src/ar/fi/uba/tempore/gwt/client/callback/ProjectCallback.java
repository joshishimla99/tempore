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
		Window.alert("Failure");
	}

	public void onSuccess(List<ProjectDTO> result) {
		Window.alert(result.get(0).getName());
	}
}


