package cl.tesoreria.finanzas.tareas; 

import java.math.BigDecimal;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import cl.tesoreria.AdfResult;
import cl.tesoreria.FormRenta;
import cl.tesoreria.FormularioR;
import cl.tesoreria.ItemCUT;
import cl.tesoreria.ItemRenta;
import cl.tesoreria.finanzas.RentaMException;
import cl.tesoreria.finanzas.Utils;
import cl.tesoreria.renta.me.pkgsiirentame.ItemCut;

public class TareasME 
{ 
    
    private static Logger logger = Logger.getLogger("cl.tesoreria.finpub.RentaMonedaExtranjera.RentaMEJB.TareasME"); 
    
    public TareasME(){
    }
    
    /**
     * DHN(22-02-2016) Form_Version 
     * MAN000000471 - Mejora para el Archivo Contable y almacenamiento en CUT MONEX
     */
    public String getFormVersion(int frmNro){
        String form_ver = "";
        
        if(frmNro==10){
            form_ver = "E";
        }else if(frmNro==22){
            form_ver = "C";
        }else if(frmNro==29){
            form_ver = "F";
        }else if(frmNro==50){
            form_ver = "B";
        }else if(frmNro==54){ // Incorpora F-54A (**DHN: 01-08-2012**)
            form_ver = "A";
        }else if(frmNro==55){
            form_ver = "B";
        }else if(frmNro==72){
            form_ver = "E";
        }else if(frmNro==74){
            form_ver = "C";
        }else if(frmNro==76){
            form_ver = "B";
        }else if(frmNro==79){
            form_ver = "B";
        }else if((frmNro==221) || (frmNro==21) || (frmNro==321)){ // DHN (03-03-2016) F-321
            form_ver = "C";
        }else if((frmNro==245) || (frmNro==45) || (frmNro==345)){ // DHN (03-03-2016) F-345
            form_ver = "C";
        }
        return form_ver;
    }
    
    public boolean esIgualCUTCta(FormularioR formBD,FormRenta form){
        if(formBD.getRut().equals(new BigDecimal(form.getRutContr())) && formBD.getDv().trim().equals(form.getDvContr()) && formBD.getFormTipo().equals(new BigDecimal(form.getFormulario())) && formBD.getFolio().equals(new BigDecimal(form.getFolioDecl()))){
            return true;
        }else
            return false;
    }
    
    public void formatDataTOADF(FormRenta form){
        ItemRenta items[] = form.getItemsRenta();
        for(int i=0;i<items.length;i++){
            ItemRenta item = items[i];
            item.setContenido(Utils.formatearDataTO(item.getContenido(),"ADF"));
        }    
    } 
    
    public ItemCut[] transformaAColeccionItem(BigDecimal idMov,ItemCUT arrayItemsCUT[]){        
        ItemCut[] arrayout=new ItemCut[arrayItemsCUT.length];
         for(int i=0;i<arrayItemsCUT.length;i++){
            ItemCUT item=arrayItemsCUT[i];
            //item salida
            ItemCut itemIO=new ItemCut();
            itemIO.setIdMov(idMov);
            itemIO.setCodg(new BigDecimal(item.getCodigo()));
            itemIO.setDatoType(item.getTipoDato());
            itemIO.setStatus(new BigDecimal(0));
            itemIO.setValor(item.getValor());
            arrayout[i]=itemIO;
        }                
        return arrayout;
    }
    
    public BigDecimal buscarValorCambio(FormRenta form){
        ItemRenta arrayItems[]=form.getItemsRenta();
        ItemRenta item8833=null;
        for(int i=0;i<arrayItems.length;i++){
            ItemRenta item=arrayItems[i];
            if(item.getCodigo().equals("8833")){
                item8833=item;
                break;
            }    
        }
        
        BigDecimal valorCambio;
        if(form.getFormulario()!=null && (new BigDecimal(form.getFormulario()).intValue()==74 || new BigDecimal(form.getFormulario()).intValue()==72 || new BigDecimal(form.getFormulario()).intValue()==79)){                   
            valorCambio=new BigDecimal(0);
        }else{
            if(item8833!=null){
                valorCambio=new BigDecimal(item8833.getContenido());    
            }else
                valorCambio=new BigDecimal(0);                  
        }        

        return valorCambio;     
    }
    
