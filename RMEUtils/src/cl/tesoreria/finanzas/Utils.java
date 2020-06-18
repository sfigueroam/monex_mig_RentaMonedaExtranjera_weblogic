package cl.tesoreria.finanzas;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.regex.Pattern;

import noNamespace.MessageDocument;
import noNamespace.MessageDocument.Message.Formulario;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlError;
import org.apache.xmlbeans.XmlOptions;

import cl.tesoreria.AdfResult;
import cl.tesoreria.FormRenta;
import cl.tesoreria.FormularioResult;
import cl.tesoreria.ItemRenta;
import cl.tesoreria.MessageID;



public class Utils 
{ 
    //private static Logger logger = Logger.getLogger("cl.tesoreria.RentaMonedaExtranjera.utils"); 
    private static Logger logger = Logger.getLogger("cl.tesoreria.finpub.RentaMonedaExtranjera.Utils");
    private static MessageDocument myMessageIn;    
    
    private static boolean parserMessage(String MensajeIN) throws RentaMEGenericException
    {
        boolean isValid=false;
        String mensajesErrores = "";
        String respuesta ="";
        try
        {
            myMessageIn = MessageDocument.Factory.parse(MensajeIN);
            //System.out.println(myMessageIn.toString());
            // Create an XmlOptions instance and set the error listener.
            XmlOptions validateOptions = new XmlOptions();
            ArrayList listaErrores = new ArrayList();
            validateOptions.setErrorListener(listaErrores);
            // Validate the XML.
            isValid = myMessageIn.validate(validateOptions);
            // If the XML isn't valid, loop through the listener's contents, printing contained messages.    
            if (!isValid)
            {
                for (int i = 0; i < listaErrores.size(); i++)
                {
                    XmlError error=(XmlError)listaErrores.get(i);
                    XmlCursor cursor=error.getCursorLocation();
                    cursor.toParent();
                    mensajesErrores = mensajesErrores + "el Valor ( "+cursor.getTextValue()+ " ) no es valido para el tag:" + cursor.xmlText()+ "Error:"+ error.getMessage()+"\n";
                }
                logger.info("ERRORES -->"+mensajesErrores);
                return false;        
            }
        }
        catch(Exception ex)
        {
            //throw new RuntimeException("<ParserMessage> XML NO ES VALIDO ",ex);
        	ex.printStackTrace();
        	logger.error("Error en el metodo Utils.parserMessage() : " + ex);
            throw new RentaMEGenericException(2);                       
            
        }
        return true;
    }
    
    private static FormRenta buildFormulario(String msgInput) 
    {
        FormRenta form= null; 
        try
        {  
            
            if(myMessageIn.getMessage().getFormularioArray()!=null && myMessageIn.getMessage().getFormularioArray().length==1){
                
                form= new FormRenta();           
                //TODO: setea mensaje de entrada
                form.setMsgInput(msgInput);
                //TODO: rescata el mmessaageId
                MessageID msgID=new MessageID(); 
                
                MessageDocument.Message.MessageId messageId=myMessageIn.getMessage().getMessageId();
                
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
                
                form.setMessageId(msgID);
                //TODO: rescata identificacion del formulario
                Collection coll = new ArrayList();            
                MessageDocument.Message.Formulario.Identificacion identificacion= myMessageIn.getMessage().getFormularioArray(0).getIdentificacion();
    
                form.setRutIra(identificacion.getRutIra().toString());
                form.setDvIra(identificacion.getDvIra());
                form.setFolioF01(identificacion.getFolioF01().toString());
                form.setFormulario(identificacion.getFormulario().toString());
                form.setPeriodo(identificacion.getPeriodo().toString());
                form.setFolioDecl(identificacion.getFolioDecl().toString());
                form.setSigno(identificacion.getSigno());
                form.setRutContr(identificacion.getRutContr().toString());
                form.setDvContr(identificacion.getDvContr());
                form.setMarcaFisc(identificacion.getMarcaFisc().toString());
                form.setGlosaFisc(identificacion.getGlosaFisc());
                form.setMontoRet(identificacion.getMontoRet().toString());
                form.setSignoMontoImp(identificacion.getSignoMontoImp());
                form.setMontoImp(identificacion.getMontoImp().toString());
                form.setSignoMontoPag(identificacion.getSignoMontoPag());
                form.setMontoPago(identificacion.getMontoPago().toString());
    
    
                MessageDocument.Message.Formulario.ListaCodigos codigos = myMessageIn.getMessage().getFormularioArray(0).getListaCodigos();
        
    
                int nroCodigos = codigos.getCodigosArray().length;
                int ini =0; 
                ItemRenta itemRenta; 
                for (ini=0; ini<nroCodigos; ini++)
                {
                    String codigo  = codigos.getCodigosArray(ini).getCodigo().toString();
                    String signo  = codigos.getCodigosArray(ini).getSigno();
                    String contenido  = codigos.getCodigosArray(ini).getContenido();
    
                    itemRenta=new ItemRenta();
                    itemRenta.setCodigo(codigo);
                    itemRenta.setSigno(signo);
                    itemRenta.setContenido(contenido);
                    coll.add(itemRenta);       
                }
                          
                ItemRenta[] array = new ItemRenta[coll.size()];
                form.setItemsRenta((ItemRenta[]) coll.toArray(array));
                logger.info(form.viewForm());    
                return form;   
            }else{
                //hay mas de un formulario
                
                form= new FormRenta();           
                //TODO: setea mensaje de entrada
                form.setMsgInput(msgInput);
                //capturo arreglo de formularios
                Formulario [] arrayForm=myMessageIn.getMessage().getFormularioArray();
                Formulario formResct=null;
                //rescato formulario (+)
                for(int i=0;i<arrayForm.length;i++){
                    Formulario formI=arrayForm[i];
                    if(formI.getIdentificacion().getSigno().trim().equals("+")){
                        formResct=formI;
                        break;
                    }                        
                }    
                
                //seteo la identificacion del mensaje
                MessageID msgID=new MessageID(); 
                
                MessageDocument.Message.MessageId messageId=myMessageIn.getMessage().getMessageId();
                
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
                form.setMessageId(msgID);
                
                //SETERAR DATA DEL FORMULARIO
                MessageDocument.Message.Formulario.Identificacion identificacion= formResct.getIdentificacion();
    
                form.setRutIra(identificacion.getRutIra().toString());
                form.setDvIra(identificacion.getDvIra());
                form.setFolioF01(identificacion.getFolioF01().toString());
                form.setFormulario(identificacion.getFormulario().toString());
                form.setPeriodo(identificacion.getPeriodo().toString());
                form.setFolioDecl(identificacion.getFolioDecl().toString());
                form.setSigno(identificacion.getSigno());
                form.setRutContr(identificacion.getRutContr().toString());
                form.setDvContr(identificacion.getDvContr());
                form.setMarcaFisc(identificacion.getMarcaFisc().toString());
                form.setGlosaFisc(identificacion.getGlosaFisc());
                form.setMontoRet(identificacion.getMontoRet().toString());
                form.setSignoMontoImp(identificacion.getSignoMontoImp());
                form.setMontoImp(identificacion.getMontoImp().toString());
                form.setSignoMontoPag(identificacion.getSignoMontoPag());
                form.setMontoPago(identificacion.getMontoPago().toString());
                
                // coleccion de item
                Collection coll = new ArrayList();  
                 
                MessageDocument.Message.Formulario.ListaCodigos codigos = formResct.getListaCodigos();            
                int nroCodigos = codigos.getCodigosArray().length;
                int ini =0; 
                ItemRenta itemRenta; 
                for (ini=0; ini<nroCodigos; ini++)
                {
                    String codigo  = codigos.getCodigosArray(ini).getCodigo().toString();
                    String signo  = codigos.getCodigosArray(ini).getSigno();
                    String contenido  = codigos.getCodigosArray(ini).getContenido();
    
                    itemRenta=new ItemRenta();
                    itemRenta.setCodigo(codigo);
                    itemRenta.setSigno(signo);
                    itemRenta.setContenido(contenido);
                    coll.add(itemRenta);       
                }
                          
                ItemRenta[] array = new ItemRenta[coll.size()];
                form.setItemsRenta((ItemRenta[]) coll.toArray(array));
                
                return form;
            }            
            
        }
        catch(Exception ex)
        {	
        	ex.printStackTrace();
        	logger.error("Error en el metodo Utils.buildFormulario() : " + ex);	
            throw new RuntimeException("<buildFormulario> : No se pudo crear Formulario  MsgIn: " +myMessageIn.toString() ,ex);
        }    
    }
    
    
    public static FormRenta buildMensajeOriginal(String msgIn)throws RentaMException,RentaMEGenericException{        
        FormRenta formr=null;        
        if(msgIn!=null && msgIn.trim().length()>0){
            if(parserMessage(msgIn)){
                formr=buildFormulario(msgIn);                                                       
            }else{
                throw new RentaMException("","0000",msgIn,"mal paseo msg entrada");
            }        
        }else{
            throw new RentaMException("","0000",msgIn,"msg entrada VACIO");
        }            
                
        return formr;   
    }    
    
    public static boolean validarCamposObligNumerosFromSii(FormRenta form){
        String arrItems[]={"30","563","68","62","565","120","122","123","700","701","711","703","595","547","91","92","93","94"};
        int i,largo=arrItems.length;
        boolean resp=true;
        String patronDot="[-]{0,1}[0-9]+[.]{1}[0-9]+"; 
		        
        for(i=0;i<largo;i++){
            String valorI=extraeContenidoByCodigo(form,arrItems[i]);
            if(valorI!=null){
                boolean b = Pattern.matches(patronDot, valorI.trim());
                if(!b){
                    resp=false;
                    break;
                }                
            }    
        }    
        return resp;
    }    
    
