package cl.tesoreria.adf.adfprocedure; 

import javax.ejb.Remote;

@Remote
public interface AdfprocedureRemote {

    public ProcessResult process(String touplestgf, String contexttgfin)
        throws AdfprocedureException;

}
