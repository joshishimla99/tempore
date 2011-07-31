package ar.fi.uba.tempore.gwt.client.panel.configuration;


public class ClientData {  
	  
    private static ClientRecord[] records;  
  
    public static ClientRecord[] getClientRecords() {  
        if (records == null) {  
            records = getClientNewRecords();  
        }  
        return records;  
    }  
    
    public static ClientRecord[] getClientNewRecords() {  
        return new ClientRecord[]{  
                new ClientRecord("FIUBA", "Paseo Colón 895", "Argentina", "Bs. As.", "1111", "30-11111111-9", "4335-0891"),
                new ClientRecord("Interno", "Paseo Colón 895", "Argentina", "Bs. As.", "1111", "30-11111111-9", "4335-0891")
        };  
    }  
}  