    public static String formatearDataTO(String value,String to){
		String newValue="";
		String patronDot="[-]*[0-9]+.[0-9]+"; 
		String patronComma="[-]*[0-9]+,[0-9]+";
		if(value!=null && value.length()>0){
			if(to.equalsIgnoreCase("ADF")){
				//. -> ,
				boolean b = Pattern.matches(patronDot, value);
				if(b){
					newValue=value.replace('.', ',');
				}else{
					newValue=value;
				}				
			}else if(to.equalsIgnoreCase("BD")){
				//, -> .
				boolean b = Pattern.matches(patronComma, value);
				if(b){
					newValue=value.replace(',', '.');
				}else{
					newValue=value;
				}	
			}
			return newValue;
		}else
			return "";		
	}
   
   
    public static Collection extraeCollContenidosByCodigo(FormRenta form,String cod){
        ItemRenta itemI=null;
        Collection contenidos=new ArrayList();
        BigDecimal codB=new BigDecimal(cod.trim());
        if(form.getItemsRenta()!=null && form.getItemsRenta().length>0){
            ItemRenta items[]=form.getItemsRenta();
            //itemI=new ItemRenta();
            for(int i=0;i<items.length;i++){
                 ItemRenta item=items[i];
                 if(new BigDecimal(item.getCodigo()).intValue()==codB.intValue()){
                    itemI=item;
                    if(itemI!=null){
                        if(itemI.getContenido()!=null && itemI.getContenido().trim().length()>0){
                            contenidos.add(itemI.getContenido().trim());   
                        }else{
                            contenidos.add("");  
                        }    
                    }                    
                 }   
            }                
        }
        return contenidos;        
    }        
    public static String extraeContenidoByCodigo(FormRenta form,String cod){
        ItemRenta itemI=null;
        BigDecimal codB=new BigDecimal(cod.trim());
        if(form.getItemsRenta()!=null && form.getItemsRenta().length>0){
            ItemRenta items[]=form.getItemsRenta();
            //itemI=new ItemRenta();
            for(int i=0;i<items.length;i++){
                 ItemRenta item=items[i];
                 if(new BigDecimal(item.getCodigo()).intValue()==codB.intValue()){
                    itemI=item;
                    break;
                 }   
            }
            if(itemI!=null){
                if(itemI.getContenido()!=null && itemI.getContenido().trim().length()>0){
                    return itemI.getContenido();   
                }else
                    return "";        
            }else
                return null;     
        }else
            return null;                                           
    }  
    
    public static boolean regla125(FormRenta form){
        String rut=null;
        if(extraeContenidoByCodigo(form,"8811")!=null && extraeContenidoByCodigo(form,"8811").length()>0){    
            rut=extraeContenidoByCodigo(form,"8811");      
            if(rut!=null && !rut.trim().equals("CLP")){
                return true;
            }else
                return false;
                
        }else
            return false;    
    }      
    
    public static boolean regla126(FormRenta form){
        
        if(form.getFormulario()!=null && form.getFormulario().length()>0){
            String formu=form.getFormulario();                     
            BigDecimal f=new BigDecimal(formu);
            if(f.intValue()==22){
                return true;
            }else 
                return false;    
        }else 
            return false;
    } 
    
    
     public static boolean regla126b(FormRenta form){
        
        if(form.getFormulario()!=null && form.getFormulario().length()>0){
            String formu=form.getFormulario();                     
            BigDecimal f=new BigDecimal(formu);
            //comenta linea para dejar no disponible form 29
            //if(f.intValue()==29 || f.intValue()==50 || f.intValue()==21 || f.intValue()==22 || f.intValue()==45){
            // Se incorpora F-54 (**DHN: 01-08-2012**)
            if(f.intValue()==50 || f.intValue()==21 || f.intValue()==22 || f.intValue()==45 || f.intValue()==10 || f.intValue()==55 || f.intValue()==54){
                return true;
            }else 
                return false;    
        }else 
            return false;
    } 
    
    public static boolean regla127(FormRenta form){
        if(form.getRutContr()!=null && form.getDvContr()!=null && form.getRutContr().length()>0 && form.getDvContr().length()>0){
            String rut=form.getRutContr();
            String dv=form.getDvContr();
			if(rut.indexOf(".")!=-1 || rut.indexOf(",")!=-1){
				return false;
			}
			rut=new BigDecimal(rut).toString();
			char arrayRut[]=rut.toCharArray();
			int indiceM=2;
			int total=0;
			int ultimo=arrayRut.length-1;
			boolean sw=false;
			for(int i=ultimo;i>=0;i--){
				int calcI=new BigDecimal(new Character(arrayRut[i]).toString()).intValue()*indiceM;
				total+=calcI;
				if(indiceM<7){
					indiceM++;	
				}else if(indiceM==7){
					indiceM=2;
				}
			}
			//total=total*10;
			int mod=total%11;
			String dv1="";
			if(mod==1){
				dv1="K";
			}else if(mod==0){
				dv1="0";
			}else{
				dv1=new Integer(11-mod).toString();
			}
            if(dv1.equalsIgnoreCase(dv)){
                return true;    
            }else{
                return false;
            }           
        }else
            return false; 
    }       
    
    public static boolean regla128(FormRenta form){        
        if(extraeContenidoByCodigo(form,"3")!=null && extraeContenidoByCodigo(form,"3").length()>0){    
            String rut=extraeContenidoByCodigo(form,"3");        
            String rutMant=rut.substring(0, rut.length()-1);
            String dv=String.valueOf(rut.charAt(rut.length()-1));
            BigDecimal rutNum=new BigDecimal(rutMant);
            if(validaRUTConDV(rutNum.toString(),dv)){
                return true;
           }else
                return false;                     
        }else
            return false;                                       
    } 
    
    
    
    public static boolean regla129(FormRenta form){
        ItemRenta itemI;
        if(extraeContenidoByCodigo(form,"3")!=null && extraeContenidoByCodigo(form,"3").length()>0){    
           String rut=extraeContenidoByCodigo(form,"3");   
           //BigDecimal rutNum=new BigDecimal(rut);    
           
           String rutMant=rut.substring(0, rut.length()-1);
           String dv=String.valueOf(rut.charAt(rut.length()-1));
           BigDecimal rutNum=new BigDecimal(rutMant);  
           
           String rutStr=rutNum.toString()+dv;
           String rutInfo=(form.getRutContr()+form.getDvContr()).trim();
           if(rutStr.equals(rutInfo)){
                return true;
           }else
                return false;                                                    
        }else
            return false;                              
    }
    
     public static boolean regla130(FormRenta form){
        String valor_7=null;
        if(extraeContenidoByCodigo(form,"7")!=null && extraeContenidoByCodigo(form,"7").length()>0){    
           valor_7=extraeContenidoByCodigo(form,"7");   
        }else{
            valor_7="0";
        }    
        //TODO: periodo 
        String periodo=form.getPeriodo();
        
        if(periodo!=null && periodo.length()==6){
            periodo=String.valueOf(periodo.charAt(3));
        }else
            periodo="0";    
                
        //cod mod 10       
        int resto=(int)(new BigDecimal(valor_7).longValue() % 10);
        
        if(resto==new Integer(periodo).intValue()){
            return true;
        }else{
            return false;
        }            
                            
    }
    
    
    
    public static boolean regla131(FormRenta form){
        if(form.getMarcaFisc()!=null && form.getMarcaFisc().length()>0){
            if(form.getMarcaFisc().equals("0") || form.getMarcaFisc().equals("3")){
                return true;                
            }else
                return false;        
        }else
            return false;    
    }
    
    public static boolean regla132(FormRenta form){
        String data87=null;
        boolean resultado=true;
        if(extraeContenidoByCodigo(form,"087")!=null && extraeContenidoByCodigo(form,"087").length()>0){    
            data87=extraeContenidoByCodigo(form,"087");
        }
        
        BigDecimal marca;
        if(form.getMarcaFisc()!=null && form.getMarcaFisc().length()>0){
            marca=new BigDecimal(form.getMarcaFisc());
        }else
            marca=new BigDecimal(0);   
        
        BigDecimal monto;
        if(form.getMontoRet()!=null && form.getMontoRet().length()>0){
            monto=new BigDecimal(form.getMontoRet());
        }else
            monto=new BigDecimal(0);
            
        if(data87!=null && new BigDecimal(data87).intValue()>0){
            if(monto.intValue()==new BigDecimal(data87).intValue()){
                resultado=true;    
            }else
                resultado=false;    
        }        
            
        return resultado;
    }
    
    public static boolean regla133(FormRenta form){
        boolean resultado=true;
        String data87=null;
        if(extraeContenidoByCodigo(form,"087")!=null && extraeContenidoByCodigo(form,"087").length()>0){    
            data87=extraeContenidoByCodigo(form,"087");
        }
        
        BigDecimal marca;
        if(form.getMarcaFisc()!=null && form.getMarcaFisc().length()>0){
            marca=new BigDecimal(form.getMarcaFisc());
        }else
            marca=new BigDecimal(0);   
        
        BigDecimal monto;
        if(form.getMontoRet()!=null && form.getMontoRet().length()>0){
            monto=new BigDecimal(form.getMontoRet());
        }else
            monto=new BigDecimal(0);
            
        if(marca.intValue()==3){
            if(monto.intValue()==0){
                resultado=true;
            }else{
                resultado=false;
            }        
        }
        return resultado;              
    }
    
    
    public static boolean regla134(FormRenta form){
        if(form.getPeriodo()!=null && form.getPeriodo().length()==6){
            String periodo=form.getPeriodo();
            Date hoy=new Date();
            SimpleDateFormat sdf1=new SimpleDateFormat("yyyy");
            String periodoHoy=sdf1.format(hoy)+"04";                    
            if(periodo.equals(periodoHoy)){
                return true; 
            }else{
                return false; 
            }        
        }else
            return false;                      
     } 
    
