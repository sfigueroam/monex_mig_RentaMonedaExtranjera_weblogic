package webservice;

import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import org.apache.log4j.Logger;

import cl.tesoreria.finanzas.ProcessRentaMELocal;

@WebService(name="WSRentaMasivaME", targetNamespace="http://www.openuri.org/", serviceName="WSRentaMasivaME", portName="WSRentaMasivaMESoap", wsdlLocation="WEB-INF/wsdl/WSRentaMasivaME.wsdl")            
public class WSRentaMasivaME 
{     
    @EJB ProcessRentaMELocal processME;    
    private static Logger logger = Logger.getLogger("webservice.WSRentaMasivaME");
    static final long serialVersionUID = 1L;
    
    @WebMethod(action="receiverXML")
		public @WebResult(name="receiverXMLResult",targetNamespace="http://www.openuri.org/") String receiverXML(@WebParam(name="inputXML")String inputXML)throws Exception
	
    {   
    	logger.info("-------->>>>>>>>Iniciando DEBUG...<-------------------------------------");
        logger.info("[********---- >>>WSME: Mensaje de entrada es: ["+inputXML+"] *******]");
        String myXmlOut = processME.receiverXML(inputXML);
        logger.info("[********---- >>>WSME: Mensaje de Salida: \n"+myXmlOut+"\n ********]");
        
        return myXmlOut;
        
    }   
}