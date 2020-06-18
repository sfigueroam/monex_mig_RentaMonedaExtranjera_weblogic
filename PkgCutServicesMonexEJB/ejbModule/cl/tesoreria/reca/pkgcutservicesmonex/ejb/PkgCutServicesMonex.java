package cl.tesoreria.reca.pkgcutservicesmonex.ejb; 
import javax.ejb.Stateless; 
import javax.ejb.TransactionAttribute; 
import javax.ejb.TransactionAttributeType; 
import javax.annotation.Resource; 
import javax.sql.DataSource; 
import java.io.Serializable; 
import cl.tesoreria.reca.pkgcutservicesmonex.dao.*; 
import cl.tesoreria.reca.pkgcutservicesmonex.vo.*;
import org.apache.log4j.Logger;
import cl.tesoreria.finanzas.CargarProperties;
import javax.naming.Context;
import javax.naming.InitialContext;

/** 
* Session Bean implementation class PkgCutServicesMonex 
*/ 
@Stateless(name="PkgCutServicesMonex", mappedName="cl.teso.reca.pkgcutservicesmonex.PkgCutServicesMonex") 

@TransactionAttribute(TransactionAttributeType.REQUIRED) 
public class PkgCutServicesMonex implements PkgCutServicesMonexRemote,PkgCutServicesMonexLocal { 

  //@Resource(lookup = "java:/jdbc/recaDS") DataSource dataSource; 
  private DataSource dataSource = cargaDataSource();
  private static Logger logger = Logger.getLogger("cl.tesoreria.finpub.RentaMonedaExtranjera.PkgCutServicesMonex");
  /** 
  * Default constructor. 
  */ 
  public PkgCutServicesMonex() { 
    // TODO Auto-generated constructor stub 
  } 

	private static DataSource cargaDataSource() {
		try {
			Context ctx = new InitialContext();
			String jndiDatasourceReca = CargarProperties.getValueProp("JNDI.DATASOURCE.RECA");
			DataSource dataSource = (DataSource)ctx.lookup(jndiDatasourceReca);
			//DataSource dataSource = (DataSource)ctx.lookup(CargarProperties.getValueProp("rentaMe.JNDI.DATASOURCE.RECA"));
			logger.info("Seguimiento ------ CARGA Constantes.JNDI.DATASOURCE.RECA=" + CargarProperties.getValueProp("JNDI.DATASOURCE.RECA"));
			return dataSource;
		} catch(Exception ex) {
			ex.printStackTrace();
			logger.error("Error en el metodo PkgCMonEx.cargaDataSource() : " + ex);
		}
		return null;
	}

  
  @Override 
  public TrxFormOutVO trxForm(TrxFormInVO entrada) throws Exception{ 
    try{ 
       TrxFormDAO dao = new TrxFormDAO(); 
       dao.setEntrada(entrada); 
       Serializable salida = dao.executeProcedure(dataSource); 
       return (TrxFormOutVO) salida; 
    }catch (java.sql.SQLException ex){ 
      throw new Exception(ex); 
    } 
  } 



} 