     public static boolean regla135(FormRenta form){
        ItemRenta itemI;
        if(extraeContenidoByCodigo(form,"315")!=null && extraeContenidoByCodigo(form,"315").length()>0){    
            String fecha=extraeContenidoByCodigo(form,"315");        
            SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
                try{
                    Date fechaDate=sdf.parse(fecha);
                    return true;
                }catch(ParseException ex){
                    return false;    
                }        
        }else
            return false;                          
     } 
     
    public static boolean regla136(FormRenta form){
        if(extraeContenidoByCodigo(form,"315")!=null && extraeContenidoByCodigo(form,"315").length()>0){    
            String fecha=extraeContenidoByCodigo(form,"315");
            SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
            Date fechaout=null;
            try{
                fechaout=sdf.parse(fecha);
            }catch(ParseException ex){
                return false;
            }  
            Date hoy=new Date();
            if(fechaout.getTime()<=hoy.getTime()){
                return true;
            }else          
                return false;
        }else            
            return false;                   
    }
    
    public static boolean regla137(FormRenta form){
        String data87=null;
        boolean resultado=true;
        if(extraeContenidoByCodigo(form,"087")!=null && extraeContenidoByCodigo(form,"087").length()>0){    
            data87=extraeContenidoByCodigo(form,"087");
        }
        
        BigDecimal marca;
        if(form.getMarcaFisc()!=null && form.getMarcaFisc().length()>0){
            marca=new BigDecimal(form.getMarcaFisc());
        }else
            marca=new BigDecimal(99);   
        
        BigDecimal monto;
        if(form.getMontoRet()!=null && form.getMontoRet().length()>0){
            monto=new BigDecimal(form.getMontoRet());
        }else
            monto=new BigDecimal(99);
        
        if(marca.intValue()==0){
            if(data87!=null && monto.intValue()==new BigDecimal(data87).intValue()){
                resultado=true;
            }else{
                resultado=false;
            }        
        }
        return resultado;    
    }
    
    public static boolean regla138(FormRenta form){
         boolean resultado=true;
        String data87=null;
        if(extraeContenidoByCodigo(form,"087")!=null && extraeContenidoByCodigo(form,"087").length()>0){    
            data87=extraeContenidoByCodigo(form,"087");
        }
        
        BigDecimal marca;
        if(form.getMarcaFisc()!=null && form.getMarcaFisc().length()>0){
            marca=new BigDecimal(form.getMarcaFisc());
        }else
            marca=new BigDecimal(0);   
                    
        if(data87!=null && new BigDecimal(data87).intValue()>0){
            if(marca.intValue()==0){
               resultado=true; 
            }else{    
                resultado=false;
            }
        }
        return resultado;
    }
    
    public static boolean regla139(FormRenta form){
        boolean resultado=true;
        String data87=null;
        if(extraeContenidoByCodigo(form,"087")!=null && extraeContenidoByCodigo(form,"087").length()>0){    
            data87=extraeContenidoByCodigo(form,"087");
        }
        
        BigDecimal marca;
        if(form.getMarcaFisc()!=null && form.getMarcaFisc().length()>0){
            marca=new BigDecimal(form.getMarcaFisc());
        }else
            marca=new BigDecimal(0);   
                    
        if(data87!=null && new BigDecimal(data87).intValue()==0){
            if(marca.intValue()==3){                
                resultado=true;
            }else{
                resultado=false;
            }        
        }
        return resultado;
    }
    
     public static boolean regla140(FormRenta form){
        String data87=null;
        boolean resultado =true;
        if(extraeContenidoByCodigo(form,"087")!=null && extraeContenidoByCodigo(form,"087").length()>0){    
            data87=extraeContenidoByCodigo(form,"087");
        }
        
        String data91=null;
        if(extraeContenidoByCodigo(form,"091")!=null && extraeContenidoByCodigo(form,"091").length()>0){    
            data91=extraeContenidoByCodigo(form,"091");
        }
                    
        if(data87!=null && new BigDecimal(data87).intValue()>0){
            if(data91!=null && new BigDecimal(data91).intValue()==0){
                resultado=true;
            }else{
                resultado=false;
            }        
        }
        return resultado;                 
  }  
    
    public static boolean regla142(FormRenta form){
        String data9927=null;
        if(extraeContenidoByCodigo(form,"9927")!=null && extraeContenidoByCodigo(form,"9927").length()>0){    
            data9927=extraeContenidoByCodigo(form,"9927");
        }
                            
        if(data9927!=null){
            return true;
        }else
            return false;                 
    }
    
    public static boolean regla143(FormRenta form){
        String data87=null;
        if(extraeContenidoByCodigo(form,"87")!=null && extraeContenidoByCodigo(form,"87").length()>0){    
            data87=extraeContenidoByCodigo(form,"87");
        }
        
        String data91=null;
        if(extraeContenidoByCodigo(form,"91")!=null && extraeContenidoByCodigo(form,"91").length()>0){    
            data91=extraeContenidoByCodigo(form,"91");
        }
        
        String data315=null;
        if(extraeContenidoByCodigo(form,"315")!=null && extraeContenidoByCodigo(form,"315").length()>0){    
            data315=extraeContenidoByCodigo(form,"315");
        } 
        
        String data82=null;
        if(extraeContenidoByCodigo(form,"82")!=null && extraeContenidoByCodigo(form,"82").length()>0){    
            data82=extraeContenidoByCodigo(form,"82");
        }
        //TODO: campos
        String tipo=null;
        if(form.getFormulario()!=null && form.getFormulario().length()>0){
            tipo=form.getFormulario();    
        } 
        
        String rut=null;
        if(form.getRutContr()!=null && form.getRutContr().length()>0){
            rut=form.getRutContr();    
        }           
        
        String numFolio=null;
        if(form.getFolioDecl()!=null && form.getFolioDecl().length()>0){
            numFolio=form.getFolioDecl();    
        } 
        
        String periodo=null;
        if(form.getPeriodo()!=null && form.getPeriodo().length()>0){
            periodo=form.getPeriodo();    
        }
        
        //TODO: campos
        if(data87!=null && data87.length()>0 && data91!=null && data91.length()>0 &&
            data315!=null && data315.length()>0 && tipo!=null && tipo.length()>0 &&
            rut!=null && rut.length()>0 && numFolio!=null && numFolio.length()>0 &&
            periodo!=null && periodo.length()>0 && form.getMessageId().getCode().equals("RTRMASME") &&
            data82!=null && data82.length()>0){
                return true;
            }else{
                return false;
            }            
    }
    
     public static boolean regla144(FormRenta form){
        String data03=null;
        if(extraeContenidoByCodigo(form,"03")!=null && extraeContenidoByCodigo(form,"03").length()>0){    
            data03=extraeContenidoByCodigo(form,"03");
        }
        
        String data07=null;
        if(extraeContenidoByCodigo(form,"07")!=null && extraeContenidoByCodigo(form,"07").length()>0){    
            data07=extraeContenidoByCodigo(form,"07");
        }
        
        if(data03!=null && data03.length()>0 && data07!=null && data07.length()>0){
            return true;            
        }else{
            return false;
        }       
     }   
     public static boolean regla145(FormRenta form){
        String data03=null;
        if(extraeContenidoByCodigo(form,"03")!=null && extraeContenidoByCodigo(form,"03").length()>0){    
            BigDecimal rutNum=new BigDecimal(extraeContenidoByCodigo(form,"03"));                     
            data03=rutNum.toString().substring(0,rutNum.toString().length()-1);
        }
                
        String rut=null;
        if(form.getRutContr()!=null && form.getRutContr().length()>0){
            rut=form.getRutContr();
        }      
        
        if(data03!=null && rut!=null && rut.equals(data03)){
            return true;            
        }else{
            return false;            
        }        
     }
    
     public static boolean regla146(FormRenta form){
        BigDecimal folio=null;
        if(extraeContenidoByCodigo(form,"07")!=null && extraeContenidoByCodigo(form,"07").length()>0){    
            folio=new BigDecimal(extraeContenidoByCodigo(form,"07"));                     
        }
                
        BigDecimal folioC=null;
        if(form.getFolioDecl()!=null && form.getFolioDecl().length()>0){
            folioC=new BigDecimal(form.getFolioDecl());
        }      
        
        if(folio!=null && folioC!=null && folio.intValue()==folioC.intValue()){
            return true;            
        }else{
            return false;            
        }        
     }
    
    public static boolean regla149(FormRenta form){
        if(form.getMarcaFisc()!=null && form.getMarcaFisc().length()>0){
            if(new BigDecimal(form.getMarcaFisc()).intValue()==7){
                return true;
            }else
                return false;         
        }else
            return false;     
    }
    
   
    public static boolean regla150(FormRenta form){
        if(form.getFormulario()!=null && form.getFormulario().length()>0){
            if(new BigDecimal(form.getFormulario()).intValue()==22){
                return true;
            }else
                return false;         
        }else
            return false;     
    } 
        
    public static boolean regla151(FormRenta form){
        if(form.getSigno()!=null && form.getSigno().length()>0){
            if(form.getSigno().equals("-")){
                return true;
            }else
                return false;         
        }else
            return false;     
    }
    
    public static boolean regla153(FormRenta form){
        String data315=null;
        if(extraeContenidoByCodigo(form,"315")!=null && extraeContenidoByCodigo(form,"315").length()>0){    
            data315=extraeContenidoByCodigo(form,"315");                     
            
        }                  
        if(data315!=null && data315.length()>0){
            Date hoy=new Date();
            int year=hoy.getYear();
            String fechLimite=year+"0430";
            
            SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");            
            Date fechLimit=null;
            Date fecha=null;
            try{
                fechLimit=sdf.parse(fechLimite);
                fecha=sdf.parse(data315);            
            }catch(ParseException ex){
                return false;
            }
            if(fecha.getTime()<=fechLimit.getTime()){
                return true;    
            }else
                return false;       
        }else
            return false;    
    }
    
