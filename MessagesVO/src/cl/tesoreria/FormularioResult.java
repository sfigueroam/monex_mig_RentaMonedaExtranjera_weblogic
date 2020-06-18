package cl.tesoreria;

public class FormularioResult implements java.io.Serializable
{    
	private static final long serialVersionUID = -6400700835424261280L;
	protected int              resultCode;     // Código de Resultado (0=OK)
    protected String           resultMessage;  // Mensaje Resultado
    protected String           contestadorID;  // Identificador del Contestador
  
    protected AdfResult[]         mensajes;   // Lista de Mensajes
    protected String           msgInput;
    protected String           idMensaje;
    protected String           periodo;
    protected ItemCUT[]         itemsCUT;   // Lista de Mensajes
    protected int indexFormError;
    protected boolean existeAllPrimitivas;
    protected boolean noEstanRegistTrxPositivas;
    
    public void setIndexFormError(int index){
        indexFormError=index;
    }    
    public int getIndexFormError(){
        return indexFormError;
    }
    
    public void setItemsCUT(ItemCUT[] arg){
        itemsCUT=arg;
    }    
    
    public ItemCUT[] getItemsCUT(){
        return itemsCUT;
    }
    
    public void setPeriodo(String per){
       periodo=per; 
    }    
    
    public String getPeriodo(){
        return periodo;
    }
    
    public void setIdMensaje(String id){
       idMensaje=id; 
    }    
    
    public String getIdMensaje(){
        return idMensaje;
    }    


    public void setMsgInput(String msg){
       msgInput=msg; 
    }    
    
    public String getMsgInput(){
        return msgInput;
    }    
    
    
    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public void setContestadorID(String contestadorID) {
        this.contestadorID = contestadorID;
    }

    public void setRecaMensajes(AdfResult[] mensajes) {
        this.mensajes = mensajes;
    }

    public void setRecaMensajes(int i ,AdfResult mensajes) {
        this.mensajes[i] = mensajes;
    }
    
    

    public int getResultCode() {
        return resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public String getContestadorID() {
        return contestadorID;
    }
    
    public int getMensajes(){
        return mensajes.length;
    } 
    
    public AdfResult[] getMensajesArray(){
        return mensajes;
    } 

    /*public Mensaje[] getRecaMensajes() {
        return mensajes;
    }*/
    public AdfResult getMensajeI(int i) {
        return (AdfResult)mensajes[i];
    }
    
    
    public boolean isExisteAllPrimitivas() {
		return existeAllPrimitivas;
	}
	public void setExisteAllPrimitivas(boolean existeAllPrimitivas) {
		this.existeAllPrimitivas = existeAllPrimitivas;
	}
    
    
    public boolean isNoEstanRegistTrxPositivas() {
		return noEstanRegistTrxPositivas;
	}
	public void setNoEstanRegistTrxPositivas(boolean noEstanRegistTrxPositivas) {
		this.noEstanRegistTrxPositivas = noEstanRegistTrxPositivas;
	}

}