    public String buscarGlosaMoneda(FormRenta form){
        ItemRenta arrayItems[]=form.getItemsRenta();
        ItemRenta item8811=null;
        for(int i=0;i<arrayItems.length;i++){
            ItemRenta item=arrayItems[i];
            if(item.getCodigo().equals("8811")){
                item8811=item;
                break;
            }    
        }
        String glosa=null;
        if(form.getFormulario()!=null && (new BigDecimal(form.getFormulario()).intValue()==74 || new BigDecimal(form.getFormulario()).intValue()==76)){                   
            glosa=Utils.extraeContenidoByCodigo(form,"8822");
            if (glosa==null) glosa = "8822";
        }else if(form.getFormulario()!=null && (new BigDecimal(form.getFormulario()).intValue()==72)){  
            glosa=Utils.extraeContenidoByCodigo(form,"204");
            if (glosa==null) glosa = "0204";
        }else if(form.getFormulario()!=null && (new BigDecimal(form.getFormulario()).intValue()==10)){  
            glosa=Utils.extraeContenidoByCodigo(form,"464");
            if (glosa==null) glosa = "0464";
        }else if(form.getFormulario()!=null && (new BigDecimal(form.getFormulario()).intValue()==79)){  
            glosa=Utils.extraeContenidoByCodigo(form,"60");
            if (glosa==null) glosa = "0060";
        }else if(form.getFormulario()!=null && (new BigDecimal(form.getFormulario()).intValue()==55)){  
            glosa=Utils.extraeContenidoByCodigo(form,"50");
            if (glosa==null) glosa = "0050";
        }else{            
            if(item8811!=null){
                glosa=item8811.getContenido();
                if (glosa==null) glosa = "8811";
            }                    
        } 
        if (glosa!=null) glosa=glosa.trim();
        return glosa;     
    }
    
    public BigDecimal buscarMontoMonOrigen(FormRenta form)throws RentaMException{
        ItemRenta arrayItems[]=form.getItemsRenta();
        ItemRenta item8844=null;
        for(int i=0;i<arrayItems.length;i++){
            ItemRenta item=arrayItems[i];
            if(item.getCodigo().equals("8844")){
                item8844=item;
                break;
            }    
        }
        
        BigDecimal montoOrigen=null;
        // Incopora F54 (**DHN: 01-08-2012**)
        if(form.getFormulario()!=null && (new BigDecimal(form.getFormulario()).intValue()==74 || new BigDecimal(form.getFormulario()).intValue()==76 || new BigDecimal(form.getFormulario()).intValue()==72 || new BigDecimal(form.getFormulario()).intValue()==10 || new BigDecimal(form.getFormulario()).intValue()==79 || new BigDecimal(form.getFormulario()).intValue()==55 || new BigDecimal(form.getFormulario()).intValue()==54)){
            String valorMonto=Utils.extraeContenidoByCodigo(form,"91").trim();
            montoOrigen=new BigDecimal(valorMonto);
        }else if(form.getFormulario()!=null && (new BigDecimal(form.getFormulario()).intValue()==21 || new BigDecimal(form.getFormulario()).intValue()==321 || new BigDecimal(form.getFormulario()).intValue()==345)){
            montoOrigen=new BigDecimal(0);
        }else{
            if(item8844!=null && item8844.getContenido()!=null && item8844.getContenido().length()>0){
                montoOrigen=new BigDecimal(item8844.getContenido());    
            }else{
                //montoOrigen=new BigDecimal(0)    
                //setRollbackOnly();
                throw new RentaMException("","8844",item8844.getContenido(),"No tiene un valor numerico en el codigo 8844, el valor que viene es: "+ item8844.getContenido());              
            }                 
        }        
        
        return montoOrigen;     
    }
    