    //TODO: nuevas reglas
    
    public static boolean regla154(FormRenta form){
        
        String value8811=null;String value8822=null;
        String value8833=null;String value8844=null;
        String value8866=null;String value8877=null;
        
        if(extraeContenidoByCodigo(form,"8811")!=null && extraeContenidoByCodigo(form,"8811").length()>0){    
            value8811=extraeContenidoByCodigo(form,"8811");                                 
        }                  
        
        if(extraeContenidoByCodigo(form,"8822")!=null){    
            value8822=extraeContenidoByCodigo(form,"8822");                                 
        }
        
        if(extraeContenidoByCodigo(form,"8833")!=null && extraeContenidoByCodigo(form,"8833").length()>0){    
            value8833=extraeContenidoByCodigo(form,"8833");                                 
        }
        
        if(extraeContenidoByCodigo(form,"8844")!=null){    
            value8844=extraeContenidoByCodigo(form,"8844");                                 
        }
        
        if(extraeContenidoByCodigo(form,"8866")!=null && extraeContenidoByCodigo(form,"8866").length()>0){    
            value8866=extraeContenidoByCodigo(form,"8866");                                 
        }
        
        if(extraeContenidoByCodigo(form,"8877")!=null){    
            value8877=extraeContenidoByCodigo(form,"8877");                                 
        }
        
        if(value8811!=null && value8822!=null && value8833!=null && value8844!=null && value8866!=null && value8877!=null){                            
            return true;    
        }else{
            return false;    
        }           
        
    }
    
    /*public static boolean regla155(FormRenta form){        
        String value8811=null;    
        
        if(extraeContenidoByCodigo(form,"8811")!=null && extraeContenidoByCodigo(form,"8811").length()>0){    
            value8811=extraeContenidoByCodigo(form,"8811");                                 
        }     
                                             
        if(value8811!=null && !value8811.equals("CLP") && !value8811.equals(" ")){                                        
            return true;    
        }else{
            return false;    
        }                   
    }*/
    
    //si 8822 es valido
    public static boolean regla156(FormRenta form){       
         
        String value8822=null;            
        if(extraeContenidoByCodigo(form,"8822")!=null){    
            value8822=extraeContenidoByCodigo(form,"8822");                                 
        }             
        boolean resp1=false;
        boolean resp=true;
        if(value8822!=null && value8822.length()==0){
            resp1=true;    
        }else if(value8822!=null && value8822.trim().length()>0){    
            Collection col=CargarProperties.getValuePropIntoColl("rentaME.currencies");
            Iterator ite=col.iterator();
            while(ite.hasNext()){
                String valorI=(String)ite.next();
                if(valorI.trim().equalsIgnoreCase(value8822.trim())){
                    resp1=true;
                    break;
                }    
            }                
        }       
        resp=resp1;
        return resp;                                                  
    }
    
    //8822 es distinto a CLP 
    public static boolean regla157(FormRenta form){        
        String value8822=null;
        boolean resp=true;            
        if(extraeContenidoByCodigo(form,"8822")!=null){    
            value8822=extraeContenidoByCodigo(form,"8822");                                 
        }             
        
        if(value8822!=null && value8822.trim().length()>0){
            if(!value8822.equals("CLP")){
                resp=true;
            }else{
                resp=false;
            }        
        }   
        return resp;                                                                       
    }
    
    
    //8833 sea diastinto a 1 
    public static boolean regla158(FormRenta form){        
        String value8833=null;
                    
        if(extraeContenidoByCodigo(form,"8833")!=null && extraeContenidoByCodigo(form,"8833").length()>0){    
            value8833=extraeContenidoByCodigo(form,"8833");                                 
        }             
        
        if(value8833!=null && value8833.trim().length()>0){
            if(!value8833.equals("1")){
                return true;
            }else{
                return false;
            }        
        }else{
            return false;
        }                                                                              
    }
    
    //8833 sea diastinto a 1 
    public static boolean regla158b(FormRenta form){        
        String value8833=null;
        boolean resp=true;            
        if(extraeContenidoByCodigo(form,"8833")!=null && extraeContenidoByCodigo(form,"8833").length()>0){    
            value8833=extraeContenidoByCodigo(form,"8833");                                 
        }             
        
        if(value8833!=null && value8833.trim().length()>0){
            if(new BigDecimal(value8833).longValue()==1){
                resp=true;
            }else{
                resp=false;
            }        
        }                                                                             
        
        return resp;
    }
    
    
    public static boolean regla159(FormRenta form){        
        String value8844=null;
        String value8833=null;
        boolean resp=true;            
        if(extraeContenidoByCodigo(form,"8833")!=null && extraeContenidoByCodigo(form,"8833").length()>0){    
            value8833=extraeContenidoByCodigo(form,"8833");                                 
        } 
        
        if(extraeContenidoByCodigo(form,"8844")!=null && extraeContenidoByCodigo(form,"8844").length()>0){    
            value8844=extraeContenidoByCodigo(form,"8844");                                 
        }              
                
        if(value8844!=null && value8844.trim().length()>0){
            if(new BigDecimal(value8844).intValue()>0){
                if(new BigDecimal(value8833).intValue()>0 && value8833.trim().length()>0){
                    resp=true;
                }else{
                    resp=false;
                }                           
            }    
        }                
        return resp;                                                                                
    }
    
    
    public static boolean regla160(FormRenta form){        
        String value8844=null;
        String value8822=null;
        boolean resp=true;
                    
        if(extraeContenidoByCodigo(form,"8822")!=null){    
            value8822=extraeContenidoByCodigo(form,"8822");                                 
        } 
        
        if(extraeContenidoByCodigo(form,"8844")!=null && extraeContenidoByCodigo(form,"8844").length()>0){    
            value8844=extraeContenidoByCodigo(form,"8844");                                 
        }              
        
        if(value8844!=null && value8844.trim().length()>0){
            if(new BigDecimal(value8844).intValue()>0){
                if(value8822!=null && value8822.trim().length()>0 && !value8822.trim().equals("CLP")){
                    resp=true;                    
                }else{
                    resp=false;
                }        
            }    
        }    
        return resp;                                                           
    }
    
    public static boolean regla161(FormRenta form){        
        String value8844=null;        
        if(extraeContenidoByCodigo(form,"8844")!=null){    
            value8844=extraeContenidoByCodigo(form,"8844");                                 
        }              
                
        if(value8844!=null && value8844.trim().length()>0 && new BigDecimal(value8844).intValue()>=0){            
            return true;                
        }else{
            return false;
        }               
                                                                                        
    }
    
    public static boolean regla162(FormRenta form){        
        String value8822=null;
        String value8877=null;                
        boolean resp=true;
        if(extraeContenidoByCodigo(form,"8822")!=null){    
            value8822=extraeContenidoByCodigo(form,"8822");                                
        }  
          
        if(extraeContenidoByCodigo(form,"8877")!=null && extraeContenidoByCodigo(form,"8877").length()>0){    
            value8877=extraeContenidoByCodigo(form,"8877");                                 
        }                
                
        if(value8822!=null && value8822.trim().length()>0){
            if(value8877==null || value8877.trim().length()==0){
                resp=true;
            }else{
               resp=false;
            }    
        }                            
        return resp;                                                                               
    }
    
    
    
    public static boolean regla162b(FormRenta form){        
        String value8822=null;
        String value8877=null;                
        boolean resp=true;            
        if(extraeContenidoByCodigo(form,"8877")!=null){    
            value8877=extraeContenidoByCodigo(form,"8877");                                 
        }                
                    
        if(value8877==null || value8877.trim().length()==0){
            resp=true;
        }else{
           resp=false;
        }                                        
        return resp;                                                                               
    }
    
    
    
    public static boolean regla163(FormRenta form){        
        String value87=null;
        String value8844=null;  
        boolean resp=true;              
        if(extraeContenidoByCodigo(form,"87")!=null && extraeContenidoByCodigo(form,"87").length()>0){    
            value87=extraeContenidoByCodigo(form,"87");                                 
        } 
        
        if(extraeContenidoByCodigo(form,"8844")!=null && extraeContenidoByCodigo(form,"8844").length()>0){    
            value8844=extraeContenidoByCodigo(form,"8844");                                 
        }                
        
        if(value87!=null){                    
            if(new BigDecimal(value87).intValue()>0){
                if(value8844!=null && new BigDecimal(value8844).intValue()==0){
                    resp=true;  
                }else{
                    resp=false; 
                }                        
            }                                 
        }                
        return resp;                                                                                       
    }
    
    public static boolean regla164(FormRenta form){        
        String value8866=null;
        String value8811=null;    
        String patronNumWithDec="[0-9]{1,13}[.]{1}[0-9]{2}"; 
        
                    
        if(extraeContenidoByCodigo(form,"8811")!=null && extraeContenidoByCodigo(form,"8811").length()>0){    
            value8811=extraeContenidoByCodigo(form,"8811");                                 
        } 
        
        if(extraeContenidoByCodigo(form,"8866")!=null && extraeContenidoByCodigo(form,"8866").trim().length()>0){    
            value8866=extraeContenidoByCodigo(form,"8866");                                 
        }                
        boolean resp=true;        
        if(value8811!=null && !value8811.trim().equals("CLP")){
            if(value8866!=null){
                boolean b = Pattern.matches(patronNumWithDec,value8866.trim());
                if(!b){
                    resp=false; 
                }else{
                    if(new BigDecimal(value8866.trim()).intValue()>0){
                        resp=true;     
                    }else{
                        resp=false;        
                    }                            
                }        
                
            }else{
                resp=false;               
            }        
            /*if(value8866!=null) && new BigDecimal(value8866).intValue()>0)            
                resp=true;                
            else
                resp=false;                */
        }
        return resp;                                                                                                     
    }
    
    
    
