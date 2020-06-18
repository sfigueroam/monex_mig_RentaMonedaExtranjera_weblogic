package cl.tesoreria.adf.adfprocedure; 

import javax.ejb.Local;

@Local
public interface AdfprocedureLocal {

    public ProcessResult process(String touplestgf, String contexttgfin)
        throws AdfprocedureException;

}