    public ArrayList getErroresAdf(String messagesTGF)
    {   
        String ECU_CS = new Character((char) 1).toString();  //Cell separator
        String ECU_LS = new Character((char) 6).toString();  //Line separator
        ArrayList array = new ArrayList();
        if (messagesTGF.length() > 0)
        {    
            String[] messages = messagesTGF.split(ECU_LS);
            for ( int x=0; x < messages.length; x++)
            {
                if (messages[x].length() > 6)
                {    
                    String[] fields = messages[x].split(ECU_CS);
                    if (fields.length >= 6) 
                    {
                        //logger.info("Mensaje nro:"+ x + "    Valor : " + fields);
                        //0:Module (txt)
                        //1:Severity (nn)
                        //2:Number (nnnn)
                        //3:ObjectName (txt)
                        //4:ObjectValue (txt)
                        //5:Description (txt)
                        //6:ObjectDesc (txt)

                        AdfResult mensaje= new AdfResult();
                        mensaje.setDescription(fields[5].toString());
                        logger.info(Utils.validateCodeError(fields[3]));
                        mensaje.setFormIndex("0");
                        mensaje.setModule(fields[0]);
                        mensaje.setNumber(fields[2]);
                        if(fields.length>6 && fields[6]!=null && fields[6].length()>0){
                            mensaje.setObjectDesc(fields[6]);
                        }else{
                            mensaje.setObjectDesc("");
                        }    
                        String objName=fields[3];
                        int indIni=objName.indexOf("[");
                        int indFin=objName.indexOf("]");
                        if(indIni==-1 || indFin==-1){
                            fields[3]="0000";
                            mensaje.setObjectName(fields[3].trim());
                        }else{
                            mensaje.setObjectName(fields[3].substring(indIni+1,indFin));
                        }        
                        mensaje.setObjectValue(fields[4]);
                        mensaje.setSeverity(fields[1]);
                        array.add(mensaje);                           
                    }
                } 
            }
        }
        return array;
    }
    
    public boolean esUnMovimientoRTRMAS(FormRenta form){
        if(form.getMessageId().getCode().equalsIgnoreCase("RTRMASME")){
            return true;
        }else{
            
            return false;
        }       
    }   
    
    
    public boolean esUnMovimientoRTRMODME(FormRenta form){
        if(form.getMessageId().getCode().equalsIgnoreCase("RTRMODME")){
            return true;
        }else{            
            return false;
        }       
    }  
    
     public boolean esUnMovimientoRECAINME(FormRenta form){
        if(form.getMessageId().getCode().equalsIgnoreCase("RECAINME")){
            return true;
        }else{
            
            return false;
        }       
    }   
    
    public boolean esUnMovimientoEGRESO(FormRenta form){
        if(form.getMessageId().getCode().equalsIgnoreCase("EGRESO")){
            return true;
        }else{
            
            return false;
        }       
    }  
    
    public boolean esUnMovimientoRTRINCME(FormRenta form){
        if(form.getMessageId().getCode().equalsIgnoreCase("RTRINCME")){
            return true;
        }else{
            
            return false;
        }       
    } 
    
    public boolean esUnMovimientoGIRO21ME(FormRenta form){
        if(form.getMessageId().getCode().equalsIgnoreCase("GIRO21ME")){
            return true;
        }else{
            
            return false;
        }       
    }   
    
    
    public boolean esUnMovimientoRECMAS(FormRenta form){
        if(form.getMessageId().getCode().equalsIgnoreCase("RECMASME")){
            return true;
        }else{
            
            return false;
        }       
    } 
    
    public boolean esUnMovimientoGIRO45ME(FormRenta form){
        if(form.getMessageId().getCode().equalsIgnoreCase("GIRO45ME")){
            return true;
        }else{
            
            return false;
        }       
    } 
    
    public boolean esUnMovimientoDESC45ME(FormRenta form){
        if(form.getMessageId().getCode().equalsIgnoreCase("DESC45ME")){
            return true;
        }else{
            
            return false;
        }       
    } 
    
    public boolean esUnMovimientoDESC21ME(FormRenta form){
        if(form.getMessageId().getCode().equalsIgnoreCase("DESC21ME")){
            return true;
        }else{
            
            return false;
        }       
    }
    
    public boolean esUnMovimientoRECTIFME(FormRenta form){
        if(form.getMessageId().getCode().equalsIgnoreCase("RECTIF")){
            return true;
        }else{
            
            return false;
        }       
    }
  
} 
