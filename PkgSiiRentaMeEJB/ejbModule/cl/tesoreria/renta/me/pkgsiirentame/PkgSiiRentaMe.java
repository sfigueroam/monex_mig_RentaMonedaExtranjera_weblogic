package cl.tesoreria.renta.me.pkgsiirentame; 

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import oracle.jdbc.OracleConnection;
import cl.tesoreria.finanzas.CargarProperties;
import javax.naming.Context;
import javax.naming.InitialContext;

@Stateless(name = "PkgSiiRentaMe", mappedName = "cl.tesoreria.renta.me.pkgsiirentame.PkgSiiRentaMe")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class PkgSiiRentaMe implements PkgSiiRentaMeRemote, PkgSiiRentaMeLocal {

	// @Resource(lookup = "java:/jdbc/siiDS")
	// private DataSource dataSource;
	private DataSource dataSource = cargaDataSource();
	private static Logger logger = Logger.getLogger("cl.tesoreria.finpub.RentaMonedaExtranjera.PkgSiiRentaMe");

	private static DataSource cargaDataSource() {
		try {
			Context ctx = new InitialContext();
			String jndiDatasourceSii = CargarProperties.getValueProp("JNDI.DATASOURCE.SII");
			DataSource dataSource = (DataSource)ctx.lookup(jndiDatasourceSii);
			//DataSource dataSource = (DataSource)ctx.lookup(CargarProperties.getValueProp("rentaMe.JNDI.DATASOURCE.SII"));
			logger.info("Seguimiento ------ CARGA Constantes.JNDI.DATASOURCE.SII=" + CargarProperties.getValueProp("JNDI.DATASOURCE.SII"));
			return dataSource;
		} catch(Exception ex) {
			ex.printStackTrace();
			logger.error("Error en el metodo PkgCMonEx.cargaDataSource() : " + ex);
		}
		return null;
	}

	@Override
	public ActRectificadaCtaByLlaveResult actRectificadaCtaByLlave(
			BigDecimal rutin, String dvin, BigDecimal formtipoin,
			BigDecimal folioin, Date fechavenc) throws PkgSiiRentaMeException {
		try {
			Connection conn = dataSource.getConnection();
			try {
				return ActRectificadaCtaByLlaveCaller.execute(conn, rutin,
						dvin, formtipoin, folioin, fechavenc);
			} finally {
				conn.close();
			}
		} catch (SQLException ex) {	
			ex.printStackTrace();
			logger.error("Error en el metodo PkgSiiRentaMe.ActRectificadaCtaByLlaveResult() : " + ex);
			throw new PkgSiiRentaMeException(ex);
		}
	}
	

	@Override
	public ActRectificadaCtaByLlave2Result actRectificadaCtaByLlave2(
			BigDecimal rutin, String dvin, BigDecimal formtipoin,
			BigDecimal folioin) throws PkgSiiRentaMeException {
		try {
			Connection conn = dataSource.getConnection();
			try {
				return ActRectificadaCtaByLlave2Caller.execute(conn, rutin,
						dvin, formtipoin, folioin);
			} finally {
				conn.close();
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.error("Error en el metodo PkgSiiRentaMe.actRectificadaCtaByLlave2() : " + ex);

			throw new PkgSiiRentaMeException(ex);
		}
	}

	public static OracleConnection getOracleConnection(Connection conFromPool)
			throws SQLException, Exception {
			 
		  try {
		    Class[] parms = null;
		    Method method = (conFromPool.getClass()).getMethod("getUnderlyingConnection", parms);
		    return (OracleConnection) method.invoke(conFromPool, parms);
		 
		  } catch (InvocationTargetException ite) {
		    throw new SQLException(ite.getMessage());
		  }
	}	
	
	@Override
	public InsertItemsByCollResult insertItemsByColl(ItemCut[] itmCol)
       throws PkgSiiRentaMeException

	{
		try {
			
				Connection conn = dataSource.getConnection();

				OracleConnection con = getOracleConnection( conn );
				    
				try {
					return InsertItemsByCollCaller.execute(con, itmCol);
				} finally {
					conn.close();
				}
			  
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Error en el metodo PkgSiiRentaMe.insertItemsByColl() : " + ex);
			throw new PkgSiiRentaMeException(ex);
		}
	}

	@Override
	public MyfunctionResult myfunction(BigDecimal param1)
			throws PkgSiiRentaMeException {
		try {
			Connection conn = dataSource.getConnection();
			try {
				return MyfunctionCaller.execute(conn, param1);
			} finally {
				conn.close();
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.error("Error en el metodo PkgSiiRentaMe.myfunction() : " + ex);
			throw new PkgSiiRentaMeException(ex);
		}
	}

	@Override
	public MyprocedureResult myprocedure(BigDecimal param1)
			throws PkgSiiRentaMeException {
		try {
			Connection conn = dataSource.getConnection();
			try {
				return MyprocedureCaller.execute(conn, param1);
			} finally {
				conn.close();
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.error("Error en el metodo PkgSiiRentaMe.myprocedure() : " + ex);
			throw new PkgSiiRentaMeException(ex);
		}
	}
	
	@Override
	public RegistrarFomulariosResult registrarFomularios(Formulario[] formucoll)
    throws PkgSiiRentaMeException
	{
		try {
			Connection conn = dataSource.getConnection();
			try {
				return RegistrarFomulariosCaller.execute(conn, formucoll);
			} finally {
				conn.close();
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.error("Error en el metodo PkgSiiRentaMe.myprocedure() : " + ex);
			throw new PkgSiiRentaMeException(ex);
		}
	}

	@Override
	public SpBuscarcodigoobbymovResult spBuscarcodigoobbymov(BigDecimal idmov)
			throws PkgSiiRentaMeException {
		try {
			Connection conn = dataSource.getConnection();
			try {
				return SpBuscarcodigoobbymovCaller.execute(conn, idmov);
			} finally {
				conn.close();
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.error("Error en el metodo PkgSiiRentaMe.spBuscarcodigoobbymov() : " + ex);			
			throw new PkgSiiRentaMeException(ex);
		}
	}

	@Override
	public SpBuscarcontitembyidmovResult spBuscarcontitembyidmov(
			BigDecimal idmov) throws PkgSiiRentaMeException {
		try {	
			Connection conn = dataSource.getConnection();
			try {
				return SpBuscarcontitembyidmovCaller.execute(conn, idmov);
			} finally {
				conn.close();
			}
		} catch (SQLException ex) {		
			ex.printStackTrace();
			logger.error("Error en el metodo PkgSiiRentaMe.spBuscarcontitembyidmov() : " + ex);						
			throw new PkgSiiRentaMeException(ex);
		}
	}

	@Override
	public SpBuscaridcuentacutbyformResult spBuscaridcuentacutbyform(
			BigDecimal rut, String dv, BigDecimal formtipo, BigDecimal folio,
			Date fechvto) throws PkgSiiRentaMeException {
		try {
			Connection conn = dataSource.getConnection();
			try {
				return SpBuscaridcuentacutbyformCaller.execute(conn, rut, dv,
						formtipo, folio, fechvto);
			} finally {
				conn.close();
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.error("Error en el metodo PkgSiiRentaMe.spBuscaridcuentacutbyform() : " + ex);						
			throw new PkgSiiRentaMeException(ex);
		}
	}

	@Override
	public SpBuscaridmonedabynemoResult spBuscaridmonedabynemo(String nemos)
			throws PkgSiiRentaMeException {
		try {
			Connection conn = dataSource.getConnection();
			try {
				return SpBuscaridmonedabynemoCaller.execute(conn, nemos);
			} finally {
				conn.close();
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.error("Error en el metodo PkgSiiRentaMe.spBuscaridmonedabynemo() : " + ex);						
			throw new PkgSiiRentaMeException(ex);
		}
	}

	@Override
	public SpBuscaridtipomovbynemoResult spBuscaridtipomovbynemo(
			String descripnemo) throws PkgSiiRentaMeException {
		try {
			Connection conn = dataSource.getConnection();
			try {
				return SpBuscaridtipomovbynemoCaller.execute(conn, descripnemo);
			} finally {
				conn.close();
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.error("Error en el metodo PkgSiiRentaMe.spBuscaridmonedabynemo() : " + ex);						
			throw new PkgSiiRentaMeException(ex);
		}
	}

	@Override
	public SpBuscarmovrecmasmebyformResult spBuscarmovrecmasmebyform(
			BigDecimal idcta) throws PkgSiiRentaMeException {
		try {
			Connection conn = dataSource.getConnection();
			try {
				return SpBuscarmovrecmasmebyformCaller.execute(conn, idcta);
			} finally {
				conn.close();
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.error("Error en el metodo PkgSiiRentaMe.spBuscarmovrecmasmebyform() : " + ex);						
			throw new PkgSiiRentaMeException(ex);
		}
	}

	@Override
	public SpExisteCuentabyfolioandrutResult spExisteCuentabyfolioandrut(
			BigDecimal rut, String dv, BigDecimal tipoform,
			BigDecimal folioform, Date fechvto) throws PkgSiiRentaMeException {
		try {
			Connection conn = dataSource.getConnection();
			try {
				return SpExisteCuentabyfolioandrutCaller.execute(conn, rut, dv,
						tipoform, folioform, fechvto);
			} finally {
				conn.close();
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.error("Error en el metodo PkgSiiRentaMe.spExisteCuentabyfolioandrut() : " + ex);						
			throw new PkgSiiRentaMeException(ex);
		}
	}

	@Override
	public SpExisteMovByLlaveResult spExisteMovByLlave(BigDecimal rutin,
			String dvin, BigDecimal formtipoin, BigDecimal folioin,
			Date fechavenc) throws PkgSiiRentaMeException {
		try {
			Connection conn = dataSource.getConnection();
			try {
				return SpExisteMovByLlaveCaller.execute(conn, rutin, dvin,
						formtipoin, folioin, fechavenc);
			} finally {
				conn.close();
			}
		} catch (SQLException ex) {	
			ex.printStackTrace();
			logger.error("Error en el metodo PkgSiiRentaMe.spExisteMovByLlave() : " + ex);						
			throw new PkgSiiRentaMeException(ex);
		}
	}

	@Override
	public SpExisteMovByLlave2Result spExisteMovByLlave2(BigDecimal rutin,
			String dvin, BigDecimal formtipoin, BigDecimal folioin)
			throws PkgSiiRentaMeException {
		try {
			Connection conn = dataSource.getConnection();
			try {
				return SpExisteMovByLlave2Caller.execute(conn, rutin, dvin,
						formtipoin, folioin);
			} finally {
				conn.close();
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.error("Error en el metodo PkgSiiRentaMe.spExisteMovByLlave2() : " + ex);						
			throw new PkgSiiRentaMeException(ex);
		}
	}

	@Override
	public SpExisteRegistradoFormmovtoResult spExisteRegistradoFormmovto(
			BigDecimal numbertrx) throws PkgSiiRentaMeException {
		try {
			Connection conn = dataSource.getConnection();
			try {
				return SpExisteRegistradoFormmovtoCaller.execute(conn,
						numbertrx);
			} finally {
				conn.close();
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.error("Error en el metodo PkgSiiRentaMe.spExisteRegistradoFormmovto() : " + ex);						
			throw new PkgSiiRentaMeException(ex);
		}
	}

	@Override
	public SpFindMsgmovByLlaveResult spFindMsgmovByLlave(BigDecimal rutin,
			String dvin, BigDecimal formtipoin, BigDecimal folioin,
			Date fechavenc) throws PkgSiiRentaMeException {
		try {
			Connection conn = dataSource.getConnection();
			try {
				return SpFindMsgmovByLlaveCaller.execute(conn, rutin, dvin,
						formtipoin, folioin, fechavenc);
			} finally {
				conn.close();
			}
		} catch (SQLException ex) {		
			ex.printStackTrace();
			logger.error("Error en el metodo PkgSiiRentaMe.spFindMsgmovByLlave() : " + ex);						
			throw new PkgSiiRentaMeException(ex);
		}
	}

	@Override
	public SpFindMsgmovByLlave2Result spFindMsgmovByLlave2(BigDecimal rutin,
			String dvin, BigDecimal formtipoin, BigDecimal folioin)
			throws PkgSiiRentaMeException {
		try {
			Connection conn = dataSource.getConnection();
			try {
				return SpFindMsgmovByLlave2Caller.execute(conn, rutin, dvin,
						formtipoin, folioin);
			} finally {
				conn.close();
			}
		} catch (SQLException ex) {		
			ex.printStackTrace();
			logger.error("Error en el metodo PkgSiiRentaMe.spFindMsgmovByLlave2() : " + ex);						
			throw new PkgSiiRentaMeException(ex);
		}
	}

	@Override
	public SpGenerarfolio01meResult spGenerarfolio01me(BigDecimal formulario,
			BigDecimal fech315, BigDecimal fech9927)
			throws PkgSiiRentaMeException {
		try {
			Connection conn = dataSource.getConnection();
			try {
				return SpGenerarfolio01meCaller.execute(conn, formulario,
						fech315, fech9927);
			} finally {
				conn.close();
			}
		} catch (SQLException ex) {	
			ex.printStackTrace();
			logger.error("Error en el metodo PkgSiiRentaMe.spGenerarfolio01me() : " + ex);						
			throw new PkgSiiRentaMeException(ex);
		}
	}

	@Override
	public SpInsertErrorMsgMeResult spInsertErrorMsgMe(String messageid,
			BigDecimal errorindex, BigDecimal numbererror, BigDecimal severity,
			String description, BigDecimal formindex, BigDecimal codigoindex,
			String contenido, String modulename, String objectname,
			String objectdesc, BigDecimal periodocontable)
			throws PkgSiiRentaMeException {
		try {
			Connection conn = dataSource.getConnection();
			try {
				return SpInsertErrorMsgMeCaller.execute(conn, messageid,
						errorindex, numbererror, severity, description,
						formindex, codigoindex, contenido, modulename,
						objectname, objectdesc, periodocontable);
			} finally {
				conn.close();
			}
		} catch (SQLException ex) {	
			ex.printStackTrace();
			logger.error("Error en el metodo PkgSiiRentaMe.spInsertErrorMsgMe() : " + ex);
			throw new PkgSiiRentaMeException(ex);
		}
	}

	@Override
	public SpInsertMsgRentaMeResult spInsertMsgRentaMe(String messageid,
			String code, Date fechain, BigDecimal identif, BigDecimal rutcontr,
			BigDecimal formulario, BigDecimal foliodecl,
			BigDecimal periodocont, String status, String xml,
			BigDecimal fecha9927) throws PkgSiiRentaMeException {
		try {
			Connection conn = dataSource.getConnection();

			OracleConnection con = getOracleConnection( conn );
			try {
				return SpInsertMsgRentaMeCaller.execute(con, messageid, code,
						fechain, identif, rutcontr, formulario, foliodecl,
						periodocont, status, xml, fecha9927);
			} finally {
				conn.close();
			}
		} catch ( Exception ex ) {	
			ex.printStackTrace();
			logger.error("Error en el metodo PkgSiiRentaMe.spInsertMsgRentaMe() : " + ex);
			throw new PkgSiiRentaMeException(ex);
		}
	}

	@Override
	public SpRegistraCuentaCutResult spRegistraCuentaCut(BigDecimal rut,
			String dv, BigDecimal formtipo, BigDecimal folio,
			BigDecimal instit, Date periodo, Date fechaCrea, Date fechaAct,
			Date fechaVto, BigDecimal moneda, BigDecimal saldo, String recti)
			throws PkgSiiRentaMeException {
		try {
			Connection conn = dataSource.getConnection();
			try {
				return SpRegistraCuentaCutCaller.execute(conn, rut, dv,
						formtipo, folio, instit, periodo, fechaCrea, fechaAct,
						fechaVto, moneda, saldo, recti);
			} finally {
				conn.close();
			}
		} catch (SQLException ex) {	
			ex.printStackTrace();
			logger.error("Error en el metodo PkgSiiRentaMe.spRegistraCuentaCut() : " + ex);
			throw new PkgSiiRentaMeException(ex);
		}
	}

	@Override
	public SpRegistraItemCutResult spRegistraItemCut(BigDecimal movid,
			BigDecimal codigo, String datoTipo, BigDecimal estado, String valor)
			throws PkgSiiRentaMeException {
		try {
			Connection conn = dataSource.getConnection();
			try {
				return SpRegistraItemCutCaller.execute(conn, movid, codigo,
						datoTipo, estado, valor);
			} finally {
				conn.close();
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.error("Error en el metodo PkgSiiRentaMe.spRegistraItemCut() : " + ex);
			throw new PkgSiiRentaMeException(ex);
		}
	}

	@Override
	public SpRegistraMovimientoCutResult spRegistraMovimientoCut(
			BigDecimal cuenta, BigDecimal numtrx, BigDecimal movtipo,
			Date fecha, Date fechaVto, BigDecimal formtipo, String formver,
			BigDecimal folio, BigDecimal moneda, BigDecimal monto,
			BigDecimal montopesos, BigDecimal valorcambio, BigDecimal estado,
			BigDecimal rutira, String dvira, BigDecimal fol01,
			BigDecimal cod9927, BigDecimal montoret, BigDecimal montoimp,
			BigDecimal montopag, Date periodocont)
			throws PkgSiiRentaMeException {
		try {
			Connection conn = dataSource.getConnection();
			try {
				return SpRegistraMovimientoCutCaller.execute(conn, cuenta,
						numtrx, movtipo, fecha, fechaVto, formtipo, formver,
						folio, moneda, monto, montopesos, valorcambio, estado,
						rutira, dvira, fol01, cod9927, montoret, montoimp,
						montopag, periodocont);
			} finally {
				conn.close();
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.error("Error en el metodo PkgSiiRentaMe.spRegistraMovimientoCut() : " + ex);
			throw new PkgSiiRentaMeException(ex);
		}
	}

	@Override
	public BuscarCutItemsResult buscarCutItems(BigDecimal idMov)
			throws PkgSiiRentaMeException {
		try {
			Connection conn = dataSource.getConnection();
			try {
				return BuscarCutItemsCaller.execute(conn, idMov);
			} finally {
				conn.close();
			}
		} catch (SQLException ex) {	
			ex.printStackTrace();
			logger.error("Error en el metodo PkgSiiRentaMe.buscarCutItems() : " + ex);
			throw new PkgSiiRentaMeException(ex);
		}
	}

	@Override
	public BigDecimal validaNumber(BigDecimal numberXML, BigDecimal rut,
			BigDecimal folio, BigDecimal formulario)
			throws PkgSiiRentaMeException {
		try {
			Connection conn = dataSource.getConnection();
			try {
				return ValidaNumberCaller.execute(conn, numberXML, rut, folio,
						formulario);
			} finally {
				conn.close();
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.error("Error en el metodo PkgSiiRentaMe.validaNumber() : " + ex);
			throw new PkgSiiRentaMeException(ex);
		}
	}

	@Override
	public BigDecimal validaLabel(String code) throws PkgSiiRentaMeException {
		try {
			Connection conn = dataSource.getConnection();
			try {
				return ValidaLabelCaller.execute(conn, code);
			} finally {
				conn.close();
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.error("Error en el metodo PkgSiiRentaMe.validaLabel() : " + ex);
			throw new PkgSiiRentaMeException(ex);
		}
	}

}
