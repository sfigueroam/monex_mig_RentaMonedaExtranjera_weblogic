package cl.tesoreria; 

import java.io.Serializable;
import java.math.BigDecimal;

public class FormularioR implements Serializable{
	
	private static final long serialVersionUID = 5868433176403088754L;
	private boolean indExiste;
	private BigDecimal rut;
	private String dv;
	private BigDecimal formTipo;
	private BigDecimal folio;
    private String idTipoMov;
	private String idMov;
    boolean existeRECMAS;
   	private String valor03;
	private String valor07;
	private String valor087;
	private String valor091;
	private String valor315;
    private BigDecimal montoRet;
    private String idcta;
    private boolean rectificada;
    
	
	
	public FormularioR() {
		
	}
    
    public String getValor03() {
		return valor03;
	}


	public void setValor03(String valor03) {
		this.valor03 = valor03;
	}


	public String getValor07() {
		return valor07;
	}


	public void setValor07(String valor07) {
		this.valor07 = valor07;
	}


	public String getValor087() {
		return valor087;
	}


	public void setValor087(String valor087) {
		this.valor087 = valor087;
	}


	public String getValor091() {
		return valor091;
	}


	public void setValor091(String valor091) {
		this.valor091 = valor091;
	}


	public String getValor315() {
		return valor315;
	}


	public void setValor315(String valor315) {
		this.valor315 = valor315;
	}

    
    
    public boolean isExisteRECMAS() {
		return existeRECMAS;
	}


	public void setExisteRECMAS(boolean existeRECMAS) {
		this.existeRECMAS = existeRECMAS;
	}

	
	
	
	
	public String getIdTipoMov() {
		return idTipoMov;
	}


	public void setIdTipoMov(String idTipoMov) {
		this.idTipoMov = idTipoMov;
	}


	public String getIdMov() {
		return idMov;
	}


	public void setIdMov(String idMov) {
		this.idMov = idMov;
	}

	public boolean isIndExiste() {
		return indExiste;
	}

	public void setIndExiste(boolean indExiste) {
		this.indExiste = indExiste;
	}

	public BigDecimal getRut() {
		return rut;
	}

	public void setRut(BigDecimal rut) {
		this.rut = rut;
	}

	public String getDv() {
		return dv;
	}

	public void setDv(String dv) {
		this.dv = dv;
	}

	public BigDecimal getFormTipo() {
		return formTipo;
	}

	public void setFormTipo(BigDecimal formTipo) {
		this.formTipo = formTipo;
	}

	public BigDecimal getFolio() {
		return folio;
	}

	public void setFolio(BigDecimal folio) {
		this.folio = folio;
	}
    
    public BigDecimal getMontoRet() {
		return montoRet;
	}
	public void setMontoRet(BigDecimal montoRet) {
		this.montoRet = montoRet;
	}
	public String getIdcta() {
		return idcta;
	}
	public void setIdcta(String idcta) {
		this.idcta = idcta;
	}
	public boolean isRectificada() {
		return rectificada;
	}
	public void setRectificada(boolean rectificada) {
		this.rectificada = rectificada;
	}

}

