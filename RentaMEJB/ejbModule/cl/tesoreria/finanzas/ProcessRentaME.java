package cl.tesoreria.finanzas; 

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;

import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.sql.RowSet;

import noNamespace.MessageDocument;
import noNamespace.MessageDocument.Message.Formulario;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlError;
import org.apache.xmlbeans.XmlOptions;

import cl.tesoreria.reca.pkgcutservicesmonex.ejb.PkgCutServicesMonexLocal;
import cl.tesoreria.reca.pkgcutservicesmonex.vo.TrxFormInVO;
import cl.tesoreria.reca.pkgcutservicesmonex.vo.TrxFormOutRecaMsgVO;
import cl.tesoreria.reca.pkgcutservicesmonex.vo.TrxFormOutVO;
import cl.tesoreria.AdfResult;
import cl.tesoreria.FormRenta;
import cl.tesoreria.FormularioR;
import cl.tesoreria.FormularioResult;
import cl.tesoreria.ItemCUT;
import cl.tesoreria.ItemRenta;
import cl.tesoreria.MessageID;
import cl.tesoreria.adf.adfprocedure.AdfprocedureException;
import cl.tesoreria.adf.adfprocedure.AdfprocedureLocal;
import cl.tesoreria.adf.adfprocedure.ProcessResult;
//import cl.tesoreria.finanzas.properties.CargarArchivo;
import cl.tesoreria.finanzas.tareas.TareasME;
import cl.tesoreria.renta.me.pkgsiirentame.ActRectificadaCtaByLlave2Result;
import cl.tesoreria.renta.me.pkgsiirentame.ActRectificadaCtaByLlaveResult;
import cl.tesoreria.renta.me.pkgsiirentame.BuscarCutItemsResult;
import cl.tesoreria.renta.me.pkgsiirentame.ItemCut;
import cl.tesoreria.renta.me.pkgsiirentame.PkgSiiRentaMeException;
import cl.tesoreria.renta.me.pkgsiirentame.PkgSiiRentaMeLocal;
import cl.tesoreria.renta.me.pkgsiirentame.SpBuscarcodigoobbymovResult;
import cl.tesoreria.renta.me.pkgsiirentame.SpBuscarcontitembyidmovResult;
import cl.tesoreria.renta.me.pkgsiirentame.SpBuscaridcuentacutbyformResult;
import cl.tesoreria.renta.me.pkgsiirentame.SpBuscaridmonedabynemoResult;
import cl.tesoreria.renta.me.pkgsiirentame.SpBuscaridtipomovbynemoResult;
import cl.tesoreria.renta.me.pkgsiirentame.SpBuscarmovrecmasmebyformResult;
import cl.tesoreria.renta.me.pkgsiirentame.SpExisteCuentabyfolioandrutResult;
import cl.tesoreria.renta.me.pkgsiirentame.SpExisteMovByLlave2Result;
import cl.tesoreria.renta.me.pkgsiirentame.SpExisteMovByLlaveResult;
import cl.tesoreria.renta.me.pkgsiirentame.SpExisteRegistradoFormmovtoResult;
import cl.tesoreria.renta.me.pkgsiirentame.SpFindMsgmovByLlave2Result;
import cl.tesoreria.renta.me.pkgsiirentame.SpFindMsgmovByLlaveResult;
import cl.tesoreria.renta.me.pkgsiirentame.SpGenerarfolio01meResult;
import cl.tesoreria.renta.me.pkgsiirentame.SpInsertMsgRentaMeResult;
import cl.tesoreria.renta.me.pkgsiirentame.SpRegistraCuentaCutResult;
import cl.tesoreria.renta.me.pkgsiirentame.SpRegistraMovimientoCutResult;

@Stateless(name = "ProcessRentaME", mappedName = "cl.tesoreria.finanzas.ProcessRentaME")
public class ProcessRentaME implements ProcessRentaMERemote,ProcessRentaMELocal {
	SessionContext sessionCtx;	
	@EJB AdfprocedureLocal adflocal;
	@EJB PkgSiiRentaMeLocal rentaMeEJB;	
	@EJB PkgCutServicesMonexLocal pkgCutServicesMonexEJB;
	FormularioResult formResult = new FormularioResult(); // MAN438: DHN
															// (20150128)
															// Instanciar
	FormularioR res = new FormularioR(); // MAN438: DHN (20150129) Instanciar
	String c250 = "0";
	String c251 = "0";
	String c252 = "0";
	private MessageDocument myMessageIn;	
	private static Logger logger = Logger.getLogger("cl.tesoreria.finpub.RentaMonedaExtranjera.ProcessRentaME");
	
	private TareasME tareasME = new TareasME();
	
	@Override
	public String receiverXML(String inputXML) {
		logger.info("[******** EJBME: Mensaje de entrada es: [" + inputXML + "] *******]");
		FormularioResult result;
		boolean parser = false;
		String myXmlOut = "ok";
		try {
			parser = parserMessage(inputXML);
			if (parser) {
				// TODO: se parsea la entrada en un objeto FormRenta
				FormRenta[] arrayFormRenta = buildFormulariosRenta(inputXML);
				FormRenta formulario = null;
				if (arrayFormRenta != null && arrayFormRenta.length > 1) {
					try {
						result = (FormularioResult) this
								.processDataRenta(arrayFormRenta);
					} catch (Exception ex) {
						logger.error(ex);
						throw new RentaMEGenericException(ex);
					}
					myXmlOut = Utils.buildMessageResponse(result,
							arrayFormRenta);
				} else {
					formulario = arrayFormRenta[0];
					String codeXML = formulario.getMessageId().getCode();
					if (codeXML != null && codeXML.length() > 0
							&& this.validaLabel(codeXML)) {
						result = (FormularioResult) this
								.processDataRenta(formulario);
					} else {
						// TODO: mensaje de Salida
						result = new FormularioResult();
						result.setResultCode(1);
						// mensaje
						AdfResult mensaje = new AdfResult();
						mensaje.setNumber("7");
						mensaje.setDescription("Mensaje no valido");
						mensaje.setObjectName("CODE");
						mensaje.setObjectValue(codeXML);
						Collection mensajes = new ArrayList();
						mensajes.add(mensaje);
						AdfResult[] mensajesADF = new AdfResult[mensajes.size()];
						result.setRecaMensajes((AdfResult[]) mensajes.toArray(mensajesADF));
					}
					myXmlOut = Utils.buildMessageResponse(result, myMessageIn);
				}
			} else {
				// TODO: mensaje de Salida
				result = new FormularioResult();
				result.setResultCode(1);
				// mensaje
				AdfResult mensaje = new AdfResult();
				mensaje.setNumber("7");
				mensaje.setDescription("Mensaje no valido.");
				mensaje.setObjectName("XML");
				mensaje.setObjectValue(inputXML);
				Collection mensajes = new ArrayList();
				mensajes.add(mensaje);

				AdfResult[] mensajesADF = new AdfResult[mensajes.size()];
				result.setRecaMensajes((AdfResult[]) mensajes.toArray(mensajesADF));
				myXmlOut = Utils.buildMessageResponse(result, myMessageIn);
			}
		} catch (RentaMException ex) {
			// TODO: mensaje de Salida
			ex.printStackTrace();
			logger.error("Error en el metodo ProcessRentaME.receiverXML()1 :" + ex);
			result = new FormularioResult();
			result.setResultCode(1);
			// mensaje
			AdfResult mensaje = new AdfResult();
			mensaje.setNumber("7");
			mensaje.setFormIndex("0");
			mensaje.setDescription(ex.getGlosaErr());
			mensaje.setObjectName(ex.getMethodName());
			mensaje.setObjectValue(ex.getValueErr());
			Collection mensajes = new ArrayList();
			mensajes.add(mensaje);

			AdfResult[] mensajesADF = new AdfResult[mensajes.size()];
			result.setRecaMensajes((AdfResult[]) mensajes.toArray(mensajesADF));
			myXmlOut = Utils.buildMessageResponse(result, myMessageIn);
		} catch (RentaMEGenericException g) {
			// log salida
			g.printStackTrace();
			logger.error("Error en el metodo ProcessRentaME.receiverXML()2 :" + g);
			logger.error(g);
			myXmlOut = Utils.buildMessageException(g);
		}
		logger.info("[******** RENTAME: Mensaje de Salida: \n" + myXmlOut + "\n ********]");
		return myXmlOut;
	}

	private boolean validaLabel(String code) {
		boolean esValido = false;
		try {
			BigDecimal existe = rentaMeEJB.validaLabel(code);
			// Si existe el CODE en la tabla SII_TIPO_MOV_ME
			if (existe.intValue() > 0)
				esValido = true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error en el metodo ProcessRentaME.validaLabel() :" + e);			
		}
		return esValido;
	}

	private boolean parserMessage(String MensajeIN)
			throws RentaMEGenericException {
		boolean isValid = false;
		String mensajesErrores = "";
		String respuesta = "";
		try {
			//private MessageDocument myMessageIn;
			
			myMessageIn = MessageDocument.Factory.parse(MensajeIN);
			// Create an XmlOptions instance and set the error listener.
			XmlOptions validateOptions = new XmlOptions();
			ArrayList listaErrores = new ArrayList();
			validateOptions.setErrorListener(listaErrores);
			// Validate the XML.
			isValid = myMessageIn.validate(validateOptions);

			// If the XML isn't valid, loop through the listener's contents,
			// printing contained messages.
			if (!isValid) {
				for (int i = 0; i < listaErrores.size(); i++) {
					XmlError error = (XmlError) listaErrores.get(i);
					XmlCursor cursor = error.getCursorLocation();
					cursor.toParent();
					mensajesErrores = mensajesErrores + "el Valor ( "
							+ cursor.getTextValue()
							+ " ) no es valido para el tag:" + cursor.xmlText()
							+ "Error:" + error.getMessage() + "\n";
					logger.info("ERRORES -->" + mensajesErrores);
				}
				logger.info("ERRORES -->" + mensajesErrores);
				return false;
			}
		} catch (Exception ex) {
			// throw new
			// RuntimeException("<ParserMessage> XML NO ES VALIDO ",ex);			
			ex.printStackTrace();
			logger.error("Error en el metodo ProcessRentaME.parserMessage() :" + ex);			
			throw new RentaMEGenericException(2);
		}
		return true;
	}

	protected FormRenta[] buildFormulariosRenta(String msgInput) {
		// formularios objetos parseados
		Formulario[] formulariosArray = this.myMessageIn.getMessage().getFormularioArray();
		// collecion de formularios queb va mos a procesar
		FormRenta[] formulariosOut = new FormRenta[formulariosArray.length];
		for (int i = 0; i < formulariosArray.length; i++) {
			Formulario formuI = formulariosArray[i];
			// crea objeto renta

			FormRenta rentaI = new FormRenta();
			rentaI.setMsgInput(msgInput);
			// setea cabecera de formulario
			rentaI.setNroForm(formuI.getNro().toString());
			rentaI.setRutIra(formuI.getIdentificacion().getRutIra().toString());
			rentaI.setDvIra(formuI.getIdentificacion().getDvIra());
			rentaI.setFolioF01(formuI.getIdentificacion().getFolioF01()
					.toString());
			rentaI.setFormulario(formuI.getIdentificacion().getFormulario()
					.toString());
			rentaI.setPeriodo(formuI.getIdentificacion().getPeriodo()
					.toString());
			rentaI.setFolioDecl(formuI.getIdentificacion().getFolioDecl()
					.toString());
			rentaI.setSigno(formuI.getIdentificacion().getSigno());
			rentaI.setRutContr(formuI.getIdentificacion().getRutContr()
					.toString());
			rentaI.setDvContr(formuI.getIdentificacion().getDvContr());
			rentaI.setMarcaFisc(formuI.getIdentificacion().getMarcaFisc()
					.toString());
			rentaI.setGlosaFisc(formuI.getIdentificacion().getGlosaFisc());
			rentaI.setMontoRet(formuI.getIdentificacion().getMontoRet()
					.toString());
			rentaI.setSignoMontoImp(formuI.getIdentificacion()
					.getSignoMontoImp());
			rentaI.setMontoImp(formuI.getIdentificacion().getMontoImp()
					.toString());
			rentaI.setSignoMontoPag(formuI.getIdentificacion()
					.getSignoMontoPag());
			rentaI.setMontoPago(formuI.getIdentificacion().getMontoPago()
					.toString());

			// setea messageId de mensaje
			MessageDocument.Message.MessageId messageId = myMessageIn
					.getMessage().getMessageId();
			MessageID msgID = new MessageID();
			msgID.setCode(messageId.getCode());
			msgID.setMsgDesc(messageId.getMsgDesc());
			msgID.setVersion(new Long(messageId.getVersion()).toString());
			msgID.setFechaVersion(messageId.getFechaVersion());
			msgID.setFromAddress(messageId.getFromAddress());
			msgID.setToAddress(messageId.getToAddress());
			msgID.setRefAddress(messageId.getRefAddress().toString());
			msgID.setDateTime(messageId.getDateTime());
			msgID.setValidado(messageId.getValidado());
			msgID.setNumber(messageId.getNumber().toString());
			msgID.setPeriodoContable(messageId.getPeriodoContable().toString());
			rentaI.setMessageId(msgID);

			// crear lista con los items
			Collection colitems = new ArrayList();
			MessageDocument.Message.Formulario.ListaCodigos codigos = formuI
					.getListaCodigos();
			int nroCodigos = codigos.getCodigosArray().length;
			int ini = 0;
			ItemRenta itemRenta;
			for (ini = 0; ini < nroCodigos; ini++) {
				String codigo = codigos.getCodigosArray(ini).getCodigo()
						.toString();
				String signo = codigos.getCodigosArray(ini).getSigno();
				String contenido = codigos.getCodigosArray(ini).getContenido();

				itemRenta = new ItemRenta();
				itemRenta.setCodigo(codigo);
				itemRenta.setSigno(signo);
				itemRenta.setContenido(contenido);
				colitems.add(itemRenta);
			}
			// agregar item 9920 al positivo
			String signoI = formuI.getIdentificacion().getSigno().trim();
			String codeI = msgID.getCode().trim();
			if (signoI != null
					&& signoI.length() > 0
					&& signoI.equals("+")
					&& codeI != null
					&& codeI.length() > 0
					&& (codeI.startsWith("R22") || codeI.startsWith("R29") || codeI
							.startsWith("R50"))) {

				itemRenta = new ItemRenta();
				itemRenta.setCodigo("9920");
				itemRenta.setSigno("+");
				itemRenta.setContenido(formuI.getIdentificacion()
						.getMarcaFisc().toString());
				colitems.add(itemRenta);
			}

			// DHN (2013-07-29) = ITIL00017255: Cut Monex Grabar 9939
			Date hoy = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			itemRenta = new ItemRenta();
			itemRenta.setCodigo("9939");
			itemRenta.setSigno("+");
			itemRenta.setContenido(sdf.format(hoy));
			colitems.add(itemRenta);

			// agregar lista al objeto renta
			ItemRenta[] array = new ItemRenta[colitems.size()];
			rentaI.setItemsRenta((ItemRenta[]) colitems.toArray(array));
			formulariosOut[i] = rentaI;
		}
		return formulariosOut;
	}

	private FormularioResult preValidacionFomsConReglas(FormRenta []form){     
        Collection erroresF=new ArrayList();               
        //sumarErrores(erroresF,Utils.regla004Retro(form)); 
        sumarErrores(erroresF,Utils.regla009Retro(form));
        sumarErrores(erroresF,Utils.regla0010Retro(form));
        
        
        if(erroresF!=null && erroresF.size()>0){
            formResult.setContestadorID("REGLAS_FORM");
            formResult.setResultCode(1);
            formResult.setResultMessage("NOK");            
            AdfResult[] array=new AdfResult[erroresF.size()];
            formResult.setRecaMensajes((AdfResult[])erroresF.toArray(array));  
        }else{    
            formResult.setContestadorID("REGLAS_FORM");
            formResult.setResultCode(0);
            formResult.setResultMessage("OK");
        }                            
        return this.formResult;
        
    }

	private FormularioResult validarFomulariosConReglas(FormRenta[] form){
        Collection erroresF=new ArrayList();
        
        sumarErrores(erroresF,Utils.regla001Retro(form));
        sumarErrores(erroresF,Utils.regla002Retro(form));
        sumarErrores(erroresF,Utils.regla003Retro(form));
        sumarErrores(erroresF,Utils.regla006Retro(form));
        sumarErrores(erroresF,Utils.regla008Retro(form));
        sumarErrores(erroresF,Utils.regla009Retro(form));
        sumarErrores(erroresF,Utils.regla0011Retro(form));
        sumarErrores(erroresF,Utils.regla012Retro(form));
        
        if(erroresF!=null && erroresF.size()>0){
            formResult.setContestadorID("REGLAS_FORM");
            formResult.setResultCode(1);
            formResult.setResultMessage("NOK");            
            AdfResult[] array=new AdfResult[erroresF.size()];
            formResult.setRecaMensajes((AdfResult[])erroresF.toArray(array));  
        }else{    
            formResult.setContestadorID("REGLAS_FORM");
            formResult.setResultCode(0);
            formResult.setResultMessage("OK");
        }                            
        return this.formResult;
    }

	private void sumarErrores(Collection erroresF, Collection errores) {
		if (errores != null && errores.size() > 0) {
			Iterator ite = null;
			for (ite = errores.iterator(); ite.hasNext();) {
				AdfResult adf = (AdfResult) ite.next();
				erroresF.add(adf);
			}
		}
	}

