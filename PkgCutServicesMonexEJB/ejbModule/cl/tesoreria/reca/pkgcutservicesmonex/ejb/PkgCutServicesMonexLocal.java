package cl.tesoreria.reca.pkgcutservicesmonex.ejb; 

import javax.ejb.Local; 
import cl.tesoreria.reca.pkgcutservicesmonex.vo.*; 

@Local 
public interface PkgCutServicesMonexLocal  { 


  public TrxFormOutVO trxForm(TrxFormInVO entrada) throws Exception; 


} 