    public static boolean regla172(FormRenta form){        
        String value8866=null;
      
        String patronNumWithDec="[0-9]{1,13}[.]{1}[0-9]{2}"; 
            
        if(extraeContenidoByCodigo(form,"8866")!=null && extraeContenidoByCodigo(form,"8866").trim().length()>0){    
            value8866=extraeContenidoByCodigo(form,"8866");                                 
        }                
        boolean resp=true;        
      
        if(value8866!=null){
            boolean b = Pattern.matches(patronNumWithDec,value8866.trim());
            if(!b){
                resp=false; 
            }else{            
                resp=true;                     
            }        
            
        }else{
            resp=false;               
        }                        
        return resp;                                                                                                     
    }
    
    
    public static boolean regla165(FormRenta form){        
        String value8822=null;
        String value8844=null;  
        boolean resp=true;              
        if(extraeContenidoByCodigo(form,"8822")!=null){    
            value8822=extraeContenidoByCodigo(form,"8822");                                 
        } 
        
        if(extraeContenidoByCodigo(form,"8844")!=null && extraeContenidoByCodigo(form,"8844").length()>0){    
            value8844=extraeContenidoByCodigo(form,"8844");                                 
        }                
        
        if(value8822!=null){                    
            if(value8822.trim().length()==0){
                if(value8844!=null && value8844.trim().length()>0 && new BigDecimal(value8844).intValue()==0){
                    resp=true;
                }else{
                    resp=false;
                }        
            }                                     
        }                
        return resp;                                                                                       
    }
    
    public static boolean regla166(FormRenta form){ 
        String value8811=null;                
        boolean resp=true;
        if(extraeContenidoByCodigo(form,"8811")!=null && extraeContenidoByCodigo(form,"8811").length()>0){    
            value8811=extraeContenidoByCodigo(form,"8811");                                 
        }
        
        if(value8811!=null && value8811.length()>0){
            if(!value8811.equals("CLP")){
                resp=true;
            }else{
                resp=false;        
            }            
        }
        return resp;    
    }    
      
      
    public static boolean regla167(FormRenta form){ 
        String value03=null;
        String value01=null;                                
        String value02=null;                
        String value05=null;                
        boolean resp=true;
        if(extraeContenidoByCodigo(form,"3")!=null){    
            value03=extraeContenidoByCodigo(form,"3");                                 
        }
        
        if(extraeContenidoByCodigo(form,"1")!=null){    
            value01=extraeContenidoByCodigo(form,"1");                                 
        }
        
        if(extraeContenidoByCodigo(form,"2")!=null){    
            value02=extraeContenidoByCodigo(form,"2");                                 
        }
        
        if(extraeContenidoByCodigo(form,"5")!=null){    
            value05=extraeContenidoByCodigo(form,"5");                                 
        }
        value03=value03.substring(0,value03.length()-1);
        if(value03!=null && value03.trim().length()>0){
            if(new BigDecimal(value03.trim()).longValue()>=50000000){
                if((value01!=null && value01.trim().length()>0)&&(value02==null || (value02!=null && value02.trim().length()==0))&&(value05==null ||(value05!=null && value05.trim().length()==0))){
                    resp=true;    
                }else{
                    resp=false;
                }                       
            }                                
        }
        return resp;    
    } 
    
    
    public static boolean regla168(FormRenta form){ 
        String value03=null;
        String value01=null;                                
        String value02=null;                
        String value05=null;          
        boolean resp=true;
        if(extraeContenidoByCodigo(form,"3")!=null){    
            value03=extraeContenidoByCodigo(form,"3");                                 
        }
        
        if(extraeContenidoByCodigo(form,"1")!=null){    
            value01=extraeContenidoByCodigo(form,"1");                                 
        }
        
        if(extraeContenidoByCodigo(form,"2")!=null){    
            value02=extraeContenidoByCodigo(form,"2");                                 
        }
        
        if(extraeContenidoByCodigo(form,"5")!=null){    
            value05=extraeContenidoByCodigo(form,"5");                                 
        }
        
        value03=value03.substring(0,value03.length()-1);
        
        if(value03!=null && value03.trim().length()>0){
            if(new BigDecimal(value03.trim()).longValue()<50000000){
                if((value05!=null && value05.trim().length()>0)&&(
                    ((value01!=null && value01.trim().length()>0)||(value01!=null && value01.trim().length()>0 && value01.trim().startsWith("SUC")))||
                    (value02!=null && value02.trim().length()>0))){
                    resp=true;    
                }else{
                    resp=false;
                }                       
            }                                
        }
        return resp;    
    }
    
    //Valida que exista item 8811 en form45
    public static boolean regla169(FormRenta form){
        String value8811=null;                
        boolean resp=true;
        String valor=extraeContenidoByCodigo(form,"8811");       
        if(valor!=null && valor.trim()!=null && valor.trim().length()>0){    
            resp=true;                    
        }else{              
            resp=false;
        }                
        return resp;    
    }    
    
    //Valida que exista item 8866 en form45
    public static boolean regla170(FormRenta form){        
        boolean resp=true;
        String valor=extraeContenidoByCodigo(form,"8866");       
        if(valor!=null && valor.trim()!=null && valor.trim().length()>0){    
            resp=true;                    
        }else{              
            resp=false;
        }                
        return resp;    
    }  
    
    //Valida que exista item 8877 en form45
    public static boolean regla171(FormRenta form){        
        boolean resp=true;
        String valor=extraeContenidoByCodigo(form,"8877");       
        if(valor!=null && valor.trim()!=null && valor.trim().length()>0){    
            resp=true;                    
        }else{              
            resp=false;
        }                
        return resp;    
    }   
    
        
    public static boolean validaRUTConDV(String rut, String dv){
        
        if(rut!=null && dv!=null && rut.length()>0 && dv.length()>0){
            
			if(rut.indexOf(".")!=-1 || rut.indexOf(",")!=-1){
				return false;
			}
			rut=new BigDecimal(rut).toString();
			char arrayRut[]=rut.toCharArray();
			int indiceM=2;
			int total=0;
			int ultimo=arrayRut.length-1;
			boolean sw=false;
			for(int i=ultimo;i>=0;i--){
				int calcI=new BigDecimal(new Character(arrayRut[i]).toString()).intValue()*indiceM;
				total+=calcI;
				if(indiceM<7){
					indiceM++;	
				}else if(indiceM==7){
					indiceM=2;
				}
			}
			//total=total*10;
			int mod=total%11;
			String dv1="";
			if(mod==1){
				dv1="K";
			}else if(mod==0){
				dv1="0";
			}else{
				dv1=new Integer(11-mod).toString();
			}
            if(dv1.equalsIgnoreCase(dv)){
                return true;    
            }else{
                return false;
            }           
        }else
            return false;         
    }    
    
    
    public static String validateCodeError(String arg){
        int charIni=arg.indexOf("[");
        int charFin=arg.indexOf("]");
        if(arg!=null){
            if(charIni!=-1 && charFin!=-1){
                String newcode=arg.substring(charIni+1,charFin);
                return newcode;
            }else{
                return arg;
            }           
        }else{
            return null;   
        }    
            
    }    
    
    public static String buildMessageException(RentaMEGenericException e){
        String mensaje="<Message>"; 
        mensaje+="<Validado>ERR</Validado>";
        mensaje+="<Resultado>";
            mensaje+="<Aprobacion>NOK</Aprobacion>";
        mensaje+="</Resultado>";
        mensaje+="<Errores>";
            mensaje+="<Error>";
                mensaje+="<ErrorNro>" + e.getTypeError()+ "</ErrorNro>";
                mensaje+="<Glosa>" +(e.getTypeError()==1?"Error Interno TGR":"Mensaje Invalido")+ "</Glosa>";
            mensaje+="</Error>";
        mensaje+="</Errores>";
        mensaje+="</Message>";
        return mensaje;
    }    
    
    public static FormRenta extraeFormPositivo(FormRenta []arrayForm){
        Collection errores=new ArrayList();
        FormRenta formPos=null;
        for(int i=0;i<arrayForm.length;i++){
            FormRenta formI=arrayForm[i];
            if(formI.getSigno()!=null && formI.getSigno().trim().length()>0 && formI.getSigno().trim().equals("+")){
                formPos=formI;
                break;   
            }
        }                                
        return formPos;
    }    
    
