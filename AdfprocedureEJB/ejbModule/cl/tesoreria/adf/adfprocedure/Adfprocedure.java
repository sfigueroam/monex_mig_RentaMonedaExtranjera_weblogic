package cl.tesoreria.adf.adfprocedure; 

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import cl.tesoreria.finanzas.CargarProperties;

import javax.naming.Context;
import javax.naming.InitialContext;

@Stateless(name = "Adfprocedure", mappedName = "cl.tesoreria.adf.adfprocedure.Adfprocedure")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class Adfprocedure implements AdfprocedureRemote, AdfprocedureLocal {
	
	//@Resource(lookup = "java:/jdbc/ecusqlDS")
	//private DataSource dataSource;
	private DataSource dataSource = cargaDataSource();
	private static Logger logger = Logger.getLogger("cl.tesoreria.finpub.RentaMonedaExtranjera.Adfprocedure");

	private static DataSource cargaDataSource() {
		try {
			Context ctx = new InitialContext();
			String jndiDatasourceEcusql = CargarProperties.getValueProp("JNDI.DATASOURCE.ECUSQL");
			DataSource dataSource = (DataSource)ctx.lookup(jndiDatasourceEcusql);
			//DataSource dataSource = (DataSource)ctx.lookup(CargarProperties.getValueProp("JNDI.DATASOURCE.ECUSQL"));
			logger.info("Seguimiento ------ CARGA Constantes.JNDI.DATASOURCE.ECUSQL=" + CargarProperties.getValueProp("JNDI.DATASOURCE.ECUSQL"));
			return dataSource;
		} catch(Exception ex) {
			ex.printStackTrace();
			logger.error("Error en el metodo PkgCMonEx.cargaDataSource() : " + ex);
		}
		return null;
	}

	@Override
	public ProcessResult process(String touplestgf, String contexttgfin)
			throws AdfprocedureException {
		try {
			return ProcessCaller.execute(dataSource, touplestgf, contexttgfin);
		} catch (java.sql.SQLException ex) {
			ex.printStackTrace();
			logger.error("Error en el metodo Adfprocedure.process() : " + ex);
			throw new AdfprocedureException(ex.getMessage(), ex);
		}
	}

}