package ar.fi.uba.temporeutils.listgrid.filter;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.IsSerializable;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.rpc.RPCResponse;
import com.smartgwt.client.util.JSOHelper;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.tree.TreeNode;
import com.smartgwt.client.widgets.viewer.DetailViewerRecord;

/**
 * Generic abstract {@link GwtRpcDataSourceFilterId} implementation, based on the
 * {@link GwtRpcDataSourceFilterId} example provided in the smartgwt-extensions. Extend this
 * class if you want to create a GwtRpcDataSource for SmartGWT.
 * 
 * Para poder utilizar esta clase se debe implementar ambas interfaces 
 * {@link GenericGwtRpcDataSourceServiceFilterId} and {@link GenericGwtRpcDataSourceServiceFilterIdAsync}
 * provistas en el mismo paquete.
 * @Param <F>
 * 			  Filtro para realizar el fetch (comunmente Integer para poder filtra el id de usuario o el id del proyecto)
 * @param <D>
 *            type of the transfer object holding the data (will most likely be
 *            a simple POJO), must implement {@link Serializable} or {@link IsSerializable}.
 * @param <R>
 *            any extension of {@link Record}, such as {@link ListGridRecord},
 *            {@link DetailViewerRecord} or {@link TreeNode} to use with your SmartGWT widget.
 * @param <SA>
 *            the asynchronous version of your service. Extend
 *            {@link GenericGwtRpcDataSourceServiceFilterId} and then 
 *            {@link GenericGwtRpcDataSourceServiceFilterIdAsync} to implement it.
 * 
 * @see GwtRpcDataSourceFilterId
 * @see GenericGwtRpcDataSourceServiceFilterId
 * @see GenericGwtRpcDataSourceServiceFilterIdAsync
 *
 */