    public static String buildMessageResponse(FormularioResult formResult, FormRenta []arrayForm){
        String msgResponse="";       
        SimpleDateFormat sdf1=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        SimpleDateFormat sdf2=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        
        FormRenta formPos=extraeFormPositivo(arrayForm);
        if(formPos!=null){
            msgResponse+="<Message>";
                msgResponse+="<MessageId>";
                    msgResponse+="<Code>"+formPos.getMessageId().getCode()+"</Code>";
                    msgResponse+="<MsgDesc>"+formPos.getMessageId().getMsgDesc()+"</MsgDesc>";
                    msgResponse+="<Version>"+formPos.getMessageId().getVersion()+"</Version>";
                    try{
                        Date fechVer=sdf1.parse(formPos.getMessageId().getFechaVersion());       
                                                             
                        sdf1.applyPattern("dd-MM-yyyy HH:mm");                                                                                 
                        msgResponse+="<FechaVersion>"+sdf1.format(fechVer)+"</FechaVersion>";                   
                    }catch(ParseException e){
                        
                    }
                    
                    msgResponse+="<FromAddress>"+formPos.getMessageId().getToAddress()+"</FromAddress>";
                    msgResponse+="<ToAddress>"+formPos.getMessageId().getFromAddress()+"</ToAddress>";
                    msgResponse+="<RefAddress>"+formPos.getMessageId().getRefAddress()+"</RefAddress>";
                    try{
                        Date fechTime=sdf2.parse(formPos.getMessageId().getDateTime()); 
                        sdf2.applyPattern("dd-MM-yyyy HH:mm");
                        msgResponse+="<DateTime>"+sdf2.format(fechTime)+"</DateTime>";    
                    }catch(ParseException e){
                    }                            
                    
                    msgResponse+="<Validado>CUT</Validado>";
                    msgResponse+="<Number>"+formPos.getMessageId().getNumber()+"</Number>";
                msgResponse+="</MessageId>";  
                
                msgResponse+="<Detalle>";
                    msgResponse+="<TipoRegistro>2</TipoRegistro>";
                    msgResponse+="<RutIra>"+formPos.getRutIra()+"</RutIra>";
                    msgResponse+="<DvIra>"+formPos.getDvIra()+"</DvIra>";
                    msgResponse+="<FolioF01>"+formPos.getFolioF01()+"</FolioF01>";
                    msgResponse+="<FechaRecepcion>00000000</FechaRecepcion>";
                    msgResponse+="<RutContr>"+formPos.getRutContr()+"</RutContr>";
                    msgResponse+="<DvContr>"+formPos.getDvContr()+"</DvContr>";
                    msgResponse+="<Formulario>"+formPos.getFormulario()+"</Formulario>";
                    msgResponse+="<Folio>"+formPos.getFolioDecl()+"</Folio>";
                    msgResponse+="<Periodo>"+formPos.getPeriodo()+"</Periodo>";
                msgResponse+="</Detalle>";
                
                if(formResult.getResultCode()==0){
                    //TODO: construir mensaje correcto..
                    msgResponse+="<Resultado>";
                    msgResponse+="<Aprobacion>OK</Aprobacion>";
                    msgResponse+="</Resultado>";        
                }else if(formResult.getResultCode()!=0 && formResult.getMensajesArray().length>0){   
                    //TODO: respuesta
                    msgResponse+="<Resultado>";
                        msgResponse+="<Aprobacion>NOK</Aprobacion>";
                    msgResponse+="</Resultado>";
                
                    //TODO: recorrer errores
                    msgResponse+="<Errores>";
                    for(int i=0;i<formResult.getMensajesArray().length;i++){
                        AdfResult result=(AdfResult)formResult.getMensajeI(i);
                        msgResponse+="<Error>";
                            msgResponse+="<ErrorNro>"+result.getNumber()+"</ErrorNro>";
                            msgResponse+="<Formulario>"+result.getFormIndex()+"</Formulario>";
                            msgResponse+="<Codigo>"+result.getObjectName()+"</Codigo>";
                            msgResponse+="<Contenido>"+result.getObjectValue()+"</Contenido>";
                            msgResponse+="<Glosa>"+result.getDescription()+"</Glosa>";
                        msgResponse+="</Error>";
                    }    
                    msgResponse+="</Errores>";
                
                }
               //TODO: tag final
               msgResponse+="</Message>";           
        }    
        return msgResponse;
    }        
    
    public static String buildMessageResponse(FormularioResult formResult,MessageDocument myMessageIn){
        String msgResponse="";
        SimpleDateFormat sdf1=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        SimpleDateFormat sdf2=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        if(formResult!=null){
            //TODO: creamos cuerpo principal de mensaje salida
            msgResponse+="<Message>";
                msgResponse+="<MessageId>";
                    msgResponse+="<Code>"+myMessageIn.getMessage().getMessageId().getCode()+"</Code>";
                    msgResponse+="<MsgDesc>"+myMessageIn.getMessage().getMessageId().getMsgDesc()+"</MsgDesc>";
                    msgResponse+="<Version>"+myMessageIn.getMessage().getMessageId().getVersion()+"</Version>";
                    try{
                        Date fechVer=sdf1.parse(myMessageIn.getMessage().getMessageId().getFechaVersion());       
                                                             
                        sdf1.applyPattern("dd-MM-yyyy HH:mm");                                                                                 
                        msgResponse+="<FechaVersion>"+sdf1.format(fechVer)+"</FechaVersion>";                   
                    }catch(ParseException e){
                        
                    }                            
                    
                    msgResponse+="<FromAddress>"+myMessageIn.getMessage().getMessageId().getToAddress()+"</FromAddress>";
                    msgResponse+="<ToAddress>"+myMessageIn.getMessage().getMessageId().getFromAddress()+"</ToAddress>";
                    msgResponse+="<RefAddress>"+myMessageIn.getMessage().getMessageId().getRefAddress()+"</RefAddress>";
                    try{
                        Date fechTime=sdf2.parse(myMessageIn.getMessage().getMessageId().getDateTime()); 
                        sdf2.applyPattern("dd-MM-yyyy HH:mm");
                        msgResponse+="<DateTime>"+sdf2.format(fechTime)+"</DateTime>";    
                    }catch(ParseException e){
                    }                            
                    
                    msgResponse+="<Validado>CUT</Validado>";
                    msgResponse+="<Number>"+myMessageIn.getMessage().getMessageId().getNumber()+"</Number>";
                msgResponse+="</MessageId>";
                
                MessageDocument.Message.Formulario.Identificacion identificacion= myMessageIn.getMessage().getFormularioArray(0).getIdentificacion();
                msgResponse+="<Detalle>";
                    msgResponse+="<TipoRegistro>2</TipoRegistro>";
                    msgResponse+="<RutIra>"+identificacion.getRutIra()+"</RutIra>";
                    msgResponse+="<DvIra>"+identificacion.getDvIra()+"</DvIra>";
                    msgResponse+="<FolioF01>"+identificacion.getFolioF01()+"</FolioF01>";
                    msgResponse+="<FechaRecepcion>00000000</FechaRecepcion>";
                    msgResponse+="<RutContr>"+identificacion.getRutContr()+"</RutContr>";
                    msgResponse+="<DvContr>"+identificacion.getDvContr()+"</DvContr>";
                    msgResponse+="<Formulario>"+identificacion.getFormulario()+"</Formulario>";
                    msgResponse+="<Folio>"+identificacion.getFolioDecl()+"</Folio>";
                    msgResponse+="<Periodo>"+identificacion.getPeriodo()+"</Periodo>";
                msgResponse+="</Detalle>";
            
            if(formResult.getResultCode()==0){
                //TODO: construir mensaje correcto..
                msgResponse+="<Resultado>";
                    msgResponse+="<Aprobacion>OK</Aprobacion>";
                msgResponse+="</Resultado>";
            
            }else if(formResult.getResultCode()==1 && formResult.getMensajesArray().length>0){   
                //TODO: respuesta
                msgResponse+="<Resultado>";
                    msgResponse+="<Aprobacion>NOK</Aprobacion>";
                msgResponse+="</Resultado>";
                
                //TODO: recorrer errores
                msgResponse+="<Errores>";
                for(int i=0;i<formResult.getMensajesArray().length;i++){
                    AdfResult result=(AdfResult)formResult.getMensajeI(i);
                    msgResponse+="<Error>";
                        msgResponse+="<ErrorNro>"+result.getNumber()+"</ErrorNro>";
                        msgResponse+="<Formulario>"+result.getFormIndex()+"</Formulario>";
                        msgResponse+="<Codigo>"+result.getObjectName()+"</Codigo>";
                        msgResponse+="<Contenido>"+result.getObjectValue()+"</Contenido>";
                        msgResponse+="<Glosa>"+result.getDescription()+"</Glosa>";
                    msgResponse+="</Error>";
                }    
                msgResponse+="</Errores>";
                
            }else{
                msgResponse+="<Resultado>";
                    msgResponse+="<Aprobacion>NOK</Aprobacion>";
                msgResponse+="</Resultado>";
            }  
           //TODO: tag final
           msgResponse+="</Message>"; 
        }    
        return msgResponse;
    }
    
        
   /* public static void escribeLog(String caracter,Exception exeption) //Exception ex
    {
        java.io.FileOutputStream myFile = null;
        java.io.PrintStream printLog= null;
        java.util.Date fecha = new java.util.Date();
        SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        try
        {   
            myFile = new FileOutputStream("/tgr/aplicaciones/internet/AduanaBean.log",true);
            try
            {
                printLog = new PrintStream(myFile);
                try
                {
                    printLog.println(formato.format(fecha) + " <ERROR> "+ caracter);
                    exeption.printStackTrace(printLog);
                }
                finally
                {
                    printLog.close();
                }
            }
            finally
            {
                myFile.close();
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }*/
    
    
    public static boolean esPesosItem8822(FormRenta renta) throws RentaMException{        
        String valor=null;
        boolean resp=false;
        valor=extraeContenidoByCodigo(renta,"8822");
        if(valor!=null && valor.length()>0){
            if(valor.trim().equals("CLP")){
                resp=true;
            }else{
                resp=false;
            }                        
        }else if(valor!=null && valor.length()==0){
            resp=false;
        }else    
            throw new RentaMException("","0000","null","no existe o no es un valor valido item 8822");
                
        return resp;
    }   
    
    public static boolean esDeclaracionPesos(FormRenta renta) throws RentaMException{        
        String valor=null;
        boolean resp=false;
        valor=extraeContenidoByCodigo(renta,"8811");
        if(valor!=null && valor.length()>0){
            if(valor.trim().equals("CLP")){
                resp=true;
            }else{
                resp=false;
            }                        
        }else{
            //throw new RentaMException("","0000","null","no existe o no es un valor valido item 8811");
              resp=false;
        }        
        return resp;
    }    
    
    public static String getValorAprobacionRentaPesos(String msg){
        int indInicio=msg.indexOf("<Aprobacion>");	
		int indFin=msg.indexOf("</Aprobacion>");
		//System.out.println("indice inicio: "+indInicio+"\n");
		//System.out.println("indice fin: "+indFin+"\n");
		String valor=msg.substring(indInicio+12, indFin);
		return valor.trim();
    }     
    
