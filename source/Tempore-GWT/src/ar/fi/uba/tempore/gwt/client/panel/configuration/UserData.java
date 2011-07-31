package ar.fi.uba.tempore.gwt.client.panel.configuration;


public class UserData {  
  
    private static UserRecord[] records;  
  
    public static UserRecord[] getRecords() {  
        if (records == null) {  
            records = getNewRecords();  
        }  
        return records;  
    }  
  
    public static UserRecord[] getNewRecords() {  
        return new UserRecord[]{  
                new UserRecord("Rinaudo", "Ludmila Lis", "Gemalto", "1532070761", "ludmila.rinaudo@gemalto.com", "Argentina", "Eduardo Madero 900 - 21", "1980", "lrinaudo", "12345678", false,"Admin"),
                new UserRecord("Gigante", "Juan Pablo", "Nobleza Picardo", "1532070760", "juanPablo.gigante@picardo.com", "Argentina", "Corrientes 900 - 21", "1980", "jgigante", "12345678", false,"Admin"),
                new UserRecord("Garcia", "Nicolas", "EmpresaX", "1532070768", "nicolas.garcia@empresaX.com", "Argentina", "9 de Julio 900 - 21", "1980", "ngarcia", "12345678", false,"Admin"),
                new UserRecord("Pantaleo", "Guillermo", "itMentor", "1532070754", "guillermo.pantaleo@itMentor.com", "Argentina", "Suipacha 900 - 21", "1980", "gpantaleo", "12345678", true,"Client"),
                new UserRecord("Fernandez", "Ignacio", "Tata", "1532070767", "ignacio.fernandez@tata.com", "Argentina", "Jujuy 900 - 21", "1980", "ifernandez", "12345678", false,"User"),
                new UserRecord("Perez", "Carlos", "Microsoft", "1532070723", "carlos.perez@microsoft.com", "Argentina", "Eduardo Madero 900 - 21", "1980", "cperez", "12345678", true,"Client"),
                new UserRecord("Arnoni", "Mauro", "PetroleraX", "1532070756", "mauro.arnoni@petroleraX.com", "Argentina", "Independencia 900 - 21", "1980", "marnoni", "12345678", false,"User"),
                new UserRecord("Riccillo", "Diego", "Gemalto", "1532070789", "diego.riccillo@gemalto.com", "Argentina", "Eduardo Madero 900 - 21", "1980", "driccillo", "12345678", false,"Admin"),
                new UserRecord("Durante", "Maria", "Gemalto", "1532070761", "maria.durante@gemalto.com", "Argentina", "Eduardo Madero 900 - 21", "1980", "mdurante", "12345678", false,"User"),
                new UserRecord("Lopez", "Julio", "Tata", "1534070761", "julio.lopez@gemalto.com", "Argentina", "Jujuy 900 - 21", "1980", "jlopez", "12345678", false,"Admin")
        };  
    }  
}  