public abstract class GenericGwtRpcDataSourceFilterId<F, D, R extends Record, SA extends GenericGwtRpcDataSourceServiceFilterIdAsync<F,D>>
		extends GwtRpcDataSourceFilterId {

	private F id;
	
	public GenericGwtRpcDataSourceFilterId() {
		super();
		List<DataSourceField> fields = getDataSourceFields();
		if (fields != null) {
			for (DataSourceField field : fields) {
				addField(field);
			}
		}
	}

	public F getId() {
		return id;
	}

	public void setId(F id) {
		this.id = id;
	}

	/**
	 * @return a list of {@link DataSourceField}, used to define the fields of
	 *         your {@link DataSource}. NOTE: Make sure to set a primary key, as
	 *         some problems might occur if it's omitted.
	 */
	public abstract List<DataSourceField> getDataSourceFields();

	/**
	 * Copies values from the {@link Record} to the data object.
	 * 
	 * @param from
	 *            the {@link Record} to copy from.
	 * @param to
	 *            the data object to copy to.
	 */
	public abstract void copyValues(R from, D to);

	/**
	 * Copies values from the data object to the {@link Record}.
	 * 
	 * @param from
	 *            the data object to copy from.
	 * @param to
	 *            the {@link Record} to copy to.
	 */
	public abstract void copyValues(D from, R to);

	/**
	 * @return the {@link GenericGwtRpcDataSourceServiceFilterIdAsync} to use, created
	 *         using
	 *         <code>GWT.create(YourGenericGwtRpcDataSourceService.class)</code>
	 *         .
	 * 
	 *         This is unfortunately necessary as <code>GWT.create()</code> only
	 *         allows class literal as argument. We cannot create a class
	 *         literal from a parameterized type because it has no exact runtime
	 *         type representation, which is due to type erasure at compile
	 *         time.
	 */
	public abstract SA getServiceAsync();

	/**
	 * @return a new instance of your {@link Record}, such as
	 *         <code>new Record()</code> or <code>new ListGridRecord()</code>.
	 * 
	 *         This method is needed because we cannot instantiate parameterized
	 *         types at runtime. It also increases flexibility as we can pass
	 *         more complex default objects.
	 */
	public abstract R getNewRecordInstance();

	/**
	 * @return a new instance of your data object, such as
	 *         <code>new YourDataObject()</code>.
	 * 
	 *         This method is needed because we cannot instantiate parameterized
	 *         types at runtime. It also increases flexibility as we can pass
	 *         more complex default objects.
	 */
	public abstract D getNewDataObjectInstance();

	@Override
	protected void executeFetch(final String requestId, final DSRequest request, final DSResponse response) {
		// These can be used as parameters to create paging.
		// request.getStartRow ();
		// request.getEndRow ();
		// request.getSortBy ();
		getServiceAsync().fetch(getId(), new AsyncCallback<List<D>>() {
			
			public void onFailure(Throwable caught) {
				response.setStatus(RPCResponse.STATUS_FAILURE);
				processResponse(requestId, response);
			}

			public void onSuccess(List<D> result) {
				List<R> records = new ArrayList<R>();
				for (D data : result) {
					R newRec = getNewRecordInstance();
					copyValues(data, newRec);
					records.add(newRec);
				}
				response.setData(records.toArray(new Record[records.size()]));
				processResponse(requestId, response);
			}
		});
	}

	@Override
	protected void executeAdd(final String requestId, final DSRequest request,
			final DSResponse response) {
		// Retrieve record which should be added.
		R newRec = getNewRecordInstance();
		newRec.setJsObj(request.getData());
		D data = getNewDataObjectInstance();
		copyValues(newRec, data);
		getServiceAsync().add(data, new AsyncCallback<D>() {
			public void onFailure(Throwable caught) {
				response.setStatus(RPCResponse.STATUS_FAILURE);
				processResponse(requestId, response);
			}

			public void onSuccess(D result) {
				R newRec = getNewRecordInstance();
				copyValues(result, newRec);
				response.setData(new Record[] { newRec });
				processResponse(requestId, response);
			}
		});
	}

	@Override
	protected void executeUpdate(final String requestId,
			final DSRequest request, final DSResponse response) {
		// Retrieve record which should be updated.
		R editedRec = getEditedRecord(request);
		D data = getNewDataObjectInstance();
		copyValues(editedRec, data);
		getServiceAsync().update(data, new AsyncCallback<D>() {
			public void onFailure(Throwable caught) {
				response.setStatus(RPCResponse.STATUS_FAILURE);
				processResponse(requestId, response);
			}

			public void onSuccess(D result) {
				R updatedRec = getNewRecordInstance();
				copyValues(result, updatedRec);
				response.setData(new Record[] { updatedRec });
				processResponse(requestId, response);
			}
		});
	}

	@Override
	protected void executeRemove(final String requestId,
			final DSRequest request, final DSResponse response) {
		// Retrieve record which should be removed.
		final R rec = getNewRecordInstance();
		rec.setJsObj(request.getData());
		D data = getNewDataObjectInstance();
		copyValues(rec, data);
		getServiceAsync().remove(data, new AsyncCallback<Void>() {
			public void onFailure(Throwable caught) {
				response.setStatus(RPCResponse.STATUS_FAILURE);
				processResponse(requestId, response);
			}

			public void onSuccess(Void v) {
				// We do not receive removed record from server.
				// Return record from request.
				response.setData(new Record[] { rec });
				processResponse(requestId, response);
			}

		});
	}

	private R getEditedRecord(DSRequest request) {
		// Retrieving values before edit
		JavaScriptObject oldValues = request.getOldValues().getJsObj();
		// Creating new record for combining old values with changes
		R newRecord = getNewRecordInstance();
		// Copying properties from old record
		JSOHelper.apply(oldValues, newRecord.getJsObj());
		// Retrieving changed values
		JavaScriptObject changedData = request.getData();
		// Apply changes
		JSOHelper.apply(changedData, newRecord.getJsObj());
		return newRecord;
	}
}