    //valida que tosdos los negativos tengan el item 9901
    public static Collection regla001Retro(FormRenta []arrayForm){
        
        boolean todos9901=true;
        Collection erroresADF=new ArrayList();
        Collection idsError=new ArrayList();
        for(int i=0;i<arrayForm.length;i++){
            FormRenta formI=arrayForm[i];
            if(formI.getSigno()!=null && formI.getSigno().trim().length()>0 && formI.getSigno().trim().equals("-")){
                String valorFolio9901=null;
                valorFolio9901=extraeContenidoByCodigo(formI,"9901");       
                if(valorFolio9901==null || valorFolio9901.trim().length()==0){    
                     AdfResult adf=new AdfResult();
                    adf.setDescription("no existe item[9901]");    
                    adf.setFormIndex(formI.getNroForm());
                    adf.setModule("");
                    adf.setNumber("1");
                    adf.setObjectDesc("");
                    adf.setObjectName("9901");
                    adf.setObjectValue("no existe");
                    adf.setSeverity("3");
                    erroresADF.add(adf);
                                   
                }        
            }                                
        }                                
        return erroresADF;
    }        
    
    public static Collection regla002Retro(FormRenta []arrayForm){
        //valida que los formularios negativos tengan un positivo
        Collection erroresADF=new ArrayList();
        Collection errores=new ArrayList();
        for(int i=0;i<arrayForm.length;i++){
            FormRenta formI=arrayForm[i];
            if(formI.getSigno()!=null && formI.getSigno().trim().length()>0 && formI.getSigno().trim().equals("-")){
                String valorFolio9901=null;
                valorFolio9901=extraeContenidoByCodigo(formI,"9901");       
                if(valorFolio9901!=null && valorFolio9901.trim().length()>0){
                    //ahora pregunto por formluario que exiasta con este folio                    
                    boolean resp=existeFormPositivoConFolio(arrayForm,valorFolio9901);
                    if(!resp){
                        AdfResult adf=new AdfResult();
                        adf.setDescription("no existe form (+)");    
                        adf.setFormIndex(formI.getNroForm());
                        adf.setModule("");
                        adf.setNumber("1");
                        adf.setObjectDesc("");
                        adf.setObjectName("FolioDecl");
                        adf.setObjectValue(formI.getFolioDecl());
                        adf.setSeverity("3");
                        erroresADF.add(adf);
                    }                                                                    
                }    
            }                
        }                            
        return erroresADF;    
    }  
    //esta regla valida qye los positivos si tienen 9907 vanga su formulario correspondiente 
    public static Collection regla003Retro(FormRenta []arrayForm){
        int countPosit=0,countGood=0;
        Collection ides=new ArrayList();
        Collection erroresADF=new ArrayList();
        for(int i=0;i<arrayForm.length;i++){
            FormRenta formI=arrayForm[i];
            if(formI.getSigno()!=null && formI.getSigno().trim().length()>0 && formI.getSigno().trim().equals("+")){
                countPosit++;
                String valorFolio9901=null;
                Collection cont9907=extraeCollContenidosByCodigo(formI,"9907");                                
                if(cont9907!=null && cont9907.size()>0){
                    Iterator ite=null;
                    for(ite=cont9907.iterator();ite.hasNext();){
                        String cogigo=(String)ite.next();
                        boolean resp=estaNormalizadoEsteFormularioPositivo(cogigo,arrayForm);    
                        if(resp){
                            countGood++;
                        }else{
                            AdfResult adf=new AdfResult();
                            adf.setDescription("no existe form(G) para form(+)");    
                            adf.setFormIndex(formI.getNroForm());
                            adf.setModule("");
                            adf.setNumber("1");
                            adf.setObjectDesc("");
                            adf.setObjectName("FolioDecl");
                            adf.setObjectValue(formI.getFolioDecl());
                            adf.setSeverity("3");
                            erroresADF.add(adf);   
                        }      
                    }                          
                }                                         
            }                    
        }
        
        return erroresADF;
                            
    } 
    //valida que los item 315 de todos los foemularios no sean mayores a la fecha de hoy
    public static Collection regla004Retro(FormRenta []arrayForm){
        Collection erroresADF=new ArrayList();
        
        for(int i=0;i<arrayForm.length;i++){            
            FormRenta formI=arrayForm[i];
            if(formI.getSigno()!=null && formI.getSigno().trim().length()>0 && !formI.getSigno().trim().equals("G")){
                String valor315=null;
                valor315=extraeContenidoByCodigo(formI,"315");              
                if(valor315!=null && valor315.trim().length()>0){
                    SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
                    Date hoy=new Date();
                    Date fechaC=null;
                    try{
                        fechaC=sdf.parse(valor315);                                
                    }catch(Exception e){
                        //TIERNE FORMATYO INCORRECTO...                    
                    }        
                    if(fechaC.getTime()>hoy.getTime()){
                        AdfResult adf=new AdfResult();
                        adf.setDescription("item[315] no es menor a la fecha actual");    
                        adf.setFormIndex(formI.getNroForm());
                        adf.setModule("");
                        adf.setNumber("1");
                        adf.setObjectDesc("");
                        adf.setObjectName("315");
                        adf.setObjectValue(valor315);
                        adf.setSeverity("3");
                        erroresADF.add(adf);  
                    }                        
                }else{
                    AdfResult adf=new AdfResult();
                    adf.setDescription("no existe item[315]");    
                    adf.setFormIndex(formI.getNroForm());
                    adf.setModule("");
                    adf.setNumber("1");
                    adf.setObjectDesc("");
                    adf.setObjectName("315");
                    adf.setObjectValue("");
                    adf.setSeverity("3");
                    erroresADF.add(adf);                      
                }      
            }                    
            
        }            
        return erroresADF;
    }     
    
    //valida que exista el codigo 9905 en todos los positivos
    public static Collection regla005Retro(FormRenta []arrayForm){
        Collection errores=new ArrayList();
        for(int i=0;i<arrayForm.length;i++){
            FormRenta formI=arrayForm[i];
            if(formI.getSigno()!=null && formI.getSigno().trim().length()>0 && formI.getSigno().trim().equals("+")){
                String valor9905=null;
                valor9905=extraeContenidoByCodigo(formI,"9905"); 
                if(valor9905==null || valor9905.trim().length()==0){
                    AdfResult adf=new AdfResult();
                    adf.setDescription("no existe item[9905]");    
                    adf.setFormIndex(formI.getNroForm());
                    adf.setModule("");
                    adf.setNumber("1");
                    adf.setObjectDesc("");
                    adf.setObjectName("9905");
                    adf.setObjectValue("");
                    adf.setSeverity("3");
                    errores.add(adf); 
                }          
            }                
        } 
        return errores;  
    } 
    
    //valida que exista el codigo 9920 en todos los positivos
    public static Collection regla006Retro(FormRenta []arrayForm){
        Collection errores=new ArrayList();
        for(int i=0;i<arrayForm.length;i++){
            FormRenta formI=arrayForm[i];
            if(formI.getSigno()!=null && formI.getSigno().trim().length()>0 && formI.getSigno().trim().equals("+")){
                String valor9905=null;
                valor9905=extraeContenidoByCodigo(formI,"9920"); 
                if(valor9905==null || valor9905.trim().length()==0 || !valor9905.trim().equals("4")){
                    AdfResult adf=new AdfResult();
                    adf.setDescription("no existe item[9920]");    
                    adf.setFormIndex(formI.getNroForm());
                    adf.setModule("");
                    adf.setNumber("1");
                    adf.setObjectDesc("");
                    adf.setObjectName("9920");
                    adf.setObjectValue("");
                    adf.setSeverity("3");
                    errores.add(adf); 
                }          
            }                
        } 
        return errores;  
    }
    
    //regla que valida que si existe un GIRO valide que su item9917 corresoponda a un positivo
    public static Collection regla007Retro(FormRenta []arrayForm){
        Collection errores=new ArrayList();
        int countFound=0;
        for(int i=0;i<arrayForm.length;i++){
            FormRenta formI=arrayForm[i];
            if(formI.getSigno()!=null && formI.getSigno().trim().length()>0 && formI.getSigno().trim().equals("G")){
                String folioGiro=null;                
                folioGiro=formI.getFolioDecl();                        
                if(folioGiro!=null && folioGiro.trim().length()>0){
                     boolean resp=existeFormPositivoCon9907igualFolioG(arrayForm,folioGiro);
                     if(!resp){                                                                 
                        AdfResult adf=new AdfResult();
                        adf.setDescription("no existe (+)9907 asoc a Giro");    
                        adf.setFormIndex(formI.getNroForm());
                        adf.setModule("");
                        adf.setNumber("1");
                        adf.setObjectDesc("");
                        adf.setObjectName("9907");
                        adf.setObjectValue("");
                        adf.setSeverity("3");
                        errores.add(adf); 
                    }                              
                }else{
                    //declarar mensaje salida de error
                    AdfResult adf=new AdfResult();
                    adf.setDescription("no existe folio en Giro");    
                    adf.setFormIndex(formI.getNroForm());
                    adf.setModule("");
                    adf.setNumber("1");
                    adf.setObjectDesc("");
                    adf.setObjectName("9907");
                    adf.setObjectValue("");
                    adf.setSeverity("3");
                    errores.add(adf); 
                }                    
            }
        }     
        return errores;  
    }      
     
