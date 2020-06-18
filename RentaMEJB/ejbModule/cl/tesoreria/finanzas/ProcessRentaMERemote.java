package cl.tesoreria.finanzas; 

import javax.ejb.Remote;

import cl.tesoreria.FormRenta;
import cl.tesoreria.FormularioResult;

@Remote
public interface ProcessRentaMERemote {

    public String receiverXML(String inputXML);
    public FormularioResult processDataRenta(FormRenta form)throws RentaMException,RentaMEGenericException;
    public FormularioResult processDataRenta(FormRenta[] form)throws RentaMException,RentaMEGenericException;
}
