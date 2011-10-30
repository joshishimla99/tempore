package ar.fi.uba.tempore.gwt.client;

import ar.fi.uba.tempore.dto.TaskUserDTO;
import ar.fi.uba.tempore.dto.TimeFilterDTO;
import ar.fi.uba.temporeutils.listgrid.filter.GenericGwtRpcDataSourceServiceFilterIdAsync;

public interface TimeServicesClientAsync extends GenericGwtRpcDataSourceServiceFilterIdAsync<TimeFilterDTO, TaskUserDTO>{

}