	private FormularioResult validaFormularioConReglas(FormRenta form) throws RentaMException {
		Collection errores = new ArrayList();
		if (form.getMessageId().getCode().equalsIgnoreCase("RECMASME")) {
			// TODO: regla 126
			if (!Utils.regla126(form)) {
				AdfResult adf = new AdfResult();
				adf.setDescription("No existe formulario,RutContr,Folio,Periodo,items[87,91,315,82]");
				adf.setFormIndex("0");
				adf.setModule("");
				adf.setNumber("126");
				adf.setObjectDesc("");
				adf.setObjectName("87");
				adf.setObjectValue("");
				adf.setSeverity("3");
				errores.add(adf);
			}

			if (!Utils.regla127(form)) {
				AdfResult adf = new AdfResult();
				adf.setDescription("El rutContribuyente debe ser valido");
				adf.setFormIndex("0");
				adf.setModule("");
				adf.setNumber("127");
				adf.setObjectDesc("");
				adf.setObjectName("0000");
				adf.setObjectValue(form.getRutContr());
				adf.setSeverity("0");
				errores.add(adf);
			}

			if (!Utils.regla128(form)) {
				AdfResult adf = new AdfResult();
				adf.setDescription("El campo [03] debe ser valido..");
				adf.setFormIndex("0");
				adf.setModule("");
				adf.setNumber("128");
				adf.setObjectDesc("");
				adf.setObjectName("03");
				adf.setObjectValue(Utils.extraeContenidoByCodigo(form, "03"));
				adf.setSeverity("3");
				errores.add(adf);
			}

			if (!Utils.regla129(form)) {
				AdfResult adf = new AdfResult();
				adf.setDescription("El campo [03] = RUTCont");
				adf.setFormIndex("0");
				adf.setModule("");
				adf.setNumber("129");
				adf.setObjectDesc("");
				adf.setObjectName("03");
				adf.setObjectValue(Utils.extraeContenidoByCodigo(form, "03"));
				adf.setSeverity("3");
				errores.add(adf);
			}

			if (!Utils.regla130(form)) {
				AdfResult adf = new AdfResult();
				adf.setDescription("El Resto de [07]/10=(ultimo digito del año indicado en el campo periodo tributario)");
				adf.setFormIndex("0");
				adf.setModule("");
				adf.setNumber("130");
				adf.setObjectDesc("");
				adf.setObjectName("07");
				adf.setObjectValue(Utils.extraeContenidoByCodigo(form, "07"));
				adf.setSeverity("3");
				errores.add(adf);
			}

			if (!Utils.regla131(form)) {
				AdfResult adf = new AdfResult();
				adf.setDescription("Los valores validos para la marca son 0 ó 3..");
				adf.setFormIndex("0");
				adf.setModule("");
				adf.setNumber("131");
				adf.setObjectDesc("");
				adf.setObjectName("0000");
				adf.setObjectValue(form.getMarcaFisc());
				adf.setSeverity("3");
				errores.add(adf);
			}

			if (!Utils.regla132(form)) {
				AdfResult adf = new AdfResult();
				adf.setDescription("No cumple regla: Si campo[87] mayor 0 y (Monto a Retener)=[87]");
				adf.setFormIndex("0");
				adf.setModule("");
				adf.setNumber("132");
				adf.setObjectDesc("");
				adf.setObjectName("9999");
				adf.setObjectValue(form.getMontoRet());
				adf.setSeverity("3");
				errores.add(adf);
			}

			if (!Utils.regla133(form)) {
				AdfResult adf = new AdfResult();
				adf.setDescription("No cumple: Si Marca=3 entonces (Monto a Retener)=0");
				adf.setFormIndex("0");
				adf.setModule("");
				adf.setNumber("133");
				adf.setObjectDesc("");
				adf.setObjectName("0000");
				adf.setObjectValue(form.getMarcaFisc());
				adf.setSeverity("3");
				errores.add(adf);
			}

			if (!Utils.regla135(form)) {
				AdfResult adf = new AdfResult();
				adf.setDescription("No cumple regla: Campo [315] debe corresponder a una fecha valida..");
				adf.setFormIndex("0");
				adf.setModule("");
				adf.setNumber("135");
				adf.setObjectDesc("");
				adf.setObjectName("315");
				adf.setObjectValue(Utils.extraeContenidoByCodigo(form, "315"));
				adf.setSeverity("3");
				errores.add(adf);
			}

			if (!Utils.regla136(form)) {
				AdfResult adf = new AdfResult();
				adf.setDescription("No cumple regla: Campo [315] menor Fecha Actual..");
				adf.setFormIndex("0");
				adf.setModule("");
				adf.setNumber("136");
				adf.setObjectDesc("");
				adf.setObjectName("315");
				adf.setObjectValue(Utils.extraeContenidoByCodigo(form, "315"));
				adf.setSeverity("3");
				errores.add(adf);
			}

			if (!Utils.regla137(form)) {
				AdfResult adf = new AdfResult();
				adf.setDescription("No cumple regla:Marca=0 entonces [87]=(MontoRet)");
				adf.setFormIndex("0");
				adf.setModule("");
				adf.setNumber("137");
				adf.setObjectDesc("");
				adf.setObjectName("0000");
				adf.setObjectValue(form.getMarcaFisc());
				adf.setSeverity("3");
				errores.add(adf);
			}

			if (!Utils.regla138(form)) {
				AdfResult adf = new AdfResult();
				adf.setDescription("No cumple regla: Si [87] mayor a 0, entonces, Marca=0");
				adf.setFormIndex("0");
				adf.setModule("");
				adf.setNumber("138");
				adf.setObjectDesc("");
				adf.setObjectName("87");
				adf.setObjectValue(Utils.extraeContenidoByCodigo(form, "87"));
				adf.setSeverity("3");
				errores.add(adf);
			}

			if (!Utils.regla139(form)) {
				AdfResult adf = new AdfResult();
				adf.setDescription("No cumple regla: Si [87]=0 entonces Marca=3 ..");
				adf.setFormIndex("0");
				adf.setModule("");
				adf.setNumber("139");
				adf.setObjectDesc("");
				adf.setObjectName("0000");
				adf.setObjectValue(form.getMarcaFisc());
				adf.setSeverity("3");
				errores.add(adf);
			}

			if (!Utils.regla140(form)) {
				AdfResult adf = new AdfResult();
				adf.setDescription("No cumple: Si [87] mayor 0 entonces [91]=0 ..");
				adf.setFormIndex("0");
				adf.setModule("");
				adf.setNumber("140");
				adf.setObjectDesc("");
				adf.setObjectName("91");
				adf.setObjectValue(Utils.extraeContenidoByCodigo(form, "91"));
				adf.setSeverity("3");
				errores.add(adf);
			}

			if (!Utils.regla142(form)) {
				AdfResult adf = new AdfResult();
				adf.setDescription("No cumple regla: Debe venir [9927] ..");
				adf.setFormIndex("0");
				adf.setModule("");
				adf.setNumber("142");
				adf.setObjectDesc("");
				adf.setObjectName("9927");
				adf.setObjectValue(Utils.extraeContenidoByCodigo(form, "9927"));
				adf.setSeverity("3");
				errores.add(adf);
			}

			if (!Utils.regla154(form)) {
				AdfResult adf = new AdfResult();
				adf.setDescription("No cumple regla: Debe venir los items [88xx] ..");
				adf.setFormIndex("0");
				adf.setModule("");
				adf.setNumber("154");
				adf.setObjectDesc("");
				adf.setObjectName("0000");
				adf.setObjectValue("");
				adf.setSeverity("3");
				errores.add(adf);
			}

			if (!Utils.regla156(form)) {
				AdfResult adf = new AdfResult();
				adf.setDescription("No cumple regla: El item 8822 debe ser valido ..");
				adf.setFormIndex("0");
				adf.setModule("");
				adf.setNumber("156");
				adf.setObjectDesc("");
				adf.setObjectName("8822");
				adf.setObjectValue(Utils.extraeContenidoByCodigo(form, "8822"));
				adf.setSeverity("3");
				errores.add(adf);
			}

			if (!Utils.regla157(form)) {
				AdfResult adf = new AdfResult();
				adf.setDescription("No cumple regla: item 8822!= CLP");
				adf.setFormIndex("0");
				adf.setModule("");
				adf.setNumber("157");
				adf.setObjectDesc("");
				adf.setObjectName("0000");
				adf.setObjectValue("");
				adf.setSeverity("3");
				errores.add(adf);
			}

			if (!Utils.regla158(form)) {
				AdfResult adf = new AdfResult();
				adf.setDescription("No cumple regla:[8833]!=1");
				adf.setFormIndex("0");
				adf.setModule("");
				adf.setNumber("158");
				adf.setObjectDesc("");
				adf.setObjectName("0000");
				adf.setObjectValue("");
				adf.setSeverity("3");
				errores.add(adf);
			}

			if (!Utils.regla159(form)) {
				AdfResult adf = new AdfResult();
				adf.setDescription("No cumple regla:[8844] mayor 0 entonces [8833] mayor 0");
				adf.setFormIndex("0");
				adf.setModule("");
				adf.setNumber("159");
				adf.setObjectDesc("");
				adf.setObjectName("0000");
				adf.setObjectValue("");
				adf.setSeverity("3");
				errores.add(adf);
			}

			if (!Utils.regla160(form)) {
				AdfResult adf = new AdfResult();
				adf.setDescription("No cumple regla:8844 mayor 0  y 8822 != CLP");
				adf.setFormIndex("0");
				adf.setModule("");
				adf.setNumber("160");
				adf.setObjectDesc("");
				adf.setObjectName("0000");
				adf.setObjectValue("");
				adf.setSeverity("3");
				errores.add(adf);
			}

			if (!Utils.regla161(form)) {
				AdfResult adf = new AdfResult();
				adf.setDescription("No cumple regla: 8844 mayor 0");
				adf.setFormIndex("0");
				adf.setModule("");
				adf.setNumber("161");
				adf.setObjectDesc("");
				adf.setObjectName("0000");
				adf.setObjectValue("");
				adf.setSeverity("3");
				errores.add(adf);
			}
			if (!Utils.regla162(form)) {
				AdfResult adf = new AdfResult();
				adf.setDescription("No cumple regla: 8822 es valido entonces 8877=''");
				adf.setFormIndex("0");
				adf.setModule("");
				adf.setNumber("162");
				adf.setObjectDesc("");
				adf.setObjectName("0000");
				adf.setObjectValue("");
				adf.setSeverity("3");
				errores.add(adf);
			}

			if (!Utils.regla163(form)) {
				AdfResult adf = new AdfResult();
				adf.setDescription("No cumple regla: si [87] mayor 0 entonces [8844]=0");
				adf.setFormIndex("0");
				adf.setModule("");
				adf.setNumber("163");
				adf.setObjectDesc("");
				adf.setObjectName("0000");
				adf.setObjectValue("");
				adf.setSeverity("3");
				errores.add(adf);
			}

			if (!Utils.regla164(form)) {
				AdfResult adf = new AdfResult();
				adf.setDescription("si[8811] !=CLP entonces [8866] mayor 0");
				adf.setFormIndex("0");
				adf.setModule("");
				adf.setNumber("164");
				adf.setObjectDesc("");
				adf.setObjectName("0000");
				adf.setObjectValue("");
				adf.setSeverity("3");
				errores.add(adf);
			}

			if (!Utils.regla165(form)) {
				AdfResult adf = new AdfResult();
				adf.setDescription("si el campo [8822]=' ' entonces [8844]=0");
				adf.setFormIndex("0");
				adf.setModule("");
				adf.setNumber("165");
				adf.setObjectDesc("");
				adf.setObjectName("0000");
				adf.setObjectValue("");
				adf.setSeverity("3");
				errores.add(adf);
			}

			// crear VO de salida
			if (errores.size() > 0) {
				formResult.setContestadorID("REGLAS_FORM");
				formResult.setResultCode(1);
				formResult.setResultMessage("NOK");
				AdfResult[] array = new AdfResult[errores.size()];
				formResult
						.setRecaMensajes((AdfResult[]) errores.toArray(array));
			} else {
				formResult.setContestadorID("REGLAS_FORM");
				formResult.setResultCode(0);
				formResult.setResultMessage("OK");
			}

		} else if (form.getMessageId().getCode().equalsIgnoreCase("RTRMASME")) {

			if (!Utils.regla143(form)) {
				AdfResult adf = new AdfResult();
				adf.setDescription("RUT,folio,tipform,periodo,[87,91,315,82] no existen");
				adf.setFormIndex("0");
				adf.setModule("");
				adf.setNumber("143");
				adf.setObjectDesc("");
				adf.setObjectName("0000");
				adf.setObjectValue("");
				adf.setSeverity("3");
				errores.add(adf);
			}

			if (!Utils.regla144(form)) {
				AdfResult adf = new AdfResult();
				adf.setDescription("Noi cumple regla: [003] y [007] no existen..");
				adf.setFormIndex("0");
				adf.setModule("");
				adf.setNumber("144");
				adf.setObjectDesc("");
				adf.setObjectName("0000");
				adf.setObjectValue("");
				adf.setSeverity("3");
				errores.add(adf);
			}

			if (!Utils.regla145(form)) {
				AdfResult adf = new AdfResult();
				adf.setDescription("No cumple regla: Si [003]=Rut Contrib..");
				adf.setFormIndex("0");
				adf.setModule("");
				adf.setNumber("145");
				adf.setObjectDesc("");
				adf.setObjectName("0000");
				adf.setObjectValue("");
				adf.setSeverity("3");
				errores.add(adf);
			}

			if (!Utils.regla146(form)) {
				AdfResult adf = new AdfResult();
				adf.setDescription("No cumple regla: Si [007]=Numero de Folio..");
				adf.setFormIndex("0");
				adf.setModule("");
				adf.setNumber("146");
				adf.setObjectDesc("");
				adf.setObjectName("0000");
				adf.setObjectValue("");
				adf.setSeverity("3");
				errores.add(adf);
			}

			if (!Utils.regla149(form)) {
				AdfResult adf = new AdfResult();
				adf.setDescription("No cumple regla: Campo Marca=7..");
				adf.setFormIndex("0");
				adf.setModule("");
				adf.setNumber("149");
				adf.setObjectDesc("");
				adf.setObjectName("0000");
				adf.setObjectValue("");
				adf.setSeverity("3");
				errores.add(adf);
			}

			if (!Utils.regla150(form)) {
				AdfResult adf = new AdfResult();
				adf.setDescription("No cumple regla: Tipo Formulario=022..");
				adf.setFormIndex("0");
				adf.setModule("");
				adf.setNumber("150");
				adf.setObjectDesc("");
				adf.setObjectName("0000");
				adf.setObjectValue("");
				adf.setSeverity("3");
				errores.add(adf);
			}

			if (!Utils.regla151(form)) {
				AdfResult adf = new AdfResult();
				adf.setDescription("No cumple regla: Signo debe ser - ..");
				adf.setFormIndex("0");
				adf.setModule("");
				adf.setNumber("151");
				adf.setObjectDesc("");
				adf.setObjectName("0000");
				adf.setObjectValue("");
				adf.setSeverity("3");
				errores.add(adf);
			}

			if (!regla152(form)) {
				AdfResult adf = new AdfResult();
				adf.setDescription("No cumple:RECMAS tiene [82]entonces RTRMASME Y [82] de igual valor ..");
				adf.setFormIndex("0");
				adf.setModule("");
				adf.setNumber("152");
				adf.setObjectDesc("");
				adf.setObjectName("0000");
				adf.setObjectValue("");
				adf.setSeverity("3");
				errores.add(adf);
			}

			if (!Utils.regla153(form)) {
				AdfResult adf = new AdfResult();
				adf.setDescription("No cumple regla:[315]menor 30/04 del año en curso");
				adf.setFormIndex("0");
				adf.setModule("");
				adf.setNumber("153");
				adf.setObjectDesc("");
				adf.setObjectName("0000");
				adf.setObjectValue("");
				adf.setSeverity("3");
				errores.add(adf);
			}

			if (!Utils.regla154(form)) {
				AdfResult adf = new AdfResult();
				adf.setDescription("No existen items [88xx]");
				adf.setFormIndex("0");
				adf.setModule("");
				adf.setNumber("154");
				adf.setObjectDesc("");
				adf.setObjectName("0000");
				adf.setObjectValue("");
				adf.setSeverity("3");
				errores.add(adf);
			}

			if (!Utils.regla156(form)) {
				AdfResult adf = new AdfResult();
				adf.setDescription("No cumple regla: El item 8822 debe ser valido");
				adf.setFormIndex("0");
				adf.setModule("");
				adf.setNumber("156");
				adf.setObjectDesc("");
				adf.setObjectName("0000");
				adf.setObjectValue("");
				adf.setSeverity("3");
				errores.add(adf);
			}

			if (!Utils.regla157(form)) {
				AdfResult adf = new AdfResult();
				adf.setDescription("No cumple regla: 8822 != CLP");
				adf.setFormIndex("0");
				adf.setModule("");
				adf.setNumber("157");
				adf.setObjectDesc("");
				adf.setObjectName("0000");
				adf.setObjectValue("");
				adf.setSeverity("3");
				errores.add(adf);
			}

			if (!Utils.regla158(form)) {
				AdfResult adf = new AdfResult();
				adf.setDescription("No cumple regla: item 8833 != 1");
				adf.setFormIndex("0");
				adf.setModule("");
				adf.setNumber("158");
				adf.setObjectDesc("");
				adf.setObjectName("0000");
				adf.setObjectValue("");
				adf.setSeverity("3");
				errores.add(adf);
			}

			if (!Utils.regla159(form)) {
				AdfResult adf = new AdfResult();
				adf.setDescription("No cumple regla: si 8844Mayor0 entonces 8833 MAYOR 0");
				adf.setFormIndex("0");
				adf.setModule("");
				adf.setNumber("159");
				adf.setObjectDesc("");
				adf.setObjectName("0000");
				adf.setObjectValue("");
				adf.setSeverity("3");
				errores.add(adf);
			}
			if (!Utils.regla160(form)) {
				AdfResult adf = new AdfResult();
				adf.setDescription("No cumple regla:8844 MAYOR 0 entonces 8822 !=CLP");
				adf.setFormIndex("0");
				adf.setModule("");
				adf.setNumber("160");
				adf.setObjectDesc("");
				adf.setObjectName("0000");
				adf.setObjectValue("");
				adf.setSeverity("3");
				errores.add(adf);
			}

			if (!Utils.regla161(form)) {
				AdfResult adf = new AdfResult();
				adf.setDescription("No cumple regla: si el campo 8844 Mayor 0 ");
				adf.setFormIndex("0");
				adf.setModule("");
				adf.setNumber("161");
				adf.setObjectDesc("");
				adf.setObjectName("0000");
				adf.setObjectValue("");
				adf.setSeverity("3");
				errores.add(adf);
			}
			if (!Utils.regla162(form)) {
				AdfResult adf = new AdfResult();
				adf.setDescription("No cumple regla:8822 es valido entonces 8877=''");
				adf.setFormIndex("0");
				adf.setModule("");
				adf.setNumber("162");
				adf.setObjectDesc("");
				adf.setObjectName("0000");
				adf.setObjectValue("");
				adf.setSeverity("3");
				errores.add(adf);
			}

			if (!Utils.regla163(form)) {
				AdfResult adf = new AdfResult();
				adf.setDescription("No cumple: [87] MAYOR 0 entonces [8844] = 0..");
				adf.setFormIndex("0");
				adf.setModule("");
				adf.setNumber("163");
				adf.setObjectDesc("");
				adf.setObjectName("0000");
				adf.setObjectValue("");
				adf.setSeverity("3");
				errores.add(adf);
			}

			if (!Utils.regla164(form)) {
				AdfResult adf = new AdfResult();
				adf.setDescription("No cumple regla:[8811] != CLP entonces [8866] MAYOR 0");
				adf.setFormIndex("0");
				adf.setModule("");
				adf.setNumber("164");
				adf.setObjectDesc("");
				adf.setObjectName("0000");
				adf.setObjectValue("");
				adf.setSeverity("3");
				errores.add(adf);
			}

			if (!Utils.regla165(form)) {
				AdfResult adf = new AdfResult();
				adf.setDescription("No cumple regla:[8822]=' ' entonces [8844]=0");
				adf.setFormIndex("0");
				adf.setModule("");
				adf.setNumber("165");
				adf.setObjectDesc("");
				adf.setObjectName("0000");
				adf.setObjectValue("");
				adf.setSeverity("3");
				errores.add(adf);
			}

			// crear VO de salida
			if (errores.size() > 0) {
				formResult.setContestadorID("REGLAS_FORM");
				formResult.setResultCode(1);
				formResult.setResultMessage("NOK");
				AdfResult[] array = new AdfResult[errores.size()];
				formResult
						.setRecaMensajes((AdfResult[]) errores.toArray(array));
			} else {
				formResult.setContestadorID("REGLAS_FORM");
				formResult.setResultCode(0);
				formResult.setResultMessage("OK");
			}

		} else if (form.getMessageId().getCode().equalsIgnoreCase("RECAINME")) {

			if (!Utils.regla126b(form)) {
				AdfResult adf = new AdfResult();
				adf.setDescription("formulario invalido");
				adf.setFormIndex("0");
				adf.setModule("");
				adf.setNumber("126");
				adf.setObjectDesc("");
				adf.setObjectName("0000");
				adf.setObjectValue("");
				adf.setSeverity("3");
				errores.add(adf);
			}

			if (!Utils.regla127(form)) {
				AdfResult adf = new AdfResult();
				adf.setDescription("El rut del Contribuyente debe ser valido..");
				adf.setFormIndex("0");
				adf.setModule("");
				adf.setNumber("128");
				adf.setObjectDesc("");
				adf.setObjectName("0000");
				adf.setObjectValue("");
				adf.setSeverity("0");
				errores.add(adf);
			}

			if (!Utils.regla128(form)) {
				AdfResult adf = new AdfResult();
				adf.setDescription("El campo [03] debe ser valido..");
				adf.setFormIndex("0");
				adf.setModule("");
				adf.setNumber("129a");
				adf.setObjectDesc("");
				adf.setObjectName("0000");
				adf.setObjectValue("");
				adf.setSeverity("3");
				errores.add(adf);
			}

			if (!Utils.regla129(form)) {
				AdfResult adf = new AdfResult();
				adf.setDescription("campo[03]ser igual campo RUTContr");
				adf.setFormIndex("0");
				adf.setModule("");
				adf.setNumber("129b");
				adf.setObjectDesc("");
				adf.setObjectName("0000");
				adf.setObjectValue("");
				adf.setSeverity("3");
				errores.add(adf);
			}

			if (form.getMessageId().getFromAddress().compareToIgnoreCase("SII") == 0) {
				if (!Utils.validarCamposObligNumerosFromSii(form)) {
					AdfResult adf = new AdfResult();
					adf.setDescription("items que deben venir con decimales [30,563,68,62,565,120,122,123,700,701,711,703,595,547,91,92,93,94]");
					adf.setFormIndex("0");
					adf.setModule("");
					adf.setNumber("154");
					adf.setObjectDesc("");
					adf.setObjectName("0000");
					adf.setObjectValue("");
					adf.setSeverity("3");
					errores.add(adf);
				}

				if (!Utils.regla167(form)) {
					AdfResult adf = new AdfResult();
					adf.setDescription("regla: [03] mayor 50000000 entonces [01]!='' Y [02]=''Y [05]=''");
					adf.setFormIndex("0");
					adf.setModule("");
					adf.setNumber("130");
					adf.setObjectDesc("");
					adf.setObjectName("0000");
					adf.setObjectValue("");
					adf.setSeverity("3");
					errores.add(adf);
				}
			}

			// crear VO de salida
			if (errores.size() > 0) {
				formResult.setContestadorID("REGLAS_FORM");
				formResult.setResultCode(1);
				formResult.setResultMessage("NOK");
				AdfResult[] array = new AdfResult[errores.size()];
				formResult
						.setRecaMensajes((AdfResult[]) errores.toArray(array));
			} else {
				formResult.setContestadorID("REGLAS_FORM");
				formResult.setResultCode(0);
				formResult.setResultMessage("OK");
			}

		} else if (form.getMessageId().getCode().equalsIgnoreCase("EGRESO")) {
			// crear VO de salida
			if (errores.size() > 0) {
				formResult.setContestadorID("REGLAS_FORM");
				formResult.setResultCode(1);
				formResult.setResultMessage("NOK");
				AdfResult[] array = new AdfResult[errores.size()];
				formResult
						.setRecaMensajes((AdfResult[]) errores.toArray(array));
			} else {
				formResult.setContestadorID("REGLAS_FORM");
				formResult.setResultCode(0);
				formResult.setResultMessage("OK");
			}
		} else if (form.getMessageId().getCode().equalsIgnoreCase("GIRO45ME")
				|| form.getMessageId().getCode().equalsIgnoreCase("DESC45ME")) {
			// crear VO de salida

			if (!Utils.regla169(form)) {
				AdfResult adf = new AdfResult();
				adf.setDescription("No tiene item [8811]");
				adf.setFormIndex("0");
				adf.setModule("");
				adf.setNumber("169");
				adf.setObjectDesc("");
				adf.setObjectName("0000");
				adf.setObjectValue("");
				adf.setSeverity("3");
				errores.add(adf);
			}

			if (!Utils.regla170(form)) {
				AdfResult adf = new AdfResult();
				adf.setDescription("No tiene item [8866]");
				adf.setFormIndex("0");
				adf.setModule("");
				adf.setNumber("170");
				adf.setObjectDesc("");
				adf.setObjectName("0000");
				adf.setObjectValue("");
				adf.setSeverity("3");
				errores.add(adf);
			}

			if (!Utils.regla171(form)) {
				AdfResult adf = new AdfResult();
				adf.setDescription("No tiene item [8877]");
				adf.setFormIndex("0");
				adf.setModule("");
				adf.setNumber("170");
				adf.setObjectDesc("");
				adf.setObjectName("0000");
				adf.setObjectValue("");
				adf.setSeverity("3");
				errores.add(adf);
			}

			if (errores.size() > 0) {
				formResult.setContestadorID("REGLAS_FORM");
				formResult.setResultCode(1);
				formResult.setResultMessage("NOK");
				AdfResult[] array = new AdfResult[errores.size()];
				formResult
						.setRecaMensajes((AdfResult[]) errores.toArray(array));
			} else {
				formResult.setContestadorID("REGLAS_FORM");
				formResult.setResultCode(0);
				formResult.setResultMessage("OK");
			}
		} else if (form.getMessageId().getCode().equalsIgnoreCase("GIRO21ME")
				|| form.getMessageId().getCode().equalsIgnoreCase("DESC21ME")
				|| form.getMessageId().getCode().equalsIgnoreCase("RECTIF")) {
			if (errores.size() > 0) {
				formResult.setContestadorID("REGLAS_FORM");
				formResult.setResultCode(1);
				formResult.setResultMessage("NOK");
				AdfResult[] array = new AdfResult[errores.size()];
				formResult
						.setRecaMensajes((AdfResult[]) errores.toArray(array));
			} else {
				formResult.setContestadorID("REGLAS_FORM");
				formResult.setResultCode(0);
				formResult.setResultMessage("OK");
			}
		} else {
			formResult.setContestadorID("REGLAS_FORM");
			formResult.setResultCode(1);
			formResult.setResultMessage("NOK");

			AdfResult mensaje = new AdfResult();
			mensaje.setNumber("7");
			mensaje.setDescription("Este formulario no se encuentra registrado como valido...");
			mensaje.setObjectName("ProcessRentaME" + "." + "validaFormularioConReglas");
			mensaje.setObjectValue(form.getMessageId().getCode());
			mensaje.setFormIndex("0");
			mensaje.setModule("");
			mensaje.setObjectDesc("");
			mensaje.setSeverity("3");
			errores.add(mensaje);
			AdfResult[] array = new AdfResult[errores.size()];
			formResult.setRecaMensajes((AdfResult[]) errores.toArray(array));
		}
		return formResult;
	}

