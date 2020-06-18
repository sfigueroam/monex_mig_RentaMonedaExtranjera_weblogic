package cl.tesoreria; 

import java.io.Serializable;

public class FormRenta implements Serializable 
{ 	
	private static final long serialVersionUID = -8051475920908594584L;
	protected String montoPago;
    protected String signoMontoPag;
    protected String montoImp;
    protected String signoMontoImp;
    protected String montoRet;
    protected String glosaFisc;
    protected String marcaFisc;
    protected String dvContr;
    protected String rutContr;
    protected String signo;
    protected String folioDecl;
    protected String periodo;
    protected String formulario;
    protected String folioF01;
    protected String dvIra;
    protected String rutIra;
    protected ItemRenta[]  itemsRenta; // Lista de Items
    protected String idMessage;
    protected String           msgInput;    
    protected MessageID messageId;
    protected String nroForm;
    
    protected ItemCUT[]         itemsCUT;   // Lista de Mensajes
    
    
    public void setItemsCUT(ItemCUT[] arg){
        itemsCUT=arg;
    }    
    
    public ItemCUT[] getItemsCUT(){
        return itemsCUT;
    }



    public void setMessageId(MessageID message){
       messageId=message; 
    }    
    
    public MessageID getMessageId(){
        return messageId;
    } 

    public void setMsgInput(String msg){
       msgInput=msg; 
    }    
    
    public String getMsgInput(){
        return msgInput;
    } 
    
    public void setIdMessage(String id){
        idMessage=id;
    } 
    
     public String getIdMessage(){
        return idMessage;
    }    

        public void setRutIra(String rutIra)
        {
            this.rutIra = rutIra;
        }

        public String getRutIra()
        {
            return this.rutIra;
        }

        public void setDvIra(String dvIra)
        {
            this.dvIra = dvIra;
        }

        public String getDvIra()
        {
            return this.dvIra;
        }

        public void setFolioF01(String folioF01)
        {
            this.folioF01 = folioF01;
        }

        public String getFolioF01()
        {
            return this.folioF01;
        }

        public void setFormulario(String formulario)
        {
            this.formulario = formulario;
        }

        public String getFormulario()
        {
            return this.formulario;
        }

        public void setPeriodo(String periodo)
        {
            this.periodo = periodo;
        }

        public String getPeriodo()
        {
            return this.periodo;
        }

        public void setFolioDecl(String folioDecl)
        {
            this.folioDecl = folioDecl;
        }

        public String getFolioDecl()
        {
            return this.folioDecl;
        }

        public void setSigno(String signo)
        {
            this.signo = signo;
        }

        public String getSigno()
        {
            return this.signo;
        }

        public void setRutContr(String rutContr)
        {
            this.rutContr = rutContr;
        }

        public String getRutContr()
        {
            return this.rutContr;
        }

        public void setDvContr(String dvContr)
        {
            this.dvContr = dvContr;
        }

        public String getDvContr()
        {
            return this.dvContr;
        }

        public void setMarcaFisc(String marcaFisc)
        {
            this.marcaFisc = marcaFisc;
        }

        public String getMarcaFisc()
        {
            return this.marcaFisc;
        }

        public void setGlosaFisc(String glosaFisc)
        {
            this.glosaFisc = glosaFisc;
        }

        public String getGlosaFisc()
        {
            return this.glosaFisc;
        }

        public void setMontoRet(String montoRet)
        {
            this.montoRet = montoRet;
        }

        public String getMontoRet()
        {
            return this.montoRet;
        }

        public void setSignoMontoImp(String signoMontoImp)
        {
            this.signoMontoImp = signoMontoImp;
        }

        public String getSignoMontoImp()
        {
            return this.signoMontoImp;
        }

        public void setMontoImp(String montoImp)
        {
            this.montoImp = montoImp;
        }

        public String getMontoImp()
        {
            return this.montoImp;
        }

        public void setSignoMontoPag(String signoMontoPag)
        {
            this.signoMontoPag = signoMontoPag;
        }

        public String getSignoMontoPag()
        {
            return this.signoMontoPag;
        }

        public void setMontoPago(String montoPago)
        {
            this.montoPago = montoPago;
        }

        public String getMontoPago()
        {
            return this.montoPago;
        }
        
        
        public void setNroForm(String nroForm)
        {
            this.nroForm = nroForm;
        }

        public String getNroForm()
        {
            return this.nroForm;
        }

    
    public ItemRenta[] getItemsRenta() {
        return itemsRenta;
    }
    public ItemRenta getItemRenta(int i) {
        return itemsRenta[i];
    }
    public void setItemsRenta(ItemRenta[] itemsRenta) {
    this.itemsRenta = itemsRenta;
    }
    
    public String viewForm()
    { 
        String sOut;   
        sOut= "montoPago : " + this.montoPago+ "\n";
        sOut= sOut + "signoMontoPag :"+this.signoMontoPag+ "\n";
        sOut= sOut + "montoImp      :"+this.montoImp+ "\n";
        sOut= sOut + "signoMontoImp :"+this.signoMontoImp+ "\n";
        sOut= sOut + "montoRet      :"+this.montoRet + "\n";
        sOut= sOut + "glosaFisc     :"+this.glosaFisc+ "\n";
        sOut= sOut + "marcaFisc     :"+this.marcaFisc+ "\n";
        sOut= sOut + "dvContr       :"+this.dvContr+ "\n";
        sOut= sOut + "rutContr      :"+this.rutContr+ "\n";
        sOut= sOut + "signo         :"+this.signo+ "\n";
        sOut= sOut + "folioDecl     :"+this.folioDecl+ "\n";
        sOut= sOut + "periodo       :"+this.periodo+ "\n";
        sOut= sOut + "formulario    :"+this.formulario+ "\n";
        sOut= sOut + "folioF01      :"+this.folioF01+ "\n";
        sOut= sOut + "dvIra         :"+this.dvIra+ "\n";
        sOut= sOut + "rutIra        :"+this.rutIra+ "\n";

        int nroItem = this.getItemsRenta().length;
            int ini =0;  
            for (ini=0; ini<nroItem; ini++)
            {
                sOut = sOut + "ITEM ["+ini+"]"+ "\n";
                sOut = sOut + "   codigo    : " + this.getItemRenta(ini).getCodigo()+ "\n";
                sOut = sOut + "   signo     : " + this.getItemRenta(ini).getSigno()+ "\n";
                sOut = sOut + "   contenido : " + this.getItemRenta(ini).getContenido()+ "\n";
            }

        return sOut;
        }
    

} 