    //regla que valida que si existe un positivo refencia (9902) a su negativa
    public static Collection regla008Retro(FormRenta []arrayForm){
        Collection errores=new ArrayList();
        int countFound=0;
        for(int i=0;i<arrayForm.length;i++){
            FormRenta formI=arrayForm[i];
            if(formI.getSigno()!=null && formI.getSigno().trim().length()>0 && formI.getSigno().trim().equals("+")){
                String valor9902=null;
                valor9902=extraeContenidoByCodigo(formI,"9902"); 
                if(valor9902!=null && valor9902.trim().length()>0){
                     boolean resp=existeFormNegativoConFolio(arrayForm,valor9902);
                     if(!resp){                                                                 
                        AdfResult adf=new AdfResult();
                        adf.setDescription("no existe(-)asoc a form(+)");    
                        adf.setFormIndex(formI.getNroForm());
                        adf.setModule("");
                        adf.setNumber("1");
                        adf.setObjectDesc("");
                        adf.setObjectName("9902");
                        adf.setObjectValue(extraeContenidoByCodigo(formI,"9902"));
                        adf.setSeverity("3");
                        errores.add(adf); 
                    }                              
                }                
            }
        }     
        return errores;  
    } 
    
    //valida que exista en el giro el item 9917    
    public static Collection regla012Retro(FormRenta []arrayForm){
        Collection errores=new ArrayList();
        int countFound=0;
        for(int i=0;i<arrayForm.length;i++){
            FormRenta formI=arrayForm[i];
            if(formI.getSigno()!=null && formI.getSigno().trim().length()>0 && formI.getSigno().trim().equals("G")){
                String valor9917=null;                
                valor9917=extraeContenidoByCodigo(formI,"9917"); 
                if(valor9917==null || valor9917.trim()==null || valor9917.trim().length()==0){
                    AdfResult adf=new AdfResult();
                    adf.setDescription("no existe item[9917]");    
                    adf.setFormIndex(formI.getNroForm());
                    adf.setModule("");
                    adf.setNumber("1");
                    adf.setObjectDesc("");
                    adf.setObjectName("9917");
                    adf.setObjectValue("");
                    adf.setSeverity("3");
                    errores.add(adf); 
                }                    
            }    
        }    
        return errores;
    }    
    
    //regla que valida que si existe el item 15
    public static Collection regla009Retro(FormRenta []arrayForm){
        Collection errores=new ArrayList();
        int countFound=0;
        for(int i=0;i<arrayForm.length;i++){
            FormRenta formI=arrayForm[i];
            String valor15=null;
            if(formI.getFormulario()!=null && new BigDecimal(formI.getFormulario()).intValue()!=22){
                valor15=extraeContenidoByCodigo(formI,"15"); 
                if(valor15==null || valor15.trim().length()==0){                                    
                    AdfResult adf=new AdfResult();
                    adf.setDescription("no existe item[15]");    
                    adf.setFormIndex(formI.getNroForm());
                    adf.setModule("");
                    adf.setNumber("1");
                    adf.setObjectDesc("");
                    adf.setObjectName("15");
                    adf.setObjectValue("");
                    adf.setSeverity("3");
                    errores.add(adf);  
                }                               
            }                
        }     
        return errores;  
    }       
    
    //regla que valida que si existe el item 9927
    public static Collection regla0010Retro(FormRenta []arrayForm){
        Collection errores=new ArrayList();
        int countFound=0;
        for(int i=0;i<arrayForm.length;i++){
            FormRenta formI=arrayForm[i];
            String valor15=null;
            valor15=extraeContenidoByCodigo(formI,"9927"); 
            if(valor15==null || valor15.trim().length()==0){                
                AdfResult adf=new AdfResult();
                adf.setDescription("no existe item[9927]");    
                adf.setFormIndex(formI.getNroForm());
                adf.setModule("");
                adf.setNumber("1");
                adf.setObjectDesc("");
                adf.setObjectName("9927");
                adf.setObjectValue("");
                adf.setSeverity("3");
                errores.add(adf);  
            }               
        }     
        return errores;  
    }    
    
    //regla que valida QUE EXISTAN LOS 88XX
    public static Collection regla0011Retro(FormRenta[] arrayForm){
        Collection errores=new ArrayList();
        int countFound=0;
        for(int i=0;i<arrayForm.length;i++){
            FormRenta formI=arrayForm[i];
            if(formI.getFormulario().trim()!=null 
            && formI.getFormulario().trim().length()>0 
            && new BigDecimal(formI.getFormulario().trim()).intValue()!=221
            && !formI.getSigno().trim().equals("-") ){
                boolean resp=regla154(formI);
                if(!resp){                
                    AdfResult adf=new AdfResult();
                    adf.setDescription("no existen item 8811");    
                    adf.setFormIndex(formI.getNroForm());
                    adf.setModule("");
                    adf.setNumber("1");
                    adf.setObjectDesc("");
                    adf.setObjectName("8811");
                    adf.setObjectValue("");
                    adf.setSeverity("3");                
                    errores.add(adf); 
                     
                    AdfResult adf1=new AdfResult();
                    adf1.setDescription("no existen item 8822");    
                    adf1.setFormIndex(formI.getNroForm());
                    adf1.setModule("");
                    adf1.setNumber("1");
                    adf1.setObjectDesc("");
                    adf1.setObjectName("8822");
                    adf1.setObjectValue("");
                    adf1.setSeverity("3");                
                    errores.add(adf1);  
                    
                    AdfResult adf2=new AdfResult();
                    adf2.setDescription("no existen item 8833");    
                    adf2.setFormIndex(formI.getNroForm());
                    adf2.setModule("");
                    adf2.setNumber("1");
                    adf2.setObjectDesc("");
                    adf2.setObjectName("8833");
                    adf2.setObjectValue("");
                    adf2.setSeverity("3");                
                    errores.add(adf2);  
                    
                    AdfResult adf3=new AdfResult();
                    adf3.setDescription("no existen item 8844");    
                    adf3.setFormIndex(formI.getNroForm());
                    adf.setModule("");
                    adf3.setNumber("1");
                    adf3.setObjectDesc("");
                    adf3.setObjectName("8844");
                    adf3.setObjectValue("");
                    adf3.setSeverity("3");                
                    errores.add(adf3);  
                    
                    AdfResult adf4=new AdfResult();
                    adf4.setDescription("no existen item 8866");    
                    adf4.setFormIndex(formI.getNroForm());
                    adf4.setModule("");
                    adf4.setNumber("1");
                    adf4.setObjectDesc("");
                    adf4.setObjectName("8866");
                    adf4.setObjectValue("");
                    adf4.setSeverity("3");                
                    errores.add(adf4);  
                    
                    AdfResult adf5=new AdfResult();
                    adf5.setDescription("no existen item 8877");    
                    adf5.setFormIndex(formI.getNroForm());
                    adf5.setModule("");
                    adf5.setNumber("1");
                    adf5.setObjectDesc("");
                    adf5.setObjectName("8877");
                    adf5.setObjectValue("");
                    adf5.setSeverity("3");                
                    errores.add(adf5);                  
                }     
            }                                      
        }     
        return errores;  
    }    
    
    public static boolean estaNormalizadoEsteFormularioPositivo(String folio,FormRenta []array){        
        boolean swFound=false;
        for(int i=0;i<array.length;i++){
            FormRenta formI=array[i];
            if(formI.getSigno()!=null && formI.getSigno().trim().length()>0 && formI.getSigno().trim().equals("G")){
                if(formI.getFolioDecl()!=null && formI.getFolioDecl().trim().length()>0 && new BigDecimal(formI.getFolioDecl().trim()).intValue()==new BigDecimal(folio).intValue()){
                    swFound=true;
                    break;
                }                        
            }            
        }    
        return swFound;            
    }        
    
    public static boolean existeFormPositivoCon9907igualFolioG(FormRenta []array,String folio){
        boolean swFound=false;        
        for(int i=0;i<array.length;i++){
            FormRenta formI=array[i]; 
            if(formI.getSigno()!=null && formI.getSigno().trim().length()>0 && formI.getSigno().trim().equals("+")){
                String valor9907=null;
                valor9907=Utils.extraeContenidoByCodigo(formI,"9907");            
                if(valor9907!=null && valor9907.trim()!=null && valor9907.trim().length()>0 && folio.trim()!=null && folio.trim().length()>0 && new BigDecimal(folio.trim()).longValue()==new BigDecimal(valor9907.trim()).longValue()){
                    swFound=true;
                    break;
                }    
            }
        } 
        return swFound;  
    }    
    
    
    public static boolean existeFormPositivoConFolio(FormRenta []array,String folio){
        boolean swFound=false;        
        for(int i=0;i<array.length;i++){
            FormRenta formI=array[i]; 
            if(formI.getSigno()!=null && formI.getSigno().trim().length()>0 && formI.getSigno().trim().equals("+")){
                if(formI.getFolioDecl().trim()!=null && formI.getFolioDecl().trim().length()>0 && new BigDecimal(formI.getFolioDecl().trim()).intValue()==new BigDecimal(folio).intValue()){
                    swFound=true;
                    break; 
                }
            }
        }    
        return swFound;        
    }
    
    public static boolean existeFormNegativoConFolio(FormRenta []array,String folio){
        boolean swFound=false;        
        for(int i=0;i<array.length;i++){
            FormRenta formI=array[i]; 
            if(formI.getSigno()!=null && formI.getSigno().trim().length()>0 && formI.getSigno().trim().equals("-")){
                if(formI.getFolioDecl().trim()!=null && formI.getFolioDecl().trim().length()>0 && new BigDecimal(formI.getFolioDecl().trim()).intValue()==new BigDecimal(folio).intValue()){
                    swFound=true;
                    break; 
                }
            }
        }    
        return swFound;        
    }
    
  /*  public static void escribeLog(String caracter) //Exception ex
    {
        java.io.FileOutputStream myFile = null;
        java.io.PrintStream printLog= null;
        java.util.Date fecha = new java.util.Date();
        SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        try
        {   
            myFile = new FileOutputStream("/tgr/aplicaciones/internet/AduanaBean.log",true);
            try
            {
                printLog = new PrintStream(myFile);
                try
                {
                    printLog.println(formato.format(fecha) + " <ERROR> "+ caracter);
                    
                }
                finally
                {
                    printLog.close();
                }
            }
            finally
            {
                myFile.close();
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }*/
    
   
} 