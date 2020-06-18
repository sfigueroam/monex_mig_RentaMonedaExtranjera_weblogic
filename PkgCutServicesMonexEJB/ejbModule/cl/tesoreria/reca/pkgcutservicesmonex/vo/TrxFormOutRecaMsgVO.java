package cl.tesoreria.reca.pkgcutservicesmonex.vo; 
import java.io.Serializable; 
import java.math.BigDecimal; 

public class TrxFormOutRecaMsgVO implements Serializable { 
  /** 
  * 
  */ 
  private static final long serialVersionUID = 1L; 

  private BigDecimal tipo; 
  private BigDecimal codigo; 
  private BigDecimal severidad; 
  private String glosa; 
  private BigDecimal errcode; 
  private BigDecimal errorTgr; 
  private String errmsg; 
  private String objname; 
  private String objvalue; 
  private String objdescrip; 
 

  public TrxFormOutRecaMsgVO(){ 
  } 


  public void setTipo(BigDecimal newTipo){ 
    this.tipo = newTipo; 
  } 

  public BigDecimal getTipo(){ 
    return this.tipo; 
  } 


  public void setCodigo(BigDecimal newCodigo){ 
    this.codigo = newCodigo; 
  } 

  public BigDecimal getCodigo(){ 
    return this.codigo; 
  } 


  public void setSeveridad(BigDecimal newSeveridad){ 
    this.severidad = newSeveridad; 
  } 

  public BigDecimal getSeveridad(){ 
    return this.severidad; 
  } 


  public void setGlosa(String newGlosa){ 
    this.glosa = newGlosa; 
  } 

  public String getGlosa(){ 
    return this.glosa; 
  } 


  public void setErrcode(BigDecimal newErrcode){ 
    this.errcode = newErrcode; 
  } 

  public BigDecimal getErrcode(){ 
    return this.errcode; 
  } 


  public void setErrorTgr(BigDecimal newErrorTgr){ 
    this.errorTgr = newErrorTgr; 
  } 

  public BigDecimal getErrorTgr(){ 
    return this.errorTgr; 
  } 


  public void setErrmsg(String newErrmsg){ 
    this.errmsg = newErrmsg; 
  } 

  public String getErrmsg(){ 
    return this.errmsg; 
  } 


  public void setObjname(String newObjname){ 
    this.objname = newObjname; 
  } 

  public String getObjname(){ 
    return this.objname; 
  } 


  public void setObjvalue(String newObjvalue){ 
    this.objvalue = newObjvalue; 
  } 

  public String getObjvalue(){ 
    return this.objvalue; 
  } 


  public void setObjdescrip(String newObjdescrip){ 
    this.objdescrip = newObjdescrip; 
  } 

  public String getObjdescrip(){ 
    return this.objdescrip; 
  } 


} 