	private boolean regla152(FormRenta form) throws RentaMException {
		BigDecimal idCuenta = buscarIDCuentaCUTByForm(form);
		if (idCuenta != null && idCuenta.intValue() > 0) {
			FormularioR formBD = existeRegistradoMovRECMASME(idCuenta);
			if (formBD != null && formBD.isExisteRECMAS()) {
				if (esIgualCod82conFormulario(formBD, form)) {
					return true;
				} else
					return false;

			} else
				return false;
		} else {
			// TODO: retornal malo...
			return false;
		}
	}

	private boolean esIgualCod82conFormulario(FormularioR formBD, FormRenta form) {
		String idMOV = formBD.getIdMov();
		SpBuscarcontitembyidmovResult resultado = null;
		try {
			resultado = rentaMeEJB.spBuscarcontitembyidmov(new BigDecimal(idMOV));
		} catch (PkgSiiRentaMeException ex) {
			logger.error("[******** RENTAMEEJB:  exception ********]", ex);

		}

		String cont82Form = null;
		if (Utils.extraeContenidoByCodigo(form, "82") != null
				&& ((String) Utils.extraeContenidoByCodigo(form, "82"))
						.length() > 0) {
			cont82Form = Utils.extraeContenidoByCodigo(form, "82");
		} else {
			cont82Form = "XXXX";
		}

		String cont82RECMAS = null;
		if (resultado.getCont82() != null && resultado.getCont82().length() > 0) {
			cont82RECMAS = resultado.getCont82();
			if (!cont82Form.equals("XXXX")) {
				if (cont82Form.equalsIgnoreCase(cont82RECMAS)) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	private void sumaErroresADF(Collection colErrFinal,AdfResult []array,FormRenta form){

        for(int i=0;i<array.length;i++){
            AdfResult indI=array[i]; 
            indI.setFormIndex(form.getNroForm());      
            colErrFinal.add(indI);
        }    
    }

	private FormularioResult validaFormulariosByADF(FormRenta[] form)throws RentaMException{
        //procesa cada formulario, desde el 2° parea saber si es valido         
        Collection erroresADF=new ArrayList();
        int countGD=0;
        boolean error=false;
        int indexProbl=0;
        for(int i=0; i<form.length;i++){            
            formResult=adfValidateFormulario(form[i]);
            if(formResult!=null && formResult.getResultCode()!=0){
                sumaErroresADF(erroresADF,formResult.getMensajesArray(),form[i]);
            }    
        }
        //ecvalua si los mensaje fueron correctos 
        if(erroresADF!=null && erroresADF.size()>0){
            formResult.setResultCode(3);
            formResult.setContestadorID("ADF");
            formResult.setResultMessage("NOK");  
            AdfResult []array=new AdfResult[erroresADF.size()];                      
            formResult.setRecaMensajes((AdfResult[]) erroresADF.toArray(array));
        }else{
            formResult.setContestadorID("ADF");
            formResult.setResultCode(0);
            formResult.setResultMessage("OK");                                   
        }                            
        return formResult;
    }

	@Override
	public FormularioResult processDataRenta(FormRenta[] form)
			throws RentaMException, RentaMEGenericException {
		// Se valida el NUMBER del XML
		// (MAN471 - DHN - 20160229)
		BigDecimal numberValido = null;
		for (int i = 0; form.length > i; i++) {
			if (form[i].getSigno().equals("+")) {
				formResult = this.validaNumberXML(form[i]);
				if (formResult != null) {
					return formResult;
				}
			}
		}

		formResult = new FormularioResult();
		// normalizar negativas para enviarlas a la ADF y tener los items con
		// sus tipos
		formResult.setPeriodo(form[0].getPeriodo());
		formResult = preValidacionFomsConReglas(form);

		if (formResult != null && (formResult.getResultCode() == 0)) {
			logger.info("[******** RENTAMEEJB: va a normalizar los formularios negativos *****");
			formResult = existeAllPrimSinRectif(form);
			if (formResult.isExisteAllPrimitivas()) {
				normaliceFormsNegativos(form); // MAN331: DHN (20151222)
				normalizaFormPostivoCambio315(form);
				if (form != null) {
					// Validar los formularios con reglas
					formResult = validarFomulariosConReglas(form);
					if (formResult != null && (formResult.getResultCode() == 0)) {
						// validar formulario adf
						formResult = validaFormulariosByADF(form);
						if (formResult != null
								&& (formResult.getResultCode() == 0)) {
							formResult = procesaFormularios(form);
						} else {
							// registra errores en la BD
							guardarMensajeToBD(form);
						}
					} else {
						// registra errores en la BD
						logger.info("[******** RENTAMEEJB: guardar los mensajes de errores *****");
						guardarMensajeToBD(form);
					}
					return formResult;
				} else {
					FormularioResult result = new FormularioResult();
					result = new FormularioResult();
					result.setResultCode(1);
					result.setResultMessage("NOK");
					// mensaje
					AdfResult mensaje = new AdfResult();
					mensaje.setNumber("7");
					mensaje.setDescription("el Formulario Renta esta nulo..");
					mensaje.setObjectName("ProcessRentaME" + "." + "processDataRenta");
					mensaje.setObjectValue(form.toString());
					Collection mensajes = new ArrayList();
					mensajes.add(mensaje);

					AdfResult[] mensajesADF = new AdfResult[mensajes.size()];
					result.setRecaMensajes((AdfResult[]) mensajes
							.toArray(mensajesADF));
					return result;
				}
			} else {
				// se rechaza mensaje
				logger.info("[******** RENTAMEEJB: guarda el mensaje completo con errores *****");
				guardarMensajeToBD(form);
				return formResult;
			}
		} else {
			logger.info("[******** RENTAMEEJB: guardar los mensajes de errores *****");
			guardarMensajeToBD(form);
			return formResult;
		}
	}

	@Override
	public FormularioResult processDataRenta(FormRenta form)
			throws RentaMException, RentaMEGenericException {

		if (form != null) {

			// Se valida el NUMBER del XML
			// (MAN471 - DHN - 20160229)
			BigDecimal numberValido = null;
			formResult = this.validaNumberXML(form);
			if (formResult != null) {
				return formResult;
			}

			// TODO: valida formulario con las reglas
			formResult = new FormularioResult();
			formResult.setMsgInput(form.getMsgInput());
			formResult.setPeriodo(form.getPeriodo());
			// TODO: valida formulario con reglas de forma
			formResult = validaFormularioConReglas(form);
			if (formResult != null && (formResult.getResultCode() == 0)) {
				boolean simularADF = false;
				if (simularADF) {
					formResult.setContestadorID("ADF");
					formResult.setResultCode(0);
					formResult.setResultMessage("OK");
				} else {

					formResult = adfValidateFormulario(form);
					if (formResult != null && (formResult.getResultCode() == 0)) {
						// TODO: procesa mensaje correcto
						formResult = procesaFormularioRenta(form);

					} else {
						// TODO: guardar mensaje erroneo..
						guardaErroresToBD(form);
					}
				}
				return formResult;
			} else {
				// TODO: guardar mensaje erroneo..
				guardaErroresToBD(form);
				return formResult;
			}

		} else {
			FormularioResult result = new FormularioResult();
			result = new FormularioResult();
			result.setResultCode(1);
			result.setResultMessage("NOK");
			// mensaje
			AdfResult mensaje = new AdfResult();
			mensaje.setNumber("7");
			mensaje.setDescription("el Formulario Renta esta nulo..");
			mensaje.setObjectName("ProcessRentaME" + "." + "processDataRenta");
			mensaje.setObjectValue(form.toString());
			Collection mensajes = new ArrayList();
			mensajes.add(mensaje);

			AdfResult[] mensajesADF = new AdfResult[mensajes.size()];
			result.setRecaMensajes((AdfResult[]) mensajes.toArray(mensajesADF));
			return result;

		}

	}

	private void procesaFormularioBy74F(FormRenta form) {
		if (form.getFormulario() != null
				&& new BigDecimal(form.getFormulario()).intValue() == 74) {
			String value9927 = Utils.extraeContenidoByCodigo(form, "9927");
			if (value9927 != null && value9927.length() > 0) {
				// asdasd
				logger.info("existe item 9927..");
			} else {
				// inserta item
				ItemCUT[] itemsCUT = form.getItemsCUT();
				int sizeC = itemsCUT.length;
				ItemCUT[] itemNEW = new ItemCUT[sizeC + 3];
				for (int ii = 0; ii < sizeC; ii++) {
					itemNEW[ii] = itemsCUT[ii];
				}

				ItemCUT item9927 = new ItemCUT();
				item9927.setCodigo(9927);
				item9927.setTipoDato("A");
				item9927.setValor(Utils.extraeContenidoByCodigo(form, "915"));
				itemNEW[sizeC] = item9927;

				ItemCUT item15 = new ItemCUT();
				item15.setCodigo(15);
				item15.setTipoDato("P");
				item15.setValor(Utils.extraeContenidoByCodigo(form, "915"));
				itemNEW[sizeC + 1] = item15;

				ItemCUT item8811 = new ItemCUT();
				item8811.setCodigo(8811);
				item8811.setTipoDato("A");
				item8811.setValor(Utils.extraeContenidoByCodigo(form, "8822"));
				itemNEW[sizeC + 2] = item8811;

				form.setItemsCUT(itemNEW);

				ItemRenta[] itemsR = form.getItemsRenta();
				int sizeR = itemsR.length;
				ItemRenta[] itemsR2 = new ItemRenta[sizeR + 3];
				for (int ii = 0; ii < sizeR; ii++) {
					itemsR2[ii] = itemsR[ii];
				}

				ItemRenta itm = new ItemRenta();
				itm.setCodigo("9927");
				itm.setContenido(Utils.extraeContenidoByCodigo(form, "915"));
				itm.setSigno(" ");
				itemsR2[sizeR] = itm;

				ItemRenta itm15 = new ItemRenta();
				itm15.setCodigo("15");
				itm15.setContenido(Utils.extraeContenidoByCodigo(form, "915"));
				itm15.setSigno(" ");
				itemsR2[sizeR + 1] = itm15;

				ItemRenta itm8811 = new ItemRenta();
				itm8811.setCodigo("8811");
				itm8811.setContenido(Utils
						.extraeContenidoByCodigo(form, "8822"));
				itm8811.setSigno(" ");
				itemsR2[sizeR + 2] = itm8811;

				form.setItemsRenta(itemsR2);

			}
		}
	}

	private FormularioResult existeAllPrimSinRectif(FormRenta[] form)
			throws RentaMException {
		Collection errores = new ArrayList();
		for (int i = 0; i < form.length; i++) {
			FormRenta formI = form[i];
			if (formI.getSigno() != null
					&& formI.getSigno().trim().length() > 0
					&& formI.getSigno().trim().equals("-")) {
				res = existeMovPrimitivo(formI);
				if (!res.isIndExiste()) {
					AdfResult adf = new AdfResult();
					adf.setDescription("DPS original no existe"); // MAN000000426:
																	// DHN
																	// (15-01-2016)
					adf.setFormIndex(formI.getNroForm());
					adf.setModule("");
					adf.setNumber("1");
					adf.setObjectDesc("");
					adf.setObjectName("0000");
					adf.setObjectValue(formI.getFolioDecl());
					adf.setSeverity("3");
					errores.add(adf);
				} else if (res.isRectificada()) {
					// swGood++;
					AdfResult adf = new AdfResult();
					adf.setDescription("Existe un mov rectificado");
					adf.setFormIndex(formI.getNroForm());
					adf.setModule("");
					adf.setNumber("1");
					adf.setObjectDesc("");
					adf.setObjectName("0000");
					adf.setObjectValue(formI.getFolioDecl());
					adf.setSeverity("3");
					errores.add(adf);
				}
			}
		}
		// preguntar si hay errores
		if (errores != null && errores.size() > 0) {
			formResult.setExisteAllPrimitivas(false);
			formResult.setContestadorID("EXISTE_ALL_PRIMITIVAS");
			formResult.setResultCode(1);
			formResult.setResultMessage("NOK");
			AdfResult[] array = new AdfResult[errores.size()];
			formResult.setRecaMensajes((AdfResult[]) errores.toArray(array));
		} else {
			formResult.setExisteAllPrimitivas(true);
			formResult.setContestadorID("EXISTE_ALL_PRIMITIVAS");
			formResult.setResultCode(0);
			formResult.setResultMessage("OK");
		}
		return formResult;
	}

	private FormularioResult noEstanRegistrAllFormsPositivos(FormRenta []form)throws RentaMException{
        Collection errores=new ArrayList();  
        int swBad=0;           
        for(int i=0;i<form.length;i++){
            FormRenta formI=form[i];
            if(formI.getSigno()!=null && formI.getSigno().trim().length()>0 && formI.getSigno().trim().equals("+")){
                FormularioR res=existeMovPrimitivo(formI);
                if(res.isIndExiste()){
                    AdfResult adf=new AdfResult();
                    adf.setDescription("existe Mov rectif ya esta registrado");    
                    adf.setFormIndex(formI.getNroForm());
                    adf.setModule("");
                    adf.setNumber("1");
                    adf.setObjectDesc("");
                    adf.setObjectName("0000");
                    adf.setObjectValue(formI.getFolioDecl());
                    adf.setSeverity("3");
                    errores.add(adf);                             
                }else{
                    swBad++;
                }                           
            } 
        }
        
        if(errores!=null && errores.size()>0){
            formResult.setNoEstanRegistTrxPositivas(false);
            formResult.setContestadorID("NO_ESTAN_ERGISTRADOS_TRX_POS");
            formResult.setResultCode(1);
            formResult.setResultMessage("NOK");            
            AdfResult[] array=new AdfResult[errores.size()];
            formResult.setRecaMensajes((AdfResult[])errores.toArray(array));         
        }else{
            formResult.setNoEstanRegistTrxPositivas(true);
            formResult.setContestadorID("EXISTE_ALL_PRIMITIVAS");
            formResult.setResultCode(0);
            formResult.setResultMessage("OK");
        }   
        
       return formResult;                   
    }

	private FormRenta igualeFormNegConOriginalReg(FormRenta formNeg)
			throws RentaMException, RentaMEGenericException {
		// rescata valoers para busqueda
		BigDecimal rutIn = new BigDecimal(formNeg.getRutContr().trim());
		logger.info("rutIn: " + rutIn.toString());
		String dvContr = formNeg.getDvContr();
		logger.info("dvContr: " + dvContr);
		BigDecimal folion = new BigDecimal(formNeg.getFolioDecl().trim());
		logger.info("folion: " + folion.toString());
		BigDecimal tipoForm = new BigDecimal(formNeg.getFormulario());
		logger.info("tipoForm: " + tipoForm.toString());

		Date fechaVcto = null;
		// va a buscar mensjae original
		SpFindMsgmovByLlaveResult resultado = null;
		SpFindMsgmovByLlave2Result resultado2 = null;
		String mensaje = null;

		String valor315Out = null;

		try {
			if (formNeg.getFormulario() != null
					&& new BigDecimal(formNeg.getFormulario()).intValue() == 22) {
				resultado2 = rentaMeEJB.spFindMsgmovByLlave2(rutIn, dvContr,
						tipoForm, folion);
				if (resultado2.getExisteout() != null
						&& resultado2.getExisteout().intValue() != 0) {
					mensaje = resultado2.getMsjout();
					logger.info("XML Primitiva: " + mensaje);
					valor315Out = resultado2.getValorOut();
					logger.info("valor315Out: " + valor315Out);
				} else {
					// lanzar exception
					logger.error("resultado2.getValorOut: "
							+ resultado2.getValorOut());
				}
			} else {
				fechaVcto = buscarFechaVencimientoFormulario(formNeg);
				logger.info("fechaVcto: " + fechaVcto.toLocaleString());
				resultado = rentaMeEJB.spFindMsgmovByLlave(rutIn, dvContr,
						tipoForm, folion, fechaVcto);
				if (resultado.getExisteout() != null
						&& resultado.getExisteout().intValue() != 0) {
					mensaje = resultado.getMsjout();
					logger.info("XML Primitiva: " + mensaje);
					valor315Out = resultado.getValorOut();
					logger.info("valor315Out: " + valor315Out);
				} else {
					// lanzar exception
					logger.error("resultado.getValorOut: "
							+ resultado.getValorOut());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error en el metodo ProcessRentaME.igualeFormNegConOriginalReg() :" + e);						
		}
		// a parsear mensaje
		FormRenta formOrig = Utils.buildMensajeOriginal(mensaje);
		// modificar msg original para que quede como negativo
		formNeg = rectificarItemNegConOriginal(formNeg, formOrig);
		procesaFormNegativoAdd315(formNeg, valor315Out);
		return formNeg;
	}

	private FormRenta procesaFormNegativoAdd315(FormRenta formNeg,
			String new315Valor) {
		ItemRenta arrayCod[] = formNeg.getItemsRenta();
		boolean respExiste = false;
		for (int i = 0; i < arrayCod.length; i++) {
			if (arrayCod[i].getCodigo().trim() != null
					&& new BigDecimal(arrayCod[i].getCodigo().trim())
							.intValue() == 315) {
				respExiste = true;
				break;
			}
		}

		if (!respExiste) {
			int largoNuevo = arrayCod.length + 1;
			ItemRenta arrayNW[] = new ItemRenta[largoNuevo];
			for (int i = 0; i < largoNuevo; i++) {
				if (i < (largoNuevo - 1)) {
					arrayNW[i] = arrayCod[i];
				} else {
					ItemRenta itm = new ItemRenta();
					itm.setCodigo("315");
					itm.setSigno("+");
					itm.setContenido(new315Valor);
					arrayNW[i] = itm;
				}
			}
			// agregar nuevo array de codigos
			formNeg.setItemsRenta(arrayNW);
		}

		return formNeg;
	}

	private FormRenta rectificarItemNegConOriginal(FormRenta formNeg,
			FormRenta formOrig) {
		ItemRenta[] itemsNeg = formNeg.getItemsRenta();
		ItemRenta[] itemsOrig = formOrig.getItemsRenta();
		Collection arrayCodigos = new ArrayList();

		for (int i = 0; i < itemsOrig.length; i++) {
			String contenido = null;
			contenido = Utils.extraeContenidoByCodigo(formNeg,
					itemsOrig[i].getCodigo());
			if (contenido == null) {
				arrayCodigos.add(new Integer(i).toString());
			}
		}

		int largoTotal = itemsNeg.length + arrayCodigos.size() + 1;

		ItemRenta[] itemF = new ItemRenta[largoTotal];
		Iterator ite = arrayCodigos.iterator();
		for (int i = 0; i < (largoTotal - 1); i++) {
			if (i < itemsNeg.length) {
				itemF[i] = itemsNeg[i];
			} else {
				String codigo = (String) ite.next();
				int ind = new BigDecimal(codigo).intValue();
				itemF[i] = itemsOrig[ind];
			}
		}

		ItemRenta itm = new ItemRenta();
		itm.setCodigo("9923");
		itm.setContenido(Utils.extraeContenidoByCodigo(formOrig, "15"));
		itm.setSigno("+");
		itemF[largoTotal - 1] = itm;
		// setear nueva colleccion de item en el form negativo
		formNeg.setItemsRenta(itemF);

		return formNeg;
	}

	private void normaliceFormsNegativos(FormRenta[] form)
			throws RentaMException, RentaMEGenericException {
		for (int i = 0; i < form.length; i++) {
			FormRenta formI = form[i];
			// PREGUNTA SI ES NEGATIVO
			if (formI.getSigno() != null
					&& formI.getSigno().trim().length() > 0
					&& formI.getSigno().trim().equals("-")) {
				form[i] = igualeFormNegConOriginalReg(formI);
			}
		}
	}

	private void registraFomularios(FormRenta[] forms)throws RentaMException,RentaMEGenericException{
        logger.info("[******** RENTAMEEJB: procesa 1 a 1 los formulatrios *****");
        for(int i=0;i<forms.length;i++){
            //controla la trx de registro de formularios
            procesaFormConLogicaNegocio(forms[i]);        
        } 
        
        //registra objeto desalida
        formResult.setContestadorID("registraFomularios");
        formResult.setResultCode(0);
        formResult.setResultMessage("OK"); 
    }

	private FormularioR existeRegistradoMov(FormRenta form)
			throws RentaMException {
		return existeMovPrimitivo(form);
	}

	private void procesaFormConLogicaNegocio(FormRenta form)
			throws RentaMException, RentaMEGenericException {
		logger.info("[******** RENTAMEEJB: evaluar si existe registrado el movimiento *****");
		FormularioR respBD = existeRegistradoMov(form);
		if (respBD.isIndExiste()) {
			// existe mopvimiento por lo tanto registro las (-)
			// 1. saber ID CUENTA
			if (respBD.getIdcta() != null
					&& respBD.getIdcta().trim().length() > 0
					&& new BigDecimal(respBD.getIdcta()).intValue() > 0) {
				// 2. registrar movimiento
				BigDecimal idMovm = registrarMovimientosCUT(new BigDecimal(
						respBD.getIdcta()), form);
				// 3. registrar items
				registrarItemCUT(idMovm, form);
			}
		} else {
			// no existe
			// 1 registrar cuenta
			BigDecimal idcta = registraCuentaCUTME(form);
			// 2. registrar movimiento
			BigDecimal idMovm = registrarMovimientosCUT(idcta, form);
			// 3. registrar items
			registrarItemCUT(idMovm, form);
		}
	}

	private void normalizaCODESFormularios(FormRenta []form){
        for(int i=0;i<form.length;i++){
            FormRenta formI=form[i];
            if(formI.getSigno()!=null && formI.getSigno().trim().length()>0 && !formI.getSigno().trim().equals("-")){
                if(formI.getSigno().trim().equals("+")){
                    formI.getMessageId().setCode("RECAINME");
                }else if(formI.getSigno().trim().equals("G")){
                    if(formI.getFormulario()!=null & formI.getFormulario().trim().length()>0 && formI.getFormulario().trim().equals("221")){
                        formI.getMessageId().setCode("GIRO21ME");
                    }else if(formI.getFormulario()!=null & formI.getFormulario().trim().length()>0 && formI.getFormulario().trim().equals("245")){                        
                        formI.getMessageId().setCode("GIRO45ME");
                    }                        
                }                    
            }                
        }        
    }

	private FormularioResult procesaFormularios(FormRenta []form)throws RentaMException,RentaMEGenericException{
        //asumir que todos los - estan cuadrados con sus + 
        // verificar que todos los negativos tengan sus originales registrados
        logger.info("[******** RENTAMEEJB: verifica que existan todas las originales sin rectif *****");
        formResult=existeAllPrimSinRectif(form);
        if(formResult.isExisteAllPrimitivas()){
            //verificar que todos los (+)s no esten registrados
            logger.info("[******** RENTAMEEJB: verifica que No existan todas las trxs positivas *****");
            formResult=noEstanRegistrAllFormsPositivos(form);
            if(formResult.isNoEstanRegistTrxPositivas()){
                //comenzar a registrar los formularios
                //normaliza los CODES de los formularios                
                normalizaCODESFormularios(form);
                //normalizaFormPostivoCambio315(form);
                
                // MAN426 - DHN: Registro primero y luego Actualizo (18-01-2016)
                //registra los formularios *****");                
                registraFomularios(form); 
                
                //actualiza todas las trxs originales 
                actualizaRectifTRXOriginales(form);                                    
                
                // guarda el mensaje completo con el estado               
                guardarMensajeToBD(form);                                   
            }else{
                //hay algun formulario rectificado
                logger.info("[******** RENTAMEEJB: guarda el mensaje completo con errores *****");
                guardarMensajeToBD(form);
            }           
        }else{
            //se rechaza mensaje 
            logger.info("[******** RENTAMEEJB: guarda el mensaje completo con errores *****");
            guardarMensajeToBD(form);
        }                                      
        return formResult;
        
    }

	private void normalizaFormPostivoCambio315(FormRenta []form)throws RentaMException{
        for(int i=0;i<form.length;i++){
            FormRenta formPos=form[i];
            if(formPos.getSigno()!=null && formPos.getSigno().trim().length()>0 && formPos.getSigno().trim().equals("+")){
                agregaItem315Positivo(formPos,form);    
            }
        }                    
    }

	private void agregaItem315Positivo(FormRenta form, FormRenta []arrayforms)throws RentaMException{
        FormRenta formNeg=null;
        FormRenta formPost=form;
        for(int i=0;i<arrayforms.length;i++){
            FormRenta formI=arrayforms[i]; 
            if(formI.getSigno()!=null && formI.getSigno().trim().length()>0 && formI.getSigno().trim().equals("-")){
                FormRenta formNegat=formI;  
                String folioPos=Utils.extraeContenidoByCodigo(formPost,"9902");
                String folioNeg=formNegat.getFolioDecl();
                if(folioPos.trim()!=null && folioNeg.trim()!=null && folioPos.trim().length()>0 && folioNeg.trim().length()>0 && new BigDecimal(folioPos.trim()).intValue()==new BigDecimal(folioNeg.trim()).intValue()){
                    formNeg=formI;
                    break;                    
                }                                    
            }
        }
        if(formNeg!=null){
            String newValor315=Utils.extraeContenidoByCodigo(formNeg,"315");   
            ItemRenta item315n=new ItemRenta();
            item315n.setCodigo("315");
            item315n.setSigno("+");
            item315n.setContenido(newValor315);
            
            Collection arrayItems=new ArrayList();
            ItemRenta[] arrayI=form.getItemsRenta();
            for(int i=0;i<arrayI.length;i++){
                arrayItems.add(arrayI[i]);
            }    
            //agrega item 315
            arrayItems.add(item315n);
            ItemRenta []itemsC=new ItemRenta[arrayI.length+1];
            form.setItemsRenta((ItemRenta [])arrayItems.toArray(itemsC));        
        }else{
            throw new RentaMException("00","00","","No existe (-) para folio "+Utils.extraeContenidoByCodigo(formPost,"9902")); 
        }                                
    }

	private void actualizaRectifTRXOriginales(FormRenta []form)throws RentaMException,RentaMEGenericException{
        for(int i=0;i<form.length;i++){
            FormRenta formNeg=form[i];
            if(formNeg.getSigno()!=null && formNeg.getSigno().trim().length()>0 && formNeg.getSigno().trim().equals("-")){
                BigDecimal rutIn=new BigDecimal(formNeg.getRutContr().trim());
                String dvContr=formNeg.getDvContr();
                BigDecimal folion=new BigDecimal(formNeg.getFolioDecl().trim());
                BigDecimal tipoForm=new BigDecimal(formNeg.getFormulario());                
                ActRectificadaCtaByLlaveResult res=null;
                ActRectificadaCtaByLlave2Result res2=null;
                try{                    
                    if(formNeg.getFormulario()!=null && new BigDecimal(formNeg.getFormulario()).intValue()==22){
                        res2=rentaMeEJB.actRectificadaCtaByLlave2(rutIn,dvContr,tipoForm,folion);                                            
                    }else{
                        Date fechaVcto=buscarFechaVencimientoFormulario(formNeg);
                        res=rentaMeEJB.actRectificadaCtaByLlave(rutIn,dvContr,tipoForm,folion,fechaVcto);
                        logger.info("la resp es:"+res.getExiste().intValue());
                    }                                                        
                }catch(Exception e){                   
                    throw new RentaMEGenericException(1);
                }                        
            }                
            
        }                  
    }

	private FormularioR existeMovPrimitivo(FormRenta form) throws RentaMException {
		// elije el formulario negativo
		FormRenta formNeg = form;
		// sacar datos de esntrada
		FormularioR resulta = new FormularioR();
		BigDecimal rutIn = new BigDecimal(formNeg.getRutContr().trim());
		String dvContr = formNeg.getDvContr();
		BigDecimal folion = new BigDecimal(formNeg.getFolioDecl().trim());
		BigDecimal tipoForm = new BigDecimal(formNeg.getFormulario());
		Date fechaVcto = null;
		SpExisteMovByLlaveResult result = null;
		SpExisteMovByLlave2Result result2 = null;
		String value15;
		// evalua si es un mov 22
		try {
			if (form.getFormulario() != null
					&& new BigDecimal(form.getFormulario()).intValue() == 22) {
				// value15=Utils.extraeContenidoByCodigo(form,"15");
				result2 = rentaMeEJB.spExisteMovByLlave2(rutIn, dvContr,
						tipoForm, folion);
				if (result2.getExisteout() != null
						&& result2.getExisteout().intValue() != 0) {
					resulta.setIndExiste(true);
					resulta.setIdcta(result2.getIdcta().toString());
					resulta.setIdMov(result2.getIdmov().toString());
					if (result2.getRectind() != null
							&& result2.getRectind().trim().length() > 0
							&& result2.getRectind().trim().equals("Y")) {
						resulta.setRectificada(true);
					} else {
						resulta.setRectificada(false);
					}
				} else {
					resulta.setIndExiste(false);
				}
			} else {
				// ES CUALQUIER OTRO FORMULARIO
				fechaVcto = buscarFechaVencimientoFormulario(formNeg);
				result = rentaMeEJB.spExisteMovByLlave(rutIn, dvContr,
						tipoForm, folion, fechaVcto);
				if (result.getExisteout() != null
						&& result.getExisteout().intValue() != 0) {
					resulta.setIndExiste(true);
					resulta.setIdcta(result.getIdcta().toString());
					resulta.setIdMov(result.getIdmov().toString());
					if (result.getRectind() != null
							&& result.getRectind().trim().length() > 0
							&& result.getRectind().trim().equals("Y")) {
						resulta.setRectificada(true);
					} else {
						resulta.setRectificada(false);
					}
				} else {
					resulta.setIndExiste(false);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error en el metodo ProcessRentaME.existeMovPrimitivo() :" + e);						
		}

		return resulta;
	}
	
	private FormularioResult procesaFormularioRenta(FormRenta form) throws RentaMException, RentaMEGenericException {
		logger.info("[********---- >>>procesaFormularioRenta: Entrada form: \n"+form.toString()+"\n ********]");
		if (tareasME.esUnMovimientoGIRO21ME(form))
			return procesaFormularioRentaNew( form);
		else
			return procesaFormularioRentaOld( form);
	}
	String bigDecimalToString(BigDecimal bigDecimal)
	{
		if(bigDecimal == null)
			return "";
		else
			return bigDecimal.toString();
	}
	
	private FormularioResult procesaFormularioRentaNew(FormRenta form) throws RentaMException, RentaMEGenericException {
		
		try
		{	
			logger.info("[********---- >>>procesaFormularioRentaNew: Entrada form: \n"+form.toString()+"\n ********]");						
			//PkgCutServicesMonexRemote pkgCutServicesMonexRemote= new PkgCutServicesMonexLocator().getPkgCutServicesMonex();						
			TrxFormInVO trxFormInVO = new TrxFormInVO();
			
			BigDecimal formTipo = new BigDecimal(form.getFormulario());
			String formVersion = tareasME.getFormVersion(formTipo.intValue());
			trxFormInVO.setInFormTipo(formTipo);
			trxFormInVO.setInFormVer(formVersion);
			trxFormInVO.setInItems(getItemsAdf(form));
			trxFormInVO.setInIdOrigen(form.getMessageId().getNumber());
												
			String loteCanal = CargarProperties.getValueProp("rentaMe.GIRO21ME.loteCanal");
			String loteTipo = CargarProperties.getValueProp("rentaMe.GIRO21ME.loteTipo");
			String rutIra = CargarProperties.getValueProp("rentaMe.GIRO21ME.rutIra");
			String rutIraDv = CargarProperties.getValueProp("rentaMe.GIRO21ME.rutIraDv");
			
			trxFormInVO.setInLoteCanal(new BigDecimal(loteCanal));
			trxFormInVO.setInLoteTipo(new BigDecimal(loteTipo));
			trxFormInVO.setInRutIra(new BigDecimal(rutIra));
			trxFormInVO.setInRutIraDv(rutIraDv);
			
			
			
			TrxFormOutVO trxFormOutVO = pkgCutServicesMonexEJB.trxForm(trxFormInVO);
			
			if (trxFormOutVO.getOutErrlvl().equals(new BigDecimal(5)))
			{
			formResult.setResultCode(0);
			formResult.setContestadorID("procesaFormularioRenta");
			formResult.setResultMessage("OK");
			}
			else
			{
				formResult.setResultCode(1);
				formResult.setContestadorID("procesaFormularioRenta");
				formResult.setResultMessage("Es un formulario RTRMASME, pero no hay registrado un RECMASME..");				
				
				Iterator<TrxFormOutRecaMsgVO> iterator = trxFormOutVO.getOutRecaMsg().iterator();
				
				Collection col = new ArrayList();
				TrxFormOutRecaMsgVO trxFormOutRecaMsgVO;
				while (iterator.hasNext()) {
					trxFormOutRecaMsgVO = iterator.next();
					AdfResult adf = new AdfResult();
					adf.setDescription(trxFormOutRecaMsgVO.getGlosa());
					adf.setFormIndex("0");
					adf.setModule(bigDecimalToString(trxFormOutRecaMsgVO.getTipo()));
					adf.setNumber(bigDecimalToString(trxFormOutRecaMsgVO.getErrorTgr()));
					adf.setObjectDesc(trxFormOutRecaMsgVO.getObjdescrip().substring(1,100));
					adf.setObjectName(trxFormOutRecaMsgVO.getObjname());
					adf.setObjectValue(trxFormOutRecaMsgVO.getObjvalue());
					adf.setSeverity(bigDecimalToString(trxFormOutRecaMsgVO.getSeveridad()));
					col.add(adf);
			    }
				
				AdfResult[] array = new AdfResult[col.size()];
				formResult.setRecaMensajes((AdfResult[]) col.toArray(array));
			}
			guardaErroresToBD(form);	
		}
		catch (Exception e)
		{							
			e.printStackTrace();
			logger.error("Error en el metodo pkgCutServicesMonexRemote.trxForm:" + e);
			throw new RentaMException("ProcessRentaME",
					"procesaFormularioRenta", e.getMessage(),
					"Error en Inyeccion a pkgCutServicesMonex");
		}
		
		logger.info("[******** RENTAMEEJB:  salida form salida" + formResult + " ********]");
		return formResult;
		
	}

	private FormularioResult procesaFormularioRentaOld(FormRenta form) throws RentaMException, RentaMEGenericException {
		logger.info("[********---- >>>procesaFormularioRentaOld: Entrada form: \n"+form.toString()+"\n ********]");
		FormularioR formBD = null;
		formBD = existeRegistradoFormularioMvto(form);
		if (formBD.isIndExiste()) {
			// TODO: existe registrado
			if (tareasME.esIgualCUTCta(formBD, form)) {
				// TODO: error esta duplicado
				formResult.setResultCode(0);
				formResult.setContestadorID("procesaFormularioRenta");
				formResult.setResultMessage("duplicado..");
			} else {
				// TODO: error 51=Id de transaccion ya existe..
				formResult.setResultCode(1);
				formResult.setContestadorID("procesaFormularioRenta");
				formResult.setResultMessage("Id de transaccion ya existe.");
				Collection col = new ArrayList();
				AdfResult adf = new AdfResult();
				adf.setDescription("Id de transaccion ya existe..");
				adf.setFormIndex("0");
				adf.setModule("");
				adf.setNumber("51");
				adf.setObjectDesc("");
				adf.setObjectName("ProcessRentaME.procesaFormularioRenta");
				adf.setObjectValue(form.getMessageId().getNumber());
				adf.setSeverity("3");
				col.add(adf);
				AdfResult[] array = new AdfResult[col.size()];
				formResult.setRecaMensajes((AdfResult[]) col.toArray(array));
				guardaErroresToBD(form);
			}
		} else {
			// TODO: no existe regiXtrado y no tiene cta..
			// TODO: nuevo(RECMAS) o RECAINME
			if (!existeCuentaByFormularioAndCliente(form)) {
				// TODO: registrar CTA CUT
				if (tareasME.esUnMovimientoRECMAS(form)
						|| tareasME.esUnMovimientoRECAINME(form)
						|| tareasME.esUnMovimientoEGRESO(form)
						|| tareasME.esUnMovimientoRTRINCME(form)
						|| tareasME.esUnMovimientoGIRO45ME(form)
						|| tareasME.esUnMovimientoDESC45ME(form)
						|| tareasME.esUnMovimientoGIRO21ME(form)
						|| tareasME.esUnMovimientoDESC21ME(form)) {
					formatDataTOBD(form);
					// TODO: registramos la cuenta
					BigDecimal id = registraCuentaCUTME(form);
					// TODO: registramos los movimientos
					BigDecimal idMov = registrarMovimientosCUT(id, form);
					// TODO: registramos items
					registrarItemCUT(idMov, form);

					formResult.setResultCode(0);
					formResult.setContestadorID("procesaFormularioRenta");
					formResult.setResultMessage("OK");
					guardaErroresToBD(form);
				} else {
					// TODO: ERROR =Es un RTRECMASME pero no hay registrada una
					// recmas.. (147)
					formResult.setResultCode(1);
					formResult.setContestadorID("procesaFormularioRenta");
					formResult.setResultMessage("Es un formulario RTRMASME, pero no hay registrado un RECMASME..");
					Collection col = new ArrayList();
					AdfResult adf = new AdfResult();
					adf.setDescription("Debe existir en el sistema un DPS vigente del mismo tipo F22.");
					adf.setFormIndex("0");
					adf.setModule("");
					adf.setNumber("147");
					adf.setObjectDesc("");
					adf.setObjectName("ProcessRentaME.procesaFormularioRenta");
					adf.setObjectValue(form.toString());
					adf.setSeverity("3");
					col.add(adf);
					AdfResult[] array = new AdfResult[col.size()];
					formResult.setRecaMensajes((AdfResult[]) col.toArray(array));
					guardaErroresToBD(form);
				}

			} else {
				// TODO: existe cuenta registrada pero es una nueva
				// movimiento(RTRECMAS)
				BigDecimal idCuenta = buscarIDCuentaCUTByForm(form);
				if (tareasME.esUnMovimientoRTRMAS(form)) {
					formBD = existeRegistradoMovRECMASME(idCuenta);
					if (formBD != null && formBD.isExisteRECMAS()) {
						if (esIgualComoFormRECMAS(formBD, form)) {
							// TODO: registramos los movimientos
							BigDecimal idMov = registrarMovimientosCUT(
									idCuenta, form);
							// TODO: registramos items
							registrarItemCUT(idMov, form);
							// TODO: devuelve una respuetas buena
							formResult.setResultCode(0);
							formResult.setContestadorID("procesaFormularioRenta");
							formResult.setResultMessage("OK");
							guardaErroresToBD(form);

						} else {
							// TODO: ERROR =Es un RTRECMASME pero no hay
							// registrada una recmas (147)
							formResult.setResultCode(1);
							formResult.setContestadorID("procesaFormularioRenta");
							formResult.setResultMessage("Es un formulario RTRMASME, pero no hay registrado un RECMASME..");
							Collection col = new ArrayList();
							AdfResult adf = new AdfResult();
							adf.setDescription("Debe existir en el sistema un DPS "
									+ "vigente del mismo tipo F22 con los sgtes campos iguales "
									+ "[003], [007], [087], [091], [315]");

							adf.setFormIndex("0");
							adf.setModule("");
							adf.setNumber("147");
							adf.setObjectDesc("");
							adf.setObjectName("ProcessRentaME.procesaFormularioRenta");
							adf.setObjectValue(form.toString());
							adf.setSeverity("3");
							col.add(adf);
							AdfResult[] array = new AdfResult[col.size()];
							formResult.setRecaMensajes((AdfResult[]) col.toArray(array));
							guardaErroresToBD(form);
						}
					} else {
						// Error 148
						formResult.setResultCode(1);
						formResult.setContestadorID("procesaFormularioRenta");
						formResult.setResultMessage("ya existe cargado un formulario de tipo RTRMASME");
						Collection col = new ArrayList();
						AdfResult adf = new AdfResult();
						adf.setDescription("No debe existir una rectificatoria ya cargada en el sistema, "
								+ "cuyo RUT, numero de Folio, tipo de Formulario y periodo de venc sea el mismo");

						adf.setFormIndex("0");
						adf.setModule("");
						adf.setNumber("148");
						adf.setObjectDesc("ProcessRentaME.procesaFormularioRenta");
						adf.setObjectName("");
						adf.setObjectValue(form.toString());
						adf.setSeverity("3");
						col.add(adf);
						AdfResult[] array = new AdfResult[col.size()];
						formResult.setRecaMensajes((AdfResult[]) col.toArray(array));
						guardaErroresToBD(form);
					}
				} else if (tareasME.esUnMovimientoRECAINME(form)
						|| tareasME.esUnMovimientoEGRESO(form)
						|| tareasME.esUnMovimientoRECTIFME(form)) {
					// TODO: este caso es un formulario recainme
					// TODO: registramos los movimientos
					BigDecimal idMov = registrarMovimientosCUT(idCuenta, form);
					// TODO: registramos items
					registrarItemCUT(idMov, form);
					// TODO: devuelve una respuetas buena
					formResult.setResultCode(0);
					formResult.setContestadorID("procesaFormularioRenta");
					formResult.setResultMessage("OK");
					guardaErroresToBD(form);
				} else if (tareasME.esUnMovimientoRTRMODME(form)) {
					formBD = existeRegistradoMovimientoOrg(idCuenta);
					if (formBD != null && formBD.isIndExiste()
							&& formBD.getFormTipo().intValue() == 3) {
						BigDecimal idMov = registrarMovimientosCUT(idCuenta,
								form);
						// TODO: registramos items
						registrarItemCUT(idMov, form);
						// TODO: devuelve una respuetas buena
						formResult.setResultCode(0);
						formResult.setContestadorID("procesaFormularioRenta");
						formResult.setResultMessage("OK");
						guardaErroresToBD(form);
					} else {
						formResult.setResultCode(1);
						formResult.setContestadorID("procesaFormularioRenta");
						formResult.setResultMessage("NO EXISTE FORM A REEMPLAZAR");
						Collection col = new ArrayList();
						AdfResult adf = new AdfResult();
						adf.setDescription("NO EXISTE FORM A REEMPLAZAR");
						adf.setFormIndex("0");
						adf.setModule("");
						adf.setNumber("325");
						adf.setObjectDesc("ProcessRentaME.procesaFormularioRenta");
						adf.setObjectName("");
						adf.setObjectValue(form.toString());
						adf.setSeverity("3");
						col.add(adf);
						AdfResult[] array = new AdfResult[col.size()];
						formResult.setRecaMensajes((AdfResult[]) col.toArray(array));
						guardaErroresToBD(form);
					}
				} else if (tareasME.esUnMovimientoGIRO45ME(form)
						|| tareasME.esUnMovimientoDESC45ME(form)) {
					logger.info("06-04-2010: entra a bloque 45's");
					formBD = existeRegistradoMovimientoOrg(idCuenta);
					if (formBD != null && formBD.isIndExiste()) {
						logger.info("06-04-2010: entra a bloque 45's - registra movimiento");
						BigDecimal idMov = registrarMovimientosCUT(idCuenta,
								form);
						// TODO: registramos items
						logger.info("06-04-2010: entra a bloque 45's - registra items");
						registrarItemCUT(idMov, form);
						// TODO: devuelve una respuetas buena
						formResult.setResultCode(0);
						formResult.setContestadorID("procesaFormularioRenta");
						formResult.setResultMessage("OK");
						guardaErroresToBD(form);
					}
				} else if (tareasME.esUnMovimientoGIRO21ME(form)
						|| tareasME.esUnMovimientoDESC21ME(form)) {
					formBD = existeRegistradoMovimientoOrg(idCuenta);
					if (formBD != null && formBD.isIndExiste()) {
						// if(formBD!=null && formBD.isIndExiste() &&
						// formBD.getFormTipo().intValue()==21){
						BigDecimal idMov = registrarMovimientosCUT(idCuenta,form);
						// TODO: registramos items
						registrarItemCUT(idMov, form);
						// TODO: devuelve una respuetas buena
						formResult.setResultCode(0);
						formResult.setContestadorID("procesaFormularioRenta");
						formResult.setResultMessage("OK");
						guardaErroresToBD(form);
					}
				}
				// TODO: fin
			}
		}
		logger.info("[******** RENTAMEEJB:  salida form salida" + formResult + " ********]");
		return formResult;

	}
	
	private String getItemsAdf(FormRenta form)
	{
		String ECU_CS = new Character((char) 1).toString(); // Cell separator
		String ECU_LS = new Character((char) 6).toString(); // Line separator
		String ECU_RS = new Character((char) 5).toString(); // Rule separator
		
		String touples = "";
		int indice = 0;
				int largo = form.getItemsRenta().length;
				boolean status;

				while (indice < largo) {
					ItemRenta items = (ItemRenta) form.getItemRenta(indice);
					String codigo = items.getCodigo();
					String valor = (items.getSigno().trim().equals("-") ? "-" : "")
							+ items.getContenido().trim();

					status = true;
					while (indice + 1 < largo && status) {
						ItemRenta itemsNext = (ItemRenta) form.getItemRenta(indice + 1);
						if (codigo.equals(itemsNext.getCodigo())) {
							valor += (itemsNext.getSigno().trim().equals("-") ? "-"
									: "") + itemsNext.getContenido().trim();
							indice++;
						} else {
							status = false;
						}
					}

					touples = touples + codigo + ECU_CS;
					touples = touples + valor + ECU_LS;
					indice++;
				}
				
		return touples + ECU_RS;
		
	}
	
	private boolean esIgualComoFormRECMAS(FormularioR formBD, FormRenta form) {

		BigDecimal idMovim = null;
		if (formBD.getIdMov() != null
				&& new BigDecimal(formBD.getIdMov()).intValue() > 0) {
			idMovim = new BigDecimal(formBD.getIdMov());
		} else {
			idMovim = new BigDecimal(0);
		}
		SpBuscarcodigoobbymovResult resultado = null;
		try {
			resultado = rentaMeEJB.spBuscarcodigoobbymov(idMovim);
		} catch (PkgSiiRentaMeException ex) {			
			ex.printStackTrace();
			logger.error("Error en el metodo ProcessRentaME.esIgualComoFormRECMAS() :" + ex);						
		}

		String valor_3;
		String valor_7;
		String valor_87;
		String valor_91;
		String valor_315;
		if (Utils.extraeContenidoByCodigo(form, "3") != null
				&& Utils.extraeContenidoByCodigo(form, "3").length() > 0) {
			valor_3 = Utils.extraeContenidoByCodigo(form, "3");
		} else {
			valor_3 = "0";
		}

		if (Utils.extraeContenidoByCodigo(form, "7") != null
				&& Utils.extraeContenidoByCodigo(form, "7").length() > 0) {
			valor_7 = Utils.extraeContenidoByCodigo(form, "7");
		} else {
			valor_7 = "0";
		}

		if (Utils.extraeContenidoByCodigo(form, "87") != null
				&& Utils.extraeContenidoByCodigo(form, "87").length() > 0) {
			valor_87 = Utils.extraeContenidoByCodigo(form, "87");
		} else {
			valor_87 = "0";
		}

		if (Utils.extraeContenidoByCodigo(form, "91") != null
				&& Utils.extraeContenidoByCodigo(form, "91").length() > 0) {
			valor_91 = Utils.extraeContenidoByCodigo(form, "91");
		} else {
			valor_91 = "0";
		}

		if (Utils.extraeContenidoByCodigo(form, "315") != null
				&& Utils.extraeContenidoByCodigo(form, "315").length() > 0) {
			valor_315 = Utils.extraeContenidoByCodigo(form, "315");
		} else {
			valor_315 = "0";
		}

		if (valor_3.equals(resultado.getResp03())
				&& valor_7.equals(resultado.getResp07())
				&& valor_87.equals(resultado.getResp87())
				&& valor_91.equals(resultado.getResp91())
				&& valor_315.equals(resultado.getResp315())) {
			return true;
		} else
			return false;
	}

	private FormularioR existeRegistradoMovRECMASME(BigDecimal id) {
		FormularioR resultadoR = new FormularioR();
		SpBuscarmovrecmasmebyformResult resultado = null;
		try {
			resultado = rentaMeEJB.spBuscarmovrecmasmebyform(id);
		} catch (PkgSiiRentaMeException ex) {			
			ex.printStackTrace();
			logger.error("Error en el metodo ProcessRentaME.existeRegistradoMovRECMASME() :" + ex);						
		}
		if (resultado != null && resultado.getRespout() != null
				&& resultado.getRespout().intValue() > 0) {
			if (resultado.getRespout().intValue() == 1) {
				resultadoR.setIdTipoMov(resultado.getRespout().toString());
				resultadoR.setIdMov(resultado.getIdmovm().toString());
				resultadoR.setExisteRECMAS(true);

			} else
				resultadoR.setExisteRECMAS(false);
		} else
			resultadoR.setExisteRECMAS(false);
		return resultadoR;
	}

	private FormularioR existeRegistradoMovimientoOrg(BigDecimal id) {
		FormularioR resultadoR = new FormularioR();
		SpBuscarmovrecmasmebyformResult resultado = null;
		try {
			resultado = rentaMeEJB.spBuscarmovrecmasmebyform(id);
		} catch (PkgSiiRentaMeException ex) {			
			ex.printStackTrace();
			logger.error("Error en el metodo ProcessRentaME.existeRegistradoMovimientoOrg() :" + ex);						
		}
		if (resultado != null && resultado.getRespout() != null
				&& resultado.getRespout().intValue() > 0) {
			resultadoR.setIdTipoMov(resultado.getRespout().toString());
			resultadoR.setIdMov(resultado.getIdmovm().toString());
			resultadoR.setIndExiste(true);
		} else
			resultadoR.setExisteRECMAS(false);
		return resultadoR;
	}

	private BigDecimal buscarIDCuentaCUTByForm(FormRenta form) throws RentaMException{
        String rut=form.getRutContr();
        String dv=form.getDvContr();
        String formTipo="";
        String folio=form.getFolioDecl();
        
                
        //rescatar tipo         
        if(form.getFormulario()!=null && (new BigDecimal(form.getFormulario()).intValue()==245 || new BigDecimal(form.getFormulario()).intValue()==345)){
            formTipo="45";                
        }else if(form.getFormulario()!=null && (new BigDecimal(form.getFormulario()).intValue()==221 || new BigDecimal(form.getFormulario()).intValue()==321)){            
            formTipo="21";                
        }else            
            formTipo=form.getFormulario();
        
        Date fechaVto=null;
        if(buscarFechaVencimientoFormulario(form)!=null){
            fechaVto=buscarFechaVencimientoFormulario(form);
        }    
        
        SpBuscaridcuentacutbyformResult resultado=null;
        try{
            resultado= rentaMeEJB.spBuscaridcuentacutbyform(new BigDecimal(rut),dv,new BigDecimal(formTipo),new BigDecimal(folio),fechaVto);
        }catch(PkgSiiRentaMeException ex){             
			ex.printStackTrace();
			logger.error("Error en el metodo ProcessRentaME.buscarIDCuentaCUTByForm() :" + ex);						
        }   
        if(resultado!=null && resultado.getIdcta()!=null && resultado.getIdcta().intValue()>0){         
           return resultado.getIdcta();            
        }else{
           return new BigDecimal(0); 
        }        
    }	
	
	private void formatDataTOBD(FormRenta form) {
		ItemCUT arrayItemsCUT[] = form.getItemsCUT();
		for (int i = 0; i < arrayItemsCUT.length; i++) {
			ItemCUT item = arrayItemsCUT[i];
			item.setValor(Utils.formatearDataTO(item.getValor(), "BD"));
		}

		ItemRenta items[] = form.getItemsRenta();
		for (int i = 0; i < items.length; i++) {
			ItemRenta item = items[i];
			item.setContenido(Utils.formatearDataTO(item.getContenido(), "BD"));
		}
	}

	private void registrarItemCUT(BigDecimal id, FormRenta form)
			throws RentaMException, RentaMEGenericException {
		// formatDataTOBD(form);
		ItemCut[] arrayItems = tareasME.transformaAColeccionItem(id,
				form.getItemsCUT());
		try {
			rentaMeEJB.insertItemsByColl(arrayItems);
		} catch (PkgSiiRentaMeException ex) {
			ex.printStackTrace();
			logger.error("Error en el metodo ProcessRentaME.registrarItemCUT() :" + ex);
			throw new RentaMEGenericException(1);
		}
	}

	
	private BigDecimal registrarMovimientosCUT(BigDecimal id, FormRenta form)
			throws RentaMException, RentaMEGenericException {
		// TODO: averiguar como rescatar codigo de mov_tipo
		MessageID msgId = form.getMessageId();

		// imaragliano: enviar el item 315 como fecha
		Date fechaMov = buscarFechaCreacionFormulario(form);

		SimpleDateFormat sdfp = new SimpleDateFormat("yyyyMM");
		Date periodoContab = null;
		try {
			periodoContab = sdfp.parse(msgId.getPeriodoContable());
		} catch (ParseException e) {
			e.printStackTrace();
			logger.error("Error en el metodo ProcessRentaME.registrarMovimientosCUT() :" + e);
			throw new RentaMException("00", "00", "", "PeriodoContable invalido");
		}

		Date fecha_vto = buscarFechaVencimientoFormulario(form);
		BigDecimal formMovTipoGlosa = new BigDecimal(form.getFormulario());

		// TODO: averigual como rescatar ek mov_tipo
		BigDecimal movtipo = extraerCodTipoMovByGlosa(form);

		BigDecimal formTipo = new BigDecimal(form.getFormulario());
		// String version=form.getMessageId().getVersion(); MAN471 DHN
		// (22-02-2016)
		String version = tareasME.getFormVersion(formTipo.intValue());
		BigDecimal folio = new BigDecimal(form.getFolioDecl());
		BigDecimal moneda = buscarIdMonedaDeclaracion(form);
		BigDecimal montoMonOrigen = null;
		if (form.getFormulario().trim() != null
				&& form.getFormulario().trim().length() > 0
				&& (new BigDecimal(form.getFormulario().trim()).intValue() == 221 || new BigDecimal(
						form.getFormulario().trim()).intValue() == 245)) {
			montoMonOrigen = new BigDecimal(0);
		} else {
			montoMonOrigen = tareasME.buscarMontoMonOrigen(form);
		}
		BigDecimal valorCambio = tareasME.buscarValorCambio(form);
		// TODO: calcula el monto en pesos
		BigDecimal montoPesos = null;
		if (form.getFormulario() != null
				&& new BigDecimal(form.getFormulario()).intValue() == 74) {
			montoPesos = new BigDecimal(0);
		} else {
			montoPesos = montoMonOrigen.multiply(valorCambio);
		}
		montoPesos.setScale(2, BigDecimal.ROUND_UP);

		// TODO: rescatar nuevos campos 26-03-08
		BigDecimal rutIra = new BigDecimal(form.getRutIra());
		String dvIra = form.getDvIra();
		BigDecimal fol01 = null;
		BigDecimal fecha9927 = new BigDecimal(buscarFechaRecaudacion(form));

		Date fecha315 = buscarFechaCreacionFormulario(form);

		if ((form.getFormulario() != null && (new BigDecimal(
				form.getFormulario()).intValue() == 22
				|| new BigDecimal(form.getFormulario()).intValue() == 29
				|| new BigDecimal(form.getFormulario()).intValue() == 50 || new BigDecimal(
				form.getFormulario()).intValue() == 45))) {
			if (!Utils.esPesosItem8822(form) && form.getFolioF01() != null
					&& new BigDecimal(form.getFolioF01()).intValue() == 0) {
				fol01 = generarFolio01RentaME(form.getFormulario(), fecha315,
						fecha9927);
			} else {
				fol01 = new BigDecimal(form.getFolioF01());
			}
		} else if (form.getFormulario() != null
				&& (new BigDecimal(form.getFormulario()).intValue() == 74
						|| new BigDecimal(form.getFormulario()).intValue() == 245
						|| new BigDecimal(form.getFormulario()).intValue() == 76 || new BigDecimal(
						form.getFormulario()).intValue() == 321)) {
			fol01 = new BigDecimal(form.getFolioF01());
		}
		BigDecimal montRet = new BigDecimal(form.getMontoRet());
		BigDecimal montIMP = new BigDecimal(form.getMontoImp());
		BigDecimal montPag = new BigDecimal(form.getMontoPago());

		// TODO: preguntar como rescatar el estado
		BigDecimal estado = new BigDecimal(0);
		SpRegistraMovimientoCutResult result = null;
		// TODO: ingresar movimiento
		try {
			result = rentaMeEJB.spRegistraMovimientoCut(id, new BigDecimal(
					msgId.getNumber()), movtipo, fechaMov, fecha_vto, formTipo,
					version, folio, moneda, montoMonOrigen, montoPesos,
					valorCambio, estado, rutIra, dvIra, fol01, fecha9927,
					montRet, montIMP, montPag, periodoContab);
		} catch (PkgSiiRentaMeException ex) {						
			ex.printStackTrace();
			logger.error("Error en el metodo ProcessRentaME.registrarMovimientosCUT() :" + ex);
			throw new RentaMEGenericException(1);
		}
		BigDecimal resp = null;
		if (result != null && result.getRespout() != null) {
			resp = result.getRespout();
		} else
			resp = new BigDecimal(0);

		logger.info("[******** RENTAMEEJB:  termina de registrar movimientos ********]");
		return resp;
	}

	
	private BigDecimal generarFolio01RentaME(String formulario, Date fecha315, BigDecimal fecha9927) throws RentaMException, RentaMEGenericException {
		BigDecimal formNumber = null;
		if (formulario != null && formulario.trim().length() > 0) {
			formNumber = new BigDecimal(formulario);
		} else {
			throw new RentaMException("ProcessRentaME",
					"generarFolio01RentaME", "null",
					"No existe o no tiene un valor valido el campo cabecera formulario");
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		BigDecimal fecha315Number = new BigDecimal(sdf.format(fecha315));
		SpGenerarfolio01meResult result = null;
		try {
			result = rentaMeEJB.spGenerarfolio01me(formNumber, fecha315Number,
					fecha9927);
		} catch (PkgSiiRentaMeException ex) {			
			ex.printStackTrace();
			logger.error("Error en el metodo ProcessRentaME.generarFolio01RentaME() :" + ex);
			throw new RentaMEGenericException(1);
		}
		BigDecimal folio = null;

		if (result != null && result.getFol01out() != null) {
			folio = result.getFol01out();
		}
		String newfolio = "000000" + folio.toString();
		newfolio = newfolio.substring(newfolio.length() - 7, newfolio.length());
		newfolio = "706" + newfolio;
		BigDecimal folioSalida = new BigDecimal(newfolio);
		return folioSalida;
	}

	private BigDecimal extraerCodTipoMovByGlosa(FormRenta form)
			throws RentaMEGenericException {
		MessageID msgId = form.getMessageId();
		if (msgId.getCode() != null && msgId.getCode().length() > 0) {
			SpBuscaridtipomovbynemoResult resultado = null;
			try {
				resultado = rentaMeEJB.spBuscaridtipomovbynemo(msgId.getCode());
			} catch (PkgSiiRentaMeException ex) {				
				logger.error("[******** RENTAMEEJB:  exception ********]", ex);
				throw new RentaMEGenericException(1);
			}
			if (resultado != null && resultado.getIdout() != null
					&& resultado.getIdout().intValue() > 0) {
				return resultado.getIdout();
			} else
				return new BigDecimal(0);

		} else
			return new BigDecimal(0);
	}

	private BigDecimal registraCuentaCUTME(FormRenta form)
			throws RentaMException, RentaMEGenericException {
		// rentaMeEJB.
		Date periodo = null;
		SpRegistraCuentaCutResult result;
		logger.info("[******** RENTAMEEJB:  comienza a registrar cuenta ********]");
		if (form.getPeriodo() != null && form.getPeriodo().length() > 0) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

			BigDecimal periodoBD = null;
			try {
				periodoBD = new BigDecimal(form.getPeriodo().trim());
				if (periodoBD.intValue() == 0) {
					periodo = sdf.parse("19000101");
				} else {
					periodo = sdf.parse(form.getPeriodo() + "30");
				}

			} catch (ParseException ex) {
				// TODO: define accion en la exception								
				ex.printStackTrace();
				logger.error("Error en el metodo ProcessRentaME.registraCuentaCUTME()1 :" + ex);				
				throw new RentaMException("ProcessRentaME",
						"registraCuentaCUTME", form.getPeriodo(),
						"El periodo no viene en el formato yyyyMM, no se puede parsear :"
								+ form.getPeriodo());
			}
		} else {			
			throw new RentaMException("ProcessRentaME", "registraCuentaCUTME",
					"null", "El campo periodo viene null");
		}

		Date fechaCreacion = buscarFechaCreacionFormulario(form);
		Date hoy = new Date();
		Date fechaVto = buscarFechaVencimientoFormulario(form);
		BigDecimal moneda = buscarIdMonedaDeclaracion(form);
		BigDecimal formu = null;

		if (form.getFormulario() != null
				&& form.getFormulario().trim().length() > 0) {
			formu = new BigDecimal(form.getFormulario().trim());
			if (formu.intValue() == 321 || formu.intValue() == 221) {
				formu = new BigDecimal("21");
			} else if (formu.intValue() == 345 || formu.intValue() == 245) {
				formu = new BigDecimal("45");
			}
		}
		try {
			result = rentaMeEJB.spRegistraCuentaCut(
					new BigDecimal(form.getRutContr()), form.getDvContr(),
					formu, new BigDecimal(form.getFolioDecl()), new BigDecimal(
							7), periodo, fechaCreacion, hoy, fechaVto, moneda,
					new BigDecimal(0), "N");
		} catch (PkgSiiRentaMeException ex) {						
			ex.printStackTrace();
			logger.error("Error en el metodo ProcessRentaME.registraCuentaCUTME()2 :" + ex);
			throw new RentaMEGenericException(1);
		}

		logger.info("[******** RENTAMEEJB:  termina de registrar cuenta ********]");
		return result.getIdout();
	}

	private BigDecimal buscarIdMonedaDeclaracion(FormRenta form) throws RentaMException {
		SpBuscaridmonedabynemoResult nemo = null;
		BigDecimal resultado = null;
		String glosa =  null;
		try {
			glosa = tareasME.buscarGlosaMoneda(form);
			if (glosa!=null)
				if (glosa.endsWith("0") || glosa.endsWith("1") || glosa.endsWith("2") || glosa.endsWith("4")) // DHN(29-12-2016: Valido MONEDA
					throw new RentaMException();
			
			Hashtable ht = CargarProperties.getHashTableOfProperValue("rentaME.trad.currencies");
			String glosatrad = (String) ht.get(glosa);
			nemo = rentaMeEJB.spBuscaridmonedabynemo(glosatrad);
			
		} catch (Exception ex) { //PkgSiiRentaMeException ex) {
			ex.printStackTrace();
			logger.error("RentaMEJB.ProcessRentaME.buscarIdMonedaDeclaracion()", ex);			
			throw new RentaMException("", glosa, "", "MONEDA");
		}
		if (nemo != null && nemo.getIdmoneda() != null	&& nemo.getIdmoneda().intValue() != 999) 
			resultado = nemo.getIdmoneda();
		else		
			throw new RentaMException("", "8811", glosa, "MONEDA INVALIDA");
		
		return resultado;
	}
	
	private Date buscarFechaVencimientoFormulario(FormRenta form)
			throws RentaMException {
		ItemRenta arrayItems[] = form.getItemsRenta();
		ItemRenta item15 = null;
		for (int i = 0; i < arrayItems.length; i++) {
			ItemRenta item = arrayItems[i];
			if (item.getCodigo().equals("15")) {
				item15 = item;
				break;
			}
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date fechaVto = null;
		if (form.getFormulario() != null
				&& (new BigDecimal(form.getFormulario()).intValue() == 74 || new BigDecimal(
						form.getFormulario()).intValue() == 72)) {
			try {
				fechaVto = sdf.parse(Utils.extraeContenidoByCodigo(form, "915")
						.trim());
			} catch (ParseException ex) {				
				ex.printStackTrace();
				logger.error("Error en el metodo ProcessRentaME.buscarFechaVencimientoFormulario()1 :" + ex);			
			}
		} else if (form.getFormulario() != null
				&& (new BigDecimal(form.getFormulario()).intValue() == 221
						|| new BigDecimal(form.getFormulario()).intValue() == 321
						|| new BigDecimal(form.getFormulario()).intValue() == 21
						|| // ** DHN: 13-01-2016 **
						new BigDecimal(form.getFormulario()).intValue() == 245
						|| new BigDecimal(form.getFormulario()).intValue() == 345
						|| new BigDecimal(form.getFormulario()).intValue() == 54 || // **
																					// DHN:
																					// 02-08-2012
																					// **
				new BigDecimal(form.getFormulario()).intValue() == 79)) {
			if (item15 != null) {
				try {
					fechaVto = sdf.parse(item15.getContenido());
				} catch (ParseException ex) {					
					ex.printStackTrace();
					logger.error("Error en el metodo ProcessRentaME.buscarFechaVencimientoFormulario()2 :" + ex);			
				}
			} else {				
				throw new RentaMException("", "0015", "", "item 15 no existe");
			}

		} else {
			if (item15 != null) {
				try {
					fechaVto = sdf.parse(item15.getContenido() + "30");
				} catch (ParseException ex) {					
					ex.printStackTrace();
					logger.error("Error en el metodo ProcessRentaME.buscarFechaVencimientoFormulario()3 :" + ex);			
				}
			} else {				
				throw new RentaMException("", "0015", "", "item 15 no existe");
			}
		}

		return fechaVto;
	}

	private String buscarFechaRecaudacion(FormRenta form)
			throws RentaMException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		ItemRenta arrayItems[] = form.getItemsRenta();
		ItemRenta item9927 = null;
		String value9927;
		for (int i = 0; i < arrayItems.length; i++) {
			ItemRenta item = arrayItems[i];
			if (item.getCodigo().equals("9927")) {
				item9927 = item;
				break;
			}
		}

		if ((form.getFormulario() != null && new BigDecimal(
				form.getFormulario()).intValue() == 74)
				|| (form.getFormulario() != null && new BigDecimal(
						form.getFormulario()).intValue() == 76)
				|| (form.getFormulario() != null && new BigDecimal(
						form.getFormulario()).intValue() == 72)) {
			if (Utils.extraeContenidoByCodigo(form, "915") != null
					&& Utils.extraeContenidoByCodigo(form, "915").length() > 0) {
				value9927 = Utils.extraeContenidoByCodigo(form, "915").trim();
			} else {				
				throw new RentaMException("", "9927", "",
						"No existe o no tiene un valor valido el item 9927");
			}
		} else if ((form.getFormulario() != null && new BigDecimal(
				form.getFormulario()).intValue() == 10)
				|| (form.getFormulario() != null && new BigDecimal(
						form.getFormulario()).intValue() == 79)
				|| (form.getFormulario() != null && new BigDecimal(
						form.getFormulario()).intValue() == 55)) {
			if (Utils.extraeContenidoByCodigo(form, "15") != null
					&& Utils.extraeContenidoByCodigo(form, "15").length() > 0) {
				value9927 = Utils.extraeContenidoByCodigo(form, "15").trim();
				if (value9927 != null) {
					if (value9927.length() != 8) {						
						throw new RentaMException("", "15", "",
								"No existe o no tiene un valor valido el item 15");
					}
				} else {					
					throw new RentaMException("", "15", "",
							"No existe o no tiene un valor valido el item 15");
				}
			} else {				
				throw new RentaMException("", "15", "",
						"No existe o no tiene un valor valido el item 15");
			}
		} else {
			if (item9927 != null && item9927.getContenido().length() > 0) {
				value9927 = item9927.getContenido();
			} else {				
				throw new RentaMException("", "9927", "",
						"No existe o no tiene un valor valido el item 9927");

			}
		}

		return value9927.trim();
	}

	private Date buscarFechaCreacionFormulario(FormRenta form)
			throws RentaMException {
		ItemRenta arrayItems[] = form.getItemsRenta();
		ItemRenta item315 = null;
		for (int i = 0; i < arrayItems.length; i++) {
			ItemRenta item = arrayItems[i];
			if (item.getCodigo().equals("315")) {
				item315 = item;
				break;
			}
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date fechaCreacion = null;
		try {
			if (item315 != null) {

				if (form.getFormulario() != null
						&& new BigDecimal(form.getFormulario()).intValue() == 74) {
					fechaCreacion = sdf.parse(Utils.extraeContenidoByCodigo(
							form, "915").trim());
				} else {
					fechaCreacion = sdf.parse(item315.getContenido());
				}

			} else {
				if ((form.getFormulario() != null && new BigDecimal(
						form.getFormulario()).intValue() == 221)
						|| (form.getFormulario() != null && new BigDecimal(
								form.getFormulario()).intValue() == 245)
						|| (form.getFormulario() != null && new BigDecimal(
								form.getFormulario()).intValue() == 76)) {
					Date hoy = new Date();
					String fechForm = sdf.format(hoy);
					hoy = sdf.parse(fechForm);
					fechaCreacion = hoy;
				} else if (form.getFormulario() != null
						&& (new BigDecimal(form.getFormulario()).intValue() == 321 || new BigDecimal(
								form.getFormulario()).intValue() == 345)) {
					Date hoy = new Date();
					String fechForm = "19000101";
					hoy = sdf.parse(fechForm);
					fechaCreacion = hoy;
				} else if (form.getFormulario() != null
						&& ((new BigDecimal(form.getFormulario()).intValue() == 72) || (new BigDecimal(
								form.getFormulario()).intValue() == 79))) {
					fechaCreacion = sdf.parse(Utils.extraeContenidoByCodigo(
							form, "915").trim());
					if (fechaCreacion == null) {						
						throw new RentaMException("", "0915", "",
								"No existe item 915");
					}
				} else if (form.getFormulario() != null
						&& (new BigDecimal(form.getFormulario()).intValue() == 10 || new BigDecimal(
								form.getFormulario()).intValue() == 55)) {
					fechaCreacion = sdf.parse(Utils.extraeContenidoByCodigo(
							form, "15").trim());
					if (fechaCreacion == null) {						
						throw new RentaMException("", "0015", "",
								"No existe item 915");
					}
				} else {				
					throw new RentaMException("", "0315", "",
							"No existe o no tiene un valor valido el item 315");
				}
			}
		} catch (ParseException ex) {						
			ex.printStackTrace();
			logger.error("Error en el metodo ProcessRentaME.buscarFechaCreacionFormulario() :" + ex);			
		}
		return fechaCreacion;
	}
	
	private FormularioR existeRegistradoFormularioMvto(FormRenta form) {
		FormularioR formResu = new FormularioR();
		SpExisteRegistradoFormmovtoResult resultado;
		int resp = 9;
		if (form.getMessageId().getNumber() != null
				&& form.getMessageId().getNumber().length() > 0) {
			// TODO: invoco a proc si existe
			try {
				resultado = rentaMeEJB
						.spExisteRegistradoFormmovto(new BigDecimal(form
								.getMessageId().getNumber()));
				if (resultado != null) {
					if (resultado.getIndexiste().intValue() == 0) {
						formResu.setIndExiste(false);
					} else if (resultado.getIndexiste().intValue() == 1) {
						// TODO: existe
						formResu.setIndExiste(true);
						formResu.setRut(resultado.getRutout());
						formResu.setDv(resultado.getDvout());
						formResu.setFormTipo(resultado.getFormtipoout());
						formResu.setFolio(resultado.getFolioout());
					}
				}
			} catch (PkgSiiRentaMeException ex) {				
				ex.printStackTrace();
				logger.error("Error en el metodo ProcessRentaME.existeRegistradoFormularioMvto() :" + ex);
			}
		}
		return formResu;
	}

	private boolean existeCuentaByFormularioAndCliente(FormRenta form)
			throws RentaMException {

		String rut, dv, formu, folio;
		boolean resp = false;
		if (form.getRutContr() != null && form.getRutContr().length() > 0) {
			rut = form.getRutContr();
		} else {
			rut = "0";
		}

		if (form.getDvContr() != null && form.getDvContr().length() > 0) {
			dv = form.getDvContr();
		} else {
			dv = "0";
		}

		if (form.getFormulario() != null && form.getFormulario().length() > 0) {
			formu = form.getFormulario();
			if (new BigDecimal(formu).intValue() == 321
					|| new BigDecimal(formu).intValue() == 221) {
				formu = "21";
			} else if (new BigDecimal(formu).intValue() == 345
					|| new BigDecimal(formu).intValue() == 245) {
				formu = "45";
			}
		} else {
			formu = "0";
		}

		if (form.getFolioDecl() != null && form.getFolioDecl().length() > 0) {
			folio = form.getFolioDecl();
		} else {
			folio = "0";
		}

		Date fechVto = null;
		if (buscarFechaVencimientoFormulario(form) != null) {
			fechVto = buscarFechaVencimientoFormulario(form);
		}

		SpExisteCuentabyfolioandrutResult result;
		try {
			result = rentaMeEJB.spExisteCuentabyfolioandrut(
					new BigDecimal(rut), dv, new BigDecimal(formu),
					new BigDecimal(folio), fechVto);
			if (result != null && result.getRespout() != null
					&& result.getRespout().intValue() == 0) {
				resp = true;
			} else {
				resp = false;
			}
		} catch (PkgSiiRentaMeException ex) {
			ex.printStackTrace();
			logger.error("Error en el metodo ProcessRentaME.existeCuentaByFormularioAndCliente :" + ex);			
			throw new RentaMException(ex, "ProcessRentaME", "existeCuentaByFormularioAndCliente", "se produjo un error al buscar Cta por el filtro....");
		}
		return resp;
	}
		
	private void guardarMensajeToBD(FormRenta[] form) throws RentaMException,
			RentaMEGenericException {
		if (form != null && form.length > 0) {
			FormRenta formI = form[0];
			logger.info("[******** RENTAMEEJB:  comienza a guardar errorres.. ********]");
			MessageID msgId = formI.getMessageId();

			Date hoy = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHssSSSS");

			// TODO: datos de esntrada
			String messageID = sdf.format(hoy);
			String code = msgId.getCode();

			java.sql.Date fechaLlegada = new java.sql.Date(hoy.getTime());
			BigDecimal identif = new BigDecimal(msgId.getNumber());
			BigDecimal rutContr = new BigDecimal(formI.getRutContr());
			BigDecimal formulario = new BigDecimal(formI.getFormulario());
			BigDecimal folioDecl = new BigDecimal(formI.getFolioDecl());
			BigDecimal periodo = new BigDecimal(formI.getPeriodo());
			String status = "IN";
			sdf.applyPattern("yyyyMMdd");

			String fecha9927 = Utils.extraeContenidoByCodigo(formI, "9927");
			if (fecha9927 != null && fecha9927.trim().length() > 0) {
				fecha9927 = Utils.extraeContenidoByCodigo(formI, "9927");
			} else {
				fecha9927 = sdf.format(hoy);
			}
			// renta.setIdMessage(messageID);
			int codeRes = formResult.getResultCode();
			String respuesta = "";
			if (codeRes == 0) {
				respuesta = "OK";
			} else {
				respuesta = "NOK";
			}

			SpInsertMsgRentaMeResult result = null;
			try {
				result = rentaMeEJB.spInsertMsgRentaMe(messageID, code, hoy,
						identif, rutContr, formulario, folioDecl, periodo,
						respuesta, formI.getMsgInput(), new BigDecimal(
								fecha9927.trim()));
			} catch (PkgSiiRentaMeException ex) {				// 			
				ex.printStackTrace();
				logger.error("Error en el metodo ProcessRentaME.guardarMensajeToBD()1 :" + ex);			
				throw new RentaMEGenericException(ex);
			}
			// logger.info("el resultado es :"+result.getRespout().intValue());
			if (result != null) {
				if (result.getRespout().intValue() > 0) {
					formI.setIdMessage(new BigDecimal(result.getRespout()
							.intValue()).toString());
				}
			}

			// String msgId=formResult.getIdMensaje();
			// ----------- REGISTRA ERRORRES --------------//
			if (formResult.getMensajesArray() != null
					&& formResult.getMensajesArray().length > 0) {
				AdfResult arrayErrores[] = formResult.getMensajesArray();
				// TODO: procesa errorres
				for (int i = 0; i < arrayErrores.length; i++) {
					AdfResult error = arrayErrores[i];
					try {
						logger.info(formI.getIdMessage() + ","
								+ new BigDecimal(i + 1) + ","
								+ new BigDecimal(error.getNumber()) + ","
								+ new BigDecimal(error.getSeverity()) + ","
								+ error.getDescription() + ","
								+ new BigDecimal(error.getFormIndex()) + ","
								+ new BigDecimal(0) + ","
								+ error.getObjectValue() + ","
								+ error.getModule() + ","
								+ error.getObjectName() + ","
								+ error.getObjectDesc() + ","
								+ new BigDecimal(formResult.getPeriodo()));
						if (error.getObjectDesc().length() > 100)
							error.setObjectDesc(error.getObjectDesc()
									.substring(0, 99));
						rentaMeEJB.spInsertErrorMsgMe(formI.getIdMessage(),
								new BigDecimal(i),
								new BigDecimal(error.getNumber()),
								new BigDecimal(error.getSeverity()), error
										.getDescription(),
								new BigDecimal(error.getFormIndex()),
								new BigDecimal(0), error.getObjectValue(),
								error.getModule(), error.getObjectName(), error
										.getObjectDesc(), new BigDecimal(
										formResult.getPeriodo()));
					} catch (PkgSiiRentaMeException e) {						
						e.printStackTrace();
						logger.error("Error en el metodo ProcessRentaME.guardarMensajeToBD()2 :" + e);			
						throw new RentaMEGenericException(e);
					}
				}
			}
		}
	}

	private void guardaErroresToBD(FormRenta form) throws RentaMException,
			RentaMEGenericException {
		// --------- REGISTRA MENSAJE -----------------//
		logger.info("[******** RENTAMEEJB:  comienza a guardar errorres.. ********]");
		MessageID msgId = form.getMessageId();

		Date hoy = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHssSSSS");

		// TODO: datos de esntrada
		String messageID = sdf.format(hoy);
		String code = msgId.getCode();

		java.sql.Date fechaLlegada = new java.sql.Date(hoy.getTime());
		BigDecimal identif = new BigDecimal(msgId.getNumber());
		BigDecimal rutContr = new BigDecimal(form.getRutContr());
		BigDecimal formulario = new BigDecimal(form.getFormulario());
		BigDecimal folioDecl = new BigDecimal(form.getFolioDecl());

		// DHN:MAN241 (09-12-2015)
		// BigDecimal periodo=new BigDecimal(form.getPeriodo());
		BigDecimal periodoContable = new BigDecimal(msgId.getPeriodoContable());
		// Fin: MAN241

		String status = "IN";
		sdf.applyPattern("yyyyMMdd");
		String fecha9927 = buscarFechaRecaudacion(form);
		// renta.setIdMessage(messageID);
		int codeRes = formResult.getResultCode();
		String respuesta = "";
		if (codeRes == 0) {
			respuesta = "OK";
		} else {
			respuesta = "NOK";
		}

		SpInsertMsgRentaMeResult result = null;
		try {
			result = rentaMeEJB.spInsertMsgRentaMe(messageID, code, hoy,
					identif, rutContr, formulario, folioDecl, periodoContable,
					respuesta, form.getMsgInput(), new BigDecimal(fecha9927));
		} catch (PkgSiiRentaMeException ex) {
			ex.printStackTrace();
			logger.error("Error en el metodo ProcessRentaME.guardaErroresToBD()1 :" + ex);		
			throw new RentaMEGenericException(1);
		}
		// logger.info("el resultado es :"+result.getRespout().intValue());
		if (result != null) {
			if (result.getRespout().intValue() > 0) {
				form.setIdMessage(new BigDecimal(result.getRespout().intValue())
						.toString());
			}
		}

		// String msgId=formResult.getIdMensaje();
		// ----------- REGISTRA ERRORRES --------------//
		if (formResult.getMensajesArray() != null
				&& formResult.getMensajesArray().length > 0) {
			AdfResult arrayErrores[] = formResult.getMensajesArray();
			// TODO: procesa errorres
			for (int i = 0; i < arrayErrores.length; i++) {
				AdfResult error = arrayErrores[i];
				try {
					logger.info(form.getIdMessage() + ","
							+ new BigDecimal(i + 1) + ","
							+ new BigDecimal(error.getNumber()) + ","
							+ new BigDecimal(error.getSeverity()) + ","
							+ error.getDescription() + ","
							+ new BigDecimal(error.getFormIndex()) + ","
							+ new BigDecimal(0) + "," + error.getObjectValue()
							+ "," + error.getModule() + ","
							+ error.getObjectName() + ","
							+ error.getObjectDesc() + ","
							+ new BigDecimal(formResult.getPeriodo()));
					rentaMeEJB.spInsertErrorMsgMe(form.getIdMessage(),
							new BigDecimal(i + 1),
							new BigDecimal(error.getNumber()), new BigDecimal(
									error.getSeverity()), error
									.getDescription(),
							new BigDecimal(error.getFormIndex()),
							new BigDecimal(0), error.getObjectValue(), error
									.getModule(), error.getObjectName(), error
									.getObjectDesc(),
							new BigDecimal(formResult.getPeriodo()));
				} catch (PkgSiiRentaMeException e) {					
					e.printStackTrace();
					logger.error("Error en el metodo ProcessRentaME.guardaErroresToBD()2 :" + e);		
					throw new RentaMEGenericException(1);
				}
			}
		}

		logger.info("[******** RENTAMEEJB:  termina a guardar errorres.. ********]");
	}

	private FormularioResult adfValidateFormulario(FormRenta form)
			throws RentaMException {
		ArrayList itemsCut;

		formResult.setContestadorID("ADF");
		formResult.setResultCode(0);
		formResult.setResultMessage("OK");
		// Construir parametros ContextTGF y TouplesTGF
		// tareasME.formatDataTOADF(form); DHN (???)

		String ECU_CS = new Character((char) 1).toString(); // Cell separator
		String ECU_LS = new Character((char) 6).toString(); // Line separator
		String ECU_RS = new Character((char) 5).toString(); // Rule separator
		/**
		 * STS_OK = 0 'Success STS_INFO = 1 'Information STS_WARN = 2 'Warning
		 * STS_ERROR = 3 'Error STS_FATAL = 4 'Fatal
		 */
		String STS_ERROR = "3"; // Severidad de Error
		String MessagesTGF;
		DateFormat format1 = new SimpleDateFormat("yyyyMMdd");
		String fechaHoy = format1.format(new java.util.Date()).toString();
		String frmNro = "";
		if (form.getFormulario() != null
				&& new BigDecimal(form.getFormulario()).intValue() == 321) {
			frmNro = "221";
		} else if (form.getFormulario() != null
				&& new BigDecimal(form.getFormulario()).intValue() == 345) {
			frmNro = "245";
		} else {
			frmNro = form.getFormulario();
		}

		String ContextTGF = "fecha_caja" + ECU_CS + fechaHoy + ECU_LS
				+ "form_cod" + ECU_CS + frmNro + ECU_LS;

		if (frmNro != null)
			ContextTGF += "form_ver"
					+ ECU_CS
					+ tareasME
							.getFormVersion(new BigDecimal(frmNro).intValue())
					+ ECU_LS;

		if (frmNro != null && new BigDecimal(frmNro).intValue() == 50) {
			try {
				String vigencia8707 = Utils.extraeContenidoByCodigo(form,
						"8707").trim();
				logger.info("vigencia8707=" + vigencia8707);
				String form_vig = "";
				if (vigencia8707 != null && vigencia8707.trim() != null
						&& vigencia8707.trim().length() > 0) {
					try {
						BigDecimal c8707 = new BigDecimal(vigencia8707);
						
						form_vig = CargarProperties.getValueProp( "vigencia.50.8707." + c8707);
						
						//CargarArchivo.cargarVigencia(c8707.toString());
						
						//form_vig = CargarArchivo.VIGENCIA_F50;
						
						logger.info("form_vig=" + form_vig);
					} catch (Exception e8707) {
						e8707.printStackTrace();
						logger.error("Error en el metodo ProcessRentaME.adfValidateFormulario()1 :" + e8707);		
						throw new RentaMException("", "8707", "",
								"Valor no valido 8707");
					}

					if (form_vig == null || form_vig.trim() == "")
						throw new RentaMException("", "8707", "",
								"Valor no valido 8707");

					ContextTGF += "form_vig" + ECU_CS + form_vig + ECU_LS;

				} else {
					ContextTGF += "form_vig" + ECU_CS + fechaHoy.trim()
							+ ECU_LS;
				}
			} catch (Exception eF50) {
				logger.error(eF50);
				eF50.printStackTrace();
				logger.error("Error en el metodo ProcessRentaME.adfValidateFormulario()2 :" + eF50);		
				ContextTGF += "form_vig" + ECU_CS + fechaHoy.trim() + ECU_LS;
			}
		} else {
			//ContextTGF += "form_vig" + ECU_CS + fechaHoy.trim() + ECU_LS;
			if (frmNro != null && new BigDecimal(frmNro).intValue() == 22){
                String vigencia315 = Utils.extraeContenidoByCodigo(form,"315").trim();
                logger.info("vigencia315=" + vigencia315);
	            if (vigencia315 != null && vigencia315.trim() != null && vigencia315.trim().length() > 0){
	            	ContextTGF += "form_vig" + ECU_CS + vigencia315 + ECU_LS;
	            }else{
	            	ContextTGF += "form_vig" + ECU_CS + fechaHoy.trim() + ECU_LS;
	            }
            }else{
            	ContextTGF += "form_vig" + ECU_CS + fechaHoy.trim() + ECU_LS;
            }                              

		}

		// DHN (11-09-2014) ITIL00018087 = Monex Retro
		if (form.getSigno().equals("-")) {
			ContextTGF += "oblr" + ECU_CS + "R" + ECU_LS;
		}

		ContextTGF += "comuna_mov" + ECU_CS + "13101" + ECU_LS + "trace_lvl"
				+ ECU_CS + STS_ERROR + ECU_LS + ECU_RS;

		String touples = "";
		int indice = 0;
		int largo = form.getItemsRenta().length;
		boolean status;

		while (indice < largo) {
			ItemRenta items = (ItemRenta) form.getItemRenta(indice);
			String codigo = items.getCodigo();
			String valor = (items.getSigno().trim().equals("-") ? "-" : "")
					+ items.getContenido().trim();

			status = true;
			while (indice + 1 < largo && status) {
				ItemRenta itemsNext = (ItemRenta) form.getItemRenta(indice + 1);
				if (codigo.equals(itemsNext.getCodigo())) {
					valor += (itemsNext.getSigno().trim().equals("-") ? "-"
							: "") + itemsNext.getContenido().trim();
					indice++;
				} else {
					status = false;
				}
			}

			touples = touples + codigo + ECU_CS;
			touples = touples + valor + ECU_LS;
			indice++;
		}

		// MAN331: DHN (20151223)
		// Agrega los item 250.251.252 a los Negativos
		if (form.getSigno().trim().equals("-")) {
			if (res.getIdMov() != null) { // MAN438: DHN (20150129)
				this.getItemsOriginalesCUT(new BigDecimal(res.getIdMov()));
				touples = touples + "250" + ECU_CS;
				touples = touples + c250 + ECU_LS;
				touples = touples + "251" + ECU_CS;
				touples = touples + c251 + ECU_LS;
				touples = touples + "252" + ECU_CS;
				touples = touples + c252 + ECU_LS;
			}
		}

		String TouplesTGF = touples + ECU_RS;
		ProcessResult result = null;
		try {
			logger.info("TouplesTGF :" + TouplesTGF);
			logger.info("ContextTGF :" + ContextTGF);
			result = adflocal.process(TouplesTGF, ContextTGF);

		} catch (AdfprocedureException ex) {			
			ex.printStackTrace();
			logger.error("Error en el metodo ProcessRentaME.adfValidateFormulario()3 :" + ex);		
			throw new RentaMException(ex, "ProcessRentaME",
					"adfValidateFormulario",
					"error al ejecutar procedimiento validor ADF..");
		}

		int ReturnValue = Integer.parseInt(result.getResultado().toString());

		// MAN331: DHN (20151223)
		// Ignora la validación del ADF el Formulario Negativo
		if (form.getSigno().trim().equals("-"))
			ReturnValue = 1;

		if (ReturnValue >= 3) {
			formResult.setResultCode(1);
			formResult.setResultMessage("NOK");
			ArrayList arrayErrores = new ArrayList();
			MessagesTGF = result.getMessagestgf();
			logger.info("CODIGO RETORNO DISTINTO A 3 DESDE ADF MESSAGESTGF:["
					+ MessagesTGF + "]\n");
			arrayErrores = tareasME.getErroresAdf(MessagesTGF);
			AdfResult[] array = new AdfResult[arrayErrores.size()];
			formResult.setRecaMensajes((AdfResult[]) arrayErrores
					.toArray(array));
		} else {
			String itemsTGF = result.getItemsOut();
			logger.info("ITEMSTGF    :[" + itemsTGF);
			logger.info("MESSAGESTGF :[" + result.getMessagestgf());
			Collection itemsCol = new ArrayList();
			itemsCol = getItemsAdf(itemsTGF);
			ItemCUT[] itemsI = new ItemCUT[itemsCol.size()];
			form.setItemsCUT((ItemCUT[]) itemsCol.toArray(itemsI));
			formatDataTOBD(form);
		}

		return formResult;

	}

	protected void getItemsOriginalesCUT(BigDecimal idMov) {
		String touplesExtra = "";
		try {
			BuscarCutItemsResult cutResult = rentaMeEJB.buscarCutItems(idMov);
			RowSet rs = cutResult.getRowSet(0);
			boolean isData = rs.first();

			while (isData) {

				String valorAlfa = rs.getString("VALOR_ALFA");
				int codigo = rs.getInt("CODIGO");
				String datoTipo = rs.getString("DATO_TIPO");

				// cargo los Items 250, 251 y 252
				if (codigo == 250)
					c250 = valorAlfa;
				if (codigo == 251)
					c251 = valorAlfa;
				if (codigo == 252)
					c252 = valorAlfa;

				isData = rs.next();
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error en el metodo ProcessRentaME.getItemsOriginalesCUT() :" + e);		
		}

	}

	protected ArrayList getItemsAdf(String itemsTGF) {
		String ECU_CS = new Character((char) 1).toString(); // Cell separator
		String ECU_LS = new Character((char) 6).toString(); // Line separator
		ArrayList array = new ArrayList();
		if (itemsTGF.length() > 0) {
			String[] messagesItems = itemsTGF.split(ECU_LS);
			for (int x = 0; x < messagesItems.length; x++) {
				if (messagesItems[x].length() > 3) {
					String[] fields = messagesItems[x].split(ECU_CS);
					if (fields.length > 3) {
						ItemCUT itemCut = new ItemCUT();
						itemCut.setCodigo(Integer.parseInt(fields[0].toString()));
						itemCut.setTipoDato(fields[1].toString());
						itemCut.setValor(fields[3].toString());
						array.add(itemCut);
					} else {
						ItemCUT itemCut = new ItemCUT();
						itemCut.setCodigo(Integer.parseInt(fields[0].toString()));
						itemCut.setTipoDato(fields[1].toString());
						itemCut.setValor("");
						array.add(itemCut);
					}
				}
			}
		}
		return array;
	}

	/**
	 * DHN (29-02-2016) MAN000000472 - Validación de NUMBER XML
	 */
	private FormularioResult validaNumberXML(FormRenta form) {
		FormularioResult resultNumberXML = null;
		BigDecimal numberXML = new BigDecimal(form.getMessageId().getNumber());
		BigDecimal rutContr = new BigDecimal(form.getRutContr());
		BigDecimal folioDecl = new BigDecimal(form.getFolioDecl());
		BigDecimal formulario = new BigDecimal(form.getFormulario());
		BigDecimal retorno = null;
		try {
			retorno = rentaMeEJB.validaNumber(numberXML, rutContr, folioDecl,
					formulario);

			if (retorno.intValue() == 51) {
				// Error 51: Number duplicado
				resultNumberXML = new FormularioResult();
				resultNumberXML.setResultCode(1);
				resultNumberXML.setResultMessage("NOK");
				// mensaje
				AdfResult mensaje = new AdfResult();
				mensaje.setNumber("51");
				mensaje.setFormIndex("0");
				mensaje.setDescription("Numero de Identificacion ya existe");
				mensaje.setObjectName("NUMBER");
				mensaje.setObjectValue(numberXML.toString());
				Collection mensajes = new ArrayList();
				mensajes.add(mensaje);

				AdfResult[] mensajesADF = new AdfResult[mensajes.size()];
				resultNumberXML.setRecaMensajes((AdfResult[]) mensajes
						.toArray(mensajesADF));

			} else if (retorno.intValue() == 1) {
				// Trx ya está OK en la CUT
				resultNumberXML = new FormularioResult();
				resultNumberXML.setResultCode(0);
				resultNumberXML.setResultMessage("OK");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error en el metodo ProcessRentaME.validaNumberXML() :" + e);		

		}

		return resultNumberXML;
	}
}
