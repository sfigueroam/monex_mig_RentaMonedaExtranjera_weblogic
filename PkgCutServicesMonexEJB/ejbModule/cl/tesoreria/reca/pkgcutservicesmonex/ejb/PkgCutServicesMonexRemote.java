package cl.tesoreria.reca.pkgcutservicesmonex.ejb; 

import javax.ejb.Remote; 
import cl.tesoreria.reca.pkgcutservicesmonex.vo.*; 

@Remote 
public interface PkgCutServicesMonexRemote  { 


  public TrxFormOutVO trxForm(TrxFormInVO entrada) throws Exception; 


} 
