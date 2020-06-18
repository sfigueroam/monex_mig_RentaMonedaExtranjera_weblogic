package cl.tesoreria.finanzas; 

import javax.ejb.Local;

import cl.tesoreria.FormRenta;
import cl.tesoreria.FormularioResult;

@Local
public interface ProcessRentaMELocal {

    public String receiverXML(String inputXML);
    public FormularioResult processDataRenta(FormRenta form)throws RentaMException,RentaMEGenericException;
    public FormularioResult processDataRenta(FormRenta[] form)throws RentaMException,RentaMEGenericException;
}
