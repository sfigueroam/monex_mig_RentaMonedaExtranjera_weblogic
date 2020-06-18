package cl.tesoreria; 

import java.io.Serializable;

public class MessageID implements Serializable 
{ 
	private static final long serialVersionUID = -2626880660473066019L;


	public MessageID() {
		// TODO Auto-generated constructor stub
	}
    
    private String code;
	private String msgDesc;
	private String version;
	private String fechaVersion;
	private String fromAddress;
	private String toAddress;
	private String refAddress;
	private String dateTime;
	private String validado;
	private String number;
	private String periodoContable;
    
    
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsgDesc() {
		return msgDesc;
	}
	public void setMsgDesc(String msgDesc) {
		this.msgDesc = msgDesc;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getFechaVersion() {
		return fechaVersion;
	}
	public void setFechaVersion(String fechaVersion) {
		this.fechaVersion = fechaVersion;
	}
	public String getFromAddress() {
		return fromAddress;
	}
	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}
	public String getToAddress() {
		return toAddress;
	}
	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}
	public String getRefAddress() {
		return refAddress;
	}
	public void setRefAddress(String refAddress) {
		this.refAddress = refAddress;
	}
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public String getValidado() {
		return validado;
	}
	public void setValidado(String validado) {
		this.validado = validado;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getPeriodoContable() {
		return periodoContable;
	}
	public void setPeriodoContable(String periodoContable) {
		this.periodoContable = periodoContable;
	}
    
    
} 
